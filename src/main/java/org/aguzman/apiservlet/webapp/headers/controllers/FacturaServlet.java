package org.aguzman.apiservlet.webapp.headers.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aguzman.apiservlet.webapp.headers.models.Carro;
import org.aguzman.apiservlet.webapp.headers.models.ItemCarro;
import org.aguzman.apiservlet.webapp.headers.models.entities.Factura;
import org.aguzman.apiservlet.webapp.headers.models.entities.ItemFactura;
import org.aguzman.apiservlet.webapp.headers.services.FacturaService;

import java.io.IOException;

@WebServlet("/crearFactura")
public class FacturaServlet extends HttpServlet {

    @Inject
    private FacturaService facturaService;

    @Inject
    private Carro carro;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Factura factura = new Factura();
        for (ItemCarro item : carro.getItems()) {
            ItemFactura itemFactura = new ItemFactura();
            itemFactura.setProducto(item.getProducto());
            itemFactura.setCantidad(item.getCantidad());
            itemFactura.setPrecio(item.getProducto().getPrecio() * item.getCantidad());
            factura.getItems().add(itemFactura);
        }
        facturaService.guardarFactura(factura);
        request.setAttribute("facturaId", factura.getId());
        request.getRequestDispatcher("/confirmacionFactura.jsp").forward(request, response);
    }
}
