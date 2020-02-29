/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;
import frc.robot.Constants.DashboardConstants;
import frc.robot.subsystems.Turret;
import frc.util.LimelightCamera;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class SetShooterAltitudeProfiledPid extends ProfiledPIDCommand {
  /**
   * Creates a new SdetShooterAltitudeProfiledPid.
   */
  public SetShooterAltitudeProfiledPid(Turret turret) {
    super(
        // The ProfiledPIDController used by the command
        new ProfiledPIDController(
            // The PID gains
            3, .1, 0,
            // The motion profile constraints
            new TrapezoidProfile.Constraints(1, 1)),
        // This should return the measurement
        () -> LimelightCamera.getInstance().getTargetYPosition(),
        // This should return the goal (can also be a constant)
        () -> new TrapezoidProfile.State(0, 0),
        // This uses the output
        (output, setpoint) -> {
          // Use the output (and setpoint, if desired) here
		  SmartDashboard.putNumber(DashboardConstants.hoodMotorKey, output);
		});
    // Use addRequirements() here to declare subsystem dependencies.
	// Configure additional PID options by calling `getController` here.
	getController().setTolerance(1.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
