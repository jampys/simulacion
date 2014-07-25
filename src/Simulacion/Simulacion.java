/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulacion;

/**
 *
 * @author dario
 */
public class Simulacion {

    /**
     * @param args the command line arguments
     */
    private float TE; //tiempos entre llegada
    private float TS; //tiempos de servicio
    
    private float TM; //tiempo de reloj de la simulacion (tiempo actual)
    private float AT; // tiempo de siguiente llegada
    private float DT; //tiempo de siguiente salida
    private int SS; // estado del servidor (1=ocupado, 0=desocupado)
    private int WL; // longitud de la cola de espera
    private int MX; //tiempo maximo de simulacion (fin de la simulacion)
    
    
    public Simulacion(int MX){
        this.AT=0;
        this.DT=9999;
        this.SS=0;
        this.WL=0;
        this.MX=MX;  
    }
    
    public float generarTS(){
        //GENERAR LOS TIEMPOS DE SERVICIO
        float ts;
        float rnd=(float)Math.random();
        //System.out.println(rnd);
        
        if(rnd<=0.35){ts=1;}
        else if(rnd<=0.75){ts=2;}
        else {ts=3;}
        
        return ts;
    }
    
    public float generarTE(){
        //GENERAR LOS TIEMPOS DE SERVICIO
        float ts;
        float rnd=(float)Math.random();
        //System.out.println(rnd);
        
        if(rnd<=0.20){ts=1;}
        else if(rnd<=0.50){ts=2;}
        else if(rnd<=0.85){ts=3;}
        else {ts=4;}
        
        return ts;
    }
    
    
    public void simular(){
        do{
            if(AT<DT){
                //PROCESAR UNA LLEGADA
                TM=AT;
                if(SS==0){
                    //SERVIDOR DESOCUPADO
                    SS=1;
                    DT=TM+this.generarTS();
                }
                else{
                    //SERVIDOR OCUPADO
                    WL=WL+1;
                }
                AT=TM+this.generarTE();
              
            }
            else{
                //PROCESAR UNA SALIDA
                TM=DT;
                if(WL>0){
                    //WL>0
                    WL=WL-1;
                    DT=TM+this.generarTS();
                }
                else{
                    //WL=0
                    SS=0;
                    DT=9999;
                }
            
            }
            
        }
        while(TM<=MX);
    
    }
    
    public void imprimirResultados(){
    System.out.println(MX);
    System.out.println(TM);
    }
    
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        Simulacion sim=new Simulacion(100);
        sim.simular();
        sim.imprimirResultados();
    }
}
