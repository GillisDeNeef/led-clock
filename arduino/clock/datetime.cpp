/*
  datetime.cpp
*/

// Includes
#include "datetime.h"

// Variables
tm datetime::previous_time;
tm datetime::sunrise_time;
tm datetime::sunset_time;
int datetime::gmt_offset;
bool datetime::midnight_flag;
bool datetime::sunrise_flag;
bool datetime::sunset_flag;

// Start wifi connection
bool datetime::begin_wifi(char* ssid, char* password)
{
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) delay(500);
}

// Start ntp connection
bool datetime::begin_ntp(char* ntp, int gmt, int daylight)
{
  configTime(gmt*60, daylight*60, ntp);
  while(!getLocalTime(&previous_time)) delay(500);
  gmt_offset = gmt / 60;
}

// Returns current time
bool datetime::get_time(int &hours, int &minutes)
{
  tm current_time;
  if (getLocalTime(&current_time)) {
      if ((previous_time.tm_year < current_time.tm_year) || (previous_time.tm_mon < current_time.tm_mon) || (previous_time.tm_mday < current_time.tm_mday)) {
        midnight_flag = true;
      }
      if ((compare_time(previous_time, sunrise_time) == -1) && ((compare_time(current_time, sunrise_time) == 0) || (compare_time(current_time, sunrise_time) == 1))) {
        sunrise_flag = true;
      }
      if ((compare_time(previous_time, sunset_time) == -1) && ((compare_time(current_time, sunset_time) == 0) || (compare_time(current_time, sunset_time) == 1))) {
        sunset_flag = true;
      }
      hours = current_time.tm_hour;
      minutes = current_time.tm_min;
      previous_time = current_time;
      return true;
  }
  return false;
}

// Update sunset and sunrise times
bool datetime::update_sunset_sunrise(double latitude, double longitude)
{
  char temp[128];
  sprintf(temp, "https://api.sunrise-sunset.org/json?lat=%.7f&lng=%.7f&formatted=0", latitude, longitude);

  HTTPClient http;
  if (!http.begin(temp)) return false;
  int httpResponseCode = http.GET();
  
  if (httpResponseCode>0) {
    Serial.print("HTTP Response code: ");
    Serial.println(httpResponseCode);
    
    String response = http.getString();
    Serial.println(response);
    
    StaticJsonDocument<1024> json;
    DeserializationError error = deserializeJson(json, response);
    if (error) return false;
  
    strcpy(temp, json["results"]["sunrise"].as<const char*>());
    sunrise_time.tm_year = ((temp[0]-0x30) * 1000 + (temp[1]-0x30) * 100 + (temp[2]-0x30) * 10 + (temp[3]-0x30)) - 1900;
    sunrise_time.tm_mon = ((temp[5]-0x30) * 10 + (temp[6]-0x30)) - 1;
    sunrise_time.tm_mday = (temp[8]-0x30) * 10 + (temp[9]-0x30);
    sunrise_time.tm_hour = (temp[11]-0x30) * 10 + (temp[12]-0x30) + gmt_offset;
    sunrise_time.tm_min = (temp[14]-0x30) * 10 + (temp[15]-0x30);
  
    strcpy(temp, json["results"]["sunset"].as<const char*>());
    sunset_time.tm_year = ((temp[0]-0x30) * 1000 + (temp[1]-0x30) * 100 + (temp[2]-0x30) * 10 + (temp[3]-0x30)) - 1900;
    sunset_time.tm_mon = ((temp[5]-0x30) * 10 + (temp[6]-0x30)) - 1;
    sunset_time.tm_mday = (temp[8]-0x30) * 10 + (temp[9]-0x30);
    sunset_time.tm_hour = (temp[11]-0x30) * 10 + (temp[12]-0x30) + gmt_offset;
    sunset_time.tm_min = (temp[14]-0x30) * 10 + (temp[15]-0x30);
  
    http.end();
    return true;
  }
  else {
    Serial.print("Error code: ");
    Serial.println(httpResponseCode);
    
    http.end();
    return false;
  }  
}

// Returns true if its day
bool datetime::is_day()
{
  tm current_time;
  if (getLocalTime(&current_time)) {
    if ((compare_time(current_time, sunrise_time) == 1) && (compare_time(current_time, sunset_time) == -1)) {
      return true;
    }
  }
  return false;
}

// Returns true if time passed midnight
bool datetime::passed_midnight()
{
  if (midnight_flag) {
    midnight_flag = false;
    return true;
  }
  return false;
}

// Returns true if time passed sunrise time
bool datetime::passed_sunrise()
{
  if (sunrise_flag) {
    sunrise_flag = false;
    return true;
  }
  return false;
}

// Returns true if time passed sunset time
bool datetime::passed_sunset()
{
  if (sunset_flag) {
    sunset_flag = false;
    return true;
  }
  return false;
}

// Returns -1 if first comes before second, 0 if they are the same, 1 if first comes after second
int datetime::compare_time(tm first, tm second)
{
  if (first.tm_year > second.tm_year) return 1;
  else if (first.tm_year < second.tm_year) return -1;
  if (first.tm_mon > second.tm_mon) return 1;
  else if (first.tm_mon < second.tm_mon) return -1;
  if (first.tm_mday > second.tm_mday) return 1;
  else if (first.tm_mday < second.tm_mday) return -1;
  if (first.tm_hour > second.tm_hour) return 1;
  else if (first.tm_hour < second.tm_hour) return -1;
  if (first.tm_min > second.tm_min) return 1;
  else if (first.tm_min < second.tm_min) return -1;
  if (first.tm_sec > second.tm_sec) return 1;
  else if (first.tm_sec < second.tm_sec) return -1;
  return 0;
}
