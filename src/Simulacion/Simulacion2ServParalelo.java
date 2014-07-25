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
public class Simulacion2ServParalelo {
    
    protected float TM; //tiempo de reloj de la simulacion (tiempo actual)
    protected float AT; // tiempo de siguiente llegada
    private float DT1; //tiempo de siguiente salida en servidor 1
    private float DT2; //tiempo de siguiente salida en servidor 2
    
    protected int MX; //tiempo maximo de simulacion (fin de la simulacion)
    
    protected int NA; //numero de llegadas hasta el instante TM
    protected int C1; // numero de clientes atendidos por Servidor 1
    protected int C2; // numero de clientes atendidos por Servidor 2
    
    //private int n; //numero de clientes en el sistema, en el instante TM
    
    protected ArrayList<Cliente> clientes;
    protected ArrayList<Integer> ES;
    
    public Simulacion2ServParalelo(int MX){
        this.MX=MX;
        this.NA=0;
        this.C1=0;
        this.C2=0;
        
        ES=new ArrayList();        
        for(int i=0; i<3; i++){
            ES.add(i, 0);
        }
        
        clientes=new ArrayList();
        
        this.AT=0;
        this.DT1=9999;
        this.DT2=9999;  // tambien puedo poner =Float.POSITIVE_INFINITY;
        
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
            if(AT==Math.min(AT, DT1) && AT==Math.min(AT, DT2)){
                
                if(AT>MX)break;
                //CASO 1 //Pregunta si AT es el minimo entre AT, DT1 y DT2
                System.out.println("CASO 1");
                TM=AT;
                NA=NA+1;
                AT=TM+this.generarTE();
                          
                //OJO PROBANDO. 
                Cliente cli=new Cliente(NA, TM);
                clientes.add(cli);
                
                if(ES.get(0)==0 && ES.get(1)==0 && ES.get(2)==0){
                    ES.set(0, 1);
                    ES.set(1, NA);
                    ES.set(2, 0);
                    
                    DT1=TM+this.generarTS();
                   
                }
                
                else if(ES.get(0)==1 && ES.get(1)!=0 && ES.get(2)==0){
                    
                    ES.set(0, 2);
                    //el elemento 1 queda igual.
                    ES.set(2, NA);
                    
                    DT2=TM+this.generarTS();
                  
                }
                
                else if(ES.get(0)==1 && ES.get(1)==0 && ES.get(2)!=0){
                    
                    ES.set(0, 2);
                    ES.set(1, NA);
                    //el elemento 3 queda igual.
                    
                    DT1=TM+this.generarTS();
                  
                }
                
                else if(ES.get(0)>1){ //la posicion 0 del Array es n..
                    ES.set(0, ES.get(0)+1); //n=n+1
                    ES.add(NA); 
                
                }
                   
            }
            
            else if(DT1<AT && DT1<=DT2){ //CASO 2
                System.out.println("CASO 2");
                TM=DT1;
                C1=C1+1;
                
                //PROBANDO
                clientes.get(ES.get(1)-1).tiempoSalida=TM;
                
                if(ES.get(0)==1){
                    
                    ES.set(0, 0);
                    ES.set(1, 0);
                    ES.set(2, 0);
                    
                    DT1=9999;
                    }
                
                else if(ES.get(0)==2){
                    ES.set(0, 1);
                    ES.set(1, 0);
                    //el elemento 2 queda como estaba
                    
                    DT1=9999;
                }
                
                else if(ES.get(0)>2){
                    ES.set(0, ES.get(0)-1); //n=n-1
                    //LINEA DE ABAJO PROBANDO
                    ES.set(1, ES.get(3)); //Coloco en el servidor 1 (que se acaba de liberar), al 1ro de la cola
                    ES.remove(3); //Lo elimino de la cola, ya que paso a ser atendido.
                    
                    DT1=TM+this.generarTS();
                
                }
                   
            }
            
            else if(DT2<AT && DT2<DT1){ //CASO 3. OJO NO ESTA EN EL LIBRO
                System.out.println("CASO 3");
                TM=DT2;
                C2=C2+1;
                
                //PROBANDO
                clientes.get(ES.get(2)-1).tiempoSalida=TM;
                
                if(ES.get(0)==1){
                    
                    ES.set(0, 0);
                    ES.set(1, 0);
                    ES.set(2, 0);
                    
                    DT2=9999;
                    }
                
                else if(ES.get(0)==2){
                    ES.set(0, 1);
                    //el elemento 1 queda como estaba
                    ES.set(2, 0);
                    
                    DT2=9999;
                }
                
                else if(ES.get(0)>2){
                    ES.set(0, ES.get(0)-1); //n=n-1
                    
                    //LINEA DE ABAJO PROBANDO. 
                    ES.set(2, ES.get(3));
                    ES.remove(3);
                    
                    DT2=TM+this.generarTS();
                
                }
                   
            }
            
            
            
            System.out.print("Estado sistema en instante "+TM);
            System.out.print(" Nro de clientes en el sistema "+ES.get(0));
            System.out.print(" En el serv 1 esta el cliente "+ES.get(1));
            System.out.println(" En el serv 2 esta el cliente "+ES.get(2));
            
            
            
        }
        while(true);
        //while(TM<=MX);  
    
    }
    
    
    
    
    
    
    public static void main(String[] args) {
   
        Simulacion2ServParalelo sim=new Simulacion2ServParalelo(100);
        sim.simular();
        sim.imprimirResultados();
        
    }
    
    
    public void imprimirResultados(){
 
    System.out.println("MX (fin simulacion): "+MX);
    System.out.println("TM (tiempo actual) "+TM);
    //System.out.println("AT (proximo arribo) "+AT);
    //System.out.println("DT (proxima salida) "+DT);
    
    System.out.println("\nNumero total de llegadas: "+NA);
    //System.out.println("Numero total de salidas: "+ND);
    System.out.println("Numero de clientes atendidos por Servidor 1: "+C1);
    System.out.println("Numero de clientes atendidos por Servidor 2: "+C2);
    
    System.out.println("\n******** LISTADO DE CLIENTES PROCESADOS EN EL SISTEMA ***********\n");
    for(int i=0; i<clientes.size(); i++){
        System.out.println("Nro cliente:"+clientes.get(i).nroCliente+
                           " | Tiempo arribo: "+clientes.get(i).tiempoArribo+
                           " | Tiempo salida: "+clientes.get(i).tiempoSalida);
    
    }
    
    }
    
    
    
    
}

