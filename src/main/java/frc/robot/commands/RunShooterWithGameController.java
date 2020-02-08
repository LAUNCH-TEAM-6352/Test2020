/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.MotorSubsystemBase;
import frc.robot.subsystems.Shooter;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class RunShooterWithGameController extends CommandBase
{
	@SuppressWarnings(
	{ "PMD.UnusedPrivateField", "PMD.SingularField" })
	private final Shooter shooter;
	private final XboxController controller;

	public RunShooterWithGameController(Shooter shooter, XboxController controller)
	{
		this.shooter = shooter;
		this.controller = controller;

		// Use addRequirements() here to declare subsystem dependencies.
		if (shooter != null)
		{
			addRequirements(shooter);
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
		if (shooter != null)
		{
			shooter.setAltitude(controller.getY(Hand.kRight) * -1.0);
			shooter.setAzimuth(controller.getX(Hand.kRight));
		}
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted)
	{
		shooter.stopTurret();
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished()
	{
		return false;
	}
}
