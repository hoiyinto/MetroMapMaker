package m3.transaction;

import javafx.scene.paint.Color;
import jtps.jTPS_Transaction;
import m3.data.m3Data;

/**
 *
 * @author Sabrina To
 */
public class backgroundColorTransaction implements jTPS_Transaction {
    Color currentC;
    Color newC;
    m3Data data;
    
    public backgroundColorTransaction(Color cc,Color nc, m3Data d)
    {
        currentC=cc;
        newC=nc;
        data=d;
    }
    
    @Override
    public void doTransaction()
    { 
        data.setBackgroundColor(newC);
    }
    
    @Override
    public void undoTransaction()
    {
        data.setBackgroundColor(currentC);
    }
}

