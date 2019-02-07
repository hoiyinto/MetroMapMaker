package m3.data;

import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class DraggableLine extends Polyline implements Draggable {
    double startCenterX;
    double startCenterY;
    
    double startX;
    double startY;
    double endX;
    double endY;
    
    ArrayList<DraggableStation> stations;
    ArrayList<String> stationsNames;
    ArrayList<Double> points;
    boolean circular;
    
    String name;
    Color color;
    Label label1;
    double label1X;
    double label1Y;
    Label label2;
    double label2X;
    double label2Y;
    double width;
    
    boolean bold1;
    boolean italics1;
    boolean bold2;
    boolean italics2;
    
    public DraggableLine(String name,Color color)
    {
        this.name=name;
        this.color=color;
        startX=100;
        startY=100;
        endX=600;
        endY=100;   
        
        stationsNames=new ArrayList<String>();
        stations=new ArrayList<DraggableStation>();
        points=new ArrayList<Double>();
        points.add(100.0);
        points.add(100.0);
        points.add(600.0);
        points.add(100.0);
        getPoints().addAll(points);
        circular=false;
        
        setStroke(color);
        setStrokeWidth(5);
        width=5;
        label1=new Label(name);
        label2=new Label(name);
        
        label1X=70;
        label1Y=90;
        label1X=610;
        label1Y=90;
        
        label1.setTranslateX(70);
        label1.setTranslateY(90);
        label2.setTranslateX(610);
        label2.setTranslateY(90);
        
        bold1=false;
        italics1=false;
        bold2=false;
        italics2=false;
    }
    
    public DraggableLine(String name,double sx,double sy,double ex,double ey,double w,double l1x,double l1y,double l2x,double l2y,Color c,ArrayList<Double> points, ArrayList<String> stationsNames,boolean cir)
    {
        this.name=name;
        startX=sx;
        startY=sy;
        endX=ex;
        endY=ey; 
        width=w;
        label1=new Label(name);
        label2=new Label(name);
        color=c;
        setStroke(c);
        setStrokeWidth(w);
        
        label1X=l1x;
        label1Y=l1y;
        label1X=l2x;
        label1Y=l2y;
        
        label1.setTranslateX(l1x);
        label1.setTranslateY(l1y);
        label2.setTranslateX(l2x);
        label2.setTranslateY(l2y); 
        
        this.points=points;
        this.stationsNames=stationsNames;
        stations=new ArrayList<DraggableStation>();
        circular=cir;
        getPoints().addAll(points);
        
    }
    
    @Override
    public void start(int x, int y) {
	startCenterX = x;
	startCenterY = y;      
    }
    
    @Override
    public void drag(int x, int y) {
	double diffX = x - startCenterX;
	double diffY = y - startCenterY;
	double newStartX = startX + diffX;
	double newStartY = startY + diffY;
        double newEndX = endX + diffX;
	double newEndY = endY + diffY;
	
        startX=newStartX;
        startY=newStartY;
        endX=newEndX;
        endY=newEndY;
        
        double temp;
        for(int i=0;i<points.size();i++){
            if(i%2==0){
                temp=points.get(i)+diffX;
                points.set(i, temp);
            }
            else{
                temp=points.get(i)+diffY;
                points.set(i, temp);
            }
        }    
        getPoints().clear();
        getPoints().addAll(points);
        
        for(int i=0;i<stations.size();i++)
        {
            stations.get(i).updateLocation(diffX, diffY);
        }
        
        label1X=newStartX-30;
        label1Y=newStartY-10;
        label2X=newEndX+10;
        label2Y=newEndY-10;
        label1.setTranslateX(newStartX-30);
        label1.setTranslateY(newStartY-10);
        label2.setTranslateX(newEndX+10);
        label2.setTranslateY(newEndY-10);
        
        startCenterX = x;
	startCenterY = y;  
    }
    
    public void dragLeft(int x, int y) {
	double diffX = x - startCenterX;
	double diffY = y - startCenterY;
	double newStartX = startX + diffX;
	double newStartY = startY + diffY;
	
        startX=newStartX;
        startY=newStartY;
        
        points.set(0, newStartX);
        points.set(1, newStartY); 
        getPoints().clear();
        getPoints().addAll(points);
        
        label1X=newStartX-30;
        label1Y=newStartY-10;
        label1.setTranslateX(newStartX-30);
        label1.setTranslateY(newStartY-10);
        
        startCenterX = x;
	startCenterY = y;  
    }
    
    public void snapLeft(double x,double y)
    {
        startX=x;
        startY=y;
        
        points.set(0, x);
        points.set(1, y); 
        getPoints().clear();
        getPoints().addAll(points);
        
        label1X=x-30;
        label1Y=y-10;
        label1.setTranslateX(x-30);
        label1.setTranslateY(y-10);
    }
    
    public void dragRight(int x, int y) {
	double diffX = x - startCenterX;
	double diffY = y - startCenterY;
        double newEndX = endX + diffX;
	double newEndY = endY + diffY;
	
        endX=newEndX;
        endY=newEndY;
        
        points.set(points.size()-2,  newEndX);
        points.set(points.size()-1,  newEndY);   
        getPoints().clear();
        getPoints().addAll(points);
        
        label2X=newEndX+10;
        label2Y=newEndY-10;
        
        label2.setTranslateX(newEndX+10);
        label2.setTranslateY(newEndY-10);
        
        startCenterX = x;
	startCenterY = y;  
    } 
    
    public void snapRight(double x,double y)
    {
        endX=x;
        endY=y;
        
        points.set(points.size()-2,  x);
        points.set(points.size()-1,  y);   
        getPoints().clear();
        getPoints().addAll(points);
        
        label2X=x+10;
        label2Y=y-10;
        
        label2.setTranslateX(x+10);
        label2.setTranslateY(y-10);
    }

    @Override
    public String getShapeType(){
        return LINE;
    };
    
    public String getName(){
        return name;
    }

    public Color getColor() {
        return color;
    }

    public Label getLabel1() {
        return label1;
    }

    public Label getLabel2() {
        return label2;
    }
    
    @Override
    public double getX(){
        return startCenterX;
    };
    
    @Override
    public double getY(){
        return startCenterY;
    };

    public double getLabel1X() {
        return label1X;
    }

    public double getLabel1Y() {
        return label1Y;
    }

    public double getLabel2X() {
        return label2X;
    }

    public double getLabel2Y() {
        return label2Y;
    }
    
    
    @Override
    public m3State getStartingState(){
        return null;
    }; 
    
    public void setColor(Color c)
    {
        color=c;
        setStroke(c);
    }

    public void setName(String name) {
        this.name = name;
        label1.setText(name);
        label2.setText(name);
    }
    
    public void setW(double w)
    {
        setStrokeWidth(w);
        width=w;
    }
    
    public boolean isFirstLabel(Label l)
    {
        if(l.getTranslateX()!=label1X)
            return false;
        if(l.getTranslateY()!=label1Y)
            return false;
        return true;
    }
    
    public boolean isSecondLabel(Label l)
    {
        if(l.getTranslateX()!=label2X)
            return false;
        if(l.getTranslateY()!=label2Y)
            return false;
        return true;
    }
    
    @Override
    public void size(int x, int y){};
    
    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight){};

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public double getEndX() {
        return endX;
    }

    public double getEndY() {
        return endY;
    }
    
    public void addStation(DraggableStation newStation)
    {
        if(circular==true){
            points.remove(points.size()-1);
            points.remove(points.size()-1);
            points.remove(points.size()-1);
            points.remove(points.size()-1);
            points.add(newStation.getCenterX());
            points.add(newStation.getCenterY());
            points.add(stations.get(stations.size()-1).getCenterX());
            points.add(stations.get(stations.size()-1).getCenterY());
            points.add(endX);
            points.add(endY);
            getPoints().clear();
            getPoints().addAll(points);
            DraggableStation end=stations.get(stations.size()-1);
            stations.remove(end);
            stations.add(newStation);
            stations.add(end);
        }
        else if(stations.size()>=1&&newStation.equals(stations.get(0)))
        {
            stations.add(newStation);
            points.remove(points.size()-1);
            points.remove(points.size()-1);
            points.add(newStation.getCenterX());
            points.add(newStation.getCenterY());
            points.add(endX);
            points.add(endY);   
            getPoints().clear();
            getPoints().addAll(points);
            circular=true;
        }
        else
        {
            stations.add(newStation);
            points.remove(points.size()-1);
            points.remove(points.size()-1);
            points.add(newStation.getCenterX());
            points.add(newStation.getCenterY());
            points.add(endX);
            points.add(endY);   
            getPoints().clear();
            getPoints().addAll(points);
        }
    }
    
    public void removeStation(DraggableStation s)
    {
        if(circular&&s.equals(stations.get(0)))
        {
            stations.remove(stations.size()-1);
            points.remove(points.size()-1);
            points.remove(points.size()-1);
            points.remove(points.size()-1);
            points.remove(points.size()-1);
            points.add(endX);
            points.add(endY);
            getPoints().clear();
            getPoints().addAll(points);
            circular=false;
        }
        else{
            stations.remove(s); 
            points.remove(s.getCenterX());
            points.remove(s.getCenterY());  
            getPoints().clear();
            getPoints().addAll(points);
        }
    }

    public ArrayList<DraggableStation> getStations() {
        return stations;
    }

    public ArrayList<Double> getpoints() {
        return points;
    }

    public ArrayList<String> getStationsNames() {
        return stationsNames;
    }

    public boolean isCircular() {
        return circular;
    }
    
    public boolean isBold1() {
        return bold1;
    }

    public boolean isItalics1() {
        return italics1;
    }

    public void setBold1(boolean bold) {
        this.bold1 = bold;
    }

    public void setItalics1(boolean italics) {
        this.italics1 = italics;
    }
    
     public boolean isBold2() {
        return bold2;
    }

    public boolean isItalics2() {
        return italics2;
    }

    public void setBold2(boolean bold) {
        this.bold2 = bold;
    }

    public void setItalics2(boolean italics) {
        this.italics2 = italics;
    }
    
    public void setText(Color x,double bold,double italics,double size,String family,Color y,double bold2,double italics2,double size2,String family2)
    {
        this.bold1=true;
        this.italics1=true;
        FontWeight weight1=FontWeight.BOLD;
        FontPosture posture1=FontPosture.ITALIC;
        
        if(bold==0)
        {
            this.bold1=false;
            weight1=FontWeight.NORMAL;
        }
        if(italics==0)
        {
            this.italics1=false;
            posture1=FontPosture.REGULAR;
        }
        
        label1.setFont(Font.font(family,weight1,posture1,size));
        label1.setTextFill(x);
        
        this.bold2=true;
        this.italics2=true;
        FontWeight weight2=FontWeight.BOLD;
        FontPosture posture2=FontPosture.ITALIC;
        
        if(bold2==0)
        {
            this.bold2=false;
            weight2=FontWeight.NORMAL;
        }
        if(italics2==0)
        {
            this.italics2=false;
            posture2=FontPosture.REGULAR;
        }
        
        label2.setFont(Font.font(family2,weight2,posture2,size2));
        label2.setTextFill(y);
    }

    public void setCircular(boolean circular) {
        this.circular = circular;
    }
    
}
