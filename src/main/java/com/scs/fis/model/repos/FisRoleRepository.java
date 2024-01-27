package com.scs.fis.model.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.scs.fis.model.entities.FisRole;

public interface FisRoleRepository extends JpaRepository<FisRole, Long>, JpaSpecificationExecutor<FisRole> {
	Optional<FisRole> findById(Long id);

	Optional<FisRole> findByRole(String role);
}
