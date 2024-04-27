package org.aguzman.apiservlet.webapp.headers.repositories;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import org.aguzman.apiservlet.webapp.headers.configs.MysqlConn;
import org.aguzman.apiservlet.webapp.headers.configs.Repository;
import org.aguzman.apiservlet.webapp.headers.models.entities.Tarjeta;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Repository
@RepositoryJdbc
public class TarjetaRepositoryJdbcImpl implements CrudRepository<Tarjeta> {

    @Inject
    private Logger log;

    @Inject
    @MysqlConn
    private Connection conn;

    @PostConstruct
    public void inicializar(){
        log.info("Inicializando el beans " + this.getClass().getName());
    }

    @PreDestroy
    public void destruir(){
        log.info("Destruyendo el beans " + getClass().getName());
    }

    @Override
    public List<Tarjeta> listar() throws SQLException {
        List<Tarjeta> tarjetas = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tarjetas")) {
            while (rs.next()) {
                Tarjeta tarjeta = getTarjeta(rs);
                tarjetas.add(tarjeta);
            }
        }
        return tarjetas;
    }

    @Override
    public Tarjeta porId(Long id) throws SQLException {
        Tarjeta tarjeta = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tarjetas WHERE id = ?")) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tarjeta = getTarjeta(rs);
                }
            }
        }
        return tarjeta;
    }

    @Override
    public void guardar(Tarjeta tarjeta) throws SQLException {
        String sql;
        if (tarjeta.getId() != null && tarjeta.getId() > 0) {
            sql = "UPDATE tarjetas SET numero=?, cvv=?, fechaExp=?, saldo=? WHERE id=?";
        } else {
            sql = "INSERT INTO tarjetas (numero, cvv, fechaExp, saldo) VALUES (?, ?, ?, ?)";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tarjeta.getNumero());
            stmt.setString(2, tarjeta.getCvv());
            stmt.setDate(3, java.sql.Date.valueOf(tarjeta.getFechaExpiracion()));
            stmt.setBigDecimal(4, tarjeta.getSaldo());
            if (tarjeta.getId() != null && tarjeta.getId() > 0) {
                stmt.setLong(5, tarjeta.getId());
            }
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM tarjetas WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private Tarjeta getTarjeta(ResultSet rs) throws SQLException {
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setId(rs.getLong("id"));
        tarjeta.setNumero(rs.getString("numero"));
        tarjeta.setCvv(rs.getString("cvv"));
        tarjeta.setFechaExpiracion(rs.getDate("fechaExp").toLocalDate());
        tarjeta.setSaldo(rs.getBigDecimal("saldo"));
        return tarjeta;
    }

    //
    public void restarSaldo(String numeroTarjeta, BigDecimal valor) throws SQLException {
        Tarjeta tarjeta = porNumero(numeroTarjeta);
        if (tarjeta != null) {
            BigDecimal saldoActual = tarjeta.getSaldo();
            BigDecimal nuevoSaldo = saldoActual.subtract(valor);
            if (nuevoSaldo.compareTo(BigDecimal.ZERO) >= 0) {
                tarjeta.setSaldo(nuevoSaldo);
                guardar(tarjeta);
            } else {
                throw new SQLException("No hay suficiente saldo en la tarjeta");
            }
        } else {
            throw new SQLException("No se encontró la tarjeta con el número especificado");
        }
    }

    public Tarjeta porNumero(String numeroTarjeta) throws SQLException {
        Tarjeta tarjeta = null;
        String sql = "SELECT * FROM tarjetas WHERE numero = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numeroTarjeta);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tarjeta = new Tarjeta();
                    tarjeta.setId(rs.getLong("id"));
                    tarjeta.setNumero(rs.getString("numero"));
                    tarjeta.setCvv(rs.getString("cvv"));
                    tarjeta.setFechaExpiracion(LocalDate.parse(rs.getString("fechaExp")));
                    tarjeta.setSaldo(rs.getBigDecimal("saldo"));
                }
            }
        }
        return tarjeta;
    }

}
