/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.DashboardConstants;
import frc.robot.Constants.LimelightConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.AimShooterUsingLimelight;
import frc.robot.commands.RunJohnsonMotor;
import frc.robot.commands.RunTurretWithGameController;
import frc.robot.commands.SpinDownShooter;
import frc.robot.commands.SpinUpShooterAndAimUsingLimelight;
import frc.robot.subsystems.JohnsonMotor;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import frc.util.LimelightCamera;

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

	private Shooter shooter = null;
	private Turret turret = null;
	private JohnsonMotor johnsonMotor;

	// I/O Devices:
	private XboxController gameController = null;
	private LimelightCamera limelightCamera = null;

	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer()
	{
		// Create I/O Devices:
		gameController = new XboxController(OIConstants.xboxControllerPort);
		limelightCamera = LimelightCamera.getInstance();

		// Create desired subsystems:
		// neoMotorSubsystem = new NeoMotorSubsystem();
		// falcon500MotorSubsystem = new Falcon500MotorSubsystem();
		//redlineMotorSubsystem = new RedlineMotorSubsystem();
		//vex775proMotorSubsystem = new Vex775proMotorSubsystem();

		//shooter = new Shooter(xboxController);
		//turret = new Turret(xboxController);
		johnsonMotor = new JohnsonMotor();

		// Configure default commands:
		if (turret != null)
		{
			turret.setDefaultCommand(new RunTurretWithGameController(turret, gameController));
		}

		if (shooter != null)
		{
			shooter.setDefaultCommand(new RunCommand(
				() -> shooter.setPercentage(gameController.getTriggerAxis(Hand.kLeft)),
				shooter
			));
		}

		if (johnsonMotor != null)
		{
			johnsonMotor.setDefaultCommand(new RunCommand(
				() -> johnsonMotor.setPercentage(gameController.getTriggerAxis(Hand.kLeft) - gameController.getTriggerAxis(Hand.kRight)),
				johnsonMotor
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
		// Start shooter motors, switch to vision procesing, aim turret
		new JoystickButton(gameController, Button.kA.value)
			.whenPressed(new SpinUpShooterAndAimUsingLimelight(johnsonMotor));

		// Stop the shooter motors and restore the Limelight to driver mode:
		new JoystickButton(gameController, Button.kY.value)
			.whenPressed(new SpinDownShooter(johnsonMotor));

		// Set the limelight to 1x driver mode:
		new JoystickButton(gameController, Button.kBumperLeft.value)
			.whenPressed(new InstantCommand(() -> limelightCamera.setPipeline(LimelightConstants.pipelineDriver1)));

		// Set the limelight to 2x driver mode:
		new JoystickButton(gameController, Button.kBumperRight.value)
			.whenPressed(new InstantCommand(() -> limelightCamera.setPipeline(LimelightConstants.pipelineDriver2)));

	}

	private void initSmartDashboard()
	{
		SmartDashboard.putNumber(DashboardConstants.johnsonTargetVelocityKey, DashboardConstants.johnsonTargetVelocityDefault);

		SmartDashboard.putData("LL: Driver1", new InstantCommand(() -> limelightCamera.setPipeline(LimelightConstants.pipelineDriver1)));
		SmartDashboard.putData("LL: Driver2", new InstantCommand(() -> limelightCamera.setPipeline(LimelightConstants.pipelineDriver2)));
		SmartDashboard.putData("LL: Driver3", new InstantCommand(() -> limelightCamera.setPipeline(LimelightConstants.pipelineDriver3)));
		SmartDashboard.putData("LL: Vision1", new InstantCommand(() -> limelightCamera.setPipeline(LimelightConstants.pipelineVision1)));
		SmartDashboard.putData("LL: Vision2", new InstantCommand(() -> limelightCamera.setPipeline(LimelightConstants.pipelineVision2)));
		SmartDashboard.putData("LL: Vision3", new InstantCommand(() -> limelightCamera.setPipeline(LimelightConstants.pipelineVision3)));

		if (johnsonMotor != null)
		{
			SmartDashboard.putData("Run Johnson Vel", new StartEndCommand(
				() -> johnsonMotor.setVelocity(
					SmartDashboard.getNumber(DashboardConstants.johnsonTargetVelocityKey, 0)),
				() -> johnsonMotor.setPercentage(0),
				johnsonMotor
				)
			);

			SmartDashboard.putData("Spin Up Shooter & Aim", new SpinUpShooterAndAimUsingLimelight(johnsonMotor));
			SmartDashboard.putData("Spin Down Shooter", new SpinDownShooter(johnsonMotor));
		}

		if (turret != null)
		{

			SmartDashboard.putData("Move Hood", new StartEndCommand(
				() -> turret.setHoodPosition(
					SmartDashboard.getNumber(DashboardConstants.hoodTargetPositionKey, 0)),
				() -> turret.setHood(0),
				turret
				)
			);
		}

		if (shooter != null)
		{
			SmartDashboard.putData("Run Shooter Vel", new StartEndCommand(
				() -> shooter.setVelocity(
					SmartDashboard.getNumber(DashboardConstants.shooterTargetVelocityKey, 0)),
				() -> shooter.setPercentage(0),
				shooter
				)
			);

			SmartDashboard.putData("Run Shooter %", new StartEndCommand(
				() -> shooter.setPercentage(
					SmartDashboard.getNumber(DashboardConstants.shooterTargetPercentageKey, 0)),
				() -> shooter.setPercentage(0),
				shooter
				)
			);
		}
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
