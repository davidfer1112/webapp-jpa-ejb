package org.aguzman.apiservlet.webapp.headers.repositories;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.aguzman.apiservlet.webapp.headers.configs.Repository;
import org.aguzman.apiservlet.webapp.headers.models.entities.Tarjeta;

import java.util.List;

@RepositoryJpa
@Repository
public class TarjetasRepositoryJpaImpl implements CrudRepository<Tarjeta> {

    @Inject
    private EntityManager em;

    @Override
    public List<Tarjeta> listar() throws Exception {
        return em.createQuery("SELECT t FROM Tarjeta t", Tarjeta.class).getResultList();
    }

    @Override
    public Tarjeta porId(Long id) throws Exception {
        return em.find(Tarjeta.class, id);
    }

    @Override
    public void guardar(Tarjeta tarjeta) throws Exception {
        if (tarjeta.getId() != null && tarjeta.getId() > 0) {
            em.merge(tarjeta);
        } else {
            em.persist(tarjeta);
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        Tarjeta tarjeta = porId(id);
        if (tarjeta != null) {
            em.remove(tarjeta);
        }
    }
}
