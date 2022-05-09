// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Lighting extends SubsystemBase {
  private AddressableLED led;
  private AddressableLEDBuffer buffer;
  private int LED_COUNT = 60;

  private String lockHolder = "";

  /** Creates a new Lighting. */
  public Lighting() {
    led = new AddressableLED(0);
    buffer = new AddressableLEDBuffer(LED_COUNT);
    led.setLength(buffer.getLength());

    led.setData(buffer);
    led.start();

    setColor(0, 255, 0);
  }

  @Override
  public void periodic() {
    double matchTime = DriverStation.getMatchTime();

    if (!DriverStation.isAutonomous() && (matchTime < 10) && (matchTime > 7.9)) {
      setColor(255, 0, 0);
    }
  }

  public void on() {
    led.start();
  }

  public void off() {
    setColor(0, 0, 0);
    led.stop();
  }

  public void lock(final String lockHolder) {
    this.lockHolder = lockHolder;
  }

  public void unlock() {
    this.lockHolder  = "";
  }

  public void setColor(int r, int g, int b, String caller) {

    if (caller != null && caller.equals(lockHolder)) {

      for (int i = 0; i < buffer.getLength(); i++) {
        buffer.setRGB(i, r, g, b);
      }
      led.setData(buffer);
    }
  }

  public void setColor(int r, int g, int b) {
    setColor(r, g, b, "");
  }

}
