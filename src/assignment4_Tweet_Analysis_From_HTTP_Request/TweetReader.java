package assignment4_Tweet_Analysis_From_HTTP_Request;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


/**
 * TweetReader contains method used to return tweets from method
 * Do not change the method header
 */
public class TweetReader {
    /**
     * Find tweets written by a particular user.
     *
     * @param url
     *            url used to query a GET Request from the server
     * @return return list of tweets from the server
     *
     */
    public static List<Tweets> readTweetsFromWeb(String url) throws Exception
    {
        List<Tweets> tweetList = new ArrayList<>();
        
        OkHttpGetPost okHttpGP= new OkHttpGetPost();
        //System.out.println("Testing 1 - Send Http GET request");
        String str = okHttpGP.sendGet(url);     
        //System.out.println(str);
        /**
         * Each Tweet will contain:
         * int Id: Id will be a number from 1 to 2^31-1
         * String Name: name only contains a-zA-Z0-9 and _ everything else is invalid
         * String Date: represented in Java parseable format i.e YYYY-MM-DDT00:00:00Z
         * String Text: <= 140 characters
         * */
        
        boolean done = str.isEmpty();
        while( !done ) {
        	String id = "", name = "", date = "", text = "";
        	boolean tweetIsValid = true;
        	
        	//check id
        	int beginOfId = str.indexOf("{\"Id\":") + 6;
        	int endOfId = str.indexOf(",\"Name\":");
        	id = str.substring(beginOfId, endOfId);
        	tweetIsValid &= Integer.parseInt(id) >= 1 && Integer.parseInt(id) <= Integer.MAX_VALUE;
    		
        	//check name
        	int beginOfName = endOfId + 8;
        	int endOfName = str.indexOf(",\"Date\":");
        	if(str.charAt(beginOfName) == 'n') {
        		tweetIsValid = false;
        	}else {
            	name = str.substring(beginOfName+1, endOfName-1);
            	tweetIsValid &= checkContent(name);
            	//System.out.println("beginOfName: " + beginOfName + "  endOfName: " + endOfName);
        	}
        	
        	//check date
        	int beginOfDate = endOfName + 8;
        	int endOfDate = str.indexOf(",\"Text\":");
        	if(str.charAt(beginOfDate) == 'n') {
        		tweetIsValid = false;
        	}else {
        		date = str.substring(beginOfDate+1, endOfDate-1);
	        	try{
	        		Instant.parse(date);
	        	}catch(java.time.format.DateTimeParseException e){
	        		tweetIsValid = false;
	        	}
        	}
        	
        	//check text
        	int beginOfText = endOfDate + 8;
        	int endOfText = str.indexOf("},{\"Id\"");
        	if(endOfText == -1)
        		endOfText = str.length()-2;
        	if(str.charAt(beginOfText) == 'n') {
        		tweetIsValid = false;
        	}else {
	        	//System.out.println("beginOfText: " + beginOfText + "  endOfText: " + endOfText +  "  length: " + str.length());
        		if( endOfText-1-beginOfText-1 <= 140 )
        			text = str.substring(beginOfText+1, endOfText-1);
        		else
        			tweetIsValid = false;
        	}
        	
        	if(tweetIsValid)
        		addTweet(tweetList, id, name, date, text);
	  	    
	  	    str = str.substring(endOfText + 1);
	  	    if(str.length() == 0 || str.equals("]"))	done = true;
        }
        
        return tweetList;
    }
    
	private static void addTweet(List<Tweets> tweetList, String id, String name, String date, String text) {
		Tweets tweet = new Tweets();
		tweet.setId(Integer.parseInt(id));
		tweet.setDate(date);
		tweet.setName(name);
		tweet.setText(text);
		tweetList.add(tweet);
	}
	
	//String Name: name only contains a-zA-Z0-9 and _ everything else is invalid
	protected static boolean checkContent(String str) {
		for(char ch : str.toCharArray()) {
			if( !( (ch >= 65 && ch<=90) || (ch >= 97 && ch<=122) || (ch >= 48 && ch<=57) || (ch == 95) ) )
				return false;
		}
		return true;
	}
	
	protected static int getIndexOfFirstInvalidChar(String str) {
		int i = 0;
		for(char ch : str.toCharArray()) {
			if( !( (ch >= 65 && ch<=90) || (ch >= 97 && ch<=122) || (ch >= 48 && ch<=57) || (ch == 95) ) )
				return i;
			i++;
		}
		return -1;
	}

}
