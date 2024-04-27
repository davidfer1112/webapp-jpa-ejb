package org.aguzman.apiservlet.webapp.headers.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.aguzman.apiservlet.webapp.headers.configs.ProductoServicePrincipal;
import org.aguzman.apiservlet.webapp.headers.models.Carro;
import org.aguzman.apiservlet.webapp.headers.models.ItemCarro;
import org.aguzman.apiservlet.webapp.headers.models.entities.Factura;
import org.aguzman.apiservlet.webapp.headers.models.entities.ItemFactura;
import org.aguzman.apiservlet.webapp.headers.services.EnviarEmailService;
import org.aguzman.apiservlet.webapp.headers.services.FacturaService;
import org.aguzman.apiservlet.webapp.headers.services.PagoService;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/pago")
public class PagoServlet extends HttpServlet {
    @Inject
    @ProductoServicePrincipal
    private PagoService pagoService;


    @Inject
    private FacturaService facturaService;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        boolean pagoExitoso ;
        Carro carro = (Carro) session.getAttribute("carro");

        if (carro == null || carro.getItems().isEmpty()) {
           resp.sendRedirect(req.getContextPath() + "/confirmacionFactura.jsp");
            System.out.printf("carro no esta vacio");
        } else {
            //Obtenemos datos
            String nombreTarjeta = req.getParameter("nombreTarjeta");
            String numeroTarjeta = req.getParameter("numeroTarjeta");
            String fechaExpiracion = req.getParameter("fechaExpiracion");
            String cvv = req.getParameter("cvv");

            double tarifaEnvio = PagoService.calcularTarifaEnvio();

            try {
                pagoExitoso = pagoService.procesarPago(numeroTarjeta, fechaExpiracion, cvv, carro.getTotal());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            // envia el total, impuesto y tarifaEnvio por medio de la sesion
            session.setAttribute("total", carro.getTotal());
            session.setAttribute("impuesto", carro.getImpuesto());
            session.setAttribute("tarifaEnvio", tarifaEnvio);
            System.out.printf("pago exitoso: %b");

            if (pagoExitoso) {

               // resp.sendRedirect(req.getContextPath() + "/confirmacion.jsp");
                Factura factura = new Factura();
                for (ItemCarro item : carro.getItems()) {
                    ItemFactura itemFactura = new ItemFactura();
                    itemFactura.setProducto(item.getProducto());
                    itemFactura.setCantidad(item.getCantidad());
                    itemFactura.setPrecio(item.getProducto().getPrecio());
                    factura.getItems().add(itemFactura);
                }
                facturaService.guardarFactura(factura);

               // session.removeAttribute("carro"); // Limpiar el carro después de crear la factura

                resp.sendRedirect(req.getContextPath() + "/crearFactura");
                System.out.printf("Creacion de factura");

                //guardar pago en base de datos
            } else {
                //mensaje error
                resp.sendRedirect(req.getContextPath() + "/pago.jsp?error=1&productos=" + obtenerProductosEnCarro(carro));
            }
        }

    }
    private String obtenerProductosEnCarro(Carro carro) {
        StringBuilder productos = new StringBuilder();
        List<ItemCarro> items = carro.getItems();
        for (ItemCarro item : items) {
            productos.append("Nombre: ").append(item.getProducto().getNombre());
            productos.append(", Precio: ").append(item.getProducto().getPrecio());
            productos.append(", Cantidad: ").append(item.getCantidad()).append("<br>");
        }
        return productos.toString();
    }
}
