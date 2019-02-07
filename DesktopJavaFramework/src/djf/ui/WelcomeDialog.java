/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package djf.ui;

import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import java.io.File;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

/**
 *
 * @author Sabrina To
 */
public class WelcomeDialog extends Stage {
    HBox welcomepane;
    VBox left;
    VBox right;
    Scene scene;
    String selection;
    
    public WelcomeDialog(){
        welcomepane=new HBox();
        left=new VBox();
        right=new VBox();
        
        setTitle("Welcome to Metro Map Maker");
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + "m3logo.png";
        Image logo=new Image(imagePath);
        ImageView logoview=new ImageView(logo);
        Text create=new Text("Create New Map");
        create.setUnderline(true);
        create.setFont(Font.font(null,null,null,20));
        create.setFill(Color.CADETBLUE);
        
        create.setOnMouseClicked(e->{
            selection="create";
            WelcomeDialog.this.hide();
        });
        
        right.getChildren().addAll(logoview,create);
        
        Text recent=new Text("Recent Work");
        recent.setFont(Font.font(null,null,null,20));
        left.getChildren().add(recent); 
        
        File folder = new File("./work/");
        if(folder.exists()){
            File[] listOfFiles = folder.listFiles();

            for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                Text a=new Text(listOfFiles[i].getName());
                a.setUnderline(true);
                a.setOnMouseClicked(e->{
                    selection=a.getText();
                    WelcomeDialog.this.hide();
                }); 
                a.setFont(Font.font(null,null,null,20));
                a.setFill(Color.CADETBLUE);
                left.getChildren().add(a);   
                }
             }
        }
        
        left.setMinSize(600,600);
        right.setMinSize(600,600);
        left.setAlignment(Pos.CENTER);
        right.setAlignment(Pos.CENTER);
        left.setBackground(new Background(new BackgroundFill(Color.CORNSILK, CornerRadii.EMPTY, Insets.EMPTY)));
        right.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
        
        welcomepane.getChildren().addAll(left,right);
        
        scene = new Scene(welcomepane);
        this.setScene(scene);
        
        showAndWait();
    }
    
    public String getSelection() {
        return selection;
    }
   
}
