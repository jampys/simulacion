/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulacion;

/**
 *
 * @author dario
 */
public class Simulacion2ServSerie extends SimulacionColaSimple {
    
    /*Heredo:
     * MX
     * NA
     * ND
     * AT //tiempo de la siguiente llegada
     * 
     * 
     */
    private int n1;
    private int n2;
    private float DT1; //tiempo de siguiente salida en servidor 1
    private float DT2; //tiempo de siguiente salida en servidor 2
    
    public Simulacion2ServSerie(int MX){
        super(MX);
        this.n1=0;
        this.n2=0;
        this.DT1=9999;
        this.DT2=9999;  // tambien puedo poner =Float.POSITIVE_INFINITY;
        
    }
    
    @Override
    public void simular(){    
        do{
            if(AT==Math.min(AT, DT1) && AT==Math.min(AT, DT2)){ //CASO 1 //Pregunta si AT es el minimo entre AT, DT1 y DT2
                
                TM=AT;
                NA=NA+1;
                n1=n1+1;
                
                AT=TM+this.generarTE();
                
                if(n1==1){
                    DT1=TM+this.generarTS();
                }
                
                //OJO PROBANDO. 
                Cliente cli=new Cliente(NA, TM);
                clientes.add(cli);
            }
            
            if(DT1<AT && DT1<=DT2){ //CASO 2
                
                TM=DT1;
                n1=n1-1;
                n2=n2+1;
                
                if(n1==0){ 
                    DT1=9999;    
                }
                else{
                    DT1=TM+this.generarTS();
                }
                
                if(n2==1){ 
                    DT2=TM+this.generarTS();    
                }
                
                //OJO PROBANDO.
                clientes.get(NA-n1-1).arriboServidor2=TM;
            }
            
            
            if(DT2<AT && DT2<DT1){ //CASO 3
                
                TM=DT2;
                ND=ND+1;
                n2=n2-1;
                
                
                
                if(n2==0){ 
                    DT2=9999;    
                }
                else{ //n2>0
                    DT2=TM+this.generarTS();
                }
                
                //OJO PROBANDO.
                clientes.get(ND-1).tiempoSalida=TM;
            }
            
            
        }
        while(TM<=MX);  //REVISAR LA FUNCION DE CORTE
    
    }
    
    
    
    
    
    
    public static void main(String[] args) {
   
        Simulacion2ServSerie sim=new Simulacion2ServSerie(100);
        sim.simular();
        sim.imprimirResultados();
        
    }
    
    @Override
    public void imprimirResultados(){
 
    System.out.println("MX (fin simulacion): "+MX);
    System.out.println("TM (tiempo actual) "+TM);
    //System.out.println("AT (proximo arribo) "+AT);
    //System.out.println("DT (proxima salida) "+DT);
    
    System.out.println("\nNumero total de llegadas: "+NA);
    System.out.println("Numero total de salidas: "+ND);
    System.out.println("Numero de clientes en Servidor 1: "+n1);
    System.out.println("Numero de clientes en Servidor 2: "+n2);
    
    System.out.println("\n******** LISTADO DE CLIENTES PROCESADOS EN EL SISTEMA ***********\n");
    for(int i=0; i<clientes.size(); i++){
        System.out.println("Nro cliente:"+clientes.get(i).nroCliente+
                           " | Tiempo arribo Servidor1: "+clientes.get(i).tiempoArribo+
                           " | Tiempo arribo Servidor2: "+clientes.get(i).arriboServidor2+
                           " | Tiempo salida: "+clientes.get(i).tiempoSalida);
    
    }
    
    }
    
    
    
    
}
