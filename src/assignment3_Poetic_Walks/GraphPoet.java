/* EE422C Assignment #3 submission by
 * <Student Name>
 * <Student EID>
 */

package assignment3_Poetic_Walks;

import java.io.BufferedReader;
import java.io.File;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
//import java.util.stream.Collectors;

public class GraphPoet {
	
	
    private List<String> corpus_words; //list of words parsed from corpus
    protected Graph<String> affinity_graph; //a weighted graph consists of words
    
    /**
     *
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */

    public GraphPoet(File corpus) throws IOException {

        /* Read in the File and place into graph here */
        corpus_words = _get_words_from_file(corpus);
        //System.out.println(corpus_words);
        affinity_graph = _generate_affinity_graph(corpus_words);
    }

    /**
     * Generate a poem.
     *
     * @param input File from which to create the poem
     * @return poem (as described above)
     */
    public String poem(File input) throws IOException {

        /* Read in input and use graph to complete poem */
    	List<List<String>> listOfInputWords = _get_words_from_file_detect_new_line(input);
    	String poem = "";
    	for(int j = 0; j < listOfInputWords.get(0).size(); j++) {
    		String word = listOfInputWords.get(0).get(j);
    		
    		if( j+1 >= listOfInputWords.get(0).size()) {
    			poem = poem + " " + word;
    			break;
    		}
    		String nextWord = listOfInputWords.get(0).get(j+1);
    		String bridgeWord = null;
    		bridgeWord = affinity_graph.findBridgeWord(word.toLowerCase(), nextWord.toLowerCase());
    		if(bridgeWord != null) {
    			poem = poem + " " + word;
    			poem = poem + " " + bridgeWord;
    		}else {
    			poem = poem + " " + word;
    		}
    	}

    	
        return poem.trim();
    }
    
    /** extract words from file to a List */
    private List<String> _get_words_from_file(File corpus) throws IOException {
        List<String> words = new ArrayList<>();
        try (Scanner s = new Scanner(new BufferedReader(new FileReader(corpus)))) {
            while (s.hasNext()) {
                words.add(s.next().toLowerCase());
            }
        }
        return words;
    }
    
    /** extract words from input to a List of List */
    private List<List<String>> _get_words_from_file_detect_new_line(File input) throws IOException {
        List<List<String>> listOfWords = new ArrayList<>(5);
        @SuppressWarnings("resource")
		Scanner s = new Scanner(new BufferedReader(new FileReader(input)));
        do{
        	List<String> words = new ArrayList<>();
        	
            while (s.hasNext()) {
                words.add(s.next());
            }
            listOfWords.add(words);
        } while (false);
        
        return listOfWords;
    }
    
    private Graph<String> _generate_affinity_graph(List<String> corpus_words){
        Graph<String> graph = new Graph<String>();
        int size = corpus_words.size();
        
        for(int i=0; i<size; i++) {
            String cur = corpus_words.get(i);
            graph.addVertex(cur);
            if(i+1 >= size)	break;
            String next = corpus_words.get(i + 1);
            graph.addEdgeToVertex(cur, next);  //cur is already a vertex, add next as an edge to it.
        }
        return graph;
    }
    
    
}