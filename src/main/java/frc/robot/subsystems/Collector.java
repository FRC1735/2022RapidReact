// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Collector extends SubsystemBase {
  // TODO - rename to match function
  private WPI_VictorSPX collectController;
  private WPI_VictorSPX deployController;

  /** Creates a new Collector. */
  public Collector() {
    // TODO - assign real device IDs

    collectController = new WPI_VictorSPX(7);
    deployController = new WPI_VictorSPX(14);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void deploy() {
    // TODO - direction probably wrong be very careful when testing pre limit switch
    deployController.set(0.1);
  }

  public void withdraw() {
    // TODO - direction probably wrong be very careful when testing pre limit switch
    deployController.set(-0.1);
  }

  public void stopDeploy() {
    deployController.stopMotor();
  }

  public void in() {
    // TODO
    collectController.set(-0.2);
  }

  public void out() {
    // TODO
    collectController.set(0.2);
  }

  public void stopCollect() {
    collectController.stopMotor();
  }
}
