// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  private CANSparkMax motor;
  /** Creates a new Shooter. */
  public Shooter() {
    // TODO - assign real device IDs

    // TODO - '4' temporarily assigned for shooter test
    motor = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);
    motor.setIdleMode(IdleMode.kCoast);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  private void shoot(double speed) {
    motor.set(speed);
  }

  public void shoot50() {
    shoot(.5);
  }

  public void shoot75() {
    shoot(.75);
  }

  public void shoot100() {
    shoot(1);
  }

  public void stop() {
    motor.stopMotor();
  }
}
