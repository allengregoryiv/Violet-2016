
package org.spectrum3847.robot;

import org.spectrum3847.lib.drivers.SpecAHRS;
import org.spectrum3847.lib.drivers.SpectrumEncoder;
import org.spectrum3847.lib.drivers.SpectrumSpeedController;
import org.spectrum3847.lib.drivers.SpectrumSpeedControllerCAN;
import org.spectrum3847.lib.util.Debugger;
import org.spectrum3847.robot.subsystems.Cameras;
import org.spectrum3847.robot.subsystems.Drive;
import org.spectrum3847.robot.subsystems.Intake;
import org.spectrum3847.robot.subsystems.IntakePosition;
import org.spectrum3847.robot.subsystems.MotorSubsystem;
import org.spectrum3847.robot.subsystems.ShooterNoPID;
import org.spectrum3847.robot.subsystems.SolenoidSubsystem;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.I2C;;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
    //Add Debug flags
	//You can have a flag for each subsystem, etc
	public static final String output = "OUT";
	public static final String input = "IN";
	public static final String controls = "CONTROL";
	public static final String general = "GENERAL";
	public static final String auton = "AUTON";
	public static final String commands = "COMMAND";
	
	// Create a single static instance of all of your subsystems
    // This MUST be here. If the OI creates Commands (which it very likely
    // will), constructing it during the construction of CommandBase (from
    // which commands extend), subsystems are not guaranteed to be
    // yet. Thus, their requires() statements may grab null pointers. Bad
    // news. Don't move it.
	
	public static SpectrumSpeedController rightDrive;
	public static SpectrumSpeedController leftDrive;
	public static Drive drive; 
	public static SolenoidSubsystem shiftSol;
	public static Intake intake;
	public static IntakePosition intakeSol;
	public static SpectrumSpeedController shooterMotors;
	public static ShooterNoPID shooter;
	public static MotorSubsystem catTail;
	public static Compressor compressor;
	public static Cameras cams;
	public static SpecAHRS navX;
	
    public static void setupSubsystems(){
    	compressor = new Compressor(0);
    	
    	//Setup Drive Motor Speed Controllers
    	Spark LR_CIM = new Spark(HW.LEFT_REAR_DRIVE_MOTOR_8);
    	Spark LF_CIM = new Spark(HW.LEFT_FRONT_DRIVE_MOTOR_9);
    	Spark RR_CIM = new Spark(HW.RIGHT_REAR_DRIVE_MOTOR_0);
    	Spark RF_CIM = new Spark(HW.RIGHT_FRONT_DRIVE_MOTOR_1);
    	
    	rightDrive = new SpectrumSpeedController(
    				new SpeedController[] {LR_CIM,LF_CIM}, 
    				new int[] {HW.LEFT_REAR_DRIVE_MOTOR_PDP, HW.LEFT_FRONT_DRIVE_MOTOR_PDP}
    			);
    	rightDrive.setInverted(true);
    	
    	leftDrive = new SpectrumSpeedController(
    			new SpeedController[] {RR_CIM, RF_CIM}, 
    			new int[] {HW.RIGHT_REAR_DRIVE_MOTOR_PDP, HW.RIGHT_FRONT_DRIVE_MOTOR_PDP}
    			);
    	leftDrive.setInverted(true);
    	
    	drive = new Drive("defaultDrive",
							leftDrive, 
    						rightDrive,  
    						new SpectrumEncoder(HW.LEFT_ENCODER_0, HW.LEFT_ENCODER_1, 240), new SpectrumEncoder(HW.RIGHT_ENCODER_8, HW.RIGHT_ENCODER_9, 240)
    						);
    	
    	CANTalon shooter1 = new CANTalon(HW.SHOOTER_MOTOR_1_3);
    	shooter1.setInverted(true);
    	CANTalon shooter2 = new CANTalon(HW.SHOOTER_MOTOR_2_4);
    	
    	shooterMotors = new SpectrumSpeedController(
    		new SpeedController[] {shooter1, shooter2}, 
    		new int[] {HW.SHOOTER_1_PDP, HW.SHOOTER_2_PDP}
    	);
    	
    	
    	shooter = new ShooterNoPID("Shooter", shooterMotors);
    	
    	catTail = new MotorSubsystem("Cat Tail", HW.CAT_TAIL_MOTOR_5, HW.CAT_TAIL_PDP, 0.5, -0.5);
    	
    	//Setup a Solenoid Subsystem and give it an initial state
    	shiftSol = new SolenoidSubsystem("Shift Solenoid", HW.SHIFTING_SOL_HIGH_5);
    	shiftSol.retract();
    	intakeSol = new IntakePosition("Intake Position", HW.INTAKE_SOL_UP_6, HW.INTAKE_SOL_DOWN_1, HW.INTAKE_LOCK_2);
    	intakeSol.extend();
    	
    	SpectrumSpeedControllerCAN in775 = new SpectrumSpeedControllerCAN(new CANTalon(HW.INTAKE_775_1), HW.INTAKE_775_PDP);
    	SpectrumSpeedControllerCAN inBAG = new SpectrumSpeedControllerCAN(new CANTalon(HW.INTAKE_BAG_2), HW.INTAKE_BAG_PDP);
    	
    	intake = new Intake("Intake", in775, inBAG, HW.BALL_SENSOR_2 );
    	cams = new Cameras();
    	try {
            /* Communicate w/navX MXP via the MXP SPI Bus.                                     */
            /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
            /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
            navX = new SpecAHRS(I2C.Port.kMXP); 
            Debugger.println("NavX Setup Success", Robot.input, Debugger.error5);
        } catch (RuntimeException ex ) {
            Debugger.println("Error instantiating navX MXP:  " + ex.getMessage(), Robot.input, Debugger.error5);
        }
    }
    
    //Used to keep track of the robot current state easily
    public enum RobotState {
        DISABLED, AUTONOMOUS, TELEOP
    }

    public static RobotState s_robot_state = RobotState.DISABLED;

    public static RobotState getState() {
        return s_robot_state;
    }

    public static void setState(RobotState state) {
        s_robot_state = state;
    }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	initDebugger();
    	printGeneralInfo("Start robotInit()");
    	setupSubsystems(); //This has to be before the OI is created on the next line
        Dashboard.intializeDashboard();
		HW.oi = new OI();
    }
    
    private static void initDebugger(){
    	Debugger.setLevel(Debugger.info3); //Set the initial Debugger Level
    	Debugger.flagOn(general); //Set all the flags on, comment out ones you want off
    	Debugger.flagOn(controls);
    	Debugger.flagOn(input);
    	Debugger.flagOn(output);
    	Debugger.flagOn(auton);
    	Debugger.flagOn(commands);
    }
    /**
     * Initialization code for test mode should go here.
     *
     * Users should override this method for initialization code which will be called each time
     * the robot enters test mode.
     */
    public void testInit() {
    	compressor.startLiveWindowMode();
    	compressor.setClosedLoopControl(false);
    }
    
    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){
        setState(RobotState.DISABLED);
        printGeneralInfo("Start disabledInit()");
        Disabled.init();
        printGeneralInfo("End disableInit()");
    }
    /**
     * This function is called while in disabled mode.
     */    
    public void disabledPeriodic(){
    	Disabled.periodic();
    }


    public void autonomousInit() {
    	setState(RobotState.AUTONOMOUS);
    	printGeneralInfo("Start autonomousInit()");
        Autonomous.init();
        printGeneralInfo("End autonomousInit()");
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Autonomous.periodic();
    }

    public void teleopInit() {
    	setState(RobotState.TELEOP);
    	printGeneralInfo("Start teleopInit()");
        Teleop.init();
        printGeneralInfo("End teleopInit()");
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Teleop.periodic();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    public static void printGeneralInfo(String msg){
    	Debugger.println(msg, general, Debugger.info3);
    }
}
