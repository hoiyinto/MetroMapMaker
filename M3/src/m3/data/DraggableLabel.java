package m3.data;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class DraggableLabel extends Label implements Draggable{
    double startX;
    double startY;
    String text;
    boolean bold;
    boolean italics;
            
    
    public DraggableLabel(String text){
        this.text=text;
        startX=100;
        startY=100;
        setTranslateX(100);
        setTranslateY(100);
        setText(text);
        bold=false;
        italics=false;
    }
    
    public DraggableLabel(String text,double x,double y)
    {
        this.text=text;
        startX=x;
        startY=y;
        setTranslateX(x);
        setTranslateY(y);
        setText(text);
    }
    
    @Override
    public void start(int x, int y){
        startX = x;
	startY = y;
    };
    
    @Override
    public void drag(int x, int y) {
	double diffX = x - startX;
	double diffY = y - startY;
	double newX = getTranslateX() + diffX;
	double newY = getTranslateY() + diffY;
        
        setTranslateX(newX);
	setTranslateY(newY);
        
	startX = x;
	startY = y;
    }
    
    @Override
    public String getShapeType(){
        return LABEL;
    };
    
    @Override
    public double getX(){
        return startX;
    };
    
    @Override
    public double getY(){
        return startY;
    };
    
    @Override
    public m3State getStartingState(){
        return null;
    }; 
    
    @Override
    public void size(int x, int y){};
    
    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight){};

    public boolean isBold() {
        return bold;
    }

    public boolean isItalics() {
        return italics;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public void setItalics(boolean italics) {
        this.italics = italics;
    }
    
    public void setText(Color x,double bold,double italics,double size,String family)
    {
        this.bold=true;
        this.italics=true;
        FontWeight weight=FontWeight.BOLD;
        FontPosture posture=FontPosture.ITALIC;
        
        if(bold==0)
        {
            this.bold=false;
            weight=FontWeight.NORMAL;
        }
        if(italics==0)
        {
            this.italics=false;
            posture=FontPosture.REGULAR;
        }
        
        setFont(Font.font(family,weight,posture,size));
        setTextFill(x);
    }
    
}
