
package Final;

import java.util.ArrayList;


public class LOCURAv2 {

    private float TE; //tiempos entre llegada
    private float TS; //tiempos de servicio
    protected float TM; //tiempo de reloj de la simulacion (tiempo actual)
    protected float AT; // tiempo de siguiente llegada
    private float DT; //tiempo de siguiente salida
    private int WL; // longitud de la cola de espera
    protected int MX; //tiempo maximo de simulacion (fin de la simulacion)
    
    protected int NA; //numero de llegadas hasta el instante TM
    protected int ND; //numero de salidas hasta el instante TM
    private int n; //numero de clientes en el sistema, en el instante TM
    private float Tp; //tiempo de salida del ultimo cliente posterior a TM
    
    protected ArrayList<Cliente> clientes;
    protected ArrayList<Evento> eventos;
    
    protected int cantEnlaces;
    protected int cantTelefonos; //telefono=linea
    
    public LOCURAv2(int MX, int cantEnlaces, int cantTelefonos){
        this.AT=0;
        this.DT=9999;
        this.WL=0;
        this.MX=MX; 
     
        this.cantEnlaces=cantEnlaces;
        this.cantTelefonos=cantTelefonos;
        this.NA=0;
        this.ND=0;
        this.n=0;
     
        clientes=new ArrayList();
        eventos=new ArrayList();
        
        Evento primerEvento=new Evento(0, 1, 0);
        eventos.add(primerEvento);
    }
    
    
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
                    float tiempoSalida=TM+this.generarTS(); //calcula el tiempo de salida
                    Evento eve=new Evento(tiempoSalida, 2, NA-1);
                    eventos.add(eve);
                    //this.imprimirListaEventos();
                    
                    clientes.get(NA-1).tiempoSalida=tiempoSalida;
                    clientes.get(NA-1).incioAtencion=TM;
                }
                else{ //no hay ningun enlace libre
                    WL=WL+1;
                }
   
                clientes.get(NA-1).clientesEnCola=(NA-ND-cantEnlaces<0)? 0:NA-ND-cantEnlaces; //operador ternario
            
                float proximoArribo=TM+this.generarTE(); //calcula el proximo arribo
                Evento eve1=new Evento(proximoArribo, 1, NA);
                eventos.add(eve1);
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
                    
                    //PROBANDO//OJO MUY IMPORTANE
                    //para 2 servidores ND+1
                    //para 3 servidores ND+2
                    //para 4 servidores ND+3 .... para n servidores ND+cantEnlaces-1
                    clientes.get(ND+cantEnlaces-1).incioAtencion=TM;
                    clientes.get(ND+cantEnlaces-1).tiempoSalida=tiempoSalida; //calcula el tiempo de salida
                    //clientes.get(ND+cantEnlaces-1).clientesEnCola=WL;  
                }
                else{ //WL=0
                    this.ordenarEventos(); //no es necesario, se puede borrar el else
                }
                
            }
            //*******************************************************************************
            
            
            //*********************************CASO 3*********************************
            else if(eventos.get(0).tiempo>MX && n>0){
                  System.out.println("SE EJECUTA CASO 3. Con evento tipo "+eventos.get(0).tipoEvento);
                  this.imprimirListaEventos();
                  
                  if(eventos.get(0).tipoEvento==1){ //para asegurar que no haya eventos de ingreso despues de MX
                      eventos.remove(0);
                  }
                  else{     
                    TM=eventos.get(0).tiempo;
                    n=n-1;
                    ND=ND+1;
                    eventos.remove(0);
                  
                    if(n>=cantEnlaces){
                        WL=WL-1;
                        float tiempoSalida=TM+this.generarTS(); //calcula el tiempo de salida
                        Evento eve3=new Evento(tiempoSalida, 2, NA-1);
                        eventos.add(eve3);
                        //this.ordenarEventos();
                      
                        clientes.get(ND+cantEnlaces-1).incioAtencion=TM;
                        clientes.get(ND+cantEnlaces-1).tiempoSalida=tiempoSalida; //calcula el tiempo de salida
                        //clientes.get(ND+cantEnlaces-1).clientesEnCola=WL; 
                    }
                  }
                  
                  if(eventos.isEmpty()){ //condicion de corte del bucle infinito
                      break;
                  }
                                      
            }
            
            //***************************CASO 4********************************
//            else if(eventos.get(0).tiempo>MX && n==0){
//                System.out.println("SE EJECUTA CASO 4");
//                this.imprimirListaEventos();
//                Tp=Math.max(TM-MX, 0);
//                break;
//            }
               
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
                           " | Tiempo arribo:"+convertirAMinutos(clientes.get(i).tiempoArribo)+
                           " | Inicio Atencion:"+convertirAMinutos(clientes.get(i).incioAtencion)+
                           " | Tiempo salida:"+convertirAMinutos(clientes.get(i).tiempoSalida)+
                           " | En cola: "+clientes.get(i).clientesEnCola);
        }
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
    }
    
    public void imprimirListaEventos(){
        System.out.println("******LISTA EVENTOS*******");
        for(int i=0; i<eventos.size(); i++){   
            System.out.println("Tipo evento "+eventos.get(i).tipoEvento +" - Tiempo evento "+eventos.get(i).tiempo);
        }
        System.out.println("");
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
    
    
    public static void main(String[] args) {
   
        LOCURAv2 sim=new LOCURAv2(200, 3, 10);
        sim.simular();
        sim.imprimirResultados();
           
    }
}
