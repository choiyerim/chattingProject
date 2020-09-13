package com.doyun.doyun.user.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class FriendVO {
	private int friendNo;
	private String userName;
	private Date birth;
	private String nickName;
	private String statusMessage;
	private String imageUrl;

	
}
