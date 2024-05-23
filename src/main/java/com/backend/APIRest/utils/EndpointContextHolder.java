package com.backend.APIRest.utils;

public class EndpointContextHolder {
    private static final ThreadLocal<String> endpointHolder = new ThreadLocal<>();

    public static void setEndpoint(String endpoint) {
        endpointHolder.set(endpoint);
    }

    public static String getEndpoint() {
        return endpointHolder.get();
    }

    public static void clearEndpoint() {
        try {
            endpointHolder.remove();
        } catch (Exception e) {
            // Maneja cualquier excepción que pueda ocurrir al limpiar el ThreadLocal
        }
    }
}