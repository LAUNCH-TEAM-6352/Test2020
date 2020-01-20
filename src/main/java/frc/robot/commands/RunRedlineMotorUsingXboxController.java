/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.RedlineMotorSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class RunRedlineMotorUsingXboxController extends CommandBase
{
	@SuppressWarnings(
	{ "PMD.UnusedPrivateField", "PMD.SingularField" })
	private final RedlineMotorSubsystem subsystem;
	private final XboxController xboxController;

	/**
	 * Creates a new ExampleCommand.
	 *
	 * @param subsystem
	 *                      The subsystem used by this command.
	 */
	public RunRedlineMotorUsingXboxController(RedlineMotorSubsystem subsystem, XboxController controller)
	{
		this.subsystem = subsystem;
		xboxController = controller;

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
		subsystem.setPercentage(xboxController.getTriggerAxis(Hand.kRight) - xboxController.getTriggerAxis(Hand.kLeft));
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
