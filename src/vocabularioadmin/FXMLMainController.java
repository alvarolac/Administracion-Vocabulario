/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vocabularioadmin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.application.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author linus
 */
public class FXMLMainController implements Initializable {

    @FXML private Button crearCategoriaButton;
    
    
    @FXML protected void handleCrearButtonAction(ActionEvent event) throws Exception {
        
        Stage stage;
        Parent root;
        
        //System.out.println("SLAIDOA: " + getCategoriasBD(2).get(0).getNombre());
        
        stage = (Stage) crearCategoriaButton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("FXMLCreateCategoria.fxml"));
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
  
    @FXML protected void handleCrearPalabraButtonAction(ActionEvent event) throws Exception {
        
        Stage stage;
        Parent root;
        
        //System.out.println("SLAIDOA: " + getCategoriasBD(2).get(0).getNombre());
        
        stage = (Stage) crearCategoriaButton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("FXMLCreatePalabra.fxml"));
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
  
  
    @FXML protected void handleListarButtonAction(ActionEvent event) throws Exception {
        
        Stage stage;
        Parent root;
        
        //System.out.println("SLAIDOA: " + getCategoriasBD(2).get(0).getNombre());
        
        stage = (Stage) crearCategoriaButton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("FXMLListCategorias.fxml"));
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

    
    @FXML protected void handleListarPalabraButtonAction(ActionEvent event) throws Exception {
        
        Stage stage;
        Parent root;
        
        //System.out.println("SLAIDOA: " + getCategoriasBD(2).get(0).getNombre());
        
        stage = (Stage) crearCategoriaButton.getScene().getWindow();
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
        
    
    @FXML protected void handleEliminarCategoria(ActionEvent event) throws Exception {
        
        Stage stage;
        Parent root;
        
        stage = (Stage) crearCategoriaButton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("FXMLDeleteCategoria.fxml"));
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
    
   
    @FXML protected void handleSalirButtonAction(ActionEvent event) throws Exception {
        
        Platform.exit();
    }
     
     
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
