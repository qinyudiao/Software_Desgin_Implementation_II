package assignment4_Tweet_Analysis_From_HTTP_Request;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Main {
    final static String URLEndpoint = "http://kevinstwitterclient2.azurewebsites.net/api/products";

    /**
     * We will not use your Main class to test your code
     * Feel free to modify this as much as you want
     * Here is an example of how you might test the code from main
     * for Problem 1 and Problem 2
     */
    @SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {

        // Problem 1: Returning Tweets from Server
        TweetReader reader = new TweetReader();
        List<Tweets> tweetsList = reader.readTweetsFromWeb(URLEndpoint);
        System.out.println("Size: " + tweetsList.size() + " TweetsList: " + tweetsList);

        // Problem 2:
        // Filter Tweets by Username
        Filter filter = new Filter();
        List<Tweets> filteredUser = filter.writtenBy(tweetsList,"kevinyee");
        System.out.println("Size: " + filteredUser.size() + " FilteredUser: " + filteredUser);

        // Filter by Timespan
        Instant testStart = Instant.parse("2017-11-11T00:00:00Z");
        Instant testEnd = Instant.parse("2017-11-12T12:00:00Z");
//        Instant testStart = Instant.parse("2017-11-1T00:00:00.731Z");
//        Instant testEnd = Instant.parse("2017-11-12T12:00:00Z");
        //System.out.println("InstantComparison: " + testEnd.compareTo(testStart));
        
//        Instant testStart = Instant.parse("2016-06-18T00:00:00Z");
//        Instant testEnd = Instant.parse("2016-06-18T12:00:00Z");
        Timespan timespan = new Timespan(testStart,testEnd);
        List<Tweets> filteredTimeSpan = filter.inTimespan(tweetsList, timespan);
        System.out.println("Size: " + filteredTimeSpan.size() + " FilteredTimespan: " + filteredTimeSpan);
//        for(Tweets t : filteredTimeSpan) {
//        	System.out.println(t);
//        }
        

        //Filter by words containinng
        List<Tweets> filteredWords = filter.containing(tweetsList,Arrays.asList("good","luck"));
        System.out.println("Size: " + filteredWords.size() + " FilteredWords: " + filteredWords);
        
//        List<Tweets> filteredWords2 = filter.containing(tweetsList,Arrays.asList("testcliquestest"));
//        System.out.println("Size: " + filteredWords2.size() + " FilteredWords2: " + filteredWords2);
//        for(Tweets t : filteredWords) {
//        	System.out.println(t);
//        }
        
        // Problem 3:
        //Find K most follower
        List<String> mostFollowers = SocialNetwork.findKMostFollower(tweetsList, 10);
        System.out.println("Size: " + mostFollowers.size() + " MostFollowers: " + mostFollowers);
        
        //Find cliques
        SocialNetwork s = new SocialNetwork();
        List<Set<String>> listOfCliques = s.findCliques(tweetsList);
        System.out.println("Size: " + listOfCliques.size() + " ListOfCliques " + listOfCliques);
        
//        List<Set<String>> listOfCliques2 = s.findCliques(filteredWords2);
//        System.out.println("Size: " + listOfCliques2.size() + " ListOfCliques " + listOfCliques2);
//              
    }
}
