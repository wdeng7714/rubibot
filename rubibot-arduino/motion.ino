void initSeat() {
  for(; seatPos >= 0; seatPos -= SEAT_SPEED) {
    seat.write(seatPos);
    delay(DELAY);
  }
  for (; seatPos <= INIT_SEAT + CORRECTION_ANGLE; seatPos += SEAT_SPEED) {
    seat.write(seatPos);
    delay(DELAY);
  }
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

void down() {
  rotateBase();
  flip();
  flip();
  flip();
  roll();
  flip();
}

void downInverted() {
  rotateBaseInverted();
  flip();
  roll();
  flip();
  flip();
  flip(); 
}

void front() {
  initSeat();
  flipInverted();
  rotateBaseInverted();
  flip();
  roll();
}

void frontInverted() {
  initSeat();
  flipInverted();
  rotateBase();
  flip();
  rollInverted();
}

void upInverted() {
  initSeat();
  flip();
  flip();
  rotateBase();
  roll();
  flipInverted();
  roll();
}

void up() {
  initSeat();
  flip();
  flip();
  rotateBaseInverted();
  roll();
  flip();
  roll();
}

void right() {
  roll();
  rotateBaseInverted();
  rollInverted();
  flip();
}

void rightInverted() {
  roll();
  rotateBase();
  rollInverted();
  flipInverted();
}

void left() {
  rollInverted();
  rotateBaseInverted();
  roll();
  flipInverted();
}

void leftInverted() {
  rollInverted();
  rotateBase();
  roll();
  flip();
}

void back() {
  initSeat();
  flip();
  rotateBase();
  flipInverted();
  roll();
}

void backInverted() {
  initSeat();
  flip();
  rotateBaseInverted();
  flipInverted();
  rollInverted();
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

void rotateBaseInverted() {
  for (; seatPos <= INIT_SEAT; seatPos += SEAT_SPEED) {
    seat.write(seatPos);
    delay(DELAY);
  }
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

void roll() {
  rotateInverted();
  flip();
  rotate();
}

void rollInverted() {
  rotateInverted();
  flipInverted();
  rotate();
}
