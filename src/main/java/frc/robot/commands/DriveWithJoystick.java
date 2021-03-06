// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.joysticks.XBoxJoystick;
import frc.robot.subsystems.Driveline;
import frc.robot.util.Log;

public class DriveWithJoystick extends CommandBase {

private Joystick joystick;
private Driveline driveLine;
private Log logger;
  /** Creates a new DriveWithJoystick. */
  public DriveWithJoystick(Log logger, Joystick joystick, Driveline driveLine) {
    addRequirements(driveLine);

    this.logger = logger;
    this.joystick = joystick;
    this.driveLine = driveLine;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // jta
    //System.out.println("DriveWithJoystick - execute");

    double x = joystick.getRawAxis(0);
    double y = joystick.getRawAxis(1);

    //System.out.println("x=" + x + ", y=" + y);


    //logger.log("DriveWithJoystick(x=" + x + ", y=" + y + ")");

    // TODO - deadzone

  
    boolean halfSpeed = joystick.getRawButton(XBoxJoystick.LB);

    if (halfSpeed) {
      driveLine.arcadeDrive(x / 2, y / 2);
    } else {
      driveLine.arcadeDrive(x, y);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveLine.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
