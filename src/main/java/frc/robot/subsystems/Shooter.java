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

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DashboardConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.Vex775proMotorConstants;

public class Shooter extends SubsystemBase
{
	private TalonSRX masterMotor = new TalonSRX(ShooterConstants.leftMotorChannel);
	private TalonSRX slaveMotor = new TalonSRX(ShooterConstants.rightMotorChannel);

	private XboxController controller;

	/**
	 * Creates an instance.
	 */
	public Shooter(XboxController controller)
	{
		this.controller = controller;

		masterMotor.setInverted(ShooterConstants.leftMotorInverted);
		slaveMotor.setInverted(ShooterConstants.rightMotorInverted);

		slaveMotor.set(ControlMode.Follower, masterMotor.getDeviceID());

		for (TalonSRX motor : new TalonSRX[] { masterMotor, slaveMotor})
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

	/***
	 * Sets the shooter motor speeds in velocity (RPM).
	 */
	public void setVelocity(double veolcity)
	{
		// Velocity is measured in encoder units per 100 ms.

		var unitsPer100Ms =
			veolcity * Vex775proMotorConstants.countsPerRevolution * Vex775proMotorConstants.ticksPerCount
			/ (60.0 * 1000.0 / 100.0);
		SmartDashboard.putNumber(DashboardConstants.shooterSetVelocityKey, unitsPer100Ms);
		masterMotor.set(ControlMode.Velocity, unitsPer100Ms);
	}

	/***
	 * Sets the shooter motor speeds in percentage.
	 */
	public void setPercentage(double percentage)
	{
		masterMotor.set(ControlMode.PercentOutput, percentage);
	}

	/***
	 * Stops the shooter motors.
	 */
	public void stop()
	{
		setPercentage(0);
	}
}
