
package Final;

import java.util.ArrayList;
import FinalPruebas.*;


public class Ejercicio1v1 {

    private float TE; //tiempos entre llegada
    private float TS; //tiempos de servicio
    protected float TM; //tiempo de reloj de la simulacion (tiempo actual)
    protected float AT; // tiempo de siguiente llegada
    private float DT; //tiempo de siguiente salida
    protected int MX; //tiempo maximo de simulacion (fin de la simulacion)
    
    protected int NA; //numero de llegadas hasta el instante TM
    protected int ND; //numero de salidas hasta el instante TM
    private int n; //numero de clientes en el sistema, en el instante TM
    
    protected ArrayList<Cliente> clientes;
    protected ArrayList<Evento> eventos;
    
    protected int cantEnlaces;
    protected int cantTelefonos; //telefono=linea
    protected Conmutador con;
    protected int exitosas; //cantidad de exitosas
    
    public Ejercicio1v1(int MX, int cantEnlaces, int cantTelefonos){
        this.AT=0;
        this.DT=9999;
        this.MX=MX; 
        this.exitosas=0;
     
        this.cantEnlaces=cantEnlaces;
        this.cantTelefonos=cantTelefonos;
        this.con=new Conmutador(cantTelefonos, 1000);
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
            //Agregado pra encontrar defecto
            //System.out.println("bucle infinito");
            //System.out.println("El n es: "+n);
            //System.out.println("El tiemo actual es: "+convertirAMinutos(eventos.get(0).tiempo));
            //this.imprimirListaEventos();
            //fin agregado para encontrar defecto
            this.ordenarEventos();
            
            //****************PROCESAR UNA LLEGADA. CASO 1*********************************
            if(eventos.get(0).tipoEvento==1 && eventos.get(0).tiempo<=MX){ 
                //System.out.println("CASO 1");
                TM=eventos.get(0).tiempo;
                n=n+1;
                NA=NA+1;
                   
                int v[];
                int origen=con.getOrigen();
                v=con.getDestino(origen);
                int destino=v[1];
                //Cliente cli=new Cliente(NA, TM);
                Cliente cli=new Cliente(NA, TM, origen, destino);
                clientes.add(cli);
                eventos.remove(0);
                
                if(n<=cantEnlaces && v[0]==0){ //Si hay algun enlace libre y el destino esta libre
                    //Agregados para ocupar/desocupar telefono
                    exitosas++; //incrementa en 1 la propo
                    con.busyTelefono(origen);
                    con.busyTelefono(destino);
                    //Fin agregados
                    
                    float tiempoSalida=TM+this.generarTS(); //calcula el tiempo de salida
                    Evento eve=new Evento(tiempoSalida, 2, NA-1);
                    eventos.add(eve);
                    
                    clientes.get(NA-1).tiempoSalida=tiempoSalida;
                    clientes.get(NA-1).incioAtencion=TM;
                    clientes.get(NA-1).estado="Exitosa";
                }
                else{ //no hay ningun enlace libre o el destino esta ocupado
                    
                    if(n>cantEnlaces) clientes.get(NA-1).estado="sin enlace";
                    else clientes.get(NA-1).estado="ocupada";
                    
                    n=n-1;  //IMPORTANTE: Si la llamada no es atendida se debe descontar de n
                    clientes.get(NA-1).tiempoSalida=0;
                    clientes.get(NA-1).incioAtencion=0;  
                    
                }
   
                float proximoArribo=TM+this.generarTE(); //calcula el proximo arribo
                Evento eve1=new Evento(proximoArribo, 1, NA);
                eventos.add(eve1);
             
            }
            //****************************************************************************
            
            
            //****************PROCESAR UNA SALIDA. CASO 2*********************************
            else if(eventos.get(0).tipoEvento==2 && eventos.get(0).tiempo <=MX){ 
                //System.out.println("CASO 2");
                TM=eventos.get(0).tiempo;
                n=n-1;
                ND=ND+1;
                //AGREGO TODO LO NECESARIO PARA LIBERAR LOS TELEFONOS
                int origen=clientes.get(eventos.get(0).idxCliente).origen;
                int destino=clientes.get(eventos.get(0).idxCliente).destino;
                con.freeTelefono(origen);
                con.freeTelefono(destino);
                //FIN AGREGADO
                eventos.remove(0);
                
                
            }
            //*******************************************************************************
            
            
            //*********************************CASO 3*********************************
            //else if(eventos.get(0).tiempo>MX && n>0){
            else if(eventos.get(0).tiempo>MX){
                  //System.out.println("SE EJECUTA CASO 3. Con evento tipo "+eventos.get(0).tipoEvento);
      
                  if(eventos.get(0).tipoEvento==1){  //para asegurar que no haya eventos de ingreso despues de MX
                      eventos.remove(0);
                  }
                  else{     
                    TM=eventos.get(0).tiempo;
                    n=n-1;
                    ND=ND+1;
                    //AGREGO TODO LO NECESARIO PARA LIBERAR LOS TELEFONOS
                    int origen=clientes.get(eventos.get(0).idxCliente).origen;
                    int destino=clientes.get(eventos.get(0).idxCliente).destino;
                    con.freeTelefono(origen);
                    con.freeTelefono(destino);
                    //FIN AGREGADO
                    eventos.remove(0);
                             
                  }
                  
                  if(eventos.isEmpty()){ //condicion de corte del bucle infinito
                      break;
                  }
                                      
            }
            
               
        }
        
        while(true);
    }
    
    public void imprimirResultados(){
 
    //System.out.println("MX (fin simulacion): "+MX);
    //System.out.println("TM (tiempo actual) "+TM);
    //System.out.println("AT (proximo arribo) "+AT);
    //System.out.println("DT (proxima salida) "+DT);
    
    //System.out.println("\nNumero total de llegadas: "+NA);
    //System.out.println("Numero total de salidas: "+ND);
    //System.out.println("Numero de clientes en el sistema: "+n);
    
    System.out.println("\n******** LISTADO DE CLIENTES PROCESADOS EN EL SISTEMA ***********\n");
    System.out.println("Cantidad de enlaces: "+cantEnlaces);
    System.out.println("Cantidad de telefonos: "+cantTelefonos);
    float proporcion=(float)exitosas/clientes.size();
    
    for(int i=0; i<clientes.size(); i++){
        System.out.println("Nro cliente:"+clientes.get(i).nroCliente+
                           " | Tiempo arribo:"+convertirAMinutos(clientes.get(i).tiempoArribo)+
                           " | Inicio Atencion:"+convertirAMinutos(clientes.get(i).incioAtencion)+
                           " | Tiempo salida:"+convertirAMinutos(clientes.get(i).tiempoSalida)+
                           " | Origen:"+clientes.get(i).origen+
                           " | Destino: "+clientes.get(i).destino+
                           " | Estado: "+clientes.get(i).estado);
        }
   
    System.out.println("\nProporcion de exitosas: "+Math.rint(proporcion*1000)/1000);
    //Redondea a 3 decimales: https://ar.answers.yahoo.com/question/index?qid=20130520185213AAOxhOo
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
            System.out.println("Tipo evento "+eventos.get(i).tipoEvento +" - Tiempo evento "+convertirAMinutos(eventos.get(i).tiempo));
        }
        System.out.println("");
    }
    
    public static String convertirAMinutos(float seg){
//        recibe el tiempo en segundos y lo convierte en minutos y segundos.
//        es solo para darle formato a las salidas por pantalla. En caso de tener que operar con tiempos
//        conviente previamente pasarlos a segundos, operar y luego volver a convertirlos a minutos
        int minutos=(int)seg/60;
        int segundos=(int)seg%60;
        //Agregando formato
        String minutosS="";
        String segundosS="";
        if(minutos<10) minutosS="0"+Integer.toString(minutos);
        else minutosS=Integer.toString(minutos);
        if(segundos<10) segundosS="0"+Integer.toString(segundos);
        else segundosS=Integer.toString(segundos);
        //fin agregando formato
        
        String tiempo=minutosS+":"+segundosS;
        return tiempo;
    }
    
    
    public static void main(String[] args) {
        
        EntradaDatos ed=new EntradaDatos();
        int cantTelefonos=ed.getCantTelefonos();
        int cantEnlaces=ed.getCantEnlaces();
        
        Ejercicio1v1 sim=new Ejercicio1v1(36000, cantEnlaces, cantTelefonos);
        sim.simular();
        sim.imprimirResultados();
        
    }
}
