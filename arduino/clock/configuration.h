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
    static const int STRING_SIZE = 60;
    
    /* Wifi settings */
    struct WifiSettings {
      char ssid[STRING_SIZE];
      char password[STRING_SIZE];
    };

    /* Bluetooth settings */
    struct BtSettings {
      
    };

    /* Time settings */
    struct TimeSettings {
      char ntp[STRING_SIZE];
      long refresh_rate;
      long gmt_offset;
      long daylight_offset;
    };

    /* Led settings */
    struct LedSettings {
      long rgb_hours;
      long rgb_minutes;
      byte brightness_day;
      byte brightness_night;
    };
    
    /* Functions */
    void reset();
    void load();
    void save();
    void print();

    /* Accessors */
    WifiSettings wifi;
    BtSettings bt;
    TimeSettings time;
    LedSettings led;
};

/* Configuration instance */
extern Configuration configuration;

#endif
