/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colaSimple;

import java.lang.Math;
import java.awt.*;
/**
 *
 * @author dario
 */
public class Elemento {

  public int creado;
  public int inicioProceso;
  public int tiempoProceso;
  
  
  public Elemento(int tiempo) {  //constructor
    creado = tiempo + (int)(Math.random() * 10);
  }

  public void Dibujar(Graphics g){ //un metodo de la clase
    g.setColor(Color.black);
    g.drawRect(creado*5,100,2,2);
    g.drawLine(creado*5,100,inicioProceso*5,150);
    g.drawRect(inicioProceso*5,150,tiempoProceso*5,2);
  }
}


