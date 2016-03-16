package org.spectrum3847.robot.subsystems;

import org.spectrum3847.lib.drivers.SpectrumSpeedControllerCAN;
import org.spectrum3847.lib.util.Debugger;
import org.spectrum3847.lib.util.Util;
import org.spectrum3847.robot.Robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {

	private SpectrumSpeedControllerCAN intakeFront;
	private SpectrumSpeedControllerCAN intakeRear;
	private DigitalInput ballSensor;
	private double FrontMax = 1;
	private double FrontMin = -1;
	private double RearMax = 1;
	private double RearMin = -1;
	private double maxFrontCurrentFwd = 10000;
	private double maxFrontCurrentRev = -10000;
	private double maxRearCurrentFwd = 10000;
	private double maxRearCurrentRev = -10000;
	private boolean currentLimit = false;
	
	public Intake(String n, SpectrumSpeedControllerCAN in_775, SpectrumSpeedControllerCAN in_BAG, int sensor){
		super(n);
		intakeFront = in_775;
		intakeRear = in_BAG;
		ballSensor = new DigitalInput(sensor);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
	public void set(double FrontV, double RearV){
		FrontV = Util.limit(FrontV, FrontMax, FrontMin);
		RearV = Util.limit(RearV, RearMax, RearMin);
		
		//Check the current limit if it is enabled
		if (currentLimit){
			double current = getFrontCurrent();
			if (current > maxFrontCurrentFwd && FrontV > 0){
				FrontV = 0;
			} else if (current < maxFrontCurrentRev && FrontV < 0){
				FrontV = 0;
			}
			current = getRearCurrent();
			if (current > maxRearCurrentFwd && RearV > 0){
				RearV = 0;
			} else if (current < maxRearCurrentRev && RearV < 0){
				RearV = 0;
			}
		}
		
		intakeFront.set(FrontV);
		intakeRear.set(RearV);
		Debugger.println("INTAKE: Front: " + FrontV + " Rear: " + RearV, Robot.output, Debugger.debug2);
	}
	
	public void setFrontMax(double m){
		FrontMax = m;
	}
	
	public void setFrontMin(double m){
		FrontMin = m;
	}
	
	public void setRearMax(double m){
		RearMax = m;
	}
	
	public void setRearMin(double m){
		RearMin = m;
	}
	
	//Set the max fwd current
	public void setMaxFrontCurrentFwd(double c){
		maxFrontCurrentFwd = c;
		currentLimit = true;
	}
	
	//Set the max rev current, SHOULD BE NEGATIVE
	public void setMaxFrontCurrentRev(double c){
		maxFrontCurrentRev = c;
		currentLimit = true;
	}
	
	//Set the max fwd current
	public void setMaxRearCurrentFwd(double c){
		maxRearCurrentFwd = c;
		currentLimit = true;
	}
	
	//Set the max rev current, SHOULD BE NEGATIVE
	public void setMinRearCurrentRev(double c){
		maxRearCurrentRev = c;
		currentLimit = true;
	}
	
	public void disableCurrentLimit(){
		currentLimit = false;
	}
	
	public void enableCurrentLimit (){
		currentLimit = true;
	}
	
	public double getFrontSpeed(){
		return intakeFront.get();
	}
	
	public double getRearSpeed(){
		return intakeFront.get();
	}
	
	public double getFrontCurrent(){
		return intakeFront.getCurrent();
	}
	
	public double getRearCurrent(){
		return intakeRear.getCurrent();
	}
	
	public void setInverted_Front(boolean value){
		intakeFront.setInverted(value);
	}
	
	public void setInverted_Rear(boolean value){
		intakeRear.setInverted(value);
	}
	
	public void disable(){
		intakeFront.disable();
		intakeRear.disable();
	}
	
	public boolean isBall(){
		return !ballSensor.get();
	}

}
