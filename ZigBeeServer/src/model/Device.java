package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the device database table.
 * 
 */
@Entity
@Table(name="Device")
@NamedQuery(name="Device.findAll", query="SELECT d FROM Device d")
public class Device implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idDevice;

	private String device_Name;

	private String device_PosX;

	private String device_PosY;

	

	//bi-directional many-to-one association to Sensoren
	@OneToMany(mappedBy="device")
	private List<Sensoren> sensorens;

	//bi-directional many-to-one association to UuidShortLog
	@OneToMany(mappedBy="device")
	private List<UuidShortLog> uuidShortLogs;

	public Device() {
	}

	public int getIdDevice() {
		return this.idDevice;
	}

	public void setIdDevice(int idDevice) {
		this.idDevice = idDevice;
	}

	public String getDevice_Name() {
		return this.device_Name;
	}

	public void setDevice_Name(String device_Name) {
		this.device_Name = device_Name;
	}

	public String getDevice_PosX() {
		return this.device_PosX;
	}

	public void setDevice_PosX(String device_PosX) {
		this.device_PosX = device_PosX;
	}

	public String getDevice_PosY() {
		return this.device_PosY;
	}

	public void setDevice_PosY(String device_PosY) {
		this.device_PosY = device_PosY;
	}

	public List<Sensoren> getSensorens() {
		return this.sensorens;
	}

	public void setSensorens(List<Sensoren> sensorens) {
		this.sensorens = sensorens;
	}

	public Sensoren addSensoren(Sensoren sensoren) {
		getSensorens().add(sensoren);
		sensoren.setDevice(this);

		return sensoren;
	}

	public Sensoren removeSensoren(Sensoren sensoren) {
		getSensorens().remove(sensoren);
		sensoren.setDevice(null);

		return sensoren;
	}

	public List<UuidShortLog> getUuidShortLogs() {
		return this.uuidShortLogs;
	}

	public void setUuidShortLogs(List<UuidShortLog> uuidShortLogs) {
		this.uuidShortLogs = uuidShortLogs;
	}

	public UuidShortLog addUuidShortLog(UuidShortLog uuidShortLog) {
		getUuidShortLogs().add(uuidShortLog);
		uuidShortLog.setDevice(this);

		return uuidShortLog;
	}

	public UuidShortLog removeUuidShortLog(UuidShortLog uuidShortLog) {
		getUuidShortLogs().remove(uuidShortLog);
		uuidShortLog.setDevice(null);

		return uuidShortLog;
	}

}
