/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lineales.dinamicas;
/**
 *
 * @author CalamarCoder
 */
public class Cola {

    private Nodo frente;
    private Nodo fin;

    public Cola() {
        this.fin = null;
        this.frente = null;
    }

    public boolean poner(Object n) {
        boolean sePudo = true;
        Nodo aux = new Nodo(n, null);
        if (this.fin == null && this.frente == null) {
            this.frente = aux;
            this.fin = aux;
        } else {
            this.fin.setEnlace(aux);
        }
        this.fin = aux;
        return sePudo;
    }

    public boolean esVacia() {
        boolean vacia = false;
        if (this.fin == null && this.frente == null) {
            vacia = true;
        }
        return vacia;
    }

    public boolean sacar() {
        boolean sePudo = true;
        if (this.esVacia()) {
            sePudo = false;
        } else {
            this.frente = this.frente.getEnlace();
            if (this.frente == null) {
                this.fin = null;
            }
        }
        return sePudo;
    }

    public Object obtenerFrente() {
        if(this.frente==null){
            return null;
        }else{
           return this.frente.getElemento(); 
        }
        
    }
    
    public Object obtenerFin() {
        return this.fin.getElemento();
    }

    public void vaciar() {
        this.fin = null;
        this.frente = null;
    }

    public String toString() {
        String cadena = "";
        if (this.esVacia()) {
            cadena = "Es vacía";
        } else {
            cadena += "[ ";
            Nodo aux = this.frente;
            while (aux.getEnlace() != null) {
                cadena += aux.getElemento();
                aux = aux.getEnlace();
                if (aux.getEnlace() != null) {
                    cadena += ',';
                }
            }
            cadena += ',';
            cadena += aux.getElemento();
            cadena += "]";
        }
        return cadena;
    }
    
        public Cola clone() {
        Cola clon = new Cola();
        Nodo aux = this.frente;
        Nodo aux2, N;
        
        if (aux != null) {
            clon.frente = new Nodo(aux.getElemento(), null);
            aux2 = clon.frente;
            //recorro la lista original con aux
            while (aux.getEnlace() != null) {
                aux = aux.getEnlace();
                N = new Nodo(aux.getElemento(), null);
                aux2.setEnlace(N);
                aux2 = N;
            }
            clon.fin=aux2;
        }
        
        return clon;
    }

}
