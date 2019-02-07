package m3.transaction;

import djf.AppTemplate;
import javafx.scene.Node;
import jtps.jTPS_Transaction;
import m3.data.DraggableLine;
import m3.data.DraggableStation;
import m3.data.m3Data;

/**
 *
 * @author Sabrina To
 */
public class changeRadiusTransaction implements jTPS_Transaction {
    DraggableStation station;
    m3Data data;
    double oldRadius;
    double newRadius;
    
    public changeRadiusTransaction(AppTemplate app)
    {
        data=(m3Data)app.getDataComponent();
    }
    
    @Override
    public void doTransaction()
    { 
        station.setR(newRadius);
    }
    
    @Override
    public void undoTransaction()
    {
       station.setR(oldRadius);
    }

    public void setStation(String name) {
        for(Node x:data.getShapes())
        {
            if(x instanceof DraggableStation)
            {
                if((((DraggableStation)x).getName()).equals(name))
                    station=(DraggableStation)x;
                    oldRadius=station.getRadius();                   
            }
        }     
    }

    public void setNewRadius(double newRadius) {
        this.newRadius = newRadius;
    } 
}
