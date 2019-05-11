package vocabularioadmin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.sql.*;
import java.io.File;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;


public class BDmanager {
    Connection conn = null;
    
    public static Connection ConnectDB(){
       
        String directoryTarget = "";
    
         try{
            Runtime run = Runtime.getRuntime();
            String cmd = "pwd";
            Process pr = run.exec(cmd);
            pr.waitFor();
            BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            if((line=buf.readLine())!=null) {
                String[] splitString = line.split("/");
                directoryTarget += "/"+splitString[1]+"/"+splitString[2]+"/";
            }
        } catch (Exception e) {}
        
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:"+ 
                    directoryTarget + "juego_interactivo/conocimiento.db");
           // JOptionPane.showMessageDialog(null, "Database connected");
            return conn;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Database not connected");
            return null;
        }
    }
    
    
}

