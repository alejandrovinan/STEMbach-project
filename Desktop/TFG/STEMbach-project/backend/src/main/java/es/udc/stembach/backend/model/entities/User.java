package es.udc.stembach.backend.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@MappedSuperclass
public class User {
	
	public enum RoleType {UDCTEACHER, STEMCOORDINATOR, CENTERSTEMCOORDINATOR, STUDENT};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "surname")
	private String surname;

	@Column(name = "secondSurname")
	private String secondSurname;

	@Column(name = "role")
	private RoleType role;

	public User() {
	}

	public User(String name, String surname, String secondSurname, RoleType role) {
		this.name = name;
		this.surname = surname;
		this.secondSurname = secondSurname;
		this.role = role;
	}

	public User(Long id, String name, String surname, String secondSurname, RoleType role) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.secondSurname = secondSurname;
		this.role = role;
	}
}
