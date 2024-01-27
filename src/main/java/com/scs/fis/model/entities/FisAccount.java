package com.scs.fis.model.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fis_account")
@Getter
@Setter
public class FisAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotNull
	private String name;

	@Column(nullable = false, unique = true)
	@NotNull
	private String username;

	@Column(nullable = false, unique = true)
	@NotNull
	@Email(message = "{errors.invalid_email}")
	private String email;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "fis_account_role_map", joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<FisRole> roles;

	@NotNull
	@Pattern(regexp = "(^[0-9]{10}$)")
	@Column(name = "mobile_phone")
	private String mobilePhone;

	@NotNull
	@Size(min = 2, max = 256)
	private String password;

	private boolean enabled;

	@Column(name = "mobile_verified")
	private boolean mobileVerified;

	private boolean expired;
	private boolean locked;

	@Column(name = "credentials_expired")
	private boolean credentialsExpired;

	@Column(name = "last_login")
	private LocalDateTime lastLogin;

	@Column(name = "created_on", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
	private LocalDateTime createdOn;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "updated_on")
	private LocalDateTime updatedOn;

	@Column(name = "updated_by")
	private Long updatedBy;

	private boolean approved;

	private boolean x;

	@Transient
	private int lastLoginYear;

	@Transient
	private int lastLoginDay;

	@Transient
	private String lastLoginMonth;

	@Transient
	private String lastLoginTime;

	@Transient
	private String lastLoginFormatted;

	@Transient
	private int createdYear;

	@Transient
	private int createdMonth;

	@Transient
	private int createdDay;

	@Transient
	private String createdStrMonth;

	@Transient
	private String createdOnFormatted;

	public FisAccount() {
	}

	public FisAccount(long accountId) {
		this.id = accountId;
	}

	@PrePersist
	private void onCreate() {
		setCreatedOn(LocalDateTime.now());
	}

	@PostLoad
	private void onLoad() {
		this.createdYear = createdOn.getYear();
		this.createdMonth = createdOn.getMonthValue();
		this.createdDay = createdOn.getDayOfMonth();
		this.createdStrMonth = createdOn.getMonth().toString();
		this.createdOnFormatted = this.createdStrMonth + " " + this.createdDay + ", " + this.createdYear;

		if (lastLogin != null) {
			this.lastLoginYear = lastLogin.getYear();
			this.lastLoginDay = lastLogin.getDayOfMonth();
			this.lastLoginMonth = lastLogin.getMonth().toString();
			this.lastLoginTime = lastLogin.getHour() + ":" + lastLogin.getMinute() + ":" + lastLogin.getSecond();
			this.lastLoginFormatted = this.lastLoginMonth + " " + this.lastLoginDay + ", " + this.lastLoginYear + " at "
					+ lastLoginTime;
		}
	}
}
