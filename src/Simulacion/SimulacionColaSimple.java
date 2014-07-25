/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulacion;

import java.util.ArrayList;

/**
 *
 * @author dario
 */
public class SimulacionColaSimple {

    /**
     * @param args the command line arguments
     */
    private float TE; //tiempos entre llegada
    private float TS; //tiempos de servicio
    
    protected float TM; //tiempo de reloj de la simulacion (tiempo actual)
    protected float AT; // tiempo de siguiente llegada
    private float DT; //tiempo de siguiente salida
    private int SS; // estado del servidor (1=ocupado, 0=desocupado)
    private int WL; // longitud de la cola de espera
    protected int MX; //tiempo maximo de simulacion (fin de la simulacion)
    
    protected int NA; //numero de llegadas hasta el instante TM
    protected int ND; //numero de salidas hasta el instante TM
    private int n; //numero de clientes en el sistema, en el instante TM
    private float Tp; //tiempo de salida del ultimo cliente posterior a TM
    
    protected ArrayList<Cliente> clientes;
    
    
    public SimulacionColaSimple(int MX){
        this.AT=0;
        this.DT=9999;
        this.SS=0;
        this.WL=0;
        this.MX=MX;  
        
        clientes=new ArrayList();
    }
    
    public void generarT(float s){
        //calcula el proximo arribo a partir de un instante s (que tomara el valor de TM)
        double t=s;
        double rnd;
        double lambda=10;
        double fd; //funcion de intencidad lambda de t.
      do{
           rnd=Math.random();
           
           t=t-1/lambda* Math.log10(rnd);
           rnd=Math.random();
           //fd=20*((Math.pow(Math.E, -40*t)))*Math.pow(40*t, 2);
           fd=20*((Math.pow(Math.E, -4*t)))*Math.pow(40*t, 2);
           
           System.out.println(fd/lambda);
           
            
       
        if(rnd<=fd/lambda){
            System.out.println(t);
          
           break;
       }
       
     
       }
       while(true);
       
       
       
        
        
        
    
        
        //return t;
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
            if(AT<=DT && AT<=MX){ //PROCESAR UNA LLEGADA. CASO 1
                TM=AT;
                n=n+1;
                NA=NA+1;
                if(SS==0){ //SERVIDOR DESOCUPADO
                    SS=1;
                    DT=TM+this.generarTS();
                }
                else{ //SERVIDOR OCUPADO
                    WL=WL+1;
                }
                AT=TM+this.generarTE();
                
                //OJO PROBANDO. FUNCIONA OK
                Cliente cli=new Cliente(NA, TM);
                clientes.add(cli);
            }
            
            if(DT<AT && DT<=MX){ //PROCESAR UNA SALIDA. CASO 2
                TM=DT;
                n=n-1;
                ND=ND+1;
                if(WL>0){ //WL>0
                    WL=WL-1;
                    DT=TM+this.generarTS();
                }
                else{ //WL=0
                    SS=0;
                    DT=9999;
                }
                
                //OJO PROBANDO. FUNCIONA OK
                clientes.get(ND-1).tiempoSalida=TM;
            }
            
            if(AT>MX && DT>MX){ //CASO 3
                /*
                if(n>0){
                    System.out.println("SE EJECUTA CASO 3: n>0");
                    TM=DT;
                    n=n-1;
                    ND=ND+1;
                    
                    DT=TM+this.generarTS();
                }
                else{ //n=0
                    System.out.println("SE EJECUTA CASO 3: n=0");
                    Tp=Math.max(TM-MX, 0);
                    break;
                    
                } */
                if(n>0){
                    while(n!=0){
                        System.out.println("SE EJECUTA 1 ciclo: n>0");
                        TM=DT;
                        n=n-1;
                        ND=ND+1;
                        
                        //OJO PROBANDO. FUNCIONA OK
                        clientes.get(ND-1).tiempoSalida=TM;
                        
                        if(n>0){
                            DT=TM+this.generarTS();
                            
                        }
                    }  
                }
                System.out.println("SE EJECUTA CASO 3: n=0");
                Tp=Math.max(TM-MX, 0);
                break;  
            }
            
        }
        while(TM<=MX || n>0);
    
    }
    
    public void imprimirResultados(){
 
    System.out.println("MX (fin simulacion): "+MX);
    System.out.println("TM (tiempo actual) "+TM);
    System.out.println("AT (proximo arribo) "+AT);
    System.out.println("DT (proxima salida) "+DT);
    //System.out.println("Elementos en cola "+WL);
    
    System.out.println("\nNumero total de llegadas: "+NA);
    System.out.println("Numero total de salidas: "+ND);
    System.out.println("Numero de clientes en el sistema: "+n);
    System.out.println("Tiempo salida posterior a MX: "+Tp);
    
    System.out.println("\n******** LISTADO DE CLIENTES PROCESADOS EN EL SISTEMA ***********\n");
    for(int i=0; i<clientes.size(); i++){
        System.out.println("Nro cliente:"+clientes.get(i).nroCliente+
                           " | Tiempo arribo:"+clientes.get(i).tiempoArribo+
                           " | Tiempo salida:"+clientes.get(i).tiempoSalida);
    
    }
    
    }
    
    
    
    public static void main(String[] args) {
   
        SimulacionColaSimple sim=new SimulacionColaSimple(100);
        sim.simular();
        sim.imprimirResultados();
        
    }
}
