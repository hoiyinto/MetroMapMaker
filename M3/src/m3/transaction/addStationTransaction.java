/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.transaction;

import djf.AppTemplate;
import javafx.scene.paint.Color;
import jtps.jTPS_Transaction;
import m3.data.DraggableStation;
import m3.data.m3Data;
import m3.gui.m3Workspace;

/**
 *
 * @author Sabrina To
 */
public class addStationTransaction implements jTPS_Transaction{
    DraggableStation station;
    m3Data data;
    m3Workspace ws;
    
    public addStationTransaction(DraggableStation station,AppTemplate app,m3Workspace ws)
    {
        data=(m3Data)app.getDataComponent();
        this.station=station;
        this.ws=ws;
    }
    
    @Override
    public void doTransaction()
    { 
        data.getShapes().add(station);
        data.getShapes().add(station.getLabel());
        ws.getStationsBox().getItems().add(station.getName());
        ws.getStationsBox().setValue(station.getName());
        ws.getFromStation().getItems().add(station.getName());
        ws.getToStation().getItems().add(station.getName());
        
        ws.getEditStation().setTextFill(station.getFillColor());
    }
    
    @Override
    public void undoTransaction()
    {
        data.getShapes().remove(station);
        data.getShapes().remove(station.getLabel());
        ws.getStationsBox().getItems().remove(station.getName());
        ws.getStationsBox().setValue("");
        ws.getFromStation().getItems().remove(station.getName());
        ws.getToStation().getItems().remove(station.getName());
        
        ws.getEditStation().setTextFill(Color.BLACK);
    }
}
