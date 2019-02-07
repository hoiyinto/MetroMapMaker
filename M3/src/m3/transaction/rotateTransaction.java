/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.transaction;

import jtps.jTPS_Transaction;
import m3.data.DraggableStation;

/**
 *
 * @author Sabrina To
 */
public class rotateTransaction implements jTPS_Transaction{
    DraggableStation station;
    
    public rotateTransaction(DraggableStation s)
    {
        station=s;
    }
    
     @Override
    public void doTransaction()
    { 
        if(station.getLabel().getRotate()==0)
        {
            station.getLabel().setRotate(90);
        }
        else
        {
            station.getLabel().setRotate(0);
        }
    }
    
    @Override
    public void undoTransaction()
    {
        if(station.getLabel().getRotate()==0)
        {
            station.getLabel().setRotate(90);
        }
        else
        {
            station.getLabel().setRotate(0);
        }
    }
}
