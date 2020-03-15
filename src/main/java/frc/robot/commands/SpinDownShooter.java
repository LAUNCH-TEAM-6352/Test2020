/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.DashboardConstants;
import frc.robot.Constants.LimelightConstants;
import frc.robot.subsystems.JohnsonMotor;
import frc.util.LimelightCamera;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class SpinDownShooter extends ParallelCommandGroup
{
	/**
	 * Creates a new StartShooterAndAimUsingLimelight.
	 */
	public SpinDownShooter(JohnsonMotor johnsonMotor)
	{
		super(
			new InstantCommand(() -> johnsonMotor.stop(), johnsonMotor),
			new ConditionalCommand(
				new InstantCommand(() -> LimelightCamera.getInstance().setPipeline(
					LimelightConstants.drivingPipelines[LimelightCamera.getInstance().getPipeline()])),
				new WaitCommand(0),
				() -> LimelightConstants.drivingPipelines[LimelightCamera.getInstance().getPipeline()] >= 0
			)
		);
	}
}
