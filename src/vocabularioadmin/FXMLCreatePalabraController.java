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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javax.swing.JOptionPane;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;


/**
 * FXML Controller class
 *
 * @author linus
 */
public class FXMLCreatePalabraController implements Initializable {

    private Connection conn = null;
    private ResultSet rs = null;
    private Statement stmt = null;
    private PreparedStatement pst = null;
    
    private String resourceName = null;
    private String extencion = null;
    private String directoryTarget = null;
    
           
    @FXML private Button volverButtonCRUD;
    @FXML private TextField palabraNameField;
    @FXML private TextArea palabraDescriptionField;
    @FXML private Label palabraImagenField;
    @FXML private ListView listBoxMain;
    @FXML private ImageView imagenPreview;
    
    ArrayList <CheckBox> checkList = new ArrayList();
       
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
    
    
    @FXML protected void handleGuardarButtonAction(ActionEvent event) throws Exception {
        
        Stage stage;
        Parent root;
        
        ArrayList<Integer> nivelesSelection = new ArrayList();
        
        for(int i=0; i<checkList.size(); i++){
            if(checkList.get(i).isSelected()){
                nivelesSelection.add(1);
            }else{
                nivelesSelection.add(0);
            }
        }
        
        String resourceFinalName = palabraNameField.getText().toLowerCase().replace(' ', '_')+"."+extencion;
        
        if(guardarPalabraBD(palabraNameField.getText(), 
                palabraDescriptionField.getText(), 
                nivelesSelection, resourceFinalName))
        {
            
            Runtime run = Runtime.getRuntime();
            String cmd = "mv "+directoryTarget+"juego_interactivo/media/"+resourceName+" "+ directoryTarget+"juego_interactivo/media/"+resourceFinalName+" -f";
            Process pr = run.exec(cmd);
            pr.waitFor();
            String line;
            BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            if((line=buf.readLine())!=null){
                System.out.println("Shell: "+ line);
            }
            
            
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
    
    @FXML protected void handleSeleccionarImagenButtonAction(ActionEvent event) throws Exception {
        
        Stage stage;
        String fileName;
        
        directoryTarget = "";
        
        Runtime run = Runtime.getRuntime();
        String cmd = "pwd";
        Process pr = run.exec(cmd);
        pr.waitFor();
        BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line;
        if((line=buf.readLine())!=null){
            String[] splitString = line.split("/");
            directoryTarget += "/"+splitString[1]+"/"+splitString[2]+"/";
        } 
        
        stage = (Stage) volverButtonCRUD.getScene().getWindow();
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar multimedia");
         ArrayList <String> ext = new ArrayList();
         ext.add("*.mp4");
         ext.add("*.gif");
         ext.add("*.jpg");
         ext.add("*.png");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MEDIA",ext),
                new FileChooser.ExtensionFilter("MP4", "*.mp4"),
                new FileChooser.ExtensionFilter("GIF", "*.gif"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {

            fileName = openFile(file);
            palabraImagenField.setText(fileName);
           
            String[] splitString = fileName.split("\\.");
            String[] splitName = splitString[splitString.length-2].split("\\/");
            String only_name = splitName[splitName.length-1];            
            extencion = splitString[splitString.length-1];
                        
            if (extencion.equals("mp4")) {
                cmd = "ffmpeg -i "+fileName+" "+directoryTarget+"juego_interactivo/media/__" + only_name + ".gif" + " -y";
                pr = run.exec(cmd);
                pr.waitFor();
                buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                if((line=buf.readLine())!=null){
                    System.out.println("Shell: "+ line);
                }
                extencion = "gif";
            }else{
                cmd = "mv "+fileName+" "+directoryTarget+"juego_interactivo/media/__" + only_name + "." + extencion + " -f";
                pr = run.exec(cmd);
                pr.waitFor();
                buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                if((line=buf.readLine())!=null){
                    System.out.println("Shell: "+ line);
                }
            }
            
            String imageCopy = directoryTarget+"juego_interactivo/media/__" + only_name +"."+ extencion;
            System.out.println(imageCopy);
            
            Image i = new Image(new File(imageCopy).toURI().toString());
            this.imagenPreview.setImage(i);
                        
            imagenPreview.setFitWidth(200);
            imagenPreview.setFitHeight(200);
            imagenPreview.setPreserveRatio(true);
            imagenPreview.setSmooth(true);
            imagenPreview.setCache(true);       
          
            resourceName = "__" + only_name +"."+ extencion; 
        } 
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.conn = BDmanager.ConnectDB();
        
        List<String> nombres = new ArrayList();

        final String[] names = new String[]{"Maternal", "Prescolar", 
            "1er Grado","2do Grado","3er Grado", "4to Grado", 
            "5to Grado", "6to Grado"};
        
        for (int i = 0; i < names.length; i++) {
            checkList.add(new CheckBox(names[i]));
            checkList.get(i).setSelected(true);
        }
       
        final ObservableList<CheckBox> listItems = FXCollections.observableArrayList(checkList);
        listBoxMain.setItems(listItems);
        listBoxMain.setMinHeight(100);
        listBoxMain.setEditable(false);
    }    
    
    
    public Boolean guardarPalabraBD(String nombre, String descripcion, 
                                    ArrayList<Integer> niveles, String imagen)
    {
           
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
            
            int lastID = getLastPalabraIDBD();
            
            for(int i=0; i<8; i++)
                if(niveles.get(i) == 1)
                    insertNivelPalabraBD(lastID, i+1);
            stmt.close();
                        
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
        
        JOptionPane.showMessageDialog(null, "Palabra Guardada");
        return true;
    }
    
    
    public Boolean insertNivelPalabraBD(int idPalabra, int idNivel){
    
        String query = "INSERT INTO nivel_palabra (id_palabra, id_nivel)"
                + "VALUES("+idPalabra+","+idNivel+");\n";
        
        try{
           
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
        
        return true;
    }
    
    
    public Boolean existPalabraBD(String nombre){
        
        String query = "SELECT DISTINCT palabra.* FROM palabra WHERE "
                + "palabra.nombre like '"+nombre+"'";
        
        try{
            pst = conn.prepareStatement(query);
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
     
    private static void configureFileChooser(final FileChooser fileChooser){                           
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
            new File(System.getProperty("user.home"))
        ); 
    }
    
    private String openFile(File file) { 
            return file.getAbsolutePath();
    }

}
