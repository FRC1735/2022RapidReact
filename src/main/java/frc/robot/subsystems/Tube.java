// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Tube extends SubsystemBase {
  private WPI_VictorSPX motorA;

  private double SPEED = 1;

  /** Creates a new Tube. */
  public Tube() {
    motorA = new WPI_VictorSPX(8);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void in() {
    motorA.set(ControlMode.PercentOutput, SPEED);
  }

  public void out() {
    motorA.set(ControlMode.PercentOutput, -SPEED);
  }

  public void stop() {
    motorA.stopMotor();
  }
}
