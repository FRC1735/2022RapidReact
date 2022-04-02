// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  private CANSparkMax motor;
  private RelativeEncoder encoder;
  private SparkMaxPIDController pidController;
  private double targetVelocity = 0; 
  private int rollingAvg = 0;
  private String SHOOTER_SPEED_KEY = "SHOOTER_SPEED";

  private int LOW_SHOOTER_SPEED = 1800;
  private int HIGH_SHOOTER_SPEED = 3750;

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

    // TODO - keep 3750 for high goal speed

    SmartDashboard.putNumber("SHOOTER_SPEED", 3750);
  }

  public void setVelocity() {
    //3500 - 4000 seems to be the ideal for shooting
    targetVelocity = SmartDashboard.getNumber(SHOOTER_SPEED_KEY, 3750);
    pidController.setReference(targetVelocity, ControlType.kVelocity);
  }

  public void shootLow() {
    pidController.setReference(LOW_SHOOTER_SPEED, ControlType.kVelocity);
  }

  public void shootHigh() {
    pidController.setReference(HIGH_SHOOTER_SPEED, ControlType.kVelocity);
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
