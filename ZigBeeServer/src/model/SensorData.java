package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the sensor_data database table.
 * 
 */
@Entity
@Table(name="sensor_data")
@NamedQuery(name="SensorData.findAll", query="SELECT s FROM SensorData s")
public class SensorData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idSensor_Data;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="time_stamp")
	private Date timeStamp;

	private String wert;

	//bi-directional many-to-one association to Sensoren
	@ManyToOne
	@JoinColumn(name="Sensor_ID")
	private Sensoren sensoren;

	public SensorData() {
	}

	public int getIdSensor_Data() {
		return this.idSensor_Data;
	}

	public void setIdSensor_Data(int idSensor_Data) {
		this.idSensor_Data = idSensor_Data;
	}

	public Date getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getWert() {
		return this.wert;
	}

	public void setWert(String wert) {
		this.wert = wert;
	}

	public Sensoren getSensoren() {
		return this.sensoren;
	}

	public void setSensoren(Sensoren sensoren) {
		this.sensoren = sensoren;
	}

}