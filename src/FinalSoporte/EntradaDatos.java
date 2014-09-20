/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FinalSoporte;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author Titan
 */
public class EntradaDatos {
    
    
    public int getCantTelefonos(){
        Scanner sc=new Scanner(System.in);
        int cantTelefonos;
        boolean b;
        String cadenaAEvaluar;
        do{ //asegura que el valor ingresado este en el intervalo [5, 100]
            
            do{ //asegura que el valor ingresado sea un entero
                String expresionRegular="[0-9]+";
                System.out.print("Introduzca la cantidad de telefonos (5 - 100): ");
                cadenaAEvaluar =sc.next();
                b=Pattern.matches(expresionRegular,cadenaAEvaluar);
            }
            while(!b);
        
            cantTelefonos=Integer.parseInt(cadenaAEvaluar);
        }
        while(cantTelefonos<5 || cantTelefonos>100);
       
        return cantTelefonos;
    
    }
    
    public int getCantEnlaces(){
        Scanner sc=new Scanner(System.in);
        int cantEnlaces;
        boolean b;
        String cadenaAEvaluar;
        do{ //asegura que el valor ingresado este en el intervalo [1, 10]
            
            do{ //asegura que el valor ingresado sea un entero
                String expresionRegular="[0-9]+";
                System.out.print("Introduzca la cantidad de enlaces (1 - 10): ");
                cadenaAEvaluar =sc.next();
                b=Pattern.matches(expresionRegular,cadenaAEvaluar);
            }
            while(!b);
        
            cantEnlaces=Integer.parseInt(cadenaAEvaluar);
        }
        while(cantEnlaces<1 || cantEnlaces>10);
       
        return cantEnlaces;
    
    }
    
    public int getTiempoSimulacion(){
        Scanner sc=new Scanner(System.in);
        int tiempoSimulacion;
        boolean b;
        String cadenaAEvaluar;
        do{ //asegura que el valor ingresado este en el intervalo [1, 36000]
            
            do{ //asegura que el valor ingresado sea un entero
                String expresionRegular="[0-9]+";
                System.out.print("Introduzca el tiempo de simulacion en segundos (1 - 36000): ");
                cadenaAEvaluar =sc.next();
                b=Pattern.matches(expresionRegular,cadenaAEvaluar);
            }
            while(!b);
        
            tiempoSimulacion=Integer.parseInt(cadenaAEvaluar);
        }
        while(tiempoSimulacion<1 || tiempoSimulacion>36000);
       
        return tiempoSimulacion;
    
    }
    
    
    public static void main (String []args){
        
        EntradaDatos a=new EntradaDatos();
        System.out.println("Cantidad de Telefonos: "+a.getCantTelefonos());
        System.out.println("Cantidad de enlaces: "+a.getCantEnlaces());  
        System.out.println("Tiempo de simulacion: "+a.getTiempoSimulacion());  
    }
    
}
