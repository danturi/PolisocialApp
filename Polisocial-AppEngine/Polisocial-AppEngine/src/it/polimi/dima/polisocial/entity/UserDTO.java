package it.polimi.dima.polisocial.entity;


public class UserDTO {

	private Long userId;
	private String nickname;
	private Integer age;
	private String faculty;
	
	public UserDTO(Long userId, String nickname, Integer age, String faculty) {
		super();
		this.userId = userId;
		this.nickname = nickname;
		this.age = age;
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
	
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getFaculty() {
		return faculty;
	}
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
}
