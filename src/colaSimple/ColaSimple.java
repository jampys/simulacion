/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colaSimple;

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


    while (tiempo < 100) {

      tiempo = cola.anadirElememto(tiempo);
      System.out.println("Tiempo:" + tiempo+ " Elementos en cola: " + cola.size());

      
          while ((horaLibre < tiempo) && (cola.tieneElementos())) {

            Elemento procesado = cola.procesarElemento(colaProcesada);
            procesado.inicioProceso =  Math.max(horaLibre, procesado.creado);
            horaLibre = procesado.inicioProceso + procesado.tiempoProceso;
            System.out.println("Tiempo:" + tiempo+ " Items: " + cola.size()
              + " Hora entrada: " + procesado.creado+ " Tiempo proceso: " + procesado.tiempoProceso);

          }

    }

	  ventana.show();

  }

}


