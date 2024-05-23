package com.backend.APIRest.utils;

public class Excepcion extends RuntimeException {
    public Excepcion(String message) {
        super(message);
    }
}