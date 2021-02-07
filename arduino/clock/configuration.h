/*
  configuration.h
*/

#ifndef Configuration_h
#define Configuration_h

/* Library includes */
#include "EEPROM.h"

/* Class definition */
class Configuration
{
  public:
    /* Max string length */
    static const int STRING_SIZE = 64;
    
    /* Wifi settings */
    struct WifiSettings {
      char ssid[STRING_SIZE];
      char password[STRING_SIZE];
    };

    /* Time settings */
    struct TimeSettings {
      char ntp[STRING_SIZE];
      unsigned int gmt_offset;
      unsigned int daylight_time;
      unsigned int refresh_rate;
    };

    /* Location settings */
    struct LocationSettings {
      double longitude;
      double latitude;
    };

    /* Led settings */
    struct LedSettings {
      unsigned long rgb_hours;
      unsigned long rgb_minutes;
      unsigned int brightness_day;
      unsigned int brightness_night;
    };
    
    /* Functions */
    void reset();
    void load();
    void save();
    void print();

    /* Accessors */
    WifiSettings wifi;
    TimeSettings time;
    LocationSettings location;
    LedSettings led;
};

/* Configuration instance */
extern Configuration configuration;

#endif
