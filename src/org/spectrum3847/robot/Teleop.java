package org.spectrum3847.robot;

import org.spectrum3847.lib.drivers.CheesyDriveHelper;
import org.spectrum3847.lib.drivers.Gamepad;
import org.spectrum3847.robot.commands.CompressorControl;
import edu.wpi.first.wpilibj.command.Scheduler;


/**
 * The Driver Control period of the competition
 */
public class Teleop {
	
    public static void init() {
        Scheduler.getInstance().removeAll();
        CompressorControl compressorControl = new CompressorControl(Robot.compressor);
        compressorControl.start();
    }

    public static void periodic() {

    	Dashboard.updateDashboard();
        Scheduler.getInstance().run();
        //Robot.compressor.stop();
        
        //Tank Drive
        //Robot.drive.setOpenLoop(new DriveSignal(HW.Driver_Gamepad.getLeftY(), HW.Driver_Gamepad.getRightY()));
        
        CheesyDriveHelper cheesy = new CheesyDriveHelper(Robot.drive);
        double leftTrigger = HW.Driver_Gamepad.getRawAxis(Gamepad.LeftTrigger);
        double rightTrigger = HW.Driver_Gamepad.getRawAxis(Gamepad.RightTrigger);
        if (leftTrigger > 0 || rightTrigger > 0) {
            cheesy.cheesyDrive(HW.Driver_Gamepad.getLeftY(), leftTrigger - rightTrigger, true, Robot.shiftSol.isExtened());
        } else {
            cheesy.cheesyDrive(HW.Driver_Gamepad.getLeftY(), -1 * HW.Driver_Gamepad.getRightX(), false, Robot.shiftSol.isExtened());
        }
        
    }

    public static void cancel() {
        Scheduler.getInstance().removeAll();
    }
}
