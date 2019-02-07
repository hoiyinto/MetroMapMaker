package m3.transaction;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import jtps.jTPS_Transaction;
import m3.gui.m3Workspace;

public class textColorTransaction implements jTPS_Transaction{
    Label l;
    Color oldC;
    Color newC;
    m3Workspace ws;
    
    public textColorTransaction(Label label,Color newC,m3Workspace ws)
    {
        this.ws=ws;
        l=label;
        this.newC=newC;
        oldC=(Color)label.getTextFill();
    }
    
    @Override
    public void doTransaction()
    {
        l.setTextFill(newC);
        ws.getTextColor().setTextFill(newC);
    }
    
    public void undoTransaction()
    {
        l.setTextFill(oldC);
        ws.getTextColor().setTextFill(oldC);
    }
}