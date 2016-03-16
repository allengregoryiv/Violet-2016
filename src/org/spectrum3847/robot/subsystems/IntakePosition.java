package org.spectrum3847.robot.subsystems;

import org.spectrum3847.lib.drivers.SpectrumSolenoid;
import org.spectrum3847.lib.util.Debugger;
import org.spectrum3847.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Allows us to easily create a class that is used to control a solenoid
 * @author JAG
 *
 */
public class IntakePosition extends Subsystem {

	private SpectrumSolenoid extendSol;
	private SpectrumSolenoid retractSol;
	private SpectrumSolenoid lockSol;
	private Boolean isExtended = false;
	private Boolean isDouble = false;
	private String name;
	
	public IntakePosition(String n, SpectrumSolenoid e, SpectrumSolenoid r, SpectrumSolenoid l) {
		super(n);
		name = n; 
		extendSol = e;
		retractSol = r;
		lockSol = l;
		isDouble = true;
	}
	
	public IntakePosition(String n, int e, int r, int l){
		this(n, new SpectrumSolenoid(e), new SpectrumSolenoid(r), new SpectrumSolenoid(l));
	}

	@Override
	protected void initDefaultCommand() {

	}
	
	public void extend(){
		extendSol.set(true);
		if (isDouble){
			retractSol.set(false);
		}
		isExtended = true;
		Debugger.println(name +": Extend", Robot.output, Debugger.info3);
		
	}

	public void retract(){
		extendSol.set(false);
		lockSol.set(true);
		retractSol.set(true);
		isExtended = false;
		Debugger.println(name +": Retract", Robot.output, Debugger.info3);
	}
	
	public void resetLock(){
		lockSol.set(false);
	}
	
	public boolean isExtened(){
		return isExtended;
	}
}
