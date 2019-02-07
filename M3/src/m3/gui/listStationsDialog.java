package m3.gui;

import djf.ui.WelcomeDialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import m3.data.DraggableLine;

public class listStationsDialog extends Stage{
    VBox pane;
    Scene scene;
    Text n;
    Button ok;
    
    public listStationsDialog(DraggableLine line)
    {
        pane=new VBox();
        
        n=new Text(line.getName()+" Stops:");
        n.setFont(Font.font(null,null,null,25));
        pane.getChildren().add(n);
        
        
        for (int i = 0; i < line.getStations().size(); i++) {
                Text a=new Text(line.getStations().get(i).getName());
                a.setFont(Font.font(null,null,null,20));
                a.setFill(Color.CADETBLUE);
                pane.getChildren().add(a);   
        }
        
        ok=new Button("ok");
        ok.setOnAction(e->{
            this.hide();
        });
        pane.getChildren().add(ok);
        
        pane.setAlignment(Pos.CENTER);
        pane.setBackground(new Background(new BackgroundFill(Color.FLORALWHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        setTitle("Metro Map Maker - Metro Line Stops");
        setMinHeight(250);
        setMinWidth(500);
        scene = new Scene(pane);
        this.setScene(scene);
        
        showAndWait();
    }
    
}
