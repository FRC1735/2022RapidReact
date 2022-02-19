// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Tube extends SubsystemBase {
  private WPI_VictorSPX motorA;
  private WPI_VictorSPX motorB; 

  /** Creates a new Tube. */
  public Tube() {
    // TODO - assign real device IDs
    motorA = new WPI_VictorSPX(11);
    motorB = new WPI_VictorSPX(12);

    // TODO - ?
    // motorA.follow(motorB);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}