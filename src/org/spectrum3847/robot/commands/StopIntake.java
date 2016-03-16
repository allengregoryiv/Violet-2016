package org.spectrum3847.robot.commands;

import org.spectrum3847.robot.subsystems.Intake;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class StopIntake extends Command {
	private Intake intake;

    public StopIntake(Intake in) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(in);
    	intake = in;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	intake.set(0.0, 0.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	intake.set(0.0, 0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
