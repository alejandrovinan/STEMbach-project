package es.udc.stembach.backend.rest.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto {
	
	public interface AllValidations {}
	
	public interface UpdateValidations {}

	private Long id;
	private String name;
	private String password;
	private String surname;
	private String secondSurname;
	private String email;
	private String role;
	private String dni;
	private Long facultyId;
	private Long schoolId;

	public UserDto() {}

	public UserDto(String name, String password, String surname, String secondSurname, String email, String role, String dni, Long facultyId, Long schoolId) {
		this.name = name != null ? name.trim() : null;
		this.surname = surname.trim();
		this.secondSurname = secondSurname.trim();
		this.email = email.trim();
		this.role = role;
		this.dni = dni != null ? dni.trim() : null;
		this.facultyId = facultyId != null ? facultyId : null;
		this.schoolId = schoolId != null ? schoolId : null;
	}

	public UserDto(Long id, String name, String surname, String secondSurname, String role) {
		this.id = id;
		this.name = name != null ? name.trim() : null;
		this.surname = surname.trim();
		this.secondSurname = secondSurname.trim();
		this.role = role;
	}

	public UserDto(Long id, String name, String surname, String secondSurname, String email, String role, String dni, Long facultyId, Long schoolId) {

		this.id = id;
		this.name = name != null ? name.trim() : null;
		this.surname = surname.trim();
		this.secondSurname = secondSurname.trim();
		this.email = email.trim();
		this.role = role;
		this.dni = dni != null ? dni.trim() : null;
		this.facultyId = facultyId != null ? facultyId : null;
		this.schoolId = schoolId != null ? schoolId : null;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull(groups={AllValidations.class})
	@Size(min=1, max=60, groups={AllValidations.class})
	public String getName() {
		return name;
	}

	public void setName(String userName) {
		this.name = userName.trim();
	}

	@NotNull(groups={AllValidations.class})
	@Size(min=1, max=60, groups={AllValidations.class})
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotNull(groups={AllValidations.class, UpdateValidations.class})
	@Size(min=1, max=60, groups={AllValidations.class, UpdateValidations.class})
	public String getSurname() {
		return surname;
	}

	public void setSurname(String firstName) {
		this.surname = firstName.trim();
	}

	@NotNull(groups={AllValidations.class, UpdateValidations.class})
	@Size(max=60, groups={AllValidations.class, UpdateValidations.class})
	public String getSecondSurname() {
		return secondSurname;
	}

	public void setSecondSurname(String lastName) {
		this.secondSurname = lastName.trim();
	}

	@NotNull(groups={AllValidations.class, UpdateValidations.class})
	@Size(min=1, max=60, groups={AllValidations.class, UpdateValidations.class})
	@Email(groups={AllValidations.class, UpdateValidations.class})
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.trim();
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Long getFacultyId() {
		return facultyId;
	}

	public void setFaculty(Long facultyId) {
		this.facultyId = facultyId;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
}
