/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulacion;

/**
 *
 * @author dario
 */
public class Cliente {
    public int nroCliente;
    public float tiempoArribo;
    public float tiempoSalida;
    //public float incioAtencion; //no la uso
    //public float tiempoServicio; //no la uso
    
    //AGREGADA PARA 2 SERVIDORES EN SERIE
    public float arriboServidor2;
    
    public Cliente(int nroCliente, float tiempoArribo){
        this.nroCliente=nroCliente;
        this.tiempoArribo=tiempoArribo;
    }
   
    
}
