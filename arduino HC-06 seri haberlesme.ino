
#include <SoftwareSerial.h>
#include <Servo.h>

#define rxPin 5
#define txPin 6

// Servo servo1;
// Servo servo2;
// Servo servo3;
// Servo servo4;


// Set up a new SoftwareSerial object
SoftwareSerial mySerial =  SoftwareSerial(rxPin, txPin);

String inString = "";  // string to hold input
int Throttle = 127;
int Yaw = 127;
int Roll = 127;
int Pitch = 127;

int ThrottlePin = 9;
int YawPin = 10;
int RollPin = 3;
int PitchPin = 11;

void setup() {

  // servo1.attach(ThrottlePin);
  // servo2.attach(YawPin);
  // servo3.attach(RollPin);
  // servo4.attach(PitchPin);


  pinMode(ThrottlePin, OUTPUT);  // Throttle
  pinMode(YawPin, OUTPUT);  // Yaw
  pinMode(RollPin, OUTPUT);  // Roll
  pinMode(PitchPin, OUTPUT);  // Pitch

  pinMode(rxPin, INPUT);
  pinMode(txPin, OUTPUT);


  // Open serial communications and wait for port to open:
  mySerial.begin(9600);
  Serial.begin(9600);
}

void loop() {
  // Read serial input:
  if (mySerial.available() > 0) {
    int data = mySerial.parseInt();
    if( 0 < data && data < 1000){
      Throttle = map(data, 0, 1000, 0, 255);
      Serial.print("Throttle: " );
      Serial.println( Throttle);
    }
    else if(1000 <= data && data < 2000){
      Yaw = map(data, 1000, 2000, 0, 255);
      Serial.print("Yaw: ");
      Serial.println( Yaw);
    }
    else if(2000 <= data && data < 3000){
      Roll = map(data, 2000, 3000, 0, 255);
      Serial.print("Roll: ");
      Serial.println(Roll);
    }
    else if(3000 <= data && data < 4000){
      Pitch = map(data, 3000, 4000, 0, 255);
      Serial.print("Pitch: ");
      Serial.println(Pitch);
    }
  }
  // servo1.write(Throttle);
  // servo2.write(Yaw);
  // servo3.write(Roll);
  // servo4.write(Pitch);
  // Serial.println(Throttle);
  analogWrite(ThrottlePin, Throttle);
  analogWrite(YawPin, Yaw);
  analogWrite(RollPin, Roll);
  analogWrite(PitchPin, Pitch);
}
