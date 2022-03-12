// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Collector;

public class CollectWithTrigger extends CommandBase {
  private final Joystick joystick;
  private final Collector collector;

  /** Creates a new CollectWithTrigger. */
  public CollectWithTrigger(Joystick xboxController, Collector collector) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(collector);

    this.joystick = xboxController;
    this.collector = collector;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double x = joystick.getRawAxis(3);
    //System.out.println("x: " + x);

    if (x > 0.25) {
      collector.in();
    } else {
      collector.stopCollect();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
