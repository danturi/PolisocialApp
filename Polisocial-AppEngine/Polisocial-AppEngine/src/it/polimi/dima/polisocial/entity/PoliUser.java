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
	private Long userId;
	private String nickname;
	private String email;
	private String password;
	private PhoneNumber phoneNumber;
	private User userGoogleAccount;
	private String FBaccount;
	private Blob profilePicture1;
	private Blob profilePicture2;
	private String selfSummary;
	private int height;
	private int age;
	private String whatImDoingWithMyLife;
	private String imReallyGoodAt;
	private Text favoriteBooksMoviesShowsMusic;
	private String sixThingsWithout;
	private String faculty;
	
	//flags per notifiche
	private Boolean notifySpotted;
	private Boolean notifyAnnouncement;
	private Boolean notifyEvent;	
	private Boolean notifiedSpotted;
	private Boolean notifiedAnnouncement;
	private Boolean notifiedEvent;
	
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
	public Boolean getNotifySpotted() {
		return notifySpotted;
	}
	public void setNotifySpotted(Boolean notifySpotted) {
		this.notifySpotted = notifySpotted;
	}
	public Boolean getNotifyAnnouncement() {
		return notifyAnnouncement;
	}
	public void setNotifyAnnouncement(Boolean notifyAnnouncement) {
		this.notifyAnnouncement = notifyAnnouncement;
	}
	public Boolean getNotifyRental() {
		return notifyEvent;
	}
	public void setNotifyRental(Boolean notifyRental) {
		this.notifyEvent = notifyRental;
	}
	public String getFaculty() {
		return faculty;
	}
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
	public Boolean getNotifiedSpotted() {
		return notifiedSpotted;
	}
	public void setNotifiedSpotted(Boolean notifyed) {
		this.notifiedSpotted = notifyed;
	}
	public Boolean getNotifiedAnnouncement() {
		return notifiedAnnouncement;
	}
	public void setNotifiedAnnouncement(Boolean notifiedAnnouncement) {
		this.notifiedAnnouncement = notifiedAnnouncement;
	}
	public Boolean getNotifiedEvent() {
		return notifiedEvent;
	}
	public void setNotifiedEvent(Boolean notifiedEvent) {
		this.notifiedEvent = notifiedEvent;
	}
	
}
