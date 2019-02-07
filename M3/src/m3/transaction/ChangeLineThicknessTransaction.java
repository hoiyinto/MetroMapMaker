package m3.transaction;

import djf.AppTemplate;
import javafx.scene.Node;
import jtps.jTPS_Transaction;
import m3.data.DraggableLine;
import m3.data.m3Data;

/**
 *
 * @author Sabrina To
 */
public class ChangeLineThicknessTransaction implements jTPS_Transaction {
    DraggableLine line;
    m3Data data;
    double oldWidth;
    double newWidth;
    
    public ChangeLineThicknessTransaction(AppTemplate app)
    {
        data=(m3Data)app.getDataComponent();
    }
    
    @Override
    public void doTransaction()
    { 
        line.setW(newWidth);
    }
    
    @Override
    public void undoTransaction()
    {
       line.setW(oldWidth);
    }

    public void setNewWidth(double newWidth) {
        this.newWidth = newWidth;
    }

    public void setLine(String name){
       for(Node x:data.getShapes())
        {
            if(x instanceof DraggableLine)
            {
                if((((DraggableLine)x).getName()).equals(name))
                    line=(DraggableLine)x;
                    oldWidth=line.getStrokeWidth();                   
            }
        }     
    }
    
}
