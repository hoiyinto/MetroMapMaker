/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.gui;

import java.util.List;
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
import m3.data.DraggableStation;

/**
 *
 * @author Sabrina To
 */
public class RouteDialog extends Stage {
    VBox pane;
    Scene scene;
    Text n;
    Button ok;
    
    public RouteDialog(String from,String to,List<DraggableStation> path)
    {
     pane=new VBox();
        
        n=new Text("Route from "+from+" to "+to);
        n.setFont(Font.font(null,null,null,25));
        pane.getChildren().add(n);
        
        if(path == null) {
            Text a=new Text("No path found.");
            a.setFont(Font.font(null,null,null,20));
                a.setFill(Color.CADETBLUE);
                pane.getChildren().add(a);  
        }
        else {
            for(DraggableStation s : path) {
                Text a=new Text(s.getName());
                a.setFont(Font.font(null,null,null,20));
                a.setFill(Color.CADETBLUE);
                pane.getChildren().add(a); 
                Text b=new Text("|");
                b.setFont(Font.font(null,null,null,20));
                pane.getChildren().add(b);
            }
            pane.getChildren().remove(pane.getChildren().size()-1);
        }       
        
        ok=new Button("ok");
        ok.setOnAction(e->{
            this.hide();
        });
        pane.getChildren().add(ok);
        
        pane.setAlignment(Pos.CENTER);
        pane.setBackground(new Background(new BackgroundFill(Color.FLORALWHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        setTitle("Metro Map Maker - Route");
        setMinHeight(250);
        setMinWidth(500);
        scene = new Scene(pane);
        this.setScene(scene);
        
        showAndWait();   
    }
}
