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
		public static final double peakVelocityUnitsPer100ms = 153000.0;

		public static final int channel = 9;
		public static final int profileSlot = 0;
		public static final double pidP = 0.003;
		public static final double pidI = 0.00001;
		public static final double pidD = 0.03;
		public static final int pidIZ = 3000;
		public static final double pidFF = 1023.0 / peakVelocityUnitsPer100ms;
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

	public static final class Vex775proMotorConstants
	{
		public static final double peakVelocityUnitsPer100ms = 24000.0;

		public static final int channel = 9;
		public static final int profileSlot = 0;
		public static final double pidP = 0.003;
		public static final double pidI = 0.00001;
		public static final double pidD = 0.03;
		public static final int pidIZ = 3000;
		public static final double pidFF = 1023.0 / peakVelocityUnitsPer100ms;
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
		public static final String targetPercentageKey = "Target %";
		public static final double targetPercentageDefault = 0.50;
		public static final String targetVelocityKey = "Target Velocity";
		public static final double targetVelocityDefault = 1000;
		public static final String setVelocityKey = "Set Velocity";
		public static final String maxAltitudeLimitKey = "Max Altitude";
		public static final String minAltitudeLimitKey = "Min Altitude";
		public static final String altitudeMotorKey = "Altitude Motor";
		public static final String azimuthMotorKey = "Azimuth Motor";

		public static final String leftShooterTargetVelocityKey = "L Shooter Vel";
		public static final String rightShooterTargetVelocityKey = "R Shooter Vel";
		public static final String leftShooterSetVelocityKey = "L Shooter Vel Set";
		public static final String rightShooterSetVelocityKey = "R Shooter Vel Set";

		public static final String leftShooterTargetPercentageKey = "L Shooter %";
		public static final String rightShooterTargetPercentageKey = "R Shooter %";
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

		public static final int ledModePipeline = 0;
		public static final int ledModeOff = 1;
		public static final int ledModeOn = 3;

		public static final int pipelineDriver1 = 0;
		public static final int pipelineDriver2 = 1;
		public static final int pipelineDriver3 = 2;
		public static final int pipelineVision1 = 3;
		public static final int pipelineVision2 = 4;
		public static final int pipelineVision3 = 5;
		public static final int pipelineDefault = pipelineDriver1;

		// The desired targeting vision processing pipeline for each
		// pipeline. -1 represents no change.
		public static final int[] targetingPipelines =
		{
			pipelineVision1,
			pipelineVision2,
			pipelineVision3,
			-1,
			-1,
			-1,
			pipelineVision1,
			pipelineVision1,
			pipelineVision1,
			pipelineVision1
		};
	}

	public static final class ShooterConstants
	{
		public static final int altitudeMotorChannel = 20;
		public static final int azimuthMotorChannel = 21;
		public static final int leftMotorChannel = 22;
		public static final int rightMotorChannel = 23;
		public static final int maxAltitudeLimitChannel = 9;
		public static final int minAltitudeLimitChannel = 8;
		public static final double altitudeUpPercentageScaleFactor = 0.47;
		public static final double altitudeDownPercentageScaleFactor = 0.15;
		public static final double azimuthPercentageScaleFactor = 1.00;
	}

	public static final class TargetingConstants
	{
		public static final double maxAzimuthSpeed = 1.0;
		public static final double minAzimuthSpeed = 0.1;
		public static final double maxAzimuthDelta = 30.0;
		public static final double minAzimuthDelta = 0.5;
		public static final double azimuthTolerance = 0.5;

		// The altitude moves at different speeds in the different directions.
		// Hence the different max and min values per direction.
		// The arrays make it easier to get the desired speed based upon sign
		public static final double maxAltitudeSpeedUp = 1.0;
		public static final double maxAltitudeSpeedDown = -1.0;
		public static final double[] maxAltitudeSpeeds = { maxAltitudeSpeedDown, maxAltitudeSpeedUp };
		public static final double minAltitudeSpeedUp = 0.3;
		public static final double minAltitudeSpeedDown = -0.3;
		public static final double[] minAltitudeSpeeds = { minAltitudeSpeedDown, minAltitudeSpeedUp };
		public static final double maxAltitudeDeltaUp = 25.0;
		public static final double maxAltitudeDeltaDown = -25.0;
		public static final double[] maxAltitudeDeltas = { maxAltitudeDeltaDown, maxAltitudeDeltaUp };
		public static final double minAltitudeDeltaUp = 0.5;
		public static final double minAltitudeDeltaDown = -0.5;
		public static final double[] minAltitudeDeltas = { minAltitudeDeltaDown, minAltitudeDeltaUp };
		public static final double altitudeTolerance = 0.5;
	}
}
