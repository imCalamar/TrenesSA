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
public class Pila {

    private Nodo tope;

    public Pila() {
        this.tope = null;
    }

    public boolean apilar(Object nuevoElem) {
        Nodo nodoNuevo = new Nodo(nuevoElem, this.tope);
        this.tope = nodoNuevo;
        return true;
    }

    public boolean desapilar() {
        boolean condicion = false;

        if (this.tope != null) {
            this.tope = this.tope.getEnlace();
            condicion = true;
        }
        return condicion;
    }

    public Object obtenerTope() {
        if (this.tope==null) {
            return null;
        }
        return this.tope.getElemento();
    }

    public boolean esVacia() {
        boolean condicion = false;
        if (this.tope == null) {
            condicion = true;
        }
        return condicion;
    }

    public void vaciar() {
        this.tope = null;
    }
    
    public Pila clone() {
        Pila clon = new Pila();
        if (this.tope == null) {
            clon.tope = null;
        } else {
            Nodo aux1 = this.tope;
            Nodo aux2;
            clon.tope = new Nodo(aux1.getElemento());
            aux2 = clon.tope;
            aux1 = aux1.getEnlace();
            while (aux1 != null) {
                Nodo nodoNuevo = new Nodo(aux1.getElemento());
                aux2.setEnlace(nodoNuevo);
                aux2 = nodoNuevo;
                aux1 = aux1.getEnlace();
            }
        }
        return clon;
    }

    public String toString() {
        String cad = "";
        if (this.tope == null) {
            cad = "la cadena es vacía";
        } else {
            cad = "]";
            Nodo aux = this.tope;
            while (aux != null) {
                cad = aux.getElemento()+cad;
                aux = aux.getEnlace();
                if (aux != null) {
                    cad = ","+cad;
                }
            }
            cad = "["+cad;
        }
        return cad;
    }
}