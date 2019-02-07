package m3.gui;

import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import m3.data.m3Data;
import djf.ui.AppYesNoCancelDialogSingleton;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppGUI;
import djf.AppTemplate;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import static djf.settings.AppPropertyType.LOAD_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.LOAD_ERROR_TITLE;
import static djf.settings.AppPropertyType.LOAD_WORK_TITLE;
import static djf.settings.AppPropertyType.LOGO_ICON;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import static djf.settings.AppStartupConstants.PATH_WORK;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.embed.swing.SwingFXUtils;
import static m3.css.m3Style.*;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import jtps.jTPS;
import m3.data.DraggableImage;
import m3.data.DraggableLabel;
import m3.data.DraggableLine;
import m3.data.DraggableStation;
import m3.data.MapGraph;
import m3.data.m3State;
import static m3.data.m3State.ADDING_STATION;
import static m3.data.m3State.REMOVING_STATION;
import m3.file.m3Files;
import static m3.m3Property.*;
import m3.transaction.ChangeLineThicknessTransaction;
import m3.transaction.addLineTransaction;
import m3.transaction.addStationTransaction;
import m3.transaction.backgroundColorTransaction;
import m3.transaction.boldTransaction;
import m3.transaction.changeRadiusTransaction;
import m3.transaction.dragTransaction;
import m3.transaction.editLineTransaction;
import m3.transaction.editStationTransaction;
import m3.transaction.fontFamilyTransaction;
import m3.transaction.fontSizeTransaction;
import m3.transaction.imageTransaction;
import m3.transaction.italicsTransaction;
import m3.transaction.labelTransaction;
import m3.transaction.mapSizeTransaction;
import m3.transaction.moveLabelTransaction;
import m3.transaction.removeLineTransaction;
import m3.transaction.removeStationTransaction;
import m3.transaction.removeTransaction;
import m3.transaction.rotateTransaction;
import m3.transaction.setbackgroundTransaction;
import m3.transaction.snapLeftTransaction;
import m3.transaction.snapRightTransaction;
import m3.transaction.textColorTransaction;
import properties_manager.PropertiesManager;

/**
 * This class serves as the workspace component for this application, providing
 * the user interface controls for editing work.
 *
 * @author Richard McKenna
 * @author Sabrina To
 * @version 1.0
 */
public class m3Workspace extends AppWorkspaceComponent {
    AppTemplate app; 
    AppGUI gui;
 
    VBox editToolbar;

    VBox row1;
    FlowPane pane1;
    FlowPane pane2;
    FlowPane pane3;
    Label lines;
    ComboBox<String> linesBox;
    Button editLine;
    Button addLine;
    Button removeLine;
    Button addStationToLine;
    Button removeStationFromLine;
    Button linesList;
    Slider lineThickness;

    VBox row2;
    FlowPane pane4;
    FlowPane pane5;
    FlowPane pane6;
    Label stations;
    ComboBox<String> stationsBox;
    Button editStation;
    Button addStation;
    Button removeStation;
    Button snap;
    Button moveLabel;
    Button rotate;
    Slider stationThickness;

    HBox row3;
    FlowPane pane7;
    FlowPane pane8;
    ComboBox<String> fromStation;
    ComboBox<String> toStation;
    Button findRoute;

    VBox row4;
    FlowPane pane9;
    FlowPane pane10;
    Label decor;
    Button backgroundColor;
    Button setImageBackground;
    Button addImage;
    Button addLabel;
    Button remove;

    VBox row5;
    FlowPane pane11;
    FlowPane pane12;
    Label font;
    Button textColor;
    Button bold;
    Button italics;
    ComboBox<Double> fontSize;
    ComboBox<String> fontFamily;

    VBox row6;
    FlowPane pane13;
    FlowPane pane14;
    Label navigation;
    CheckBox grid;
    boolean gridClicked;
    ArrayList<Line> gridlines;
    Button zoomIn;
    Button zoomOut;
    Button increase;
    Button decrease;
    
    Pane map;
    
    m3MouseController controller;
    AppMessageDialogSingleton messageDialog;
    AppYesNoCancelDialogSingleton  yesNoCancelDialog;
    Text debugText;
    jTPS jtps;

    public jTPS getjTPS() {
        return jtps;
    }
    
    public Pane getMap()
    {
         return map;       
    }

    public ComboBox getLinesBox() {
        return linesBox;
    }

    public Button getEditLine() {
        return editLine;
    }

    public ComboBox getFromStation() {
        return fromStation;
    }

    public ComboBox getToStation() {
        return toStation;
    }

    public ComboBox getStationsBox() {
        return stationsBox;
    }

    public Button getEditStation() {
        return editStation;
    }

    public Button getTextColor() {
        return textColor;
    }
    
    
    /**
     * Constructor for initializing the workspace, note that this constructor
     * will fully setup the workspace user interface for use.
     *
     * @param initApp The application this workspace is part of.
     *
     * @throws IOException Thrown should there be an error loading application
     * data for setting up the user interface.
     */
    public m3Workspace(AppTemplate initApp) {
	// KEEP THIS FOR LATER
	app = initApp;

	// KEEP THE GUI FOR LATER
	gui = app.getGUI();
        
        //transaction
        jtps=new jTPS();

        // LAYOUT THE APP
        initLayout();
        
        // HOOK UP THE CONTROLLERS
        initControllers();

        // AND INIT THE STYLE FOR THE WORKSPACE
        initStyle();    
    }
    
    /**
     * Note that this is for displaying text during development.
     */
    public void setDebugText(String text) {
	debugText.setText(text);
    }
    
    // HELPER SETUP METHOD
    private void initLayout() {
	// THIS WILL GO IN THE LEFT SIDE OF THE WORKSPACE
	editToolbar = new VBox();
	
	// ROW 1
        row1=new VBox();
        pane1=new FlowPane(Orientation.HORIZONTAL);
        pane2=new FlowPane(Orientation.HORIZONTAL);
        pane3=new FlowPane(Orientation.HORIZONTAL);
        
        lines=new Label("Metro Lines");
        linesBox=new ComboBox<String>();
        linesBox.setMinWidth(200);
        editLine=new Button("edit");
        pane1.getChildren().addAll(lines,linesBox,editLine);
        
        addLine=gui.initChildButton( pane2, ADD_ICON.toString(), ADDLINE_TOOLTIP.toString(), false);
        removeLine=gui.initChildButton( pane2, REMOVE_ICON.toString(), REMOVELINE_TOOLTIP.toString(), false);
        addStationToLine=new Button();
        addStationToLine.setTooltip(new Tooltip(props.getProperty(ADDSTATIONONLINE_TOOLTIP.toString())));
        addStationToLine.setText("Add Station");
        pane2.getChildren().add(addStationToLine);
        removeStationFromLine=new Button();
        removeStationFromLine.setTooltip(new Tooltip(props.getProperty(REMOVESTATIONONLINE_TOOLTIP.toString())));
        removeStationFromLine.setText("Remove Station");
        pane2.getChildren().add(removeStationFromLine);
        linesList=gui.initChildButton(pane2, LINELIST_ICON.toString(), LINELIST_TOOLTIP.toString(), false);
        
        lineThickness=new Slider();
        lineThickness.setMinWidth(400);
        pane3.getChildren().add(lineThickness);
        
        row1.getChildren().addAll(pane1,pane2,pane3);
	
	//row2
        row2=new VBox();
        pane4=new FlowPane(Orientation.HORIZONTAL);
        pane5=new FlowPane(Orientation.HORIZONTAL);
        pane6=new FlowPane(Orientation.HORIZONTAL);
        
        stations=new Label("Metro Stations");
        stationsBox=new ComboBox<String>();
        stationsBox.setMinWidth(200);
        editStation=new Button("edit");
        pane4.getChildren().addAll(stations,stationsBox,editStation);
        
        addStation=gui.initChildButton(pane5, ADD_ICON.toString(), ADDSTATION_TOOLTIP.toString(), false);
        removeStation=gui.initChildButton(pane5, REMOVE_ICON.toString(), REMOVESTATION_TOOLTIP.toString(), false);
        
        snap=new Button("Snap");
        snap.setTooltip(new Tooltip(props.getProperty(SNAPSHOT_TOOLTIP.toString())));
        moveLabel=new Button("Move Label");
        moveLabel.setTooltip(new Tooltip(props.getProperty(MOVELABEL_TOOLTIP.toString())));
        pane5.getChildren().addAll(snap,moveLabel);
        
        rotate=gui.initChildButton(pane5, ROTATELABEL_ICON.toString(), ROTATELABEL_TOOLTIP.toString(), false);
        
        stationThickness=new Slider();
        stationThickness.setMinWidth(400);
        pane6.getChildren().add(stationThickness);
        
        row2.getChildren().addAll(pane4,pane5,pane6);        
        
        //row3
        row3=new HBox();
        pane7=new FlowPane(Orientation.VERTICAL);
        pane8=new FlowPane(Orientation.HORIZONTAL);
        fromStation=new ComboBox<String>();
        fromStation.setMinWidth(200);
        toStation=new ComboBox<String>();
        toStation.setMinWidth(200);
        pane7.getChildren().addAll(fromStation,toStation);
        findRoute=gui.initChildButton(pane8, ROUTE_ICON.toString(), ROUTE_TOOLTIP.toString(), false);
        row3.getChildren().addAll(pane7,pane8);
        
        row3.setMaxHeight(300);
        
        //row4
        row4=new VBox();
        pane9=new FlowPane();
        pane10=new FlowPane(Orientation.HORIZONTAL);
        
        decor=new Label("Decor");
        backgroundColor=new Button("Change Color");
        pane9.getChildren().addAll(decor,backgroundColor);
        
        setImageBackground=new Button("Set Image Background");
        setImageBackground.setTooltip(new Tooltip(props.getProperty(SETIMAGEBACKGROUND_TOOLTIP.toString())));
        addImage=new Button("Add Image");
        addImage.setTooltip(new Tooltip(props.getProperty(ADDIMAGE_TOOLTIP.toString())));
        addLabel=new Button("Add Label");
        addLabel.setTooltip(new Tooltip(props.getProperty(ADDLABEL_TOOLTIP.toString())));
        remove=new Button("Remove Element");
        remove.setTooltip(new Tooltip(props.getProperty(REMOVEELEMNT_TOOLTIP.toString())));
        pane10.getChildren().addAll(setImageBackground,addImage,addLabel,remove);
        
        row4.getChildren().addAll(pane9,pane10);

        //row 5
        row5=new VBox();
        pane11=new FlowPane();
        pane12=new FlowPane(Orientation.HORIZONTAL);
        
        font=new Label("Font");
        textColor=new Button("Change Color");
        pane11.getChildren().addAll(font,textColor);
        
        bold=gui.initChildButton(pane12, BOLD_ICON.toString(), BOLD_TOOLTIP.toString(), false);
        italics=gui.initChildButton(pane12, ITALICS_ICON.toString(), ITALICS_TOOLTIP.toString(), false);
        fontSize=new ComboBox<Double>();
        fontSize.getItems().addAll(5.0,10.0,15.0,20.0,25.0,30.0,35.0,40.0,45.0,50.0,55.0,60.0);
        fontSize.setMinWidth(80);
        fontFamily=new ComboBox<String>();
        fontFamily.getItems().addAll("Times New Roman","Cooper Black","Comic Sans MS","System","Arial Narrow" );
        fontFamily.setMinWidth(150);
        pane12.getChildren().addAll(fontSize,fontFamily);
        
        row5.getChildren().addAll(pane11,pane12);
        
        //row 6
        row6=new VBox();
        pane13=new FlowPane();
        pane14=new FlowPane(Orientation.HORIZONTAL);
        
        navigation=new Label("Navigation");
        grid=new CheckBox("Show Grid");
        gridClicked=false;
        pane13.getChildren().addAll(navigation,grid);
        
        zoomIn=gui.initChildButton(pane14, ZOOMIN_ICON.toString(), ZOOMIN_TOOLTIP.toString(), false);
        zoomOut=gui.initChildButton(pane14, ZOOMOUT_ICON.toString(), ZOOMOUT_TOOLTIP.toString(), false);
        decrease=gui.initChildButton(pane14, DECREASE_ICON.toString(), DECREASE_TOOLTIP.toString(), false);
        increase=gui.initChildButton(pane14, INCREASE_ICON.toString(), INCREASE_TOOLTIP.toString(), false);
        
        row6.getChildren().addAll(pane13,pane14);
        
        // NOW ORGANIZE THE EDIT TOOLBAR
	editToolbar.getChildren().add(row1);
	editToolbar.getChildren().add(row2);
	editToolbar.getChildren().add(row3);
	editToolbar.getChildren().add(row4);
	editToolbar.getChildren().add(row5);
	editToolbar.getChildren().add(row6);
	editToolbar.setMaxWidth(450);
        
	// WE'LL RENDER OUR STUFF HERE IN THE CANVAS
	map = new Pane();
	debugText = new Text();
	map.getChildren().add(debugText);
	debugText.setX(100);
	debugText.setY(100);
	
	// AND MAKE SURE THE DATA MANAGER IS IN SYNCH WITH THE PANE
	m3Data data = (m3Data)app.getDataComponent();
	data.setShapes(map.getChildren());

	// AND NOW SETUP THE WORKSPACE
	workspace = new BorderPane();
        ((BorderPane)workspace).setCenter(map);
	((BorderPane)workspace).setLeft(editToolbar); 
    }
    
       
        
    PropertiesManager props = PropertiesManager.getPropertiesManager();
    // HELPER SETUP METHOD
    private void initControllers() {
       gui.getWindow().setOnCloseRequest(e->{gui.getFileController().handleExitRequest();});
            
       gui.getExportButton().setOnAction(e ->{
           String nameOftheFile=gui.getFileController().getName();
           
            WritableImage image = map.snapshot(new SnapshotParameters(), null);
            File file = new File("./export/"+nameOftheFile+"\\"+nameOftheFile+" Metro.png");
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                ((m3Files)app.getFileComponent()).exportData(app.getDataComponent(),"./export/"+nameOftheFile+"\\"+nameOftheFile+" Metro.json");
            }
            catch(IOException ioe) {
                ioe.printStackTrace();
            }
           
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Congratulation!");
            alert.setHeaderText(null);
            alert.setContentText("You have successfully exported your map!");        

            alert.showAndWait();
        });
       
       gui.getUndoButton().setOnAction(e ->{
           jtps.undoTransaction();
       });
          
       gui.getRedoButton().setOnAction(e ->{
           jtps.doTransaction();
       });
       
       gui.getInfoButton().setOnAction(e->{
            Alert alert = new Alert(AlertType.INFORMATION);            
            alert.setTitle("About");
            alert.setContentText("Name of app: Metro Map Maker\n\nDevelopers: Richard McKenna , Sabrina To\n\nYear of work: 2017");             
           
             String imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(LOGO_ICON);
             Image logoImage = new Image(imagePath);
             alert.setGraphic(new ImageView(logoImage));  
             alert.setHeaderText(null);
             alert.showAndWait();
        }); 
       
       //mouse
        controller = new  m3MouseController(app);
	map.setOnMousePressed(e->{
	    controller.processCanvasMousePress((int)e.getX(), (int)e.getY());
	});
	map.setOnMouseReleased(e->{
	    controller.processCanvasMouseRelease((int)e.getX(), (int)e.getY());
	});
	map.setOnMouseDragged(e->{
	    controller.processCanvasMouseDragged((int)e.getX(), (int)e.getY());
	});
        
        //row1
        linesBox.setOnAction(e->{
            if(!linesBox.isDisable()){
                if(linesBox.getValue()!=null){
                    m3Data data=(m3Data)app.getDataComponent();
                    DraggableLine line=null;
                    for(Node x:data.getShapes())
                    {
                        if(x instanceof DraggableLine)
                        {
                            if((((DraggableLine)x).getName()).equals(linesBox.getValue()))
                                line=(DraggableLine)x;
                        }
                    } 
                    loadSelectedShapeSettings(line);
                }
            }
        });
        
        addLine.setOnAction(e->{
            addLineDialog add=new addLineDialog();
            jtps.addTransaction(new addLineTransaction(new DraggableLine(add.getName(),add.getColor()),app,this));
            gui.updateToolbarControls(false);
            gui.getFileController().markFileAsNotSaved();
        });
        
        removeLine.setOnAction(e->{
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Remove "+linesBox.getValue()+" line?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                jtps.addTransaction(new removeLineTransaction(linesBox.getValue(),app,this));
                gui.updateToolbarControls(false);
                gui.getFileController().markFileAsNotSaved();
            } 
        });
        
        editLine.setOnAction(e->{
            if(!editLine.isDisable()){
            m3Data data=(m3Data)app.getDataComponent();
            DraggableLine line=null;
            for(Node x:data.getShapes())
            {
                if(x instanceof DraggableLine)
                {
                    if((((DraggableLine)x).getName()).equals(linesBox.getValue().toString()))
                        line=(DraggableLine)x;
                }
            }         
            editLineDialog editedit=new editLineDialog(linesBox.getValue(),line,jtps);
            jtps.addTransaction(new editLineTransaction(linesBox.getValue().toString(),editedit.getName(),editedit.getColor(),app,this));
            gui.updateToolbarControls(false);
            gui.getFileController().markFileAsNotSaved();
            }
        });
        
        ChangeLineThicknessTransaction ccc=new ChangeLineThicknessTransaction(app);
        lineThickness.setOnMousePressed(e->{
            if(linesBox.getValue()!=null)
                ccc.setLine(linesBox.getValue());
        });
        
        lineThickness.setOnMouseReleased(e->{
            if(linesBox.getValue()!=null){
                ccc.setNewWidth(lineThickness.getValue());
                jtps.addTransaction(ccc);
                gui.updateToolbarControls(false);
                gui.getFileController().markFileAsNotSaved();
            }
        });
        
        addStationToLine.setOnAction(e->{
            ((m3Data) app.getDataComponent()).setState(ADDING_STATION);
        });
        
        removeStationFromLine.setOnAction(e->{
            ((m3Data) app.getDataComponent()).setState(REMOVING_STATION);
        });
        
        linesList.setOnAction(e->{
            DraggableLine line=null;
            for(Node x:((m3Data)app.getDataComponent()).getShapes())
            {
                if(x instanceof DraggableLine)
                {
                     if((((DraggableLine)x).getName()).equals(linesBox.getValue()))
                         line=(DraggableLine)x;
                }
            }
            listStationsDialog ddd=new listStationsDialog(line);
        });
  
        //row2
        stationsBox.setOnAction(e->{
            if(!stationsBox.isDisable()){
                if(stationsBox.getValue()!=null){
                    m3Data data=(m3Data)app.getDataComponent();
                    DraggableStation station=null;
                    for(Node x:data.getShapes())
                    {
                        if(x instanceof DraggableStation)
                        {
                            if((((DraggableStation)x).getName()).equals(stationsBox.getValue().toString()))
                                station=(DraggableStation)x;
                        }
                     }   
                    loadSelectedShapeSettings(station);
                }
            }
        });
        
        addStation.setOnAction(e->{
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Station");
            dialog.setHeaderText("Enter a name for this new station!");
            dialog.setContentText("Name:");
 
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                jtps.addTransaction(new addStationTransaction(new DraggableStation(result.get()),app,this));
                gui.updateToolbarControls(false);
                gui.getFileController().markFileAsNotSaved();
            }
        });
        
        removeStation.setOnAction(e->{
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Remove "+stationsBox.getValue()+" station?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                jtps.addTransaction(new removeStationTransaction(stationsBox.getValue().toString(),app,this));
                gui.updateToolbarControls(false);
                gui.getFileController().markFileAsNotSaved();
            } 
        });
        
        editStation.setOnAction(e->{
            if(!editStation.isDisable()){
            editStationDialog es=new editStationDialog(stationsBox.getValue().toString());
            jtps.addTransaction(new editStationTransaction(stationsBox.getValue().toString(),es.getColor(),app,this));
            gui.updateToolbarControls(false);
            gui.getFileController().markFileAsNotSaved();
            }
        });
        
        changeRadiusTransaction crt=new changeRadiusTransaction(app);
        stationThickness.setOnMousePressed(e->{
            if(stationsBox.getValue()!=null)
                crt.setStation(stationsBox.getValue().toString());
        });
        
        stationThickness.setOnMouseReleased(e->{
            if(stationsBox.getValue()!=null)
            {
                crt.setNewRadius(stationThickness.getValue());
                jtps.addTransaction(crt); 
                gui.updateToolbarControls(false);
                gui.getFileController().markFileAsNotSaved();
            }
        });
        
        snap.setOnAction(e->{
            m3Data dataManager = (m3Data) app.getDataComponent();
      
            DraggableStation station = dataManager.getSelectedStation();
            Label lineEnd=dataManager.getSelectedLineEnd();
            if(station!=null)
            {
                double x=station.getX();
                double y=station.getY();
 
                while(x-20>0)
                {
                    x-=20;
                }
                
                while(y-20>0)
                {
                    y-=20;
                }

                double newX;
                double newY;
                if(x>10)
                   newX=station.getX()-x+20;
                else
                   newX=station.getX()-x; 
                if(y>10)
                   newY=station.getY()-y+20;
                else
                   newY=station.getY()-y; 

                jtps.addTransaction(new dragTransaction(station,station.getX(),station.getY(),newX,newY));
                //station.drag((int)newX,(int)newY);
            }
            if(lineEnd!=null)
            {
                DraggableLine thatline=null;
                for(Node nodes:dataManager.getShapes())
                     {
                         if(nodes instanceof DraggableLine)
                         {
                             if((((DraggableLine)nodes).getName()).equals(lineEnd.getText()))
                                 thatline=(DraggableLine)nodes;
                         }
                     } 
                     boolean left=thatline.isFirstLabel(lineEnd);
                     boolean right=thatline.isSecondLabel(lineEnd);                       
                     if(left)
                     {
                           double x=thatline.getStartX();
                           double y=thatline.getStartY();

                            while(x-20>0)
                            {
                                x-=20;
                            }

                            while(y-20>0)
                            {
                                y-=20;
                            }

                            double newX;
                            double newY;
                            if(x>10)
                               newX=thatline.getStartX()-x+20;
                            else
                               newX=thatline.getStartX()-x; 
                            if(y>10)
                               newY=thatline.getStartY()-y+20;
                            else
                               newY=thatline.getStartY()-y; 

                            jtps.addTransaction(new snapLeftTransaction(thatline,thatline.getStartX(),thatline.getStartY(),newX,newY));
                            //thatline.snapLeft((int)newX,(int)newY);
                     }
                     else if(right)
                     {
                         double x=thatline.getEndX();
                           double y=thatline.getEndY();

                            while(x-20>0)
                            {
                                x-=20;
                            }

                            while(y-20>0)
                            {
                                y-=20;
                            }

                            double newX;
                            double newY;
                            if(x>10)
                               newX=thatline.getEndX()-x+20;
                            else
                               newX=thatline.getEndX()-x; 
                            if(y>10)
                               newY=thatline.getEndY()-y+20;
                            else
                               newY=thatline.getEndY()-y; 
                            
                            jtps.addTransaction(new snapRightTransaction(thatline,thatline.getEndX(),thatline.getEndY(),newX,newY));
                            //thatline.snapRight((int)newX,(int)newY);
                     }
            }
        });
        
        moveLabel.setOnAction(e->{
            m3Data data = (m3Data) app.getDataComponent();
            DraggableStation station=null;
            
            for(Node x:data.getShapes())
            {
                if(x instanceof DraggableStation)
                {
                    if((((DraggableStation)x).getName()).equals(stationsBox.getValue().toString()))
                        station=(DraggableStation)x;
                }
            } 
            jtps.addTransaction(new moveLabelTransaction(station,this));
        });
        
        rotate.setOnAction(e->{
            m3Data data = (m3Data) app.getDataComponent();
            DraggableStation station=null;
            
            for(Node x:data.getShapes())
            {
                if(x instanceof DraggableStation)
                {
                    if((((DraggableStation)x).getName()).equals(stationsBox.getValue().toString()))
                        station=(DraggableStation)x;
                }
            }     
            
            jtps.addTransaction(new rotateTransaction(station));
        });
        
        //row3
        findRoute.setOnAction(e->{
            m3Data data = (m3Data) app.getDataComponent();
            MapGraph mg=new MapGraph(data.getShapes());
            
            String name1=fromStation.getValue().toString();
            String name2=toStation.getValue().toString();
            DraggableStation s1=null;
            DraggableStation s2=null;
            
            for(Node x:data.getShapes())
            {
                if(x instanceof DraggableStation)
                {
                    if((((DraggableStation)x).getName()).equals(name1))
                        s1=(DraggableStation)x;
                    if((((DraggableStation)x).getName()).equals(name2))
                        s2=(DraggableStation)x;
                }
            } 

            List<DraggableStation> path = mg.minimumWeightPath(s1,s2);
            
            RouteDialog rd= new RouteDialog(name1,name2,path);
        });
        
        //row4
        backgroundColor.setOnAction(e->{
            backgroundColorDialog bcd=new backgroundColorDialog();
            jtps.addTransaction(new backgroundColorTransaction(((m3Data)app.getDataComponent()).getBackgroundColor(),bcd.getColor(),((m3Data)app.getDataComponent()))); 
            gui.getFileController().markFileAsNotSaved();
        });
        
        setImageBackground.setOnAction(e->{
            YesNoDialog ynd=new YesNoDialog();
            if((ynd.getSelection()).equals("Yes"))
            {
                FileChooser fc = new FileChooser();
                fc.setInitialDirectory(new File(PATH_WORK));
                fc.setTitle(props.getProperty(LOAD_WORK_TITLE));
                File selectedFile = fc.showOpenDialog(app.getGUI().getWindow());
                
                if (selectedFile != null) {
                try {
                    Image i=new Image("file:"+selectedFile.getPath());
                    
                    // new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)
                    BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
                    // new BackgroundImage(image, repeatX, repeatY, position, size)
                    BackgroundImage backgroundImage = new BackgroundImage(i, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
                    // new Background(images...)
                    Background background = new Background(backgroundImage);
                    
                    jtps.addTransaction(new setbackgroundTransaction("file:"+selectedFile.getPath(),((m3Data)app.getDataComponent()),background,map)); 
                } catch (Exception c) {
                    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                    dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
                }
                gui.getFileController().markFileAsNotSaved();
            } 
            }
        });
        
        addImage.setOnAction(e->{
	    PropertiesManager props = PropertiesManager.getPropertiesManager();
	
            // AND NOW ASK THE USER FOR THE FILE TO OPEN
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File(PATH_WORK));
            fc.setTitle(props.getProperty(LOAD_WORK_TITLE));
            File selectedFile = fc.showOpenDialog(app.getGUI().getWindow());

            // ONLY OPEN A NEW FILE IF THE USER SAYS OK
            if (selectedFile != null) {
                try {
                     jtps.addTransaction(new imageTransaction(new Image("file:"+selectedFile.getPath()),"file:"+selectedFile.getPath(),app));
                     
                } catch (Exception c) {
                    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                    dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
                }
                gui.getFileController().markFileAsNotSaved();
            } 
	});
        addLabel.setOnAction(e->{
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Label");
            dialog.setContentText("Enter text here:");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                jtps.addTransaction(new labelTransaction(result.get(),app));
                gui.getFileController().markFileAsNotSaved();
            }
        });
        
        remove.setOnAction(e->{
            m3Data data=(m3Data)app.getDataComponent();
            DraggableLabel label=data.getSelectedLabel();
            DraggableImage image=(DraggableImage)data.getSelectedImage();
            
            if(label==null&&image==null)
            {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Nothing is selected!");

                alert.showAndWait();
            }
            else{
                if(label!=null)
                {
                    jtps.addTransaction(new removeTransaction(label,app)); 
                }
                else if(image!=null)
                {
                    jtps.addTransaction(new removeTransaction(image,app)); 
                }
            }
        });
        
        //row 5
        textColor.setOnAction(e->{
            if(!textColor.isDisable()){
            m3Data dataManager = (m3Data) app.getDataComponent();
      
            DraggableStation station = dataManager.getSelectedStation();
            DraggableLabel label = dataManager.getSelectedLabel();
            Label lineEnd=dataManager.getSelectedLineEnd();
            
            if(station==null&&label==null&&lineEnd==null)
            {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Nothing is selected!");

                alert.showAndWait();
            }
            else{
                textColorDialog tcd=new textColorDialog();
                if(lineEnd!=null)
                    jtps.addTransaction(new textColorTransaction(lineEnd,tcd.getColor(),this)); 
                else if(label!=null)
                    jtps.addTransaction(new textColorTransaction(label,tcd.getColor(),this)); 
                else
                    jtps.addTransaction(new textColorTransaction(station.getLabel(),tcd.getColor(),this)); 
            }
            }
            });
        bold.setOnAction(e->{
            m3Data dataManager = (m3Data) app.getDataComponent();
      
            DraggableStation station = dataManager.getSelectedStation();
            DraggableLabel label = dataManager.getSelectedLabel();
            Label lineEnd=dataManager.getSelectedLineEnd();
            
            if(station==null&&label==null&&lineEnd==null)
            {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Nothing is selected!");

                alert.showAndWait();
            }
            else{
                if(lineEnd!=null)
                    jtps.addTransaction(new boldTransaction(lineEnd,this)); 
                else if(label!=null)
                    jtps.addTransaction(new boldTransaction(label,this)); 
                else
                    jtps.addTransaction(new boldTransaction(station,this)); 
            }
            });
        
        italics.setOnAction(e->{
            m3Data dataManager = (m3Data) app.getDataComponent();
      
            DraggableStation station = dataManager.getSelectedStation();
            DraggableLabel label = dataManager.getSelectedLabel();
            Label lineEnd=dataManager.getSelectedLineEnd();
            
            if(station==null&&label==null&&lineEnd==null)
            {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Nothing is selected!");

                alert.showAndWait();
            }
            else{
                if(lineEnd!=null)
                    jtps.addTransaction(new italicsTransaction(lineEnd,this)); 
                else if(label!=null)
                    jtps.addTransaction(new italicsTransaction(label,this)); 
                else
                    jtps.addTransaction(new italicsTransaction(station,this)); 
            }
            });
        
        fontSize.setOnAction(e->{
            if(!fontSize.isDisable()){
            m3Data dataManager = (m3Data) app.getDataComponent();
      
            DraggableStation station = dataManager.getSelectedStation();
            DraggableLabel label = dataManager.getSelectedLabel();
            Label lineEnd=dataManager.getSelectedLineEnd();
            
            if(station==null&&label==null&&lineEnd==null)
            {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Nothing is selected!");

                alert.showAndWait();
            }
            else{
                FontWeight weight=FontWeight.NORMAL;
                FontPosture posture=FontPosture.REGULAR;
                if(lineEnd!=null)
                {
                    DraggableLine thatline=null;
                    for(Node nodes:dataManager.getShapes())
                         {
                             if(nodes instanceof DraggableLine)
                             {
                                 if((((DraggableLine)nodes).getName()).equals(lineEnd.getText()))
                                     thatline=(DraggableLine)nodes;
                             }
                         } 
                         boolean left=thatline.isFirstLabel(lineEnd);
                         boolean right=thatline.isSecondLabel(lineEnd);                
                         if(left)
                         {
                            if(thatline.isBold1()){
                                weight=FontWeight.BOLD;
                            }
                            if(thatline.isItalics1()){
                                posture=FontPosture.ITALIC;
                            }
                            jtps.addTransaction(new fontSizeTransaction(lineEnd,lineEnd.getFont().getFamily(),weight,posture,fontSize.getValue())); 
                         }
                         else if(right)
                         {
                            if(thatline.isBold2()){
                                weight=FontWeight.BOLD;
                            }
                            if(thatline.isItalics2()){
                                posture=FontPosture.ITALIC;
                            }
                            jtps.addTransaction(new fontSizeTransaction(lineEnd,lineEnd.getFont().getFamily(),weight,posture,fontSize.getValue())); 
                         }
                }
                else if(label!=null)
                {
                    if(label.isBold()){
                        weight=FontWeight.BOLD;
                    }
                    if(label.isItalics()){
                        posture=FontPosture.ITALIC;
                    }
                    jtps.addTransaction(new fontSizeTransaction(label,label.getFont().getFamily(),weight,posture,fontSize.getValue()));
                }
                else
                {
                    if(station.isBold()){
                        weight=FontWeight.BOLD;
                    }
                    if(station.isItalics()){
                        posture=FontPosture.ITALIC;
                    }
                    jtps.addTransaction(new fontSizeTransaction(station.getLabel(),station.getLabel().getFont().getFamily(),weight,posture,fontSize.getValue()));
                }
            } 
            }
        });
        
        fontFamily.setOnAction(e->{
           if(!fontFamily.isDisable()){
           m3Data dataManager = (m3Data) app.getDataComponent();
      
            DraggableStation station = dataManager.getSelectedStation();
            DraggableLabel label = dataManager.getSelectedLabel();
            Label lineEnd=dataManager.getSelectedLineEnd();
            
            if(station==null&&label==null&&lineEnd==null)
            {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Nothing is selected!");

                alert.showAndWait();
            }
            else{
                FontWeight weight=FontWeight.NORMAL;
                FontPosture posture=FontPosture.REGULAR;
                if(lineEnd!=null)
                {
                    DraggableLine thatline=null;
                    for(Node nodes:dataManager.getShapes())
                         {
                             if(nodes instanceof DraggableLine)
                             {
                                 if((((DraggableLine)nodes).getName()).equals(lineEnd.getText()))
                                     thatline=(DraggableLine)nodes;
                             }
                         } 
                         boolean left=thatline.isFirstLabel(lineEnd);
                         boolean right=thatline.isSecondLabel(lineEnd);                
                         if(left)
                         {
                            if(thatline.isBold1()){
                                weight=FontWeight.BOLD;
                            }
                            if(thatline.isItalics1()){
                                posture=FontPosture.ITALIC;
                            }
                            jtps.addTransaction(new fontFamilyTransaction(lineEnd,fontFamily.getValue(),weight,posture,lineEnd.getFont().getSize())); 
                         }
                         else if(right)
                         {
                            if(thatline.isBold2()){
                                weight=FontWeight.BOLD;
                            }
                            if(thatline.isItalics2()){
                                posture=FontPosture.ITALIC;
                            }
                            jtps.addTransaction(new fontFamilyTransaction(lineEnd,fontFamily.getValue(),weight,posture,lineEnd.getFont().getSize())); 
                         }
                }
                else if(label!=null)
                {
                    if(label.isBold()){
                        weight=FontWeight.BOLD;
                    }
                    if(label.isItalics()){
                        posture=FontPosture.ITALIC;
                    }
                    jtps.addTransaction(new fontFamilyTransaction(label,fontFamily.getValue(),weight,posture,label.getFont().getSize())); 
                }
                else
                {
                    if(station.isBold()){
                        weight=FontWeight.BOLD;
                    }
                    if(station.isItalics()){
                        posture=FontPosture.ITALIC;
                    }
                    jtps.addTransaction(new fontFamilyTransaction(station.getLabel(),fontFamily.getValue(),weight,posture,station.getLabel().getFont().getSize())); 
                }
            }   
           }
        });
        
        //row 6
        grid.setOnAction(e->{
            if(gridlines==null)
            {
                //gridlines
                gridlines=new ArrayList<>();
                for(int i=20;i<=map.getWidth()-20;i+=20)
                {
                    gridlines.add(new Line(i,0,i,map.getHeight()));
                }
                for(int i=20;i<=map.getHeight()-20;i+=20)
                {
                    gridlines.add(new Line(0,i,map.getWidth(),i));
                }
            }
            if(gridClicked==false){
                for(Line x:gridlines)
                {
                    map.getChildren().add(x);
                }
                gridClicked=true;
            }
            else
            {
                for(Line x:gridlines)
                {
                    map.getChildren().remove(x);
                }
                gridClicked=false;
            }
        });
        
        zoomIn.setOnAction(e->{
            ((BorderPane)workspace).setTop(gui.getTopToolbarPane());
            Double x=map.getScaleX()+0.1;
            Double y=map.getScaleX()+0.1;
            map.setScaleX(x);
            map.setScaleY(y); 
        }); 
        
        zoomOut.setOnAction(e->{
            ((BorderPane)workspace).setTop(gui.getTopToolbarPane());
            Double x=map.getScaleX()-0.1;
            Double y=map.getScaleX()-0.1;
            map.setScaleX(x);
            map.setScaleY(y);
        });
        
        increase.setOnAction(e->{
           ((BorderPane)workspace).setTop(gui.getTopToolbarPane());
           Double h=map.getHeight()+100;
           Double w=map.getWidth()+100;
           //map.setMaxSize(0,0);
           //map.setMinSize(w, h);
           jtps.addTransaction(new mapSizeTransaction(map,map.getWidth(),map.getHeight(),w,h,"increase"));
           
        });
        
        decrease.setOnAction(e->{
           ((BorderPane)workspace).setTop(gui.getTopToolbarPane());
           Double h=map.getHeight()-100;
           Double w=map.getWidth()-100;
           //map.setMinSize(0,0);
           //map.setMaxSize(w, h);
           jtps.addTransaction(new mapSizeTransaction(map,map.getWidth(),map.getHeight(),w,h,"decrease"));
        });
    } 
        //Font.font("Times New Roman",FontWeight.BOLD, FontPosture.ITALIC, 20);
        //Font.font("Times New Roman",FontWeight.BOLD, FontPosture.REGULAR, 20);
        //Font.font("Times New Roman",FontWeight.NORMAL, FontPosture.ITALIC, 20);
        //Font fontNormal = Font.font("Times New Roman",FontWeight.NORMAL, FontPosture.REGULAR, 20);
     
      //bold italics
      public void processBold(Node n) {
      if(n instanceof DraggableLabel)
      {
                double size=((DraggableLabel)n).getFont().getSize();
                String fontFamily=((DraggableLabel)n).getFont().getFamily();
                
                FontWeight weight=FontWeight.BOLD;
                FontPosture posture=FontPosture.REGULAR;
                
                if((((DraggableLabel)n).isBold())==false)
                {
                    ((DraggableLabel)n).setBold(true);
                }
                else{
                    weight=FontWeight.NORMAL;
                    ((DraggableLabel)n).setBold(false);
                }
                if(((DraggableLabel)n).isItalics()){
                    posture=FontPosture.ITALIC;
                }
                ((DraggableLabel)n).setFont(Font.font(fontFamily,weight,posture,size));
       }
       else if(n instanceof DraggableStation)
      {
                double size=((DraggableStation)n).getLabel().getFont().getSize();
                String fontFamily=((DraggableStation)n).getLabel().getFont().getFamily();
                
                FontWeight weight=FontWeight.BOLD;
                FontPosture posture=FontPosture.REGULAR;
                
                if(((DraggableStation)n).isBold()==false)
                {
                    ((DraggableStation)n).setBold(true);
                }
                else{
                    weight=FontWeight.NORMAL;
                    ((DraggableStation)n).setBold(false);
                }
                if(((DraggableStation)n).isItalics()){
                    posture=FontPosture.ITALIC;
                }
                ((DraggableStation)n).getLabel().setFont(Font.font(fontFamily,weight,posture,size));
      }   
      else
       {
           m3Data dataManager = (m3Data) app.getDataComponent();
           Label lineEnd=(Label)n;
           DraggableLine thatline=null;
           for(Node nodes:dataManager.getShapes())
                {
                    if(nodes instanceof DraggableLine)
                    {
                        if((((DraggableLine)nodes).getName()).equals(lineEnd.getText()))
                            thatline=(DraggableLine)nodes;
                    }
                } 
                boolean left=thatline.isFirstLabel(lineEnd);
                boolean right=thatline.isSecondLabel(lineEnd);                
                if(left)
                {
                    double size=lineEnd.getFont().getSize();
                    String fontFamily=lineEnd.getFont().getFamily();
                    
                    FontWeight weight=FontWeight.BOLD;
                    FontPosture posture=FontPosture.REGULAR;

                    if(thatline.isBold1()==false)
                    {
                        thatline.setBold1(true);
                    }
                    else{
                        weight=FontWeight.NORMAL;
                        thatline.setBold1(false);
                    }
                    if(thatline.isItalics1()){
                        posture=FontPosture.ITALIC;
                    }
                    lineEnd.setFont(Font.font(fontFamily,weight,posture,size));
                    
                }
                else if(right)
                {
                    double size=lineEnd.getFont().getSize();
                    String fontFamily=lineEnd.getFont().getFamily();
                    
                    FontWeight weight=FontWeight.BOLD;
                    FontPosture posture=FontPosture.REGULAR;

                    if(thatline.isBold2()==false)
                    {
                        thatline.setBold2(true);
                    }
                    else{
                        weight=FontWeight.NORMAL;
                        thatline.setBold2(false);
                    }
                    if(thatline.isItalics2()){
                        posture=FontPosture.ITALIC;
                    }
                    lineEnd.setFont(Font.font(fontFamily,weight,posture,size));
                }
       }
     }
      
      public void move(DraggableStation s)
      {
          if(s.getPos()==0)
          {
             s.setLabelX(s.getCenterX()-4*s.getRadius());
             s.setLabelY(s.getCenterY()+s.getRadius());
             s.getLabel().setTranslateX(s.getCenterX()-4*s.getRadius());
             s.getLabel().setTranslateY(s.getCenterY()+s.getRadius());
             s.setPos(1);
          }
          else if(s.getPos()==1)
          {
             s.setLabelX(s.getCenterX()+3*s.getRadius());
             s.setLabelY(s.getCenterY()+s.getRadius());
             s.getLabel().setTranslateX(s.getCenterX()+3*s.getRadius());
             s.getLabel().setTranslateY(s.getCenterY()+s.getRadius());
             s.setPos(2);
          }
           else if(s.getPos()==2)
          {
             s.setLabelX(s.getCenterX()+3*s.getRadius());
             s.setLabelY(s.getCenterY()-3*s.getRadius());
             s.getLabel().setTranslateX(s.getCenterX()+3*s.getRadius());
             s.getLabel().setTranslateY(s.getCenterY()-3*s.getRadius());
             s.setPos(3);
          }
          else if(s.getPos()==3)
          {
             s.setLabelX(s.getCenterX()-4*s.getRadius());
             s.setLabelY(s.getCenterY()-3*s.getRadius());
             s.getLabel().setTranslateX(s.getCenterX()-4*s.getRadius());
             s.getLabel().setTranslateY(s.getCenterY()-3*s.getRadius());
             s.setPos(4);
          }
          else if(s.getPos()==4)
          {
             s.setLabelX(s.getCenterX()-s.getRadius());
             s.setLabelY(s.getCenterY()+s.getRadius());
             s.getLabel().setTranslateX(s.getCenterX()-s.getRadius());
             s.getLabel().setTranslateY(s.getCenterY()+s.getRadius());
             s.setPos(0);
          }
      }
      
      public void processItalics(Node n) {
          if(n instanceof DraggableLabel)
           {
                double size=((DraggableLabel)n).getFont().getSize();
                String fontFamily=((DraggableLabel)n).getFont().getFamily();
                
                FontWeight weight=FontWeight.NORMAL;
                FontPosture posture=FontPosture.ITALIC;
                
                if(((DraggableLabel)n).isBold()){
                    weight=FontWeight.BOLD; 
                }
                if(((DraggableLabel)n).isItalics()==false)
                {
                    ((DraggableLabel)n).setItalics(true);
                }
                else{
                    posture=FontPosture.REGULAR;
                    ((DraggableLabel)n).setItalics(false);
                }
                
                ((DraggableLabel)n).setFont(Font.font(fontFamily,weight,posture,size));
       }
          
       else if(n instanceof DraggableStation)
      {
                double size=((DraggableStation)n).getLabel().getFont().getSize();
                String fontFamily=((DraggableStation)n).getLabel().getFont().getFamily();
                
                FontWeight weight=FontWeight.NORMAL;
                FontPosture posture=FontPosture.ITALIC;
                
                if(((DraggableStation)n).isBold()){
                    weight=FontWeight.BOLD; 
                }
                if(((DraggableStation)n).isItalics()==false)
                {
                    ((DraggableStation)n).setItalics(true);
                }
                else{
                    posture=FontPosture.REGULAR;
                    ((DraggableStation)n).setItalics(false);
                }
                
                ((DraggableStation)n).getLabel().setFont(Font.font(fontFamily,weight,posture,size));
      }      
       else
       {
           m3Data dataManager = (m3Data) app.getDataComponent();
           Label lineEnd=(Label)n;
           DraggableLine thatline=null;
           for(Node nodes:dataManager.getShapes())
                {
                    if(nodes instanceof DraggableLine)
                    {
                        if((((DraggableLine)nodes).getName()).equals(lineEnd.getText()))
                            thatline=(DraggableLine)nodes;
                    }
                } 
                boolean left=thatline.isFirstLabel(lineEnd);
                boolean right=thatline.isSecondLabel(lineEnd);                
                if(left)
                {
                    double size=lineEnd.getFont().getSize();
                    String fontFamily=lineEnd.getFont().getFamily();
                    
                    FontWeight weight=FontWeight.NORMAL;
                    FontPosture posture=FontPosture.ITALIC;
                
                    if(thatline.isBold1()){
                        weight=FontWeight.BOLD; 
                    }
                    if(thatline.isItalics1()==false)
                    {
                        thatline.setItalics1(true);
                    }
                    else{
                        posture=FontPosture.REGULAR;
                        thatline.setItalics1(false);
                    }

                   lineEnd.setFont(Font.font(fontFamily,weight,posture,size));
                    
                }
                else if(right)
                {
                    double size=lineEnd.getFont().getSize();
                    String fontFamily=lineEnd.getFont().getFamily();
                    
                    FontWeight weight=FontWeight.NORMAL;
                    FontPosture posture=FontPosture.ITALIC;
                
                    if(thatline.isBold2()){
                        weight=FontWeight.BOLD; 
                    }
                    if(thatline.isItalics2()==false)
                    {
                        thatline.setItalics2(true);
                    }
                    else{
                        posture=FontPosture.REGULAR;
                        thatline.setItalics2(false);
                    }

                   lineEnd.setFont(Font.font(fontFamily,weight,posture,size));
                }
       }    
         
     }
      
    public ComboBox getFontFamily() {
       return fontFamily;
    }

    public ComboBox getFontSize() {
        return fontSize;
    }
    
    
    // HELPER METHOD loadSelectedShapeSettings
    public void loadSelectedShapeSettings(Node n) {
	if (n instanceof DraggableLabel){
           textColor.setDisable(true);
           fontSize.setDisable(true);
           fontFamily.setDisable(true);
            
           fontSize.setValue(((DraggableLabel)n).getFont().getSize());
           fontFamily.setValue(((DraggableLabel)n).getFont().getFamily());
           textColor.setTextFill(((DraggableLabel)n).getTextFill());
           
           textColor.setDisable(false);
           fontSize.setDisable(false);
           fontFamily.setDisable(false);
	}
        else if(n instanceof DraggableLine)
        {
           linesBox.setDisable(true);
           linesBox.setValue(((DraggableLine)n).getName());
           linesBox.setDisable(false);
           editLine.setDisable(true);
           editLine.setTextFill(((DraggableLine)n).getStroke());
           editLine.setDisable(false);
        } 
        else if(n instanceof DraggableStation)
        {
           stationsBox.setDisable(true);
           stationsBox.setValue(((DraggableStation)n).getName());
           stationsBox.setDisable(false);
           
           textColor.setDisable(true);
           fontSize.setDisable(true);
           fontFamily.setDisable(true);
           editStation.setDisable(true);
           
           editStation.setTextFill(((DraggableStation)n).getFillColor());
           textColor.setTextFill(((DraggableStation)n).getLabel().getTextFill());
           fontSize.setValue(((DraggableStation)n).getLabel().getFont().getSize());
           fontFamily.setValue(((DraggableStation)n).getLabel().getFont().getFamily());
           
           editStation.setDisable(false);
           textColor.setDisable(false);
           fontSize.setDisable(false);
           fontFamily.setDisable(false);
        } 
        else if(n instanceof Label)
        {
           textColor.setDisable(true);
           fontSize.setDisable(true);
           fontFamily.setDisable(true); 
            
           textColor.setTextFill(((Label)n).getTextFill());
           fontSize.setValue(((Label)n).getFont().getSize());
           fontFamily.setValue(((Label)n).getFont().getFamily());
           
           textColor.setDisable(false);
           fontSize.setDisable(false);
           fontFamily.setDisable(false);
        }
    } 
    
    /**
     * This function specifies the CSS style classes for all the UI components
     * known at the time the workspace is initially constructed. Note that the
     * tag editor controls are added and removed dynamicaly as the application
     * runs so they will have their style setup separately.
     */
    public void initStyle() {
	// NOTE THAT EACH CLASS SHOULD CORRESPOND TO
	// A STYLE CLASS SPECIFIED IN THIS APPLICATION'S
	// CSS FILE
	map.getStyleClass().add(CLASS_RENDER_CANVAS);
	
	// Button 
	addLine.getStyleClass().add(CLASS_BUTTON);
        removeLine.getStyleClass().add(CLASS_BUTTON);
        addStationToLine.getStyleClass().add(CLASS_BUTTON);
        removeStationFromLine.getStyleClass().add(CLASS_BUTTON);
        linesList.getStyleClass().add(CLASS_BUTTON);
        addStation.getStyleClass().add(CLASS_BUTTON);
        removeStation.getStyleClass().add(CLASS_BUTTON);
        snap.getStyleClass().add(CLASS_BUTTON);
        moveLabel.getStyleClass().add(CLASS_BUTTON);
        rotate.getStyleClass().add(CLASS_BUTTON);
        findRoute.getStyleClass().add(CLASS_BUTTON);
	setImageBackground.getStyleClass().add(CLASS_BUTTON);
        addImage.getStyleClass().add(CLASS_BUTTON);
	addLabel.getStyleClass().add(CLASS_BUTTON);
        remove.getStyleClass().add(CLASS_BUTTON);
        bold.getStyleClass().add(CLASS_BUTTON);
        italics.getStyleClass().add(CLASS_BUTTON);       
        zoomIn.getStyleClass().add(CLASS_BUTTON); 
        zoomOut.getStyleClass().add(CLASS_BUTTON);
        increase.getStyleClass().add(CLASS_BUTTON);
        decrease.getStyleClass().add(CLASS_BUTTON);
          
        editLine.getStyleClass().add(CLASS_BUTTON);
        editStation.getStyleClass().add(CLASS_BUTTON);
        backgroundColor.getStyleClass().add(CLASS_BUTTON);
        textColor.getStyleClass().add(CLASS_BUTTON);
        
        //toolbar
	editToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR);
	row1.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row2.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row3.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row4.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row5.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        row6.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
	
        //Label
	lines.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);   
        stations.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);  
        decor.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
        font.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
        navigation.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL); 
    }

    /**
     * fool proof design
     * This function reloads all the controls for editing logos
     * the workspace.
     */
    @Override
    public void reloadWorkspace(AppDataComponent data) {
	m3Data dataManager = (m3Data)data;
	if (dataManager.isInState(m3State.DRAGGING_IMAGE)||dataManager.isInState(m3State.DRAGGING_LINE)) {
	    textColor.setDisable(true);
            bold.setDisable(true);
            italics.setDisable(true);
            fontSize.setDisable(true);
            fontFamily.setDisable(true);
        }
        else{
            textColor.setDisable(false);
            bold.setDisable(false);
            italics.setDisable(false);
            fontSize.setDisable(false);
            fontFamily.setDisable(false);
        }
    }
    
    @Override
    public void resetWorkspace() {
        // WE ARE NOT USING THIS, THOUGH YOU MAY IF YOU LIKE
    }
}