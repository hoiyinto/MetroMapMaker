package m3.transaction;

import djf.AppTemplate;
import javafx.scene.image.Image;
import jtps.jTPS_Transaction;
import m3.data.DraggableImage;
import m3.data.m3Data;

/**
 *
 * @author Sabrina To
 */
public class imageTransaction implements jTPS_Transaction {
    String path;
    DraggableImage i;
    m3Data data;
    
    public imageTransaction(Image image,String path,AppTemplate app)
    {
        data=(m3Data)app.getDataComponent();
        i=new DraggableImage(image,path);
        this.path=path;
    }
    
    @Override
    public void doTransaction()
    { 
        data.getShapes().add(i);
    }
    
     @Override
    public void undoTransaction()
    {
        data.getShapes().remove(i);
    }
}


