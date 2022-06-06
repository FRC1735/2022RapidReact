// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.SparkMaxLimitSwitch.Type;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  private CANSparkMax motor;

  private final double SPEED = 1;

  private SparkMaxLimitSwitch reverseLimitSwitch;
  private SparkMaxLimitSwitch forwardLimitSwitch;

  /** Creates a new Climber. */
  public Climber() {
      motor = new CANSparkMax(11, CANSparkMaxLowLevel.MotorType.kBrushless);
      motor.setIdleMode(IdleMode.kBrake);
    } // climber is 11
  
    @Override
    public void periodic() {
      forwardLimitSwitch = motor.getForwardLimitSwitch(Type.kNormallyOpen);
      reverseLimitSwitch = motor.getReverseLimitSwitch(Type.kNormallyOpen);
      boolean reversePressed = reverseLimitSwitch.isPressed();
      boolean forwardPressed = forwardLimitSwitch.isPressed();
      SmartDashboard.putBoolean("forwardLimit", forwardPressed);
      SmartDashboard.putBoolean("reverseLimit", reversePressed);

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
