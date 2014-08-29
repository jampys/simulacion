/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

import java.util.ArrayList;

/**
 *
 * @author Titan
 */
public class Conmutador {
    private static int cantTelefonos;
    private int desde; //el 1er nro de telefono, el resto es el incremento
    
    protected static ArrayList<Telefono> telefonos;
    
    
   public Conmutador(int cant, int desde){ //constructor
       this.cantTelefonos=cant;
       this.desde=desde;
       
       telefonos=new ArrayList<Telefono>();
       for(int i=0; i<cantTelefonos; i++){
           Telefono tel=new Telefono(i+desde);
           telefonos.add(tel);
       }    
   }
   
   public void getTelefonos(){
       System.out.println("LISTADO DE TELEFONOS CON LOS QUE OPERA EL CONMUTADOR");
       for(int i=0; i<cantTelefonos; i++){
           System.out.println("Nro telefono: "+telefonos.get(i).getNroTelefono()+" Estado: "+telefonos.get(i).getEstadoTelefono());    
       }
   }
   
   public int getOrigen(){ //selecciona el origen de la llamada
       int u;
       do{
           float rnd=(float)Math.random();
           u=(int)(0+((0+cantTelefonos-1)-0)*rnd);
           //System.out.println("El u es "+u);
       }
       while(telefonos.get(0).estadoTelefono==1); //hacer mientras el telefono este ocupado
       
       //return u;
       return telefonos.get(u).nroTelefono;
   }
   
   
   //*************************INNERCLASS TELEFONO************************************
   
   class Telefono{
       
       private int nroTelefono;
       private int estadoTelefono;  //0 libre. 1 ocupado
       
       public Telefono(int nro){ //constructor
           this.nroTelefono=nro;
       }
       
       //Setters y getters
       
       public void setNroTelefono(int nro){
           this.nroTelefono=nro;
       }
       
       public int getNroTelefono(){
           return this.nroTelefono;
       }
       
       public void setEstadoTelefono(int estado){
           this.estadoTelefono=estado;
       }
       
       public int getEstadoTelefono(){
           return this.estadoTelefono;
       }
   
   } //clase Telefono
   
      
        public static void main(String []args){
            Conmutador a=new Conmutador(100, 1000);
            a.getTelefonos();
            System.out.println("El telefono de origen es "+a.getOrigen());
        }
}





