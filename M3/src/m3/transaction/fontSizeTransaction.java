package m3.transaction;

import static java.awt.SystemColor.text;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import jtps.jTPS_Transaction;

/**
 *
 * @author Sabrina To
 */
public class fontSizeTransaction implements jTPS_Transaction {
    Label l;
    String fontFamily;
    FontWeight weight;
    FontPosture posture;
    double oldSize;
    double newSize;
    
    public fontSizeTransaction(Label l,String fontFamily,FontWeight weight,FontPosture posture,double newSize)
    {
        oldSize=l.getFont().getSize();
        this.l=l;
        this.fontFamily=fontFamily;
        this.weight=weight;
        this.posture=posture;
        this.newSize=newSize;
    }
    
    @Override
    public void doTransaction()
    { 
        l.setFont(Font.font(fontFamily,weight,posture,newSize));
    }
    
    @Override
    public void undoTransaction()
    {
        l.setFont(Font.font(fontFamily,weight,posture,oldSize));
    }
}
