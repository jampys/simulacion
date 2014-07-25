/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colaSim;

import colaSimple.*;
import java.awt.*;
import java.util.Vector;
import java.util.Enumeration;

/**
 *
 * @author dario
 */
public class DrawWindow extends Panel {

    private Vector v;

    public DrawWindow(Vector v) {
        super(new FlowLayout());
        this.v=v;
    }


    public void paint(Graphics g) {

        Elemento dib;
        Enumeration e;
        e = v.elements();

        while(e.hasMoreElements()){
            dib=(Elemento)e.nextElement();
            dib.Dibujar(g);
       }

   }

}


