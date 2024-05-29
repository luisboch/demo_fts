package com.luis.demo.util;

public class CepUtil {

    public static String formatCep(Long cep) {
        return formatCep("" + cep);
    }

    public static String formatCep(String cep) {
        return cep.substring(0, 5) + "-" + cep.substring(5);
    }
}
