package m3.transaction;

import javafx.scene.Node;
import jtps.jTPS_Transaction;
import m3.gui.m3Workspace;

/**
 *
 * @author Sabrina To
 */
public class boldTransaction implements jTPS_Transaction {
    Node n;
    m3Workspace ws;
    
    public boldTransaction(Node n,m3Workspace ws)
    {
        this.n=n;
        this.ws=ws;        
    }
    
    @Override
    public void doTransaction()
    { 
        ws.processBold(n);
    }
    
    @Override
    public void undoTransaction()
    {
        ws.processBold(n);
    }
}
