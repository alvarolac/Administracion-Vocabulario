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
import java.util.Collections;
import java.util.Comparator;
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
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javax.swing.JOptionPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;



/**
 * FXML Controller class
 *
 * @author linus
 */
public class FXMLListPalabrasController implements Initializable {

    @FXML private Button volverButtonCRUD;
    @FXML private ListView listBoxMain;
    //@FXML private VBox CheckList;
    
    
    List<Palabra> palabras = new ArrayList();
    
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
        
        Stage stage;
        Parent root;
        
        
        FXMLEditPalabraController controller = 
        new FXMLEditPalabraController(
            palabras.get(listBoxMain.getSelectionModel().getSelectedIndex()).getNombre(), 
            palabras.get(listBoxMain.getSelectionModel().getSelectedIndex()).getID()
        );
        // Set it in the FXMLLoader
        
        stage = (Stage) volverButtonCRUD.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLEditPalabra.fxml"));
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
        palabras = getPalabrasBD();
           
        List<String> nombrePalabras = new ArrayList(); 
        
        for (Palabra palabra: palabras)
            nombrePalabras.add(palabra.getNombre());
        
        final ObservableList<String> listItems = FXCollections.observableArrayList(nombrePalabras); 
        listBoxMain.setItems(listItems);
    }    
    
    public List<Palabra> getPalabrasBD(){
    
        List<Palabra> _palabras = new ArrayList();
        String query = "SELECT DISTINCT id,nombre FROM palabra";
            
        try{
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            
            while(rs.next()){
                int p = rs.getInt(1);
                String s = rs.getString(2);
                _palabras.add(new Palabra (p, s.toLowerCase()));
            }
            
            rs.close();
            pst.close();
            
            if (_palabras.size() > 0) {
                Collections.sort(_palabras, new Comparator<Palabra>() {
                    @Override
                    public int compare(final Palabra object1, final Palabra object2) {
                        return object1.getNombre().compareTo(object2.getNombre());
                    }
                });
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return _palabras;
    }
    
     
     
    
}
