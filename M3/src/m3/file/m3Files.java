 package m3.file;

import java.io.IOException;
import javafx.scene.paint.Color;
import javax.json.Json;
import javax.json.JsonObject;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import m3.data.Draggable;
import static m3.data.Draggable.IMAGE;
import static m3.data.Draggable.LINE;
import static m3.data.Draggable.STATION;
import m3.data.DraggableImage;
import m3.data.DraggableLabel;
import m3.data.DraggableLine;
import m3.data.DraggableStation;
import m3.data.m3Data;
import m3.gui.m3Workspace;

/**
 * This class serves as the file management component for this application,
 * providing all I/O services.
 *
 * @author Richard McKenna
 * @author Sabrina To
 * @version 1.0
 */
public class m3Files implements AppFileComponent {
    // FOR JSON LOADING
    static final String JSON_BG_COLOR = "background_color";
    static final String JSON_RED = "red";
    static final String JSON_GREEN = "green";
    static final String JSON_BLUE = "blue";
    static final String JSON_ALPHA = "alpha";
    static final String JSON_SHAPES = "shapes";
    static final String JSON_SHAPE = "shape";
    
    static final String JSON_TYPE = "type";
    static final String JSON_X = "x";
    static final String JSON_Y = "y";
    static final String JSON_LABELX = "labelx";
    static final String JSON_LABELY = "labely";
    static final String JSON_RADIUS = "radius";
    static final String JSON_NAME = "name";
    
    static final String JSON_FILL_COLOR = "fill_color";
    static final String JSON_OUTLINE_COLOR = "outline_color";
    static final String JSON_OUTLINE_THICKNESS = "outline_thickness";
    static final String JSON_COLOR = "color";
    
    static final String JSON_TEXT = "text";
    
    static final String JSON_TEXTCOLOR = "text_color";
    static final String JSON_BOLD = "bold";
    static final String JSON_ITALICS = "italics";
    static final String JSON_SIZE = "size";
    static final String JSON_FAMILY = "family";
    
    static final String JSON_TEXTCOLOR2 = "text_color2";
    static final String JSON_BOLD2 = "bold2";
    static final String JSON_ITALICS2 = "italics2";
    static final String JSON_SIZE2 = "size2";
    static final String JSON_FAMILY2 = "family2";
    
    static final String JSON_PATH = "path";
    
    static final String JSON_STARTX = "startX";
    static final String JSON_STARTY = "startY";
    static final String JSON_ENDX = "endX";
    static final String JSON_ENDY = "endY";
    static final String JSON_LABEL2X = "label2x";
    static final String JSON_LABEL2Y = "label2y";
    static final String JSON_WIDTH = "width";
    
    static final String JSON_POINTS = "points";
    static final String JSON_LOC = "locations";
    static final String JSON_LINES = "lines";
    static final String JSON_STATIONS = "station_names";
    static final String JSON_STATIONSARRAY = "stations";
    
    static final String JSON_FILENAME = "filename";
    static final String JSON_CIRCULAR = "circular";
    static final String JSON_BACKGROUND = "background";
    static final String JSON_MAPSIZEH = "map_size_height";
    static final String JSON_MAPSIZEW = "map_size_width";
        
    static final String JSON_ROTATE="rotate";
    static final String JSON_POS="pos";
    
    static final String DEFAULT_DOCTYPE_DECLARATION = "<!doctype html>\n";
    static final String DEFAULT_ATTRIBUTE_VALUE = "";
    
 
    /**
     * This method is for saving user work, which in the case of this
     * application means the data that together draws the logo.
     * 
     * @param data The data management component for this application.
     * 
     * @param filePath Path (including file name/extension) to where
     * to save the data to.
     * 
     * @throws IOException Thrown should there be an error writing 
     * out data to the file.
     */
    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
        // GET THE DATA
	m3Data dataManager = (m3Data)data;
	
	// FIRST THE BACKGROUND COLOR
	Color bgColor = dataManager.getBackgroundColor();
	JsonObject bgColorJson = makeJsonColorObject(bgColor);

        JsonObject filename=Json.createObjectBuilder()
                            .add(JSON_FILENAME, dataManager.getFileName()).build();
        
        JsonObject bg=Json.createObjectBuilder()
                            .add(JSON_BACKGROUND, dataManager.getBackgroundImagePath()).build();
        
	// NOW BUILD THE JSON OBJCTS TO SAVE
	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
	ObservableList<Node> shapes = dataManager.getShapes();
	for (Node node : shapes) {
            JsonObject shapeJson=null;
            
            if(node instanceof ImageView)
            {
                Draggable draggableShape = ((Draggable)node);
                String type = draggableShape.getShapeType();  
                double x = draggableShape.getX();
                double y = draggableShape.getY();
                String path=((DraggableImage)draggableShape).getPath();
                shapeJson = Json.createObjectBuilder()
                    .add(JSON_TYPE, type)
		    .add(JSON_PATH, path)
		    .add(JSON_X, x)
		    .add(JSON_Y, y).build();   
            }          
            
            else if(node instanceof DraggableStation){
                DraggableStation draggableShape = ((DraggableStation)node);
                String type = draggableShape.getShapeType();          
                double x = draggableShape.getX();
                double y = draggableShape.getY();
                double radius = draggableShape.getRadius();
                String name=draggableShape.getName();
                double labelx = draggableShape.getLabelX();
                double labely = draggableShape.getLabelY();
                JsonObject fillColorJson = makeJsonColorObject((Color)draggableShape.getFill());
                JsonObject outlineColorJson = makeJsonColorObject((Color)draggableShape.getStroke());
                double rotate=draggableShape.getLabel().getRotate();
                
                JsonObject textColor = makeJsonColorObject((Color)draggableShape.getLabel().getTextFill());
                double bold=0;
                if(draggableShape.isBold())
                    bold=1;
                double italics=0;
                if(draggableShape.isItalics())
                    italics=1;
                double fontsize=draggableShape.getLabel().getFont().getSize();
                String fontfamily=draggableShape.getLabel().getFont().getFamily();
                
                double pos=draggableShape.getPos();
                //
                JsonArrayBuilder arrayBuilder2 = Json.createArrayBuilder(); 
                JsonObject allLines=null;
                for(DraggableLine l:draggableShape.getLines())
                {
                    allLines=Json.createObjectBuilder().add(JSON_NAME, l.getName()).build();
                    arrayBuilder2.add(allLines);
                }
                JsonArray linesArray = arrayBuilder2.build();
                
                shapeJson = Json.createObjectBuilder()
                        .add(JSON_POS,pos)
                        .add(JSON_TEXTCOLOR, textColor)
                        .add(JSON_BOLD, bold)
                        .add(JSON_ITALICS, italics)
                        .add(JSON_SIZE, fontsize)
                        .add(JSON_FAMILY, fontfamily)
                        .add(JSON_ROTATE,rotate)
                        .add(JSON_TYPE, type)
                        .add(JSON_X, x)
                        .add(JSON_Y, y)
                        .add(JSON_RADIUS, radius)
                        .add(JSON_NAME, name)
                        .add(JSON_LABELX, labelx)
                        .add(JSON_LABELY, labely)
                        .add(JSON_FILL_COLOR, fillColorJson)
                        .add(JSON_LINES, linesArray)
                        .add(JSON_OUTLINE_COLOR, outlineColorJson).build();
            }
            
            else if(node instanceof DraggableLine){
                DraggableLine draggableShape = ((DraggableLine)node);
                String type = draggableShape.getShapeType();          
                double sx = draggableShape.getStartX();
                double sy = draggableShape.getStartY();
                double ex = draggableShape.getEndX();
                double ey = draggableShape.getEndY();
                String name=draggableShape.getName();
                double label1x = draggableShape.getLabel1X();
                double label1y = draggableShape.getLabel1Y();
                double label2x = draggableShape.getLabel2X();
                double label2y = draggableShape.getLabel2Y();
                double width=draggableShape.getStrokeWidth();
                JsonObject outlineColorJson = makeJsonColorObject((Color)draggableShape.getStroke());
                Boolean cir=draggableShape.isCircular();
                double circular=0;
                if(cir)
                    circular=1;    
                
                JsonArrayBuilder arrayBuilder2 = Json.createArrayBuilder(); 
                JsonObject points=null;
                for(Double p:draggableShape.getpoints())
                {
                    points=Json.createObjectBuilder().add(JSON_LOC, p).build();
                    arrayBuilder2.add(points);
                }
                JsonArray pointsArray = arrayBuilder2.build();
                
                JsonArrayBuilder arrayBuilder3 = Json.createArrayBuilder(); 
                JsonObject stations=null;
                for(DraggableStation s:draggableShape.getStations())
                {
                    stations=Json.createObjectBuilder().add(JSON_NAME, s.getName()).build();
                    arrayBuilder3.add(stations);
                }
                JsonArray stationsArray = arrayBuilder3.build();
                
                JsonObject textColor1 = makeJsonColorObject((Color)draggableShape.getLabel1().getTextFill());
                double bold1=0;
                if(draggableShape.isBold1())
                    bold1=1;
                double italics1=0;
                if(draggableShape.isItalics1())
                    italics1=1;
                double fontsize1=draggableShape.getLabel1().getFont().getSize();
                String fontfamily1=draggableShape.getLabel1().getFont().getFamily();
                
                JsonObject textColor2 = makeJsonColorObject((Color)draggableShape.getLabel2().getTextFill());
                double bold2=0;
                if(draggableShape.isBold2())
                    bold2=1;
                double italics2=0;
                if(draggableShape.isItalics2())
                    italics2=1;
                double fontsize2=draggableShape.getLabel2().getFont().getSize();
                String fontfamily2=draggableShape.getLabel2().getFont().getFamily();

                shapeJson = Json.createObjectBuilder()
                        .add(JSON_CIRCULAR,circular)
                        .add(JSON_TEXTCOLOR, textColor1)
                        .add(JSON_BOLD, bold1)
                        .add(JSON_ITALICS, italics1)
                        .add(JSON_SIZE, fontsize1)
                        .add(JSON_FAMILY, fontfamily1)
                        .add(JSON_TEXTCOLOR2, textColor2)
                        .add(JSON_BOLD2, bold2)
                        .add(JSON_ITALICS2, italics2)
                        .add(JSON_SIZE2, fontsize2)
                        .add(JSON_FAMILY2, fontfamily2)
                        .add(JSON_TYPE, type)
                        .add(JSON_STARTX, sx)
                        .add(JSON_STARTY, sy)
                        .add(JSON_ENDX, ex)
                        .add(JSON_ENDY, ey)
                        .add(JSON_WIDTH, width)
                        .add(JSON_NAME, name)
                        .add(JSON_LABELX, label1x)
                        .add(JSON_LABELY, label1y)
                        .add(JSON_LABEL2X, label2x)
                        .add(JSON_LABEL2Y, label2y)
                        .add(JSON_POINTS, pointsArray)
                        .add(JSON_STATIONS, stationsArray)
                        .add(JSON_OUTLINE_COLOR, outlineColorJson).build();
            }
            else if(node instanceof DraggableLabel){
                DraggableLabel draggableShape = ((DraggableLabel)node);
                String type = draggableShape.getShapeType(); 
                double x = draggableShape.getX();
                double y = draggableShape.getY();
                String text=draggableShape.getText();
                
                JsonObject textColor = makeJsonColorObject((Color)draggableShape.getTextFill());
                double bold=0;
                if(draggableShape.isBold())
                    bold=1;
                double italics=0;
                if(draggableShape.isItalics())
                    italics=1;
                double fontsize=draggableShape.getFont().getSize();
                String fontfamily=draggableShape.getFont().getFamily();
                
                
                shapeJson = Json.createObjectBuilder()
                        .add(JSON_TEXTCOLOR, textColor)
                        .add(JSON_BOLD, bold)
                        .add(JSON_ITALICS, italics)
                        .add(JSON_SIZE, fontsize)
                        .add(JSON_FAMILY, fontfamily)
                        .add(JSON_TYPE, type)
                        .add(JSON_X, x)
                        .add(JSON_Y, y)
                        .add(JSON_TEXT,text).build();           
            } 
            if(shapeJson!=null)
                arrayBuilder.add(shapeJson);
            }
	   
	JsonArray shapesArray = arrayBuilder.build();
	
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_MAPSIZEH,(((m3Workspace)(dataManager.getApp().getWorkspaceComponent())).getMap().getHeight()))
                .add(JSON_MAPSIZEW,(((m3Workspace)(dataManager.getApp().getWorkspaceComponent())).getMap().getWidth()))        
                .add(JSON_BACKGROUND,bg)
                .add(JSON_FILENAME, filename)
		.add(JSON_BG_COLOR, bgColorJson)
		.add(JSON_SHAPES, shapesArray)
		.build();
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    private JsonObject makeJsonColorObject(Color color) {
	JsonObject colorJson = Json.createObjectBuilder()
		.add(JSON_RED, color.getRed())
		.add(JSON_GREEN, color.getGreen())
		.add(JSON_BLUE, color.getBlue())
		.add(JSON_ALPHA, color.getOpacity()).build();
	return colorJson;
    }
      
    /**
     * This method loads data from a JSON formatted file into the data 
     * management component and then forces the updating of the workspace
     * such that the user may edit the data.
     * 
     * @param data Data management component where we'll load the file into.
     * 
     * @param filePath Path (including file name/extension) to where
     * to load the data from.
     * 
     * @throws IOException Thrown should there be an error reading
     * in data from the file.
     */
    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
	// CLEAR THE OLD DATA OUT
	m3Data dataManager = (m3Data)data;
	dataManager.resetData();
	
	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);
	
	// LOAD THE BACKGROUND COLOR
	Color bgColor = loadColor(json, JSON_BG_COLOR);
	dataManager.setBackgroundColor(bgColor);
        
        JsonObject bg=json.getJsonObject(JSON_BACKGROUND);
        JsonValue value5 = bg.get(JSON_BACKGROUND);
        JsonString bgstring=(JsonString)value5;
        if(!((bgstring.getString()).equals("")))
        {
            Image i=new Image(bgstring.getString());
                    
            // new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)
            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
            // new BackgroundImage(image, repeatX, repeatY, position, size)
            BackgroundImage backgroundImage = new BackgroundImage(i, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            // new Background(images...)
            Background background = new Background(backgroundImage);
            ((m3Workspace)dataManager.getApp().getWorkspaceComponent()).getMap().setBackground(background);
            dataManager.setBackgroundImagePath(bgstring.getString());
        }
        
        Double mapheight=getDataAsDouble(json, JSON_MAPSIZEH);
        Double mapwidth=getDataAsDouble(json, JSON_MAPSIZEW);
        ((m3Workspace)dataManager.getApp().getWorkspaceComponent()).getMap().setMaxSize(mapwidth, mapheight);
        
        JsonObject filename=json.getJsonObject(JSON_FILENAME);
        JsonValue value = filename.get(JSON_FILENAME);
        JsonString filenamestirng=(JsonString)value;
        dataManager.setFileName(filenamestirng.getString());
                
	// AND NOW LOAD ALL THE SHAPES
	JsonArray jsonShapeArray = json.getJsonArray(JSON_SHAPES);
	for (int i = 0; i < jsonShapeArray.size(); i++) {
	    JsonObject jsonShape = jsonShapeArray.getJsonObject(i);
            if(jsonShape.getString(JSON_TYPE).equals(IMAGE))
            {  
                ImageView image=new DraggableImage(null,null);
                double x = getDataAsDouble(jsonShape, JSON_X);
                double y = getDataAsDouble(jsonShape, JSON_Y);
                JsonValue value3 = jsonShape.get(JSON_PATH);
                JsonString string=(JsonString)value3;       
                ((DraggableImage)image).setPath((string.getString()));
                ((DraggableImage)image).setImage(new Image(string.getString()));
                ((DraggableImage)image).setX(x);
                ((DraggableImage)image).setY(y); 
                 dataManager.getShapes().add(image);
            }
            else if(jsonShape.getString(JSON_TYPE).equals(STATION))
            {
                //
                ArrayList<String> linesNames=new ArrayList<String>();
                JsonArray jsonShapeArray2 = jsonShape.getJsonArray(JSON_LINES);
                for (int k = 0; k < jsonShapeArray2.size(); k++) 
                {
                    JsonObject line = jsonShapeArray2.getJsonObject(k);
                    JsonValue name = line.get(JSON_NAME);
                    JsonString string=(JsonString)name;
                    linesNames.add(string.getString());
                }
                Color textColor = loadColor(jsonShape, JSON_TEXTCOLOR);
                double bold = getDataAsDouble(jsonShape, JSON_BOLD);
                double italics = getDataAsDouble(jsonShape, JSON_ITALICS);
                double fontsize=getDataAsDouble(jsonShape, JSON_SIZE);
                JsonValue family = jsonShape.get(JSON_FAMILY);
                JsonString fam=(JsonString)family;
                
                
                double x = getDataAsDouble(jsonShape, JSON_X);
                double y = getDataAsDouble(jsonShape, JSON_Y);
                double radius = getDataAsDouble(jsonShape, JSON_RADIUS);
                JsonValue value3 = jsonShape.get(JSON_NAME);
                JsonString string=(JsonString)value3;
                double lx = getDataAsDouble(jsonShape, JSON_LABELX);
                double ly = getDataAsDouble(jsonShape, JSON_LABELY);
                Color fillColor = loadColor(jsonShape, JSON_FILL_COLOR);
                Color outlineColor = loadColor(jsonShape, JSON_OUTLINE_COLOR);
                double rotate = getDataAsDouble(jsonShape, JSON_ROTATE);
                double pos = getDataAsDouble(jsonShape, JSON_POS);
                
                DraggableStation station=new DraggableStation(string.getString(),x,y,radius,lx,ly,fillColor,outlineColor,linesNames,rotate,pos);
                station.setText(textColor, bold, italics, fontsize, fam.getString());
                
                dataManager.getShapes().add(station);
                dataManager.getShapes().add(station.getLabel());
                ((m3Workspace)(dataManager.getApp().getWorkspaceComponent())).getStationsBox().getItems().add(string.getString());
                ((m3Workspace)(dataManager.getApp().getWorkspaceComponent())).getFromStation().getItems().add(string.getString());
                ((m3Workspace)(dataManager.getApp().getWorkspaceComponent())).getToStation().getItems().add(string.getString());
            }
            else if(jsonShape.getString(JSON_TYPE).equals(LINE))
            {
                //
                ArrayList<Double> points=new ArrayList<Double>();
                JsonArray jsonShapeArray2 = jsonShape.getJsonArray(JSON_POINTS);
                for (int k = 0; k < jsonShapeArray2.size(); k++) 
                {
                    JsonObject point = jsonShapeArray2.getJsonObject(k);
                    double p = getDataAsDouble(point, JSON_LOC);
                    points.add(p);
                }
                
                ArrayList<String> stations=new ArrayList<String>();
                JsonArray jsonShapeArray3 = jsonShape.getJsonArray(JSON_STATIONS);
                for (int k = 0; k < jsonShapeArray3.size(); k++) 
                {
                    JsonObject s = jsonShapeArray3.getJsonObject(k);
                    JsonValue name = s.get(JSON_NAME);
                    JsonString string=(JsonString)name;
                    stations.add(string.getString());
                }
                
                Color textColor = loadColor(jsonShape, JSON_TEXTCOLOR);
                double bold = getDataAsDouble(jsonShape, JSON_BOLD);
                double italics = getDataAsDouble(jsonShape, JSON_ITALICS);
                double fontsize=getDataAsDouble(jsonShape, JSON_SIZE);
                JsonValue family = jsonShape.get(JSON_FAMILY);
                JsonString fam=(JsonString)family;
                
                Color textColor2 = loadColor(jsonShape, JSON_TEXTCOLOR2);
                double bold2 = getDataAsDouble(jsonShape, JSON_BOLD2);
                double italics2 = getDataAsDouble(jsonShape, JSON_ITALICS2);
                double fontsize2=getDataAsDouble(jsonShape, JSON_SIZE2);
                JsonValue family2 = jsonShape.get(JSON_FAMILY2);
                JsonString fam2=(JsonString)family2;
                
                double sx = getDataAsDouble(jsonShape, JSON_STARTX);
                double sy = getDataAsDouble(jsonShape, JSON_STARTY);
                double ex = getDataAsDouble(jsonShape, JSON_ENDX);
                double ey = getDataAsDouble(jsonShape, JSON_ENDY);
                double width = getDataAsDouble(jsonShape, JSON_WIDTH);
                JsonValue value3 = jsonShape.get(JSON_NAME);
                JsonString string=(JsonString)value3;
                double label1x = getDataAsDouble(jsonShape, JSON_LABELX);
                double label1y = getDataAsDouble(jsonShape, JSON_LABELY);
                double label2x = getDataAsDouble(jsonShape, JSON_LABEL2X);
                double label2y= getDataAsDouble(jsonShape, JSON_LABEL2Y);
                Color outlineColor = loadColor(jsonShape, JSON_OUTLINE_COLOR);
                
                double cir=getDataAsDouble(jsonShape,JSON_CIRCULAR);
                boolean circular=false;
                if(cir==1)
                {
                    circular=true;
                }
                
                DraggableLine line=new DraggableLine(string.getString(),sx,sy,ex,ey,width,label1x,label1y,label2x,label2y,outlineColor,points,stations,circular);
                line.setText(textColor, bold, italics, fontsize, fam.getString(), textColor2, bold2, italics2, fontsize2, fam2.getString());
                dataManager.getShapes().add(line);
                dataManager.getShapes().add(line.getLabel1());
                dataManager.getShapes().add(line.getLabel2());
                ((m3Workspace)(dataManager.getApp().getWorkspaceComponent())).getLinesBox().getItems().add(string.getString());
            }
            else
            {
                JsonValue value3 = jsonShape.get(JSON_TEXT);
                JsonString string=(JsonString)value3; 
                double x = getDataAsDouble(jsonShape, JSON_X);
                double y = getDataAsDouble(jsonShape, JSON_Y);
                
                Color textColor = loadColor(jsonShape, JSON_TEXTCOLOR);
                double bold = getDataAsDouble(jsonShape, JSON_BOLD);
                double italics = getDataAsDouble(jsonShape, JSON_ITALICS);
                double fontsize=getDataAsDouble(jsonShape, JSON_SIZE);
                JsonValue family = jsonShape.get(JSON_FAMILY);
                JsonString fam=(JsonString)family;
                
                DraggableLabel label=new DraggableLabel(string.getString(),x,y);
                label.setText(textColor, bold, italics, fontsize, fam.getString());
                dataManager.getShapes().add(label);
            }
	}
        
        for(Node x:dataManager.getShapes())
        {
            if(x instanceof DraggableLine)
            {
               for(String name:((DraggableLine)x).getStationsNames())
                {
                    for(Node a:dataManager.getShapes())
                    {
                        if(a instanceof DraggableStation&&(((DraggableStation)a).getName()).equals(name))
                            (((DraggableLine)x).getStations()).add((DraggableStation)a);
                    }
                }
            }
            else if(x instanceof DraggableStation)
            {
                for(String name:((DraggableStation)x).getLinesNames())
                {
                    for(Node a:dataManager.getShapes())
                    {
                        if(a instanceof DraggableLine&&(((DraggableLine)a).getName()).equals(name))
                            (((DraggableStation)x).getLines()).add((DraggableLine)a);
                    }
                }
            }
        }     
    }
    
    private double getDataAsDouble(JsonObject json, String dataName) {
	JsonValue value = json.get(dataName);
	JsonNumber number = (JsonNumber)value;
	return number.bigDecimalValue().doubleValue();	
    }
    
    private Color loadColor(JsonObject json, String colorToGet) {
	JsonObject jsonColor = json.getJsonObject(colorToGet);
	double red = getDataAsDouble(jsonColor, JSON_RED);
	double green = getDataAsDouble(jsonColor, JSON_GREEN);
	double blue = getDataAsDouble(jsonColor, JSON_BLUE);
	double alpha = getDataAsDouble(jsonColor, JSON_ALPHA);
	Color loadedColor = new Color(red, green, blue, alpha);
	return loadedColor;
    }

    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }
    
   
    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        // GET THE DATA
	m3Data dataManager = (m3Data)data;
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
	ObservableList<Node> shapes = dataManager.getShapes();
	for (Node node : shapes) {
            JsonObject shapeJson=null;
            if(node instanceof DraggableLine){
                DraggableLine draggableShape = ((DraggableLine)node);
            
                //circular
                boolean circular=draggableShape.isCircular();
                String circularboolean;
                if(circular)
                    circularboolean="true";
                else
                    circularboolean="false";
                
                //color
                JsonObject outlineColorJson = makeJsonColorObject((Color)draggableShape.getStroke());
    
                //stations
                JsonArrayBuilder arrayBuilder1 = Json.createArrayBuilder(); 
                JsonObject stations=null;
                for(DraggableStation s:draggableShape.getStations())
                {
                    arrayBuilder1.add(s.getName());
                }
                JsonArray stationsArray = arrayBuilder1.build();
                
                shapeJson = Json.createObjectBuilder()
                        .add(JSON_NAME,draggableShape.getName())
                        .add(JSON_CIRCULAR,circularboolean)
                        .add(JSON_COLOR, outlineColorJson)
                        .add(JSON_STATIONS, stationsArray).build();
            }
            if(shapeJson!=null)
                arrayBuilder.add(shapeJson);
            }
	    JsonArray linesArray = arrayBuilder.build();
        
        JsonArrayBuilder arrayBuilder2 = Json.createArrayBuilder();
        for (Node node : shapes) {
            JsonObject shapeJson=null;
            if(node instanceof DraggableStation){
                DraggableStation draggableShape = ((DraggableStation)node);          
                double x = draggableShape.getX();
                double y = draggableShape.getY();
                String name=draggableShape.getName();
                
                shapeJson = Json.createObjectBuilder()
                        .add(JSON_NAME, name)
                        .add(JSON_X, x)
                        .add(JSON_Y, y).build();    
            }
            if(shapeJson!=null)
                arrayBuilder2.add(shapeJson);
        }
        JsonArray stationsArray = arrayBuilder2.build();
        
        // THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_NAME, dataManager.getFileName())
		.add(JSON_LINES , linesArray )
		.add(JSON_STATIONSARRAY, stationsArray)
		.build();
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    
    
    /**
     * This method is provided to satisfy the compiler, but it
     * is not used by this application.
     */
    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
	
    }
}
       