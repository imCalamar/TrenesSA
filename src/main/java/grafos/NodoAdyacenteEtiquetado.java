package grafos;
/**
 *
 * @author JoaquinNoillet
 */
public class NodoAdyacenteEtiquetado {

    private NodoVerticeEtiquetado NodoVert;
    private NodoAdyacenteEtiquetado NodoAdy;
    private double Etiqueta;

    public NodoAdyacenteEtiquetado(NodoVerticeEtiquetado nodoVert, NodoAdyacenteEtiquetado nodoAdy, double etiqueta) {
        this.NodoVert = nodoVert;
        this.NodoAdy = nodoAdy;
        this.Etiqueta = etiqueta;
    }

    public NodoVerticeEtiquetado getVertice() {
        return this.NodoVert;
    }

    public void setVertice(NodoVerticeEtiquetado nodo) {
        this.NodoVert = nodo;
    }

    public NodoAdyacenteEtiquetado getSiguienteAdy() {
        return this.NodoAdy;
    }

    public void setSiguienteAdy(NodoAdyacenteEtiquetado nodo) {
        this.NodoAdy = nodo;
    }

    public double getEtiqueta() {
        return this.Etiqueta;
    }

    public void setEtiqueta(double elem) {
        this.Etiqueta = elem;
    }
}
