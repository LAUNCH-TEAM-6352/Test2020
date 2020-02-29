/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.DashboardConstants;
import frc.robot.subsystems.Turret;
import frc.util.LimelightCamera;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class SetShooterAltitudePid extends PIDCommand
{
	/**
	 * Creates a new SetShooterAltitudePid.
	 */
	public SetShooterAltitudePid(Turret turret)
	{
		super(
				// The controller that the command will use
				new PIDController(3, .001, 0),
				// This should return the measurement
				() -> LimelightCamera.getInstance().getTargetYPosition(),
				// This should return the setpoint (can also be a constant)
				() -> 0,
				// This uses the output
				output ->
				{
					// Use the output here
					SmartDashboard.putNumber(DashboardConstants.hoodMotorKey, output);
					// turret.setAltitude(output);
				});
		// Use addRequirements() here to declare subsystem dependencies.
		// Configure additional PID options by calling `getController` here.
		getController().setTolerance(0.1);
		getController().setIntegratorRange(-1000, 1000);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished()
	{
		return getController().atSetpoint();
	}
}
