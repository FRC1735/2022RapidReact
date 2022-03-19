// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Driveline;
import frc.robot.util.Log;

public class DriveDistance extends CommandBase {
  private Driveline driveLine;
  private Log logger;
  private double distanceTicks;

  /** Creates a new DriveDistance. */
  public DriveDistance(Driveline driveLine, Log logger, int distanceInches) {
    addRequirements(driveLine);
    this.driveLine = driveLine;
    this.distanceTicks = inchesToEncoderTicks(distanceInches);
    this.logger = logger;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveLine.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (distanceTicks > 0) {
      driveLine.setSpeed(-0.2, 0.2);
    } else {
      driveLine.setSpeed(0.2, -0.2);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    logger.log("DriveDistance.end()");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    SmartDashboard.putNumber("Encoder position", driveLine.getEncoderPosition());
    logger.log("DriveDistance.isFinished(): encoder position: " + driveLine.getEncoderPosition());
    if (Math.abs(driveLine.getEncoderPosition()) > Math.abs(distanceTicks)) {
      logger.log("DriveDistance.isFinished(): stopping");
      driveLine.stop();
      driveLine.resetEncoders();
      return true;
    } else {
      return false;
    }
  }

  private double inchesToEncoderTicks(double inches) {
    return (inches / 12) * 7.1;
  }
}
