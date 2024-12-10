package grafos;

/**
 *
 * @author CalamarCoder
 */
import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;
import java.util.HashMap;

public class GrafoEtiquetado {

    private NodoVerticeEtiquetado inicio;

    public GrafoEtiquetado() {
        this.inicio = null;
    }

    public boolean insertarVertice(Object element) {//Nodo nuevo
        boolean retorno = true;
        NodoVerticeEtiquetado nodo = this.inicio;
        if (nodo == null) {
            this.inicio = new NodoVerticeEtiquetado(element, null, null);
            retorno = true;
        } else {
            while (retorno && nodo.getSigVertice() != null) {
                if (nodo.getElemento().equals(element)) {
                    retorno = false;
                }
                nodo = nodo.getSigVertice();
            }
            if (retorno) { // si es true
                nodo.setSigVertice(new NodoVerticeEtiquetado(element, null, null)); //se agrega nuevo nodo vertice
                retorno = true;
            }
        }
        return retorno;
    }//check

    private boolean insertarAdyacente(NodoVerticeEtiquetado nodo, NodoVerticeEtiquetado Nodoadyacente, double etiqueta) {
        boolean res = false;
        if (nodo != null) {
            NodoAdyacenteEtiquetado aux = nodo.getAdyacente(); //puntero nodoAdyAux
            if (aux != null) {
                boolean control = true; // esta condicion se podria quitar
                while (aux.getSiguienteAdy() != null && control) {
                    aux = aux.getSiguienteAdy();
                }
                aux.setSiguienteAdy(new NodoAdyacenteEtiquetado(Nodoadyacente, null, etiqueta));
                res = true;
            } else {
                nodo.setAdyacente(new NodoAdyacenteEtiquetado(Nodoadyacente, null, etiqueta));
                res = true;
            }
        }
        return res;
    }

    private HashMap<Object, NodoVerticeEtiquetado> ubicarVertices(Object elemA, Object elemB) {
        boolean corte = false;
        HashMap<Object, NodoVerticeEtiquetado> retorno = new HashMap<>();
        NodoVerticeEtiquetado aux = this.inicio; //puntero auxiliar
        while (aux != null && !corte) {
            if (aux.getElemento().equals(elemA)) {
                NodoVerticeEtiquetado verticeA = aux;
                retorno.put(elemA, verticeA);
            } else if (aux.getElemento().equals(elemB)) {
                NodoVerticeEtiquetado verticeB = aux;
                retorno.put(elemB, verticeB);
            }

            if (retorno.get(elemA) != null && retorno.get(elemB) != null) {
                corte = true;
            }
            aux = aux.getSigVertice();
        }
        return retorno;
    }

    /**
     * eliminamos un vertice(Nodo)del grafo
     *
     * @param elemento
     * @return true si se elimino, false caso contrario
     */
    public boolean eliminarVertice(Object elemento) {
        boolean retorno = false;
        NodoVerticeEtiquetado nodo = this.inicio;
        if (nodo.getElemento().equals(elemento)) {
            retorno = this.eliminarDeAdyacentes(nodo, nodo);
            this.inicio = nodo.getSigVertice();
        } else {
            NodoVerticeEtiquetado sigVertice = nodo.getSigVertice();
            while (sigVertice != null && !sigVertice.getElemento().equals(elemento)) {
                sigVertice = sigVertice.getSigVertice();
                nodo = nodo.getSigVertice();
            }
            if (sigVertice != null) {
                retorno = this.eliminarDeAdyacentes(sigVertice, sigVertice);
                nodo.setSigVertice(sigVertice.getSigVertice()); //elimina el nodo, va a trash
            }
        }
        return retorno;
    }//check

    private boolean eliminarDeAdyacentes(NodoVerticeEtiquetado vertice, NodoVerticeEtiquetado elemento) {
        boolean retorno = false;
        NodoAdyacenteEtiquetado adyacente = vertice.getAdyacente();
        while (adyacente != null) {
            retorno = this.eliminarAdyacente(adyacente.getVertice(), elemento);
            adyacente = adyacente.getSiguienteAdy();
        }
        return retorno;
    }

    private boolean eliminarAdyacente(NodoVerticeEtiquetado nodoA, NodoVerticeEtiquetado nodoB) {
        boolean res = false;
        if (nodoA != null) {
            NodoAdyacenteEtiquetado adyacente = nodoA.getAdyacente();
            if (adyacente.getVertice().equals(nodoB)) {
                nodoA.setAdyacente(adyacente.getSiguienteAdy());
                res = true;
            } else {
                NodoAdyacenteEtiquetado siguienteAdyacente = adyacente.getSiguienteAdy();
                while (siguienteAdyacente != null && !siguienteAdyacente.getVertice().equals(nodoB)) {
                    siguienteAdyacente = siguienteAdyacente.getSiguienteAdy();
                    adyacente = adyacente.getSiguienteAdy();
                }
                if (siguienteAdyacente != null) {
                    adyacente.setSiguienteAdy(siguienteAdyacente.getSiguienteAdy());
                    res = true;
                }
            }
        }
        return res;
    }//check

    public boolean insertarArco(Object elemA, Object elemB, double etiqueta) {
        boolean retorno = false;
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);// ubica los nodos con hash
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);
        if (verticeA != null && verticeB != null) {
            this.insertarAdyacente(verticeA, verticeB, etiqueta);
            this.insertarAdyacente(verticeB, verticeA, etiqueta);
            retorno = true;
        }
        return retorno;
    }//check

    public boolean eliminarArco(Object elemA, Object elemB) {
        boolean retorno = false;
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);
        if (verticeA != null && verticeB != null) {
            this.eliminarAdyacente(verticeA, verticeB);
            this.eliminarAdyacente(verticeB, verticeA);
            retorno = true;
        }

        return retorno;
    }//check

    public Object getEtiquetaArco(Object elemA, Object elemB) {
        Object res = null;
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);
        if (verticeA != null && verticeB != null) {//verifico que los 2 nodos existen
            NodoAdyacenteEtiquetado aux = verticeA.getAdyacente();
            while (aux != null && res == null) {
                if (aux.getVertice().equals(verticeB)) {
                    res = aux.getEtiqueta();
                }
                aux = aux.getSiguienteAdy();
            }
            //return res;
        }
        return res;
    }//check

    public boolean setEtiquetaArco(Object elemA, Object elemB, double etiquetaNueva) {
        boolean res = false;
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);
        if (verticeA != null && verticeB != null) {
            res = this.setEtiquetaAdyacenteAux(verticeA, verticeB, etiquetaNueva);
        }
        return res;
    }

    private boolean setEtiquetaAdyacenteAux(NodoVerticeEtiquetado nodo, NodoVerticeEtiquetado adyacente, double etiquetaNueva) {
        boolean retorno = false;
        NodoAdyacenteEtiquetado aux = nodo.getAdyacente();
        boolean control = true;
        while (aux != null && control) {
            if (aux.getVertice().equals(adyacente)) {
                aux.setEtiqueta(etiquetaNueva);
                control = false;
                retorno = true;
            }
            aux = aux.getSiguienteAdy();
        }
        return retorno;
    }

    public boolean existeVertice(Object elem) {
        boolean res = false;
        NodoVerticeEtiquetado aux = this.inicio;
        while (aux != null && !res) {
            if (aux.getElemento().equals(elem)) {
                res = true;
            }
            aux = aux.getSigVertice();
        }
        return res;
    }

    public Object recuperarVertice(Object elem) {
        Object retorno = null;
        NodoVerticeEtiquetado aux = this.inicio;
        while (aux != null && retorno == null) {
            if (aux.getElemento().equals(elem)) {
                retorno = aux.getElemento();
            }
            aux = aux.getSigVertice();
        }
        return retorno;
    }//check

    public boolean existeArco(Object elemA, Object elemB) {
        boolean res = false;
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);
        if (verticeA != null && verticeB != null) {
            NodoAdyacenteEtiquetado aux = verticeA.getAdyacente();
            while (aux != null && !res) {
                if (aux.getVertice().equals(verticeB)) {
                    res = true;
                }
                aux = aux.getSiguienteAdy();
            }
        }
        return res;
    }

    public boolean existeCamino(Object elemA, Object elemB) {
        boolean res = false;
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);
        if (verticeA != null && verticeB != null) {
            res = existeCaminoAux(verticeA, verticeB, new Lista());
        }
        return res;
    }

    private boolean existeCaminoAux(NodoVerticeEtiquetado inicio, NodoVerticeEtiquetado destino, Lista visitados) {
        boolean res = false;
        if (inicio != null) {
            if (inicio.getElemento().equals(destino.getElemento())) {
                res = true;
            } else {
                visitados.insertar(inicio.getElemento(), visitados.longitud() + 1);
                NodoAdyacenteEtiquetado aux = inicio.getAdyacente();
                while (!res && aux != null) {
                    if (visitados.localizar(aux.getVertice().getElemento()) < 0) {// se pregunta si ya se paso por el nodo, si no paso, continua
                        res = existeCaminoAux(aux.getVertice(), destino, visitados); // recursividad
                    }
                    aux = aux.getSiguienteAdy();
                }
            }
        }
        return res;
    }//check

    public Lista caminoMasCorto(Object elemA, Object elemB) {
        Lista camino = new Lista();
        HashMap<String, Object> visitados = new HashMap<>(); //hash vacio para los nodos NodosVisitados
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);// busca los 2 nodos
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);
        if (verticeA != null && verticeB != null) {
            camino = this.caminoMasCortoAux(verticeA, verticeB, visitados, camino, new Lista()); // modulo recursivo
        }
        return camino;
    }

    private Lista caminoMasCortoAux(NodoVerticeEtiquetado inicio, NodoVerticeEtiquetado destino, HashMap<String, Object> nodosVisitados, Lista camino, Lista comparar) {
        if (inicio != null) {
            nodosVisitados.put(inicio.getElemento().toString(), inicio);// coloca nodo en hash
            camino.insertar(inicio.getElemento(), camino.longitud() + 1);// inseta nodo en lista camino
            if (comparar.esVacia() || camino.longitud() < comparar.longitud()) {// si listaCaminosPosibles es vacia o si camino es menor a listaCaminosPosibles
                if (inicio.equals(destino)) {// si llego a destino
                    comparar = camino.clone();
                } else {// si no llego a destino
                    NodoAdyacenteEtiquetado nodoAdyAux = inicio.getAdyacente(); // puntero a los ady del nodo actual
                    while (nodoAdyAux != null) {
                        if (nodosVisitados.get(nodoAdyAux.getVertice().getElemento().toString()) == null) {// se pregunta en el hash si se paso por el nodoAdy 
                            comparar = this.caminoMasCortoAux(nodoAdyAux.getVertice(), destino, nodosVisitados, camino, comparar); // recursividad
                        }
                        nodoAdyAux = nodoAdyAux.getSiguienteAdy();
                    }
                }
            }
            nodosVisitados.remove(inicio.getElemento().toString());// se quita el nodo actual del hash
            camino.eliminar(camino.localizar(inicio.getElemento()));// se quita el nodo actual de la lista
        }
        return comparar;
    } //check

    public Lista caminoMasLargo(Object elemA, Object elemB) {
        Lista camino = new Lista();
        HashMap<String, Object> visitados = new HashMap<>();
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);
        if (verticeA != null && verticeB != null) {
            camino = this.caminoMasLargoAux(verticeA, verticeB, visitados, camino, new Lista());
        }
        return camino;
    }

    private Lista caminoMasLargoAux(NodoVerticeEtiquetado inicio, NodoVerticeEtiquetado destino, HashMap<String, Object> visitados, Lista camino, Lista comparar) {
        if (inicio != null) {
            visitados.put(inicio.getElemento().toString(), inicio);
            camino.insertar(inicio.getElemento(), camino.longitud() + 1);
            if (comparar.esVacia() || camino.longitud() >= comparar.longitud()) {
                if (inicio.equals(destino)) {
                    comparar = camino.clone();
                } else {
                    NodoAdyacenteEtiquetado nodoAdyAux = inicio.getAdyacente();
                    while (nodoAdyAux != null) {
                        if (visitados.get(nodoAdyAux.getVertice().getElemento().toString()) == null) {
                            comparar = this.caminoMasLargoAux(nodoAdyAux.getVertice(), destino, visitados, camino, comparar);
                        }
                        nodoAdyAux = nodoAdyAux.getSiguienteAdy();
                    }
                }
            }
            visitados.remove(inicio.getElemento().toString());
            camino.eliminar(camino.localizar(inicio.getElemento()));
        }
        return comparar;
    }//check

    public Lista caminoConMasPeso(Object elemA, Object elemB) {

        Object[] arreglo = new Object[2];
        arreglo[0] = new Lista();
        arreglo[1] = 0.0;
        HashMap<String, NodoVerticeEtiquetado> visitados = new HashMap<>();
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);
        if (verticeA != null && verticeB != null) {
            arreglo = this.caminoConMasPesoAux(verticeA, verticeB, visitados, new Lista(), 0, 0, arreglo);
        }
        return ((Lista) arreglo[0]);
    }

    private Object[] caminoConMasPesoAux(NodoVerticeEtiquetado inicio, NodoVerticeEtiquetado destino, HashMap<String, NodoVerticeEtiquetado> visitados, Lista camino, double peso, double comparar, Object[] arreglo) {
        if (inicio != null) {
            visitados.put(inicio.getElemento().toString(), inicio);
            camino.insertar(inicio.getElemento(), camino.longitud() + 1);
            if (inicio.equals(destino)) {// si llega a destino
                double sumaMasKm = peso;
                if (((Double) arreglo[1]) < peso) {
                    arreglo[0] = (Lista) camino.clone();
                    arreglo[1] = sumaMasKm;
                }
            } else {
                NodoAdyacenteEtiquetado nodoAdyAux = inicio.getAdyacente();
                while (nodoAdyAux != null) {
                    peso += nodoAdyAux.getEtiqueta();
                    if (visitados.get(nodoAdyAux.getVertice().getElemento().toString()) == null) {// si aun no paso por el nodo
                        arreglo = this.caminoConMasPesoAux(nodoAdyAux.getVertice(), destino, visitados, camino,
                                peso, comparar, arreglo);
                    }
                    peso -= nodoAdyAux.getEtiqueta();
                    nodoAdyAux = nodoAdyAux.getSiguienteAdy();
                }
            }
            visitados.remove(inicio.getElemento().toString());
            camino.eliminar(camino.localizar(inicio.getElemento()));
        }
        return arreglo;
    }//check

    public Lista caminoConMenosPeso(Object elemA, Object elemB) {
        Object[] arreglo = new Object[2];
        arreglo[0] = new Lista();
        arreglo[1] = 0.0;
        HashMap<String, NodoVerticeEtiquetado> visitados = new HashMap<>();
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);
        if (verticeA != null && verticeB != null) {
            arreglo = this.caminoConMenosPesoAux(verticeA, verticeB, visitados, new Lista(), 0, arreglo);
        }
        return ((Lista) arreglo[0]);
    }

    private Object[] caminoConMenosPesoAux(NodoVerticeEtiquetado inicio, NodoVerticeEtiquetado destino,
            HashMap<String, NodoVerticeEtiquetado> visitados, Lista camino, double peso, Object[] arreglo) {
        if (inicio != null) {
            visitados.put(inicio.getElemento().toString(), inicio);
            camino.insertar(inicio.getElemento(), camino.longitud() + 1);
            if (inicio.equals(destino)) {// si llega a destino
                double sumaMasKm = peso;
                if (((Double) arreglo[1]) > peso || ((Double) arreglo[1] == 0.0)) {
                    arreglo[0] = (Lista) camino.clone();
                    arreglo[1] = sumaMasKm;
                }
            } else {
                NodoAdyacenteEtiquetado nodoAdyAux = inicio.getAdyacente();
                while (nodoAdyAux != null) {
                    peso += nodoAdyAux.getEtiqueta();
                    if (visitados.get(nodoAdyAux.getVertice().getElemento().toString()) == null) {// si aun no paso por el nodo
                        if (((peso <= (Double) arreglo[1]) || ((Double) arreglo[1] == 0.0))) {
                            arreglo = this.caminoConMenosPesoAux(nodoAdyAux.getVertice(), destino, visitados, camino,
                                    peso, arreglo);
                        }
                    }
                    peso -= nodoAdyAux.getEtiqueta();
                    nodoAdyAux = nodoAdyAux.getSiguienteAdy();
                }
            }
            visitados.remove(inicio.getElemento().toString());
            camino.eliminar(camino.localizar(inicio.getElemento()));
        }
        return arreglo;
    }//check

    public Lista caminos(Object elemA, Object elemB) {
        Lista camino = new Lista();
        HashMap<String, Object> visitados = new HashMap<>();
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);
        if (verticeA != null && verticeB != null) {
            camino = this.caminosAux(verticeA, verticeB, visitados);
        }
        return camino;
    }

    private Lista caminosAux(NodoVerticeEtiquetado nodo, NodoVerticeEtiquetado destino, HashMap<String, Object> visitados) {
        Lista retorno = new Lista();
        if (nodo != null) {
            visitados.put(nodo.getElemento().toString(), nodo);
            if (nodo.equals(destino)) {
                Lista lista = new Lista();
                lista.insertar(nodo.getElemento(), 1);
                retorno.insertar(lista, 1);
            } else {
                NodoAdyacenteEtiquetado adyacente = nodo.getAdyacente();
                while (adyacente != null) {
                    if (visitados.get(adyacente.getVertice().getElemento().toString()) == null) {
                        Lista listaRetorno = caminosAux(adyacente.getVertice(), destino, visitados);
                        retorno = this.concatenar(listaRetorno, retorno);
                    }
                    adyacente = adyacente.getSiguienteAdy();
                }
                int i = 1;
                while (i <= retorno.longitud()) {
                    Lista lista = (Lista) retorno.recuperar(i);
                    retorno.eliminar(i);
                    lista.insertar(nodo.getElemento(), 1);
                    retorno.insertar(lista, i);
                    i++;
                }
            }
            visitados.remove(nodo.getElemento().toString());
        }
        return retorno;
    }

    private Lista concatenar(Lista l1, Lista l2) {
        Lista retorno = new Lista();
        int i = 1;

        while (i <= l1.longitud() + l2.longitud()) {
            if (i <= l1.longitud()) {
                retorno.insertar(l1.recuperar(i), i);
            } else {
                retorno.insertar(l2.recuperar(i - l1.longitud()), i - l1.longitud());
            }
            i++;
        }
        return retorno;
    }

    public Lista menorCaminoQueTiene(Object elemA, Object elemB, Object elemC) {
        /**
         * lista el camino de A a C que pasa por B, con respecto a la menor
         * cantidad de nodos recorridos, No por etiqueta
         */
        Lista listaCamino = new Lista();
        HashMap<String, NodoVerticeEtiquetado> NodosVisitados = new HashMap<>();
        NodoVerticeEtiquetado aux = this.inicio;
        NodoVerticeEtiquetado verticeA = null;
        NodoVerticeEtiquetado verticeB = null;
        NodoVerticeEtiquetado verticeC = null;
        boolean corte = false;

        while (aux != null && !corte) {
            if (aux.getElemento().equals(elemA)) {
                verticeA = aux;
            } else if (aux.getElemento().equals(elemB)) {
                verticeB = aux;
            } else if (aux.getElemento().equals(elemC)) {
                verticeC = aux;
            }
            corte = verticeA != null && verticeB != null && verticeC != null;
            aux = aux.getSigVertice();
        }
        if (verticeA != null && verticeB != null && verticeC != null) {
            listaCamino = this.menorCaminoQueTieneAux(verticeA, verticeB, verticeC, NodosVisitados, new Lista(), listaCamino);
        }
        return listaCamino;
    }

    private Lista menorCaminoQueTieneAux(NodoVerticeEtiquetado inicio, NodoVerticeEtiquetado destino, NodoVerticeEtiquetado nodoC, HashMap<String, NodoVerticeEtiquetado> nodosVisitados, Lista camino, Lista comparar) {
        if (inicio != null) {
            nodosVisitados.put(inicio.getElemento().toString(), inicio);// coloca nodo en hash
            camino.insertar(inicio.getElemento(), camino.longitud() + 1);// inseta nodo en lista camino
            if (comparar.esVacia() || camino.longitud() < comparar.longitud()) {// si listaCaminosPosibles es vacia o si camino es menor a listaCaminosPosibles
                if (inicio.equals(destino)) {// si llego a destino
                    if (camino.localizar(nodoC.getElemento()) != -1) {
                        comparar = camino.clone();
                    }
                } else {// si no llego a destino
                    NodoAdyacenteEtiquetado nodoAdyAux = inicio.getAdyacente(); // puntero a los ady del nodo actual
                    while (nodoAdyAux != null) {
                        if (nodosVisitados.get(nodoAdyAux.getVertice().getElemento().toString()) == null) {// se pregunta en el hash si se paso por el nodoAdy 
                            comparar = this.menorCaminoQueTieneAux(nodoAdyAux.getVertice(), destino, nodoC, nodosVisitados, camino, comparar); // recursividad
                        }
                        nodoAdyAux = nodoAdyAux.getSiguienteAdy();
                    }
                }
            }
            nodosVisitados.remove(inicio.getElemento().toString());// se quita el nodo actual del hash
            camino.eliminar(camino.localizar(inicio.getElemento()));// se quita el nodo actual de la lista
        }
        return comparar;
    } //check

    public Lista listaConCaminosQueNoTieneC(Object elemA, Object elemB, Object elemC) {
        /**
         * lista todos los caminos de A a B que NO pasen por C
         */
        Lista listaCaminosPosibles = new Lista();
        HashMap<String, NodoVerticeEtiquetado> NodosVisitados = new HashMap<>();
        NodoVerticeEtiquetado aux = this.inicio;
        NodoVerticeEtiquetado verticeA = null;
        NodoVerticeEtiquetado verticeB = null;
        NodoVerticeEtiquetado verticeC = null;
        boolean corte = false;

        while (aux != null && !corte) {
            if (aux.getElemento().equals(elemA)) {
                verticeA = aux;
            } else if (aux.getElemento().equals(elemB)) {
                verticeB = aux;
            } else if (aux.getElemento().equals(elemC)) {
                verticeC = aux;
            }
            corte = verticeA != null && verticeB != null && verticeC != null;
            aux = aux.getSigVertice();
        }
        if (verticeA != null && verticeB != null && verticeC != null) {
            listaCaminosPosibles = listaConCaminosQueNoTieneAux(verticeA, verticeB, verticeC, NodosVisitados, new Lista(), listaCaminosPosibles);
        }
        return listaCaminosPosibles;
    }

    private Lista listaConCaminosQueNoTieneAux(NodoVerticeEtiquetado inicio, NodoVerticeEtiquetado destino,
            NodoVerticeEtiquetado nodoC, HashMap<String, NodoVerticeEtiquetado> nodosVisitados, Lista camino,
            Lista listaCaminosPosibles) {
        if (inicio != null) {
            nodosVisitados.put(inicio.getElemento().toString(), inicio);// coloca nodo en hash
            camino.insertar(inicio.getElemento(), camino.longitud() + 1);// inseta nodo en lista camino
            if (inicio.equals(destino)) {// si llego a destino
                if (camino.localizar(nodoC.getElemento()) == -1) {
                    if (listaCaminosPosibles.existeObjeto(camino)==false) {
                        listaCaminosPosibles.insertar((Lista) camino.clone(), listaCaminosPosibles.longitud() + 1);
                    }
                }
            } else {// si no llego a destino
                NodoAdyacenteEtiquetado nodoAdyAux = inicio.getAdyacente(); // puntero a los ady del nodo actual
                while (nodoAdyAux != null) {
                    if (nodosVisitados.get(nodoAdyAux.getVertice().getElemento().toString()) == null) {// se pregunta en el hash si se paso por el nodoAdy 
                        listaCaminosPosibles = listaConCaminosQueNoTieneAux(nodoAdyAux.getVertice(), destino, nodoC, nodosVisitados, camino, listaCaminosPosibles); // recursividad
                    }
                    nodoAdyAux = nodoAdyAux.getSiguienteAdy();
                }
            }
            nodosVisitados.remove(inicio.getElemento().toString());// se quita el nodo actual del hash
            camino.eliminar(camino.localizar(inicio.getElemento()));// se quita el nodo actual de la lista
        }
        return listaCaminosPosibles;
    } //check

    public Lista caminoMasRapidoQuePasa(Object elemA, Object elemB, Object elemC) {
        HashMap<String, NodoVerticeEtiquetado> visitados = new HashMap<>();
        Object[] arreglo = new Object[2];
        arreglo[0] = new Lista();
        arreglo[1] = 0.0;
        NodoVerticeEtiquetado aux = this.inicio;
        NodoVerticeEtiquetado verticeA = null;
        NodoVerticeEtiquetado verticeB = null;
        NodoVerticeEtiquetado verticeC = null;
        boolean corte = false;
        while (aux != null && !corte) {
            if (aux.getElemento().equals(elemA)) {
                verticeA = aux;
            } else if (aux.getElemento().equals(elemB)) {
                verticeB = aux;
            } else if (aux.getElemento().equals(elemC)) {
                verticeC = aux;
            }
            corte = verticeA != null && verticeB != null && verticeC != null;
            aux = aux.getSigVertice();
        }
        if (verticeA != null && verticeB != null && verticeC != null) {
            arreglo = this.caminoMasRapidoQuePasaAux(verticeA, verticeB, verticeC, visitados, new Lista(), 0.0, arreglo);
        }
        System.out.println("km: " + ((double) arreglo[1]));
        return ((Lista) arreglo[0]);
    }

    private Object[] caminoMasRapidoQuePasaAux(NodoVerticeEtiquetado inicio, NodoVerticeEtiquetado destino, NodoVerticeEtiquetado nodoC,
            HashMap<String, NodoVerticeEtiquetado> visitados, Lista camino, double peso, Object[] arreglo) {
        if (inicio != null) {
            visitados.put(inicio.getElemento().toString(), inicio);
            camino.insertar(inicio.getElemento(), camino.longitud() + 1);
            if (inicio.equals(destino)) {// si llega a destino
                if (camino.localizar(nodoC.getElemento()) != -1) {
                    double sumaMasKm = peso;
                    if (((Double) arreglo[1]) > peso || ((Double) arreglo[1] == 0.0)) {
                        arreglo[0] = (Lista) camino.clone();
                        arreglo[1] = sumaMasKm;
                    }
                }
            } else {
                NodoAdyacenteEtiquetado nodoAdyAux = inicio.getAdyacente();
                while (nodoAdyAux != null) {
                    peso += nodoAdyAux.getEtiqueta();
                    if (visitados.get(nodoAdyAux.getVertice().getElemento().toString()) == null) {// si aun no paso por el nodo
                        if (((peso <= (Double) arreglo[1]) || ((Double) arreglo[1] == 0.0))) {
                            arreglo = this.caminoMasRapidoQuePasaAux(nodoAdyAux.getVertice(), destino, nodoC, visitados, camino, peso, arreglo);
                        }
                    }
                    peso -= nodoAdyAux.getEtiqueta();
                    nodoAdyAux = nodoAdyAux.getSiguienteAdy();
                }
            }
            visitados.remove(inicio.getElemento().toString());
            camino.eliminar(camino.localizar(inicio.getElemento()));
        }
        return arreglo;
    }//check

    //preguntar concepto del metodo
    public Lista caminoConTopeDeVertices(Object elemA, Object elemB, int cantidadVertices) {
        Lista comparar = new Lista();
        HashMap<String, NodoVerticeEtiquetado> visitados = new HashMap<>();
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);
        if (verticeA != null && verticeB != null) {
            comparar = this.caminoConTopeDeVerticesAux(verticeA, verticeB, cantidadVertices, visitados, new Lista(), comparar);
        }
        return comparar;
    }

    private Lista caminoConTopeDeVerticesAux(NodoVerticeEtiquetado inicio, NodoVerticeEtiquetado destino, int CantVertices,
            HashMap<String, NodoVerticeEtiquetado> visitados, Lista camino, Lista comparar) {
        if (inicio != null) {
            visitados.put(inicio.getElemento().toString(), inicio);
            camino.insertar(inicio.getElemento(), camino.longitud() + 1);
            if (camino.longitud() <= CantVertices || comparar.esVacia() || camino.longitud() < comparar.longitud()) {
                if (inicio.equals(destino)) {
                    comparar = camino.clone();
                } else {
                    NodoAdyacenteEtiquetado adyacente = inicio.getAdyacente();
                    while (adyacente != null) {
                        if (visitados.get(adyacente.getVertice().getElemento().toString()) == null) {
                            comparar = this.caminoConTopeDeVerticesAux(adyacente.getVertice(), destino, CantVertices,
                                    visitados, camino, comparar);
                        }
                        adyacente = adyacente.getSiguienteAdy();
                    }
                }
            }
            visitados.remove(inicio.getElemento().toString());
            camino.eliminar(camino.localizar(inicio.getElemento()));
        }
        return comparar;
    }

    public Lista listarProfundidad() {
        Lista list = new Lista();
        HashMap<String, NodoVerticeEtiquetado> nodosVisitados = new HashMap<>();
        NodoVerticeEtiquetado nodo = this.inicio;
        while (nodo != null) {
            if (nodosVisitados.get(nodo.getElemento().toString()) == null) {
                this.listarProdundidadAux(nodo, list, nodosVisitados);
            }
            nodo = nodo.getSigVertice();
        }
        return list;
    }

    private void listarProdundidadAux(NodoVerticeEtiquetado nodo, Lista listados, HashMap<String, NodoVerticeEtiquetado> visitados) {
        visitados.put(nodo.getElemento().toString(), inicio);
        listados.insertar(nodo.getElemento(), listados.longitud() + 1);
        NodoAdyacenteEtiquetado aux = nodo.getAdyacente();
        while (aux != null) {
            if (visitados.get(aux.getVertice().getElemento().toString()) == null) {
                this.listarProdundidadAux(aux.getVertice(), listados, visitados);
            }
            aux = aux.getSiguienteAdy();
        }
    }

    public Lista listarAnchura() {
        Lista listados = new Lista();
        HashMap<String, Object> visitados = new HashMap<>();
        NodoVerticeEtiquetado nodo = this.inicio;
        while (nodo != null) {
            if (visitados.get(nodo.getElemento().toString()) == null) {
                this.listarAnchuraAux(nodo, listados, visitados);
            }
            nodo = nodo.getSigVertice();
        }
        return listados;
    }

    private void listarAnchuraAux(NodoVerticeEtiquetado nodo, Lista listados, HashMap<String, Object> visitados) {
        Cola q = new Cola();
        visitados.put(nodo.getElemento().toString(), inicio);
        q.poner(nodo);
        while (!q.esVacia()) {
            NodoVerticeEtiquetado aux = (NodoVerticeEtiquetado) q.obtenerFrente();
            listados.insertar(aux.getElemento(), listados.longitud() + 1);
            q.sacar();
            NodoAdyacenteEtiquetado adyacente = aux.getAdyacente();
            while (adyacente != null) {
                if (visitados.get(adyacente.getVertice().getElemento().toString()) == null) {
                    q.poner(adyacente.getVertice());
                    visitados.put(adyacente.getVertice().getElemento().toString(), adyacente.getVertice());
                }
                adyacente = adyacente.getSiguienteAdy();
            }
        }
    }

    public GrafoEtiquetado clone() {
        GrafoEtiquetado clon = new GrafoEtiquetado();
        NodoVerticeEtiquetado nodo = this.inicio;
        HashMap<String, NodoVerticeEtiquetado> nodos = new HashMap<>();
        if (nodo != null) {
            clon.inicio = new NodoVerticeEtiquetado(nodo.getElemento(), null, null);
            NodoVerticeEtiquetado nodoClone = clon.inicio; // puntero del grafo clonado
            nodos.put(nodo.getElemento().toString(), nodoClone);
            nodo = nodo.getSigVertice();
            while (nodo != null) {
                nodoClone.setSigVertice(new NodoVerticeEtiquetado(nodo.getElemento(), null, null));
                nodoClone = nodoClone.getSigVertice();
                nodos.put(nodoClone.getElemento().toString(), nodoClone);
                nodo = nodo.getSigVertice();
            }
            nodoClone = clon.inicio;
            nodo = this.inicio;
            while (nodoClone != null) {
                NodoAdyacenteEtiquetado adyacenteNodo = nodo.getAdyacente();
                if (adyacenteNodo != null) {
                    nodoClone.setAdyacente(
                            new NodoAdyacenteEtiquetado(nodos.get(adyacenteNodo.getVertice().getElemento().toString()),
                                    null, adyacenteNodo.getEtiqueta()));
                    NodoAdyacenteEtiquetado adyacenteClonado = nodoClone.getAdyacente();
                    adyacenteNodo = adyacenteNodo.getSiguienteAdy();
                    while (adyacenteNodo != null) {
                        adyacenteClonado.setSiguienteAdy(
                                new NodoAdyacenteEtiquetado(nodos.get(adyacenteNodo.getVertice().getElemento().toString()),
                                        null, adyacenteNodo.getEtiqueta()));
                        adyacenteNodo = adyacenteNodo.getSiguienteAdy();
                        adyacenteClonado = adyacenteClonado.getSiguienteAdy();
                    }
                }
                nodoClone = nodoClone.getSigVertice();
                nodo = nodo.getSigVertice();
            }
        }
        return clon;
    }

    @Override
    public String toString() {
        System.out.println("entro al metodo");
        String retorno = "Grafo Vacio";
        NodoVerticeEtiquetado nodo = this.inicio;
        if (nodo != null) {
            retorno = "";
            while (nodo != null) {
                NodoAdyacenteEtiquetado adyacente = nodo.getAdyacente();
                retorno += nodo.getElemento().toString() + ":";
                while (adyacente != null) {
                    retorno += "->(" + adyacente.getVertice().getElemento().toString() + "," + adyacente.getEtiqueta()
                            + ")";
                    adyacente = adyacente.getSiguienteAdy();
                }
                retorno += "\n";
                nodo = nodo.getSigVertice();
            }
        }
        return retorno;
    }

}
