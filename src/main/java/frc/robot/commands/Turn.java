// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Driveline;

public class Turn extends CommandBase {
  private Driveline driveLine;
  private double amountTurn;

  /** Creates a new Turn. */
  public Turn(int amountTurn, Driveline driveLine) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveLine);
    this.driveLine = driveLine;
    this.amountTurn = amountTurn;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveLine.zeroYaw();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveLine.setSpeed(0.2, -0.2);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    System.out.println("Yaw" + driveLine.getYaw() + "Amout Turm" + amountTurn);
    if (Math.abs(driveLine.getYaw()) >= amountTurn) {
      return true;
    }
    else {
      return false;
    }
  }
}
