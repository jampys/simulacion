/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulacion;

/**
 *
 * @author dario
 */
public class Inventario {
    
    int T; //tiempo total de simulacion
    
    float t; //tiempo actual
    float t0; //tiempo llegada proximo cliente
    float t1; //tiempo llegada proximo pedido
    
    private int s; //cantidad minima del producto
    private int S; //cantidad deseada del producto
    private int x; //stock actual del producto
    private int y; //la cantidad pedida del producto
    
    float C; //cantidad total de costos de los pedidos hasta t
    float H; //cantidad total de costos de mantenimiento de inventario hasta t
    float R; //cantidad total de ingresos obtenidos hasta t
    
    float h; //costo de mantenimiento de inventario por unidad de tiempo
    float c; //costo de cada unidad de producto pedida (comprada)
    float r; //precio de venta del producto r
    
    public Inventario(int s, int S, float c, float r, int x, float h){
        this.s=s;
        this.S=S;
        this.c=c;
        this.r=r;
        this.x=x; //partimos con un stock inicial de x unidades del producto
        this.h=h;
       
        this.C=0;
        this.H=0;
        this.R=0;
        
        this.t0=0;
        this.t1=0;
        
        T=100;
        
    }
    
    
    public int generarDE(){
        //GENERAR CANTIDAD DEMANDADA POR CLIENTE
        int ts;
        float rnd=(float)Math.random();
        
        if(rnd<=0.35){ts=1;}
        else if(rnd<=0.75){ts=2;}
        else {ts=3;}
        
        return ts;
    }
    
    public float generarTE(){
        //GENERAR LOS TIEMPOS ENTRE ARRIBOS
        float ts;
        float rnd=(float)Math.random();
        
        if(rnd<=0.20){ts=1;}
        else if(rnd<=0.50){ts=2;}
        else if(rnd<=0.85){ts=3;}
        else {ts=4;}
        
        return ts;
    }
    
    
    
    public void simular(){
        
        t0+=this.generarTE();
        t1+=this.generarTE();
        
        do{
            if(t0<t1){ //CASO 1. ENTRA UN CLIENTE
                H=H+(t0-t)*x*h;
                t=t0;
                int D=this.generarDE(); //cantidad demandada por cliente
                int w=Math.min(D, x);
                R=R+w*r;
                x=x-w;
                
                if(x<s && y==0){
                    y=S-x;
                    t1=t+5; //suponiento que la mercaderia demora L=5 unidades de tiempo en llegar
                }
                
                t0=t+this.generarTE(); //calculo el proximo arribo de cliente
                
            }
            
            
            if(t1<=t0){ //CASO 2. ENTRA PEDIDO MERCADERIA
                H=H+(t1-t)*x*h;
                t=t1;
                C=C+y*c;
                x=x+y;
                y=0;
                t1=9999;
                  
            }
                           
        }
        while(t<=T);
    
    }
    
    
    
    public void imprimirResultados(){
 
    //System.out.println("MX (fin simulacion): "+MX);
    System.out.println("t (tiempo actual) "+t);
   
    
    System.out.println("\nGanancia total: $"+R);
    System.out.println("Costos por mercaderia comprada: $"+C);
    System.out.println("Costos por mantenimiento inventario: $"+H);
    System.out.println("Ganancia promeio de la tienda por unidad de tiempo: $"+(R-C-H)/T);
    
    }
    
    
    public static void main(String[]args){
        Inventario inv=new Inventario(5, 10, 250, 340, 5, 10);
        inv.simular();
        inv.imprimirResultados();
    
    }
    
}
