// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CollectorDeployer;

public class DeployCollectorWithJoystick extends CommandBase {
  private final Joystick joystick;
  private final CollectorDeployer collectorDeployer;

  /** Creates a new DeployCollectorWIthJoystick. */
  public DeployCollectorWithJoystick(Joystick joystick, CollectorDeployer collectorDeployer) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(collectorDeployer);

    this.joystick = joystick;
    this.collectorDeployer = collectorDeployer;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double y = joystick.getRawAxis(1);
    //System.out.println("y: " + y);

    if (y > 0.05) {
      // backward
      collectorDeployer.up();
    } else if (y < -0.05) {
      // forwards
      collectorDeployer.down();
    } else {
      collectorDeployer.stopDeploy();
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
