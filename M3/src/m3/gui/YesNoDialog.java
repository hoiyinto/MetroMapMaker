package m3.gui;

import djf.ui.AppYesNoCancelDialogSingleton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Sabrina To
 */
public class YesNoDialog extends Stage {
    // GUI CONTROLS FOR OUR DIALOG
    VBox messagePane;
    Scene messageScene;
    Label messageLabel;
    Button yesButton;
    Button noButton;
    String selection;
    
    public static final String YES = "Yes";
    public static final String NO = "No";
    
    public YesNoDialog(){
        // LABEL TO DISPLAY THE CUSTOM MESSAGE
        messageLabel = new Label("Set Image Background?");        

        // YES, NO, AND CANCEL BUTTONS
        yesButton = new Button(YES);
        noButton = new Button(NO);
        
        EventHandler<ActionEvent> yesNoCancelHandler = (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            this.selection = sourceButton.getText();
            this.hide();
        };
        
        yesButton.setOnAction(yesNoCancelHandler);
        noButton.setOnAction(yesNoCancelHandler);
        
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(yesButton);
        buttonBox.getChildren().add(noButton);
        
        messagePane = new VBox();
        messagePane.setAlignment(Pos.CENTER);
        messagePane.getChildren().add(messageLabel);
        messagePane.getChildren().add(buttonBox);
        
         // MAKE IT LOOK NICE
        messagePane.setPadding(new Insets(10, 20, 20, 20));
        messagePane.setSpacing(10);

        // AND PUT IT IN THE WINDOW
        messageScene = new Scene(messagePane);
        this.setScene(messageScene);
        
        showAndWait();
    }
    
    public String getSelection() {
        return selection;
    }
}
