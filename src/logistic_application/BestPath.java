
package logistic_application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/** 
* @ClassName: BestPath
* @Description: calculate the best(shortest) path among facilites
*/
public class BestPath {
    private HashMap<String,Integer> pairs;      
    private HashSet<String> seen;
    private ArrayList<String> lowPath;
    private HashMap<String, Facility> fclts;


    /** 
    * @Title: BestPath(c'tor) 
    * @Description: load Facilities information
    */
    public BestPath(HashMap<String, Facility> fclts){
        this.fclts=fclts;
        pairs=new HashMap<String,Integer>();
        seen=new HashSet<String>();
        lowPath=new ArrayList<String>();
    }
    
    /** 
    * @Title: findBestPath
    * @Description: get an array of facilities along BestPath
    */
    public ArrayList<String> findBestPath(String start,String end) {
        mapPairs(start);
        ArrayList<String> pathList=new ArrayList<String>();
        pathList.add(start);
        findPaths(start,end,pathList);
        return lowPath;
    }


    //Auxiliary method to create pair-distance HashMap, recursive
    private void mapPairs(String init) {
        seen.add(init);
        Collection<String> initNeighbors=fclts.get(init).getNeighbors();
        for (String neighbor:initNeighbors) {
            String pairS=init+"/"+neighbor;
            pairs.put(pairS, fclts.get(init).getNeighborDist(neighbor));
            
            if (!seen.contains(neighbor)) mapPairs(neighbor);
        }
    }
    
    //Auxiliary method to calculate the lowPath, recursive    
    private void findPaths(String start,String end,ArrayList<String> pathList){
        if (!start.equals(end)) {
            HashSet<String> fromHere=new HashSet<String>();
            
            Collection<String> pairsKeys=pairs.keySet();
            for (String pairS:pairsKeys) {
                String[] pair=pairS.split("/");
                if (pair[0].equals(start)) fromHere.add(pairS);
            }
            
            for (String pairS:fromHere) {
                String[] pair=pairS.split("/");
                if (!pathList.contains(pair[1])){
                    ArrayList<String> newPath=new ArrayList<String>(pathList);
                    newPath.add(pair[1]);
                    findPaths(pair[1],end,newPath);
                }
            }
        }
        else {
            if (lowPath.isEmpty()) lowPath=pathList;
            else {
                if (getLength(pathList) < getLength(lowPath)) {
                    lowPath=pathList;
                }
            }
        }
    }
    
    //Auxiliary method to calculate the length of one path      
    private int getLength(ArrayList<String> path){
        int length=0;
        int i=0;
        while (i<(path.size()-1)) {
            String pair=path.get(i)+"/"+path.get(i+1);
            length+=pairs.get(pair);
            i++; 
        }
        return length;
    }

    
}
