package org.spectrum3847.robot.subsystems;

import org.spectrum3847.lib.drivers.SpectrumCamera;
import org.spectrum3847.lib.util.Debugger;
import org.spectrum3847.robot.HW;
import org.spectrum3847.robot.Robot;
import org.spectrum3847.robot.commands.StreamCamera;

import com.ni.vision.VisionException;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Cameras extends Subsystem {
	private SpectrumCamera frontCamera;
	private SpectrumCamera rearCamera;
	private boolean frontCaptureOn = true;
	private boolean rearCaptureOn = false;
	private boolean isFront = true;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public Cameras(){
		super("Cameras");
		Debugger.println("Setting Up Front Camera", Robot.input, Debugger.fatal6);
		//Setup Front Camera
    	try{
	    	frontCamera = new SpectrumCamera(HW.FRONT_CAMERA);
	    	if (frontCamera != null){
	    		//frontCamera.setFPS(10);
	    		frontCamera.setSize(640, 480);
	    		//frontCamera.setExposureManual(30);
	    		frontCamera.setExposureAuto();
	    		frontCamera.setWhiteBalanceAuto();
	    		frontCamera.setBrightness(50);
	    		frontCamera.setupCamera();
	    	}
    	} catch(VisionException e){
        	Debugger.println("Vision Exception: Cameras Constructor" + e);
    	} catch(NullPointerException e){
        	Debugger.println("Null Pointer Exception: Front Camera Declare" + e);
        }

		Debugger.println("Setting Up Rear Camera", Robot.input, Debugger.fatal6);
    	//Setup Rear Camera
    	try{	    	 	
	    	rearCamera = new SpectrumCamera(HW.REAR_CAMERA);
	    	if (rearCamera != null){
	    		//rearCamera.setFPS(10);
	    		//rearCamera.setExposureManual(50);
	    		//rearCamera.setBrightness(50);
	    		rearCamera.setSize(640, 480);
		    	rearCamera.setupCamera();
	    	}
		} catch(VisionException e){
	    	Debugger.println("Vision Exception: Cameras Constructor" + e);
		} catch(NullPointerException e){
	    	Debugger.println("Null Pointer Exception: Rear Camera Declare" + e);
    }
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new StreamCamera());
    }
    
    private void streamFrontCamera(){
    	if (frontCamera != null){
	    	try{
	    		frontCamera.streamCamera();
	    		isFront = true;
	    	} catch(VisionException e){
	    		Debugger.println("Vision Exception: Stream Front Camera" + this.getName() + e);
	    	} catch(NullPointerException e){
	        	Debugger.println("Null Pointer Exception: Stream Front" + e);
	        }
    	}
    }
    
    private void streamRearCamera(){
    	if (rearCamera != null){
	    	try{
		    	rearCamera.streamCamera();
		    	isFront = false;
			} catch(VisionException e){
				Debugger.println("Vision Exception: Stream Rear Camera" + this.getName() + e);
			} catch(NullPointerException e){
		    	Debugger.println("Null Pointer Exception: Stream Rear" + e);
			}
    	}
    }
    
    public boolean isFront(){
    	return isFront;
    }
    
    public void setIsFront (boolean b){
    	isFront = b;
    }
    
    public String getCurrentCamera(){
    	if (isFront){
    		return "FRONT";
    	} else {
    		return "REAR";
    	}
    }
    
    public void streamCamerasWhileDisabled(){
        if (SmartDashboard.getBoolean("Disabled Rear Camera?")){
        	Robot.cams.setIsFront(false);
        	if(frontCaptureOn){
        		startRearCapture();
        		SmartDashboard.putString("Streaming Camera:", "REAR");
        	}
        } else {
        	Robot.cams.setIsFront(true);
        	if(rearCaptureOn){
        		startFrontCapture();
        		SmartDashboard.putString("Streaming Camera:", "FRONT");
        	}
        }
        Robot.cams.streamActiveCamera();
    }
    
    public void toggleCamera(){

	    	if (isFront){
	    		isFront = false;
	        	try{
		    		startRearCapture();
		    		SmartDashboard.putString("Streaming Camera:", "REAR");
	        	 } catch(VisionException e){
	     	    	Debugger.println("Vision Exception at ToggleCamera: " + e);
	     		} catch(NullPointerException e){
	     	    	Debugger.println("Null Pointer Exception at ToggleCamera: " + e);
	     	    }
	    	} else {
	    		isFront = true;
	    		try{
		    		startFrontCapture();
		    		SmartDashboard.putString("Streaming Camera:", "FRONT");
	    		} catch(VisionException e){
	     	    	Debugger.println("Vision Exception at ToggleCamera: " + e);
	     		} catch(NullPointerException e){
	     	    	Debugger.println("Null Pointer Exception at ToggleCamera: " + e);
	     		}
	    	}
    }
    
    public void streamActiveCamera(){
    	if (isFront){
    		streamFrontCamera();
    	} else {
    		streamRearCamera();
    	}
    }
    
    private void startFrontCapture(){
    	if (rearCamera != null){
    		rearCamera.stopCapture();
    	}
    	if (frontCamera != null){
    		frontCamera.startCapture();
    	}
    	rearCaptureOn = false;
    	frontCaptureOn = true;
    }
    
    private void startRearCapture(){
    	if (frontCamera != null){
    		frontCamera.stopCapture();
    	}
    	if (rearCamera != null){
    		rearCamera.startCapture();
    	}
    	frontCaptureOn = false;
    	rearCaptureOn = true;
    }
}

