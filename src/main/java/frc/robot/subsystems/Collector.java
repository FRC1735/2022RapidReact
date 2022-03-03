// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Collector extends SubsystemBase {
  // TODO - rename to match function
  private WPI_VictorSPX motorA;
  private WPI_VictorSPX motorB;

  /** Creates a new Collector. */
  public Collector() {
    // TODO - assign real device IDs

    motorA = new WPI_VictorSPX(13);
    motorB = new WPI_VictorSPX(14);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void deploy() {
    // TODO 
    motorA.set(0.2);
  }

  public void withdraw() {
    // TODO
    motorA.stopMotor();
  }

  public void in() {
    // TODO
    motorB.set(0.2);
  }

  public void out() {
    // TODO
    motorB.set(-0.2);
  }

  public void stopMove() {
    motorB.stopMotor();
  }
}
