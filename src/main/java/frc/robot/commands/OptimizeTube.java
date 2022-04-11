/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.List;
import java.util.logging.Logger;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.sensors.DistanceSensor;
import frc.robot.sensors.DistanceSensorGroup;
import frc.robot.subsystems.Lighting;
import frc.robot.subsystems.Tube;

public class OptimizeTube extends CommandBase {
  Logger logger = Logger.getGlobal();

  private Tube tube;
  private Lighting lighting;

  /**
   * Creates a new OptimizeTube.
   */
  public OptimizeTube(Tube thatTube, Lighting thatLighting) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(thatTube);
    this.tube = thatTube;

    addRequirements(thatLighting);
    this.lighting = thatLighting;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    DistanceSensorGroup sensors = tube.getDistanceSensorGroup();
    List<Double> distances = sensors.getDistances();

    // TODO -  remove???
    /*
    for (int i = 0; i < distances.size(); i++) {
      SmartDashboard.putNumber("Distance Sensor " + i, distances.get(i));
    }
    */

    // verify that the rear sensor is in analog 1
    if (sensors.isBallDetected(1)) {
      tube.stop();

      Alliance alliance = DriverStation.getAlliance();
      if (alliance != null && alliance.equals(Alliance.Blue)) {
        lighting.setColor(0, 0, 255);
      } else {
        lighting.setColor(255, 0, 0);
      }

      return;
    }

    // front
    else if (sensors.isBallDetected(0)) {
      tube.inOptimize();
    }
    else {
      tube.stop();
    }

    lighting.setColor(0, 255, 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    tube.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}