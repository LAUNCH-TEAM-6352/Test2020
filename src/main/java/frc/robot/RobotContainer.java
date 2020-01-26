/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.DashboardConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.RunMotorAtPercentage;
import frc.robot.commands.RunMotorAtVelocity;
import frc.robot.subsystems.Falcon500MotorSubsystem;
import frc.robot.subsystems.NeoMotorSubsystem;
import frc.robot.subsystems.RedlineMotorSubsystem;
import frc.util.LimelightCamera;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer
{
	// The robot's subsystems and commands are defined here...
	private final NeoMotorSubsystem neoMotorSubsystem = null;
	private final Falcon500MotorSubsystem falcon500MotorSubsystem = null;
	private RedlineMotorSubsystem redlineMotorSubsystem = null;

	XboxController xboxController = new XboxController(OIConstants.xboxControllerPort);

	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer()
	{
		// Create desired subsystems:
		// neoMotorSubsystem = new NeoMotorSubsystem();
		// falcon500MotorSubsystem = new Falcon500MotorSubsystem();
		redlineMotorSubsystem = new RedlineMotorSubsystem();

		// Configure default commands:
		if (neoMotorSubsystem != null)
		{
			neoMotorSubsystem.setDefaultCommand(
				new RunMotorAtPercentage(neoMotorSubsystem, () -> xboxController.getY(Hand.kRight)));
		}

		if (falcon500MotorSubsystem != null)
		{
			falcon500MotorSubsystem.setDefaultCommand(
				new RunMotorAtPercentage(falcon500MotorSubsystem, () -> xboxController.getY(Hand.kLeft)));
		}

		if (redlineMotorSubsystem != null)
		{
			redlineMotorSubsystem.setDefaultCommand(
				new RunMotorAtPercentage(redlineMotorSubsystem, () ->
					(xboxController.getTriggerAxis(Hand.kRight) - xboxController.getTriggerAxis(Hand.kLeft))
			));
		}

		// Configure the button bindings
		configureButtonBindings();

		// Initialize smart dashboard:
		initSmartDashboard();
	}

	/**
	 * Use this method to define your button->command mappings. Buttons can be
	 * created by instantiating a {@link GenericHID} or one of its subclasses
	 * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
	 * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
	 */
	private void configureButtonBindings()
	{
		new JoystickButton(xboxController, Button.kB.value)
				.whileHeld(new RunMotorAtVelocity(neoMotorSubsystem, DashboardConstants.targetVelocityKey));

		new JoystickButton(xboxController, Button.kX.value).whileHeld(
				new RunMotorAtVelocity(falcon500MotorSubsystem, DashboardConstants.targetVelocityKey));

		new JoystickButton(xboxController, Button.kY.value)
				.whileHeld(new RunMotorAtVelocity(redlineMotorSubsystem, DashboardConstants.targetVelocityKey));
	}

	private void initSmartDashboard()
	{
		SmartDashboard.setDefaultNumber(DashboardConstants.targetVelocityKey, DashboardConstants.targetVelocityDefault);

		SmartDashboard.putData("LL: Toggle LED", new InstantCommand(() -> LimelightCamera.getInstance().toggleLed()));
		SmartDashboard.putData("LL: Toggle Mode", new InstantCommand(() -> LimelightCamera.getInstance().toggleVisionProcessingMode()));
		SmartDashboard.putData("LL: Zoom In", new InstantCommand(() -> LimelightCamera.getInstance().zoomIn()));
		SmartDashboard.putData("LL: Zoom Out", new InstantCommand(() -> LimelightCamera.getInstance().zoomOut()));

		SmartDashboard.putData("Run Redline at Veolcity", new RunMotorAtVelocity(redlineMotorSubsystem, DashboardConstants.targetVelocityKey));
	}

	/**
	 * Use this to pass the autonomous command to the main {@link Robot} class.
	 *
	 * @return the command to run in autonomous
	 */
	public Command getAutonomousCommand()
	{
		// An ExampleCommand will run in autonomous
		return null;
	}
}
