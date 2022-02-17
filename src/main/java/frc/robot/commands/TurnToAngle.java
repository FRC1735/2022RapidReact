/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Driveline;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class TurnToAngle extends PIDCommand {
  private int atSetpoint = 0;
  private Driveline driveLine;
  
   public TurnToAngle(final Driveline driveLine, final int angle) {
      this(driveLine, angle, SmartDashboard.getNumber("Turn P", 0.2), SmartDashboard.getNumber("Turn I", 0.1), SmartDashboard.getNumber("Turn D", 0.5));
    }   


  public TurnToAngle(Driveline driveLine, int angle, double p, double i, double d) {
    super(new PIDController(p, i, d),
        // This should return the measurement
        driveLine::getYaw,
        // This should return the setpoint (can also be a constant)
        angle,
        // This uses the output
        output -> {
          double p2 = SmartDashboard.getNumber("Turn P", 0.2);
          double i2 = SmartDashboard.getNumber("Turn I", 0.1);
          double d2 = SmartDashboard.getNumber("Turn D", 0.5);
          System.out.println("P: " + p2 + " I: " + i2 + " D: " + d2);
          System.out.println("output: " + output);
          double clampedOutput = MathUtil.clamp(output, -.1, .1);
          driveLine.set(clampedOutput, -clampedOutput);
        });


    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveLine);

    // Configure additional PID options by calling `getController` here.
    getController().enableContinuousInput(-180, 180);
    getController().setTolerance(2);

    this.driveLine = driveLine;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveLine.zeroYaw();

    double p = SmartDashboard.getNumber("Turn P", 0.2);
    double i = SmartDashboard.getNumber("Turn I", 0.1);
    double d = SmartDashboard.getNumber("Turn D", 0.5);

    getController().setP(p);
    getController().setI(i);
    getController().setD(d);
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (getController().atSetpoint()) {
      atSetpoint++;
      if (atSetpoint > 4) {
        System.out.println("TurnToAngle.isFinished true");
        return true;
      }
      return false;
    } else {
      atSetpoint = 0;
      return false;
    }
  }
}