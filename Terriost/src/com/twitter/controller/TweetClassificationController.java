package com.twitter.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.twitter.beans.Tweet;
import com.twitter.model.DataClassifier;
import com.twitter.model.Classifier;
import com.twitter.model.Porter;
import com.twitter.model.StopWords;
import com.twitter.services.AdminService;
import com.twitter.services.AdminServiceImpl;

/**
 * Servlet implementation class TweetClassificationController
 */
@WebServlet("/TweetClassificationController")
public class TweetClassificationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TweetClassificationController() {
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
				
		AdminService adminService = new AdminServiceImpl();
		ArrayList<Tweet> twitList = adminService.selectTweet();
		Classifier<String, String> bayes = new DataClassifier<String, String>();
		final String[] positiveText = {"newsletter", "from", "your", "favorite", "website",
				 "love","like", "happy", "good", "sunny", "able", "temperature","climate",
				 "wind","beautiful","waterfall","climbing","race","spring","morning","home","cleared","pretty",
				 "perfectly", "lunch", "work","Friendly Natural","Honest","Handsome","Okay","Celebrated","Simple",
				 "Supporting","Honorable","Cute","Efficient","Truthful","Calm","Ideal","Instantaneous","Good",
				 "Distinguished","ThrillingMarvelous","Instinctive","Seemly","Agreeable","Agree","Quality","Heaven",
				 "Novel","Quick","Plentiful","Nurturing","Lucky","Perfect","Nutritious","Harmonious","Enthusiastic",
				 "Independent","Masterful","Creative","Inventive","Choice","Special","Joy","Superb","Victory","Bountiful",
				 "Healing","Amazing" ,"Imaginative","Rewarding", "Energetic", "Genuine", "Quiet Divine" ,"Brave Robust" ,
				 "Effervescent" ,"Valued Classic","Intellectual","Trusting","Endorsed","Thriving","Acclaimed","Effective",
				 "Spirited","Upstanding","cute"};
	        bayes.learn("Negative", Arrays.asList(positiveText));


	        final String[] negativeText = {"money","credit", "threat", "sign","job","hiring",
	                "offer", "order", "hot", "nude", "click", "amateur", "pics","videos",
	                "hardcore","teen","sex","limited","free","advertisement","mortgage","must-read","unsubscribe",
	                "dollar","special","deposit","donation","coercion","lottery","intimidation","exotic","opening","work","violence",
	                "terror","menace","bullying","potency","browbeating","bulldozing","puissance","hardheadedness","blast","terror",
	                "violence","intimidation","threat","fear","menace","coercion","sword","pressure","compulsion","bullying",
	                "duress","constraint", "arrest", "bastard","force","strength","muscle","potency","browbeating","bulldozing",
	                "stress","squeeze","might","willfulness","strain","bomb","blast","terriosm","terrorist","terror","intoler",
	                "jihad","hate","injuri","attackers","spread","Anger", "Avoid", "Awful", "Ashamed", "Annoy", "Abandon",
	                "Abuse", "Afraid"," Alone", "Attack","Alcohol","Beg"," Bore","Bad"," Broken"," Blame", "Beer"," Booze",
	                "Cannot"," Clumsy"," Confuse", "Cheat"," Delay","Danger"," Difficult"," Dislike", "Defeat"," Dead"," Damage",
	                " Deny", "Depress"," Drug","Dirty"," Dishonest", "Damage","Divorce", "Disease","Dreadful", "Dumb","Evil","Excuse",
	                " Embarrass"," Enemy"," Fear","Fight","Furious"," Fault"," Fail","Failure", "Foul"," Fright"," Force ","False",
	                " Gossip"," Greed","Hate", "Hurt","Hide","Hunger"," Horrible"," Harm","Harmful"," Humiliate"," Impossible",
	                "Ignore"," Insecure", "Ill"," Insane"," Inferior", "Insult"," Jealous ","Kill"," Lie ","Lost","Loose", "Offensive",
	                " Pain"," Pessimist"," Problem", "Poison ","Quit", "Reject"," Revenge"," Rude", "Steal"," Suspicious","Suspect",
	                "Traitor", "Tension", "Ugly" ,"wanted","Blackmail","Cruelty","Neglect","Sadism","ExploitingTerrorism",
	                "Cyberbullying","Threat","Attack","Fear","Hatred","Hostility","Harm","Exploitation","Abuse","Aggression",
	                "Conflict","Rage","Scare","Anger","Terror","Hostage","Kidnapping","Sabotage","Trauma","Devastation","Murder",
	                "Killing","Massacre","Execution","Cruelty","Violence","Bloodshed","Brutality","Torture","Ransom","Bombing",
	                "Explosive","Radical","Injustice","Anguish","Suffering","Fearmongering","AnxietyPanic","Tyranny","Insurgence",
	                "Hate","Despair","Misery","Indoctrination","Betrayal","Sabotage","Insult","Threatening","Defamation","Stalking",
	                "Harassment","Bullying","Alienation","Discrimination","Invasion","Violation"};
	        	        bayes.learn("Postive", Arrays.asList(negativeText));
		String words="";		
		String sentence="";
		try{
		if(twitList.size()>0){
			for(int i=0;i<twitList.size();i++){	
				sentence="";
				words=twitList.get(i).getMessage();
				words=words.toLowerCase();
				words=words.replace(".","");
				words=words.replace(",","");
				words=words.replace("!","");
				//words=words.replace(":d","");
				words=words.replace(":)","");
				words=words.replace(":","");
				words=words.replace(";","");
				words=words.replace("?","");
				//words=words.replace("'","");
				words=words.replace("*","");
				words=words.replace("^","");
				words=words.replace("<3","");				
				String word[]=words.split("\\s+");
				String bayesResult = bayes.classify(Arrays.asList(word)).getCategory();
				double bayesProbability = bayes.classify(Arrays.asList(word)).getProbability();
				
				for(int j=0;j<word.length;j++){
					word[j]=removeDup(word[j]);
				}
				
				
				Tweet twit = new Tweet();
				twit.setTweetId(twitList.get(i).getTweetId());
				twit.setMessage(twitList.get(i).getMessage());
				twit.setBayesResult(bayesResult);
				twit.setBayesProbability(bayesProbability);
				for(int j=0;j<word.length;j++){
					Porter porter = new Porter();
					word[j]=porter.stripSuffixes(word[j]);
					sentence = sentence + word[j]+" ";
				}
				twit.setStemming(sentence);
				String str1[] = sentence.split("\\s+");
				String str2="";
				for(int k=0;k<str1.length;k++){
		        	StopWords stop=new StopWords();
		        	String result=stop.remove(str1[k]);
		        	if(result!=null&&result!="")
		        	str2 = str2 + result + " ";
		        }
				twit.setResult(str2);
				twitList.set(i, twit);
			}
			HttpSession session = request.getSession();
			session.setAttribute("twitList", twitList);
			RequestDispatcher rd = request.getRequestDispatcher("tweet_classification.jsp");
			rd.forward(request, response);
		}else{
			request.setAttribute("ErrMsg", "There are no records");
			RequestDispatcher rd = request.getRequestDispatcher("tweet_classification.jsp");
			rd.forward(request, response);
		}	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public  String removeDup(String str){
		str = str + " "; // Adding a space at the end of the word
        int l=str.length(); // Finding the length of the word
		String ans="";
		char ch1,ch2;
				 
        for(int i=0; i<l-1; i++)
        {
            ch1=str.charAt(i); // Extracting the first character
            ch2=str.charAt(i+1); // Extracting the next character
 
// Adding the first extracted character to the result if the current and the next characters are different
            int count= countRun(str, ch1);
            if(count==2){
            	ans = ans + ch1;
            }else if(ch1!=ch2){            
            	ans = ans + ch1;
            }            
        }
	    return ans;
    }
	public static int countRun( String s, char c )
	{
	    int counter = 0;
	    for( int i = 0; i < s.length(); i++)
	    {
	      if( s.charAt(i) == c )  counter++;
	      else if(counter>0) break;
	    }
	    return counter;
	}
	public static ArrayList<String> ngrams(int n, String str) {
		ArrayList<String> ngrams = new ArrayList<String>();
	    String[] words = str.split("\\s");
	    for (int i = 0; i < words.length; i++)
	    	ngrams.add(concat(words, i, i+n));
	    System.out.println(ngrams.toString());
	     return ngrams;
	     
	}

	    public static String concat(String[] words, int start, int end) {
	        StringBuilder sb = new StringBuilder();
	        for (int i = start; i < end; i++)
	            sb.append((i > start ? " " : "") + words[i]);
	        System.out.println(sb.toString());
	        return sb.toString();
	    }	

}
