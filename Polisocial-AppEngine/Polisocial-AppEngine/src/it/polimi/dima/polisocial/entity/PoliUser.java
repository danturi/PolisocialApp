package it.polimi.dima.polisocial.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PhoneNumber;
import com.google.appengine.api.users.User;
import com.google.appengine.api.datastore.Text;


/**
 * @author buzz
 * User entity containing access credentials and profile data
 */

@Entity
public class PoliUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key userKey;
	private String nickname;
	private String email;
	private String password;
	private PhoneNumber phoneNumber;
	private User userGoogleAccount;
	private String FBaccount;
	private int push_notification_id;
	private Blob profilePicture1;
	private Blob profilePicture2;
	private String selfSummary;
	private int height;
	private int age;
	private String whatImDoingWithMyLife;
	private String imReallyGoodAt;
	private Text favoriteBooksMoviesShowsMusic;
	private String sixThingsWithout;
	
	
	public Key getUserKey() {
		return userKey;
	}
	public void setUserKey(Key userKey) {
		this.userKey = userKey;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public User getUserGoogleAccount() {
		return userGoogleAccount;
	}
	public void setUserGoogleAccount(User userGoogleAccount) {
		this.userGoogleAccount = userGoogleAccount;
	}
	public String getFBaccount() {
		return FBaccount;
	}
	public void setFBaccount(String fBaccount) {
		FBaccount = fBaccount;
	}
	public int getPush_notification_id() {
		return push_notification_id;
	}
	public void setPush_notification_id(int push_notification_id) {
		this.push_notification_id = push_notification_id;
	}
	public String getSelfSummary() {
		return selfSummary;
	}
	public void setSelfSummary(String selfSummary) {
		this.selfSummary = selfSummary;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getWhatImDoingWithMyLife() {
		return whatImDoingWithMyLife;
	}
	public void setWhatImDoingWithMyLife(String whatImDoingWithMyLife) {
		this.whatImDoingWithMyLife = whatImDoingWithMyLife;
	}
	public String getImReallyGoodAt() {
		return imReallyGoodAt;
	}
	public void setImReallyGoodAt(String imReallyGoodAt) {
		this.imReallyGoodAt = imReallyGoodAt;
	}
	public Text getFavoriteBooksMoviesShowsMusic() {
		return favoriteBooksMoviesShowsMusic;
	}
	public void setFavoriteBooksMoviesShowsMusic(Text favoriteBooksMoviesShowsMusic) {
		this.favoriteBooksMoviesShowsMusic = favoriteBooksMoviesShowsMusic;
	}
	public String getSixThingsWithout() {
		return sixThingsWithout;
	}
	public void setSixThingsWithout(String sixThingsWithout) {
		this.sixThingsWithout = sixThingsWithout;
	}
	public Blob getProfilePicture1() {
		return profilePicture1;
	}
	public void setProfilePicture1(Blob profilePicture1) {
		this.profilePicture1 = profilePicture1;
	}
	public Blob getProfilePicture2() {
		return profilePicture2;
	}
	public void setProfilePicture2(Blob profilePicture2) {
		this.profilePicture2 = profilePicture2;
	}
	
}
