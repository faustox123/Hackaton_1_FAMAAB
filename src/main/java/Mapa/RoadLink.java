/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapa;

import Graph.Edge;
import Graph.Graph;
import Graph.Vertex;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

/**
 *
 * @author Abel
 */
public class RoadLink {  
    
    public int peso;
    private int x1,y1,x2,y2;
    public int piso = 0;
    
    private Color color = Color.black;
    private float grosorLinea = 1f;
    
    RoadLink(Vertex<RoadPoint> v1, Vertex<RoadPoint> v2){
        //Obtener valor del peso de la arista .         
        this.x1 = v1.getElement().x;
        this.x2 = v2.getElement().x;
        this.y1 = v1.getElement().y;
        this.y2 = v2.getElement().y;
        int distancia;        
        distancia =  (int) Math.sqrt( Math.pow(x1 - x2, 2) + Math.pow(y1-y2, 2));
        distancia *= 0.89403973509933774834437086092715; 
        this.peso =  distancia;
    }
    
    RoadLink(Vertex<RoadPoint> v1, Vertex<RoadPoint> v2, int piso){
        //Obtener valor del peso de la arista .         
        this.x1 = v1.getElement().x;
        this.x2 = v2.getElement().x;
        this.y1 = v1.getElement().y;
        this.y2 = v2.getElement().y;
        int distancia;        
        distancia =  (int) Math.sqrt( Math.pow(x1 - x2, 2) + Math.pow(y1-y2, 2));
        distancia *= 0.89403973509933774834437086092715; 
        this.peso =  distancia;
        this.piso = piso;
    }
    
     //Dibuja aristas por default
    public void DibujarArista(Graphics g, boolean MostrarPesosGrafo){
        ((Graphics2D)g).setColor(color);
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
        BasicStroke stroke = new BasicStroke(grosorLinea);
        ((Graphics2D)g).setStroke(stroke);
        ((Graphics2D)g).drawLine(x1,y1,x2,y2);
        
        
        if(MostrarPesosGrafo){
                ((Graphics2D)g).setColor(Color.blue);
                Font fuente=new Font("Monospaced",Font.BOLD, 12);
                g.setFont(fuente);
  
                    int xMenor = Math.min(x1, x2);
                    int yMenor = Math.min(y1, y2);

                    int xMayor = Math.max(x1, x2);  
                    int yMayor = Math.max(y1, y2);                   
                    
                    int distanciaVertical = yMayor - yMenor;
                    int distanciaHorizontal = xMayor - xMenor;
                    ((Graphics2D)g).drawString(peso+"",(distanciaHorizontal/2) + xMenor,(distanciaVertical/2) + yMenor);                                         
        } 
        
        
        stroke = new BasicStroke(1);
        ((Graphics2D)g).setStroke(stroke);  
    }
    
    //Dibuja las aristas que queremos resaltar
    public void DibujarArista(Graphics g, Color HighLightColor, float v){
        ((Graphics2D)g).setColor(HighLightColor);
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
        BasicStroke stroke = new BasicStroke(v);
        ((Graphics2D)g).setStroke(stroke);
        ((Graphics2D)g).drawLine(x1,y1,x2,y2);
        
        /* Mostrar psos de arista        
        ((Graphics2D)g).setColor(Color.blue);
                Font fuente=new Font("Monospaced",Font.BOLD, 12);
                g.setFont(fuente);
  
                    int xMenor = Math.min(x1, x2);
                    int yMenor = Math.min(y1, y2);

                    int xMayor = Math.max(x1, x2);  
                    int yMayor = Math.max(y1, y2);                   
                    
                    int distanciaVertical = yMayor - yMenor;
                    int distanciaHorizontal = xMayor - xMenor;
                    ((Graphics2D)g).drawString(peso+"",(distanciaHorizontal/2) + xMenor,(distanciaVertical/2) + yMenor);  */
        
        stroke = new BasicStroke(1);
        ((Graphics2D)g).setStroke(stroke);  
    }
    
}
