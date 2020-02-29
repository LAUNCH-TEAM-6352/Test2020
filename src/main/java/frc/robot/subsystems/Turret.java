/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DashboardConstants;
import frc.robot.Constants.TurretConstants;

/***
 * A subsystem that controls the turret.
 */
public class Turret extends SubsystemBase
{
	private TalonSRX hoodMotor = new TalonSRX(TurretConstants.hoodMotorChannel);
	private VictorSPX azimuthMotor = new VictorSPX(TurretConstants.azimuthMotorChannel);

	private DigitalInput downHoodLimit = new DigitalInput(TurretConstants.downHoodLimitChannel);

	private DigitalInput leftAzimuthLimit = new DigitalInput(TurretConstants.leftAzimuthLimitChannel);
	private DigitalInput frontAzimuthLimit = new DigitalInput(TurretConstants.frontAzimuthLimitChannel);
	private DigitalInput rightAzimuthLimit = new DigitalInput(TurretConstants.rightAzimuthLimitChannel);

	private XboxController controller;

	/**
	 * Creates an instance.
	 */
	public Turret(XboxController controller)
	{
		this.controller = controller;

		hoodMotor.setInverted(InvertType.InvertMotorOutput);
		azimuthMotor.setInverted(InvertType.InvertMotorOutput);
	}

	/**
	 * Run hood motor at specified percantage.
	 * 
	 * @param percentage
	 */
	public void setHood(double percentage)
	{
		if (percentage < 0 && !downHoodLimit.get())
		{
			percentage = 0;
			controller.setRumble(RumbleType.kRightRumble, 1);
		}
		else
		{
			controller.setRumble(RumbleType.kRightRumble, 0);
		}

		SmartDashboard.putNumber(DashboardConstants.hoodMotorKey, percentage);

		// Scale percentage to reasonable speed:
		percentage *= percentage < 0
			? TurretConstants.hoodDownPercentageScaleFactor
			: TurretConstants.hoodUpPercentageScaleFactor;
		hoodMotor.set(ControlMode.PercentOutput, percentage);
	}

	/**
	 * Run azimuth motor at specified percantage.
	 * 
	 * @param percentage
	 */
	public void setAzimuth(double percentage)
	{
		if (percentage > 0 && !rightAzimuthLimit.get() || percentage < 0 && !leftAzimuthLimit.get())
		{
			percentage = 0;
			controller.setRumble(RumbleType.kRightRumble, 1);
		}
		else
		{
			controller.setRumble(RumbleType.kRightRumble, 0);
		}

		SmartDashboard.putNumber(DashboardConstants.azimuthMotorKey, percentage);
		azimuthMotor.set(ControlMode.PercentOutput, percentage * TurretConstants.azimuthPercentageScaleFactor);
	}

	/***
	 * Convenience method to stop all turret motion.
	 */
	public void stop()
	{
		setHood(0);
		setAzimuth(0);
	}

	/**
	 * Indicates if the azimuth is at the front position.
	 */
	public boolean isAtFront()
	{
		return !frontAzimuthLimit.get();
	}

	public boolean isToLeftOfFront()
	{
		return false;
	}
	
	@Override
	public void periodic()
	{
		SmartDashboard.putBoolean(DashboardConstants.downHoodLimitKey, downHoodLimit.get());

		SmartDashboard.putBoolean(DashboardConstants.leftAzimuthLimitKey, leftAzimuthLimit.get());
		SmartDashboard.putBoolean(DashboardConstants.frontAzimuthLimitKey, frontAzimuthLimit.get());
		SmartDashboard.putBoolean(DashboardConstants.rightAzimuthLimitKey, rightAzimuthLimit.get());
	}
}
