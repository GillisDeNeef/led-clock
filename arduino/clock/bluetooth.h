/*
  bluetooth.h
*/

#ifndef Bluetooth_h
#define Bluetooth_h

/* Library includes */
#include "BluetoothSerial.h"

/* Bluetooth namespace */
namespace bluetooth
{
  /* Start and stop */
  const char START  = 0x2A;
  const char STOP   = 0x0A;
  
  /* Commands */
  const char* const WIFI_SSID        = "W0";
  const char* const WIFI_PASSWORD    = "W1";
  const char* const NTP_SERVER       = "N0";
  const char* const NTP_GMT          = "N1";
  const char* const NTP_DAYLIGHT     = "N2";
  const char* const LOC_LONGITUDE    = "L0";
  const char* const LOC_LATITUDE     = "L1";
  const char* const REFRESH_RATE     = "R0";
  const char* const COLOR_HOURS      = "C0";
  const char* const COLOR_MINS       = "C1";
  const char* const BRIGHTNESS_DAY   = "B0";
  const char* const BRIGHTNESS_NIGHT = "B1";
  const char* const FETCH_SETTINGS   = "S0";
  
  /* Initialization */
  bool begin(const char* name);

  /* Communication */
  void send(const char* const cmd);
  void send(const char* const cmd, const char* value);
  void send(const char* const cmd, double value);
  void send(const char* const cmd, long value);
  void send(const char* const cmd, int value);
  bool receive(unsigned char* data, int &length);

  /* Variables */
  extern BluetoothSerial bt;
};

#endif
