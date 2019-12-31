package com.charles.idol.pojo;
public class User {
	private String username;
	private String password;
	private String profile;
	private String email;
	private String birth;
	private String place;
	private String regdate;
	private int power;
	public User() {
	}
	public User(String username, String password, String profile, String email, String birth, String place) {
		this.username = username;
		this.password = password;
		this.profile = profile;
		this.email = email;
		this.birth = birth;
		this.place = place;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", profile=" + profile + ", email=" + email
				+ ", birth=" + birth + ", place=" + place + ", power=" + power + "]";
	}
	
}
