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
  /* Initialization */
  bool begin(const char* name);

  /* Communication */
  bool send(unsigned char* data, int length);
  bool receive(unsigned char* data, int &length);

  /* Variables */
  extern BluetoothSerial bt;
};

#endif
