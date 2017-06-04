#include <Servo.h>

Servo arm, seat;
int INIT_SEAT = 36;
int armPos = 0, seatPos = 0;
int ARM_ANGLE = 62, SEAT_ANGLE = 130, ROTATE_ANGLE = 35, OVER_ANGLE = 20, CORRECTION_ANGLE = 5
;
double ARM_SPEED = 2, SEAT_SPEED = 2;
int DELAY = 15;
void setup() {
  // put your setup code here, to run once:
  seat.attach(9);
  arm.attach(10);
  armPos = 0;
  seatPos = 0;
  arm.write(armPos);
  seat.write(seatPos);
}

void flip() {
  for (;armPos <= ARM_ANGLE; armPos += (armPos > ARM_ANGLE / 2) ? ARM_SPEED / 2 : ARM_SPEED) {
    arm.write(armPos);
    delay(DELAY);
  }
  for (;armPos >= 0; armPos -= ARM_SPEED / 2) {
    arm.write(armPos);
    delay(DELAY);
  }
}

void flipInverted() {
  flip();
  flip();
  flip();
}

void rotateOver() {
  for (; seatPos >= INIT_SEAT - OVER_ANGLE; seatPos -= SEAT_SPEED) {
    seat.write(seatPos);
    delay(DELAY);
  }
}
void rotateOverInverted() {
  for(; seatPos <= SEAT_ANGLE + OVER_ANGLE; seatPos += SEAT_SPEED) {
    seat.write(seatPos);
    delay(DELAY);
  }
}

void rotateBaseInverted() {
  for (; armPos <= ROTATE_ANGLE; armPos += ARM_SPEED) {
    arm.write(armPos);
    delay(DELAY);
  }
  rotateOverInverted();
  for (; armPos >= 0; armPos -= ARM_SPEED) {
    arm.write(armPos);
    delay(DELAY);
  }
  rotate();
}

void down() {
  rotateBase();
  flip();
  rotateInverted();
  flip();
  rotate();
  flip();
  flip();
  flip(); 
}

void downInverted() {
  down();
  down();
  down();
}
void rotateBase() {
  rotateInverted();  
  for (; armPos <= ROTATE_ANGLE; armPos += ARM_SPEED) {
    arm.write(armPos);
    delay(DELAY);
  }
  rotateOver();
  for (; armPos >= 0; armPos -= ARM_SPEED) {
    arm.write(armPos);
    delay(DELAY);
  }

  for (; seatPos <= INIT_SEAT + CORRECTION_ANGLE; seatPos += SEAT_SPEED) {
    seat.write(seatPos);
    delay(DELAY);
  }
}

void rotate() {
  for(; seatPos >= INIT_SEAT; seatPos -= SEAT_SPEED) {
    seat.write(seatPos);
    delay(DELAY);
  }
}
void rotateInverted() {
  for(; seatPos <= SEAT_ANGLE; seatPos += SEAT_SPEED) {
    seat.write(seatPos);
    delay(DELAY);
  }
}

void rotateCounterClockwise() {
  for (;seatPos >= 0; seatPos -= SEAT_SPEED) {
    seat.write(seatPos);
    delay(DELAY);
  }
}

void front() {
  flipInverted();
  // rotateBaseInverted();
  flip();
}
void rotateFlip() {
  rotate();
  for (;armPos <= ARM_ANGLE; armPos += (armPos > ARM_ANGLE / 2) ? ARM_SPEED / 2 : ARM_SPEED) {
    arm.write(armPos);
    delay(DELAY);
  }
  for (;armPos >= 0; armPos -= ARM_SPEED / 2) {
    arm.write(armPos);
    delay(DELAY);
  }
  for(; seatPos >= 0; seatPos -= SEAT_SPEED) {
    seat.write(seatPos);
    delay(DELAY);
  }

}
void loop() {
  rotateBase();
//  downInverted();
  delay(2000);

// rotate();
// delay(2000);
//  rotateBase();
//  delay(2000);
//  rotateFlip();
//  front();
//  delay(2000);
  
}
