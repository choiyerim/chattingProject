package com.doyun.doyun.user.dto;

import lombok.Data;

@Data
public class UserKey {
	private int userSeq;
	private String userKey;
	public UserKey() {
		
	}
	public UserKey(int userSeq, String userKey) {
		super();
		this.userSeq = userSeq;
		this.userKey = userKey;
	}
	
}
