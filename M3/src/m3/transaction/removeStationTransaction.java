/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.transaction;

import djf.AppTemplate;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import jtps.jTPS_Transaction;
import m3.data.DraggableLine;
import m3.data.DraggableStation;
import m3.data.m3Data;
import m3.gui.m3Workspace;

/**
 *
 * @author Sabrina To
 */
public class removeStationTransaction implements jTPS_Transaction{
    DraggableStation station;
    m3Data data;
    m3Workspace ws;
    String name;
    
    public removeStationTransaction(String name,AppTemplate app,m3Workspace ws)
    {
        data=(m3Data)app.getDataComponent();
        this.name=name;
        this.ws=ws;
        for(Node x:data.getShapes())
        {
            if(x instanceof DraggableStation)
            {
                if((((DraggableStation)x).getName()).equals(name))
                    station=(DraggableStation)x;
            }
        }     
    }
     
     @Override
    public void doTransaction()
    { 
        data.getShapes().remove(station);
        data.getShapes().remove(station.getLabel());
        ws.getStationsBox().getItems().remove(station.getName());
        ws.getStationsBox().setValue("");
        ws.getFromStation().getItems().remove(station.getName());
        ws.getToStation().getItems().remove(station.getName());
        
        for(DraggableLine x:station.getLines())
        {
            x.removeStation(station);
        }
       ws.getEditStation().setTextFill(Color.BLACK);
    }
    
    @Override
    public void undoTransaction()
    {
        data.getShapes().add(station);
        data.getShapes().add(station.getLabel());
        ws.getStationsBox().getItems().add(station.getName());
        ws.getStationsBox().setValue(station.getName());
        ws.getFromStation().getItems().add(station.getName());
        ws.getToStation().getItems().add(station.getName());  
        
        for(DraggableLine x:station.getLines())
        {
            x.addStation(station);
        }
         ws.getEditStation().setTextFill(station.getFillColor());
    }
}
