/*
  configuration.cpp
*/

// Includes
#include "configuration.h"


// Configuration instance
Configuration configuration;


// Reset configuration
void Configuration::reset()
{
  strcpy(this->wifi.ssid, "");
  strcpy(this->wifi.password, "");
  strcpy(this->time.ntp, "pool.ntp.org");
  this->time.gmt_offset = 60;
  this->time.daylight_offset = 60;
  this->time.refresh_rate = 1;
  this->location.longitude = 0.0;
  this->location.latitude = 0.0;
  this->led.rgb_hours = 0x00FFFFFF;
  this->led.rgb_minutes = 0x00FFFFFF;
  this->led.brightness_day = 10;
  this->led.brightness_night = 5;
  
  EEPROM.begin(sizeof(Configuration));
  EEPROM.put(0, *this);
  EEPROM.commit();
}

// Load configuration
void Configuration::load()
{
  EEPROM.begin(sizeof(Configuration));
  EEPROM.get(0, *this);
}

// Save configuration 
void Configuration::save()
{
  EEPROM.put(0, *this);
  EEPROM.commit();
}

// Print out configuration 
void Configuration::print()
{
  Serial.println("- CONFIGURATION - ");
  Serial.printf("configuration.wifi.ssid:                     \t %s \n", this->wifi.ssid);
  Serial.printf("configuration.wifi.password:                 \t %s \n", this->wifi.password);
  Serial.printf("configuration.time.ntp:                      \t %s \n", this->time.ntp);
  Serial.printf("configuration.time.gmt_offset:               \t %d \n", this->time.gmt_offset);
  Serial.printf("configuration.time.daylight_offset:          \t %d \n", this->time.daylight_offset);
  Serial.printf("configuration.time.refresh_rate:             \t %d \n", this->time.refresh_rate);
  Serial.printf("configuration.location.longitude:            \t %f \n", this->location.longitude);
  Serial.printf("configuration.location.latitude:             \t %f \n", this->location.latitude);
  Serial.printf("configuration.led.rgb_hours:                 \t 0x%6X \n", this->led.rgb_hours);
  Serial.printf("configuration.led.rgb_minutes:               \t 0x%6X \n", this->led.rgb_minutes);
  Serial.printf("configuration.led.brightness_day_hours:      \t %d \n", this->led.brightness_day);
  Serial.printf("configuration.led.brightness_night_hours:    \t %d \n", this->led.brightness_night);
  Serial.println("");
}
