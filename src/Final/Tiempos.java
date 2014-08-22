/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

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
    
    public static String convertirAMinutos(float seg){
//        recibe el tiempo en segundos y lo convierte en minutos y segundos.
//        es solo para darle formato a las salidas por pantalla. En caso de tener que operar con tiempos
//        conviente previamente pasarlos a segundos, operar y luego volver a convertirlos a minutos
        int minutos=(int)seg/60;
        int segundos=(int)seg%60;
        String tiempo=Integer.toString(minutos)+":"+Integer.toString(segundos);
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
