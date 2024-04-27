package org.aguzman.apiservlet.webapp.headers.services;

public class UbicacionService {

    // Método para simular la obtención de la ubicación del usuario
    public static double[] getUserLocation() {
        // coordenadas aleatorias entre -100 y 100
        double[] coordinates = new double[2];
        coordinates[0] = Math.random() * 200 - 100; // Coordenada X
        coordinates[1] = Math.random() * 200 - 100; // Coordenada Y
        return coordinates;
    }

    // Método para calcular la distancia entre dos puntos en el plano cartesiano
    public static double getDistance(double[] p1, double[] p2) {
        // fórmula de distancia euclidiana: sqrt((x2 - x1)^2 + (y2 - y1)^2)
        double deltaX = p2[0] - p1[0];
        double deltaY = p2[1] - p1[1];
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}
