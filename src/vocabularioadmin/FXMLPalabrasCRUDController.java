/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vocabularioadmin;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

/**
 * FXML Controller class
 *
 * @author linus
 */
public class FXMLPalabrasCRUDController implements Initializable {

    @FXML private Button volverButtonCRUD;
    
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;
      
    
    @FXML protected void handleVolverButtonAction(ActionEvent event) throws Exception {
        
        Stage stage;
        Parent root;
        
        //System.out.println("SLAIDOA: " + event.getSource());
        
        stage = (Stage) volverButtonCRUD.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("FXMLMain.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    
    @FXML protected void handleListarButtonAction(ActionEvent event) throws Exception {
        
        
        Stage stage;
        Parent root;
        
        stage = (Stage) volverButtonCRUD.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("FXMLListPalabras.fxml"));
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
    
    @FXML protected void handleCrearButtonAction(ActionEvent event) throws Exception {
        
        Stage stage;
        Parent root;
        
        stage = (Stage) volverButtonCRUD.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("FXMLCreatePalabra.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
            
    }
    
        @FXML protected void handleEliminarButtonAction(ActionEvent event) throws Exception {
        
        Stage stage;
        Parent root;
        
        //System.out.println("SLAIDOA: " + getCategoriasBD(2).get(0).getNombre());
        
        stage = (Stage) volverButtonCRUD.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("FXMLDeleteCategoria.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
