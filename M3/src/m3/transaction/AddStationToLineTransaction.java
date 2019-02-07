package m3.transaction;

import jtps.jTPS_Transaction;
import m3.data.DraggableLine;
import m3.data.DraggableStation;

/**
 *
 * @author Sabrina To
 */
public class AddStationToLineTransaction implements jTPS_Transaction{
    DraggableStation station;
    DraggableLine line;
    
    
    public AddStationToLineTransaction(DraggableStation station,DraggableLine line)
    {
        this.station=station;
        this.line=line;   
    }
    
    @Override
    public void doTransaction()
    { 
        line.addStation(station);
        station.getLines().add(line);
    }
    
    @Override
    public void undoTransaction()
    {
        line.removeStation(station);
        station.getLines().remove(line);
    }
}
