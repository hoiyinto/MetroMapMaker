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
public class addLineDialog extends Stage {
    VBox pane;
    Scene scene;
    Label n;
    Label c;
    TextField t;
    Button ok;
    ColorPicker cp;
    String name;
    Color color;
    
    
    public addLineDialog(){
        pane=new VBox();
        
        n=new Label("Name:");
        pane.getChildren().add(n);
        
        t=new TextField();
        pane.getChildren().add(t);
        
        c=new Label("Choose a color:");
        pane.getChildren().add(c);
        
        cp=new ColorPicker();
        pane.getChildren().add(cp);
        
        ok=new Button("ok");
        ok.setOnAction(e->{
            name=t.getText();
            color=cp.getValue();
            this.hide();
        });
        pane.getChildren().add(ok);
       
        pane.setBackground(new Background(new BackgroundFill(Color.FLORALWHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        setTitle("Add Line");
        setMinHeight(250);
        setMinWidth(500);
        scene = new Scene(pane);
        this.setScene(scene);
        
        showAndWait();
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
    
}
