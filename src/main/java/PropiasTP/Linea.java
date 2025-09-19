
package PropiasTP;

import lineales.dinamicas.Lista;

/**
 *
 * @author JoaquinNoillet
 */
public class Linea {
    private String nombre;
    private Lista listaDeEstaciones;

    public Linea(String nombre, Lista listaDeEstaciones) {
        this.nombre=nombre;
        this.listaDeEstaciones=listaDeEstaciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Lista getListaDeEstaciones() {
        return listaDeEstaciones;
    }

    public void setListaDeEstaciones(Lista listaDeEstaciones) {
        this.listaDeEstaciones = listaDeEstaciones;
    }

    @Override
    public String toString() {
        return "Linea{" + "nombre=" + nombre + ", lista de estaciones=" + listaDeEstaciones.toString() + '}';
    }


}
