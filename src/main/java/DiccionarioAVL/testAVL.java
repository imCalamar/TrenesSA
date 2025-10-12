/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DiccionarioAVL;

/**
 *
 * @author JoaquinNoillet
 */
public class testAVL {

    public static void main(String[] args) {
        Diccionario prueba = new Diccionario();

        prueba.insertar("argentina", "arg");
        prueba.insertar("alemania", "alem");
        prueba.insertar("brasil", "bra");
        prueba.insertar("costa Rica", "cR");
        prueba.insertar("colombia", "colom");
        prueba.insertar("senegal", "seneg");
        System.out.println("");
        System.out.println("A: "+ prueba.listarClaves().toString());
        System.out.println("");
        System.out.println("B: "+ prueba.listarElementos().toString());

        System.out.println(prueba.listarRango("az", "zzzzzz"));
        
        System.out.println(prueba.toString());

    }
}
