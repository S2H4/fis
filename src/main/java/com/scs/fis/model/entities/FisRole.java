package com.scs.fis.model.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fis_role")
@Getter
@Setter
public class FisRole {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	@NotEmpty
	private String role;

	@Column(name = "created_on", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
	private LocalDateTime createdOn;

	// Notice that we use "roles" not the table name of "role". "roles" is from
	// Company.roles.
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "roles")
	private List<FisAccount> accounts;

	protected FisRole() {
	}

	public FisRole(Long roleId) {
		this.id = roleId;
	}
}
