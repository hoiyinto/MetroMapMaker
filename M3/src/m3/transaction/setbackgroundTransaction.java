package m3.transaction;

import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import jtps.jTPS_Transaction;
import m3.data.m3Data;

/**
 *
 * @author Sabrina To
 */
public class setbackgroundTransaction implements jTPS_Transaction{
    Background oldb;
    Background newb;
    Pane map;
    
    String oldpath;
    String newpath;
    m3Data data;
    
    public setbackgroundTransaction(String path,m3Data data,Background newb,Pane map)
    {
        this.data=data;
        oldpath=data.getBackgroundImagePath();
        newpath=path;
        this.newb=newb;
        this.map=map;
        oldb=map.getBackground();
    }
    
    @Override
    public void doTransaction()
    { 
        map.setBackground(newb); 
        data.setBackgroundImagePath(newpath);
    }
    
    @Override
    public void undoTransaction()
    { 
        map.setBackground(oldb);
        data.setBackgroundImagePath(oldpath);
    }
}
