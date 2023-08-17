package singlelinebot.entity;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="line_userinfo")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LineUserInfo {

	@JsonIgnore
	public static final String STATUS_DISABLED="1";
	
	@JsonIgnore
	public static final String STATUS_ENABLED="0";
	
	@Id
	@Column(name="uid")
	private String uid;

	@Column(name="user_id")
	private String userId;

	@Column(name="account")
	private String account;

	@Column(name="password")
	private String password;

	@Column(name="display_name")
	private String displayName;

	@Column(name="picture_url")
	private String pictureUrl;

	@Column(name="notify_token")
	private String notifyToken;

	@Column(name="status")
	private String status=STATUS_ENABLED;
	
	
	
	
}
