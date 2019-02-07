package m3.transaction;

import javafx.scene.Node;
import jtps.jTPS_Transaction;
import m3.data.DraggableLine;
import m3.data.m3Data;

/**
 *
 * @author Sabrina To
 */
public class dragLeftTransaction implements jTPS_Transaction {
    Node move;    
    double oldx;
    double oldy;
    double newx;
    double newy;  
    m3Data data;
    
    public dragLeftTransaction(Node move,double oldx,double oldy)
    {
       this.move=move;
       this.oldx=oldx;
       this.oldy=oldy;        
    }
    
     @Override
    public void doTransaction()
    { 
        ((DraggableLine)move).dragLeft((int)newx,(int)newy);
    }
    
    @Override
    public void undoTransaction()
    {
        ((DraggableLine)move).dragLeft((int)oldx,(int)oldy);
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

