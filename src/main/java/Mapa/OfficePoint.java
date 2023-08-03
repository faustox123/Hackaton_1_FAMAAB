/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapa;

/**
 *
 * @author Abel
 */
public class OfficePoint extends RoadPoint{

        private String nombre;
        private String encargado="-";
        private String telefono="-";
        private String description="-";
        
        public OfficePoint(String Etiqueta, int CoordenadaX, int CoordenadaY, int piso, String nombre){
            super(Etiqueta, CoordenadaX, CoordenadaY,  piso);
            this.nombre = nombre;
        }
        
        public void setEncargado(String encargado) {
            this.encargado = encargado;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public void setDescription(String description) {
            this.description = description;
        }   
        
        public Object[] toRow() {
        return new Object[]{nombre,piso,encargado,telefono}; // Devuelve un arreglo de objetos con las propiedades del estudiante
        }  

        public String getDescription() {
            return description;
        }     

    public String getNombre() {
        return nombre;
    }

    public String getEncargado() {
        return encargado;
    }

    public String getTelefono() {
        return telefono;
    }
        
}
