
package Final;

import Final.Cliente;
import Final.Evento;
import java.util.ArrayList;


public class Prueba {

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
    protected ArrayList<Evento> eventos;
    
    //AGREGADOS FINAL
    protected int cantEnlaces;
    protected int cantTelefonos; //telefono=linea
    //FIN
    
    public Prueba(int MX, int cantEnlaces, int cantTelefonos){
        this.AT=0;
        this.DT=9999;
        this.SS=0;
        this.WL=0;
        this.MX=MX; 
        //AGREGADOS FINAL
        this.cantEnlaces=cantEnlaces;
        this.cantTelefonos=cantTelefonos;
        this.NA=0;
        this.ND=0;
        this.n=0;
        //FIN
        clientes=new ArrayList();
        eventos=new ArrayList();
        
        Evento primerEvento=new Evento(0, 1, 0);
        eventos.add(primerEvento);
        
        
    }
    
    
    public float generarTS(){ //GENERAR LOS TIEMPOS DE SERVICIO
        float ts;
        float rnd=(float)Math.random();
        
        if(rnd<=0.35){ts=8;} //4
        else if(rnd<=0.75){ts=9;} //5
        else {ts=10;} //6
        
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
            this.ordenarEventos();
            
            //****************PROCESAR UNA LLEGADA. CASO 1*********************************
            if(eventos.get(0).tipoEvento==1 && eventos.get(0).tiempo<=MX){ 
                System.out.println("CASO 1");
                TM=eventos.get(0).tiempo;
                n=n+1;
                NA=NA+1;
                
                Cliente cli=new Cliente(NA, TM);
                clientes.add(cli);
                eventos.remove(0);
                
                if(n<=cantEnlaces){ //Si hay algun enlace libre
                    //SS=1;
                    float tiempoSalida=TM+this.generarTS(); //calcula el tiempo de salida
                    Evento eve=new Evento(tiempoSalida, 2, NA-1);
                    eventos.add(eve);
                    //this.ordenarEventos();
                    //this.imprimirListaEventos();
                    
                    clientes.get(NA-1).tiempoSalida=tiempoSalida;
                    clientes.get(NA-1).incioAtencion=TM;
                }
                else{ //no hay ningun enlace libre
                    WL=WL+1;
                }
                clientes.get(NA-1).clientesEnCola=WL; 
                //AT=TM+this.generarTE(); //calcula la proxima llegada
                
                float proximoArribo=TM+this.generarTE(); //calcula el proximo arribo
                Evento eve1=new Evento(proximoArribo, 1, NA);
                eventos.add(eve1);
                //this.ordenarEventos();
                //this.imprimirListaEventos();
                  
            }
            //****************************************************************************
            
            //****************PROCESAR UNA SALIDA. CASO 2*********************************
            else if(eventos.get(0).tipoEvento==2 && eventos.get(0).tiempo <=MX){ 
                System.out.println("CASO 2");
                TM=eventos.get(0).tiempo;
                n=n-1;
                ND=ND+1;
                eventos.remove(0);
                
                if(WL>0){ //WL>0. Si hay alguien en la cola
                    WL=WL-1;
                    
                    float tiempoSalida=TM+this.generarTS(); //calcula el tiempo de salida
                    Evento eve2=new Evento(tiempoSalida, 2, ND+cantEnlaces-1);
                    eventos.add(eve2);
                    //this.ordenarEventos();
                    
                    //PROBANDO//OJO MUY IMPORTANE
                    //para 2 servidores ND+1
                    //para 3 servidores ND+2
                    //para 4 servidores ND+3 .... para n servidores ND+cantEnlaces-1
                    clientes.get(ND+cantEnlaces-1).incioAtencion=TM;
                    clientes.get(ND+cantEnlaces-1).tiempoSalida=tiempoSalida; //calcula el tiempo de salida
                    
                }
                else{ //WL=0
                    this.ordenarEventos();
                    //SS=0;
                    //DT=9999; 
                    //this.setDT();
                    //System.out.println("TROLAZO "+getDT());
                    //DT=this.getDT();
                }
                

            }
            //*******************************************************************************
            
            //*********************************CASO 3*********************************
            else if(eventos.get(0).tiempo>MX && n>0){
            
//                System.out.println("SE EJECUTA CASO 3");
//                TM=eventos.get(0).tiempo;
//                n=n-1;
//                ND=ND+1;
//                
//                eventos.remove(0);
//                
//                
//                float tiempoSalida=TM+this.generarTS(); //calcula el tiempo de salida
//                Evento eve3=new Evento(tiempoSalida, 2, NA-1);
//                eventos.add(eve3);
//                this.ordenarEventos();
//                
//                
//                clientes.get(ND-1).incioAtencion=TM;
//                clientes.get(ND-1).tiempoSalida=tiempoSalida; //calcula el tiempo de salida
                
                  //PROBANDO MODIFICACION CASO 3
                  System.out.println("SE EJECUTA CASO 3");
                  TM=eventos.get(0).tiempo;
                  n=n-1;
                  ND=ND+1;
                  
                  eventos.remove(0);
                  
                  if(n>=cantEnlaces){
                      float tiempoSalida=TM+this.generarTS(); //calcula el tiempo de salida
                      Evento eve3=new Evento(tiempoSalida, 2, NA-1);
                      eventos.add(eve3);
                      //this.ordenarEventos();
                      
                     clientes.get(ND+cantEnlaces-1).incioAtencion=TM;
                     clientes.get(ND+cantEnlaces-1).tiempoSalida=tiempoSalida; //calcula el tiempo de salida
                  }
                  
                                 
            }
            
            //***************************CASO 4********************************
            else if(eventos.get(0).tiempo>MX && n==0){
                System.out.println("SE EJECUTA CASO 4");
                Tp=Math.max(TM-MX, 0);
                break;
            }
               
        }
        //while(TM<=MX || n>0);
        while(true);
    
    }
    
    public void imprimirResultados(){
 
    //System.out.println("MX (fin simulacion): "+MX);
    //System.out.println("TM (tiempo actual) "+TM);
    //System.out.println("AT (proximo arribo) "+AT);
    //System.out.println("DT (proxima salida) "+DT);
    //System.out.println("Elementos en cola "+WL);
    
    //System.out.println("\nNumero total de llegadas: "+NA);
    //System.out.println("Numero total de salidas: "+ND);
    //System.out.println("Numero de clientes en el sistema: "+n);
    //System.out.println("Tiempo salida posterior a MX: "+Tp);
    
    System.out.println("\n******** LISTADO DE CLIENTES PROCESADOS EN EL SISTEMA ***********\n");
    for(int i=0; i<clientes.size(); i++){
        System.out.println("Nro cliente:"+clientes.get(i).nroCliente+
                           " | Tiempo arribo:"+clientes.get(i).tiempoArribo+
                           " | Inicio Atencion:"+clientes.get(i).incioAtencion+
                           " | Tiempo salida:"+clientes.get(i).tiempoSalida+
                           " | En cola: "+clientes.get(i).clientesEnCola);
    
    }
    
//    //PARA PROBAR IMPRESION DE EVENTOS
//    for(int i=0; i<eventos.size(); i++){
//    System.out.println("tipo evento "+eventos.get(i).tipoEvento+" tiempo "+eventos.get(i).tiempo);
//    }
    
    }
    
   
    
    public void ordenarEventos(){
        //System.out.println("ORDENO LOS EVENTOS");
        for(int i=0; i<eventos.size(); i++){ //ordeno los eventos de menor a mayor
            for(int j=i+1; j<eventos.size(); j++){
                if(eventos.get(j).tiempo<eventos.get(i).tiempo){
                Evento aux=eventos.get(i);
                eventos.set(i, eventos.get(j));
                eventos.set(j, aux);
            }
            }
            
        }
        //System.out.println("proximo evento a las "+eventos.get(0).tiempo);
    }
    
    public void imprimirListaEventos(){
        System.out.println("*************");
        for(int i=0; i<eventos.size(); i++){   
            System.out.println("Tiempo evento "+eventos.get(i).tiempo);
        }
        System.out.println("");
    }
    
    
    
    public static void main(String[] args) {
   
        Prueba sim=new Prueba(25, 2, 10);
        sim.simular();
        sim.imprimirResultados();
        
    }
}
