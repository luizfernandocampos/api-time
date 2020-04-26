package com.devlf.apitime.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class JobLogVO implements Serializable {
	
	private static final long serialVersionUID = 9177884283045633115L;
	
	private int id = 0;
	private int idJob = 0;
	private int jobLogType = 0;
	private Timestamp jobLogDate = null;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdJob() {
		return idJob;
	}

	public void setIdJob(int idJob) {
		this.idJob = idJob;
	}

	public int getJobLogType() {
		return jobLogType;
	}

	public void setJobLogType(int jobLogType) {
		this.jobLogType = jobLogType;
	}

	public Timestamp getJobLogDate() {
		return jobLogDate;
	}

	public void setJobLogDate(Timestamp jobLogDate) {
		this.jobLogDate = jobLogDate;
	}


}
