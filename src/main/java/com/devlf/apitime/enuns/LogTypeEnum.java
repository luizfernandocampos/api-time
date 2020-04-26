package com.devlf.apitime.enuns;

public enum LogTypeEnum {

	STARTED(1), PAUSED(2), FINISH(3);

	private int code;

	private LogTypeEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	

}
