/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DashboardConstants;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase
{
	private VictorSPX altitudeMotor = new VictorSPX(ShooterConstants.altitudeMotorChannel);
	private VictorSPX azimuthMotor = new VictorSPX(ShooterConstants.azimuthMotorChannel);

	private DigitalInput maxAltitudeLimit = new DigitalInput(ShooterConstants.maxAltitudeLimitChannel);
	private DigitalInput minAltitudeLimit = new DigitalInput(ShooterConstants.minAltitudeLimitChannel);

	//private TalonSRX leftMotor = new TalonSRX(ShooterConstants.leftMotorChannel);
	//private TalonSRX rightMotor = new TalonSRX(ShooterConstants.rightMotorChannel);

	private XboxController controller;

	/**
	 * Creates an instance.
	 */
	public Shooter(XboxController controller)
	{
		this.controller = controller;

		altitudeMotor.setInverted(InvertType.InvertMotorOutput);
		azimuthMotor.setInverted(InvertType.InvertMotorOutput);
	}

	/**
	 * Run altitude mtor at specified percantage.
	 * 
	 * @param percentage
	 */
	public void setAltitude(double percentage)
	{
		if (percentage > 0 && !maxAltitudeLimit.get() || percentage < 0 && !minAltitudeLimit.get())
		{
			percentage = 0;
			controller.setRumble(RumbleType.kRightRumble, 1);
		}
		else
		{
			controller.setRumble(RumbleType.kRightRumble, 0);
		}

		SmartDashboard.putNumber(DashboardConstants.altitudeMotorKey, percentage);

		// Scale percentage to reasonable speed:
		percentage *= percentage < 0
			? ShooterConstants.altitudeDownPercentageScaleFactor
			: ShooterConstants.altitudeUpPercentageScaleFactor;
		altitudeMotor.set(ControlMode.PercentOutput, percentage);
	}

	/**
	 * Run azimuth motor at specified percantage.
	 * 
	 * @param percentage
	 */
	public void setAzimuth(double percentage)
	{
		SmartDashboard.putNumber(DashboardConstants.azimuthMotorKey, percentage);
		azimuthMotor.set(ControlMode.PercentOutput, percentage * ShooterConstants.azimuthPercentageScaleFactor);
	}

	/***
	 * Convenience method to stop all turret motion.
	 */
	public void stopTurret()
	{
		setAltitude(0);
		setAzimuth(0);
	}
	
	@Override
	public void periodic()
	{
		SmartDashboard.putBoolean(DashboardConstants.maxAltitudeLimitKey, maxAltitudeLimit.get());
		SmartDashboard.putBoolean(DashboardConstants.minAltitudeLimitKey, minAltitudeLimit.get());
	}
}
