/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.DashboardConstants;
import frc.robot.Constants.RedlineMotorConstants;

public class RedlineMotorSubsystem extends MotorSubsystemBase
{
	private TalonSRX motor = new TalonSRX(RedlineMotorConstants.channel);

	/**
	 * Creates a new ExampleSubsystem.
	 */
	public RedlineMotorSubsystem()
	{
		motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		motor.configAllowableClosedloopError(RedlineMotorConstants.profileSlot, RedlineMotorConstants.pidAllowableError,
				RedlineMotorConstants.pidTimeoutMs);
		motor.configClosedLoopPeakOutput(RedlineMotorConstants.profileSlot, RedlineMotorConstants.pidPeakOutput,
				RedlineMotorConstants.pidTimeoutMs);
		motor.configClosedLoopPeriod(RedlineMotorConstants.profileSlot, RedlineMotorConstants.pidLoopPeriodMs,
				RedlineMotorConstants.pidTimeoutMs);
		motor.config_kP(RedlineMotorConstants.profileSlot, RedlineMotorConstants.pidP,
				RedlineMotorConstants.pidTimeoutMs);
		motor.config_kI(RedlineMotorConstants.profileSlot, RedlineMotorConstants.pidI,
				RedlineMotorConstants.pidTimeoutMs);
		motor.config_kD(RedlineMotorConstants.profileSlot, RedlineMotorConstants.pidD,
				RedlineMotorConstants.pidTimeoutMs);
		motor.config_kF(RedlineMotorConstants.profileSlot, RedlineMotorConstants.pidFF,
				RedlineMotorConstants.pidTimeoutMs);
		motor.config_IntegralZone(RedlineMotorConstants.profileSlot, RedlineMotorConstants.pidIZ,
				RedlineMotorConstants.pidTimeoutMs);
		motor.selectProfileSlot(RedlineMotorConstants.profileSlot, RedlineMotorConstants.primaryClosedLoop);
	}

	/**
	 * Run motor at specified velocity in RPM.
	 * 
	 * @param velocity
	 */
	@Override
	public void setVelocity(double velocity)
	{
		// Velocity is measured in encoder counts per 100 ms.
		var unitsPer100Ms = velocity * RedlineMotorConstants.countsPerRevolution / 600;
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
