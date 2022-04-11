// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.sensors.DistanceSensorGroup;

public class Tube extends SubsystemBase {
  private WPI_VictorSPX motorA;
  private WPI_TalonSRX motorB; // TODO - correct type?
  private DistanceSensorGroup distanceSensorGroup;

  private double SPEED = 1;
  private double OPTIMIZE_SPEED = 0.6;

  /** Creates a new Tube. */
  public Tube() {
    motorA = new WPI_VictorSPX(8);
    motorB = new WPI_TalonSRX(9);
    distanceSensorGroup = new DistanceSensorGroup(0, 1);

    motorA.setNeutralMode(NeutralMode.Brake);
    motorB.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void in() {
    motorA.set(ControlMode.PercentOutput, SPEED);
    motorB.set(ControlMode.PercentOutput, SPEED);
  }

  public void out() {
    motorA.set(ControlMode.PercentOutput, -SPEED);
    motorB.set(ControlMode.PercentOutput, -SPEED);
  }

  public void inOptimize() {
    motorA.set(ControlMode.PercentOutput, OPTIMIZE_SPEED);
    motorB.set(ControlMode.PercentOutput, OPTIMIZE_SPEED);
  }

  public void outOptimize() {
    motorA.set(ControlMode.PercentOutput, -OPTIMIZE_SPEED);
    motorB.set(ControlMode.PercentOutput, -OPTIMIZE_SPEED);
  }

  public void stop() {
    motorA.stopMotor();
    motorB.stopMotor();
  }

  public DistanceSensorGroup getDistanceSensorGroup() {
    return distanceSensorGroup;
  }
}
