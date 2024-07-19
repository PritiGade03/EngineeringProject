package com.twitter.beans;

public class RealTweetStream {
	private int twitId;
	private String userName;
	private String tweetContent;
	private long createdAcct;
	private int followers;
	private int followings;
	private int userfavourites;
	private int listed;
	private int tweetCount;
	private int retweetCount;
	private int hashtagCount;
	private int userMention;
	private int urlCount;
	private int charCount;
	private int digitCount;
	private String result;
	private double resultProb;
	
	
	public int getTwitId() {
		return twitId;
	}
	public void setTwitId(int twitId) {
		this.twitId = twitId;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTweetContent() {
		return tweetContent;
	}
	public void setTweetContent(String tweetContent) {
		this.tweetContent = tweetContent;
	}
	
	public long getCreatedAcct() {
		return createdAcct;
	}
	public void setCreatedAcct(long createdAcct) {
		this.createdAcct = createdAcct;
	}
	public int getFollowers() {
		return followers;
	}
	public void setFollowers(int followers) {
		this.followers = followers;
	}
	public int getFollowings() {
		return followings;
	}
	public void setFollowings(int followings) {
		this.followings = followings;
	}
	public int getUserfavourites() {
		return userfavourites;
	}
	public void setUserfavourites(int userfavourites) {
		this.userfavourites = userfavourites;
	}
	public int getListed() {
		return listed;
	}
	public void setListed(int listed) {
		this.listed = listed;
	}
	public int getTweetCount() {
		return tweetCount;
	}
	public void setTweetCount(int tweetCount) {
		this.tweetCount = tweetCount;
	}
	public int getRetweetCount() {
		return retweetCount;
	}
	public void setRetweetCount(int retweetCount) {
		this.retweetCount = retweetCount;
	}
	public int getHashtagCount() {
		return hashtagCount;
	}
	public void setHashtagCount(int hashtagCount) {
		this.hashtagCount = hashtagCount;
	}
	public int getUserMention() {
		return userMention;
	}
	public void setUserMention(int userMention) {
		this.userMention = userMention;
	}
	public int getUrlCount() {
		return urlCount;
	}
	public void setUrlCount(int urlCount) {
		this.urlCount = urlCount;
	}
	public int getCharCount() {
		return charCount;
	}
	public void setCharCount(int charCount) {
		this.charCount = charCount;
	}
	public int getDigitCount() {
		return digitCount;
	}
	public void setDigitCount(int digitCount) {
		this.digitCount = digitCount;
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
