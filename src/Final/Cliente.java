
package Final;


public class Cliente {
    public int nroCliente;
    public float tiempoArribo;
    public float tiempoSalida;
    
    public float incioAtencion; 
    //public float tiempoServicio; 
    public float clientesEnCola;
    public String estado; //exitosa, sin enlace, ocupada
    //Agregados
    public int origen;
    public int destino;
    
    
    public Cliente(int nroCliente, float tiempoArribo){ //constructor 1
        this.nroCliente=nroCliente;
        this.tiempoArribo=tiempoArribo;
        //this.estado=estado;
    }
    
    public Cliente(int nroCliente, float tiempoArribo, int origen, int destino){ //constructor 2
        this.nroCliente=nroCliente;
        this.tiempoArribo=tiempoArribo;
        this.origen=origen;
        this.destino=destino;
        
    }
   
    
}
