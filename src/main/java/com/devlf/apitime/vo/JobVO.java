package com.devlf.apitime.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class JobVO implements Serializable {

	private static final long serialVersionUID = 550063553216003753L;

	private int id = 0;
	private int idUser = 0;
	private String jobName = null;
	private String jobDetails = null;
	private double jobValue = 0d;
	private Timestamp jobCreateDate = null;
	private Timestamp jobFinishDate = null;
	private Timestamp lastStartDate = null;
	private Timestamp lastPauseDate = null;
	private Timestamp expectedDateEnd = null;
	private Long jobTimer = null;
	private List<JobLogVO> logs = null;
	private boolean playing = false;


	public Timestamp getExpectedDateEnd() {
		return expectedDateEnd;
	}

	public void setExpectedDateEnd(Timestamp expectedDateEnd) {
		this.expectedDateEnd = expectedDateEnd;
	}

	public Timestamp getLastStartDate() {
		return lastStartDate;
	}

	public void setLastStartDate(Timestamp lastStartDate) {
		this.lastStartDate = lastStartDate;
	}

	public Timestamp getLastPauseDate() {
		return lastPauseDate;
	}

	public void setLastPauseDate(Timestamp lastPauseDate) {
		this.lastPauseDate = lastPauseDate;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

	public List<JobLogVO> getLogs() {
		return logs;
	}

	public void setLogs(List<JobLogVO> logs) {
		this.logs = logs;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobDetails() {
		return jobDetails;
	}

	public void setJobDetails(String jobDetails) {
		this.jobDetails = jobDetails;
	}

	public double getJobValue() {
		return jobValue;
	}

	public void setJobValue(double jobValue) {
		this.jobValue = jobValue;
	}

	public Timestamp getJobCreateDate() {
		return jobCreateDate;
	}

	public void setJobCreateDate(Timestamp jobCreateDate) {
		this.jobCreateDate = jobCreateDate;
	}

	public Timestamp getJobFinishDate() {
		return jobFinishDate;
	}

	public void setJobFinishDate(Timestamp jobFinishDate) {
		this.jobFinishDate = jobFinishDate;
	}

	public Long getJobTimer() {
		return jobTimer;
	}

	public void setJobTimer(Long jobTimer) {
		this.jobTimer = jobTimer;
	}

}
