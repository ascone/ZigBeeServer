package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sensor_typ database table.
 * 
 */
@Entity
@Table(name="Sensor_Typ")
@NamedQuery(name="SensorTyp.findAll", query="SELECT s FROM SensorTyp s")
public class SensorTyp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idSensor_Typ;

	private String sensor_Einheit;

	private String sensor_Typ;

	//bi-directional many-to-one association to Sensoren
	@OneToMany(mappedBy="sensorTyp")
	private List<Sensoren> sensorens;

	public SensorTyp() {
	}

	public int getIdSensor_Typ() {
		return this.idSensor_Typ;
	}

	public void setIdSensor_Typ(int idSensor_Typ) {
		this.idSensor_Typ = idSensor_Typ;
	}

	public String getSensor_Einheit() {
		return this.sensor_Einheit;
	}

	public void setSensor_Einheit(String sensor_Einheit) {
		this.sensor_Einheit = sensor_Einheit;
	}

	public String getSensor_Typ() {
		return this.sensor_Typ;
	}

	public void setSensor_Typ(String sensor_Typ) {
		this.sensor_Typ = sensor_Typ;
	}

	public List<Sensoren> getSensorens() {
		return this.sensorens;
	}

	public void setSensorens(List<Sensoren> sensorens) {
		this.sensorens = sensorens;
	}

	public Sensoren addSensoren(Sensoren sensoren) {
		getSensorens().add(sensoren);
		sensoren.setSensorTyp(this);

		return sensoren;
	}

	public Sensoren removeSensoren(Sensoren sensoren) {
		getSensorens().remove(sensoren);
		sensoren.setSensorTyp(null);

		return sensoren;
	}

}
