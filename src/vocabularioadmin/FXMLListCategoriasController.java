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
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.geometry.Rectangle2D;
import javax.swing.JOptionPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;



/**
 * FXML Controller class
 *
 * @author linus
 */
public class FXMLListCategoriasController implements Initializable {

    @FXML private Button volverButtonCRUD;
    @FXML private ListView listBoxMain;
    //@FXML private VBox CheckList;
    
    
    
    private List<Categoria> categorias;
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    
    @FXML protected void handleVolverButtonAction(ActionEvent event) throws Exception {
        
        Stage stage;
        Parent root;
        
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
    
    @FXML protected void handleMouseClick(MouseEvent event) throws Exception {
        
       //System.out.println("clicked on " + listBoxMain.getSelectionModel().getSelectedItem());
        
       Stage stage;
       Parent root;
        
        
        //System.out.println("SLAIDOA: " + getCategoriasBD(2).get(0).getNombre());
        
        
        FXMLListPalabrasByCategoriaController controller = 
        new FXMLListPalabrasByCategoriaController(
            categorias.get(listBoxMain.getSelectionModel().getSelectedIndex()).getNombre(), 
            categorias.get(listBoxMain.getSelectionModel().getSelectedIndex()).getID()
        );
        // Set it in the FXMLLoader
        
        stage = (Stage) volverButtonCRUD.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLListPalabrasByCategoria.fxml"));
        loader.setController(controller);
        
        root = loader.load();
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
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.conn = BDmanager.ConnectDB();
        
        categorias = getCategoriasBD();
        
        List<String> nombres = new ArrayList();
        for(int i=0; i<categorias.size(); i++){
            nombres.add(categorias.get(i).getNombre());
        }
        
        
        final ObservableList<String> listItems = FXCollections.observableArrayList(nombres); 
        listBoxMain.setItems(listItems);
             

    }    
    
    
    public List<Categoria> getCategoriasBD(){
    
        List<Categoria> _categorias = new ArrayList();
            
        String query = "SELECT DISTINCT categoria.* FROM categoria";
                
        try{
            pst = conn.prepareStatement(query);
            //Para agregar los parametros '?'
            //pst.setInt(1, xRondas);
            //pst.setInt(1, nivel);
            
            rs = pst.executeQuery();

            while(rs.next()){
                int p = rs.getInt(1);
                String s = rs.getString(2);

                _categorias.add(new Categoria(p, s.toLowerCase()));
            }
            
            rs.close();
            pst.close();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
                
        return _categorias;
    }
    
}
