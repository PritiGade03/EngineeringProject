package com.twitter.controller;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import au.com.bytecode.opencsv.CSVReader;

import com.oreilly.servlet.MultipartRequest;
import com.twitter.beans.TweetStream;
import com.twitter.model.CreateFiles;
import com.twitter.services.AdminService;
import com.twitter.services.AdminServiceImpl;



/**
 * Servlet implementation class AdminUploadController
 */
@WebServlet("/AdminUploadController")
@MultipartConfig(maxFileSize=1024*1024*50)
public class AdminUploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminUploadController() {
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
		
        String csvFile = "D:/workspace 2023/Terroist/Terriost/WebContent/upload/tweetstream.csv";
        ArrayList<String> dataArray =  new ArrayList<String>(); 
        ArrayList<TweetStream> tweetStreamList = new ArrayList<TweetStream>();
        //openCSV reader to parse the CSV file
        CSVReader reader = new CSVReader(new FileReader(csvFile));
       
        //nextLine array contains a an entire row of data,
        //Each element represents a cell in the spreadsheet.
        String[] nextLine;
        int count=0;
        //Iterate though the CSV while there are more lines to read
        while((nextLine = reader.readNext()) != null)
        {
     	   if(nextLine[0].startsWith("Tweet Id")&&nextLine[1].startsWith("Date")){
     		   nextLine[0]=null;
     		   nextLine[1]=null;
     		   nextLine[2]=null;
     		   nextLine[3]=null;
     		   nextLine[4]=null;
     		   nextLine[5]=null;
     		   nextLine[6]=null;
     		   nextLine[7]=null;
     		   nextLine[8]=null;
     		   nextLine[9]=null;
     		   nextLine[10]=null;
     		   nextLine[11]=null;
     		   nextLine[12]=null;
    		   nextLine[13]=null;
    		   nextLine[14]=null;
    		   nextLine[15]=null;
    		   nextLine[16]=null;
    		   nextLine[17]=null;
    		   nextLine[18]=null;
    		       		
     	   }else{
     		   dataArray.clear();
     		      //Iterate through the elements on this line
         	   TweetStream tstream = new TweetStream();
                for(String s : nextLine)
                {	s=s.trim();
                    dataArray.add(s);
                    //System.out.println(s);
                }
                System.out.println("\t"+dataArray.size());
                if(dataArray.size()==19){
             	   tstream.setTweet_Id(++count);
             	   tstream.setDate(dataArray.get(1));
             	   tstream.setHour(dataArray.get(2));
             	   tstream.setUserName(dataArray.get(3));
             	   tstream.setNickname(dataArray.get(4));
             	   tstream.setTweet_content(dataArray.get(6));
             	   tstream.setCountry(dataArray.get(11));
             	   tstream.setPlace(dataArray.get(12));
             	   tstream.setProfile_pic(dataArray.get(13));
             	   tstream.setFollowers(dataArray.get(14));
             	   tstream.setFollowing(dataArray.get(15));
             	   tstream.setLanguage(dataArray.get(17));
             	   tstream.setTweet_Url(dataArray.get(18));
             	   tweetStreamList.add(tstream);
             	  
                }
     	   }
                  
        }
        reader.close();
        AdminService adminService = new AdminServiceImpl();
        if(tweetStreamList!=null && tweetStreamList.size()>0){
        	if(adminService.addStreamDataset(tweetStreamList)){
        		CreateFiles cf=new CreateFiles();
        		String filePath= CreateFiles.createFile1(tweetStreamList);
        		System.out.print("Successfully added");
        	}
        	HttpSession session = request.getSession();
           	session.setAttribute("TweetStream",tweetStreamList);
           	RequestDispatcher rd = request.getRequestDispatcher("admin_listtweets.jsp");
           	rd.forward(request, response);
        }else{
        	request.setAttribute("ErrMsg","Please choose correct file");
           	RequestDispatcher rd = request.getRequestDispatcher("admin_upload.jsp");
           	rd.include(request, response);
        }
		
	}
	

}
