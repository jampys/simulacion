
package Final;


public class Cliente {
    public int nroCliente;
    public float tiempoArribo;
    public float tiempoSalida;
    
    public float incioAtencion; 
    //public float tiempoServicio; 
    public float clientesEnCola;
    //public int estado; //1 dentro del sistema, 2 fuera del sistema
    
    
    public Cliente(int nroCliente, float tiempoArribo){
        this.nroCliente=nroCliente;
        this.tiempoArribo=tiempoArribo;
        //this.estado=estado;
    }
   
    
}
