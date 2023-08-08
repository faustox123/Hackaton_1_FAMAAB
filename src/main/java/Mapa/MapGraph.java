/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapa;

import Graph.AdaptablePriorityQueue;
import Graph.AdjacencyMapGraph;
import Graph.Edge;
import Graph.Entry;
import Graph.Graph;
import Graph.HeapAdaptablePriorityQueue;
import Graph.Map;
import Graph.ProbeHashMap;
import Graph.Vertex;
import java.util.ArrayList;

/**
 *
 * @author Abel
 */
public class MapGraph {
    
    public static ArrayList<Edge<RoadLink>> ListaRoadLinks = null;  
    private static ArrayList<Vertex<RoadPoint>> ListaRoadPoints = null;
    
    public static AdjacencyMapGraph<RoadPoint, RoadLink> grafo = new AdjacencyMapGraph<>(false); 
      


    public static ArrayList<Edge<RoadLink>> getRoadLinks(){
	if(ListaRoadLinks == null) {
            ListaRoadLinks = new ArrayList<Edge<RoadLink>>();
            IniciarAristas();
	}
	return ListaRoadLinks;
    }   

    public static ArrayList<Vertex<RoadPoint>> getRoadPoints(){
	if(ListaRoadPoints == null) {
            ListaRoadPoints = new ArrayList<Vertex<RoadPoint>>();
            IniciarNodos();
	}
	return ListaRoadPoints;
    }
    
    public static Vertex<RoadPoint> getVertex(String etiqueta){
        for(Vertex<RoadPoint> p : ListaRoadPoints){
            if(etiqueta.equals(p.getElement().etiqueta ))
                return p;
        }
        return null;
    } 

//Retorna array con los Edges que forman parte de la ruta minima
 public static  <V> ArrayList<Edge<RoadLink>> PaintShortestPath(Graph<V,RoadLink> g, Vertex<V>  src,Vertex<V>  dst){
     Map<Vertex<V>, Integer> shortestPathsDistances = shortestPathLengths(g,src); //hash con distancias minismas de src a los demas nodos
     Map<Vertex<V>, Edge<RoadLink>> PathsTree = spTree(g, src,shortestPathsDistances); //ARbol de distancias minimas con raiz src
     ArrayList <Edge<RoadLink>> Ruta = new ArrayList();
     //Nodo inicial es dst, se empieza de la hoja a la raiz
     Edge<RoadLink> paso = PathsTree.get(dst); //Obtiene la arista asociada en el arbol de dist Min           
     Vertex nv = dst; //Empezar de la hoja   
     while(!nv.equals(src)){
        Ruta.add(paso);
        //System.out.println(paso.getElement().peso);    
        nv = g.opposite(nv, paso); //Obtiene nodo opuesto
        paso = PathsTree.get(nv);   //Arista de ruta minima asociada a opuesto
     }
     return Ruta;
 }
 
 public static <V> Map <Vertex<V>, Integer>shortestPathLengths(Graph<V,RoadLink> g, Vertex<V> src) {
        // d.get(v) is upper bound on distance from src to v
        Map<Vertex<V>, Integer> d = new ProbeHashMap<>( );
        // map reachable v to its d value
        Map<Vertex<V>, Integer> cloud = new ProbeHashMap<>( );
        // pq will have vertices as elements, with d.get(v) as key
        AdaptablePriorityQueue<Integer, Vertex<V>> pq;
        pq = new HeapAdaptablePriorityQueue<>( );
        // maps from vertex to its pq locator
        Map<Vertex<V>, Entry<Integer,Vertex<V>>> pqTokens;
        pqTokens = new ProbeHashMap<>( );
        // for each vertex v of the graph, add an entry to the priority queue, with
        // the source having distance 0 and all others having infinite distance
        for (Vertex<V> v : g.vertices( )) {
        if (v == src)
        d.put(v,0);
        else
        d.put(v, Integer.MAX_VALUE);
        pqTokens.put(v, pq.insert(d.get(v), v)); // save entry for future updates
        }        
        // now begin adding reachable vertices to the cloud
        while (!pq.isEmpty( )) {
        Entry<Integer, Vertex<V>> entry = pq.removeMin( );
        int key = entry.getKey( );
        
        Vertex<V> u = entry.getValue( );
        
        cloud.put(u, key); // this is actual distance to u
        pqTokens.remove(u); // u is no longer in pq
        
        for (Edge<RoadLink> e : g.outgoingEdges(u)) {
        Vertex<V> v = g.opposite(u,e);
        if (cloud.get(v) == null) {
        // perform relaxation step on edge (u,v)
        int wgt = e.getElement( ).peso;
        if (d.get(u) + wgt < d.get(v)) { // better path to v?
        d.put(v, d.get(u) + wgt); // update the distance
        pq.replaceKey(pqTokens.get(v), d.get(v)); // update the pq entry
        }
        }
        }
        }
        return cloud; // this only includes reachable vertices
 }
 //Devuelve la distancia entre dos nodos 
public static int DistanciaMin(int nodo1, int nodo2){
    return MapGraph.shortestPathLengths(MapGraph.grafo, ListaRoadPoints.get(nodo1)).get(ListaRoadPoints.get(nodo2));
}
public static int DistanciaMin(Vertex<RoadPoint> nodo1, Vertex<RoadPoint>  nodo2){
    return MapGraph.shortestPathLengths(MapGraph.grafo, nodo1).get(nodo2);
}

 public static <V> Map<Vertex<V>,Edge<RoadLink>> spTree(Graph<V,RoadLink> g, Vertex<V> s, Map<Vertex<V>,Integer> d) {
    Map<Vertex<V>, Edge<RoadLink>> tree = new ProbeHashMap<>( );
    for (Vertex<V> v : d.keySet( )){
        if (v != s)
            for (Edge<RoadLink> e : g.incomingEdges(v)) { // consider INCOMING edges
                Vertex<V> u = g.opposite(v, e);
                int wgt = e.getElement().peso;
                if (d.get(v) == d.get(u) + wgt)
                    tree.put(v, e); // edge is is used to reach v
            }
        //tree.put(v,null ); //Null al final de cada path
    }
    return tree;
 }
    
public static void IniciarNodos(){
    //Graph grafo = MaphGraph.grafo;
    //ArrayList<Vertex<RoadPoint>> ListaRoadPoints = MaphGraph.getRoadPoints();
    ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "0",153,580,1,"ENTRADA")));
    ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "1",153,534,1)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "2",130,540,1,"ECONOMIA")));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "3",98,515,1,"DGA")));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "4",176,560,1,"A.DOCENTE")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "5",200,534,1)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "6",200,529,1,"CERSEU")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "7",285,534,1)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "8",285,460,1)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "9",227,461,1,"DIRECCION ECC")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "10",322,464,1)));

 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "11",469,464,1)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "12",469,476,1,"AULAS COD100")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "13",566,464,1)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "14",566,452,1,"AULAS_2 COD100")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "15",721,464,1)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "16",721,550,1))); //Escalera
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "17",721,521,1)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "18",633,521,1,"ENTRADA 2")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "19",680,521,1)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "20",680,528,1,"QUIOSCO")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "21",285,405,1)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "22",227,405,1,"UNMSM")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "23",285,364,1)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "24",200,364,1))); //Escalera
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "25",285,326,1)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "26",227,326,1,"SSHH-N1-AP")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "27",285,249,1)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "28",260,249,1,"AUDITORIO")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "29",285,273,1)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "30",537,260,1,"LOSA DEPORTIVA")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "31",325,274,1)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "32",325,348,1)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "33",737,348,1,"VESTIDORES")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "34",638,348,1)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "35",638,367,1,"CAPILLA")));
  ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "36",285,56,1)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "37",285,23,1)));//Escalera
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "38",111,56,1)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "39",111,45,1,"AULAS-N1-NP1")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "40",404,56,1)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "41",404,65,1,"SSHH-N1-NP")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "42",472,56,1)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "43",688,56,1)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "44",688,26,1)));//Escalera
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "45",527,56,1)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "46",527,45,1,"AULAS-N1-NP2")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "47",727,348,1)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "48",727,386,1)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "49",838,386,1)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "50",838,408,1,"ENTRADA 3")));    
  ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "51",153,560,1)));   
 
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "52",240,534,1)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "53",240,568,1)));
  
  //  SEGUNDO PISO   ........................
 

 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "54",275,342,2,"SALA PROFESORES")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "55",170,517,2)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "56",170,527,2,"DECANATO")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "57",241,362,2)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "58",191,362,2))); //Escalera
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "59",241,336,2)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "60",226,336,2,"SSHH-N2-AP")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "61",241,277,2)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "62",281,277,2)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "63",351,277,2))); //Escalera
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "64",281,240,2)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "65",260,240,2,"COMEDOR")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "66",281,24,2))); //Escalera
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "67",146,55,2)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "68",146,45,2,"AULAS-N2-NP")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "69",400,55,2)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "70",400,65,2,"SSHH-N2-NP")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "71",590,55,2)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "72",590,65,2,"DATA CENTER")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "73",688,55,2)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "74",688,24,2))); //Escalera
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "75",515,45,2,"LABOTATORIOS-N2-NP")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "76",515,55,2)));
   ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "77",275,456,2)));
ListaRoadPoints.add(grafo.insertVertex(new OfficePoint("78" ,388,456,2,"TROFEOS")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "79",559,464,2)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "80",465,464,2)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "81",465,474,2,"AULAS COD200")));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "82",559,427,2,"AULAS2 COD200")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "83",641,464,2)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "84",641,455,2,"TERCIO EST.")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "85",707,464,2)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "86",707,455,2,"AULAS3 COD200")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "87",787,464,2)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "88",787,455,2,"AULA MAGNA")));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "89",787,472,2,"LABORAT-N2-AP")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "90",707,555,2))); //Escalera
  ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "91",275,362,2)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "92",281,55,2)));
  
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "93",275,560,2))); //Escalera
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "94",275,517,2)));
// 3er piusooooooooo

 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint("95" ,226,537,3,"UNAYOE")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "96",277,478,3)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "97",277,465,3)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "98",277,432,3)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "99",277,390,3)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "100",277,363,3)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "101",277,330,3)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "102",277,55,3)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "103",277,25,3))); //Escalera
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "104",196,55,3)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "105",196,44,3,"AULA MUSICA")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "106",393,50,3)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "107",393,64,3,"SSHH-N3-NP")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "108",575,55,3)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "109",575,64,3,"LABORAT-N3-NP")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "110",689,55,3)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "111",689,23,3))); //Escalera
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "112",401,465,3)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "113",401,455,3,"PUBLICIDAD")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "114",491,465,3)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "115",491,476,3,"LABORAT-N3-AP")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "116",540,465,3)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "117",540,455,3,"SOPORTE")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "118",659,465,3)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "119",659,455,3,"DEP. SISTEMAS")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "120",701,465,3)));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "121",701,553,3))); //Escalera
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "122",742,465,3)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "123",742,455,3,"LABORAT2-N3-AP")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "124",798,465,3)));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "125",798,455,3,"LABORAT3-N3-AP")));

 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "126",227,478,3,"DIR. SIST")));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "127",227,432,3,"DIR. SOFT")));
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "128",227,390,3,"MATRICULA")));
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "129",190,363,3))); //Escalera
 ListaRoadPoints.add(grafo.insertVertex(new OfficePoint( "130",227,330,3,"SSHH-N3-AP")));
  ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "131",277,566,3))); //Escalera
 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "132",277,526,3)));

 ListaRoadPoints.add(grafo.insertVertex(new RoadPoint( "133",537,348,1)));
}  



public static void IniciarAristas(){
                
        Graph grafo = MapGraph.grafo;
        ArrayList<Vertex<RoadPoint>> n = MapGraph.getRoadPoints();
        ArrayList<Edge<RoadLink>> ListaRoadLinks = MapGraph.getRoadLinks();

        ListaRoadLinks.add(grafo.insertEdge(n.get(51), n.get(1), new RoadLink(n.get(51), n.get(1),1)));
        ListaRoadLinks.add(grafo.insertEdge(n.get(0), n.get(51), new RoadLink(n.get(0), n.get(51),1)));
        ListaRoadLinks.add(grafo.insertEdge(n.get(4), n.get(51), new RoadLink(n.get(4), n.get(51),1)));
        ListaRoadLinks.add(grafo.insertEdge(n.get(1), n.get(2), new RoadLink(n.get(1), n.get(2),1)));
        ListaRoadLinks.add(grafo.insertEdge(n.get(1), n.get(3), new RoadLink(n.get(1), n.get(3),1)));
        ListaRoadLinks.add(grafo.insertEdge(n.get(1), n.get(5), new RoadLink(n.get(1), n.get(5),1)));
        ListaRoadLinks.add(grafo.insertEdge(n.get(6), n.get(5), new RoadLink(n.get(6), n.get(5),1)));
        
        ListaRoadLinks.add(grafo.insertEdge(n.get(52), n.get(5), new RoadLink(n.get(52), n.get(5),1)));
        ListaRoadLinks.add(grafo.insertEdge(n.get(52), n.get(53), new RoadLink(n.get(52), n.get(53),1)));
        ListaRoadLinks.add(grafo.insertEdge(n.get(52), n.get(7), new RoadLink(n.get(52), n.get(7),1)));
        
        ListaRoadLinks.add(grafo.insertEdge(n.get(7), n.get(8), new RoadLink(n.get(7), n.get(8),1)));
        ListaRoadLinks.add(grafo.insertEdge(n.get(9), n.get(8), new RoadLink(n.get(9), n.get(8),1)));
        ListaRoadLinks.add(grafo.insertEdge(n.get(10), n.get(8), new RoadLink(n.get(10), n.get(8),1)));
        
        ListaRoadLinks.add(grafo.insertEdge(n.get(10), n.get(11), new RoadLink(n.get(10), n.get(11),1)));
        ListaRoadLinks.add(grafo.insertEdge(n.get(12), n.get(11), new RoadLink(n.get(12), n.get(11),1)));
        ListaRoadLinks.add(grafo.insertEdge(n.get(13), n.get(11), new RoadLink(n.get(13), n.get(11),1)));
        ListaRoadLinks.add(grafo.insertEdge(n.get(13), n.get(14), new RoadLink(n.get(13), n.get(14),1)));
        ListaRoadLinks.add(grafo.insertEdge(n.get(13), n.get(15), new RoadLink(n.get(13), n.get(15),1)));
        ListaRoadLinks.add(grafo.insertEdge(n.get(17), n.get(15), new RoadLink(n.get(17), n.get(15),1)));
        ListaRoadLinks.add(grafo.insertEdge(n.get(17), n.get(19), new RoadLink(n.get(17), n.get(19),1)));
        ListaRoadLinks.add(grafo.insertEdge(n.get(17), n.get(16), new RoadLink(n.get(17), n.get(16),1)));
        ListaRoadLinks.add(grafo.insertEdge(n.get(20), n.get(19), new RoadLink(n.get(20), n.get(19),1)));
        ListaRoadLinks.add(grafo.insertEdge(n.get(18), n.get(19), new RoadLink(n.get(18), n.get(19),1)));
        
         ListaRoadLinks.add(grafo.insertEdge(n.get(8), n.get(21), new RoadLink(n.get(8), n.get(21),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(22), n.get(21), new RoadLink(n.get(22), n.get(21),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(23), n.get(21), new RoadLink(n.get(23), n.get(21),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(23), n.get(24), new RoadLink(n.get(23), n.get(24),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(23), n.get(25), new RoadLink(n.get(23), n.get(25),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(26), n.get(25), new RoadLink(n.get(26), n.get(25),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(29), n.get(25), new RoadLink(n.get(29), n.get(25),1)));
         
         ListaRoadLinks.add(grafo.insertEdge(n.get(29), n.get(31), new RoadLink(n.get(29), n.get(31),1)));
             ListaRoadLinks.add(grafo.insertEdge(n.get(32), n.get(31), new RoadLink(n.get(32), n.get(31),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(32), n.get(133), new RoadLink(n.get(32), n.get(133),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(34), n.get(133), new RoadLink(n.get(34), n.get(133),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(35), n.get(34), new RoadLink(n.get(35), n.get(34),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(47), n.get(34), new RoadLink(n.get(47), n.get(34),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(47), n.get(33), new RoadLink(n.get(47), n.get(33),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(47), n.get(48), new RoadLink(n.get(47), n.get(48),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(49), n.get(48), new RoadLink(n.get(49), n.get(48),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(49), n.get(50), new RoadLink(n.get(49), n.get(50),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(30), n.get(133), new RoadLink(n.get(30), n.get(133),1)));
         
         ListaRoadLinks.add(grafo.insertEdge(n.get(29), n.get(27), new RoadLink(n.get(29), n.get(27),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(28), n.get(27), new RoadLink(n.get(28), n.get(27),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(36), n.get(27), new RoadLink(n.get(36), n.get(27),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(36), n.get(38), new RoadLink(n.get(36), n.get(38),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(39), n.get(38), new RoadLink(n.get(39), n.get(38),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(36), n.get(37), new RoadLink(n.get(36), n.get(37),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(36), n.get(40), new RoadLink(n.get(36), n.get(40),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(41), n.get(40), new RoadLink(n.get(41), n.get(40),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(42), n.get(40), new RoadLink(n.get(42), n.get(40),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(42), n.get(45), new RoadLink(n.get(42), n.get(45),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(46), n.get(45), new RoadLink(n.get(46), n.get(45),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(43), n.get(45), new RoadLink(n.get(43), n.get(45),1)));
         ListaRoadLinks.add(grafo.insertEdge(n.get(43), n.get(44), new RoadLink(n.get(43), n.get(44),1)));
         
         
         //2DO PISO ----------------------------------
      ListaRoadLinks.add(grafo.insertEdge(n.get(93), n.get(94), new RoadLink(n.get(93), n.get(94),2)));
          ListaRoadLinks.add(grafo.insertEdge(n.get(55), n.get(94), new RoadLink(n.get(55), n.get(94),2)));
          ListaRoadLinks.add(grafo.insertEdge(n.get(77), n.get(94), new RoadLink(n.get(77), n.get(94),2)));
          ListaRoadLinks.add(grafo.insertEdge(n.get(55), n.get(56), new RoadLink(n.get(55), n.get(56),2)));
          ListaRoadLinks.add(grafo.insertEdge(n.get(77), n.get(78), new RoadLink(n.get(77), n.get(78),2)));
          ListaRoadLinks.add(grafo.insertEdge(n.get(80), n.get(78), new RoadLink(n.get(80), n.get(78),2)));
          ListaRoadLinks.add(grafo.insertEdge(n.get(80), n.get(81), new RoadLink(n.get(80), n.get(81),2)));
          ListaRoadLinks.add(grafo.insertEdge(n.get(80), n.get(79), new RoadLink(n.get(80), n.get(79),2)));
          ListaRoadLinks.add(grafo.insertEdge(n.get(82), n.get(79), new RoadLink(n.get(82), n.get(79),2)));
          ListaRoadLinks.add(grafo.insertEdge(n.get(83), n.get(79), new RoadLink(n.get(83), n.get(79),2)));
          ListaRoadLinks.add(grafo.insertEdge(n.get(83), n.get(84), new RoadLink(n.get(83), n.get(84),2)));
          ListaRoadLinks.add(grafo.insertEdge(n.get(83), n.get(85), new RoadLink(n.get(83), n.get(85),2)));
          ListaRoadLinks.add(grafo.insertEdge(n.get(86), n.get(85), new RoadLink(n.get(86), n.get(85),2)));
          ListaRoadLinks.add(grafo.insertEdge(n.get(90), n.get(85), new RoadLink(n.get(90), n.get(85),2)));
          ListaRoadLinks.add(grafo.insertEdge(n.get(87), n.get(85), new RoadLink(n.get(87), n.get(85),2)));
          ListaRoadLinks.add(grafo.insertEdge(n.get(87), n.get(89), new RoadLink(n.get(87), n.get(89),2)));
          ListaRoadLinks.add(grafo.insertEdge(n.get(87), n.get(88), new RoadLink(n.get(87), n.get(88),2)));
         
           ListaRoadLinks.add(grafo.insertEdge(n.get(77), n.get(91), new RoadLink(n.get(77), n.get(91),2)));
           ListaRoadLinks.add(grafo.insertEdge(n.get(54), n.get(91), new RoadLink(n.get(54), n.get(91),2)));
           ListaRoadLinks.add(grafo.insertEdge(n.get(57), n.get(91), new RoadLink(n.get(57), n.get(91),2)));
           ListaRoadLinks.add(grafo.insertEdge(n.get(57), n.get(58), new RoadLink(n.get(57), n.get(58),2)));
           ListaRoadLinks.add(grafo.insertEdge(n.get(57), n.get(59), new RoadLink(n.get(57), n.get(59),2)));
           ListaRoadLinks.add(grafo.insertEdge(n.get(60), n.get(59), new RoadLink(n.get(60), n.get(59),2)));
           ListaRoadLinks.add(grafo.insertEdge(n.get(61), n.get(59), new RoadLink(n.get(61), n.get(59),2)));
           ListaRoadLinks.add(grafo.insertEdge(n.get(61), n.get(62), new RoadLink(n.get(61), n.get(62),2)));
           ListaRoadLinks.add(grafo.insertEdge(n.get(63), n.get(62), new RoadLink(n.get(63), n.get(62),2)));
           ListaRoadLinks.add(grafo.insertEdge(n.get(64), n.get(62), new RoadLink(n.get(64), n.get(62),2)));
           ListaRoadLinks.add(grafo.insertEdge(n.get(64), n.get(65), new RoadLink(n.get(64), n.get(65),2)));
           ListaRoadLinks.add(grafo.insertEdge(n.get(64), n.get(92), new RoadLink(n.get(64), n.get(92),2)));
           ListaRoadLinks.add(grafo.insertEdge(n.get(66), n.get(92), new RoadLink(n.get(66), n.get(92),2)));
           ListaRoadLinks.add(grafo.insertEdge(n.get(67), n.get(92), new RoadLink(n.get(67), n.get(92),2)));
           ListaRoadLinks.add(grafo.insertEdge(n.get(67), n.get(68), new RoadLink(n.get(67), n.get(68),2)));
           ListaRoadLinks.add(grafo.insertEdge(n.get(69), n.get(92), new RoadLink(n.get(69), n.get(92),2)));
           ListaRoadLinks.add(grafo.insertEdge(n.get(69), n.get(70), new RoadLink(n.get(69), n.get(70),2)));
           ListaRoadLinks.add(grafo.insertEdge(n.get(69), n.get(76), new RoadLink(n.get(69), n.get(76),2)));
           ListaRoadLinks.add(grafo.insertEdge(n.get(75), n.get(76), new RoadLink(n.get(75), n.get(76),2)));
           ListaRoadLinks.add(grafo.insertEdge(n.get(71), n.get(76), new RoadLink(n.get(71), n.get(76),2)));
           ListaRoadLinks.add(grafo.insertEdge(n.get(71), n.get(72), new RoadLink(n.get(71), n.get(72),2)));
           ListaRoadLinks.add(grafo.insertEdge(n.get(71), n.get(73), new RoadLink(n.get(71), n.get(73),2)));
           ListaRoadLinks.add(grafo.insertEdge(n.get(74), n.get(73), new RoadLink(n.get(74), n.get(73),2)));
         
           // 3ER PISO ------------------------
            ListaRoadLinks.add(grafo.insertEdge(n.get(131), n.get(132), new RoadLink(n.get(131), n.get(132),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(95), n.get(132), new RoadLink(n.get(95), n.get(132),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(96), n.get(132), new RoadLink(n.get(96), n.get(132),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(96), n.get(97), new RoadLink(n.get(96), n.get(97),3)));
            
            ListaRoadLinks.add(grafo.insertEdge(n.get(112), n.get(97), new RoadLink(n.get(112), n.get(97),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(112), n.get(113), new RoadLink(n.get(112), n.get(113),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(112), n.get(114), new RoadLink(n.get(112), n.get(114),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(115), n.get(114), new RoadLink(n.get(115), n.get(114),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(116), n.get(114), new RoadLink(n.get(116), n.get(114),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(116), n.get(117), new RoadLink(n.get(116), n.get(117),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(116), n.get(118), new RoadLink(n.get(116), n.get(118),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(119), n.get(118), new RoadLink(n.get(119), n.get(118),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(120), n.get(118), new RoadLink(n.get(120), n.get(118),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(120), n.get(121), new RoadLink(n.get(120), n.get(121),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(120), n.get(122), new RoadLink(n.get(120), n.get(122),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(123), n.get(122), new RoadLink(n.get(123), n.get(122),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(124), n.get(122), new RoadLink(n.get(124), n.get(122),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(124), n.get(125), new RoadLink(n.get(124), n.get(125),3)));

           
            ListaRoadLinks.add(grafo.insertEdge(n.get(98), n.get(97), new RoadLink(n.get(98), n.get(97),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(98), n.get(99), new RoadLink(n.get(98), n.get(99),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(100), n.get(99), new RoadLink(n.get(100), n.get(99),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(100), n.get(101), new RoadLink(n.get(100), n.get(101),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(102), n.get(101), new RoadLink(n.get(102), n.get(101),3)));
            
            ListaRoadLinks.add(grafo.insertEdge(n.get(102), n.get(104), new RoadLink(n.get(102), n.get(104),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(105), n.get(104), new RoadLink(n.get(105), n.get(104),3)));
           
            ListaRoadLinks.add(grafo.insertEdge(n.get(102), n.get(106), new RoadLink(n.get(102), n.get(106),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(107), n.get(106), new RoadLink(n.get(107), n.get(106),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(108), n.get(106), new RoadLink(n.get(108), n.get(106),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(108), n.get(109), new RoadLink(n.get(108), n.get(109),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(108), n.get(110), new RoadLink(n.get(108), n.get(110),3)));
            ListaRoadLinks.add(grafo.insertEdge(n.get(111), n.get(110), new RoadLink(n.get(111), n.get(110),3)));
         
             ListaRoadLinks.add(grafo.insertEdge(n.get(96), n.get(126), new RoadLink(n.get(96), n.get(126),3)));
             ListaRoadLinks.add(grafo.insertEdge(n.get(98), n.get(127), new RoadLink(n.get(98), n.get(127),3)));
             ListaRoadLinks.add(grafo.insertEdge(n.get(99), n.get(128), new RoadLink(n.get(99), n.get(128),3)));
             ListaRoadLinks.add(grafo.insertEdge(n.get(100), n.get(129), new RoadLink(n.get(100), n.get(129),3)));
             ListaRoadLinks.add(grafo.insertEdge(n.get(101), n.get(130), new RoadLink(n.get(101), n.get(130),3)));
             ListaRoadLinks.add(grafo.insertEdge(n.get(102), n.get(103), new RoadLink(n.get(102), n.get(103),3)));
             
             //Escaleras
             ListaRoadLinks.add(grafo.insertEdge(n.get(37), n.get(66), new RoadLink(n.get(37), n.get(66))));
             ListaRoadLinks.add(grafo.insertEdge(n.get(103), n.get(66), new RoadLink(n.get(103), n.get(66))));

             ListaRoadLinks.add(grafo.insertEdge(n.get(44), n.get(74), new RoadLink(n.get(44), n.get(74))));
             ListaRoadLinks.add(grafo.insertEdge(n.get(111), n.get(74), new RoadLink(n.get(111), n.get(74))));
             
             ListaRoadLinks.add(grafo.insertEdge(n.get(24), n.get(58), new RoadLink(n.get(24), n.get(58))));
             ListaRoadLinks.add(grafo.insertEdge(n.get(129), n.get(58), new RoadLink(n.get(129), n.get(58))));
             
             ListaRoadLinks.add(grafo.insertEdge(n.get(53), n.get(93), new RoadLink(n.get(53), n.get(93))));
             ListaRoadLinks.add(grafo.insertEdge(n.get(131), n.get(93), new RoadLink(n.get(131), n.get(93))));
             
             ListaRoadLinks.add(grafo.insertEdge(n.get(16), n.get(90), new RoadLink(n.get(16), n.get(90))));
             ListaRoadLinks.add(grafo.insertEdge(n.get(121), n.get(90), new RoadLink(n.get(121), n.get(90))));
             ListaRoadLinks.add(grafo.insertEdge(n.get(63), n.get(31), new RoadLink(n.get(63), n.get(31))));
    }

    
    

}
