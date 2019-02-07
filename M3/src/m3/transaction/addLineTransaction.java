package m3.transaction;

import djf.AppTemplate;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import jtps.jTPS_Transaction;
import m3.data.DraggableLine;
import m3.data.m3Data;
import m3.gui.m3Workspace;

/**
 *
 * @author Sabrina To
 */
public class addLineTransaction implements jTPS_Transaction{
    DraggableLine line;
    m3Data data;
    m3Workspace ws;
    
    public addLineTransaction(DraggableLine line,AppTemplate app,m3Workspace ws)
    {
        data=(m3Data)app.getDataComponent();
        this.line=line;
        this.ws=ws;
    }
    
    @Override
    public void doTransaction()
    { 
        data.getShapes().add(line);
        data.getShapes().add(line.getLabel1());
        data.getShapes().add(line.getLabel2());
        ws.getLinesBox().getItems().add(line.getName());
        ws.getLinesBox().setValue(line.getName());
        ws.getEditLine().setTextFill(line.getColor());  
    }
    
     @Override
    public void undoTransaction()
    {
        data.getShapes().remove(line);
        data.getShapes().remove(line.getLabel1());
        data.getShapes().remove(line.getLabel2());
        ws.getLinesBox().getItems().remove(line.getName());
        ws.getLinesBox().setValue(""); 
        ws.getEditLine().setTextFill(Color.BLACK);
    }
}
