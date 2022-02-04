// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.math.trajectory.constraint.DifferentialDriveKinematicsConstraint;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Driveline extends SubsystemBase {
  private CANSparkMax leftMotor;
  private CANSparkMax rightMotor;
  private DifferentialDrive differentialDrive;
  private CANSparkMax followLeftMotor;
  private CANSparkMax followRightMotor;
  /** Creates a new Driveline. */
  public Driveline() {
    leftMotor = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
    rightMotor = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless);
    followLeftMotor = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);
    followRightMotor = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);

    followLeftMotor.follow(leftMotor);
    followRightMotor.follow(rightMotor);

    differentialDrive = new DifferentialDrive(leftMotor, rightMotor);
    differentialDrive.setSafetyEnabled(true);
    differentialDrive.setExpiration(0.1);
    differentialDrive.setMaxOutput(1.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void arcadeDrive(final double joystickX, final double joystickY) {
    differentialDrive.arcadeDrive(-joystickY, joystickX, true);
  }

  public void tankDrive(final double joystickAY, final double joystickBY) {
    differentialDrive.tankDrive(joystickAY, joystickBY);
  }
}
