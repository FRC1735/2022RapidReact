// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  private CANSparkMax motor;

  private final double SPEED = 1;

  /** Creates a new Climber. */
  public Climber() {
      motor = new CANSparkMax(11, CANSparkMaxLowLevel.MotorType.kBrushless);
      motor.setIdleMode(IdleMode.kBrake);
    } // climber is 11
  
    @Override
    public void periodic() {
      // This method will be called once per scheduler run
    }
  
    public void up() {
      // TODO - direction?
      motor.set(SPEED);
    }

    public void down() {
      // TODO - direction?
      motor.set(-SPEED);
    }
  
    public void stop() {
      motor.stopMotor();
    }
}
