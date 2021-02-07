/* Project includes */
#include "leds.h"
#include "datetime.h"
#include "bluetooth.h"
#include "configuration.h"

/* Constants */
const char* const BLUETOOTH_NAME = "LED Clock";
const int BLINK_TIME_MS = 50;

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
  
  // Create RTOS tasks
  xTaskCreate(taskTime, "Time task", 4000, NULL, 1, &timehandle);
  xTaskCreate(taskBluetooth, "Bluetooth task", 4000, NULL, 2, &bthandle);
}

/* Bluetooth task */
void taskBluetooth(void* parameter)
{
  // Variables
  unsigned char message[64];
  int count;

  // Setup bluetooth
  bluetooth::begin("LED Clock");
  
  while (1) {
    if (bluetooth::receive(message, count)) {
      if (memcmp(message, bluetooth::FETCH_SETTINGS, 2) == 0) {
        vTaskSuspend(timehandle);
        leds::set_time(88, 88);
        bluetooth::send(bluetooth::WIFI_SSID, configuration.wifi.ssid);
        bluetooth::send(bluetooth::WIFI_PASSWORD, configuration.wifi.password);
        bluetooth::send(bluetooth::NTP_SERVER, configuration.time.ntp);
        bluetooth::send(bluetooth::NTP_GMT, configuration.time.gmt_offset);
        bluetooth::send(bluetooth::NTP_DAYLIGHT, configuration.time.daylight_time);
        bluetooth::send(bluetooth::REFRESH_RATE, configuration.time.refresh_rate);
        bluetooth::send(bluetooth::LOC_LONGITUDE, configuration.location.longitude);
        bluetooth::send(bluetooth::LOC_LATITUDE, configuration.location.latitude);
        bluetooth::send(bluetooth::COLOR_HOURS, configuration.led.rgb_hours);
        bluetooth::send(bluetooth::COLOR_MINS, configuration.led.rgb_minutes);
        bluetooth::send(bluetooth::BRIGHTNESS_DAY, configuration.led.brightness_day);
        bluetooth::send(bluetooth::BRIGHTNESS_NIGHT, configuration.led.brightness_night);
        bluetooth::send(bluetooth::FETCH_SETTINGS);
      }
      else if (memcmp(message, bluetooth::WIFI_SSID, 2) == 0) {
        if (bluetooth::get_data(message, count) == nullptr) memset(configuration.wifi.ssid, 0, sizeof(configuration.wifi.ssid));
        else memcpy(configuration.wifi.ssid, bluetooth::get_data(message, count), (sizeof(configuration.wifi.ssid) >= count - 2) ? count - 2 : sizeof(configuration.wifi.ssid));
        
        leds::clear();
        vTaskDelay(BLINK_TIME_MS / portTICK_PERIOD_MS);
        leds::set_time(88, 88);
      }
      else if (memcmp(message, bluetooth::WIFI_PASSWORD, 2) == 0) {
        if (bluetooth::get_data(message, count) == nullptr) memset(configuration.wifi.password, 0, sizeof(configuration.wifi.password));
        else memcpy(configuration.wifi.password, bluetooth::get_data(message, count), (sizeof(configuration.wifi.password) >= count - 2) ? count - 2 : sizeof(configuration.wifi.password));
        
        leds::clear();
        vTaskDelay(BLINK_TIME_MS / portTICK_PERIOD_MS);
        leds::set_time(88, 88);
      }
      else if (memcmp(message, bluetooth::NTP_SERVER, 2) == 0) {
        if (bluetooth::get_data(message, count) == nullptr) memset(configuration.time.ntp, 0, sizeof(configuration.time.ntp));
        else memcpy(configuration.time.ntp, bluetooth::get_data(message, count), (sizeof(configuration.time.ntp) >= count - 2) ? count - 2 : sizeof(configuration.time.ntp));
        
        leds::clear();
        vTaskDelay(BLINK_TIME_MS / portTICK_PERIOD_MS);
        leds::set_time(88, 88);
      }
      else if (memcmp(message, bluetooth::NTP_GMT, 2) == 0) {
        if (bluetooth::get_data(message, count) == nullptr) configuration.time.gmt_offset = 0;
        else configuration.time.gmt_offset = atoi((char*)bluetooth::get_data(message, count));
        
        leds::clear();
        vTaskDelay(BLINK_TIME_MS / portTICK_PERIOD_MS);
        leds::set_time(88, 88);
      }
      else if (memcmp(message, bluetooth::NTP_DAYLIGHT, 2) == 0) {
        if (bluetooth::get_data(message, count) == nullptr) configuration.time.daylight_time = 0;
        else configuration.time.daylight_time = atoi((char*)bluetooth::get_data(message, count));
        
        leds::clear();
        vTaskDelay(BLINK_TIME_MS / portTICK_PERIOD_MS);
        leds::set_time(88, 88);
      }
      else if (memcmp(message, bluetooth::LOC_LONGITUDE, 2) == 0) {
        if (bluetooth::get_data(message, count) == nullptr) configuration.location.longitude = 0.0;
        else configuration.location.longitude = atof((char*)bluetooth::get_data(message, count));
        
        leds::clear();
        vTaskDelay(BLINK_TIME_MS / portTICK_PERIOD_MS);
        leds::set_time(88, 88);
      }
      else if (memcmp(message, bluetooth::LOC_LATITUDE, 2) == 0) {
        if (bluetooth::get_data(message, count) == nullptr) configuration.location.latitude = 0.0;
        else configuration.location.latitude = atof((char*)bluetooth::get_data(message, count));
        
        leds::clear();
        vTaskDelay(BLINK_TIME_MS / portTICK_PERIOD_MS);
        leds::set_time(88, 88);
      }
      else if (memcmp(message, bluetooth::REFRESH_RATE, 2) == 0) {
        if (bluetooth::get_data(message, count) == nullptr) configuration.time.refresh_rate = 0;
        else configuration.time.refresh_rate = atoi((char*)bluetooth::get_data(message, count));
        
        leds::clear();
        vTaskDelay(BLINK_TIME_MS / portTICK_PERIOD_MS);
        leds::set_time(88, 88);
      }
      else if (memcmp(message, bluetooth::COLOR_HOURS, 2) == 0) {
        if (bluetooth::get_data(message, count) == nullptr) configuration.led.rgb_hours = 0;
        else configuration.led.rgb_hours = strtoul((char*)bluetooth::get_data(message, count), NULL, 16);
        
        Serial.printf("Color: %06X\n", configuration.led.rgb_minutes);
        leds::set_color_hours(configuration.led.rgb_hours);
        leds::set_time(88, 88);
      }
      else if (memcmp(message, bluetooth::COLOR_MINS, 2) == 0) {
        if (bluetooth::get_data(message, count) == nullptr) configuration.led.rgb_minutes = 0;
        else configuration.led.rgb_minutes = strtoul((char*)bluetooth::get_data(message, count), NULL, 16);
        
        Serial.printf("Color: %06X\n", configuration.led.rgb_minutes);
        leds::set_color_mins(configuration.led.rgb_minutes);
        leds::set_time(88, 88);
      }
      else if (memcmp(message, bluetooth::BRIGHTNESS_DAY, 2) == 0) {
        if (bluetooth::get_data(message, count) == nullptr) configuration.led.brightness_day = 0;
        else configuration.led.brightness_day = atoi((char*)bluetooth::get_data(message, count));
        
        leds::set_brightness(configuration.led.brightness_day);
        leds::set_time(88, 88);
      }
      else if (memcmp(message, bluetooth::BRIGHTNESS_NIGHT, 2) == 0) {
        if (bluetooth::get_data(message, count) == nullptr) configuration.led.brightness_night = 0;
        else configuration.led.brightness_night = atoi((char*)bluetooth::get_data(message, count));

        leds::set_brightness(configuration.led.brightness_night);
        leds::set_time(88, 88);
      }
      else if (memcmp(message, bluetooth::SAVE_SETTINGS, 2) == 0) {
        configuration.save();
        ESP.restart();
      }
      memset(message, 0, sizeof(message));
    }
    vTaskDelay(10 / portTICK_PERIOD_MS);
  }
}

/* Time task */
void taskTime(void* parameter)
{
  // Variables
  int hours = 0;
  int minutes = 0;
  
  // Initialize time tracking
  datetime::begin_wifi(configuration.wifi.ssid, configuration.wifi.password);
  datetime::begin_ntp(configuration.time.ntp, configuration.time.gmt_offset, configuration.time.daylight_time);
  datetime::update_sunset_sunrise();
  if (datetime::is_day()) leds::set_brightness(configuration.led.brightness_day);
  else leds::set_brightness(configuration.led.brightness_night);

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
