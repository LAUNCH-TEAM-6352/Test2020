/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final class MotorConstants {
        public static final int neoMotorChannel = 1;
        public static final double neoMotorPidP = 0.00005;
        public static final double neoMotorPidI = 0.000001;
        public static final double neoMotorPidD = 0.0;
        public static final double neoMotorPidIZ = 0.0;
        public static final double neoMotorPidFF = 0.0;
        public static final double neoMotorPidMinOutput = -1.0;
        public static final double neoMotorPidMaxOutput = 1.0;

        public static final int falcon500MotorChannel = 2;
        public static final int falcon500MotorProfileSlot = 0;
        public static final int falcon500MotorCountsPerRevolution = 2048;
        public static final int falcon500MotorPrimaryClosedLoop = 0;
    }
    
    public static final class OIConstants {
        public static final int xboxControllerPort = 1;
      }

    public static final class DashboardConstants {
        public static final String motorVeolcityKey = "Motor Veolcity";
        public static final double motorVeolcityDefault = 1000;
    }
}
