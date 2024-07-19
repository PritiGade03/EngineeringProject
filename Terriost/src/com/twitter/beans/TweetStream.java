package com.twitter.beans;

import java.util.Date;

public class TweetStream {
	private long Tweet_Id;
	private String date;
	private String Hour;
	private String UserName;
	private String Nickname;
	//private String Bio;
	private String Tweet_content;
	private String Country;
	private String Place;
	private String profile_pic;
	private String Followers;
	private String Following;
	private String language;
	private String Tweet_Url;
	private String result;
	private double resultProb;
	public long getTweet_Id() {
		return Tweet_Id;
	}
	public void setTweet_Id(long tweet_Id) {
		Tweet_Id = tweet_Id;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHour() {
		return Hour;
	}
	public void setHour(String hour) {
		Hour = hour;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getNickname() {
		return Nickname;
	}
	public void setNickname(String nickname) {
		Nickname = nickname;
	}
	/*public String getBio() {
		return Bio;
	}
	public void setBio(String bio) {
		Bio = bio;
	}*/
	public String getTweet_content() {
		return Tweet_content;
	}
	public void setTweet_content(String tweet_content) {
		Tweet_content = tweet_content;
	}
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		Country = country;
	}
	public String getPlace() {
		return Place;
	}
	public void setPlace(String place) {
		Place = place;
	}
	public String getProfile_pic() {
		return profile_pic;
	}
	public void setProfile_pic(String profile_pic) {
		this.profile_pic = profile_pic;
	}
	public String getFollowers() {
		return Followers;
	}
	public void setFollowers(String followers) {
		Followers = followers;
	}
	public String getFollowing() {
		return Following;
	}
	public void setFollowing(String following) {
		Following = following;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getTweet_Url() {
		return Tweet_Url;
	}
	public void setTweet_Url(String tweet_Url) {
		Tweet_Url = tweet_Url;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public double getResultProb() {
		return resultProb;
	}
	public void setResultProb(double resultProb) {
		this.resultProb = resultProb;
	}
	
	
}
