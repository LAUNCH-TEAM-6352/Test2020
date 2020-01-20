/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.Falcon500MotorSubsystem;
import frc.robot.subsystems.MotorSubsystemBase;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class RunMotorAtPercentage extends CommandBase
{
	@SuppressWarnings(
	{ "PMD.UnusedPrivateField", "PMD.SingularField" })
	private final MotorSubsystemBase subsystem;
	private final DoubleSupplier doubleSupplier;
	private String dashboardKey;
	private double velocity;

	public RunMotorAtPercentage(MotorSubsystemBase subsystem, DoubleSupplier doubleSupplier)
	{
		this.subsystem = subsystem;
		this.doubleSupplier = doubleSupplier;

		// Use addRequirements() here to declare subsystem dependencies.
		if (subsystem != null)
		{
			addRequirements(subsystem);
		}
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize()
	{
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute()
	{
		subsystem.setPercentage(doubleSupplier.getAsDouble());
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