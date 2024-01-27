package com.scs.fis.model.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.scs.fis.model.entities.FisAccount;

public interface FisAccountRepository extends JpaRepository<FisAccount, Long>, JpaSpecificationExecutor<FisAccount> {
	Optional<FisAccount> findByEmail(String email);

	Optional<FisAccount> findByUsername(String userName);

	Optional<FisAccount> findByMobilePhone(String mobileNumber);

	Optional<FisAccount> findById(Long id);

	List<FisAccount> findByName(String name);
}