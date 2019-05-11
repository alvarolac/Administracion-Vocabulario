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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author linus
 */
public class FXMLCategoriasCRUDController implements Initializable {

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
        
        //System.out.println("SLAIDOA: " + getCategoriasBD(2).get(0).getNombre());
        
        stage = (Stage) volverButtonCRUD.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("FXMLListCategorias.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    
    @FXML protected void handleCrearButtonAction(ActionEvent event) throws Exception {
        
        Stage stage;
        Parent root;
        
        //System.out.println("SLAIDOA: " + getCategoriasBD(2).get(0).getNombre());
        
        stage = (Stage) volverButtonCRUD.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("FXMLCreateCategoria.fxml"));
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
        this.conn = BDmanager.ConnectDB();
        
        
        
    }    


    public List<Categoria> getCategoriasBD(int nivel){
    
        List<Categoria> categorias = new ArrayList();
        
        //Aqui deberia agregarse el DONDE las palabras sean de 
        //nivel TAL y categoria TAL
        //String query = "select * from Palabras where ? and ?";

        String query = "SELECT DISTINCT Categorias.* FROM Categorias INNER JOIN"+
                " CategoriasNivel ON Categorias.id = CategoriasNivel.categoria "+
                "INNER JOIN Niveles ON CategoriasNivel.nivel = Niveles.id Where"+
                " grado = ?";
        
        try{
            pst = conn.prepareStatement(query);
            //Para agregar los parametros '?'
            //pst.setInt(1, xRondas);
            pst.setInt(1, nivel);
            
            rs = pst.executeQuery();

            while(rs.next()){
                int p = rs.getInt(1);
                String s = rs.getString(2);

                categorias.add(new Categoria(p, s));
            }
            
            rs.close();
            pst.close();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return categorias;
    }

    
}
