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

// Initialize leds
void leds::begin()
{
  Serial.println("- LEDS -");
  
  pinMode(HOURS_MSB_RELAY_PIN, OUTPUT);
  digitalWrite(HOURS_MSB_RELAY_PIN, HIGH);
  FastLED.addLeds<WS2812B, HOURS_MSB_DATA_PIN, GRB>(hours_msb, LEDS_PER_DIGIT);
  for (int i = 0; i < LEDS_PER_DIGIT; i++) {
    hours_msb[i] = CRGB::Black;
    FastLED.show();
    delay(5);
  }
  Serial.printf("Added %d leds to pin %d.\n", LEDS_PER_DIGIT, HOURS_MSB_DATA_PIN);

  pinMode(HOURS_LSB_RELAY_PIN, OUTPUT);
  digitalWrite(HOURS_LSB_RELAY_PIN, HIGH);
  FastLED.addLeds<WS2812B, HOURS_LSB_DATA_PIN, GRB>(hours_lsb, LEDS_PER_DIGIT);
  for (int i = 0; i < LEDS_PER_DIGIT; i++) {
    hours_lsb[i] = CRGB::Black;
    FastLED.show();
    delay(5);
  }
  Serial.printf("Added %d leds to pin %d.\n", LEDS_PER_DIGIT, HOURS_LSB_DATA_PIN);
  
  pinMode(MINS_MSB_RELAY_PIN, OUTPUT);
  digitalWrite(MINS_MSB_RELAY_PIN, HIGH);
  FastLED.addLeds<WS2812B, MINS_MSB_DATA_PIN, GRB>(mins_msb, LEDS_PER_DIGIT);
  for (int i = 0; i < LEDS_PER_DIGIT; i++) {
    mins_msb[i] = CRGB::Black;
    FastLED.show();
    delay(5);
  }
  Serial.printf("Added %d leds to pin %d.\n", LEDS_PER_DIGIT, MINS_MSB_DATA_PIN);
  
  pinMode(MINS_LSB_RELAY_PIN, OUTPUT);
  digitalWrite(MINS_LSB_RELAY_PIN, HIGH);
  FastLED.addLeds<WS2812B, MINS_LSB_DATA_PIN, GRB>(mins_lsb, LEDS_PER_DIGIT);
  for (int i = 0; i < LEDS_PER_DIGIT; i++) {
    mins_lsb[i] = CRGB::Black;
    FastLED.show();
    delay(5);
  }
  Serial.printf("Added %d leds to pin %d.\n", LEDS_PER_DIGIT, MINS_LSB_DATA_PIN);
  Serial.println();
}

// Set brightness
void leds::set_brightness(byte percentage)
{
  FastLED.setBrightness(percentage);
  Serial.printf("Set brightness to %d%.\n", percentage);
  Serial.println();
}

// Set color of hours
void leds::set_color_hours(long color)
{
  hours_color = CRGB(color);
  Serial.printf("Set hours color to %6X.\n", color);
  Serial.println();
}

// Set color of minutes
void leds::set_color_mins(long color)
{
  mins_color = CRGB(color);
  Serial.printf("Set minutes color to %6X.\n", color);
  Serial.println();
}

// Display error
void leds::set_error(int code)
{
  for (int i = 0; i < NUMBER_OF_SEGMENTS; i++) {
    for (int j = 0; j < LEDS_PER_SEGMENT; j++) {
      if (code >= 0) {
          if (SEGMENT_MAPPING[code/1000][i] == 1) hours_msb[i*LEDS_PER_SEGMENT+j] = CRGB::Red;
          else hours_msb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
          if (SEGMENT_MAPPING[code/100][i] == 1) hours_lsb[i*LEDS_PER_SEGMENT+j] = CRGB::Red;
          else hours_lsb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
          if (SEGMENT_MAPPING[code/10][i] == 1) mins_msb[i*LEDS_PER_SEGMENT+j] = CRGB::Red;
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
  Serial.printf("Set error to %d.\n", code);
}

// Display time
void leds::set_time(byte hours, byte minutes)
{
  for (int i = 0; i < NUMBER_OF_SEGMENTS; i++) {
    for (int j = 0; j < LEDS_PER_SEGMENT; j++) {
      if (hours >= 0) {
        if (hours/10) {
          if (SEGMENT_MAPPING[hours/10][i] == 1) hours_msb[i*LEDS_PER_SEGMENT+j] = hours_color;
          else hours_msb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
        }
        else {
          hours_msb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
          hours_lsb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
        }
        if (hours%10) {
          if (SEGMENT_MAPPING[hours%10][i] == 1) hours_lsb[i*LEDS_PER_SEGMENT+j] = hours_color;
          else hours_lsb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
        }
        else {
          hours_msb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
          hours_lsb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
        }
      }
      else {
        hours_msb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
        hours_lsb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
      }
    }
  }
  for (int i = 0; i < NUMBER_OF_SEGMENTS; i++) {
    for (int j = 0; j < LEDS_PER_SEGMENT; j++) {
      if (minutes >= 0) {
        if (minutes/10) {
          if (SEGMENT_MAPPING[minutes/10][i] == 1) mins_msb[i*LEDS_PER_SEGMENT+j] = mins_color;
          else mins_msb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
        }
        else {
          mins_msb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
          mins_lsb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
        }
        if (minutes%10) {
          if (SEGMENT_MAPPING[minutes%10][i] == 1) mins_lsb[i*LEDS_PER_SEGMENT+j] = mins_color;
          else mins_lsb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
        }
        else {
          mins_msb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
          mins_lsb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
        }
      }
      else {
        mins_msb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
        mins_lsb[i*LEDS_PER_SEGMENT+j] = CRGB::Black;
      }
    }
  }
  FastLED.show();
  Serial.printf("Set time to %d:%d.\n", hours, minutes);
}
