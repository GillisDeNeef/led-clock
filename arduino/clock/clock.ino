/* Project includes */
#include "leds.h"
#include "datetime.h"
#include "bluetooth.h"
#include "configuration.h"

/* Variables */
TaskHandle_t timehandle;
TaskHandle_t bthandle;

/* FreeRTOS tasks */
void taskTime(void* parameter);
void taskBluetooth(void* parameter);

/* Setup */
void setup()
{    
  // Open serial debug port at 115200 bps
  Serial.begin(115200);
  Serial.println();

  // Load EEPROM configuration
  configuration.load();
  configuration.print();

  // Initialize LEDs
  leds::begin();
  leds::set_error(8888);
  leds::set_color_hours(configuration.led.rgb_hours);
  leds::set_color_mins(configuration.led.rgb_minutes);
  leds::set_hours_leading_zero(false);
  
  // Initialize time tracking
  datetime::begin_wifi(configuration.wifi.ssid, configuration.wifi.password);
  datetime::begin_ntp(configuration.time.ntp, configuration.time.gmt_offset, configuration.time.daylight_offset);
  datetime::update_sunset_sunrise();
  if (datetime::is_day()) leds::set_brightness(configuration.led.brightness_day);
  else leds::set_brightness(configuration.led.brightness_night);

  // Setup bluetooth
  bluetooth::begin("LED Clock");

  // Create RTOS tasks
  xTaskCreate(taskTime, "Time task", 4000, NULL, 2, &timehandle);
  xTaskCreate(taskBluetooth, "Bluetooth task", 4000, NULL, 1, &bthandle);
}

/* Bluetooth task */
void taskBluetooth(void* parameter)
{
  // Variables
  int bytes;
  unsigned char temp[64];
  
  while (1) {
    bluetooth::receive(temp, bytes);
    vTaskDelay(10 / portTICK_PERIOD_MS);
  }
}

/* Time task */
void taskTime(void* parameter)
{
  // Variables
  int hours = 0;
  int minutes = 0;

  // Turn all leds off
  leds::clear();
  
  // Loop
  while (1) {
    if(datetime::get_time(hours, minutes)){
      if (datetime::passed_midnight()) {
        datetime::update_sunset_sunrise();
      }
      else if (datetime::passed_sunrise()) {
        leds::set_brightness(configuration.led.brightness_day);
      }
      else if (datetime::passed_sunset()) {
        leds::set_brightness(configuration.led.brightness_night);
      }
      leds::set_red_dot(false);
      leds::set_time(hours, minutes);
    }
    else {
      // let RTC handle time instead
      leds::set_red_dot(true);
    }
    vTaskDelay(configuration.time.refresh_rate * 1000 / portTICK_PERIOD_MS);
  }
}

/* Loop */
void loop()
{
  // not running
}
