package com.twitter.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.twitter.beans.RealTweetStream;
import com.twitter.model.CreateArff;
import com.twitter.model.CreateFiles;
import com.twitter.services.AdminService;
import com.twitter.services.AdminServiceImpl;

/**
 * Servlet implementation class AdminRealTweetController
 */
@WebServlet("/AdminRealTweetController")
public class AdminRealTweetController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminRealTweetController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ConfigurationBuilder cb = new ConfigurationBuilder();
       cb.setDebugEnabled(true)
                .setOAuthConsumerKey("ddDMlntcDXIdVRXnPvnWpWUSP")
                .setOAuthConsumerSecret("fbxWbP9zRBTCXTljS71hJHtzthQWJ3Ggdab5kRvdwkLUgbu0OM")
                .setOAuthAccessToken("848033061597986816-TEKQhmMhgmmKuDVOcUvXrqlUI6MKMVg")
                .setOAuthAccessTokenSecret("UH1l7dFz8oSNVg6qY96MpQebxqx1cO35i4FvENjbi20cE");
        /*cb.setDebugEnabled(true)
        .setOAuthConsumerKey("3nYp3H6ndFxV4HuqgzqMB6b6Q")
        .setOAuthConsumerSecret("enOKK2FyAzcYnMU8wtUEKkftOuWtEKEAkddekNAHfOsxGG3zOW")
        .setOAuthAccessToken("857571256761290752-NHOFAU6Oq9jRVDcEHwWt3frU178HAe5")
        .setOAuthAccessTokenSecret("MjYbRGENC1s6hZAUUSYrzA3x0e1Hhz5NUWejNUeDTW6Ic");*/
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter4j.Twitter tw = tf.getInstance();
      //  Status st= tw.updateStatus("Welcome to twiiter world@@@@");
        System.out.println("Twitter updated");
        //System.out.println(st.getUser().getName()+":"+st.getText());
        ArrayList<RealTweetStream> realTweetList = new ArrayList<RealTweetStream>();
        
        
        Paging paging = new Paging(1, 1000);
        List<Status> statuses;
		try {
			statuses = tw.getHomeTimeline(paging);
			int i=0;
	         for(Status status1 : statuses){
	             if(status1.getLang().equals("en")){
	            	 RealTweetStream real = new RealTweetStream();
	            	 String cont = status1.getText();
	            	 Pattern p = Pattern.compile("\\d"); // "\d" is for digits in regex
	            	 Matcher m = p.matcher(cont);
	            	 int count = 0;
	            	 while(m.find()){
	            	    count++;
	            	 }
	            	 Date d1 = null;
	            	 Date d2 = new Date();

	            	 d1 = status1.getUser().getCreatedAt();


	            	 //in milliseconds
	            	 long diff = d2.getTime() - d1.getTime();

	            	 //long diffSeconds = diff / 1000 % 60;
	            	 //long diffMinutes = diff / (60 * 1000) % 60;
	            	 //long diffHours = diff / (60 * 60 * 1000) % 24;
	            	 long diffDays = diff / (24 * 60 * 60 * 1000);
	            	 real.setTwitId(++i);
	            	 real.setUserName(status1.getUser().getName());
	            	 real.setTweetContent(status1.getText());
	            	 real.setCreatedAcct(diffDays);
	            	 real.setFollowers(status1.getUser().getFollowersCount());
	            	 real.setFollowings(status1.getUser().getFriendsCount());
	            	 real.setUserfavourites(status1.getUser().getFavouritesCount());
	            	 real.setListed(status1.getUser().getListedCount());
	            	 real.setTweetCount(status1.getUser().getStatusesCount());
	            	 real.setRetweetCount(status1.getRetweetCount());
	            	 real.setHashtagCount(status1.getHashtagEntities().length);
	            	 real.setUserMention(status1.getUserMentionEntities().length);
	            	 real.setUrlCount(status1.getURLEntities().length);
	            	 real.setCharCount(status1.getText().toCharArray().length);
	            	 real.setDigitCount(count);
	            	
	            	 realTweetList.add(real);
	             System.out.println((i)+ " "+ status1.getId() +status1.getUser().getName()+":"+status1.getText()+":"+status1.getUser().getCreatedAt()+":"+status1.getUser().getFollowersCount()+":"+status1.getUser().getFriendsCount()+":"+status1.getUser().getFavouritesCount()+":"+status1.getUser().getListedCount()+":"+status1.getUser().getStatusesCount()+":"+status1.getRetweetCount()+":"+status1.getHashtagEntities().length+":"+status1.getUserMentionEntities().length+":"+status1.getURLEntities().length+":"+status1.getText().toCharArray().length+":"+ count);
	             System.out.println();
	             }
	         }
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
         if(realTweetList!=null && realTweetList.size()>0){
        	 AdminService adminService = new AdminServiceImpl();
        	 if(adminService.addRealStreamDataset(realTweetList)){
        		 CreateFiles cf=new CreateFiles();
        		String filePath= CreateFiles.createFile(realTweetList);
     			 //String arffFile=CreateArff.createArffFile(realTweetList);
        		 HttpSession session = request.getSession();
        		 session.setAttribute("RealTweetStream", realTweetList);
        		 RequestDispatcher rd = request.getRequestDispatcher("admin_realtweetstream.jsp");
        		 rd.forward(request, response);
        	 }
         }else {
        	 request.setAttribute("ErrMsg", "Can not loaded real time tweets from Tweeter API");
        	 RequestDispatcher rd = request.getRequestDispatcher("admin_home.jsp");
    		 rd.forward(request, response);
		}
	}

}
