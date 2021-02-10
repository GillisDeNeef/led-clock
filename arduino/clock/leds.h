/*
  leds.h
*/

#ifndef Leds_h
#define Leds_h

/* Library includes */
#define FASTLED_ALLOW_INTERRUPTS 0
#include <FastLED.h>

/* Leds namespace */
namespace leds
{
  /* Relay pin definitions */
  const int HOURS_MSB_RELAY_PIN = 2;
  const int HOURS_LSB_RELAY_PIN = 12;
  const int MINS_MSB_RELAY_PIN  = 19;
  const int MINS_LSB_RELAY_PIN  = 21;
  
  /* Data pin definitions */
  const int HOURS_MSB_DATA_PIN  = 4;
  const int HOURS_LSB_DATA_PIN  = 14;
  const int MINS_MSB_DATA_PIN   = 23;
  const int MINS_LSB_DATA_PIN   = 22;
  
  /* Segment definitions */
  const int LEDS_PER_SEGMENT    = 4;
  const int NUMBER_OF_SEGMENTS  = 7;
  const int LEDS_PER_DIGIT      = NUMBER_OF_SEGMENTS * LEDS_PER_SEGMENT;
  
  /* 7 Segment to digit mapping (for custom PCB) */
  const int SEGMENT_MAPPING[10][NUMBER_OF_SEGMENTS] = 
  {
    {1,1,1,0,1,1,1},
    {0,0,1,0,0,0,1},
    {0,1,1,1,1,1,0},
    {0,1,1,1,0,1,1},
    {1,0,1,1,0,0,1},
    {1,1,0,1,0,1,1},
    {1,1,0,1,1,1,1},
    {0,1,1,0,0,0,1},
    {1,1,1,1,1,1,1},
    {1,1,1,1,0,1,1},
  };

  /* Red dot indication */
  const int RED_DOT_HOURS_MSB_INDEX = 3;
  
  /* Initialization */
  void begin();
  
  /* Brightness */
  void set_brightness(byte percentage);

  /* Color */
  void set_color_hours(long color);
  void set_color_mins(long color);

  /* Display */
  void clear();
  void set_error(int code);
  void set_time(int hours, int minutes);
  void set_red_dot(bool state);
  void set_hours_leading_zero(bool state);
  
  /* Led strip instances */
  extern CRGB hours_msb[LEDS_PER_DIGIT];
  extern CRGB hours_lsb[LEDS_PER_DIGIT];
  extern CRGB mins_msb[LEDS_PER_DIGIT];
  extern CRGB mins_lsb[LEDS_PER_DIGIT];

  /* Defined colors */
  extern CRGB hours_color;
  extern CRGB mins_color;

  /* Red dot */
  extern bool red_dot_enabled;
  extern bool hours_leading_zero;
};

#endif
