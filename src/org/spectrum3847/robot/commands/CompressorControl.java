package org.spectrum3847.robot.commands;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class CompressorControl extends Command {
	Compressor compressor;

    public CompressorControl(Compressor comp) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	compressor = comp;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (!SmartDashboard.getBoolean("Compressor")){
    		compressor.stop();
    	} else {
    		compressor.start();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
		compressor.start();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
