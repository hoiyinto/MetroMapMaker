/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.transaction;

import djf.AppTemplate;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import jtps.jTPS_Transaction;
import m3.data.DraggableLine;
import m3.data.m3Data;
import m3.gui.m3Workspace;

/**
 *
 * @author Sabrina To
 */
public class editLineTransaction implements jTPS_Transaction {
    DraggableLine line;
    m3Data data;
    m3Workspace ws;
    String oldName;
    String newName;
    Color oldColor;
    Color newColor;
    
    public editLineTransaction(String oldName,String newName,Color newColor,AppTemplate app,m3Workspace ws)
    {
        this.oldName=oldName;
        this.newName=newName;
        this.newColor=newColor;
        data=(m3Data)app.getDataComponent();
        this.ws=ws;
        for(Node x:data.getShapes())
        {
            if(x instanceof DraggableLine)
            {
                if((((DraggableLine)x).getName()).equals(oldName))
                    line=(DraggableLine)x;
                    oldColor=line.getColor();
            }
        }     
    }
    
    @Override
    public void doTransaction()
    { 
        line.setName(newName);
        line.setColor(newColor);
        ws.getLinesBox().getItems().remove(oldName);
        ws.getLinesBox().getItems().add(newName);
        ws.getEditLine().setTextFill(newColor); 
        ws.getLinesBox().setValue(newName);
    }
    
     @Override
    public void undoTransaction()
    {
        line.setName(oldName);
        line.setColor(oldColor);
        ws.getLinesBox().getItems().remove(newName);
        ws.getLinesBox().getItems().add(oldName);
        ws.getEditLine().setTextFill(oldColor);
        ws.getLinesBox().setValue(oldName);
    }
    
}
