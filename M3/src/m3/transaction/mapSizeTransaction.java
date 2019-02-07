/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.transaction;

import javafx.scene.layout.Pane;
import jtps.jTPS_Transaction;

/**
 *
 * @author Sabrina To
 */
public class mapSizeTransaction implements jTPS_Transaction{
    Pane map;
    double oldw;
    double oldh;
    double neww;
    double newh; 
    String instr;
    
    public mapSizeTransaction(Pane map,double oldw,double oldh,double neww,double newh,String instr)
    {
       this.map=map;
       this.oldw=oldw;
       this.oldh=oldh;  
       this.neww=neww;
       this.newh=newh;
       this.instr=instr;
    }
    
    @Override
    public void doTransaction()
    { 
       if(instr.equals("increase"))
       {
           map.setMaxSize(0,0);
           map.setMinSize(neww, newh);
       }
       else
       {
           map.setMinSize(0,0);
           map.setMaxSize(neww, newh);
       }
    }
    
    public void undoTransaction()
    {
        if(instr.equals("increase"))
        {
           map.setMinSize(0,0);
           map.setMaxSize(oldw, oldh);
        }
        else
        {
          map.setMaxSize(0,0);
          map.setMinSize(oldw, oldh);  
        }
        
    }
}
