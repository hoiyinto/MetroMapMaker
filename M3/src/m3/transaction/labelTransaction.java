package m3.transaction;

import djf.AppTemplate;
import javafx.scene.image.Image;
import jtps.jTPS_Transaction;
import m3.data.DraggableImage;
import m3.data.DraggableLabel;
import m3.data.m3Data;

/**
 *
 * @author Sabrina To
 */
public class labelTransaction implements jTPS_Transaction {
    DraggableLabel l;
    m3Data data;
   
    public labelTransaction (String text,AppTemplate app)
    {
        data=(m3Data)app.getDataComponent();
        l=new DraggableLabel(text);
    }
    
    @Override
    public void doTransaction()
    { 
        data.getShapes().add(l);
    }
    
     @Override
    public void undoTransaction()
    {
        data.getShapes().remove(l);
    }
}
