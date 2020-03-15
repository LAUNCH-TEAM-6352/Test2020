/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.JohnsonMotor;

public class RunJohnsonMotor extends CommandBase
{
	private final JohnsonMotor motor;
	private String key = null;
	private double velocity;

	/**
	 * Creates a new RunJohnsonMotor.
	 */
	public RunJohnsonMotor(JohnsonMotor motor)
	{
		this.motor = motor;
		addRequirements(motor);
	}

	public RunJohnsonMotor(JohnsonMotor motor, String key)
	{
		this(motor);
		this.key = key;
	}

	public RunJohnsonMotor(JohnsonMotor motor, double velocity)
	{
		this(motor);
		this.velocity = velocity;
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize()
	{
		if (key != null)
		{
			velocity = SmartDashboard.getNumber(key, 0.0);
		}
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute()
	{
		motor.setVelocity(velocity);
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted)
	{
		motor.stop();
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished()
	{
		return false;
	}
}
