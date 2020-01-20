/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.Falcon500MotorSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class RunFalcon500MotorAtVelocity extends CommandBase
{
	@SuppressWarnings(
	{ "PMD.UnusedPrivateField", "PMD.SingularField" })
	private final Falcon500MotorSubsystem subsystem;
	private String dashboardKey;
	private double velocity;

	public RunFalcon500MotorAtVelocity(Falcon500MotorSubsystem subsystem)
	{
		this.subsystem = subsystem;

		// Use addRequirements() here to declare subsystem dependencies.
		if (subsystem != null)
		{
			addRequirements(subsystem);
		}
	}

	/**
	 * Creates a new ExampleCommand.
	 *
	 */
	public RunFalcon500MotorAtVelocity(Falcon500MotorSubsystem subsystem, String dashboardKey)
	{
		this(subsystem);

		this.dashboardKey = dashboardKey;
	}

	/**
	 * Creates a new ExampleCommand.
	 *
	 */
	public RunFalcon500MotorAtVelocity(Falcon500MotorSubsystem subsystem, double value)
	{
		this(subsystem);

		this.velocity = value;
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize()
	{
		if (dashboardKey != null)
		{
			velocity = SmartDashboard.getNumber(dashboardKey, 0);
		}
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute()
	{
		subsystem.setVelocity(velocity);
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted)
	{
		subsystem.stop();
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished()
	{
		return false;
	}
}
