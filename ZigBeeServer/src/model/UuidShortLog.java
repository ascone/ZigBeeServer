package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the uuid_short_log database table.
 * 
 */
@Entity
@Table(name="uuid_short_log")
@NamedQuery(name="UuidShortLog.findAll", query="SELECT u FROM UuidShortLog u")
public class UuidShortLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int uuid_short_log_ID;

	@Temporal(TemporalType.TIMESTAMP)
	private Date ttimestamp;

	private long UUID_short;

	//bi-directional many-to-one association to Device
	@ManyToOne
	@JoinColumn(name="UUID")
	private Device device;

	public UuidShortLog() {
	}

	public int getUuid_short_log_ID() {
		return this.uuid_short_log_ID;
	}

	public void setUuid_short_log_ID(int uuid_short_log_ID) {
		this.uuid_short_log_ID = uuid_short_log_ID;
	}

	public Date getTtimestamp() {
		return this.ttimestamp;
	}

	public void setTtimestamp(Date ttimestamp) {
		this.ttimestamp = ttimestamp;
	}

	public long getUUID_short() {
		return this.UUID_short;
	}

	public void setUUID_short(long shortCSUID) {
		this.UUID_short = shortCSUID;
	}

	public Device getDevice() {
		return this.device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

}