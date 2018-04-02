
const int buttonPin = 2;     // the number of the pushbutton pin
const int ledPin =  3;      // the number of the LED pin
const int buttonPin2 = 5; 
const int ledPin2 = 6;
const int buttonPin3 = 11;
const int ledPin3 = 12;
// variables will change:
int buttonState = 0;         // variable for reading the pushbutton status
int lastButtonState = 0; 
int buttonState2 = 0; 
int lastButtonState2 = 0; 
int buttonState3 = 0;
int lastButtonState3 = 0;

void setup() {
  // initialize the LED pin as an output:
  pinMode(ledPin, OUTPUT);
  // initialize the pushbutton pin as an input:
  pinMode(buttonPin, INPUT);
  Serial.begin(115200);
}

void loop() {
  // read the state of the pushbutton value:
  buttonState = digitalRead(buttonPin);
  if (buttonState != lastButtonState) {
    if (buttonState == HIGH) {
      Serial.println("HIGH");
      digitalWrite(ledPin, HIGH);
    } else {
      Serial.println("LOW");
      digitalWrite(ledPin, LOW);
    }
    delay(50);
  }
  lastButtonState = buttonState; 

  // read the state of the pushbutton value:
  buttonState2 = digitalRead(buttonPin2);
  if (buttonState2 != lastButtonState2) {
    if (buttonState2 == HIGH) {
      Serial.println("HIGH");
      digitalWrite(ledPin2, HIGH);
    } else {
      Serial.println("LOW");
      digitalWrite(ledPin2, LOW);
    }
    delay(50);
  }
  lastButtonState2 = buttonState2;

  // read the state of the pushbutton value:
  buttonState3 = digitalRead(buttonPin3);
  if (buttonState3 != lastButtonState3) {
    if (buttonState3 == HIGH) {
      Serial.println("HIGH");
      digitalWrite(ledPin3, HIGH);
    } else {
      Serial.println("LOW");
      digitalWrite(ledPin3, LOW);
    }
    delay(50);
  }
  lastButtonState3 = buttonState3;
}
