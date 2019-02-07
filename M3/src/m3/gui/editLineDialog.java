/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m3.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jtps.jTPS;
import m3.data.DraggableLine;
import m3.transaction.AddStationToLineTransaction;
import m3.transaction.RemoveStationFromLineTransaction;

/**
 *
 * @author Sabrina To
 */
public class editLineDialog extends Stage{
    VBox pane;
    Scene scene;
    Label n;
    Label c;
    TextField t;
    Button ok;
    ColorPicker cp;
    String name;
    Color color;
    CheckBox cir;
    boolean circular;
    
    public editLineDialog(String oldName,DraggableLine line,jTPS jtps){
        pane=new VBox();
        
        n=new Label("Name:");
        pane.getChildren().add(n);
        
        t=new TextField(oldName);
        pane.getChildren().add(t);
        
        c=new Label("Choose a color:");
        pane.getChildren().add(c);
        
        cp=new ColorPicker();
        pane.getChildren().add(cp);
        
        
        circular=line.isCircular();
        cir=new CheckBox("circular");
        if(circular)
            cir.setSelected(true);
        cir.setOnAction(e->{
          if(line.getStations().size()>=1){ 
            if(circular)
            {
                jtps.addTransaction(new RemoveStationFromLineTransaction(line.getStations().get(line.getStations().size()-1),line));
                line.setCircular(false);
                circular=false;
            }
            else
            {
                jtps.addTransaction(new AddStationToLineTransaction(line.getStations().get(0),line));
                line.setCircular(true);
                circular=true;
            }
          }
        });
        pane.getChildren().add(cir);
        
        ok=new Button("ok");
        ok.setOnAction(e->{
            name=t.getText();
            color=cp.getValue();
            this.hide();
        });
        pane.getChildren().add(ok);
       
        pane.setBackground(new Background(new BackgroundFill(Color.FLORALWHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        setTitle("Edit Line");
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
