
package Final;

import java.util.ArrayList;


public class SimulacionColaSimpleModificado {

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
    
    //AGREGADOS FINAL
    protected int cantEnlaces;
    protected int cantTelefonos; //telefono=linea
    //FIN
    
    public SimulacionColaSimpleModificado(int MX, int cantEnlaces, int cantTelefonos){
        this.AT=0;
        this.DT=9999;
        this.SS=0;
        this.WL=0;
        this.MX=MX; 
        //AGREGADOS FINAL
        this.cantEnlaces=cantEnlaces;
        this.cantTelefonos=cantTelefonos;
        //FIN
        clientes=new ArrayList();
    }
    
    
    public float generarTS(){ //GENERAR LOS TIEMPOS DE SERVICIO
        float ts;
        float rnd=(float)Math.random();
        
        if(rnd<=0.35){ts=4;}
        else if(rnd<=0.75){ts=5;}
        else {ts=6;}
        
        return ts;
    }
    
    public float generarTE(){ //GENERAR LOS TIEMPOS ENTRE ARRIBOS
        float ts;
        float rnd=(float)Math.random();
        
        if(rnd<=0.20){ts=1;}
        else if(rnd<=0.50){ts=2;}
        else if(rnd<=0.85){ts=3;}
        else {ts=4;}
        
        return ts;
    }
    
    
    public void simular(){
        do{
            //IMPORTANTE: Hacer a DT= minimo de todos los tiempos de salida de los clienes que estan en el sistema actualmente
            //Si entraron 10 y salieron 3, los clientes que quedan en el sistema son desde ND hasta NA-1
            //if(n>1){ //si hay 2 o mas elementos en el sistema
            /*for(int i=ND; i<NA-1; i++){
                DT=Math.min(clientes.get(i).tiempoSalida, clientes.get(i+1).tiempoSalida);
            }
            System.out.println("El DT es "+DT); */
            
            
            //****************PROCESAR UNA LLEGADA. CASO 1*********************************
            if(AT<=DT && AT<=MX){ 
                System.out.println("CASO 1");
                TM=AT;
                n=n+1;
                NA=NA+1;
                Cliente cli=new Cliente(NA, TM);
                clientes.add(cli);
                
                if(n<=cantEnlaces){ //Si hay algun enlace libre
                    //SS=1;
                    clientes.get(NA-1).tiempoSalida=TM+this.generarTS(); //calcula el tiempo de salida
                    clientes.get(NA-1).incioAtencion=TM;
                    this.setDT();
                    //System.out.println("CLIENTE CASO 1 "+clientes.get(NA-1).nroCliente);
                }
                else{ //no hay ningun enlace libre
                    WL=WL+1;
                }
                clientes.get(NA-1).clientesEnCola=WL; 
                AT=TM+this.generarTE(); //calcula la proxima llegada
                  
            }
            //****************************************************************************
            
            //****************PROCESAR UNA SALIDA. CASO 2*********************************
            if(DT<AT && DT<=MX){ 
                System.out.println("CASO 2");
                TM=DT;
                n=n-1;
                ND=ND+1;
                if(WL>0){ //WL>0. Si hay alguien en la cola
                    WL=WL-1;
                    clientes.get(ND-1).incioAtencion=TM;
                    clientes.get(ND-1).tiempoSalida=TM+this.generarTS(); //calcula el tiempo de salida
                    this.setDT();
                    //System.out.println("CLIENTE CASO 2 "+clientes.get(ND-1).nroCliente);
                    
                    
                }
                else{ //WL=0
                    //SS=0;
                    //DT=9999; 
                    this.setDT();
                    //System.out.println("TROLAZO "+getDT());
                    DT=this.getDT();
                }
                

            }
            //*******************************************************************************
            
            //*********************************CASO 3*********************************
            if(AT>MX && DT>MX && n>0){
                
                System.out.println("SE EJECUTA CASO 3");
                TM=DT;
                n=n-1;
                ND=ND+1;
                        
                //OJO PROBANDO. FUNCIONA OK
                //clientes.get(ND-1).tiempoSalida=TM;
                DT=TM+this.generarTS();
                                 
            }
            
            //***************************CASO 4********************************
            if(AT>MX && DT>MX && n==0){
                System.out.println("SE EJECUTA CASO 4");
                Tp=Math.max(TM-MX, 0);
                break;
            }
               
        }
        //while(TM<=MX || n>0);
        while(true);
    
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
                           " | Inicio Atencion:"+clientes.get(i).incioAtencion+
                           " | Tiempo salida:"+clientes.get(i).tiempoSalida+
                           " | En cola: "+clientes.get(i).clientesEnCola);
    
    }
    
    }
    
    public void setDT(){
        
        for(int i=ND; i<=NA-1; i++){
           if(clientes.get(i).tiempoSalida<this.DT){
               this.DT=clientes.get(i).tiempoSalida;
           }
                
        }
        System.out.println("EL DT ES "+this.DT);
    }
    
    public float getDT(){
        return DT;
    }
    
    
    
    public static void main(String[] args) {
   
        SimulacionColaSimpleModificado sim=new SimulacionColaSimpleModificado(100, 2, 10);
        sim.simular();
        sim.imprimirResultados();
        
    }
}
