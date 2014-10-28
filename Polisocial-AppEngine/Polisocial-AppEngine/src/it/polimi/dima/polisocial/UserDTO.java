package it.polimi.dima.polisocial;

import java.util.Date;


public class UserDTO {

	private Long userId;
	private String nickname;
	private Date datebirth;
	private String faculty;
	
	public UserDTO(Long userId, String nickname, Date datebirth, String faculty) {
		super();
		this.userId = userId;
		this.nickname = nickname;
		this.setDatebirth(datebirth);
		this.faculty = faculty;
	}
	
	public UserDTO() {
		
	}

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
	public String getFaculty() {
		return faculty;
	}
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public Date getDatebirth() {
		return datebirth;
	}

	public void setDatebirth(Date datebirth) {
		this.datebirth = datebirth;
	}
}
