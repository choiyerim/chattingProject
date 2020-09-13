package com.doyun.doyun.user.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * JPA연동을 위한 준비. 추후 프로젝트 완성 후 jpa 연동 예정.
 * 
 * @author ChoiDoYun
 *
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "USERS")
public class UserVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_SEQ")
	private int userSeq;

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "BIRTH")
	private String birth;

	@Column(name = "insert_date")
	private Date insertDate;
	
	@Column
	private String nickName;
	
	@Column
	private String statusMessage;
	
	/**
	 * imgSeq를 url로 file테이블과 join하여 가져옴.
	 */
	@Column
	private String imageUrl;

	
	
	public UserVO() {
		
	}

	public UserVO(int userSeq, String userId, String userName, String phone, String password, String birth,
			Date insertDate) {
		this.userSeq = userSeq;
		this.userId = userId;
		this.userName = userName;
		this.phone = phone;
		this.password = password;
		this.birth = birth;
		this.insertDate = insertDate;
	}

	
}
