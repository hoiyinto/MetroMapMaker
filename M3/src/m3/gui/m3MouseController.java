package m3.gui;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import m3.data.m3Data;
import m3.data.Draggable;
import m3.data.m3State;
import djf.AppTemplate;
import javafx.scene.Node;
import javafx.scene.control.Label;
import static m3.data.m3State.DRAGGING_IMAGE;
import m3.transaction.dragTransaction;
import javafx.scene.image.ImageView;
import m3.data.DraggableLabel;
import m3.data.DraggableLine;
import m3.data.DraggableStation;
import static m3.data.m3State.ADDING_STATION;
import static m3.data.m3State.DRAGGING_LABEL;
import static m3.data.m3State.DRAGGING_LINE;
import static m3.data.m3State.DRAGGING_STATION;
import static m3.data.m3State.REMOVING_STATION;
import static m3.data.m3State.SELECTING;
import static m3.data.m3State.SIZING_LINE;
import m3.transaction.AddStationToLineTransaction;
import m3.transaction.RemoveStationFromLineTransaction;
import m3.transaction.dragLeftTransaction;
import m3.transaction.dragRightTransaction;

/**
 * This class responds to interactions with the rendering surface.
 *
 * @author Richard McKenna
 * @author Sabrina To
 * @version 1.0
 */
public class m3MouseController {

    AppTemplate app;
    dragTransaction d;
    dragLeftTransaction dl;
    dragRightTransaction dr;
    DraggableLine thatline;
    boolean left;
    boolean right;
    
    public m3MouseController(AppTemplate initApp) {
        app = initApp;
    }

    public void processCanvasMousePress(int x, int y) {
        m3Data dataManager = (m3Data) app.getDataComponent();
                
        if (dataManager.isInState(SELECTING)) {
            // SELECT THE TOP SHAPE
            DraggableLine line = dataManager.selectTopLine(x, y);
            ImageView image=dataManager.selectTopImage(x, y);
            DraggableStation station = dataManager.selectTopStation(x, y);
            DraggableLabel label = dataManager.selectTopLabel(x, y);
            Label lineEnd=dataManager.selectTopLineEnd(x,y);
            
            Scene scene = app.getGUI().getPrimaryScene();
            
            // AND START DRAGGING IT
            if (image != null) {
                scene.setCursor(Cursor.MOVE);
                dataManager.setState(m3State.DRAGGING_IMAGE);
                app.getGUI().updateToolbarControls(false);
                
                d=new dragTransaction(image,x,y);
            } 
            else if (line != null) {
                scene.setCursor(Cursor.MOVE);
                dataManager.setState(m3State.DRAGGING_LINE);
                app.getGUI().updateToolbarControls(false);
                
                d=new dragTransaction(line,x,y); 
            } 
            else if (station != null) {
                scene.setCursor(Cursor.MOVE);
                dataManager.setState(m3State.DRAGGING_STATION);
                app.getGUI().updateToolbarControls(false);
                
                d=new dragTransaction(station,x,y); 
            }
            else if (label != null) {
                scene.setCursor(Cursor.MOVE);
                dataManager.setState(m3State.DRAGGING_LABEL);
                app.getGUI().updateToolbarControls(false);
                
                d=new dragTransaction(label,x,y); 
            }
            else if(lineEnd!=null)
            {
                scene.setCursor(Cursor.MOVE);
                dataManager.setState(m3State.SIZING_LINE);
                app.getGUI().updateToolbarControls(false);
                for(Node nodes:dataManager.getShapes())
                {
                    if(nodes instanceof DraggableLine)
                    {
                        if((((DraggableLine)nodes).getName()).equals(lineEnd.getText()))
                            thatline=(DraggableLine)nodes;
                    }
                } 
                thatline.start(x, y);
                left=thatline.isFirstLabel(lineEnd);
                right=thatline.isSecondLabel(lineEnd);                
                if(left)
                    dl=new dragLeftTransaction(thatline,x,y);
                else if(right)
                    dr=new dragRightTransaction(thatline,x,y);
            }
            else {
                dataManager.unhighlightAll();
                scene.setCursor(Cursor.DEFAULT);
                dataManager.setState(SELECTING);
                app.getWorkspaceComponent().reloadWorkspace(dataManager);
            }
        }
        else if(dataManager.isInState(ADDING_STATION))
        {
            DraggableStation station = dataManager.selectTopStation(x, y);
            if(station==null)
                dataManager.setState(SELECTING);
            else
            {
                String name=((m3Workspace)(app.getWorkspaceComponent())).getLinesBox().getValue().toString();
                DraggableLine addOnLine=null;
                for(Node nodes:dataManager.getShapes())
                {
                    if(nodes instanceof DraggableLine)
                    {
                        if((((DraggableLine)nodes).getName()).equals(name))
                            addOnLine=(DraggableLine)nodes;
                    }
                } 
                ((m3Workspace)(app.getWorkspaceComponent())).getjTPS().addTransaction(new AddStationToLineTransaction(station,addOnLine));
            }
        }
        else if(dataManager.isInState(REMOVING_STATION))
        {
            DraggableStation station = dataManager.selectTopStation(x, y);
            if(station==null)
                dataManager.setState(SELECTING);
            else
            {
                String name=((m3Workspace)(app.getWorkspaceComponent())).getLinesBox().getValue().toString();
                DraggableLine addOnLine=null;
                for(Node nodes:dataManager.getShapes())
                {
                    if(nodes instanceof DraggableLine)
                    {
                        if((((DraggableLine)nodes).getName()).equals(name))
                            addOnLine=(DraggableLine)nodes;
                    }
                } 
                ((m3Workspace)(app.getWorkspaceComponent())).getjTPS().addTransaction(new RemoveStationFromLineTransaction(station,addOnLine));
            }
        }
        m3Workspace workspace = (m3Workspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }

    /**
     * Respond to mouse dragging on the rendering surface, which we call canvas,
     * but is actually a Pane.
     */
    public void processCanvasMouseDragged(int x, int y) {
        m3Data dataManager = (m3Data) app.getDataComponent();
        if (dataManager.isInState(DRAGGING_STATION)) {
            Draggable selectedDraggableShape = (Draggable) dataManager.getSelectedStation();
            selectedDraggableShape.drag(x, y);
            app.getGUI().updateToolbarControls(false);
        }
        else if((dataManager.isInState(DRAGGING_IMAGE))){
            Draggable selectedDraggableImage = (Draggable) dataManager.getSelectedImage();
            selectedDraggableImage.drag(x, y);
            app.getGUI().updateToolbarControls(false);
        }
        else if((dataManager.isInState(DRAGGING_LINE)))
        {
            Draggable selectedDraggableShape = (Draggable) dataManager.getSelectedLine();
            selectedDraggableShape.drag(x, y);
            app.getGUI().updateToolbarControls(false);
        }
        else if((dataManager.isInState(DRAGGING_LABEL)))
        {
            Draggable selectedDraggableShape = (Draggable) dataManager.getSelectedLabel();
            selectedDraggableShape.drag(x, y);
            app.getGUI().updateToolbarControls(false);
        }  
        else if((dataManager.isInState(SIZING_LINE)))
        {
            if(left)
                thatline.dragLeft(x, y);
            else if(right)
                thatline.dragRight(x, y);
            app.getGUI().updateToolbarControls(false);
        }
    }

    /**
     * Respond to mouse button release on the rendering surface, which we call canvas,
     * but is actually a Pane.
     */
    public void processCanvasMouseRelease(int x, int y) {
        m3Data dataManager = (m3Data) app.getDataComponent();
        
        if (dataManager.isInState(m3State.DRAGGING_LINE)) {
            d.setNewx(x);
            d.setNewy(y);
            ((m3Workspace)(app.getWorkspaceComponent())).getjTPS().addTransaction(d);
            
            dataManager.setState(SELECTING);
            Scene scene = app.getGUI().getPrimaryScene();
            scene.setCursor(Cursor.DEFAULT);
            app.getGUI().updateToolbarControls(false);
            app.getGUI().getFileController().markFileAsNotSaved();
        } 
        else if (dataManager.isInState(m3State.DRAGGING_IMAGE)) {
            d.setNewx(x);
            d.setNewy(y);
            ((m3Workspace)(app.getWorkspaceComponent())).getjTPS().addTransaction(d);
            
            dataManager.setState(SELECTING);
            Scene scene = app.getGUI().getPrimaryScene();
            scene.setCursor(Cursor.DEFAULT);
            app.getGUI().updateToolbarControls(false);
            app.getGUI().getFileController().markFileAsNotSaved();
        } 
        else if(dataManager.isInState(m3State.DRAGGING_STATION))
        {
            d.setNewx(x);
            d.setNewy(y);
            ((m3Workspace)(app.getWorkspaceComponent())).getjTPS().addTransaction(d);
            
            dataManager.setState(SELECTING);
            Scene scene = app.getGUI().getPrimaryScene();
            scene.setCursor(Cursor.DEFAULT);
            app.getGUI().updateToolbarControls(false);
            app.getGUI().getFileController().markFileAsNotSaved();
        }
        else if(dataManager.isInState(m3State.DRAGGING_LABEL))
        {
            d.setNewx(x);
            d.setNewy(y);
            ((m3Workspace)(app.getWorkspaceComponent())).getjTPS().addTransaction(d);
            
            dataManager.setState(SELECTING);
            Scene scene = app.getGUI().getPrimaryScene();
            scene.setCursor(Cursor.DEFAULT);
            app.getGUI().updateToolbarControls(false);
            app.getGUI().getFileController().markFileAsNotSaved();
        } 
        else if((dataManager.isInState(SIZING_LINE)))
        {
            if(left)
            {
                dl.setNewx(x);
                dl.setNewy(y);
                ((m3Workspace)(app.getWorkspaceComponent())).getjTPS().addTransaction(dl);
            }
            else if(right)
            {
                dr.setNewx(x);
                dr.setNewy(y);
                ((m3Workspace)(app.getWorkspaceComponent())).getjTPS().addTransaction(dr);
            }
            dataManager.setState(SELECTING);
            Scene scene = app.getGUI().getPrimaryScene();
            scene.setCursor(Cursor.DEFAULT);
            app.getGUI().updateToolbarControls(false);
            app.getGUI().getFileController().markFileAsNotSaved();
        }
    }
}
