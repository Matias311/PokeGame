package com.pokegame.app;

import com.pokegame.app.modelo.AdmEquipo;
import com.pokegame.app.repository.AdmEquipoRepository;
import com.pokegame.app.repository.implementacion.AdmEquipoRepositoryImpl;

public class TestCrearEquipo {
    public static void main(String[] args) {
        AdmEquipoRepository repo = new AdmEquipoRepositoryImpl();

        // Cambia estos valores si ya existen
        String nombre = "Equipo Prueba";
        int idCliente = 1; // Asegúrate de que exista este cliente

        AdmEquipo nuevoEquipo = new AdmEquipo(nombre, idCliente);
        boolean exito = repo.crearEquipo(nuevoEquipo);

        if (exito) {
            System.out.println("✅ Equipo creado correctamente.");
        } else {
            System.out.println("❌ Error al crear el equipo.");
        }
    }
}