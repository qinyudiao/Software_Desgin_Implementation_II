package assignment1_NLP;

//Name: Jack Diao
//Eid: qd572
//By signing your name and eid, you affirm that this is your own work.

/* Write a Java program that takes as input a sentence as a String, parses each word in the sentence, and identifies
what part of speech each word is. For this assignment, you will need to do some research to learn more about
part-of-speech tagging. Additionally, you will have to choose and download a Java-based Part-of-speech tagger
and incorporate into your own program. */

//import edu.stanford.nlp.util.logging.Redwood;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.util.List;
//
//import edu.stanford.nlp.ling.SentenceUtils;
//import edu.stanford.nlp.ling.TaggedWord;
//import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;


public class Problem3 {
	public static void main(String[] args) {
		String a = "I was slowly walking to the park with my over enthusiastic dog when he bit me, and I shouted, Ouch! ";
		//String a = "I like watching movies";
		MaxentTagger tagger =  new MaxentTagger("src/assignment1/english-bidirectional-distsim.tagger");
		String tagged = tagger.tagString(a);
		System.out.println(tagged);
	}
	
	public static String problem3(String s) {
		MaxentTagger tagger =  new MaxentTagger("src/assignment1/english-bidirectional-distsim.tagger");
		String tagged = tagger.tagString(s);
		return tagged.trim();
	}
}

