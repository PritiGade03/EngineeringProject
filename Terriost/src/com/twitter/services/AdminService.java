package com.twitter.services;

import java.util.ArrayList;

import com.twitter.beans.Admin;
import com.twitter.beans.RealTweetStream;
import com.twitter.beans.Tweet;
import com.twitter.beans.TweetStream;

public interface AdminService {
	Admin selectAdmin(String email, String password);
	boolean updateAdmin(String oldPassword, String newPassword,String email);
	ArrayList<Tweet> selectTweet();
	boolean addStreamDataset(ArrayList<TweetStream> tweetStreamList);
	boolean addRealStreamDataset(ArrayList<RealTweetStream> realTweetList);
}
