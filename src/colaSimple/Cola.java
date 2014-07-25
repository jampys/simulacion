/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colaSimple;

import java.util.*;
//import Elemento;

/**
 *
 * @author dario
 */
public class Cola extends Vector {

    
  public int anadirElememto(int tiempo){
    Elemento elem;
    elem = new Elemento(tiempo);
    this.addElement(elem);
    return elem.creado;
  }


  public boolean tieneElementos(){
    Enumeration enumeracion = this.elements();
    return enumeracion.hasMoreElements();
  }

  
  public Elemento procesarElemento(Vector colaProcesados){
    Elemento elem =(Elemento)this.elementAt(0);
    elem.tiempoProceso = (int)(Math.random() * 10);
    colaProcesados.addElement(elem);
    this.removeElementAt(0);
    return elem;
  }


}


