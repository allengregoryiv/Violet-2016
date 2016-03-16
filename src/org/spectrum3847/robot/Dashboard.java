package org.spectrum3847.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * @author matthew, JAG
 */
public class Dashboard {
	
	public static final boolean ENABLE_DASHBOARD = true;
	
	
	static final double SHORT_DELAY = .015;
    static final double LONG_DELAY = 0.5;
    
    static double shortOldTime = 0.0;
    static double longOldTime = 0.0;


    
    public static void intializeDashboard() {
    	if(ENABLE_DASHBOARD){
    		//SmartDashboard.putBoolean("Relay", false);
    		//SmartDashboard.putNumber("Motor 1", 0);
    		SmartDashboard.putNumber("Drive: High Sensitivity", 0.2);
    		SmartDashboard.putNumber("Drive: Low Sensitivity", 0.8);
    		SmartDashboard.putNumber("Drive: High Quick Turn Sensitivity", 0.5);
    		SmartDashboard.putNumber("Drive: Low Quick Turn Sensitivity", 0.5);
    		SmartDashboard.putNumber("Intake: Front Speed", 1.0);
    		SmartDashboard.putNumber("Intake: Rear Speed", 0.6);
    		SmartDashboard.putNumber("Intake: Eject Front Speed", -1.0);
    		SmartDashboard.putNumber("Intake: Eject Rear Speed", -0.6);
    		SmartDashboard.putNumber("Intake: Portcullis Speed", -0.5);
    		SmartDashboard.putNumber("Intake: Feed Ball Speed", 1);
    		SmartDashboard.putNumber("Intake: Ball Delay", 10);
    		SmartDashboard.putNumber("Shooter: Layup Speed", 0.8);
    		SmartDashboard.putNumber("Shooter: Reverse Speed", -0.8);
    		SmartDashboard.putBoolean("Compressor", true);
    		SmartDashboard.putBoolean("Disabled Rear Camera?", false);	
    	}
    }

    private static void updatePutShort() {
    	//SmartDashboard.putNumber("Motor 1", Motor_1.get());
    	SmartDashboard.putNumber("Drive LeftY: ", HW.Driver_Gamepad.getLeftY());
    	SmartDashboard.putNumber("Drive RightX: ", HW.Driver_Gamepad.getRightX());
    	SmartDashboard.putNumber("Drive Trigger Left: ", HW.Driver_Gamepad.getLeftTrigger());
    	SmartDashboard.putNumber("Drive Trigger Right: ", HW.Driver_Gamepad.getRightTrigger());
    	SmartDashboard.putNumber("Drive Left:", Robot.leftDrive.get());
    	SmartDashboard.putNumber("Drive Right:", Robot.rightDrive.get());
    	SmartDashboard.putNumber("NavX Angle", Robot.navX.getAngle());
    	SmartDashboard.putNumber("NavX Roll", Robot.navX.getRoll());
    	SmartDashboard.putNumber("NavX Pitch", Robot.navX.getPitch());
    	SmartDashboard.putNumber("Left Encoder Value Raw", Robot.drive.m_left_encoder.getRaw());
    	SmartDashboard.putNumber("Right Encoder Value Raw", Robot.drive.m_right_encoder.getRaw());
    	SmartDashboard.putNumber("Left Encoder Value", Robot.drive.m_left_encoder.get());
    	SmartDashboard.putNumber("Right Encoder Value", Robot.drive.m_right_encoder.get());
    	//Robot.navX.DataMonitor();
    	
    }
    
    private static void updatePutLong(){
    	SmartDashboard.putNumber("Compressor Current", Robot.compressor.getCompressorCurrent());
    	SmartDashboard.putNumber("Drive Left Current:", Robot.leftDrive.getSignedCurrent());
    	SmartDashboard.putNumber("Drive Right Current:", Robot.rightDrive.getSignedCurrent());
    	SmartDashboard.putBoolean("Is Ball?", Robot.intake.isBall());
    }

    
    public static void updateDashboard() {
    	if (ENABLE_DASHBOARD) {
            if ((Timer.getFPGATimestamp() - shortOldTime) > SHORT_DELAY) {
                shortOldTime = Timer.getFPGATimestamp();
                updatePutShort();
            }
            if ((Timer.getFPGATimestamp() - longOldTime) > LONG_DELAY) {
                //Thing that should be updated every LONG_DELAY
                longOldTime = Timer.getFPGATimestamp();
                updatePutLong();
            }
        }
    }
}
