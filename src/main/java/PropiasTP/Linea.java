/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PropiasTP;

import lineales.dinamicas.Lista;

/**
 *
 * @author CalamarCoder
 */
public class Linea {
    private String nombre;
    private Lista listaLineas;

    public Linea(String nombre, Lista listaLineas) {
        this.nombre=nombre;
        this.listaLineas=listaLineas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Lista getListaLineas() {
        return listaLineas;
    }

    public void setListaLineas(Lista listaLineas) {
        this.listaLineas = listaLineas;
    }

    @Override
    public String toString() {
        return "Linea{" + "nombre=" + nombre + ", listaLineas=" + listaLineas.toString() + '}';
    }


}
