package assignment3_Poetic_Walks;

//import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
//import java.util.Set;
//import java.util.List;

class Vertex<T>{
	private T name;
	private Map<T, Integer> edges = new HashMap<>();; // T is a vertex, Integer is the weight
	
    public Vertex(T name){
        this.name = name;        
    }
    
	public Vertex (T name, Map<T, Integer> edges) {
		this.name =name;
		this.edges = edges;
	}

    /** Returns the name of this vertex */
    public T getName(){
        return this.name;
    }
    
    /** Returns the weight of this vertex */
    public int getEdgesWeight(T next){
    	Map<T, Integer> curEdges = this.edges;
    	if(curEdges.get(next) == null)
    		curEdges.put(next,0);
    	return curEdges.get(next);
    }

    /** Adds an edge */
    public void addEdge(T edgeKey){
      int weight = getEdgesWeight(edgeKey);
      edges.put(edgeKey, weight+1);
  }
    
    public Map<T, Integer> getEdges(){
        return Collections.unmodifiableMap(edges);
    }

}