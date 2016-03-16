package org.spectrum3847.lib.drivers;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SpecAHRS extends AHRS {

	public SpecAHRS(Port serial_port_id) {
		super(serial_port_id);
	}

	public SpecAHRS(edu.wpi.first.wpilibj.I2C.Port serial_port_id) {
		super(serial_port_id);
	}

	public SpecAHRS(edu.wpi.first.wpilibj.SerialPort.Port serial_port_id) {
		super(serial_port_id);
	}

	public SpecAHRS(Port i2c_port_id, byte update_rate_hz) {
		super(i2c_port_id, update_rate_hz);
	}

	public SpecAHRS(edu.wpi.first.wpilibj.I2C.Port i2c_port_id, byte update_rate_hz) {
		super(i2c_port_id, update_rate_hz);
	}

	public SpecAHRS(Port spi_port_id, int spi_bitrate, byte update_rate_hz) {
		super(spi_port_id, spi_bitrate, update_rate_hz);
	}

	public SpecAHRS(edu.wpi.first.wpilibj.SerialPort.Port serial_port_id, SerialDataType data_type,
			byte update_rate_hz) {
		super(serial_port_id, data_type, update_rate_hz);
	}
	
	public void DataMonitor(){
		/* Display 6-axis Processed Angle Data                                      */
        SmartDashboard.putBoolean(  "IMU_Connected",        isConnected());
        SmartDashboard.putBoolean(  "IMU_IsCalibrating",    isCalibrating());
        SmartDashboard.putNumber(   "IMU_Yaw",              getYaw());
        SmartDashboard.putNumber(   "IMU_Pitch",            getPitch());
        SmartDashboard.putNumber(   "IMU_Roll",             this.getRoll());
        
        /* Display tilt-corrected, Magnetometer-based heading (requires             */
        /* magnetometer calibration to be useful)                                   */
        
        SmartDashboard.putNumber(   "IMU_CompassHeading",   this.getCompassHeading());
        
        /* Display 9-axis Heading (requires magnetometer calibration to be useful)  */
        SmartDashboard.putNumber(   "IMU_FusedHeading",     this.getFusedHeading());

        /* These functions are compatible w/the WPI Gyro Class, providing a simple  */
        /* path for upgrading from the Kit-of-Parts gyro to the navx MXP            */
        
        SmartDashboard.putNumber(   "IMU_TotalYaw",         this.getAngle());
        SmartDashboard.putNumber(   "IMU_YawRateDPS",       this.getRate());

        /* Display Processed Acceleration Data (Linear Acceleration, Motion Detect) */
        
        SmartDashboard.putNumber(   "IMU_Accel_X",          this.getWorldLinearAccelX());
        SmartDashboard.putNumber(   "IMU_Accel_Y",          this.getWorldLinearAccelY());
        SmartDashboard.putBoolean(  "IMU_IsMoving",         this.isMoving());
        SmartDashboard.putBoolean(  "IMU_IsRotating",       this.isRotating());

        /* Display estimates of velocity/displacement.  Note that these values are  */
        /* not expected to be accurate enough for estimating robot position on a    */
        /* FIRST FRC Robotics Field, due to accelerometer noise and the compounding */
        /* of these errors due to single (velocity) integration and especially      */
        /* double (displacement) integration.                                       */
        
        SmartDashboard.putNumber(   "Velocity_X",           this.getVelocityX());
        SmartDashboard.putNumber(   "Velocity_Y",           this.getVelocityY());
        SmartDashboard.putNumber(   "Displacement_X",       this.getDisplacementX());
        SmartDashboard.putNumber(   "Displacement_Y",       this.getDisplacementY());
        
        /* Display Raw Gyro/Accelerometer/Magnetometer Values                       */
        /* NOTE:  These values are not normally necessary, but are made available   */
        /* for advanced users.  Before using this data, please consider whether     */
        /* the processed data (see above) will suit your needs.                     */
        
        SmartDashboard.putNumber(   "RawGyro_X",            this.getRawGyroX());
        SmartDashboard.putNumber(   "RawGyro_Y",            this.getRawGyroY());
        SmartDashboard.putNumber(   "RawGyro_Z",            this.getRawGyroZ());
        SmartDashboard.putNumber(   "RawAccel_X",           this.getRawAccelX());
        SmartDashboard.putNumber(   "RawAccel_Y",           this.getRawAccelY());
        SmartDashboard.putNumber(   "RawAccel_Z",           this.getRawAccelZ());
        SmartDashboard.putNumber(   "RawMag_X",             this.getRawMagX());
        SmartDashboard.putNumber(   "RawMag_Y",             this.getRawMagY());
        SmartDashboard.putNumber(   "RawMag_Z",             this.getRawMagZ());
        SmartDashboard.putNumber(   "IMU_Temp_C",           this.getTempC());
        
        /* Omnimount Yaw Axis Information                                           */
        /* For more info, see http://navx-mxp.kauailabs.com/installation/omnimount  */
        AHRS.BoardYawAxis yaw_axis = this.getBoardYawAxis();
        SmartDashboard.putString(   "YawAxisDirection",     yaw_axis.up ? "Up" : "Down" );
        SmartDashboard.putNumber(   "YawAxis",              yaw_axis.board_axis.getValue() );
        
        /* Sensor Board Information                                                 */
        SmartDashboard.putString(   "FirmwareVersion",      this.getFirmwareVersion());
        
        /* Quaternion Data                                                          */
        /* Quaternions are fascinating, and are the most compact representation of  */
        /* orientation data.  All of the Yaw, Pitch and Roll Values can be derived  */
        /* from the Quaternions.  If interested in motion processing, knowledge of  */
        /* Quaternions is highly recommended.                                       */
        SmartDashboard.putNumber(   "QuaternionW",          this.getQuaternionW());
        SmartDashboard.putNumber(   "QuaternionX",          this.getQuaternionX());
        SmartDashboard.putNumber(   "QuaternionY",          this.getQuaternionY());
        SmartDashboard.putNumber(   "QuaternionZ",          this.getQuaternionZ());
        
        /* Sensor Data Timestamp */
        SmartDashboard.putNumber(   "SensorTimestamp",		this.getLastSensorTimestamp());
        
        /* Connectivity Debugging Support                                           */
        SmartDashboard.putNumber(   "IMU_Byte_Count",       this.getByteCount());
        SmartDashboard.putNumber(   "IMU_Update_Count",     this.getUpdateCount());
    }
}

