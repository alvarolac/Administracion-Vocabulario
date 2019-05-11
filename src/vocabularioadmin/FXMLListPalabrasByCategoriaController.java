/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vocabularioadmin;

import java.awt.Desktop;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author linus
 */
public class FXMLListPalabrasByCategoriaController implements Initializable {

    private String name;
    private int id;
    /**
     * Accepts the firstName, lastName and stores them to specific instance variables
     * 
     * @param name
     * @param id
     */
    public FXMLListPalabrasByCategoriaController(String name, int id) {
        this.name = name;
        this.id = id;
    }
    
    String categoriaSeleccionada = " ";
    int idCategoriaSeleccionada = -1;
    private Desktop desktop = Desktop.getDesktop();
    
    private Connection conn = null;
    private ResultSet rs = null;
    private Statement stmt = null;
    private PreparedStatement pst = null;
    
    List<Categoria> categorias = new ArrayList();
    ArrayList<Integer> nivelesSelection = new ArrayList();
    List<Palabra> palabras;
    
    ArrayList <CheckBox> checkList = new ArrayList();
    ObservableList<CheckBox> listItems;
    List<Integer> selected;
            
    @FXML private Button volverButtonCRUD;
    @FXML private ListView listBoxMain;
    
    
    
    
    
    @FXML protected void handleVolverButtonAction(ActionEvent event) throws Exception {
        
        Stage stage;
        Parent root;
        
         stage = (Stage) volverButtonCRUD.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("FXMLListCategorias.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        
    }
    
    
    @FXML protected void handleGuardarButtonAction(ActionEvent event) throws Exception {
        
        Stage stage;
        Parent root;
        
        List<Integer> deleteList = new ArrayList();
        List<Integer> insertList = new ArrayList();
        
        
        for(int i = 0; i < listItems.size(); i ++){
            
            if(selected.contains(palabras.get(i).getID())){
                
                if(listItems.get(i).isSelected()){
                   continue;
                }else{
                    deleteList.add(palabras.get(i).getID());
                }
                
            }else{
                
                if(listItems.get(i).isSelected()){
                   insertList.add(palabras.get(i).getID());
                }
            }   
        }
        
        if(setPalabrasCategoriaBD(insertList, deleteList, id))
        {
         stage = (Stage) volverButtonCRUD.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("FXMLListCategorias.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();    
        }
    }    

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        this.conn = BDmanager.ConnectDB();
        for(int i=0; i<8; i++)
            nivelesSelection.add(0);
                

        palabras = getPalabrasBD();
        
       selected = getPalabrasCategoriaBD(id);
        
        final CheckBox[] cbs = new CheckBox[palabras.size()];
        
        for (int i = 0; i < palabras.size(); i++) {
            cbs[i] = new CheckBox(palabras.get(i).getNombre());
            cbs[i].setSelected(false);
            
            for(int j=0; j < selected.size(); j++){
                if(selected.get(j) == palabras.get(i).getID()){
                    cbs[i].setSelected(true);
                }
            }
        }
                        
        for(int i=0; i < cbs.length; i++){
            checkList.add(cbs[i]);
        }
        
        listItems = FXCollections.observableArrayList(checkList);
        listBoxMain.setItems(listItems);
        listBoxMain.setMinHeight(300);
        listBoxMain.setEditable(false);
        
    }    




    public List<Palabra> getPalabrasBD(){
    
        List<Palabra> _palabras = new ArrayList();
        
        String query = "SELECT DISTINCT id,nombre FROM palabra";
                
        try{
            pst = conn.prepareStatement(query);
            //Para agregar los parametros '?'
            //pst.setInt(1, xRondas);
            //pst.setInt(1, nivel);
            
            rs = pst.executeQuery();
            
            while(rs.next()){
                int p = rs.getInt(1);
                String s = rs.getString(2).toLowerCase();
                _palabras.add(new Palabra(p, s));
            }
                        
            rs.close();
            pst.close();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        if (_palabras.size() > 0) {
            Collections.sort(_palabras, new Comparator<Palabra>() {
                @Override
                public int compare(final Palabra object1, final Palabra object2) {
                    return object1.getNombre().compareTo(object2.getNombre());
                }
            });
        }
        
        return _palabras;
    }
    
    
    
        public List<Integer> getPalabrasCategoriaBD(int categoriaId){
    
        List<Integer> _palabras = new ArrayList(); 
        String query = "SELECT DISTINCT id_palabra FROM categoria_palabra  WHERE id_categoria = ?";
                
        try{
            pst = conn.prepareStatement(query);
            //Para agregar los parametros '?'
            pst.setInt(1, categoriaId);           
            rs = pst.executeQuery();
            
            while(rs.next()){
                int p = rs.getInt(1);
                _palabras.add(p);
            }
                        
            rs.close();
            pst.close();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return _palabras;
    }
    
    
    public Boolean setCategoriaBD(String nombre, String descripcion, 
            int categoria, ArrayList<Integer> niveles, String imagen){
    
       
        if(nombre.isEmpty()){
            JOptionPane.showMessageDialog(null, "El campo Nombre no debe estar "
                    + "vacío");
            return false;
        }
        
        if(existPalabraBD(nombre)){
            JOptionPane.showMessageDialog(null, "El nombre: "+nombre+", ya "
                    + "existe en la base de datos");
            return false;
        }
        
        String query = "INSERT INTO palabra (nombre, descripcion, imagen) "
                + "VALUES('"+nombre+"','"+descripcion+"','"+imagen+"');\n";
        
                        
        try{
            
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
            

            //Para agregar los parametros '?'
            //pst.setInt(1, xRondas);
            //pst.setInt(1, nivel);
            //pst.execute(query);

            int lastID = getLastPalabraIDBD();
            
            insertCategoriaPalabraBD(lastID, categoria);
            
            for(int i=0; i<8; i++)
                if(niveles.get(i) == 1)
                    insertNivelPalabraBD(lastID, i+1);
            
            
            //System.out.println("Inserted record's ID: " + getLastPalabraIDBD());
            //pst.close();
            stmt.close();
            
            
            
            
        }catch(SQLException e){
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
        
        JOptionPane.showMessageDialog(null, "Palabra Guardada");
        return true;
    }
    
    
    public Boolean insertCategoriaPalabraBD(int idPalabra, int idCategoria){
    
       
        
        String query = "INSERT INTO categoria_palabra (id_palabra, id_categoria)"
                + "VALUES("+idPalabra+","+idCategoria+");\n";   
                        
        try{
            
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
            
            //Para agregar los parametros '?'
            //pst.setInt(1, xRondas);
            //pst.setInt(1, nivel);
            //pst.execute(query);
            //System.out.println("Inserted record's ID: " + getLastPalabraIDBD());
            //pst.close();
            stmt.close();     
            
            
        }catch(SQLException e){
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
        
        //JOptionPane.showMessageDialog(null, "Palabra Guardada");
        return true;
    }
    
    public Boolean insertNivelPalabraBD(int idPalabra, int idNivel){
    
       
        
        String query = "INSERT INTO nivel_palabra (id_palabra, id_nivel)"
                + "VALUES("+idPalabra+","+idNivel+");\n";
        
                        
        try{
            
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
            

            //Para agregar los parametros '?'
            //pst.setInt(1, xRondas);
            //pst.setInt(1, nivel);
            //pst.execute(query);

            
            //System.out.println("Inserted record's ID: " + getLastPalabraIDBD());
            //pst.close();
            stmt.close();
            
            
            
            
        }catch(SQLException e){
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
        
        //JOptionPane.showMessageDialog(null, "Palabra Guardada");
        return true;
    }
    
    
    public Boolean existPalabraBD(String nombre){
        
        String query = "SELECT DISTINCT palabra.* FROM palabra WHERE "
                + "palabra.nombre like '"+nombre+"'";
        
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
    
    
    
     public int getLastPalabraIDBD(){
        
        String query = "SELECT DISTINCT palabra.* FROM palabra ";
        
        try{
            pst = conn.prepareStatement(query);
            //Para agregar los parametros '?'
            //pst.setInt(1, xRondas);
            //pst.setInt(1, nivel);
            
            rs = pst.executeQuery();

            int flag = -1;
            
            while(rs.next()){
                flag = rs.getInt("id");
            }

            rs.close();
            pst.close();

            return flag;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
      
        return -1;
    }

    
    public Boolean setPalabrasCategoriaBD(  List<Integer> _insert, 
                                            List<Integer> _delete,
                                            int _categoriaId)
    {     
        for (int i=0; i < _insert.size(); i++)
        {
            String query = "INSERT INTO categoria_palabra (id_palabra, id_categoria) "
                    + "VALUES("+_insert.get(i)+","+_categoriaId+");\n";
            try{
                stmt = conn.createStatement();
                stmt.executeUpdate(query);
                stmt.close();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, e.getMessage());
                return false;
            }
        }
     
        for (int i=0; i < _delete.size(); i++)
        {
            String query = "DELETE FROM categoria_palabra WHERE "
                + "(id_palabra = "+_delete.get(i)+" AND id_categoria = "+_categoriaId+");\n";
            try{
                stmt = conn.createStatement();
                stmt.executeUpdate(query);
                stmt.close();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, e.getMessage());
                return false;
            }
        }
                
        JOptionPane.showMessageDialog(null, "Palabras de la categoria Guardadas");
        return true;
    } 
}
