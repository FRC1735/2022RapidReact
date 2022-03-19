// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CollectorDeployer extends SubsystemBase {
  private WPI_TalonSRX deployController;

  private double DEPLOY_SPEED = 0.5;

  /** Creates a new CollectorDeployer. */
  public CollectorDeployer() {
    deployController = new WPI_TalonSRX(10);

    deployController.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public boolean isDeployed() {
    return deployController.isFwdLimitSwitchClosed() == 1;
  }
  
  public void up() {
    deployController.set(-DEPLOY_SPEED);
  }

  public void down() {
    deployController.set(DEPLOY_SPEED);
  }

  public void stopDeploy() {
    deployController.stopMotor();
  }

}
