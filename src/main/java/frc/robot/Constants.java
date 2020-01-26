/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants
{

	public static final class NeoMotorConstants
	{
		public static final int channel = 1;
		public static final double pidP = 0.00005;
		public static final double pidI = 0.000001;
		public static final double pidD = 0.0;
		public static final double pidIZ = 0.0;
		public static final double pidFF = 0.0;
		public static final double pidMinOutput = -1.0;
		public static final double pidMaxOutput = 1.0;
	}

	public static final class Falcon500MotorConstants
	{
		public static final int channel = 2;
		public static final int profileSlot = 0;
		public static final double pidP = 0.0499999523;
		public static final double pidI = 9.98973846E-05;
		public static final double pidD = 0.0;
		public static final int pidIZ = 300;
		public static final double pidFF = 1023.0 / 7200.0;
		public static final double pidPeakOutput = 1;
		public static final int pidLoopPeriodMs = 1;
		public static final double pidMaxIntegralAccum = 0;
		public static final int pidAllowableError = 0;
		public static final int pidTimeoutMs = 30;
		public static final double pidMinOutput = -1.0;
		public static final double idMaxOutput = 1.0;

		public static final int countsPerRevolution = 2048;
		public static final int primaryClosedLoop = 0;
	}

	public static final class RedlineMotorConstants
	{
		public static final double peakVelocityUnitesPer100ms = 153000.0;

		public static final int channel = 9;
		public static final int profileSlot = 0;
		public static final double pidP = 0.003;
		public static final double pidI = 0.00001;
		public static final double pidD = 0.03;
		public static final int pidIZ = 3000;
		public static final double pidFF = 1023.0 / peakVelocityUnitesPer100ms;
		public static final double pidPeakOutput = 1;
		public static final int pidLoopPeriodMs = 1;
		public static final double pidMaxIntegralAccum = 0;
		public static final int pidAllowableError = 0;
		public static final int pidTimeoutMs = 30;
		public static final double pidMinOutput = -1.0;
		public static final double idMaxOutput = 1.0;


		public static final int countsPerRevolution = 1024;
		public static final int ticksPerCount = 4;
		public static final int primaryClosedLoop = 0;


		public static final boolean phase = false;
	}

	public static final class OIConstants
	{
		public static final int xboxControllerPort = 1;
	}

	public static final class DashboardConstants
	{
		public static final String targetVelocityKey = "Target Veolcity";
		public static final double targetVelocityDefault = 1000;
		public static final String setVelocityKey = "Set Veolcity";
	}

	public static final class LimelightConstants
	{
		public static final String tableName = "limelight";
		public static final String pipelineEntryName = "pipeline";
		public static final String xPositionEntryName = "tx";
		public static final String yPositionEntryName = "ty";
		public static final String targetAcquiredEntryName = "tv";
		public static final String areaEntryName = "ta";
		public static final String camModeEntryName = "camMode";
		public static final String ledModeEntryName = "ledMode";

		public static final int camModeVsionProcessing = 0;
		public static final int camModeDriver = 1;

		public static final int ledModePipeline = 0;
		public static final int ledModeOff = 1;
		public static final int ledModeOn = 3;

		public static final int pipelineZoomedOut = 0;
		public static final int pipelineZoomedIn = 1;
	}
}
