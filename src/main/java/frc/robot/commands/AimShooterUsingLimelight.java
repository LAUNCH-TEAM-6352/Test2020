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
import frc.robot.Constants.LimelightConstants;
import frc.robot.Constants.TargetingConstants;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import frc.util.LimelightCamera;

public class AimShooterUsingLimelight extends CommandBase
{
	private Turret turret;
	private LimelightCamera limelight;

	private double altitudeSlope;
	private double altitudeYIntercept;
	private double altitudeMaxSpeedAbs;
	private double altitudeMinSpeedAbs;

	private double azimuthSlope;
	private double azimuthYIntercept;
	private double azimuthMaxSpeedAbs;
	private double azimuthMinSpeedAbs;

	/**
	 * Creates a new AimShooterUsingLimelight.
	 */
	public AimShooterUsingLimelight(Turret turret)
	{
		this.turret = turret;
		addRequirements(turret);
		limelight = LimelightCamera.getInstance();
	}

	/***
	 * Called before the first call to execute for a run.
	 * 
	 * We assume the camera is in the desired vision processing mode.
	 */
	@Override
	public void initialize()
	{
		// If the Limelight hasn't acquired a target, there is nothing much we can do.
		if (!limelight.isTargetAcquired())
		{
			return;
		}

		double sign = Math.signum(limelight.getTargetYPosition());
		int index = sign < 0 ? 0 : 1;
		altitudeSlope =
			(TargetingConstants.minAltitudeSpeeds[index] - TargetingConstants.maxAltitudeSpeeds[index]) /
			(TargetingConstants.minAltitudeDeltas[index] - TargetingConstants.maxAltitudeDeltas[index]);
		altitudeYIntercept = TargetingConstants.maxAltitudeSpeeds[index] - (altitudeSlope * TargetingConstants.maxAltitudeDeltas[index]);
		altitudeMaxSpeedAbs = Math.abs(TargetingConstants.maxAltitudeSpeeds[index]);
		altitudeMinSpeedAbs = Math.abs(TargetingConstants.minAltitudeSpeeds[index]);

		sign = Math.signum(limelight.getTargetXPosition());
		azimuthSlope =
			(TargetingConstants.minAzimuthSpeed * sign - TargetingConstants.maxAzimuthSpeed * sign) /
			(TargetingConstants.minAzimuthDelta * sign - TargetingConstants.maxAzimuthDelta * sign);

		azimuthYIntercept = TargetingConstants.maxAzimuthSpeed * sign - (azimuthSlope * TargetingConstants.maxAzimuthDelta * sign);
		azimuthMaxSpeedAbs = TargetingConstants.maxAzimuthSpeed;
		azimuthMinSpeedAbs = TargetingConstants.minAzimuthSpeed;
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute()
	{
		double altitudeValue = getAltitudeValue(limelight.getTargetYPosition());
		SmartDashboard.putNumber(DashboardConstants.altitudeMotorKey, altitudeValue);
		//turret.setAltitude(altitudeValue);

		double azimuthValue = getAzimuthValue(limelight.getTargetXPosition());
		SmartDashboard.putNumber(DashboardConstants.azimuthMotorKey, azimuthValue);
		//turret.setAzimuth(azimuthValue);
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted)
	{
		//turret.stop();
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

		if (Math.abs(output) > altitudeMaxSpeedAbs)
		{
			output = altitudeMaxSpeedAbs * sign;
		}
		else if (Math.abs(output) < altitudeMinSpeedAbs)
		{
			output = 0;
		}

		return output;
	}

	private double getAzimuthValue(double input)
	{
		double output = input * azimuthSlope + azimuthYIntercept;
		double sign = Math.signum(output);

		if (Math.abs(output) > azimuthMaxSpeedAbs)
		{
			output = azimuthMaxSpeedAbs * sign;
		}
		else if (Math.abs(output) < azimuthMinSpeedAbs)
		{
			output = 0;
		}

		return output;
	}
}
