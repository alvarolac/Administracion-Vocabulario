/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vocabularioadmin;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author linus
 */
public class FXMLCreateCategoriaController implements Initializable {

    private Connection conn = null;
    private ResultSet rs = null;
    private Statement stmt = null;
    private PreparedStatement pst = null;
    
    @FXML private Button volverButtonCRUD;
    @FXML private TextField categoriaNameField;
    @FXML private TextField categoriaDescriptionField;
    
      
    @FXML protected void handleVolverButtonAction(ActionEvent event) throws Exception {
        
        Stage stage;
        Parent root;
        
        //System.out.println("SLAIDOA: " + event.getSource());
        
        stage = (Stage) volverButtonCRUD.getScene().getWindow();
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
    
    
    @FXML protected void handleGuardarButtonAction(ActionEvent event) throws Exception {
        
        Stage stage;
        Parent root;
        
        if(setCategoriaBD(categoriaNameField.getText(), categoriaDescriptionField.getText()))
        {
            stage = (Stage) volverButtonCRUD.getScene().getWindow();
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.conn = BDmanager.ConnectDB();
        
        // TODO
    }    
    
    
    public Boolean setCategoriaBD(String nombre, String descripcion){
    
       
        if(nombre.isEmpty()){
            JOptionPane.showMessageDialog(null, "El campo Nombre no debe estar "
                    + "vacío");
            return false;
        }
        
        if(existCategoriaBD(nombre)){
            JOptionPane.showMessageDialog(null, "El nombre: "+nombre+", ya "
                    + "existe en la base de datos");
            return false;
        }
        
        String query = "INSERT INTO categoria (nombre, descripcion) VALUES('"
                +nombre+"','"+descripcion+"')";
                        
        try{
            //pst = conn.prepareStatement(query);
            //Para agregar los parametros '?'
            //pst.setInt(1, xRondas);
            //pst.setInt(1, nivel);
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            
        }catch(SQLException e){
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
        
        JOptionPane.showMessageDialog(null, "Categoria Guardada");
        return true;
    }
    
    
    public Boolean existCategoriaBD(String nombre){
        
        String query = "SELECT DISTINCT categoria.* FROM categoria WHERE "
                + "categoria.nombre like '"+nombre+"'";
        
        try{
            pst = conn.prepareStatement(query);
            //Para agregar los parametros '?'
            //pst.setInt(1, xRondas);
            //pst.setInt(1, nivel);
            
            rs = pst.executeQuery();

            Boolean flag = rs.next();

            rs.close();
            pst.close();

            return flag;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
      
        return false;
    }
    
    
    
}
