package m3.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Sabrina To
 */
public class textColorDialog extends Stage  {
    VBox pane;
    Scene scene;
    Label c;
    ColorPicker cp;
    Button ok;
    Color color;
    
     public textColorDialog(){
        pane=new VBox();
        
        c=new Label("Choose a color:");
        pane.getChildren().add(c);
        
        cp=new ColorPicker();
        pane.getChildren().add(cp);
        
        ok=new Button("ok");
        ok.setOnAction(e->{ 
            color=cp.getValue();
            this.hide();
        });
        pane.getChildren().add(ok);
       
        pane.setBackground(new Background(new BackgroundFill(Color.FLORALWHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        setTitle("Change text Color");
        setMinHeight(250);
        setMinWidth(500);
        scene = new Scene(pane);
        this.setScene(scene);
        
        showAndWait();
    }
     
      public Color getColor() {
        return color;
    }
}
