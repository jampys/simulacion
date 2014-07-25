/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

/**
 *
 * @author dario
 */
public class Evento {
    float tiempo;
    int tipoEvento; //1 arribo, 2 salida
    int idxCliente; //cliente involucrado en el evento
    
    public Evento(float tiempo, int tipoEvento, int idxCliente){
        this.tiempo=tiempo;
        this.tipoEvento=tipoEvento;
        this.idxCliente=idxCliente;
    }
        
}


