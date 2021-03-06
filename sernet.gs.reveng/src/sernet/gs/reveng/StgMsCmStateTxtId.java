package sernet.gs.reveng;

// Generated Jun 5, 2015 1:28:30 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * StgMsCmStateTxtId generated by hbm2java
 */
public class StgMsCmStateTxtId implements java.io.Serializable {

	private Byte cmStaId;
	private Short sprId;
	private String name;
	private String guid;
	private Date timestamp;

	public StgMsCmStateTxtId() {
	}

	public StgMsCmStateTxtId(Byte cmStaId, Short sprId, String name,
			String guid, Date timestamp) {
		this.cmStaId = cmStaId;
		this.sprId = sprId;
		this.name = name;
		this.guid = guid;
		this.timestamp = timestamp;
	}

	public Byte getCmStaId() {
		return this.cmStaId;
	}

	public void setCmStaId(Byte cmStaId) {
		this.cmStaId = cmStaId;
	}

	public Short getSprId() {
		return this.sprId;
	}

	public void setSprId(Short sprId) {
		this.sprId = sprId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof StgMsCmStateTxtId))
			return false;
		StgMsCmStateTxtId castOther = (StgMsCmStateTxtId) other;

		return ((this.getCmStaId() == castOther.getCmStaId()) || (this
				.getCmStaId() != null && castOther.getCmStaId() != null && this
				.getCmStaId().equals(castOther.getCmStaId())))
				&& ((this.getSprId() == castOther.getSprId()) || (this
						.getSprId() != null && castOther.getSprId() != null && this
						.getSprId().equals(castOther.getSprId())))
				&& ((this.getName() == castOther.getName()) || (this.getName() != null
						&& castOther.getName() != null && this.getName()
						.equals(castOther.getName())))
				&& ((this.getGuid() == castOther.getGuid()) || (this.getGuid() != null
						&& castOther.getGuid() != null && this.getGuid()
						.equals(castOther.getGuid())))
				&& ((this.getTimestamp() == castOther.getTimestamp()) || (this
						.getTimestamp() != null
						&& castOther.getTimestamp() != null && this
						.getTimestamp().equals(castOther.getTimestamp())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCmStaId() == null ? 0 : this.getCmStaId().hashCode());
		result = 37 * result
				+ (getSprId() == null ? 0 : this.getSprId().hashCode());
		result = 37 * result
				+ (getName() == null ? 0 : this.getName().hashCode());
		result = 37 * result
				+ (getGuid() == null ? 0 : this.getGuid().hashCode());
		result = 37 * result
				+ (getTimestamp() == null ? 0 : this.getTimestamp().hashCode());
		return result;
	}

}
