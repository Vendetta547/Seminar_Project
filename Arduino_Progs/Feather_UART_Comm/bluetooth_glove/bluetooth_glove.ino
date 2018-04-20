#include <Arduino.h>
#include <SPI.h>
#include "Adafruit_BLE.h"
#include "Adafruit_BluefruitLE_SPI.h"
#include "Adafruit_BluefruitLE_UART.h"

#include "BluefruitConfig.h"

#if SOFTWARE_SERIAL_AVAILABLE
  #include <SoftwareSerial.h>
#endif

#define FACTORYRESET_ENABLE         1
#define MINIMUM_FIRMWARE_VERSION    "0.6.6"
#define MODE_LED_BEHAVIOUR          "MODE"
/*=========================================================================*/

// Create the bluefruit object, either software serial...uncomment these lines


/* ...hardware SPI, using SCK/MOSI/MISO hardware SPI pins and then user selected CS/IRQ/RST */
Adafruit_BluefruitLE_SPI ble(BLUEFRUIT_SPI_CS, BLUEFRUIT_SPI_IRQ, BLUEFRUIT_SPI_RST);

// A small helper
void error(const __FlashStringHelper*err) {
  Serial.println(err);
  while (1);
}


const int play_button = 2;
const int play_button2 = 5;
const int play_button3 = 11;

void setup(void)
{
  pinMode(play_button, INPUT);

  Serial.begin(115200);

  /* Initialise the module */
  Serial.print(F("Initialising the Bluefruit LE module: "));

  if ( !ble.begin(VERBOSE_MODE) )
  {
    error(F("Couldn't find Bluefruit, make sure it's in CoMmanD mode & check wiring?"));
  }
  Serial.println( F("OK!") );

  if ( FACTORYRESET_ENABLE )
  {
    /* Perform a factory reset to make sure everything is in a known state */
    Serial.println(F("Performing a factory reset: "));
    if ( ! ble.factoryReset() ){
      error(F("Couldn't factory reset"));
    }
  }

  /* Disable command echo from Bluefruit */
  ble.echo(false);

  ble.verbose(false);  // debug info is a little annoying after this point!

  /* Wait for connection */
  while (! ble.isConnected()) {
     // delay(500);
  }

  // LED Activity command is only supported from 0.6.6
  if ( ble.isVersionAtLeast(MINIMUM_FIRMWARE_VERSION) )
  {
    // Change Mode LED Activity
    Serial.println(F("******************************"));
    Serial.println(F("Change LED activity to " MODE_LED_BEHAVIOUR));
    ble.sendCommandCheckOK("AT+HWModeLED=" MODE_LED_BEHAVIOUR);
    Serial.println(F("******************************"));
  }
}



int buttonState = 0; 
int lastButtonState = 0;
int buttonState2 = 0;
int lastButtonState2 = 0;
int buttonState3 = 0;
int lastButtonState3 = 0; 
void loop(void)
{
// check if the pushbutton is pressed. If it is, the buttonState is HIGH:
    buttonState = digitalRead(play_button);
    if (buttonState != lastButtonState) {
      if (buttonState == LOW) {
        //
      } else {
        //Serial.println("LOW");
        Serial.print("[Send] ");
        Serial.println("play/pause");

        ble.print("AT+BLEUARTTX=");
        ble.println("play/pause"); 
      }
      delay(50);
    }
    lastButtonState = buttonState;

    // check if the pushbutton is pressed. If it is, the buttonState is HIGH:
    buttonState2 = digitalRead(play_button2);
    if (buttonState2 != lastButtonState2) {
      if (buttonState2 == LOW) {
        //
      } else {
        //Serial.println("LOW");
        Serial.print("[Send] ");
        Serial.println("next");

        ble.print("AT+BLEUARTTX=");
        ble.println("next"); 
      }
      delay(50);
    }
    lastButtonState2 = buttonState2;

    // check if the pushbutton is pressed. If it is, the buttonState is HIGH:
    buttonState3 = digitalRead(play_button3);
    if (buttonState3 != lastButtonState3) {
      if (buttonState3 == LOW) {
        //
      } else {
        //Serial.println("LOW");
        Serial.print("[Send] ");
        Serial.println("previous");

        ble.print("AT+BLEUARTTX=");
        ble.println("previous"); 
      }
      delay(50);
    }
    lastButtonState3 = buttonState3;
}
