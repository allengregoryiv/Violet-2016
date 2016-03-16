package org.spectrum3847.robot.commands;

import org.spectrum3847.lib.util.Debugger;
import org.spectrum3847.robot.subsystems.IntakePosition;
import org.spectrum3847.robot.subsystems.SolenoidSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeDown extends CommandBase {

	protected IntakePosition intakePosition;
	private static int t = 0;
	protected boolean extend = true;
	
	public IntakeDown(IntakePosition ip) {
		super("Solenoid Subsystem");
		intakePosition = ip;
		extend = false;
		requires(ip);
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		intakePosition.retract();
		Debugger.println("COMMAND: " + getName());
		t = 0;
	}

	@Override
	protected void execute() {
		if (t > 200) {     
            intakePosition.resetLock();;
        }
        t++;
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * When the command ends return it to the other position
	 */
	protected void end() {
		intakePosition.extend();

	}

	@Override
	protected void interrupted() {
		end();
	}

}
