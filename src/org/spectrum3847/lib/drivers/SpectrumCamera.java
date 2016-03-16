package org.spectrum3847.lib.drivers;

import org.spectrum3847.lib.util.Debugger;
import org.spectrum3847.robot.Robot;

import com.ni.vision.NIVision;
import com.ni.vision.VisionException;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class SpectrumCamera extends USBCamera {
	public int session;
    public Image frame;

	public SpectrumCamera(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public void setupCamera(){
    	try{
			frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	    	openCamera(); // open the camera connection
	    	startCapture(); // start the frame capturing process (internal to USBCamera)
	    	Debugger.println("Camera Setup", Robot.input, Debugger.error5);
    	} catch(VisionException e){
        	Debugger.println("Vision Exception: Setup Camera" + e);
        } catch(NullPointerException e){
        	Debugger.println("Null Pointer Exception: " + e);
        }
    }
	
	public void startCapture(){
    	try{
	    		super.startCapture();
    	} catch(VisionException e){
        	Debugger.println("Vision Exception: Start Capture" + e);
        } catch(NullPointerException e){
        	Debugger.println("Null Pointer Exception: " + e);
        }
    }
	
	public void stopCapture(){
    	try{
	    		super.stopCapture();
    	} catch(VisionException e){
        	Debugger.println("Vision Exception: Stop Capture" + e);
        } catch(NullPointerException e){
        	Debugger.println("Null Pointer Exception: " + e);
        }
    }
    
    public void streamCamera(){
    	//NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);
        //NIVision.IMAQdxGrab(Robot.session, Robot.frame, 1);
        //NIVision.imaqDrawShapeOnImage(Robot.frame, Robot.frame, rect,
        //        DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);
    	if (frame != null){
	        try{
	        	getImage(frame);  // retrieve a frame from the USBCamera class
	            CameraServer.getInstance().setImage(frame);  // push that frame to the SmartDashboard using the CamServer class
	        } catch(VisionException e){
	        	Debugger.println("Vision Exception: Stream Camera" + e);
	        } catch(NullPointerException e){
	        	Debugger.println("Null Pointer Exception: " + e);
	        }
    	}
    }
    
}
