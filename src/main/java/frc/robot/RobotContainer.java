// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DriveDistance;
import frc.robot.commands.DriveWithJoystick;
import frc.robot.commands.TurnToAngle;
import frc.robot.joysticks.XBoxJoystick;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Driveline;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Tube;
import frc.robot.util.Logger;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Joysticks
  Joystick xboxController = new Joystick(0);
  
  // Subsystems
  private final Tube tube = new Tube();
  private final Collector collector = new Collector();
  //private final Driveline driveLine = new Driveline();
  private final Shooter shooter= new Shooter();

  // Commands
  //private final DriveWithJoystick driveWithJoystickCommand = new DriveWithJoystick(xboxController, driveLine);  

  // Misc
  private final Logger logger;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Switch this to false to turn off logging 
    // TODO - hardcode isCompition check to turn this off automatically
    logger = new Logger(true);

    configureButtonBindings();
    
    SmartDashboard.putNumber("Turn P", 0.005);
    SmartDashboard.putNumber("Turn I", 0);
    SmartDashboard.putNumber("Turn D", 0);

    //driveLine.setDefaultCommand(driveWithJoystickCommand);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    configureXBoxController();
  }

  private void configureXBoxController() {

    new JoystickButton(xboxController, XBoxJoystick.A)
    .whenPressed(new InstantCommand(shooter::shoot50, shooter))
    .whenReleased(new InstantCommand(shooter::stop, shooter));

    new JoystickButton(xboxController, XBoxJoystick.B)
    .whenPressed(new InstantCommand(shooter::shoot75, shooter))
    .whenReleased(new InstantCommand(shooter::stop, shooter));

    new JoystickButton(xboxController, XBoxJoystick.X)
    .whenPressed(new InstantCommand(shooter::shoot100, shooter))
    .whenReleased(new InstantCommand(shooter::stop, shooter));

    /*
    new JoystickButton(xboxController, XBoxJoystick.A).whenReleased(new DriveDistance(driveLine, logger, 72), true);

    new JoystickButton(xboxController, XBoxJoystick.B)
    .whenReleased(new DriveDistance(driveLine, logger, 24), true);

    new JoystickButton(xboxController, XBoxJoystick.X)
    .whenReleased(new TurnToAngle(driveLine, logger, 180), true);

    new JoystickButton(xboxController, XBoxJoystick.Y)
    .whenReleased(new SequentialCommandGroup(
      new DriveDistance(driveLine, logger, 120),
      new TurnToAngle(driveLine, logger, 180)
    ), true);
    */
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // TODO
    return null;
  }
}
