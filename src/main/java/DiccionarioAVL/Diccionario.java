package DiccionarioAVL;

import lineales.dinamicas.Lista;

/**
 *
 * @author CalamarCoder
 */
public class Diccionario {

    private NodoDiccionario raiz;

    //Constructor
    public Diccionario() {
        raiz = null;
    }

    public boolean insertar(Object elemento, Comparable clave) {
        //Inserta un elemento y lo acomoda en el arbol lexicograficamente
        NodoDiccionario nuevo = new NodoDiccionario(elemento, clave);
        boolean ver = true;
        if (this.esVacio()) {
            this.raiz = nuevo;
        } else {
            ver = insertarRec(this.raiz, nuevo);
            this.raiz = this.rebalancear(this.raiz);
        }
        return ver;
    }

    private boolean insertarRec(NodoDiccionario raizRec, NodoDiccionario elemento) {
        boolean ver = false;
        if (raizRec != null) {
            if (raizRec.getClave().compareTo(elemento.getClave()) > 0) {
                if (raizRec.getIzq() == null) {
                    raizRec.setIzq(elemento);
                    ver = true;
                } else {
                    ver = insertarRec(raizRec.getIzq(), elemento);
                    raizRec.setIzq(this.rebalancear(raizRec.getIzq()));
                }
            } else if (raizRec.getClave().compareTo(elemento.getClave()) < 0) {
                if (raizRec.getDer() == null) {
                    raizRec.setDer(elemento);
                    ver = true;
                } else {
                    ver = insertarRec(raizRec.getDer(), elemento);
                    raizRec.setDer(this.rebalancear(raizRec.getDer()));
                }
            }
            raizRec.recalcularAltura();
        }
        return ver;
    }

    public boolean eliminar(Comparable clave) {

        boolean exito = false;
        if (!this.esVacio()) {
            if (this.raiz.getClave().equals(clave)) {
                exito = true;
                if (this.raiz.esHoja()) {
                    this.raiz = null;
                } else {
                    Object elemAux;
                    Comparable claveAux;
                    NodoDiccionario aux;
                    if (this.raiz.getDer() != null) {
                        aux = this.minRec(this.raiz.getDer());
                        elemAux = aux.getElem();
                        claveAux = aux.getClave();
                        elimRec(this.raiz, aux.getClave());
                        this.raiz.recalcularAltura();
                        this.raiz.setElem(elemAux);
                        this.raiz.setClave(claveAux);
                        this.raiz = rebalancear(this.raiz);
                    } else {
                        aux = this.maxRec(this.raiz.getIzq());
                        elemAux = aux.getElem();
                        claveAux = aux.getClave();
                        elimRec(this.raiz, aux.getClave());
                        this.raiz.recalcularAltura();
                        this.raiz.setElem(elemAux);
                        this.raiz.setClave(claveAux);
                        this.raiz = rebalancear(this.raiz);
                    }
                }
            } else {
                exito = this.elimRec(this.raiz, clave);
                this.raiz = rebalancear(this.raiz);
                this.raiz.recalcularAltura();
            }
        }
        return exito;
    }

    private boolean elimRec(NodoDiccionario raizRec, Comparable clave) {
        NodoDiccionario izq = raizRec.getIzq(), der = raizRec.getDer(), aux;
        boolean exito = false;
        Object elemAux;
        Comparable claveAux;
        if (clave.compareTo(raizRec.getClave()) > 0) {
            if (der != null) {
                if (der.getClave().equals(clave)) {
                    exito = true;
                    if (der.esHoja()) {
                        raizRec.setDer(null);
                    } else if (der.getDer() != null) {
                        aux = this.minRec(der.getDer());
                        elemAux = aux.getElem();
                        claveAux = aux.getClave();
                        elimRec(der, aux.getClave());
                        raizRec.recalcularAltura();
                        der.setElem(elemAux);
                        der.setClave(claveAux);
                        raizRec.setDer(rebalancear(der));
                    } else {
                        aux = this.maxRec(der.getIzq());
                        elemAux = aux.getElem();
                        claveAux = aux.getClave();
                        elimRec(der, aux.getClave());
                        raizRec.recalcularAltura();
                        der.setElem(elemAux);
                        der.setClave(claveAux);
                        raizRec.setDer(rebalancear(der));
                    }
                } else {
                    exito = elimRec(der, clave);
                    raizRec.setDer(rebalancear(der));
                }
            }
        } else if (izq != null) {
            if (izq.getClave().equals(clave)) {
                exito = true;
                if (izq.esHoja()) {
                    raizRec.setIzq(null);
                } else if (izq.getDer() != null) {
                    aux = this.minRec(izq.getDer());
                    elemAux = aux.getElem();
                    claveAux = aux.getClave();
                    elimRec(izq, aux.getClave());
                    raizRec.recalcularAltura();
                    izq.setElem(elemAux);
                    izq.setClave(claveAux);
                    raizRec.setIzq(rebalancear(izq));
                } else {
                    aux = this.maxRec(izq.getIzq());
                    elemAux = aux.getElem();
                    claveAux = aux.getClave();
                    elimRec(izq, aux.getClave());
                    raizRec.recalcularAltura();
                    izq.setElem(elemAux);
                    izq.setClave(claveAux);
                    raizRec.setIzq(rebalancear(izq));
                }
            } else {
                exito = elimRec(izq, clave);
                raizRec.setIzq(rebalancear(izq));
            }
        }
        return exito;
    }

    private NodoDiccionario rebalancear(NodoDiccionario raizRec) {
        NodoDiccionario nodoRet = raizRec;
        int balance;
        if (!raizRec.esHoja()) {
            balance = raizRec.getBalance();
            if (balance < -1) {
                if (raizRec.getDer().getBalance() <= 0) {
                    nodoRet = this.rotSimpleIzq(raizRec);
                } else {
                    nodoRet = this.rotDobleIzq(raizRec);
                }

            } else if (balance > 1) {
                if (raizRec.getIzq().getBalance() >= 0) {
                    nodoRet = this.rotSimpleDer(raizRec);
                } else {
                    nodoRet = this.rotDobleDer(raizRec);
                }
            }
        }
        return nodoRet;
    }

    private NodoDiccionario rotSimpleDer(NodoDiccionario raizRec) {
        //Solo se puede aplicar si el nodo pasado por parametro tiene un HI
        NodoDiccionario nuevaRaiz = new NodoDiccionario(raizRec.getIzq().getElem(), raizRec.getIzq().getClave(), raizRec.getIzq().getIzq(), raizRec);
        raizRec.setIzq(raizRec.getIzq().getDer());
        if (nuevaRaiz.getIzq() != null) {
            nuevaRaiz.getIzq().recalcularAltura();
        }
        if (nuevaRaiz.getDer() != null) {
            nuevaRaiz.getDer().recalcularAltura();
        }
        nuevaRaiz.recalcularAltura();
        raizRec.recalcularAltura();
        return nuevaRaiz;
    }

    private NodoDiccionario rotSimpleIzq(NodoDiccionario raizRec) {
        //Solo se puede aplicar si [raizRec] tiene un hijo derecho
        NodoDiccionario nuevaRaiz = new NodoDiccionario(raizRec.getDer().getElem(), raizRec.getDer().getClave(), raizRec, raizRec.getDer().getDer());
        raizRec.setDer(raizRec.getDer().getIzq());
        if (nuevaRaiz.getIzq() != null) {
            nuevaRaiz.getIzq().recalcularAltura();
        }
        if (nuevaRaiz.getDer() != null) {
            nuevaRaiz.getDer().recalcularAltura();
        }
        nuevaRaiz.recalcularAltura();
        raizRec.recalcularAltura();
        return nuevaRaiz;
    }

    private NodoDiccionario rotDobleDer(NodoDiccionario raizRec) {
        //Solo se puede aplicar si [raizRec] tiene un hijo izquierdo que tenga un hijo derecho
        raizRec.setIzq(this.rotSimpleIzq(raizRec.getIzq()));
        raizRec = this.rotSimpleDer(raizRec);
        return raizRec;
    }

    private NodoDiccionario rotDobleIzq(NodoDiccionario raizRec) {
        //Solo se puede aplicar si [raizRec] tiene un hijo derecho que tenga un hijo izquierdo
        raizRec.setDer(this.rotSimpleDer(raizRec.getDer()));
        raizRec = this.rotSimpleIzq(raizRec);
        return raizRec;
    }

    //Observadoras
    public Lista listarRango(Comparable min, Comparable max) {
        Lista ret = null;
        if ((!this.esVacio()) && (min.compareTo(max)) <= 0) {
            ret = new Lista();
            rangoRec(min, max, this.raiz, ret);
        }
        return ret;
    }

    private void rangoRec(Comparable min, Comparable max, NodoDiccionario r, Lista l) {
        int esMin, esMax;
        if (r != null) {
            esMin = r.getClave().compareTo(min);
            esMax = r.getClave().compareTo(max);
            if (esMax < 0) {
                rangoRec(min, max, r.getDer(), l);
            }
            if ((esMin >= 0) && (esMax <= 0)) {
                l.insertar(r.getElem(), 1);
            }
            if (esMin > 0) {
                rangoRec(min, max, r.getIzq(), l);
            }

        }
    }

    public Lista listarElementos() {
        Lista datos = new Lista();
        listaElementoRec(this.raiz, datos);
        return datos;
    }

    private void listaElementoRec(NodoDiccionario n, Lista datos) {
        //recorrido inorden muestra ordenadamente el arbol
        if (n != null) {
            listaElementoRec(n.getIzq(), datos);
            datos.insertar(n.getElem(), datos.longitud() + 1);
            listaElementoRec(n.getDer(), datos);
        }
    }

    public Lista listarElementosInOrdenInverso() {
        Lista datos = new Lista();
        listaElementoInOrdenInversoAux(this.raiz, datos);
        return datos;
    }

    private void listaElementoInOrdenInversoAux(NodoDiccionario n, Lista datos) {
        //recorrido inorden muestra ordenadamente el arbol
        if (n != null) {
            listaElementoInOrdenInversoAux(n.getDer(), datos);
            datos.insertar(n.getElem(), datos.longitud() + 1);
            listaElementoInOrdenInversoAux(n.getIzq(), datos);
        }
    }

    public Lista listarClaves() {
        Lista claves = new Lista();
        listarClavesAux(this.raiz, claves);
        return claves;
    }

    private void listarClavesAux(NodoDiccionario n, Lista claves) {
        //recorrido inorden muestra ordenadamente el arbol
        if (n != null) {
            System.out.println(n.getClave());
            listarClavesAux(n.getIzq(), claves);
            claves.insertar(n.getClave(), claves.longitud() + 1);
            listarClavesAux(n.getDer(), claves);
        }
    }

    public Lista listarClavesInvertido() {
        Lista claves = new Lista();
        listarClavesInvertido(this.raiz, claves);
        return claves;
    }

    private void listarClavesInvertido(NodoDiccionario n, Lista claves) {
        if (n != null) {
            listarClavesInvertido(n.getDer(), claves);
            claves.insertar(n.getClave(), claves.longitud() + 1);
            listarClavesInvertido(n.getIzq(), claves);
        }
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public Object obtenerDato(Comparable clave) {
        //Devuelve la Object con el nombre a buscar, o null si no existe
        Object resultado = null;
        if (!this.esVacio()) {
            resultado = obtenerDatoAux(clave, this.raiz);
        }
        return resultado;
    }

    private Object obtenerDatoAux(Comparable clave, NodoDiccionario raizRec) {
        Object aux = null;
        if (raizRec != null) {
            int numeroComparador = raizRec.getClave().compareTo(clave);
            if (numeroComparador == 0) {
                aux = raizRec.getElem();
            } else if (numeroComparador < 0) {
                aux = this.obtenerDatoAux(clave, raizRec.getDer());
            } else {
                aux = this.obtenerDatoAux(clave, raizRec.getIzq());
            }
        }
        return aux;
    }
          
     public String toString() {
        String s = "";
        if (this.raiz != null) {
            s = stringAux(this.raiz);
        } else {
            s = "arbol vacio";
        }
        return s;
    }
    private String stringAux(NodoDiccionario n) {
        String s = "["+ n.getAltura()+"] " + n.getElem();
        if (n.getIzq() != null) {
            s = s + " - HI: " + n.getIzq().getElem();
        } else {
            s = s + " - HI: #";
        }
        if (n.getDer() != null) {
            s = s + " - HD: " + n.getDer().getElem() + "\n";
        } else {
            s = s + " - HD: #" + "\n";
        }
        if (n.getIzq() != null) {
            s += stringAux(n.getIzq());
        }
        if (n.getDer() != null) {
            s += stringAux(n.getDer());
        }
        return s;
    }

    private NodoDiccionario minRec(NodoDiccionario raizRec) {
        NodoDiccionario aux;
        if (raizRec.getIzq() != null) {
            aux = minRec(raizRec.getIzq());
        } else {
            aux = raizRec;
        }
        return aux;
    }

    private NodoDiccionario maxRec(NodoDiccionario raizRec) {
        NodoDiccionario aux;
        if (raizRec.getDer() != null) {
            aux = maxRec(raizRec.getDer());
        } else {
            aux = raizRec;
        }
        return aux;
    }
}
