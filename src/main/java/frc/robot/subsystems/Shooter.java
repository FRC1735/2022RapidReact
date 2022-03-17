// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  private CANSparkMax motor;
  private RelativeEncoder encoder;
  private SparkMaxPIDController pidController;
  private double targetVelocity = 0; 
  private int rollingAvg = 0;

  /** Creates a new Shooter. */
  public Shooter() {
    motor = new CANSparkMax(6, CANSparkMaxLowLevel.MotorType.kBrushless);
    encoder = motor.getEncoder();
    pidController = motor.getPIDController();

    pidController.setP(0.0002);
    pidController.setI(0.0);
    pidController.setD(0.0);
    pidController.setIZone(0.0);
    pidController.setFF(0.000175);
    pidController.setOutputRange(0, 1);

    motor.burnFlash();
  }

  public void setVelocity() {
    targetVelocity = 6000;
    pidController.setReference(targetVelocity, ControlType.kVelocity);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void shoot() {
    motor.set(0.2);
  }

  public void unShoot() {
    motor.set(-0.2);
  }

  public void stop() {
    motor.stopMotor();
  }
}
