/*
  leds.cpp
*/

// Includes
#include "leds.h"

// Variables
CRGB leds::hours_msb[LEDS_PER_DIGIT];
CRGB leds::hours_lsb[LEDS_PER_DIGIT];
CRGB leds::mins_msb[LEDS_PER_DIGIT];
CRGB leds::mins_lsb[LEDS_PER_DIGIT];

CRGB leds::hours_color;
CRGB leds::mins_color;

bool leds::red_dot_enabled = false;
bool leds::hours_leading_zero = false;

// Initialize leds
void leds::begin()
{
  // Add leds to FastLED
  FastLED.addLeds<WS2812B, HOURS_MSB_DATA_PIN, GRB>(hours_msb, LEDS_PER_DIGIT);
  FastLED.addLeds<WS2812B, HOURS_LSB_DATA_PIN, GRB>(hours_lsb, LEDS_PER_DIGIT);
  FastLED.addLeds<WS2812B, MINS_MSB_DATA_PIN, GRB>(mins_msb, LEDS_PER_DIGIT);
  FastLED.addLeds<WS2812B, MINS_LSB_DATA_PIN, GRB>(mins_lsb, LEDS_PER_DIGIT);

  // Configure relay pins
  pinMode(HOURS_MSB_RELAY_PIN, OUTPUT);
  pinMode(HOURS_LSB_RELAY_PIN, OUTPUT);
  pinMode(MINS_MSB_RELAY_PIN, OUTPUT);
  pinMode(MINS_LSB_RELAY_PIN, OUTPUT);
  
  digitalWrite(HOURS_MSB_RELAY_PIN, HIGH);
  for (int i = 0; i < LEDS_PER_DIGIT; i++) hours_msb[i] = CRGB::Black;
  FastLED.show();

  digitalWrite(HOURS_LSB_RELAY_PIN, HIGH);
  for (int i = 0; i < LEDS_PER_DIGIT; i++) hours_lsb[i] = CRGB::Black;
  FastLED.show();
  
  digitalWrite(MINS_MSB_RELAY_PIN, HIGH);
  for (int i = 0; i < LEDS_PER_DIGIT; i++) mins_msb[i] = CRGB::Black;
  FastLED.show();
  
  digitalWrite(MINS_LSB_RELAY_PIN, HIGH);
  for (int i = 0; i < LEDS_PER_DIGIT; i++) mins_lsb[i] = CRGB::Black;
  FastLED.show();
}

// Set brightness
void leds::set_brightness(byte percentage)
{
  FastLED.setBrightness(percentage);
}

// Set color of hours
void leds::set_color_hours(long color)
{
  hours_color = CRGB(color);
}

// Set color of minutes
void leds::set_color_mins(long color)
{
  mins_color = CRGB(color);
}

// Display clear
void leds::clear()
{
  FastLED.showColor(CRGB::Black);
}

// Display error
void leds::set_error(int code)
{
  FastLED.setBrightness(5);
  for (int i = 0; i < NUMBER_OF_SEGMENTS; i++) {
    for (int j = 0; j < LEDS_PER_SEGMENT; j++) {
      if (code >= 0) {
          if (SEGMENT_MAPPING[(code/1000)%10][i] == 1) hours_msb[i*LEDS_PER_SEGMENT+j] = CRGB::Red;
          else hours_msb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
          if (SEGMENT_MAPPING[(code/100)%10][i] == 1) hours_lsb[i*LEDS_PER_SEGMENT+j] = CRGB::Red;
          else hours_lsb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
          if (SEGMENT_MAPPING[(code/10)%10][i] == 1) mins_msb[i*LEDS_PER_SEGMENT+j] = CRGB::Red;
          else mins_msb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
          if (SEGMENT_MAPPING[code%10][i] == 1) mins_lsb[i*LEDS_PER_SEGMENT+j] = CRGB::Red;
          else mins_lsb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
      }
      else {
        hours_msb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
        hours_lsb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
      }
    }
  }
  FastLED.show();
}

// Display time
void leds::set_time(int hours, int minutes)
{
  for (int i = 0; i < NUMBER_OF_SEGMENTS; i++) {
    for (int j = 0; j < LEDS_PER_SEGMENT; j++) {
      if (hours >= 0) {
        if ((((hours/10)%10 != 0) || hours_leading_zero) && SEGMENT_MAPPING[(hours/10)%10][i] == 1) hours_msb[i*LEDS_PER_SEGMENT+j] = hours_color;
        else hours_msb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
        if (SEGMENT_MAPPING[hours%10][i] == 1) hours_lsb[i*LEDS_PER_SEGMENT+j] = hours_color;
        else hours_lsb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
        if (red_dot_enabled) hours_msb[RED_DOT_HOURS_MSB_INDEX] = CRGB::Red;
      }
      if (minutes >= 0) {
        if (SEGMENT_MAPPING[(minutes/10)%10][i] == 1) mins_msb[i*LEDS_PER_SEGMENT+j] = mins_color;
        else mins_msb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
        if (SEGMENT_MAPPING[minutes%10][i] == 1) mins_lsb[i*LEDS_PER_SEGMENT+j] = mins_color;
        else mins_lsb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
      }
    }
  }
  FastLED.show();
}

// Display red dot
void leds::set_red_dot(bool state)
{
  red_dot_enabled = state;
}

// Display hours leading zero
void leds::set_hours_leading_zero(bool state)
{
  hours_leading_zero = state;
}
