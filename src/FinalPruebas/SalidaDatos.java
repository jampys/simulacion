/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FinalPruebas;

import java.util.*;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 *
 * @author Titan
 */
public class SalidaDatos {
    static Scanner miscaner = new Scanner (System.in);
    static int cantidad;
    static double precio, total;
        
    public static void main(String[] args)throws FileNotFoundException{
        
        PrintStream DDescritor = new PrintStream("G:/DATOS-TITAN/Desktop/Repositorio Compartido PCDARIO/FINAL SIMULACION/resultado_ejecucion/resultado.txt");
        System.out.print("Ahora introduce la cantidad del producto? ");
        cantidad = miscaner.nextInt();
        System.out.print("Ahora introduce el precio del producto? ");
        precio = miscaner.nextDouble();
        System.out.print("Ahora los resultados seran utgenerados en un archivo .txt");
                
        total = cantidad * precio;
                
        DDescritor.println("Cantidad\tPrecio");
        DDescritor.print(cantidad);
        DDescritor.print("\t");
        DDescritor.println(precio);
        DDescritor.println("En total tienes: "+total); 
      
    }

    
}
