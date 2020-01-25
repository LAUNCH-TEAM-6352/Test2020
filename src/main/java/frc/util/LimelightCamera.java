/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants.LimelightConstants;

/**
 * This is a singleton for accessing the Limelight camera.
 */
public class LimelightCamera
{
	private static LimelightCamera instance;
	private static final String tableName = "limelight";

	// The following are for accessing the network table entries used by the Limelight:
	private NetworkTable limelightTable;
	private NetworkTableEntry pipelineEntry;
	private NetworkTableEntry xPositionEntry;
	private NetworkTableEntry yPositionEntry;
	private NetworkTableEntry areaEntry;
	private NetworkTableEntry camModeEntry;
	private NetworkTableEntry ledModeEntry;
	private NetworkTableEntry targetAcquiredEntry;
	

	public synchronized static LimelightCamera getInstance()
	{
		
		if (instance == null)
		{
			instance = new LimelightCamera();
		}

		return instance;
	}

	/**
	 * Constructor i=s private as the only way to get an instance
	 * is through the getInstance() static methoid.
	 */
	private LimelightCamera()
	{
		limelightTable = NetworkTableInstance.getDefault().getTable(LimelightConstants.tableName);

		pipelineEntry = limelightTable.getEntry(LimelightConstants.pipelineEntryName);
		xPositionEntry = limelightTable.getEntry(LimelightConstants.xPositionEntryName);
		yPositionEntry = limelightTable.getEntry(LimelightConstants.yPositionEntryName);
		areaEntry = limelightTable.getEntry(LimelightConstants.areaEntryName);
		camModeEntry = limelightTable.getEntry(LimelightConstants.camModeEntryName);
		ledModeEntry = limelightTable.getEntry(LimelightConstants.ledModeEntryName);
		targetAcquiredEntry = limelightTable.getEntry(LimelightConstants.targetAcquiredEntryName);
	}

	/**
	 * Determines if the Limelight hs acquired a target.
	 */
	public boolean isTargetAcquired()
	{
		return targetAcquiredEntry.getDouble(0) != 0;
	}
	
	/**
	 * Returns the x coordinate of the center of the target.
	 */
	public double getTargetXPosition()
	{
		return xPositionEntry.getDouble(0.0);
	}
	
	/**
	 * Returns the y coordinate of the center of the target.
	 */
	public double getTargetYPosition()
	{
		return yPositionEntry.getDouble(0.0);
	}

	/**
	 * Returns the target area as a percentage of the total image area.
	 */
	public double getTargetArea()
	{
		return areaEntry.getDouble(0);
	}

	/**
	 * Sets the camera id driver mode.
	 */
	public void setDriveMode()
	{
		camModeEntry.setDouble(LimelightConstants.camModeDriver);
		ledOff();
	}

	/**
	 * Sets the camera in vision processing mode.
	 */
	public void setVisionProcessingMode()
	{
		camModeEntry.setDouble(LimelightConstants.camModeVsionProcessing);
		ledOn();
	}

	/**
	 * Toggles the vision processing mode:
	 */
	public void toggleVisionProcessingMode()
	{
		switch ((int) camModeEntry.getDouble(0))
		{
			case LimelightConstants.camModeDriver:
				setVisionProcessingMode();
				break;

			default:
				setDriveMode();
		}
	}

	/**
	 * Turns on the LEDs.
	 */
	public void ledOn()
	{
		ledModeEntry.setDouble(LimelightConstants.ledModeOn);
	}

	/**
	 * Turns off the LEDs.
	 */
	public void ledOff()
	{
		ledModeEntry.setDouble(LimelightConstants.ledModeOff);
	}

	/**
	 * Toggles the state of the LED.
	 */
	public void toggleLed()
	{
		switch ((int) ledModeEntry.getDouble(0))
		{
			case LimelightConstants.ledModeOff:
				ledOn();
				break;

			default:
				ledOff();
		}
	}

	/**
	 * Set the camera in zoomed in state.
	 */
	public void zoomIn()
	{
		pipelineEntry.setDouble(LimelightConstants.pipelineZoomedIn);
	}

	/**
	 * Set the camera in zoomed out state.
	 */
	public void zoomOut()
	{
		pipelineEntry.setDouble(LimelightConstants.pipelineZoomedOut);
	}

	/**
	 * Toggles the zoom state.
	 */
	public void toggleZoom()
	{
		switch ((int) pipelineEntry.getDouble(0))
		{
			case LimelightConstants.pipelineZoomedIn:
				zoomOut();
				break;

			default:
				zoomIn();
		}
	}
}
