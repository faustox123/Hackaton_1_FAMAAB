/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapa;

import Graph.AdjacencyMapGraph;
import Graph.Graph;
import Graph.Vertex;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

/**
 *
 * @author Abel
 */
public class RoadPoint {
          
    int x,y, diametro=8;     
    int piso;
    boolean DestinationPoint = false;
    String etiqueta;
    Color color = Color.ORANGE;
    
    public boolean isDestinationPoint(){
        return DestinationPoint;
    }
    
    public RoadPoint(String Etiqueta, int CoordenadaX, int CoordenadaY, int piso){
        this.x = CoordenadaX;
        this.y = CoordenadaY;        
        this.etiqueta = Etiqueta;
        this.piso = piso;
    }
    
    public RoadPoint(String Etiqueta, int CoordenadaX, int CoordenadaY, int piso, boolean DestinationPoint){
        this.x = CoordenadaX;
        this.y = CoordenadaY;        
        this.etiqueta = Etiqueta;
        this.piso = piso;
        this.DestinationPoint = DestinationPoint;
    }
    
    public RoadPoint(int CoordenadaX, int CoordenadaY, String Etiqueta, Color colorPoint){
        this.x = CoordenadaX;
        this.y = CoordenadaY;        
        this.etiqueta = Etiqueta;
        this.color = colorPoint;
    }   
    
    public void dibujarCirculo(Graphics g,boolean MostrarEtiqueta){
            int d2 = diametro/2;
            g.setColor(color);
            g.fillOval(x-(d2),y-(d2),diametro,diametro);
            g.setColor(Color.black);
            g.drawOval(x-(d2),y-(d2),diametro,diametro);
            if(MostrarEtiqueta){
               ((Graphics2D)g).drawString(etiqueta,x,y);
               ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);            
            }
    }   

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDiametro() {
        return diametro;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public Color getColor() {
        return color;
    }
    
    

}