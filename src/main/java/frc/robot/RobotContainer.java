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
import frc.robot.Constants.LimelightConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.AimShooterUsingLimelight;
import frc.robot.commands.RunMotorAtPercentage;
import frc.robot.commands.RunMotorAtVelocity;
import frc.robot.commands.RunShooterWithGameController;
import frc.robot.commands.SetPipelineAndAimShooter;
import frc.robot.subsystems.Falcon500MotorSubsystem;
import frc.robot.subsystems.NeoMotorSubsystem;
import frc.robot.subsystems.RedlineMotorSubsystem;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vex775proMotorSubsystem;
import frc.util.LimelightCamera;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
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
	private Vex775proMotorSubsystem vex775proMotorSubsystem = null;

	private Shooter shooter = null;

	// I/O Devices:
	private XboxController xboxController = null;
	private LimelightCamera limelightCamera = null;

	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer()
	{
		// Create I/O Devices:
		xboxController = new XboxController(OIConstants.xboxControllerPort);
		limelightCamera = LimelightCamera.getInstance();

		// Create desired subsystems:
		// neoMotorSubsystem = new NeoMotorSubsystem();
		// falcon500MotorSubsystem = new Falcon500MotorSubsystem();
		//redlineMotorSubsystem = new RedlineMotorSubsystem();
		//vex775proMotorSubsystem = new Vex775proMotorSubsystem();

		shooter = new Shooter(xboxController);

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

		if (vex775proMotorSubsystem != null)
		{
			vex775proMotorSubsystem.setDefaultCommand(
				new RunMotorAtPercentage(vex775proMotorSubsystem, () ->
					(xboxController.getTriggerAxis(Hand.kRight) - xboxController.getTriggerAxis(Hand.kLeft))
			));
		}

		if (shooter != null)
		{
			shooter.setDefaultCommand(new RunShooterWithGameController(shooter, xboxController));
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

		new JoystickButton(xboxController, Button.kX.value)
			.whileHeld(new RunMotorAtVelocity(falcon500MotorSubsystem, DashboardConstants.targetVelocityKey));

		new JoystickButton(xboxController, Button.kY.value)
			.whileHeld(new RunMotorAtVelocity(redlineMotorSubsystem, DashboardConstants.targetVelocityKey));

		new JoystickButton(xboxController, Button.kA.value)
			.whenPressed(new AimShooterUsingLimelight(shooter));
	}

	private void initSmartDashboard()
	{
		SmartDashboard.putNumber(DashboardConstants.targetVelocityKey, DashboardConstants.targetVelocityDefault);
		SmartDashboard.putNumber(DashboardConstants.targetPercentageKey, DashboardConstants.targetPercentageDefault);

		SmartDashboard.putNumber(DashboardConstants.leftShooterTargetVelocityKey, 0);
		SmartDashboard.putNumber(DashboardConstants.rightShooterTargetVelocityKey, 0);

		SmartDashboard.putNumber(DashboardConstants.leftShooterTargetPercentageKey, 0);
		SmartDashboard.putNumber(DashboardConstants.rightShooterTargetPercentageKey, 0);

		SmartDashboard.putData("LL: Driver1", new InstantCommand(() -> limelightCamera.setPipeline(LimelightConstants.pipelineDriver1)));
		SmartDashboard.putData("LL: Driver2", new InstantCommand(() -> limelightCamera.setPipeline(LimelightConstants.pipelineDriver2)));
		SmartDashboard.putData("LL: Driver3", new InstantCommand(() -> limelightCamera.setPipeline(LimelightConstants.pipelineDriver3)));
		SmartDashboard.putData("LL: Vision1", new InstantCommand(() -> limelightCamera.setPipeline(LimelightConstants.pipelineVision1)));
		SmartDashboard.putData("LL: Vision2", new InstantCommand(() -> limelightCamera.setPipeline(LimelightConstants.pipelineVision2)));
		SmartDashboard.putData("LL: Vision3", new InstantCommand(() -> limelightCamera.setPipeline(LimelightConstants.pipelineVision3)));

		if (shooter != null)
		{
			SmartDashboard.putData("Run Alt at %", new RunCommand(
				() -> shooter.setAltitude(SmartDashboard.getNumber(DashboardConstants.targetPercentageKey, DashboardConstants.targetPercentageDefault)),
				//() -> shooter.setAltitude(0),
				shooter)
			);

			SmartDashboard.putData("Run Azm at %", new RunCommand(
				() -> shooter.setAzimuth(SmartDashboard.getNumber(DashboardConstants.targetPercentageKey, DashboardConstants.targetPercentageDefault)),
				//() -> shooter.setAzimuth(0),
				shooter)
			);

			SmartDashboard.putData("Run Shooters Vel", new StartEndCommand(
				() -> shooter.setShooterVelocities(
					SmartDashboard.getNumber(DashboardConstants.leftShooterTargetVelocityKey, 0),
					SmartDashboard.getNumber(DashboardConstants.rightShooterTargetVelocityKey, 0)),
				() -> shooter.setShooterVelocities(0, 0),
				shooter
				)
			);

			SmartDashboard.putData("Run Shooters %", new StartEndCommand(
				() -> shooter.setShooterPercentages(
					SmartDashboard.getNumber(DashboardConstants.leftShooterTargetPercentageKey, 0),
					SmartDashboard.getNumber(DashboardConstants.rightShooterTargetPercentageKey, 0)),
				() -> shooter.setShooterPercentages(0, 0),
				shooter
				)
			);
		}

		if (redlineMotorSubsystem != null)
		{
			SmartDashboard.putData("Run Redline at Veolcity", new RunMotorAtVelocity(redlineMotorSubsystem, DashboardConstants.targetVelocityKey));
			SmartDashboard.putData("Run Redline at Percentage", new RunMotorAtPercentage(redlineMotorSubsystem, () -> SmartDashboard.getNumber(DashboardConstants.targetPercentageKey, 0.0)));
		}

		if (vex775proMotorSubsystem != null)
		{
			SmartDashboard.putData("Run 775pro at Veolcity", new RunMotorAtVelocity(vex775proMotorSubsystem, DashboardConstants.targetVelocityKey));
			SmartDashboard.putData("Run 775pro at Percentage", new RunMotorAtPercentage(vex775proMotorSubsystem, () -> SmartDashboard.getNumber(DashboardConstants.targetPercentageKey, 0.0)));
		}

		SmartDashboard.putData("Target Shooter", new SetPipelineAndAimShooter(shooter));
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
