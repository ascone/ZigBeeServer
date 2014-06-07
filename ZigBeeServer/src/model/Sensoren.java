package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sensoren database table.
 * 
 */
@Entity
@NamedQuery(name="Sensoren.findAll", query="SELECT s FROM Sensoren s")
public class Sensoren implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idSensoren;

	//bi-directional many-to-one association to SensorData
	@OneToMany(mappedBy="sensoren")
	private List<SensorData> sensorData;

	//bi-directional many-to-one association to Device
	@ManyToOne
	@JoinColumn(name="Device_ID")
	private Device device;

	//bi-directional many-to-one association to SensorTyp
	@ManyToOne
	@JoinColumn(name="SensorTyp_ID")
	private SensorTyp sensorTyp;

	public Sensoren() {
	}

	public int getIdSensoren() {
		return this.idSensoren;
	}

	public void setIdSensoren(int idSensoren) {
		this.idSensoren = idSensoren;
	}

	public List<SensorData> getSensorData() {
		return this.sensorData;
	}

	public void setSensorData(List<SensorData> sensorData) {
		this.sensorData = sensorData;
	}

	public SensorData addSensorData(SensorData sensorData) {
		getSensorData().add(sensorData);
		sensorData.setSensoren(this);

		return sensorData;
	}

	public SensorData removeSensorData(SensorData sensorData) {
		getSensorData().remove(sensorData);
		sensorData.setSensoren(null);

		return sensorData;
	}

	public Device getDevice() {
		return this.device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public SensorTyp getSensorTyp() {
		return this.sensorTyp;
	}

	public void setSensorTyp(SensorTyp sensorTyp) {
		this.sensorTyp = sensorTyp;
	}

}