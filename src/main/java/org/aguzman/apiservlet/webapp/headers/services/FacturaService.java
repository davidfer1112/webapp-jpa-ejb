package org.aguzman.apiservlet.webapp.headers.services;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aguzman.apiservlet.webapp.headers.models.entities.Factura;

@Stateless
public class FacturaService {

    @PersistenceContext(unitName = "ejemploJpa")
    private EntityManager em;

    public void guardarFactura(Factura factura) {
        em.persist(factura);
    }

    public Factura obtenerFactura(Long id) {
        return em.find(Factura.class, id);
    }
}
