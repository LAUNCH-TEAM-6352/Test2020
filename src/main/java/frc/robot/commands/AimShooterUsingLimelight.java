/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DashboardConstants;
import frc.robot.Constants.TargetingConstants;
import frc.robot.subsystems.Shooter;
import frc.util.LimelightCamera;

public class AimShooterUsingLimelight extends CommandBase
{
	private Shooter shooter;
	private LimelightCamera limelight;

	private double altitudeSlope;
	private double altitudeYIntercept;
	private double altitudeMaxSpeed;
	private double altitudeMinSpeed;

	private double azimuthSlope;
	private double azimuthYIntercept;
	private double azimuthMaxSpeed;
	private double azimuthMinSpeed;

	/**
	 * Creates a new AimShooterUsingLimelight.
	 */
	public AimShooterUsingLimelight(Shooter shooter)
	{
		this.shooter = shooter;
		addRequirements(shooter);
		limelight = LimelightCamera.getInstance();
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize()
	{
		altitudeSlope =
			(TargetingConstants.minAltitudeSpeed - TargetingConstants.maxAltitudeSpeed) /
			(TargetingConstants.minAltitudeDelta - TargetingConstants.maxAltitudeDelta);
		altitudeYIntercept = TargetingConstants.maxAltitudeSpeed - (altitudeSlope * TargetingConstants.maxAltitudeDelta);
		altitudeMaxSpeed = TargetingConstants.maxAltitudeSpeed;
		altitudeMinSpeed = TargetingConstants.minAltitudeSpeed;

		azimuthSlope =
			(TargetingConstants.minAzimuthSpeed - TargetingConstants.maxAzimuthSpeed) /
			(TargetingConstants.minAzimuthDelta - TargetingConstants.maxAzimuthDelta);

		azimuthYIntercept = TargetingConstants.maxAzimuthSpeed - (azimuthSlope * TargetingConstants.maxAzimuthDelta);
		azimuthMaxSpeed = TargetingConstants.maxAzimuthSpeed;
		azimuthMinSpeed = TargetingConstants.minAzimuthSpeed;
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute()
	{
		double altitudeValue = getAltitudeValue(limelight.getTargetYPosition());
		SmartDashboard.putNumber(DashboardConstants.altitudeMotorKey, altitudeValue);
		//shooter.setAltitude(altitudeValue);

		double azimuthValue = getAzimuthValue(limelight.getTargetXPosition());
		SmartDashboard.putNumber(DashboardConstants.azimuthMotorKey, azimuthValue);
		//shooter.setAzimuth(azimuthValue);
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted)
	{
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished()
	{
		return !limelight.isTargetAcquired() ||
			(Math.abs(limelight.getTargetXPosition()) < TargetingConstants.azimuthTolerance &&
			 Math.abs(limelight.getTargetYPosition()) < TargetingConstants.altitudeTolerance);
	}

	private double getAltitudeValue(double input)
	{
		double output = input * altitudeSlope + altitudeYIntercept;
		double sign = Math.signum(output);

		if (Math.abs(output) > altitudeMaxSpeed)
		{
			output = altitudeMaxSpeed * sign;
		}
		else if (Math.abs(output) < altitudeMinSpeed)
		{
			output = 0;
		}

		return output;
	}

	private double getAzimuthValue(double input)
	{
		double output = input * azimuthSlope + azimuthYIntercept;
		double sign = Math.signum(output);

		if (Math.abs(output) > azimuthMaxSpeed)
		{
			output = azimuthMaxSpeed * sign;
		}
		else if (Math.abs(output) < azimuthMinSpeed)
		{
			output = 0;
		}

		return output;
	}
}
