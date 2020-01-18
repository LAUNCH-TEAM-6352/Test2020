/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.DashboardConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.RunFalcon500MotorAtVelocity;
import frc.robot.commands.RunFalcon500MotorUsingXboxController;
import frc.robot.commands.RunNeoMotorAtVelocity;
import frc.robot.commands.RunNeoMotorUsingXboxController;
import frc.robot.subsystems.Falcon500MotorSubsystem;
import frc.robot.subsystems.NeoMotorSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final NeoMotorSubsystem neoMotorSubsystem = new NeoMotorSubsystem();
  private final Falcon500MotorSubsystem falcon500MotorSubsystem = new Falcon500MotorSubsystem();

  XboxController xboxController = new XboxController(OIConstants.xboxControllerPort);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Configure default commands:
    neoMotorSubsystem.setDefaultCommand(new RunNeoMotorUsingXboxController(neoMotorSubsystem, xboxController));
    falcon500MotorSubsystem.setDefaultCommand(new RunFalcon500MotorUsingXboxController(falcon500MotorSubsystem, xboxController));

    // Initialize smart dashboard:
    initSmartDashboard();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(xboxController, Button.kB.value)
      .whileHeld(new RunNeoMotorAtVelocity(neoMotorSubsystem, DashboardConstants.motorVeolcityKey));

    new JoystickButton(xboxController, Button.kX.value)
      .whileHeld(new RunFalcon500MotorAtVelocity(falcon500MotorSubsystem, DashboardConstants.motorVeolcityKey));
  }
  
  private void initSmartDashboard() {
    SmartDashboard.setDefaultNumber(DashboardConstants.motorVeolcityKey, DashboardConstants.motorVeolcityDefault);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
