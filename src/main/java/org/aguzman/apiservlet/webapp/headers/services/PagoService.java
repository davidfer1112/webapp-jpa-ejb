package org.aguzman.apiservlet.webapp.headers.services;

import jakarta.ejb.Stateless;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.aguzman.apiservlet.webapp.headers.configs.ProductoServicePrincipal;
import org.aguzman.apiservlet.webapp.headers.configs.Service;
import org.aguzman.apiservlet.webapp.headers.models.Carro;
import org.aguzman.apiservlet.webapp.headers.models.entities.Tarjeta;
import org.aguzman.apiservlet.webapp.headers.repositories.TarjetaRepositoryJdbcImpl;

import java.math.BigDecimal;
import java.sql.SQLException;

@Service
@Stateless
@ProductoServicePrincipal
public class PagoService {
    private TarjetaRepositoryJdbcImpl tarjetaRepository;

    // Simulacion de procesamiento de pago
    public boolean procesarPago(String numeroTarjeta, String fechaExpiracion, String cvv, double valorTotalCarro) throws SQLException {
        // Validacion de tarjeta con los datos en la base de datos
        boolean tarjetaValida = validarTarjeta(numeroTarjeta, fechaExpiracion, cvv);

        if (tarjetaValida) {
            // Restar el valor total del carro al saldo de la tarjeta
            //
            HttpServletRequest request = null;
            boolean pagoExitoso = restarValorTarjeta(numeroTarjeta, BigDecimal.valueOf(valorTotalCarro), request);
            return pagoExitoso;
        }

        return false;
    }

    private boolean validarTarjeta(String numeroTarjeta, String fechaExpiracion, String cvv) throws SQLException {
        Tarjeta tarjeta = tarjetaRepository.porNumero(numeroTarjeta);

        return tarjeta != null && tarjeta.getFechaExpiracion().equals(fechaExpiracion) && tarjeta.getCvv().equals(cvv);
    }

    // Restar el valor del carro al saldo de la tarjeta
    private boolean restarValorTarjeta(String numeroTarjeta, BigDecimal totalCarro, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession();

        Carro carro = (Carro) session.getAttribute("carro");

        if (carro != null && !carro.getItems().isEmpty()) {
            Tarjeta tarjeta = tarjetaRepository.porNumero(numeroTarjeta);

            // Verificar que la tarjeta exista y tenga suficiente saldo
            if (tarjeta != null && tarjeta.getSaldo().compareTo(totalCarro) >= 0) {
                BigDecimal nuevoSaldo = tarjeta.getSaldo().subtract(totalCarro);
                tarjeta.setSaldo(nuevoSaldo);

                tarjetaRepository.restarSaldo(String.valueOf(tarjeta), totalCarro);

                return true; // Pago exitoso
            }
        }

        return false;
    }


    // Calcular tarifa de envío
    public static int calcularTarifaEnvio() {
        double distancia = UbicacionService.getDistance(UbicacionService.getUserLocation(), new double[]{0, 0});
        if (distancia < 10) {
            return 5;
        } else if (distancia < 20) {
            return 10;
        } else {
            return 20;
        }
    }
}
