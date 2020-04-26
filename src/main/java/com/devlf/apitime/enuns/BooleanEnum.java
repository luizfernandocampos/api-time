package com.devlf.apitime.enuns;

public enum BooleanEnum {

	YES("1"), NO("0");

	private String code;

	private BooleanEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	

}
