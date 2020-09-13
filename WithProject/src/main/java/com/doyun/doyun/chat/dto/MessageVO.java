package com.doyun.doyun.chat.dto;

import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection ="chat" )
public class MessageVO {
	@Id
	private String chatroomNo;
	private String message;
	private String nickName;
	private Date insertDate;
	private String deleteStatus="N";
	private String imageUrl;

}
