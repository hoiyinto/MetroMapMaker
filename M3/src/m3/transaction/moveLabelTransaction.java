package m3.transaction;

import jtps.jTPS_Transaction;
import m3.data.DraggableStation;
import m3.gui.m3Workspace;

/**
 *
 * @author Sabrina To
 */
public class moveLabelTransaction implements jTPS_Transaction {
    DraggableStation s;
    m3Workspace ws;
    
    public moveLabelTransaction (DraggableStation s,m3Workspace ws)
    {
        this.s=s;
        this.ws=ws;
    }
    
    @Override
    public void doTransaction()
    { 
        ws.move(s);
    }
    
    @Override
    public void undoTransaction()
    {
        ws.move(s);
    }
}
