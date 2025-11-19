package com.railconnect.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

// Done

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "roles")
@Table(name = "user")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_Id")
	private int id;

	@NotBlank(message = "UserName cannot be blank")
	@Size(min = 4, max = 20, message = "UserName must be 4 and 20 characters")
	@Column(nullable = false)
	private String fullName;

	@NotBlank(message = "email cannot be blank")
	@Column(nullable = false, unique = true)
	@Email
	private String email;

	@NotBlank(message = 	"Password not blank")
	@Size(min = 6, message = "Password must be at least 6 characters long")
	@Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@JsonIgnoreProperties("users") // ignore the 'users' field inside Role
	@ToString.Exclude
	private Set<Roles> roles = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//	@JsonManagedReference(value = "user-bookings")
	@JsonIgnore
	@ToString.Exclude
	private List<Booking> booking = new ArrayList<>();
	
	@OneToMany(mappedBy = "createdBy")
	@ToString.Exclude
	@JsonIgnore
	private List<Train> trains = new ArrayList<>();
	
	@OneToMany(mappedBy = "updatedBy")
	@ToString.Exclude
	@JsonIgnore
	private List<Train> updatedTrains = new ArrayList<>();

	

}
