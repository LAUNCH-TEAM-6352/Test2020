/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.DashboardConstants;
import frc.robot.Constants.Falcon500MotorConstants;

public class Falcon500MotorSubsystem extends MotorSubsystemBase
{
	private TalonFX motor = new TalonFX(Falcon500MotorConstants.channel);

	/**
	 * Creates a new ExampleSubsystem.
	 */
	public Falcon500MotorSubsystem()
	{
		motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
		motor.configAllowableClosedloopError(Falcon500MotorConstants.profileSlot,
				Falcon500MotorConstants.pidAllowableError, Falcon500MotorConstants.pidTimeoutMs);
		motor.configClosedLoopPeakOutput(Falcon500MotorConstants.profileSlot, Falcon500MotorConstants.pidPeakOutput,
				Falcon500MotorConstants.pidTimeoutMs);
		motor.configClosedLoopPeriod(Falcon500MotorConstants.profileSlot, Falcon500MotorConstants.pidLoopPeriodMs,
				Falcon500MotorConstants.pidTimeoutMs);
		motor.config_kP(Falcon500MotorConstants.profileSlot, Falcon500MotorConstants.pidP,
				Falcon500MotorConstants.pidTimeoutMs);
		motor.config_kI(Falcon500MotorConstants.profileSlot, Falcon500MotorConstants.pidI,
				Falcon500MotorConstants.pidTimeoutMs);
		motor.config_kD(Falcon500MotorConstants.profileSlot, Falcon500MotorConstants.pidD,
				Falcon500MotorConstants.pidTimeoutMs);
		motor.config_kF(Falcon500MotorConstants.profileSlot, Falcon500MotorConstants.pidFF,
				Falcon500MotorConstants.pidTimeoutMs);
		motor.config_IntegralZone(Falcon500MotorConstants.profileSlot, Falcon500MotorConstants.pidIZ,
				Falcon500MotorConstants.pidTimeoutMs);
		motor.selectProfileSlot(Falcon500MotorConstants.profileSlot, Falcon500MotorConstants.primaryClosedLoop);
	}

	/**
	 * Run motor at specified velocity in RPM.
	 * 
	 * @param velocity
	 */
	@Override
	public void setVelocity(double velocity)
	{
		// Encoder measures 2048 units per rev. Velocity is measured in units per 100
		// ms.
		var unitsPer100Ms = velocity * Falcon500MotorConstants.countsPerRevolution / 600;
		SmartDashboard.putNumber(DashboardConstants.setVelocityKey, unitsPer100Ms);
		motor.set(ControlMode.Velocity, unitsPer100Ms);
	}

	/**
	 * Run motor at specified power percentage.
	 * 
	 * @param percentage
	 */
	@Override
	public void setPercentage(double percentage)
	{
		motor.set(ControlMode.PercentOutput, percentage);
	}

	/**
	 * Stop the motor.
	 */
	@Override
	public void stop()
	{
		setPercentage(0);
	}

	@Override
	public void periodic()
	{
		// This method will be called once per scheduler run
	}
}
