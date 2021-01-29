/*
  bluetooth.cpp
*/

// Includes
#include "bluetooth.h"

// Variables
BluetoothSerial bluetooth::bt;

// Initialization
bool bluetooth::begin(const char* name)
{
  return bt.begin(name);
}

// Send data to android app
bool bluetooth::send(unsigned char* data, int length)
{
  
}

// Receive data from android app
bool bluetooth::receive(unsigned char* data, int &length)
{
  
}
