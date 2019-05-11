/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vocabularioadmin;

/**
 *
 * @author linus
 */
public class Palabra {
    private int ID;
    private String nombre;
    private String imagen;
    private String descripcion;
    
     
    Palabra (int id_, String n_){
        this.ID = id_;
        this.nombre = n_;
    }

    Palabra (int id_, String n_, String i_, String d_){
        this.ID = id_;
        this.nombre = n_;
        this.imagen = i_;
        this.descripcion = d_;
    }
    
    public int getID() {
        return this.ID;
    }
    
    public String getNombre(){
        return this.nombre;
    }

    public String getImagen(){
        return this.imagen;
    }

    public String getDescripcion(){
        return this.descripcion;
    }    
    
}