package com.twitter.beans;

import java.io.InputStream;
import java.util.Date;

public class Tweet {
	private int tweetId;
	private String message;
	private InputStream uploadImg;
	private String uploadName;
	private Date tweetdate;
	private User user;
	private String bayesResult;
	private double bayesProbability;
	private String stemming;
	private String result;
	
	public int getTweetId() {
		return tweetId;
	}
	public void setTweetId(int tweetId) {
		this.tweetId = tweetId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public InputStream getUploadImg() {
		return uploadImg;
	}
	public void setUploadImg(InputStream uploadImg) {
		this.uploadImg = uploadImg;
	}
	public String getUploadName() {
		return uploadName;
	}
	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}
	public Date getTweetdate() {
		return tweetdate;
	}
	public void setTweetdate(Date tweetdate) {
		this.tweetdate = tweetdate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}	
	public String getBayesResult() {
		return bayesResult;
	}
	public void setBayesResult(String bayesResult) {
		this.bayesResult = bayesResult;
	}	
	public double getBayesProbability() {
		return bayesProbability;
	}
	public void setBayesProbability(double bayesProbability) {
		this.bayesProbability = bayesProbability;
	}
	public String getStemming() {
		return stemming;
	}
	public void setStemming(String stemming) {
		this.stemming = stemming;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
		
}
