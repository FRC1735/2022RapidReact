  // Copyright (c) FIRST and other WPILib contributors.
  // Open Source Software; you can modify and/or share it under the terms of
  // the WPILib BSD license file in the root directory of this project.

  package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.Collect;
import frc.robot.commands.CollectWithTrigger;
import frc.robot.commands.DeployCollector;
import frc.robot.commands.DeployCollectorWithJoystick;
import frc.robot.commands.DriveDistance;
import frc.robot.commands.DriveWithJoystick;
import frc.robot.commands.OptimizeTube;
import frc.robot.commands.Shoot;
import frc.robot.commands.TubeIn;
import frc.robot.commands.TurnToAngle;
import frc.robot.joysticks.XBoxJoystick;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.CollectorDeployer;
import frc.robot.subsystems.Driveline;
import frc.robot.subsystems.Lighting;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Tube;
import frc.robot.util.Log;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

  /**
   * This class is where the bulk of the robot should be declared. Since Command-based is a
   * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
   * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
   * subsystems, commands, and button mappings) should be declared here.
   */
  public class RobotContainer {
    // Misc
    // Switch this to false to turn off logging
    // TODO - hardcode isCompition check to turn this off automatically
    private final Log logger = new Log(true);

    // Joysticks
    Joystick xboxController = new Joystick(0);
    Joystick attack3Controller = new Joystick(1);

    // Subsystems
    private final Tube tube = new Tube();
    private final Collector collector = new Collector();
    private final CollectorDeployer collectorDeployer = new CollectorDeployer();
    private final Driveline driveLine = new Driveline(logger);
    private final Shooter shooter= new Shooter();
    private final Climber climber = new Climber();
    private final Lighting lighting = new Lighting();

    // Commands
    private final DriveWithJoystick driveWithJoystickCommand = new DriveWithJoystick(logger, xboxController, driveLine);
    private final DeployCollectorWithJoystick deployCollectorWithJoystickCommand = new DeployCollectorWithJoystick(attack3Controller, collectorDeployer);
    private final CollectWithTrigger collectWithTrigger = new CollectWithTrigger(xboxController, collector);
    private final OptimizeTube optimizeTube = new OptimizeTube(tube);

    // Auto commands
    
    // TODO - this command has not been tested!
    private final Command shootBallThenBackUp = new SequentialCommandGroup(
      new DeployCollector(collectorDeployer).withTimeout(1.5),
      new ParallelCommandGroup(
      new Shoot(shooter).withTimeout(4),
      new SequentialCommandGroup(
        new WaitCommand(1),
        new TubeIn(tube)
      ).withTimeout(3)
    )//,
    //new DriveDistance(driveLine, logger, -120)*/
    );

    // TODO - this command  has not been tested!
    private final Command drivePickUpBallShoot = new SequentialCommandGroup(
      new DeployCollector(collectorDeployer),
      new ParallelCommandGroup(
        // the ball should get picked up while this is running
        new Collect(collector),
        new DriveDistance(driveLine, logger, 42)
      ).withTimeout(5), // TODO - timeout
      new TurnToAngle(driveLine, logger, 180),
      new ParallelCommandGroup(
      new Shoot(shooter),
      new SequentialCommandGroup(
        new WaitCommand(1),
        new TubeIn(tube)
      ).withTimeout(3)
    )
    );

    private final Command driveTenFeetForwards = new SequentialCommandGroup(
      new DriveDistance(driveLine, logger, 120)
    );
    private final Command driveTenFeetBackwards = new SequentialCommandGroup(
      new DriveDistance(driveLine, logger, -120)
    );
    private final Command doNothing = new WaitCommand(1);
    SendableChooser<Command> autoChooser = new SendableChooser<>();


    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {

      // https://docs.wpilib.org/en/stable/docs/software/vision-processing/roborio/using-the-cameraserver-on-the-roborio.html
      CameraServer.startAutomaticCapture();

      configureButtonBindings();

      autoChooser.setDefaultOption("Do Nothing", doNothing);
      autoChooser.addOption("Drive 10 Feet Forwards", driveTenFeetForwards);
      autoChooser.addOption("Drive 10 Feet Backwards", driveTenFeetBackwards);
      autoChooser.addOption("Shoot Ball Then Back Up", shootBallThenBackUp);
      autoChooser.addOption("Drive, Pick Up Ball, Shoot", drivePickUpBallShoot);
      SmartDashboard.putData(autoChooser);

      SmartDashboard.putNumber("Turn P", 0.005);
      SmartDashboard.putNumber("Turn I", 0);
      SmartDashboard.putNumber("Turn D", 0);

      driveLine.setDefaultCommand(driveWithJoystickCommand);
      collectorDeployer.setDefaultCommand(deployCollectorWithJoystickCommand);
      collector.setDefaultCommand(collectWithTrigger);
      tube.setDefaultCommand(optimizeTube);
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
      configureXBoxController(); // driver
      configureAttack3(); // operator

    }

    private void configureXBoxController() {      
      // Collector - out
      new JoystickButton(xboxController, XBoxJoystick.RB)
        .whenPressed(new InstantCommand(collector::out, collector))
        .whenReleased(new InstantCommand(collector::stopCollect, collector));

      // Tube - in
      new JoystickButton(xboxController, XBoxJoystick.X)
        .whenPressed(new InstantCommand(tube::in, tube))
        .whenReleased(new InstantCommand(tube::stop, tube));

      // Tube - out
      new JoystickButton(xboxController, XBoxJoystick.Y)
        .whenPressed(new InstantCommand(tube::out, tube))
        .whenReleased(new InstantCommand(tube::stop, tube));

      // Shooter - on
      new JoystickButton(xboxController, XBoxJoystick.A)
        .whenPressed(shooter::setVelocity, shooter)
        .whenReleased(new InstantCommand(shooter::stop, shooter));

      // Shooter - unshoot
      new JoystickButton(xboxController, XBoxJoystick.B)
        .whenPressed(new InstantCommand(shooter::unShoot, shooter))
        .whenReleased(new InstantCommand(shooter::stop, shooter));



      /*




      // Collector - in
      new JoystickButton(xboxController, XBoxJoystick.Y)
      .whenPressed(new InstantCommand(collector::in, collector))
      .whenReleased(new InstantCommand(collector::stopCollect, collector));



      // Collector - up
      new JoystickButton(xboxController, XBoxJoystick.START)
      .whenPressed(new InstantCommand(collector::up, collector))
      .whenReleased(new InstantCommand(collector::stopDeploy, collector));

      // Collector - down
      new JoystickButton(xboxController, XBoxJoystick.BACK)
      .whenPressed(new InstantCommand(collector::down, collector))
      .whenReleased(new InstantCommand(collector::stopDeploy, collector));

      /*





      */
      
      // For testing auto commands
      /*
      new JoystickButton(xboxController, XBoxJoystick.A).whenReleased(new DriveDistance(driveLine, logger, 72), true);

      new JoystickButton(xboxController, XBoxJoystick.B)
      .whenReleased(new DriveDistance(driveLine, logger, 24), true);

      new JoystickButton(xboxController, XBoxJoystick.X)
      .whenReleased(new TurnToAngle(driveLine, logger, 180), true);

      new JoystickButton(xboxController, XBoxJoystick.Y)
      .whenReleased(
        
      new SequentialCommandGroup(
        new DriveDistance(driveLine, logger, 120),
        new TurnToAngle(driveLine, logger, 180)
      )
      
      , true);
      */
    }

    private void configureAttack3() {
      
      // TODO - use Joystick to make collector go up and down

      // Climb - down, this make the robot climb up
      new JoystickButton(attack3Controller, 11)
        .whenPressed(new InstantCommand(climber::down, climber))
        .whenReleased(new InstantCommand(climber::stop, climber));

      // Climb - up, this makes the robot climb down
      new JoystickButton(attack3Controller, 10)
        .whenPressed(new InstantCommand(climber::up, climber))
        .whenReleased(new InstantCommand(climber::stop, climber));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
      return autoChooser.getSelected();
    }
  }
