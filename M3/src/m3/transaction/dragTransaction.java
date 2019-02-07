package m3.transaction;

import djf.AppTemplate;
import m3.data.Draggable;
import m3.data.m3Data;
import java.util.ArrayList;
import javafx.scene.Node;
import jtps.jTPS_Transaction;

public class dragTransaction implements jTPS_Transaction {
 
    Node move;    
    double oldx;
    double oldy;
    double newx;
    double newy;  
    m3Data data;
    
    public dragTransaction(Node move,double oldx,double oldy)
    {
       this.move=move;
       this.oldx=oldx;
       this.oldy=oldy;        
    }
    
    public dragTransaction(Node move,double oldx,double oldy,double newx,double newy)
    {
       this.move=move;
       this.oldx=oldx;
       this.oldy=oldy;  
       this.newx=newx;
       this.newy=newy;
    }
    
     @Override
    public void doTransaction()
    { 
        ((Draggable)move).drag((int)newx,(int)newy);
    }
    
    public void undoTransaction()
    {
        ((Draggable)move).drag((int)oldx,(int)oldy);
    }
    
    public void setMove(Node move) {
        this.move = move;
    }

    public void setOldx(int oldx) {
        this.oldx = oldx;
    }

    public void setOldy(int oldy) {
        this.oldy = oldy;
    }

    public void setNewx(int newx) {
        this.newx = newx;
    }

    public void setNewy(int newy) {
        this.newy = newy;
    }
}