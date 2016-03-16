package org.spectrum3847.robot.commands;

import org.spectrum3847.robot.subsystems.Intake;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class IntakeBall extends Command {
	private Intake intake;

    private static int t = 0;

    public IntakeBall(Intake in) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(in);
    	intake = in;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (!intake.isBall()){
    		intake.set(SmartDashboard.getNumber("Intake: Front Speed", 1), SmartDashboard.getNumber("Intake: Rear Speed" , 1));
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (intake.isBall()){
    		if (t >  SmartDashboard.getNumber("Intake: Ball Delay", 20)) {
                t = 0;
        		return true;
            }
            t++;
    	} 
    	
    	return false;
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
