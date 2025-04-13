package com.example.tcstest.repository;

import com.example.tcstest.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByCuentaCuentaIdAndFechaBetween(Long cuentaId, LocalDate fechaInicio, LocalDate fechaFin);

    List<Movimiento> findByCuentaCuentaId(Long cuentaId);
}
