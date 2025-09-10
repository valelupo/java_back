package com.particulares.tp.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.particulares.tp.java.entities.DictadoClase;
import com.particulares.tp.java.entities.DictadoClaseId;

@Repository
public interface DictadoClaseRepository extends JpaRepository<DictadoClase, DictadoClaseId> {
}
