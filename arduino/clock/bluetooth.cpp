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

// Send command
void bluetooth::send(const char* const cmd)
{
  unsigned char temp[4];
  
  temp[0] = START;
  memcpy(&temp[1], cmd, 2);
  temp[3] = STOP;
  
  bt.write(temp, 4);
}

// Send const char* value
void bluetooth::send(const char* const cmd, const char* value)
{
  unsigned char temp[128];
  
  temp[0] = START;
  memcpy(&temp[1], cmd, 2);
  memcpy(&temp[3], value, strlen(value));
  temp[3 + strlen(value)] = STOP;

  bt.write(temp, 4 + strlen(value));
}

// Send double value
void bluetooth::send(const char* const cmd, double value)
{
  unsigned char temp[128];

  char conv[20];
  sprintf(conv, "%.6f", value);
  
  temp[0] = START;
  memcpy(&temp[1], cmd, 2);
  memcpy(&temp[3], conv, strlen(conv));
  temp[3 + strlen(conv)] = STOP;

  bt.write(temp, 4 + strlen(conv));
}

// Send long value as hex
void bluetooth::send(const char* const cmd, unsigned long value)
{
  unsigned char temp[128];

  char conv[20];
  sprintf(conv, "%06X", value);
  
  temp[0] = START;
  memcpy(&temp[1], cmd, 2);
  memcpy(&temp[3], conv, strlen(conv));
  temp[3 + strlen(conv)] = STOP;

  bt.write(temp, 4 + strlen(conv));
}

// Send int value
void bluetooth::send(const char* const cmd, unsigned int value)
{
  unsigned char temp[128];

  char conv[20];
  sprintf(conv, "%d", value);
  
  temp[0] = START;
  memcpy(&temp[1], cmd, 2);
  memcpy(&temp[3], conv, strlen(conv));
  temp[3 + strlen(conv)] = STOP;

  bt.write(temp, 4 + strlen(conv));
}

// Receive data from android app
bool bluetooth::receive(unsigned char* msg, int &len)
{
  int temp = bt.read();
  if (temp < 0 || temp != START) return false;

  len = 0;
  temp = bt.read();
  vTaskDelay(1 / portTICK_PERIOD_MS);

  while (temp > 0) {
    msg[len++] = temp & 0xFF;
    temp = bt.read();
    if (temp == STOP) return true;
    else vTaskDelay(1 / portTICK_PERIOD_MS);
  }

  return false;
}

// Get pointer to data within given message
unsigned char* bluetooth::get_data(unsigned char* msg, int len)
{
  if (len <= 2) return nullptr;
  else return &msg[2];
}
