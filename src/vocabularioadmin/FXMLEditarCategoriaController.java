/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vocabularioadmin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author linus
 */
public class FXMLEditarCategoriaController implements Initializable {

    
    private String name;
    private int id;
    /**
     * Accepts the firstName, lastName and stores them to specific instance variables
     * 
     * @param name
     * @param id
     */
    public FXMLEditarCategoriaController(String name, int id) {
        this.name = name;
        this.id = id;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("SALIDA: " + name + " " + id);
    }    
    
}
