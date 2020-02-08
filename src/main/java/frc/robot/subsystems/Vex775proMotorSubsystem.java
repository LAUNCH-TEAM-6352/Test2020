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
import frc.robot.Constants.Vex775proMotorConstants;

public class Vex775proMotorSubsystem extends MotorSubsystemBase
{
	private TalonSRX motor = new TalonSRX(Vex775proMotorConstants.channel);

	/**
	 * Creates a new ExampleSubsystem.
	 */
	public Vex775proMotorSubsystem()
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

	/**
	 * Run motor at specified velocity in RPM.
	 * 
	 * @param velocity
	 */
	@Override
	public void setVelocity(double velocity)
	{
		// Velocity is measured in encoder units per 100 ms.
		var unitsPer100Ms =
			velocity * Vex775proMotorConstants.countsPerRevolution * Vex775proMotorConstants.ticksPerCount
			/ (60.0 * 1000.0 / 100.0);
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
