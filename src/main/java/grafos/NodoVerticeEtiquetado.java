package grafos;
/**
 *
 * @author JoaquinNoillet
 */
public class NodoVerticeEtiquetado {

    private Object elemento;
    private NodoVerticeEtiquetado sigVertice;
    private NodoAdyacenteEtiquetado primerAdy;

    public NodoVerticeEtiquetado(Object elem, NodoVerticeEtiquetado nodoVertice, NodoAdyacenteEtiquetado nodoAdy) {
        this.elemento = elem;
        this.sigVertice = nodoVertice;
        this.primerAdy = nodoAdy;
    }

    public Object getElemento() {
        return this.elemento;
    }

    public NodoVerticeEtiquetado getSigVertice() {
        return this.sigVertice;
    }

    public NodoAdyacenteEtiquetado getAdyacente() {
        return this.primerAdy;
    }

    public void setElemento(Object elem) {
        this.elemento = elem;
    }

    public void setSigVertice(NodoVerticeEtiquetado nodo) {
        this.sigVertice = nodo;
    }

    public void setAdyacente(NodoAdyacenteEtiquetado nodo) {
        this.primerAdy = nodo;
    }

    public boolean equals(NodoVerticeEtiquetado elem) {
        return this.elemento.equals(elem.elemento);
    }
}
