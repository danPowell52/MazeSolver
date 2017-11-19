/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mazesolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 *
 * @author Daniel
 */
public class Maze {
    private int[] startPos = new int[2];
    private int[] endPos= new int[2];
    private int width;
    private int height;
    private int[][] maze;
    private Stack<int[]> path = new Stack<int[]>();
    private ArrayList<String> seen = new ArrayList<String>();
    
    
    
    //read in maze, start, end
    public void readMaze(String filename){
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

                String currentLine;
                int count = 0;
                while ((currentLine = br.readLine()) != null) {
                        //System.out.println(currentLine + "   "+ count);
                        if (count == 0){
                            String[] size = currentLine.split(" ");
                            width =Integer.parseInt(size[0]);
                            height =Integer.parseInt(size[1]);
                            
                            maze = new int[height][width];
                        }
                        if (count == 1){
                            String[] startCoords = currentLine.split(" ");
                            startPos[0] = Integer.parseInt(startCoords[0]);
                            startPos[1] = Integer.parseInt(startCoords[1]);
                        
                        }
                        if (count == 2){
                            String[] endCoords = currentLine.split(" ");
                            endPos[0] = Integer.parseInt(endCoords[0]);
                            endPos[1] = Integer.parseInt(endCoords[1]);
                            
                        
                        }
                        
                        //otherwise its the maze and read into the maze array
                        if (count > 2){
                            String[] size = currentLine.split(" ");
                            
                            int lineOfMaze = count - 3;
                            //System.out.println(lineOfMaze);
                            for (int i = 0; i < width; i++){
                                
                                maze[lineOfMaze][i] = Integer.parseInt(size[i]);
                                
                            }
                        }
                        
                        count++;
                }
                           

        } catch (IOException e) {
                e.printStackTrace();
        }
        solve(startPos, endPos);
        displayMaze();
        
    }
    
    
    
    
    //solve the maze
    public void solve(int[] startPos, int[] endPos){
        //depth first search to find path, return shortest path if one exists
        try {
            Boolean solved = false;

            String positionReferance = String.valueOf(startPos[0])+String.valueOf(startPos[1]);
            seen.add(positionReferance);
            path.push(startPos);
            int[] currentPos;

            currentPos = scout(startPos);            
            path.push(currentPos);
            positionReferance = String.valueOf(currentPos[0])+String.valueOf(currentPos[1]);
            seen.add(positionReferance);
            
            
            while(!solved){
                if (currentPos[0] == endPos[1] && currentPos[1] == endPos[0]){
                    //System.out.println("FIN");
                    solved = true;
                    break;
                }
                currentPos = scout(currentPos);            
                positionReferance = String.valueOf(currentPos[0])+String.valueOf(currentPos[1]);
                seen.add(positionReferance);                
                path.push(currentPos);
                
                

            }


            /**
            maze[startPos[0]][startPos[1]] = 5;
            maze[endPos[1]][endPos[0]] = 3;
            for (int i = 0; i < height; i++){
                for (int j = 0; j < width ;j++ ){
                    System.out.print(maze[i][j]+ " ");
                }
                System.out.println("");
            }
             */
        } catch(Exception e){
            e.printStackTrace();
            maze[startPos[1]][startPos[0]] = 5;
            maze[endPos[1]][endPos[0]] = 3;
            for (int i = 0; i < height; i++){
                for (int j = 0; j < width ;j++ ){
                    System.out.print(maze[i][j]+ " ");
                }
                System.out.println("");
            }
        }
    }
    
    
 
    
    public int[] scout(int[] currentPos){
        String positionReferance;
        int[] posID = new int[2];
        
        //check West        
        if (maze[currentPos[0]][currentPos[1]-1] == 0){
            
            posID[0] = currentPos[0] ;
            posID[1] = currentPos[1]-1;
            positionReferance = String.valueOf(posID[0])+String.valueOf(posID[1]);
            if (!seen.contains(positionReferance)){
                //if you go back and restart you need to re-add the position you just popped
                if (seen.contains(String.valueOf(currentPos[0])+String.valueOf(currentPos[1]))){
                    path.push(currentPos);
                }
                return posID;
            }
        }
        //check South      
        if (maze[currentPos[0]+1][currentPos[1]] == 0){            
            posID[0] = currentPos[0]+1 ;
            posID[1] = currentPos[1];
            positionReferance = String.valueOf(posID[0])+String.valueOf(posID[1]);
            if (!seen.contains(positionReferance)){
                if (seen.contains(String.valueOf(currentPos[0])+String.valueOf(currentPos[1]))){
                    path.push(currentPos);
                }
                return posID;
            }
        }
        //check East       
        if (maze[currentPos[0]][currentPos[1]+1] == 0){
            
            posID[0] = currentPos[0] ;
            posID[1] = currentPos[1]+1;
            positionReferance = String.valueOf(posID[0])+String.valueOf(posID[1]);
            if (!seen.contains(positionReferance)){
                if (seen.contains(String.valueOf(currentPos[0])+String.valueOf(currentPos[1]))){
                    path.push(currentPos);
                }
                return posID;
            }
        }

        
        if (maze[currentPos[0]-1][currentPos[1]] == 0){
            posID[0] = currentPos[0]-1 ;
            posID[1] = currentPos[1];
            positionReferance = String.valueOf(posID[0])+String.valueOf(posID[1]);
            if (!seen.contains(positionReferance)){
                if (seen.contains(String.valueOf(currentPos[0])+String.valueOf(currentPos[1]))){
                    path.push(currentPos);
                }
                return posID;
            }
        }
        
        //if no neighbours are found go back and look for next branch to take       
        try {          
            return scout(path.pop());
        }catch (EmptyStackException e){
            System.out.println("Maze has no solution");
        }
        return null;
    }
    
    public void displayMaze(){
        //output path to end
        for (int[] coords: path){
            maze[coords[0]][coords[1]] = 8;
        }
        maze[startPos[0]][startPos[1]] = 5;
        maze[endPos[1]][endPos[0]] = 3;
        //convert to desired output form
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width ;j++ ){
                
                if (maze[i][j] == 1){
                    System.out.print("# ");
                }
                if (maze[i][j] == 0){
                    System.out.print("  ");
                }
                if (maze[i][j] == 3){
                    System.out.print("E ");
                }
                if (maze[i][j] == 5){
                    System.out.print("S ");
                }
                if (maze[i][j] == 8){
                    System.out.print("X ");
                }
            }
            System.out.println("");
        }
    }
}
