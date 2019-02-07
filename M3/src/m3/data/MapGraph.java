package m3.data;

import graph.AbstractEdgeWeightedGraph;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import wordgraph.Word;

/**
 *
 * @author Sabrina To
 */
public class MapGraph extends AbstractEdgeWeightedGraph<DraggableStation> {
     
    /**
     * Set of all the words in this WordGraph.
     */
    private final HashSet<DraggableStation> stationSet;
       
    /**
     * Map taking a word to a set of all the words accessible from it by
     * inserting or deleting a single letter.
     */
    private final HashMap<DraggableStation, Set<DraggableStation>> stationMap;
    
    /**
     * Initialize a WordGraph given an iterator to produce all the words.
     * 
     * @param words a collection of all the words.
     */
    public MapGraph(ObservableList<Node> nodes) {
        stationSet = new HashSet<>();
        stationMap = new HashMap<>();
        for(Node x:nodes){
            if(x instanceof DraggableStation)
                stationSet.add((DraggableStation)x);
        }
        for(DraggableStation s : stationSet) {
            for(DraggableLine x:s.getLines()) {
                int position=0;
                for(int i=0;i<x.getStations().size();i++){
                    if(x.getStations().get(i).equals(s))
                        position=i;
                } 
                if(position==0)
                {
                    if(x.getStations().size()>=2)
                    {
                        addToMap(x.getStations().get(1), s);
                        addToMap(s, x.getStations().get(1));
                    }    
                }
                else if(position==x.getStations().size()-1)
                {
                    if(x.getStations().size()>=2)
                    {
                        addToMap(x.getStations().get(x.getStations().size()-2), s);
                        addToMap(s, x.getStations().get(x.getStations().size()-2));
                    }
                }
                else
                {
                    if(x.getStations().size()>=3)
                    {
                        addToMap(x.getStations().get(position+1), s);
                        addToMap(s, x.getStations().get(position+1));
                        addToMap(x.getStations().get(position-1), s);
                        addToMap(s, x.getStations().get(position-1));
                    }
                }
            }
        }
    }
    
    /**
     * Insert a pair of words in the map.
     * 
     * @param from The word to be used as the key.
     * @param to The word to be added to the list of words associated
     * with the key.
     */
    private void addToMap(DraggableStation from, DraggableStation to) {
        Set<DraggableStation> s = stationMap.get(from);
        if(s == null) {
            s = new HashSet<>();
            stationMap.put(from, s);
        }
        s.add(to);
    }
    
    @Override
    public boolean containsNode(DraggableStation s) {
        return stationSet.contains(s);
    }
    
    @Override
    public boolean isAdjacent(DraggableStation from, DraggableStation to) {
        Set<DraggableStation> s = stationMap.get(from);
        if(s == null)
            return false;
        else
            return s.contains(to);
    }

    @Override
    public Iterator<DraggableStation> nodeIterator(DraggableStation from) {
        if(from == null)
            return(stationSet.iterator());
        else {
            Set<DraggableStation> l = stationMap.get(from);
            if(l == null)
                l = new HashSet<>();
            return l.iterator();
        }
    }
    
    @Override
    public double edgeWeight(DraggableStation from, DraggableStation to) {
        if(isAdjacent(from, to))
            return 1;
        else
            return Double.POSITIVE_INFINITY;
    }
}
