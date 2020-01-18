/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.MotorConstants;

public class NeoMotorSubsystem extends SubsystemBase {
  private CANSparkMax motor = new CANSparkMax(MotorConstants.neoMotorChannel, MotorType.kBrushless);
  private CANPIDController pidController;

  /**
   * Creates a new ExampleSubsystem.
   */
  public NeoMotorSubsystem() {
    pidController = motor.getPIDController();
    pidController.setP(MotorConstants.neoMotorPidP);
    pidController.setI(MotorConstants.neoMotorPidI);
    pidController.setD(MotorConstants.neoMotorPidD);
    pidController.setIZone(MotorConstants.neoMotorPidIZ);
    pidController.setFF(MotorConstants.neoMotorPidFF);
    pidController.setOutputRange(MotorConstants.neoMotorPidMinOutput, MotorConstants.neoMotorPidMaxOutput);
  }

  /**
   * Run motor at specified speed.
   * @param velocity
   */
  public void setVelocity(double velocity) {
    pidController.setReference(velocity, ControlType.kVelocity);
  }

  /**
   * Run motor at specified speed.
   * @param speed
   */
  public void setSpeed(double speed) {
    motor.set(speed);
  }

  /**
   * Stop the motor.
   */
  public void stop() {
    motor.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
