/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.MotorConstants;

public class Falcon500MotorSubsystem extends SubsystemBase {
  private TalonFX motor = new TalonFX(MotorConstants.falcon500MotorChannel);

  /**
   * Creates a new ExampleSubsystem.
   */
  public Falcon500MotorSubsystem() {
    motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    motor.selectProfileSlot(MotorConstants.falcon500MotorProfileSlot, MotorConstants.falcon500MotorPrimaryClosedLoop);
  }

  /**
   * Run motor at specified velocity in RPM.
   * @param velocity
   */
  public void setVelocity(double velocity) {
    // Encoder measures 2048 units per rev. Velocity is measured in units per 100 ms.
    var unitsPer100Ms = velocity * MotorConstants.falcon500MotorCountsPerRevolution / 600;
    motor.set(ControlMode.Velocity, unitsPer100Ms);
  }

  /**
   * Run motor at specified speed.
   * @param speed
   */
  public void setSpeed(double speed) {
    motor.set(ControlMode.PercentOutput, speed);
  }

  /**
   * Stop the motor.
   */
  public void stop() {
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
