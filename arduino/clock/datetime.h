/*
  datetime.h
*/

#ifndef DateTime_h
#define DateTime_h

/* Library includes */
#include <WiFi.h>
#include <HTTPClient.h>
#include <ArduinoJson.h>
#include "time.h"

/* Leds namespace */
namespace datetime
{
  /* Functions */
  bool begin_wifi(char* ssid, char* password);
  bool begin_ntp(char* ntp, int gmt, int daylight);
  bool get_time(int &hours, int &minutes);
  bool passed_midnight();
  bool passed_sunrise();
  bool passed_sunset();
  bool update_sunset_sunrise(double latitude, double longitude);
  bool is_day();
  int compare_time(tm first, tm second);
  
  /* Variables */
  extern tm previous_time;
  extern tm sunrise_time;
  extern tm sunset_time;
  extern int gmt_offset;
  extern bool midnight_flag;
  extern bool sunrise_flag;
  extern bool sunset_flag;
};

#endif
