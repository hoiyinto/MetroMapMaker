package m3.data;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class DraggableImage extends ImageView implements Draggable
{
    double startX;
    double startY;
    Image image;
    String path;
    
      public DraggableImage(Image i,String path) {
        image=i;
        setImage(i);
	setX(100);
	setY(100);
        startX = 100;
	startY = 100;
        this.path=path;
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
	double newX = getX() + diffX;
	double newY = getY() + diffY;
        
        setX(newX);
	setY(newY);
        
	startX = x;
	startY = y;
    }
    
    @Override
    public String getShapeType(){
        return IMAGE;
    };
 
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    @Override
    public m3State getStartingState(){
        return null;
    }; 
    
    @Override
    public void size(int x, int y){};
    
    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight){};
}