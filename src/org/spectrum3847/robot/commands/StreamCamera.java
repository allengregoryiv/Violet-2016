package org.spectrum3847.robot.commands;

import org.spectrum3847.lib.util.Debugger;
import org.spectrum3847.robot.Robot;

import com.ni.vision.VisionException;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class StreamCamera extends Command {

    public StreamCamera() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.cams);
    	setRunWhenDisabled(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	try {
	    	Robot.cams.streamActiveCamera();
	    	SmartDashboard.putString("Streaming Camera:", Robot.cams.getCurrentCamera());
    	} catch(VisionException e){
        	Debugger.println("Vision Exception: StreamCamera Init" + this.getName() + e);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	try {
    		Robot.cams.streamActiveCamera();
    	} catch(VisionException e){
        	Debugger.println("Vision Exception: StreamCamera Exec" + this.getName() + e);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
