// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.trajectory.constraint.DifferentialDriveKinematicsConstraint;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.Log;

public class Driveline extends SubsystemBase {
  private CANSparkMax leftMotor;
  private CANSparkMax rightMotor;
  private DifferentialDrive differentialDrive;
  private CANSparkMax followLeftMotor;
  private CANSparkMax followRightMotor;
  private RelativeEncoder rightEncoder;
  private RelativeEncoder leftEncoder;
  public final ADXRS450_Gyro gyro;
  private Log logger;

  // TODO - set motors to coast

  /** Creates a new Driveline. */
  public Driveline(Log logger) {
    this.logger = logger;

    leftMotor = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless); // og - 2, testing with 3 cuz 2 wasn't working
    leftEncoder = leftMotor.getEncoder(); //.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 4096);
    
    rightMotor = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless); // og - 4, testing with 5 because 4 wasn't working
    rightEncoder = rightMotor.getEncoder(); //.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 4096);
    gyro = new ADXRS450_Gyro();

    followRightMotor = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless);
    followLeftMotor = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);
    followLeftMotor.follow(leftMotor);
    followRightMotor.follow(rightMotor);

    differentialDrive = new DifferentialDrive(leftMotor, rightMotor);

    differentialDrive.setSafetyEnabled(true);
    differentialDrive.setExpiration(0.1);
    differentialDrive.setMaxOutput(1.0);
  }

  public void setDriveCoast() {
    leftMotor.setIdleMode(IdleMode.kCoast);
    rightMotor.setIdleMode(IdleMode.kCoast);
    followLeftMotor.setIdleMode(IdleMode.kCoast);
    followRightMotor.setIdleMode(IdleMode.kCoast);

    double r = 0.675;
    leftMotor.setOpenLoopRampRate(r);
    rightMotor.setOpenLoopRampRate(r);
    followLeftMotor.setOpenLoopRampRate(r);
    followRightMotor.setOpenLoopRampRate(r);
  }

  public void setAutoCoast() {
    leftMotor.setIdleMode(IdleMode.kCoast);
    rightMotor.setIdleMode(IdleMode.kCoast);
    followLeftMotor.setIdleMode(IdleMode.kCoast);
    followRightMotor.setIdleMode(IdleMode.kCoast);

    double r = 0;
    leftMotor.setOpenLoopRampRate(r);
    rightMotor.setOpenLoopRampRate(r);
    followLeftMotor.setOpenLoopRampRate(r);
    followRightMotor.setOpenLoopRampRate(r);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // Left Encoder Position: -7.666683197021484 Right Encoder Value: -7.523824214935303
    // 1 foot Left Encoder Position: -0.4999997913837433 Right Encoder Value: -0.4999997913837433
    // 2 foot Left Encoder Position: 6.833340167999268 Right Encoder Value: 6.880959987640381

    // TODO - rm
    SmartDashboard.putNumber("GYRO", getYaw());
  }

   public void set(double leftValue, double rightValue) {
    logger.log("Driveline set(leftValue: " + leftValue + ", rightValue: " + rightValue);
    leftMotor.set(leftValue);
    rightMotor.set(rightValue);
  }

  public void resetEncoders() {
    leftEncoder.setPosition(0);
    rightEncoder.setPosition(0);
  }

  public double getEncoderPosition() {
    double leftPosition = leftEncoder.getPosition();
    double rightPosition = rightEncoder.getPosition();
    return (Math.abs(leftPosition) + Math.abs(rightPosition)) / 2;
    //return leftPosition;
  }

   public void zeroYaw() {
     gyro.reset();
   }

   public double getYaw() {
     return gyro.getAngle();
   }
  
   public void setSpeed(double right, double left) {
     rightMotor.set(right);
     leftMotor.set(left);
   }


  public void arcadeDrive(final double joystickX, final double joystickY) {
    //differentialDrive.arcadeDrive(-joystickY, joystickX, true);
    differentialDrive.arcadeDrive(joystickX, -joystickY, true);
  }

  public void tankDrive(final double joystickAY, final double joystickBY) {
    differentialDrive.tankDrive(joystickAY, joystickBY);
  }

  public void stop() {
    differentialDrive.stopMotor();
  }
}