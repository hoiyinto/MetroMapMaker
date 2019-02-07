package m3.data;

import java.util.ArrayList;
import java.util.Objects;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class DraggableStation extends Circle implements Draggable,Comparable<DraggableStation>  {
    double startCenterX;
    double startCenterY;
    double radius;
    String name;
    Label label;
    double labelX;
    double labelY;
    Color fillColor;
    boolean bold;
    boolean italics;
    double rotate;
    double pos;
    
    ArrayList<DraggableLine> lines;
    
    //saving helper
    ArrayList<String> linesNames;
    
    public DraggableStation(String name){
        setCenterX(100);
	setCenterY(100);
        setRadius(10);
        radius=10;
        startCenterX = 100;
	startCenterY = 100;
        this.name=name;
        label= new Label(name);
        label.setTranslateX(90);
        label.setTranslateY(110);
        labelX=90;
        labelY=110;   
        setStroke(Color.CADETBLUE);
        setStrokeWidth(2);
        setFill(Color.ALICEBLUE);
        fillColor=Color.ALICEBLUE;
        
        lines=new ArrayList<DraggableLine>();
        linesNames=new ArrayList<String>();
        
        bold=false;
        italics=false;
        rotate=0;
        
        pos=0;
    }
    
     public DraggableStation(String name,double x,double y,double radius,double labelX,double labelY,Color fill,Color stroke,ArrayList<String> linesNames,double rotate,double pos){
         this.name=name;
         this.radius=radius;
         startCenterX = x;
	 startCenterY = y;
         this.labelX=labelX;
         this.labelY=labelY;
         fillColor=fill;
         
         setCenterX(x);
	 setCenterY(y);
         setRadius(radius);
         label= new Label(name);
         label.setTranslateX(labelX);
        label.setTranslateY(labelY);
        setStroke(stroke);
        setStrokeWidth(2);
        setFill(fill);  
        
        lines=new ArrayList<DraggableLine>();
        this.linesNames=linesNames;
        this.rotate=rotate;
        
        label.setRotate(rotate);
        this.pos=pos;
     }
    
    
    @Override
    public void start(int x, int y) {
	startCenterX = x;
	startCenterY = y;
        labelX=x;
        labelY=y+radius+10;        
    }
    
    @Override
    public void drag(int x, int y) {
	double diffX = x - startCenterX;
	double diffY = y - startCenterY;
	double newX = getCenterX() + diffX;
	double newY = getCenterY() + diffY;
	
        for(DraggableLine l:lines)
        {
            for(int i=0;i<l.getpoints().size();i+=2)
            {
                if(l.getpoints().get(i)==getCenterX()&&l.getpoints().get(i+1)==getCenterY() )
                {
                    l.getpoints().set(i, newX);
                    l.getpoints().set(i+1, newY);
                    l.getPoints().clear();
                    l.getPoints().addAll(l.getpoints());
                }
            }
        }
        
        startCenterX = newX;
	startCenterY = newY;
        setCenterX(newX);
	setCenterY(newY);
        
        if(pos==0){
            labelX=newX-radius;
            labelY=newY+radius; 
            label.setTranslateX(labelX);
            label.setTranslateY(labelY);
        }
        else if(pos==1)
        {
            labelX=newX-4*radius;
            labelY=newY+radius; 
            label.setTranslateX(labelX);
            label.setTranslateY(labelY);
        }
        else if(pos==2)
        {
            labelX=newX+3*radius;
            labelY=newY+radius; 
            label.setTranslateX(labelX);
            label.setTranslateY(labelY);
        }
        else if(pos==3)
        {
            labelX=newX+3*radius;
            labelY=newY-3*radius; 
            label.setTranslateX(labelX);
            label.setTranslateY(labelY);
        }
        else if(pos==4)
        {
            labelX=newX-4*radius;
            labelY=newY-3*radius; 
            label.setTranslateX(labelX);
            label.setTranslateY(labelY);
        }
        
    }
    
    public void setColor(Color c)
    {
        setFill(c);
        fillColor=c;
    }

    public Color getFillColor() {
        return fillColor;
    }
    
    
    @Override
    public String getShapeType(){
        return STATION;
    };
    
    @Override
    public double getX(){
        return startCenterX;
    };
    
    @Override
    public double getY(){
        return startCenterY;
    };

    public Label getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public double getLabelX() {
        return labelX;
    }

    public double getLabelY() {
        return labelY;
    }
    
    public void setR(double r)
    {
        radius=r;
        setRadius(r);
        labelX=startCenterX-radius;
        labelY=startCenterY+radius; 
        label.setTranslateX(labelX);
        label.setTranslateY(labelY);
    }
    
    @Override
    public m3State getStartingState(){
        return null;
    }; 
    
    @Override
    public void size(int x, int y){};
    
    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight){};
    
    public void updateLocation(double diffx,double diffy)
    {
        double temp1=startCenterX;
        double temp2=startCenterY;
        
        startCenterX=startCenterX+diffx;
        startCenterY=startCenterY+diffy;
        setCenterX(startCenterX);
	setCenterY(startCenterY);
        
        labelX=startCenterX-radius;
        labelY=startCenterY+radius; 
        label.setTranslateX(labelX);
        label.setTranslateY(labelY);
        
        for(DraggableLine l:lines)
        {
            for(int i=0;i<l.getpoints().size();i+=2)
            {
                if(l.getpoints().get(i)==temp1&&l.getpoints().get(i+1)==temp2)
                {
                    l.getpoints().set(i, startCenterX);
                    l.getpoints().set(i+1, startCenterY);
                    l.getPoints().clear();
                    l.getPoints().addAll(l.getpoints());
                }
            }
        }           
    }

    public ArrayList<DraggableLine> getLines() {
        return lines;
    }

    public ArrayList<String> getLinesNames() {
        return linesNames;
    }
    
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
    
    @Override
    public int compareTo(DraggableStation s ) {
        return name.compareTo(s.getName());
    }
    
    @Override
    public boolean equals(Object other) {
        if(other == null)
            return false;
        if(other == this)
            return true;
        if(other.getClass() != getClass())
            return false;
       DraggableStation os = (DraggableStation)other;
        return name.equals(os.getName());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.name);
        return hash;
    }
    
    @Override
    public String toString() {
        return name;
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
        
        label.setFont(Font.font(family,weight,posture,size));
        label.setTextFill(x);
    }

    public double getPos() {
        return pos;
    }

    public void setPos(double pos) {
        this.pos = pos;
    }

    public void setLabelX(double labelX) {
        this.labelX = labelX;
    }

    public void setLabelY(double labelY) {
        this.labelY = labelY;
    }
    
}
