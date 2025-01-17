package com.aluracursos.literalura.main;
//limitar la longitud de una cadena de texto a un número máximo de caracteres.

public class CadenaTexto {
    public static String longitudCadena(String cadenaTexto, int longitudMax){
        if (cadenaTexto.length() <= longitudMax) {
            return cadenaTexto;
        } else {
            return cadenaTexto.substring(0, longitudMax);
        }
    }
}
