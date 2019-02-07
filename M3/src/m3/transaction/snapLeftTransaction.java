package m3.transaction;

import m3.data.Draggable;
import javafx.scene.Node;
import jtps.jTPS_Transaction;
import m3.data.DraggableLine;

public class snapLeftTransaction implements jTPS_Transaction {
 
    Node move;    
    double oldx;
    double oldy;
    double newx;
    double newy;  
    
    public snapLeftTransaction(Node move,double oldx,double oldy,double newx,double newy)
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
        ((DraggableLine)move).snapLeft((int)newx,(int)newy);
    }
    
    public void undoTransaction()
    {
        ((DraggableLine)move).snapLeft((int)oldx,(int)oldy);
    }
}