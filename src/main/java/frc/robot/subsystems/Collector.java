// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Collector extends SubsystemBase {
  // TODO - rename to match function
  private WPI_VictorSPX collectController;
  private WPI_TalonSRX deployController;

  /** Creates a new Collector. */
  public Collector() {
    collectController = new WPI_VictorSPX(7);
    deployController = new WPI_TalonSRX(10);

    deployController.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void down() {
    deployController.set(-0.2);
  }

  public void up() {
    deployController.set(0.2);
  }

  public void stopDeploy() {
    deployController.stopMotor();
  }

  public void in() {
    collectController.set(-0.2);
  }

  public void out() {
    collectController.set(0.2);
  }

  public void stopCollect() {
    collectController.stopMotor();
  }
}
