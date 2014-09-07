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
   
   
   public int getOrigen(){ //selecciona el origen de la llamada
       int u;
       do{
           //System.out.println("Entra en bucle infinito al intentar obtener ORIGEN");
           float rnd=(float)Math.random();
           //u=(int)(0+((0+cantTelefonos-1)-0)*rnd);
           u=(int)(0+((0+cantTelefonos)-0)*rnd);
       }
       while(telefonos.get(u).estadoTelefono==1); //hacer mientras el telefono este ocupado

       //System.out.println("El u es "+u);
       //telefonos.get(u).setEstadoTelefono(1); //cambia el estado del telefono a ocupado
       return telefonos.get(u).nroTelefono; //devuelve un telefono no ocupado
   }
   
   public int[] getDestino(int origen){ //selecciona el destino de la llamada
       int u;
       do{
           //System.out.println("Entra en bucle infinito al intentar obtener DESTINO");
           float rnd=(float)Math.random();
           //u=(int)(0+((0+cantTelefonos-1)-0)*rnd);
           u=(int)(0+((0+cantTelefonos)-0)*rnd);
           //System.out.println("El u es "+u);
       }
       while(telefonos.get(u).nroTelefono==origen); //hacer mientras el telefono sea igual al origen
      
       int[] v=new int[2];
       if(telefonos.get(u).getEstadoTelefono()==0){ //si el telefono esta libre
           v[0]=0;
           v[1]=telefonos.get(u).nroTelefono;
           //telefonos.get(u).setEstadoTelefono(1); //cambia el estado del telefono a ocupado
       }
       else{ //si el telefono esta ocupado
           v[0]=1;
           v[1]=telefonos.get(u).nroTelefono;
       }
       
       
       return v; //devuelve un array de 2 elementos, el 1ro (0=destino libre, 1=destino ocupado) 2do (numero de telefono)
   }
   
   
      public void getTelefonos(){
       System.out.println("LISTADO DE TELEFONOS CON LOS QUE OPERA EL CONMUTADOR");
       for(int i=0; i<cantTelefonos; i++){
           System.out.println("Nro telefono: "+telefonos.get(i).getNroTelefono()+" Estado: "+telefonos.get(i).getEstadoTelefono());    
       }
   }
      
   
      public void freeTelefono(int nroTelefono){ //metodo para settear estado del telefno en libre, cuando termina una llamada
          telefonos.get(nroTelefono-desde).setEstadoTelefono(0);
      }
      
      public void busyTelefono(int nroTelefono){ //metodo para settear estado del telefno en ocupado, cuando termina una llamada
          telefonos.get(nroTelefono-desde).setEstadoTelefono(1);
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
            Conmutador a=new Conmutador(6, 1000);
            
            int origen=a.getOrigen();
            int v[];
            v=a.getDestino(origen);
            int destino=v[1];
            System.out.println("El telefono de origen es "+origen);
            System.out.println("El telefono de destino es "+destino);
            a.getTelefonos();
            
            //a.freeTelefono(origen);
            //a.freeTelefono(destino);
            //a.getTelefonos();
            
        }
}





