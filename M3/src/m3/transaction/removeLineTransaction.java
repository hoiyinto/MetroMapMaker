package m3.transaction;

import djf.AppTemplate;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import jtps.jTPS_Transaction;
import m3.data.DraggableLine;
import m3.data.DraggableStation;
import m3.data.m3Data;
import m3.gui.m3Workspace;

/**
 *
 * @author Sabrina To
 */
public class removeLineTransaction implements jTPS_Transaction{
    DraggableLine line;
    m3Data data;
    m3Workspace ws;
    String name;
    
    public removeLineTransaction(String name,AppTemplate app,m3Workspace ws)
    {
        data=(m3Data)app.getDataComponent();
        this.name=name;
        this.ws=ws;
        for(Node x:data.getShapes())
        {
            if(x instanceof DraggableLine)
            {
                if((((DraggableLine)x).getName()).equals(name))
                    line=(DraggableLine)x;
            }
        }     
    }
    
    @Override
    public void doTransaction()
    { 
        data.getShapes().remove(line);
        data.getShapes().remove(line.getLabel1());
        data.getShapes().remove(line.getLabel2());
        ws.getLinesBox().getItems().remove(line.getName());
        if(ws.getLinesBox().getItems().size()==0)
        {
            ws.getLinesBox().setValue("");
        }
        else
        {
            ws.getLinesBox().setValue(ws.getLinesBox().getItems().get(0));
        }
        ws.getEditLine().setTextFill(Color.BLACK); 
        
        for(DraggableStation x:line.getStations())
        {
            x.getLines().remove(line);
        }
        ws.getEditLine().setTextFill(Color.BLACK);
    }
    
    @Override
    public void undoTransaction()
    {
        data.getShapes().add(line);
        data.getShapes().add(line.getLabel1());
        data.getShapes().add(line.getLabel2());
        ws.getLinesBox().getItems().add(line.getName());
        ws.getLinesBox().setValue(line.getName());
        ws.getEditLine().setTextFill(line.getColor()); 
        for(DraggableStation x:line.getStations())
        {
            x.getLines().add(line);
        }
        ws.getEditLine().setTextFill(line.getColor()); 
    }   
}
