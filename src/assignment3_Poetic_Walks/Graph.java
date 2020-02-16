package assignment3_Poetic_Walks;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Random;

public class Graph<T> {

	 private final List<Vertex<T>> vertices_list = new ArrayList<>(); //list contains all vertices
	 
	 /** find a vertex's index in the vertices_list */
	 private int _get_vertex_index(T name){
		 for(int i = 0; i < vertices_list.size(); i++){
	            if (vertices_list.get(i).getName().equals(name)) {
	                return i;
	            }
	        }
	     return -1;
	 }
	
    /** add a vertex to this graph */
    public void addVertex(T vertex) {
        if (vertices_list().contains(vertex) ) {
            return;
        }
        
        Vertex<T> vertexObj = new Vertex<>(vertex);    
        vertices_list.add(vertexObj);
    }
    
    /** will return the random Most weighted existed bridge word */
    public String findBridgeWord(T curWord, T nextWord) {
        if ( !vertices_list().contains(curWord) ) return null;
        else if ( !vertices_list().contains(nextWord) ) return null;
        
    	int indexOfVertex = _get_vertex_index(curWord);
    	Vertex<T> curVertex = vertices_list.get(indexOfVertex);
    	
    	Map<T, Integer> possibleBridges = new HashMap<>();
    	
    	Map<T, Integer> mapOfEdges = curVertex.getEdges();
    	Set<T> keysCurWord = mapOfEdges.keySet();
    	for(T keyCur : keysCurWord) {
    		
    		int index = _get_vertex_index(keyCur);
        	Vertex<T> nextVertex = vertices_list.get(index);
        	
    		Map<T, Integer> mapOfEdgesOfNextVertex = nextVertex.getEdges();
    		Set<T> keysNextWord = mapOfEdgesOfNextVertex.keySet();
    		
    		for(T keyNext : keysNextWord) {
        		
        		if(keyNext.equals(nextWord)) {
        			possibleBridges.put(keyCur, mapOfEdges.get(keyCur));
        		}
        	}
    	}
       	if(possibleBridges.size()<=0)
    		return null;
    	
    	/* find the most weighed bridge word */
    	String bridgeWord = null;
    	int largestWeight = 0;
    	Set<T> keyBridgeWords = possibleBridges.keySet();
    	List<String> bridgeWords = new ArrayList<>(); //all final candidates of bridge word (with the most weight)
    	
    	for(T keyBridgeWord: keyBridgeWords) {
    		if( possibleBridges.get(keyBridgeWord) > largestWeight) {
    			largestWeight = possibleBridges.get(keyBridgeWord);
    			bridgeWords.clear();
    			bridgeWords.add((String) keyBridgeWord);
    		}
    		else if (possibleBridges.get(keyBridgeWord) == largestWeight) {
    			bridgeWords.add((String) keyBridgeWord);
    		}
    	}
    	
    	if(bridgeWords.size()<=0)
    		return null;
    	/*----------------------------------*/
    	
    	
    	/* get the random bridge word if tied at weight */
    	Random r = new Random();
    	int randomIndex = r.nextInt(bridgeWords.size());
    	bridgeWord = bridgeWords.get(randomIndex);
    	
        return bridgeWord;
    }
    
    /** add an Edge to Vertex */
    public void addEdgeToVertex(T cur, T next) {
    	int indexOfVertex = _get_vertex_index(cur);
    	Vertex<T> curVertex = vertices_list.get(indexOfVertex);
    	curVertex.addEdge(next);
    }
    
    /** return a set of names of all vertices */
    public Set<T> vertices_list() {
        return vertices_list.stream().map(Vertex::getName).collect(Collectors.toSet());
    }
    
    public int getWeight(T cur, T next) { //cur,next are Strings of keys of map
        Vertex<T> curVertex;
        
        Set<T> verticeNames = vertices_list();
        if (verticeNames.contains(cur)) {
            int curIndex = _get_vertex_index(cur);
            curVertex = vertices_list.get(curIndex);
        } 
        else {
            curVertex = new Vertex<>(cur);
            vertices_list.add(curVertex);
        }

        
        int curPrevWeight = curVertex.getEdgesWeight(next);
        return curPrevWeight;
    }
    
    /** used to print out the affinity graph, check adjancency list and weights */
    @Override public String toString(){
        return vertices_list.stream().filter(vertex -> vertex.getEdges().size() > 0).map(vertex -> vertex.getName().toString() 
        		+ " -> " 
        		+ vertex.getEdges()).collect(Collectors.joining("\n"));
    }
    
}

