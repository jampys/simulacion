/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colaSim;

import colaSimple.*;
import java.lang.Math;
import java.awt.*;
import java.util.*;
/**
 *
 * @author dario
 */
public class ColaSimple {

  private static int tiempo;
  private static int horaLibre;


  public static void main(String args[]) {

    System.out.println("Inicio de Simulación de Cola simple");
    Cola cola = new Cola();
    Vector colaProcesada = new Vector();
    Frame ventana = new Frame("Simulación de cola simple");
    DrawWindow mipanel = new DrawWindow(colaProcesada);

	  ventana.add(mipanel);
	  ventana.pack();
	  ventana.setSize(500,500);

    int nroCliente=1;
    while (tiempo < 100) {

      tiempo = cola.anadirElememto(tiempo);
      System.out.println("\nIngresa un nuevo cliente, Tiempo:" + tiempo+ " Elementos en cola: " + cola.size());

          while ((horaLibre < tiempo) && (cola.tieneElementos())) { //(horaLibre<tiempo) es igual a decir
            // el servidor esta libre
            Elemento procesado = cola.procesarElemento(colaProcesada);
            procesado.inicioProceso =  Math.max(horaLibre, procesado.creado);
            horaLibre = procesado.inicioProceso + procesado.tiempoProceso;
            //Agregado dario
            System.out.println("Nro cliente: "+nroCliente);
            System.out.println("Hora arribo: "+procesado.creado);
            System.out.println("Inicio atencion: "+procesado.inicioProceso);
            System.out.println("Duracion servicio: "+procesado.tiempoProceso);
            System.out.println("Servidor libre: "+horaLibre);
            nroCliente++;
            
            //System.out.println("Bucle wile --- Tiempo:" + tiempo+ " Items: " + cola.size()
            //  + " Hora entrada: " + procesado.creado+ " Tiempo proceso: " + procesado.tiempoProceso+" Hora libre: "+horaLibre);

          } 

    }
    
    

	  ventana.show();

  }

}


