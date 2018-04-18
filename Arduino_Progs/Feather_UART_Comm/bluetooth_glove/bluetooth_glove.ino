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

#define PLAY_BUTTON 6
#define NEXT_BUTTON 10
#define BACK_BUTTON 3
#define NUMBER_OF_BUTTONS 3
/*=========================================================================*/
/* create bluefruit object */ 
Adafruit_BluefruitLE_SPI ble(BLUEFRUIT_SPI_CS, BLUEFRUIT_SPI_IRQ, BLUEFRUIT_SPI_RST);

typedef struct {
  int current_state; 
  int last_state;
}States;

typedef struct {
  int input_pin;
  States states;
}Button;

Button play_button = {
  PLAY_BUTTON,
  {0, 0},
};

Button next_button = {
  NEXT_BUTTON,
  {0, 0},
};

Button back_button = {
  BACK_BUTTON,
  {0, 0},
};

Button buttons[NUMBER_OF_BUTTONS] = {play_button, next_button, back_button}; 


// A small helper
void error(const __FlashStringHelper*err) {
  Serial.println(err);
  while (1);
}

void setup(void)
{
  pinMode(buttons[0].input_pin, INPUT);
  pinMode(buttons[1].input_pin, INPUT);
  pinMode(buttons[2].input_pin, INPUT); 

  Serial.begin(115200);

  if ( !ble.begin(VERBOSE_MODE) )
  {
    error(F("Couldn't find Bluefruit, make sure it's in CoMmanD mode & check wiring?"));
  }

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
   //   delay(500);
  }

  // LED Activity command is only supported from 0.6.6
  if ( ble.isVersionAtLeast(MINIMUM_FIRMWARE_VERSION) )
  {
    // Change Mode LED Activity
   // Serial.println(F("******************************"));
   // Serial.println(F("Change LED activity to " MODE_LED_BEHAVIOUR));
    ble.sendCommandCheckOK("AT+HWModeLED=" MODE_LED_BEHAVIOUR);
   // Serial.println(F("******************************"));
  }
}

/**************************************************************************/
/*!
    @brief  Constantly poll for new command or response data
*/
/**************************************************************************/
void loop(void)
{
String message = ""; 
// check if the pushbutton is pressed. If it is, the buttonState is HIGH:
  for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {
    buttons[i].states.current_state = digitalRead(buttons[i].input_pin);
    int current_state = buttons[i].states.current_state;
    int last_state = buttons[i].states.last_state;   
    if (current_state != last_state) {
      if (current_state == HIGH) {
        ble.print("AT+BLEUARTTX=");
        switch(buttons[i].input_pin) {
          case PLAY_BUTTON:
            message = "play/pause";
            break;
          case NEXT_BUTTON:
            message = "next";
            break;
          case BACK_BUTTON:
            message = "previous"; 
            break;
        }
        if (message != "") {
          ble.print(message);
        }
      }
      delay(50);
    }
    buttons[i].states.last_state = buttons[i].states.current_state;
  }  
}


