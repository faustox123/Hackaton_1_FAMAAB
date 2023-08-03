package Mapa;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */


import Graph.AdjacencyMapGraph;
import Graph.Edge;
import Graph.Vertex;
import Mapa.MapGraph;
import Mapa.RoadLink;
import Mapa.RoadPoint;
import itp_library.MapaPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;



/**
 *
 * @author Abel
 */
public class Mapa extends JPanel implements MouseMotionListener, MouseListener{
    //Asegurarse de tener el panelMapa de tamaño   width = 880  height = 600
 
    public enum Piso {
    PRIMER_PISO,
    SEGUNDO_PISO,
    TERCER_PISO
    };
    Piso pisoActual = Piso.PRIMER_PISO;

    public void setPisoActual(int NroPiso) {
        this.pisoActual = Piso.values()[NroPiso];
    }
    
    
    
    public Image imagen; 
    int  x=430, y=289, diametro=10;
    String texto="";    
    
    boolean MostrarEnumeracionGrafo = false;
    boolean ShowTotalPath = false;
    boolean MostrarPesosGrafo = false;
    
    boolean MostrarRutaMinima = true; int n1,n2,dist=0;
    boolean CrearNodo = false;
    
    boolean MostrarClientesConPedido = false;
        
    ArrayList <Edge<RoadLink>> Aristas = MapGraph.getRoadLinks();           
    ArrayList<Vertex<RoadPoint>> Nodos  = MapGraph.getRoadPoints();

    
    public void setImagen(String path) {
        this.imagen = new ImageIcon(getClass().getResource(path)).getImage();
        this.repaint();
    }
    

    
    public Mapa(String pathIcon, Piso piso){
        this.imagen = new ImageIcon(getClass().getResource(pathIcon)).getImage();
        this.pisoActual = piso;
 
        initComponents();
        setBounds(100, 100, 800, 600);
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    public int getCX() {
        return x;
    }

    public int getCY() {
        return y;
    }
    

    //Dibuja los nodos
    //La variable boolean MostrarEnumeracionGrafo decide si se muestra enum o no
    public void DibujarNodos(Graphics g){
        //System.out.println("Nro nodos : "+ Nodos.size());
        switch(pisoActual){
            case PRIMER_PISO:{
                for(Vertex<RoadPoint> pp: Nodos){
                    RoadPoint rp = pp.getElement();
                    if(rp.piso==1 &&(rp instanceof OfficePoint) ){
                        rp.dibujarCirculo(g, MostrarEnumeracionGrafo); 
                    }
                }                
                break;
            }
            case SEGUNDO_PISO:{
                for(Vertex<RoadPoint> pp: Nodos){
                     RoadPoint rp = pp.getElement();
                    if(rp.piso==2 && (rp instanceof OfficePoint)){
                        rp.dibujarCirculo(g, MostrarEnumeracionGrafo); 
                    }
                }              
                break;
            }
            case TERCER_PISO:{
                for(Vertex<RoadPoint> pp: Nodos){
                    RoadPoint rp = pp.getElement();                    
                    if(rp.piso==3 && (rp instanceof OfficePoint)){
                        rp.dibujarCirculo(g, MostrarEnumeracionGrafo); 
                    }
                } 
                break;
            }
        }
    }
    //Dibuja las aristas iniciales del mapa / grafo
    public void DibujarAristas(Graphics g){   
         switch(pisoActual){
            case PRIMER_PISO:{
                //System.out.println("Tam : " + Aristas.size());
                for(Edge<RoadLink> pp: Aristas){
                    if(pp.getElement().piso==1){
                        pp.getElement().DibujarArista(g,MostrarPesosGrafo);
                    }
                }
                break;
            }
            case SEGUNDO_PISO:{
                for(Edge<RoadLink> pp:Aristas){
                    if(pp.getElement().piso==2){
                        pp.getElement().DibujarArista(g,MostrarPesosGrafo);
                    }
                }             
                break;
            }
            case TERCER_PISO:{
                for(Edge<RoadLink> pp: Aristas){
                    if(pp.getElement().piso==3){
                        pp.getElement().DibujarArista(g,MostrarPesosGrafo);
                    }
                }             
                break;
            }
        }          
    }

    //Dibuja el camino minimo que une dos nodos del grafo******************************
    public void PaintPath(Graphics g){        
         switch(pisoActual){
            case PRIMER_PISO:{
                //System.out.println("Tam : " + Aristas.size());
                for(Edge<RoadLink> pp: MapaPanel.NodosShortestPath){
                    if(pp.getElement().piso==1){
                        pp.getElement().DibujarArista(g, Color.RED,4f);
                    }
                }
                break;
            }
            case SEGUNDO_PISO:{
                for(Edge<RoadLink> pp: MapaPanel.NodosShortestPath){
                    if(pp.getElement().piso==2){
                        pp.getElement().DibujarArista(g, Color.RED,4f);
                    }
                }             
                break;
            }
            case TERCER_PISO:{
                for(Edge<RoadLink> pp: MapaPanel.NodosShortestPath){
                    if(pp.getElement().piso==3){
                        pp.getElement().DibujarArista(g, Color.RED,4f);
                    }
                }             
                break;
            }
        }          
    }
    
    //Metodos para comunicarse con el Mapa y decidir lo que se dibuja
    public void ShowTotalPath(boolean ShowTotalPath) {
        this.ShowTotalPath = ShowTotalPath;
    }   
    public void setMostrarEnumeracionGrafo(boolean MostrarEnumeracionGrafo) {
        this.MostrarEnumeracionGrafo = MostrarEnumeracionGrafo;
    }
    

    
    public void VerRuta(boolean VerRuta){
        this.MostrarRutaMinima = VerRuta;    
    }
    
    public int getDist(){
        return this.dist;
    }
    public void setDist(int NuevaDist){
        this.dist = NuevaDist;
    }
    
    @Override
    public void paint(Graphics g){                            
            g.drawImage(imagen,0,0,getWidth(),getHeight(),this); 
            setOpaque(false);
            super.paint(g);
           
                //Dibujar puntero de mouse en mapa  
                g.setColor(Color.YELLOW);
               ((Graphics2D)g).setStroke(new BasicStroke(2));
               g.fillOval(x-5,y-5,diametro,diametro);
               g.setColor(Color.GREEN);
               g.drawOval(x-5,y-5,diametro,diametro);
               g.setColor(Color.BLACK);
               
               /*//MUESTRA COORDENADAS
               Font fuente = new Font("Courier New",Font.BOLD,30);
               g.setFont(fuente);               
               g.drawString(texto, 10, 590);*/

            // Dibujar línea punteada horizontal
               Graphics2D g2d = (Graphics2D) g.create();
               Stroke dashedStroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{2}, 0);
               g2d.setStroke(dashedStroke);
               g2d.drawLine(0,y , this.getWidth(),y );
               g2d.dispose(); 
            // Dibujar línea punteada vertical
                g2d = (Graphics2D) g.create();
                g2d.setStroke(dashedStroke);
                g2d.drawLine(x, 0, x, this.getHeight());
                g2d.dispose();  
    }


    
    //************------------------------*************************
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(ShowTotalPath)
            DibujarAristas(g);
        if(MapaPanel.NodosShortestPath!=null){
            PaintPath(g); 
        }
        DibujarNodos(g); 
    }
    


//*********************  CREACION  ******************************************
        /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1249, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 770, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseClicked

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(!CrearNodo){ 
            x = (int)e.getPoint().getX();
            y = (int)e.getPoint().getY();
            texto = "X: "+x+"  Y: "+y;
            repaint();            
        }
        //System.out.println("x : "+x);
        //System.out.println("y : "+y);
    }      

    @Override
    public void mouseClicked(MouseEvent e) {
        //repaint();
        //CrearNodo = !CrearNodo;
        
        System.out.println(" ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( ,"+x+","+y+")));");
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
