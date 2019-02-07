package m3.data;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import m3.gui.m3Workspace;
import djf.components.AppDataComponent;
import djf.AppTemplate;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import static m3.data.m3State.SELECTING;

/**
 * This class serves as the data management component for this application.
 *
 * @author Richard McKenna
 * @author Sabrina To
 * @version 1.0
 */
public class m3Data implements AppDataComponent {
    // FIRST THE THINGS THAT HAVE TO BE SAVED TO FILES
    
    // THESE ARE THE SHAPES TO DRAW
    ObservableList<Node> shapes;
    
    // THE BACKGROUND COLOR
    Color backgroundColor;
    String backgroundImagePath;

    // THIS IS THE SHAPE CURRENTLY SELECTED
    DraggableStation selectedStation;
    ImageView selectedImage;
    DraggableLabel selectedLabel;
    DraggableLine selectedLine;
    Label selectedLineEnd;
    Label found;
    DraggableLabel got;
    
    // CURRENT STATE OF THE APP
    m3State state;

    // THIS IS A SHARED REFERENCE TO THE APPLICATION
    AppTemplate app;
    
    // USE THIS WHEN THE SHAPE IS SELECTED
    Effect highlightedEffect;

    public static final String WHITE_HEX = "#FFFFFF";
    public static final String BLACK_HEX = "#000000";
    public static final String YELLOW_HEX = "#EEEE00";
    public static final Paint DEFAULT_BACKGROUND_COLOR = Paint.valueOf(WHITE_HEX);
    public static final Paint HIGHLIGHTED_COLOR = Paint.valueOf(YELLOW_HEX);
    public static final int HIGHLIGHTED_STROKE_THICKNESS = 3;

    /**
     * THis constructor creates the data manager and sets up the
     *
     *
     * @param initApp The application within which this data manager is serving.
     */
    public m3Data(AppTemplate initApp) {
	// KEEP THE APP FOR LATER
	app = initApp;

	selectedStation = null;
        selectedImage = null;
        selectedLabel = null;
        selectedLine = null;
        selectedLineEnd=null;

        backgroundColor=Color.WHITE;
        backgroundImagePath="";
	// THIS IS FOR THE SELECTED SHAPE
	DropShadow dropShadowEffect = new DropShadow();
	dropShadowEffect.setOffsetX(0.0f);
	dropShadowEffect.setOffsetY(0.0f);
	dropShadowEffect.setSpread(1.0);
	dropShadowEffect.setColor(Color.YELLOW);
	dropShadowEffect.setBlurType(BlurType.GAUSSIAN);
	dropShadowEffect.setRadius(15);
	highlightedEffect = dropShadowEffect;
    }
    
    public void setBackgroundColor(Color initBackgroundColor) {
	backgroundColor = initBackgroundColor;
	m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
	Pane map = workspace.getMap();
	BackgroundFill fill = new BackgroundFill(backgroundColor, null, null);
	Background background = new Background(fill);
	map.setBackground(background);
    }
    
    public void removeSelectedShape() {
	if (selectedStation != null) {
	    shapes.remove(selectedStation);
	    selectedStation = null;
	}
        else if (selectedImage != null) {
	    shapes.remove(selectedImage);
	    selectedImage = null;
	}
        else if(selectedLabel != null) {
	    shapes.remove(selectedLabel);
	    selectedLabel = null;
	}
        else if(selectedLine != null) {
	    shapes.remove(selectedLine);
	    selectedLine = null;
	}        
    }
 
    @Override
    public void resetData() {
	setState(SELECTING);
	selectedStation = null;
        selectedImage = null;
        selectedLabel = null;
        selectedLine = null;
	selectedLineEnd=null;
        
	shapes.clear();
	((m3Workspace)app.getWorkspaceComponent()).getMap().getChildren().clear();
    }

    
    public ImageView selectTopImage(int x, int y) {
        ImageView image=getTopImage(x, y);
        if(image==null)
            return null;
        if (image == selectedImage){
            ((Draggable)image).start(x,y);
	    return image;
        }
        
        if (selectedStation != null) {
	    unhighlightShape(selectedStation);
            selectedStation=null;
	}
        if (selectedLabel != null) {
	    unhighlightLabel(selectedLabel);
            selectedLabel=null;
	}
        if (selectedLine != null) {
	    unhighlightShape(selectedLine);
            selectedLine=null;
	}
        if (selectedLineEnd != null) {
	    unhighlightLabel(selectedLineEnd);
            selectedLineEnd=null;
	}
        
        if (selectedImage != null) {
	    unhighlightImage(selectedImage);
	}
        if (image != null) {
	    highlightImageView(image);
	}
	selectedImage = image;
	if (image != null) {
	    ((Draggable)image).start(x, y);
	}
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        //workspace.loadSelectedShapeSettings(null);
	return image;
    }
    
    public ImageView getTopImage(int x, int y) {
	for (int i = shapes.size() - 1; i >= 0; i--) {
            if((shapes.get(i)) instanceof ImageView)
            {
                ImageView image = (ImageView)shapes.get(i);
                if (image.contains(x, y)) {
                    return image;           
            }
	    }
	}
	return null;
    }
    
        public DraggableLabel selectTopLabel(int x, int y) {
        DraggableLabel label=getTopLabel(x, y);
        if(label==null)
            return null;
        if (label == selectedLabel){
            label.start(x,y);
	    return label;
        }
        
        if (selectedStation != null) {
	    unhighlightShape(selectedStation);
            selectedStation=null;
	}
        if (selectedImage != null) {
	    unhighlightImage(selectedImage);
            selectedImage=null;
	}
        if (selectedLine != null) {
	    unhighlightShape(selectedLine);
            selectedLine=null;
	}
        if (selectedLineEnd != null) {
	    unhighlightLabel(selectedLineEnd);
            selectedLineEnd=null;
	}
        
        if (selectedLabel != null) {
	    unhighlightLabel(selectedLabel);
	}
        if (label != null) {
	    highlightLabel(label);
	}
	selectedLabel = label;
	if (label != null) {
	    label.start(x, y);
	}
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        workspace.loadSelectedShapeSettings(label);
	return label;
    }
    
    public DraggableLabel getTopLabel(int x, int y) {
	for (int i = shapes.size() - 1; i >= 0; i--) {
            if((shapes.get(i)) instanceof DraggableLabel)
            {
                DraggableLabel label = (DraggableLabel)shapes.get(i);              
                label.setOnMousePressed(e->{got(label);});
                if (got!=null) {
                    DraggableLabel l=got;
                    got=null;
                    return l;           
                }
                
            }
	}
        return null;
    }
     
    public void got(DraggableLabel label)
    {
        got=label;
    } 
    
     public Label selectTopLineEnd(int x, int y) {
        Label label=getTopLineEnd(x, y);
        if(label==null)
            return null;
        if (label == selectedLabel){
            //label.start(x,y);
	    return label;
        }
        
        if (selectedStation != null) {
	    unhighlightShape(selectedStation);
            selectedStation=null;
	}
        if (selectedImage != null) {
	    unhighlightImage(selectedImage);
            selectedImage=null;
	}
        if (selectedLine != null) {
	    unhighlightShape(selectedLine);
            selectedLine=null;
	}
        if (selectedLabel != null) {
	    unhighlightLabel(selectedLabel);
            selectedLabel=null;
	}
        
        if (selectedLineEnd != null) {
	    unhighlightLabel(selectedLineEnd);
	}
        if (label != null) {
	    highlightLabel(label);
	}
	selectedLineEnd = label;
	if (label != null) {
	    //label.start(x, y);
	}
        m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
        workspace.loadSelectedShapeSettings(label);
	return label;
    }
    
    public Label getTopLineEnd(int x, int y) {
	for (int i = shapes.size() - 1; i >= 0; i--) {
            if((shapes.get(i)) instanceof Label&&(!((shapes.get(i)) instanceof DraggableLabel)))
            {
                Label label = (Label)shapes.get(i);
                label.setOnMousePressed(e->{yes(label);});
                if (found!=null) {
                    Label l=found;
                    found=null;
                    return l;           
                }
	    }
	}
	return null;
    }
    
    public void yes(Label label)
    {
        found=label;
    } 
   
    public DraggableLine selectTopLine(int x, int y) {
	Shape shape = getTopShape(x, y);
        if(shape==null)
        {
            return null;
        }
        if(!(shape instanceof DraggableLine))
        {
            return null;
        }
        
	if (shape == selectedLine){
            ((Draggable)shape).start(x,y);
	    return (DraggableLine)shape;
        }
        
        if (selectedStation != null) {
	    unhighlightShape(selectedStation);
            selectedStation=null;
	}
        if (selectedLabel != null) {
	    unhighlightLabel(selectedLabel);
            selectedLabel=null;
	}
	if (selectedImage != null) {
	    unhighlightImage(selectedImage);
            selectedImage=null;
        }
        if (selectedLineEnd != null) {
	    unhighlightLabel(selectedLineEnd);
            selectedLineEnd=null;
	}
        
	if (selectedLine != null) {
	    unhighlightShape(selectedLine);
	}
        
	if (shape != null) {
	    highlightShape(shape);
	    m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
	    workspace.loadSelectedShapeSettings((DraggableLine)shape);
	}
	selectedLine = (DraggableLine)shape;
	if (shape != null) {
	    ((Draggable)shape).start(x, y);
	}
	return (DraggableLine)shape;
    }

    public DraggableStation selectTopStation(int x, int y) {
	Shape shape = getTopShape(x, y);
        if(shape==null)
        {
            return null;
        }
        if(!(shape instanceof DraggableStation))
        {
            return null;
        }
        
	if (shape == selectedStation){
            ((Draggable)shape).start(x,y);
	    return (DraggableStation)shape;
        }
        
         if (selectedLine != null) {
	    unhighlightShape(selectedLine);
            selectedLine=null;
	}
        if (selectedLabel != null) {
	    unhighlightLabel(selectedLabel);
            selectedLabel=null;
	}
	if (selectedImage != null) {
	    unhighlightImage(selectedImage);
            selectedImage=null;
        }
        if (selectedLineEnd != null) {
	    unhighlightLabel(selectedLineEnd);
            selectedLineEnd=null;
	}
        
	if (selectedStation != null) {
	    unhighlightShape(selectedStation);
	}
        
	if (shape != null) {
	    highlightShape(shape);
	    m3Workspace workspace = (m3Workspace)app.getWorkspaceComponent();
	    workspace.loadSelectedShapeSettings((DraggableStation)shape);
	}
	selectedStation = (DraggableStation)shape;
	if (shape != null) {
	    ((Draggable)shape).start(x, y);
	}
	return (DraggableStation)shape;
    }
    
    public Shape getTopShape(int x, int y) {
	for (int i = shapes.size() - 1; i >= 0; i--) {
            if((shapes.get(i)) instanceof Shape)
            {
                Shape shape = (Shape)shapes.get(i);
	        if (shape.contains(x, y)) {
		      return shape;
                }
            }
	}
	return null;
    }
    
    //effect
    public void unhighlightImage(ImageView image) {
	selectedImage.setEffect(null);
    }
    
    public void highlightImageView(ImageView image) {
	image.setEffect(highlightedEffect);
    }
    public void unhighlightShape(Shape shape) {
	shape.setEffect(null);
    }
    
    public void highlightShape(Shape shape) {
	shape.setEffect(highlightedEffect);
    }
  
    public void unhighlightLabel(Label l) {
	l.setEffect(null);
    }
    
    public void highlightLabel(Label l) {
	l.setEffect(highlightedEffect);
    }
    
     public void unhighlightAll()
    {
        if(selectedStation!=null)
        {
            selectedStation.setEffect(null);
            selectedStation=null;
        }
        if(selectedImage!=null)
        {
            selectedImage.setEffect(null);
            selectedImage=null;
        }
        if(selectedLabel!=null)
        {
            selectedLabel.setEffect(null);
            selectedLabel=null;
        }
        if(selectedLine!=null)
        {
            selectedLine.setEffect(null);
            selectedLine=null;
        }
        if(selectedLineEnd!=null)
        {
            selectedLineEnd.setEffect(null);
            selectedLineEnd=null;
        }
    }
    //effect
    
    public void setShapes(ObservableList<Node> shapes) {
        this.shapes = shapes;
    }
    
    public ObservableList<Node> getShapes() {
	return shapes;
    }

    public DraggableStation getSelectedStation() {
        return selectedStation;
    }

    public ImageView getSelectedImage() {
        return selectedImage;
    }

    public DraggableLabel getSelectedLabel() {
        return selectedLabel;
    }

    public DraggableLine getSelectedLine() {
        return selectedLine;
    }

    public Label getSelectedLineEnd() {
        return selectedLineEnd;
    }

    public void addShape(Shape shapeToAdd) {
	shapes.add(shapeToAdd);
    }

    public void removeShape(Shape shapeToRemove) {
	shapes.remove(shapeToRemove);
    }

    public m3State getState() {
	return state;
    }

    public void setState(m3State initState) {
	state = initState;
    }

    public boolean isInState(m3State testState) {
	return state == testState;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public AppTemplate getApp() {
        return app;
    }   
    
    public String getFileName()
    {
        return app.getGUI().getFileController().getName();
    }
    
    public void setFileName(String name)
    {
       app.getGUI().getFileController().setName(name); 
    }

    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }

    public void setBackgroundImagePath(String backgroundImagePath) {
        this.backgroundImagePath = backgroundImagePath;
    }
}
