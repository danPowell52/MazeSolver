/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mazesolver;

/**
 *
 * @author Daniel
 */
public class MazeSolver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //arguments mus be provided before running
        Maze testMaze = new Maze();
        testMaze.readMaze(args[0]);
        
        /*
        While not solved
            search for new neighbour
                is new neighbour the end
                    stop
            if none found go back
        */
       
        
    }
    
}
