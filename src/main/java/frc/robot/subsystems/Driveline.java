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

import edu.wpi.first.math.trajectory.constraint.DifferentialDriveKinematicsConstraint;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.Logger;

public class Driveline extends SubsystemBase {
  private CANSparkMax leftMotor;
  private CANSparkMax rightMotor;
  private DifferentialDrive differentialDrive;
  private CANSparkMax followLeftMotor;
  private CANSparkMax followRightMotor;
  private RelativeEncoder rightEncoder;
  private RelativeEncoder leftEncoder;
  public final AHRS gyro;
  private Logger logger;

  /** Creates a new Driveline. */
  public Driveline(Logger logger) {
    this.logger = logger;

    // 3 is good on left
    leftMotor = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless); // og - 2, testing with 3 cuz 2 wasn't working
    leftEncoder = leftMotor.getEncoder(); //.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 4096);
    
    // 5 is good on right
    // 4 is good?
    rightMotor = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless); // og - 4, testing with 5 because 4 wasn't working
    rightEncoder = rightMotor.getEncoder(); //.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 4096);
    gyro = new AHRS(I2C.Port.kMXP);

    //followLeftMotor = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless);
    //followRightMotor = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);
    //followLeftMotor.follow(leftMotor, false);
    //followRightMotor.follow(rightMotor, false);

    //rightMotor.setInverted(true);

    differentialDrive = new DifferentialDrive(leftMotor, rightMotor);

    /*
    differentialDrive.setSafetyEnabled(true);
    differentialDrive.setExpiration(0.1);
    differentialDrive.setMaxOutput(1.0);
    */
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // Left Encoder Position: -7.666683197021484 Right Encoder Value: -7.523824214935303
    // 1 foot Left Encoder Position: -0.4999997913837433 Right Encoder Value: -0.4999997913837433
    // 2 foot Left Encoder Position: 6.833340167999268 Right Encoder Value: 6.880959987640381
   }

   public void set(double leftValue, double rightValue) {
    logger.log("Driveline set(leftValue: " + leftValue + ", rightValue: " + rightValue);
    leftMotor.set(leftValue);
    rightMotor.set(rightValue);
  }


   public double getEncoderPosition() {
     double leftPosition = leftEncoder.getPosition();
     double rightPosition = rightEncoder.getPosition();

     return (leftPosition + rightPosition) / 2;
   }

   public void zeroYaw() {
     gyro.zeroYaw();
   }

   public double getYaw() {
     return (gyro.getYaw());
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
