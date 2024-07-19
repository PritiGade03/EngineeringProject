package com.twitter.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.twitter.beans.RealTweetStream;
import com.twitter.beans.TweetStream;
import com.twitter.model.DataClassifier;
import com.twitter.model.Classifier;
import com.twitter.model.CreateArff;
import com.twitter.model.CreateArff2;

/**
 * Servlet implementation class AdminSpamClassifyController
 */
@WebServlet("/AdminSpamClassifyController")
public class AdminSpamClassifyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminSpamClassifyController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session =request.getSession();
		
		ArrayList<RealTweetStream> twitStreamList = (ArrayList<RealTweetStream>)session.getAttribute("RealTweetStream");
		Classifier<String, String> bayes = new DataClassifier<String, String>();
		 final String[] positiveText = {"newsletter", "from", "your", "favorite", "website",
				 "love","like", "happy", "good", "sunny", "able", "temperature","climate",
				 "wind","beautiful","waterfall","climbing","race","spring","morning","home","cleared"};
	        bayes.learn("Positive", Arrays.asList(positiveText));

	        final String[] negativeText = {"money","credit", "$", "sign","job","hiring",
		"offer", "order", "hot", "nude", "click", "amateur", "pics","videos",
		"hardcore","teen","sex","limited","free","advertisement","mortgage","must-read","unsubscribe",
		"dollar","special","deposit","donation","register","lottery","guaranteed","exotic","opening","work","Angry"};
	        bayes.learn("Negative", Arrays.asList(negativeText));
		String words="";		
		String sentence="";
		try{
		if(twitStreamList.size()>0){
			for(int i=0;i<twitStreamList.size();i++){
				String tweet = twitStreamList.get(i).getTweetContent();
				int count = 0;
				if(tweet.contains("https://")){
					count++;
					String s1=tweet.substring(tweet.indexOf("https"));
					if(s1.length()>=23){
					String str=tweet.substring(tweet.indexOf("https"),tweet.indexOf("https")+23);
					System.out.println("url="+str);
					
					String url=expandUrl(str);
					FileInputStream fis= new FileInputStream(" /WebContent/upload/blacklist.txt");
					BufferedReader br = new BufferedReader(new InputStreamReader(fis));
					try {
					    String line;
					    while ((line = br.readLine()) != null) {
					       // process the line.
					    	if(url.contains(line)){
					    		count++;
					    		break;
					    	}
					    }
					}catch(Exception e){
						e.printStackTrace();
					}
					}
				}
				int followers = twitStreamList.get(i).getFollowers();
				int followings = twitStreamList.get(i).getFollowings();
				Double score=0.0;
				if(followings==0)
					score=0.0;
				else
				 score= (double)(followers/followings);
				if(score.isNaN()){
					count++;
				}			
				if(score<1.0)
					count++;
				
				int val = twitStreamList.get(i).getHashtagCount();
				int val2 = twitStreamList.get(i).getTweetCount();
				score=(double)(val/val2);
				if(score.isNaN()){
					
				}else if(score<1.0){
						count++;
				}		
				val =twitStreamList.get(i).getUserMention();
				score=(double)(val/val2);
				if(score.isNaN()){
					count++;
				}
				
				
				sentence="";
				words=twitStreamList.get(i).getTweetContent();
				words=words.toLowerCase();
				//words=words.replace(".","");
				words=words.replace(",","");
				words=words.replace("!","");
				//words=words.replace(":d","");
				words=words.replace(":)","");
				//words=words.replace(":","");
				words=words.replace(";","");
				words=words.replace("?","");
				//words=words.replace("'","");
				words=words.replace("*","");
				words=words.replace("^","");
				words=words.replace("<3","");				
				String word[]=words.split("\\s+");
				String bayesResult = bayes.classify(Arrays.asList(word)).getCategory();
				double bayesProbability = bayes.classify(Arrays.asList(word)).getProbability();
				
				twitStreamList.get(i).setResult(bayesResult);
				twitStreamList.get(i).setResultProb(bayesProbability);
			}	
			
				/*CreateArff2 arff=new CreateArff2();
				String fileArff=CreateArff2.createArffFile1(twitStreamList);*/
			
			double spam=0.0;
			double nonspam=0.0;
			for(int i=0;i<twitStreamList.size();i++){
				if(twitStreamList.get(i).getResult().equals("Spam"))
					spam++;
				else
					nonspam++;
			}
			/*double truePositive=spam;
		    double trueNegative=spam+(nonspam/2);    
		    double falsePositive=spam-nonspam;
		    double falseNegative=nonspam;
			double precision=(truePositive/(truePositive+falsePositive))*100;
			double recall=(truePositive/(truePositive+falseNegative))*100;
			double fmeasure=(2*(precision*recall)/(precision+recall));
			double accuracy=((precision+recall)/twitStreamList.size())*100;*/
			double truePositive=spam;		      
		    double falsePositive=spam-(nonspam*2);
		    double trueNegative=(spam+nonspam)-falsePositive;  
		    double falseNegative=nonspam;
		    
			double precision=(truePositive/(truePositive+falsePositive))*100;
			double recall=(truePositive/(truePositive+falseNegative))*100;
			double fmeasure=(2*(precision*recall)/(precision+recall));
			double accuracy=((truePositive+falsePositive)/(truePositive+falsePositive+falseNegative))*100;
			double tpr=recall;
			double fpr = ((falsePositive)/(falsePositive+falseNegative))*100;
			session.setAttribute("RealTweetStream", twitStreamList);
			session.setAttribute("precision", precision);
			session.setAttribute("recall", recall);
			session.setAttribute("fmeasure", fmeasure);
			session.setAttribute("tpr", tpr);
			session.setAttribute("fpr", fpr);
			session.setAttribute("spam", spam);
			session.setAttribute("nonspam", nonspam);
			session.setAttribute("accuracy", accuracy);
			session.setAttribute("RealNBAccu", accuracy);
			RequestDispatcher rd = request.getRequestDispatcher("admin_realspamdetect.jsp");
			rd.forward(request, response);
		}else{
			request.setAttribute("ErrMsg", "There are no records");
			RequestDispatcher rd = request.getRequestDispatcher("admin_upload.jsp");
			rd.forward(request, response);
		}	
		}catch(IndexOutOfBoundsException e1){
			e1.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public static String expandUrl(String shortenedUrl) throws IOException {
        URL url = new URL(shortenedUrl);    
        // open connection
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY); 
        
        // stop following browser redirect
        httpURLConnection.setInstanceFollowRedirects(false);
         
        // extract location header containing the actual destination URL
        String expandedURL = httpURLConnection.getHeaderField("Location");
        httpURLConnection.disconnect();
         
        return expandedURL;
    }

	public int characterCount(String word, char character) {

		   //initialize the counter to 0
		   int count = 0;
		   //loop through the word
		    for (int i = 0; i < word.length(); i++) {
		       //if the character in the word is equal to  the character passed in as a parameter increment count
		       if (character==word.charAt(i)) {
		           count++;
		       }
		    }
		    return count;
	}


}
