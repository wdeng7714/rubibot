#include <Servo.h>
Servo arm, seat;
int INIT_SEAT = 36;
int armPos = 0, seatPos = 0;
int ARM_ANGLE = 62, SEAT_ANGLE = 130, ROTATE_ANGLE = 35, OVER_ANGLE = 20, CORRECTION_ANGLE = 5;
double ARM_SPEED = 2, SEAT_SPEED = 2;
int DELAY = 15;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  seat.attach(9);
  arm.attach(10);
  armPos = 0;
  seatPos = 0;
  arm.write(armPos);
  seat.write(seatPos);
}

void parse(char action, bool inverted) {
  switch(action) {
    case 'L':
      if (inverted) {
        leftInverted();
      } else {
        left();
      }
      break;
    case 'R':
      if (inverted) {
        rightInverted();
      } else {
        right();
      }
      break;
    case 'U':
      if (inverted) {
        upInverted();
      } else {
        up();
      }
      break;
    case 'D':
      if (inverted) {
        downInverted();
      } else {
        down();
      }
      break;
    case 'F':
      if (inverted) {
        frontInverted();
      } else {
        front();
      }
      break;
    case 'B':
      if (inverted) {
        backInverted();
      } else {
        back();
      }
      break;
    default:
      Serial.println("Action does not exist");
  }
}
void readInput() {
  if (Serial.available()) {
    delay(DELAY);
    char data[256];
    int  n = 0;
    while (Serial.available() > 0) {
      data[n] = Serial.read();
      Serial.print(data[n]);
      n++;
    }
    Serial.print("\n");

    Serial.println(n);
    for (int i = 0; i <= n - 1; i = i + 3) {
      int times = (data[i + 1] - '0');
      if (times == 3) {
        times = 1;
      }
      for (int j = 1; j <= times; ++j) {
        parse(data[i], data[i + 1] == '3');
      }
    }
  }
}

void loop() {
  readInput();
  
}
