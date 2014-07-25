/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulacion;

/**
 *
 * @author dario
 */
public class Reparacion {
    
    int n; //maquinas en funcionamiento
    int s; //maquinas de repuesto
    
    float T; //tiempo en el que el sistema falla
    
    float t; //tiempo actual del sistema
    int r; //cantidad de maquinas descompuestas en el instante t
    
    int []te;
    int tr; //tiempo en que sale un equipo ya reparado
    
    
    public Reparacion(int n, int s){
        this.t=0;
        this.r=0;
        
        this.n=n; 
        this.s=s; 
        
        //calculo los tiempos de proxima falla de c/u de los equipos en funcionamiento
        te=new int[n];
        for(int i=0; i<te.length; i++){
            te[i]= (int)this.generarPF();
        }
        this.ordenarFallas();
        this.mostrarFallas();
        tr=(int)Float.POSITIVE_INFINITY; //t*=infinito

    }
    
    public float generarPF(){ //genera del tiempo de proxima falla de cada equipo que esta en funcionamiento
        int pf;
        //pf = Math.floor(Math.random()*(N-M+1)+M);
        //pf = (int)Math.floor(Math.random()*(1700-1200+1)+1200);
        pf = (int)Math.floor(Math.random()*(175-125+1)+125);
        
        return pf;
    }
    
    public float generarTR(){ //genera tiempo de reparacion del equipo fallado
        int tr;
        //pf = Math.floor(Math.random()*(N-M+1)+M);
        //tr = (int)Math.floor(Math.random()*(50-25+1)+25);
        tr = (int)Math.floor(Math.random()*(10-5+1)+5);
        
        return tr;
    }
    
    
    public void ordenarFallas(){
        //ordenar el vector ascendente
        for(int i=0; i<te.length;i++){
            for(int j=i+1;j<te.length;j++){
                if(te[i]>te[j]){
                    int aux=te[i];
                    te[i]=te[j];
                    te[j]=aux;    
                }
            }
        }  
    }
    
    public void mostrarFallas(){
        System.out.println();
        for(int i=0; i<te.length; i++){
            System.out.print("- "+te[i]);
        }
    }
    
    public void simular(){
        int i=0;
        do{
            
            if(te[0]<tr){ //CASO 1
                t=te[0];
                r=r+1;
                
                if(r==s+1){
                    T=t;
                    System.out.println("\nEl sistema FALLO en instante "+T);
                    System.out.println("El algoritmo hizo "+i+" ciclos.");
                    break;
                }
                if(r<s+1){
                    te[0]=(int)(t+this.generarPF()); 
                    this.ordenarFallas();
                    this.mostrarFallas();
                    /*equivale a eliminar t1 y agregar al vector t+X.
                     * Lo hago de esta manera porque estoy usando un vector, No un ArrayList
                     */
                }
                if(r==1){
                    tr=(int)(t+this.generarTR());
                
                }
            
            }
            
            if(tr<=te[0]){
                t=tr;
                r=r-1;
                if(r>0){
                    tr=(int)(t+this.generarTR());
                }
                if(r==0){
                    tr=(int)Float.POSITIVE_INFINITY;
                }
            }
            
            
        
        
        i++;
        
         System.out.print(" Hay "+r+" equipos en reparacion.");
         System.out.print(" Hay "+s+" equipos de backup.");
        
        }
        
        while(t<9999999);
    
    }
    
    
    
    public static void main(String[]args){
        Reparacion rep=new Reparacion(10, 5);// 1: maquinas en funcionamiento. 2: maquinas de repuesto
        //rep.ordenarFallas();
        //rep.mostrarFallas();
        rep.simular();
    }
    
    
    
}


