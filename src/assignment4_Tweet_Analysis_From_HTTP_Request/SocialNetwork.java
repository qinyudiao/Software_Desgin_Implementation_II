package assignment4_Tweet_Analysis_From_HTTP_Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Social Network consists of methods that filter users matching a
 * condition.
 *
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {
	
	//public static ArrayList<TwitterUser> users = new ArrayList<>();

    /**
     * Get K most followed Users.
     *
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @param k
     *            integer of most popular followers to return
     * @return the set of usernames who are most mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getName()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like ethomaz@utexas.edu does NOT
     *         contain a mention of the username.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static List<String> findKMostFollower(List<Tweets> tweets, int k) {
        List<String> mostFollowers = new ArrayList<>();
        
        ArrayList<TwitterUser> users = getUsers(tweets);
        //System.out.println("users: " + users);
        
        users = getUsersRankedByFollowers(tweets, users);
        
        //System.out.println("Size: " + users.size() + " users: " + users);     
        if(users.size() <= k) {
        	for(TwitterUser user : users) {
        		mostFollowers.add(user.getUserName());
        	}
        }else {
        	for(TwitterUser user : users) {
        		mostFollowers.add(user.getUserName());
        		if( mostFollowers.size() >= k)
        			break;
        	}
        }
        
        List<String> userNames = new ArrayList<String>();
        userNames.add("chriscuit"); userNames.add("bob"); userNames.add("realdonaldtrump"); // userNames.add("a"); userNames.add("kevinyee"); userNames.add("person3"); 
//        userNames.add("link"); userNames.add("charizard");  userNames.add("kirby"); userNames.add("ulysse"); userNames.add("person4"); userNames.add("vinesaur"); 
//        userNames.add("b"); userNames.add("c"); userNames.add("d");
//        userNames.add("alex"); userNames.add("bob"); userNames.add("jackie");
//        userNames.add("judy"); userNames.add("julien"); userNames.add("jeje"); userNames.add("jordan");
//        userNames.add("ulysse"); userNames.add("lorine");
        for (String name : userNames) {
        	int index_of_him = getIndexByUserName(users, name);
        	System.out.println(name + "'s followers: " + users.get(index_of_him).getFollowers());      	
        }
        
        return mostFollowers;
    }
    
    
    //return a list mentions that is prefixed by "invalid_user_name_character+@" and suffixed by "invalid_user_name_character"
    public static List<String> getMentions(String text){
    	List<String> mentions = new ArrayList<>();
    	text = text.toLowerCase();
    	//System.out.println("text: " + text);
    	while(true) {
    		char preCh = ' ';
	    	int beginOfMention = text.indexOf("@");	    	
	    	if(beginOfMention == -1)
	    		break;
	    	else if(beginOfMention != 0) {
	    		preCh = text.charAt(beginOfMention - 1);
	    	}
	    	
	    	text = text.substring(beginOfMention + 1);
	    	//System.out.println("text_sub: " + text);
	    	//if(!TweetReader.checkContent(Character.toString(preCh))) { //true if preCh is a valid character for username	****assuming special characters****
	    	if(preCh == ' ') { //true if preCh is a space character	**** assuming only space ****
	    		//endOfMention is the index of the first char that is not valid 
		    	int endOfMention = TweetReader.getIndexOfFirstInvalidChar(text);
		    	boolean addMention = true;
		    	if(endOfMention == -1) {
		    		endOfMention = text.length();
		    	}
		    	else if ( (endOfMention < text.length()) ) { //**** assuming only space ****
		    		if (text.charAt(endOfMention) != ' ')
		    			addMention = false;
		    	}
		    	
		    	if(addMention) {
			    	String mention = text.substring(0, endOfMention).toLowerCase();
			    	if (!mentions.contains(mention))
			    		mentions.add(mention);
			    }
	    	}
    	}
    	//System.out.println("mentions: " + mentions);
    	return mentions;
    }
    
    public static ArrayList<TwitterUser> getUsers(List<Tweets> tweets){
        ArrayList<TwitterUser> users = new ArrayList<>();
        
        //Construct the list of users from tweets
        for(Tweets tweet: tweets) {
        	String name = tweet.getName().toLowerCase();
        	boolean isNewUser = true;
        	//find if this tweet is from a new userName
        	for(TwitterUser user : users) {
        		if(user.getUserName().equals(name)) {
        			isNewUser = false;
        			break;
        		}
        	}
        	//if not new add it to the users list.
        	if(isNewUser) {
        		TwitterUser user = new TwitterUser(name);
        		users.add(user);
        	}
        }
        return users;
    }
    
    public static int getIndexByUserName(ArrayList<TwitterUser> users, String name) {
    	int index = 0;
    	for(TwitterUser user : users) {
    		if(user.getUserName().equalsIgnoreCase(name))
    			return index;
    		index++;
    	}
    	
    	return -1;
    }
    
    private static ArrayList<TwitterUser> getUsersRankedByFollowers(List<Tweets> tweets, ArrayList<TwitterUser> users){
    	//int numMentions = 0; //*******
        for(Tweets tweet: tweets) {
        	List<String> mentions = getMentions(tweet.getText());
        	if(mentions.size()>0)
        	//find if a mention is a valid username
        	for(String mention  : mentions) {
        		boolean isValidMention = false;
        		for(TwitterUser user : users) {
	        		if(mention.equals(user.getUserName())) {
	        			isValidMention = true;
	        			break;
	        		}
        		}
        		if(!mention.equalsIgnoreCase(tweet.getName())) {//prevent user from following himself
	        		int index = getIndexByUserName(users, mention);
	            	if(isValidMention) { //add mention of this tweet to followers of username
	            		users.get(index).addFollower(tweet.getName().toLowerCase());
	            		//numMentions ++; // ***********
	            	}
        		}
        	}
        }
        //System.out.println("Number of Mentions: " + numMentions);
        
        Collections.sort(users, Collections.reverseOrder());  
        return users;
    }
    /**
     * Find all cliques in the social network.
     *
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     *
     * @return list of set of all cliques in the graph
     */
    List<Set<String>> findCliques(List<Tweets> tweets) {
        List<Set<String>> result = new ArrayList<Set<String>>();
        
        ArrayList<TwitterUser> users = getUsers(tweets); 
        users = getUsersRankedByFollowers(tweets, users);

        System.out.println("HAVE USERS" + users);
        
        for(TwitterUser user : users) {
        	String name = user.getUserName().toLowerCase();
        	Set<String> followers = user.getFollowers();
        	for(String followerName : followers) {
        		boolean areMutualFriends = users.get(getIndexByUserName(users, followerName.toLowerCase())).getFollowers().contains(name);
        		if(areMutualFriends)        			
        			result = formClique(users, result, name, followerName.toLowerCase());
        	}       	
        }
        
        return result;
    }
    
    /**
     * form cliques based on userName and followerName.
     * @return list of set of all cliques by now
     */
    private static List<Set<String>> formClique(ArrayList<TwitterUser> users, List<Set<String>> result, String userName, String followerName){
    	List<Set<String>> listOfCliques = result;
    	Set<String> mutualFriends = new HashSet<>();
    	boolean isNewClique = true;
    	
    	for(Set<String> clique : listOfCliques) {
    		// if they are both part of a clique, no need to add to the clique nor to add a new clique 
    		if( clique.contains(userName) && clique.contains(followerName) ) {
    			isNewClique = false;
    			break;
    		}
    		//if the clique has the username, check if the follower is friend with everyone else in the clique
    		if( clique.contains(userName) ) {
    			boolean followerIsFriendsWithEveryone = true;
    			for( String memberName : clique) {
    				if(!memberName.equals(userName)) {
    					if( (!users.get(getIndexByUserName(users, memberName)).getFollowers().contains(followerName)) //to be friends, they need to follow each other
    							|| (!users.get(getIndexByUserName(users, followerName)).getFollowers().contains(memberName)) )
    						followerIsFriendsWithEveryone = false;
    					else
    						mutualFriends.add(memberName);
    				}
    			}
    			if(followerIsFriendsWithEveryone) {
    				clique.add(followerName);
    				isNewClique = false;
    				break;
    			}
    		}
    		// same thing for follower
    		if( clique.contains(followerName) ) {
    			boolean userIsFriendsWithEveryone = true;
    			for( String memberName : clique) {
    				if(!memberName.equals(followerName)) {
    					if( (!users.get(getIndexByUserName(users, memberName)).getFollowers().contains(userName))
    							|| (!users.get(getIndexByUserName(users, userName)).getFollowers().contains(memberName)) )
    						userIsFriendsWithEveryone = false;
    					else
    						mutualFriends.add(memberName);
    				}
    			}
    			if(userIsFriendsWithEveryone) {
    				clique.add(userName);
    				isNewClique = false;
    			}
    		}
    	}
    	//if is new clique, add a new clique formed by the two users and their mutual friends
    	if(isNewClique) {
    		Set<String> newClique = new HashSet<>();
    		newClique.add(userName);
    		newClique.add(followerName);
    		for(String mutualFriend : mutualFriends) { //add all mutual friends to form the new clique, otherwise will miss the mutual friends won't be mentioned again
    			newClique.add(mutualFriend);
    		}
    		listOfCliques.add(newClique);
    	}
    		
    	return listOfCliques;   	
    }
        
}


