
package m3.transaction;

import djf.AppTemplate;
import javafx.scene.Node;
import jtps.jTPS_Transaction;
import m3.data.DraggableLabel;
import m3.data.m3Data;

/**
 *
 * @author Sabrina To
 */
public class removeTransaction implements jTPS_Transaction{
    Node n;
    m3Data data;
    
    public removeTransaction (Node n,AppTemplate app)
    {
        data=(m3Data)app.getDataComponent();
        this.n=n;
    }
    
    @Override
    public void doTransaction()
    { 
        data.getShapes().remove(n);
    }
    
     @Override
    public void undoTransaction()
    {
        data.getShapes().add(n);
    }
    
}
