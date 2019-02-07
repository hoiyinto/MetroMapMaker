package m3.transaction;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import jtps.jTPS_Transaction;

/**
 *
 * @author Sabrina To
 */
public class fontFamilyTransaction implements jTPS_Transaction{
    Label l;
    String oldFontFamily;
    String newFontFamily;
    FontWeight weight;
    FontPosture posture;
    double size;
    
    
    public fontFamilyTransaction(Label l,String fontFamily,FontWeight weight,FontPosture posture,double size){
        oldFontFamily=l.getFont().getFamily();
        this.l=l;
        newFontFamily=fontFamily;
        this.weight=weight;
        this.posture=posture;
        this.size=size;
    }
    
    @Override
    public void doTransaction()
    { 
        l.setFont(Font.font(newFontFamily,weight,posture,size));
    }
    
    @Override
    public void undoTransaction()
    {
        l.setFont(Font.font(oldFontFamily,weight,posture,size));
    }
}