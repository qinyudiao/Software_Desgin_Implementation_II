package assignment4_Tweet_Analysis_From_HTTP_Request;

import java.util.HashSet;
import java.util.Set;

public class TwitterUser implements Comparable<TwitterUser>  {
	

	
	public String name;
	
	public Set<String> followers = new HashSet<String>();
	
	
	public TwitterUser(String name){
		this.name = name;
	}
	
	public TwitterUser(Set<String> followers){
		this.followers = followers;
	}
	
	public TwitterUser(String name, Set<String> followers){
		this.name = name;
		this.followers = followers;
	}
	
	public String getUserName() {
		return name;
	}
	
	public void setUserName(String name) {
		this.name = name;
	}
	
	public Set<String> getFollowers() {
		return followers;
	}
	
	public void addFollower(String follower) {
		followers.add(follower);
	}
	
	public Integer getNumberOfFollowers() {	
		return followers.size();
	}
	
	@Override
	public int compareTo(TwitterUser user) {
	    return this.getNumberOfFollowers().compareTo(user.getNumberOfFollowers());
	}
	
	@Override
	public String toString() {
		
		return getUserName() + " " + getNumberOfFollowers();
	}
}
