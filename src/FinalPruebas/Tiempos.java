/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FinalPruebas;

/**
 *
 * @author Titan
 */
public class Tiempos {
    
    public float generarTS(){ //GENERAR LOS TIEMPOS DE SERVICIO
        //variable aleatoria con distribucion de probabilidad exponencial con media 3 minutos (180 seg)
        double ts;
        float rnd=(float)Math.random();
        ts=-180*Math.log(1-rnd);
        return (float)ts;
    }
    
    public float generarTE(){ //GENERAR LOS TIEMPOS ENTRE ARRIBOS
        //variable aleatoria con distribucion de probabilidad exponencial con media 10seg
        double ts;
        float rnd=(float)Math.random();
        ts=-10*Math.log(1-rnd);
        return (float)ts;
    }
    
    public static String convertirAMinutos(int seg){
//        recibe el tiempo en segundos y lo convierte en minutos y segundos.
//        es solo para darle formato a las salidas por pantalla. En caso de tener que operar con tiempos
//        conviente previamente pasarlos a segundos, operar y luego volver a convertirlos a minutos
        int horas=seg/3600;
        int minutos=(seg%3600)/60;
        int segundos=(seg%3600)%60;
        //Agregando formato
        String horasS="";
        String minutosS="";
        String segundosS="";
        if(horas<10) horasS="0"+Integer.toString(horas);
        else horasS=Integer.toString(horas);
        if(minutos<10) minutosS="0"+Integer.toString(minutos);
        else minutosS=Integer.toString(minutos);
        if(segundos<10) segundosS="0"+Integer.toString(segundos);
        else segundosS=Integer.toString(segundos);
        //fin agregando formato
        
        String tiempo=horasS+":"+minutosS+":"+segundosS;
        return tiempo;
    }
    
    
    public static void main(String[] args){
        
        Tiempos ti=new Tiempos();
        for(int i=0; i<30; i++){
            System.out.println(ti.generarTE());
        }
        
        System.out.println(ti.convertirAMinutos(1000));
    }
    
}
