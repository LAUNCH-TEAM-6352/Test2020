/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.DashboardConstants;
import frc.robot.Constants.NeoMotorConstants;

public class NeoMotorSubsystem extends MotorSubsystemBase
{
	private CANSparkMax motor = new CANSparkMax(NeoMotorConstants.channel, MotorType.kBrushless);
	private CANPIDController pidController;

	/**
	 * Creates a new ExampleSubsystem.
	 */
	public NeoMotorSubsystem()
	{
		pidController = motor.getPIDController();
		pidController.setP(NeoMotorConstants.pidP);
		pidController.setI(NeoMotorConstants.pidI);
		pidController.setD(NeoMotorConstants.pidD);
		pidController.setIZone(NeoMotorConstants.pidIZ);
		pidController.setFF(NeoMotorConstants.pidFF);
		pidController.setOutputRange(NeoMotorConstants.pidMinOutput, NeoMotorConstants.pidMaxOutput);
	}

	/**
	 * Run motor at specified velocity in RPM.
	 * 
	 * @param velocity
	 */
	@Override
	public void setVelocity(double velocity)
	{
		SmartDashboard.putNumber(DashboardConstants.setVelocityKey, velocity);
		pidController.setReference(velocity, ControlType.kVelocity);
	}

	/**
	 * Run motor at specified power percentage.
	 * 
	 * @param percentage
	 */
	@Override
	public void setPercentage(double percentage)
	{
		motor.set(percentage);
	}

	/**
	 * Stop the motor.
	 */
	@Override
	public void stop()
	{
		motor.stopMotor();
	}

	@Override
	public void periodic()
	{
		// This method will be called once per scheduler run
	}
}
