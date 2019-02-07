/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.transaction;

import jtps.jTPS_Transaction;
import m3.data.DraggableLine;
import m3.data.DraggableStation;

/**
 *
 * @author Sabrina To
 */
public class RemoveStationFromLineTransaction implements jTPS_Transaction{
    DraggableStation station;
    DraggableLine line;
    
    
    public RemoveStationFromLineTransaction(DraggableStation station,DraggableLine line)
    {
        this.station=station;
        this.line=line;   
    }
    
    @Override
    public void doTransaction()
    { 
        line.removeStation(station);
        station.getLines().remove(line);
    }
    
    @Override
    public void undoTransaction()
    {
        line.addStation(station);
        station.getLines().add(line);
    }
}
