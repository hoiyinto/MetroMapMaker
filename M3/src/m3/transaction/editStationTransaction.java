package m3.transaction;

import djf.AppTemplate;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import jtps.jTPS_Transaction;
import m3.data.DraggableStation;
import m3.data.m3Data;
import m3.gui.m3Workspace;

/**
 *
 * @author Sabrina To
 */
public class editStationTransaction implements jTPS_Transaction {
    DraggableStation station;
    m3Data data;
    m3Workspace ws;
    Color oldColor;
    Color newColor;
    String name;
    
     public editStationTransaction(String name,Color newColor,AppTemplate app,m3Workspace ws)
    {
        this.name=name;
        this.newColor=newColor;
        data=(m3Data)app.getDataComponent();
        this.ws=ws;
        for(Node x:data.getShapes())
        {
            if(x instanceof DraggableStation)
            {
                if((((DraggableStation)x).getName()).equals(name))
                    station=(DraggableStation)x;
                    oldColor=((DraggableStation)x).getFillColor();
            }
        }     
    }
     
    @Override
    public void doTransaction()
    { 
        station.setColor(newColor);
        ws.getEditStation().setTextFill(newColor);
    }
    
    @Override
    public void undoTransaction()
    {
        station.setColor(oldColor);
        ws.getEditStation().setTextFill(oldColor);
    }
}
