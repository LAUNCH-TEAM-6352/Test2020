/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.JohnsonMotorConstants;

/**
 * Add your docs here.
 */
public class JohnsonMotor extends SubsystemBase
{
	private TalonSRX motor = new TalonSRX(JohnsonMotorConstants.channel);

	private XboxController controller;

	public JohnsonMotor(XboxController controller)
	{
		this.controller = controller;

		motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		motor.configAllowableClosedloopError(
			JohnsonMotorConstants.profileSlot,
			JohnsonMotorConstants.pidAllowableError,
			JohnsonMotorConstants.pidTimeoutMs);
		motor.configClosedLoopPeakOutput(
			JohnsonMotorConstants.profileSlot,
			JohnsonMotorConstants.pidPeakOutput,
			JohnsonMotorConstants.pidTimeoutMs);
		motor.configClosedLoopPeriod(
			JohnsonMotorConstants.profileSlot,
			JohnsonMotorConstants.pidLoopPeriodMs,
			JohnsonMotorConstants.pidTimeoutMs);
		motor.config_kP(
			JohnsonMotorConstants.profileSlot,
			JohnsonMotorConstants.pidP,
			JohnsonMotorConstants.pidTimeoutMs);
		motor.config_kI(
			JohnsonMotorConstants.profileSlot,
			JohnsonMotorConstants.pidI,
			JohnsonMotorConstants.pidTimeoutMs);
		motor.config_kD(
			JohnsonMotorConstants.profileSlot,
			JohnsonMotorConstants.pidD,
			JohnsonMotorConstants.pidTimeoutMs);
		motor.config_kF(
			JohnsonMotorConstants.profileSlot,
			JohnsonMotorConstants.pidFF,
			JohnsonMotorConstants.pidTimeoutMs);
		motor.config_IntegralZone(
			JohnsonMotorConstants.profileSlot,
			JohnsonMotorConstants.pidIZ,
			JohnsonMotorConstants.pidTimeoutMs);
		motor.selectProfileSlot(
			JohnsonMotorConstants.profileSlot,
			JohnsonMotorConstants.primaryClosedLoop);

		motor.setSensorPhase(JohnsonMotorConstants.phase);

		motor.setInverted(JohnsonMotorConstants.isInverted);

	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void setPercentage(double value)
	{
		motor.set(ControlMode.PercentOutput, value);
	}

	public void stop()
	{
		setPercentage(0);
	}

}
