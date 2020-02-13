/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DashboardConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.Vex775proMotorConstants;

public class Shooter extends SubsystemBase
{
	private VictorSPX altitudeMotor = new VictorSPX(ShooterConstants.altitudeMotorChannel);
	private VictorSPX azimuthMotor = new VictorSPX(ShooterConstants.azimuthMotorChannel);

	private DigitalInput maxAltitudeLimit = new DigitalInput(ShooterConstants.maxAltitudeLimitChannel);
	private DigitalInput minAltitudeLimit = new DigitalInput(ShooterConstants.minAltitudeLimitChannel);

	private TalonSRX leftMotor = new TalonSRX(ShooterConstants.leftMotorChannel);
	private TalonSRX rightMotor = new TalonSRX(ShooterConstants.rightMotorChannel);

	private XboxController controller;

	/**
	 * Creates an instance.
	 */
	public Shooter(XboxController controller)
	{
		this.controller = controller;

		altitudeMotor.setInverted(InvertType.InvertMotorOutput);
		azimuthMotor.setInverted(InvertType.InvertMotorOutput);

		rightMotor.setInverted(InvertType.InvertMotorOutput);

		for (TalonSRX motor : new TalonSRX[] { leftMotor, rightMotor})
		{
			motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
			motor.configAllowableClosedloopError(Vex775proMotorConstants.profileSlot, Vex775proMotorConstants.pidAllowableError,
					Vex775proMotorConstants.pidTimeoutMs);
			motor.configClosedLoopPeakOutput(Vex775proMotorConstants.profileSlot, Vex775proMotorConstants.pidPeakOutput,
					Vex775proMotorConstants.pidTimeoutMs);
			motor.configClosedLoopPeriod(Vex775proMotorConstants.profileSlot, Vex775proMotorConstants.pidLoopPeriodMs,
					Vex775proMotorConstants.pidTimeoutMs);
			motor.config_kP(Vex775proMotorConstants.profileSlot, Vex775proMotorConstants.pidP,
					Vex775proMotorConstants.pidTimeoutMs);
			motor.config_kI(Vex775proMotorConstants.profileSlot, Vex775proMotorConstants.pidI,
					Vex775proMotorConstants.pidTimeoutMs);
			motor.config_kD(Vex775proMotorConstants.profileSlot, Vex775proMotorConstants.pidD,
					Vex775proMotorConstants.pidTimeoutMs);
			motor.config_kF(Vex775proMotorConstants.profileSlot, Vex775proMotorConstants.pidFF,
					Vex775proMotorConstants.pidTimeoutMs);
			motor.config_IntegralZone(Vex775proMotorConstants.profileSlot, Vex775proMotorConstants.pidIZ,
					Vex775proMotorConstants.pidTimeoutMs);
			motor.selectProfileSlot(Vex775proMotorConstants.profileSlot, Vex775proMotorConstants.primaryClosedLoop);
			motor.setSensorPhase(Vex775proMotorConstants.phase);
		}
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
	 * Sets the shooter motor speeds in velocity.
	 */
	public void setShooterVelocities(double left, double right)
	{
		// Velocity is measured in encoder units per 100 ms.

		var leftUnitsPer100Ms =
			left * Vex775proMotorConstants.countsPerRevolution * Vex775proMotorConstants.ticksPerCount
			/ (60.0 * 1000.0 / 100.0);
		SmartDashboard.putNumber(DashboardConstants.leftShooterSetVelocityKey, leftUnitsPer100Ms);
		leftMotor.set(ControlMode.Velocity, leftUnitsPer100Ms);

		var rightUnitsPer100Ms =
			right * Vex775proMotorConstants.countsPerRevolution * Vex775proMotorConstants.ticksPerCount
			/ (60.0 * 1000.0 / 100.0);
		SmartDashboard.putNumber(DashboardConstants.rightShooterSetVelocityKey, rightUnitsPer100Ms);
		rightMotor.set(ControlMode.Velocity, rightUnitsPer100Ms);
	}

	/***
	 * Sets thwe shooter motor speeds in percentage.
	 */
	public void setShooterPercentages(double left, double right)
	{
		leftMotor.set(ControlMode.PercentOutput, left);
		rightMotor.set(ControlMode.PercentOutput, right);
	}

	/***
	 * Convenience method to stop all turret motion.
	 */
	public void stopTurret()
	{
		setAltitude(0);
		setAzimuth(0);
	}

	/***
	 * Stops the shooter motors.
	 */
	public void stopShooters()
	{
		setShooterPercentages(0, 0);
	}
	
	@Override
	public void periodic()
	{
		SmartDashboard.putBoolean(DashboardConstants.maxAltitudeLimitKey, maxAltitudeLimit.get());
		SmartDashboard.putBoolean(DashboardConstants.minAltitudeLimitKey, minAltitudeLimit.get());
	}
}
