/* EE422C Assignment #3 submission by
 * <Qinyu Diao>
 * <qd572>
 */


package assignment3_Poetic_Walks;

import java.io.File;
import java.io.IOException;

public class Main {
    /**
     * Example program using GraphPoet.
     */


        /**
         * Generate example poetry.
         *
         * @param args unused
         * @throws IOException if a poet corpus file cannot be found or read
         */
        public static void main(String[] args) throws IOException {
            final GraphPoet nimoy = new GraphPoet(new File("src/assignment3/corpus.txt"));
            System.out.println(nimoy.poem(new File("src/assignment3/input.txt")));
            
           // System.out.println(nimoy.affinity_graph.toString());
        }
        
        
}
