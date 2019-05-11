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
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.stage.Screen;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author linus
 */
public class FXMLDeleteCategoriaController implements Initializable {

    
    @FXML private Button volverButtonCRUD;
    @FXML private TextField categoriaField;
    
    
    
    private Connection conn = null;
    private ResultSet rs = null;
    private Statement stmt = null;
    private PreparedStatement pst = null;
    
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
    
    
    @FXML protected void handleEliminarButtonAction(ActionEvent event) throws Exception {
        
        Stage stage;
        Parent root;
        
        if(eliminarPalabraBD(categoriaField.getText())){
        
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
    }    
    
    public Boolean eliminarPalabraBD(String nombre)
    {
        if(nombre.isEmpty()){
            JOptionPane.showMessageDialog(null, "El campo Nombre no debe estar "
                    + "vacío");
            return false;
        }
        
        String query = "SELECT DISTINCT id FROM categoria WHERE nombre ='"+nombre+"';";
        
        int id = -1;
        
        try{
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            while(rs.next()){
                id = rs.getInt(1);
            }

            rs.close();
            pst.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        if(id == -1){
            JOptionPane.showMessageDialog(null, "Categoría no encontrada");
            return false;
        }
        
        
        query =  "DELETE FROM categoria_palabra WHERE id_categoria = "+ id + ";\n"+
                 "DELETE FROM categoria WHERE id = "+ id + ";\n";               
        
        try{
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
                        
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
        
        JOptionPane.showMessageDialog(null, "Categoría Eliminada");
        return true;
    }
    
    
}
