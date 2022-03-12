// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Collector extends SubsystemBase {
  private WPI_VictorSPX collectController;
  private WPI_TalonSRX deployController;

  private double COLLECT_SPEED = 1;
  private double DEPLOY_SPEED = 0.5;

  /** Creates a new Collector. */
  public Collector() {
    collectController = new WPI_VictorSPX(7);
    deployController = new WPI_TalonSRX(10);

    deployController.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    boolean fwdLimitClosed = deployController.isFwdLimitSwitchClosed() == 1;
    boolean revLimitClosed = deployController.isRevLimitSwitchClosed() == 1;

    //System.out.println("JTA - revLimitClosed: " + revLimitClosed + ", fwdLimitClosed: " + fwdLimitClosed);
  }

  public void down() {
    deployController.set(-DEPLOY_SPEED);
  }

  public void up() {
    deployController.set(DEPLOY_SPEED);
  }

  public void stopDeploy() {
    deployController.stopMotor();
  }

  public void in() {
    collectController.set(-COLLECT_SPEED);
  }

  public void out() {
    collectController.set(COLLECT_SPEED);
  }

  public void stopCollect() {
    collectController.stopMotor();
  }
}
