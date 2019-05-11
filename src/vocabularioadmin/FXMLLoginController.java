/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vocabularioadmin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
/**
 *
 * @author linus
 */
public class FXMLLoginController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        //System.out.println("You clicked me!");
        //label.setText("Hello World!");
    }
   
    @FXML private Text actiontarget;
    @FXML private TextField userField;
    @FXML private PasswordField passwordField;
    @FXML private AnchorPane content;
    
    
    @FXML protected void handleSubmitButtonAction(ActionEvent event) throws Exception {
        
        Stage stage;
        Parent root;
        
        if(userField.getText().equals("administrador") &&
           passwordField.getText().equals("#admin3542*"))
        {
            
            stage = (Stage) userField.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("FXMLMain.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());
            stage.show();
            
        }else{
            actiontarget.setText("¡Credenciales invalidas!");
            stage = (Stage) userField.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("FXMLMain.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());
            stage.show();
        }
 


    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
