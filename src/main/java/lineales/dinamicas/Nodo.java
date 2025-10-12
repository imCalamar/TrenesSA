package lineales.dinamicas;
/**
 *
 * @author JoaquinNoillet
 */
class Nodo {
    //atributos
    private Object elemento;
    private Nodo enlace;

    //constructor
    public Nodo(Object elem, Nodo enlace) {
        this.elemento = elem;
        this.enlace = enlace;
    }

    public Nodo(Object elem) {
        this.elemento = elem;
        this.enlace = null;
    }
//observadores
    public Object getElemento() {
        return this.elemento;
    }

    public Nodo getEnlace() {
        return this.enlace;
    }
//modificadores
    public void setElemento(Object elemento) {
        this.elemento = elemento;
    }

    public void setEnlace(Nodo enlace) {
        this.enlace = enlace;
    }
}
