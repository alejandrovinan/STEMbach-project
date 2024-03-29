package es.udc.stembach.backend.model.services;

import es.udc.stembach.backend.model.entities.*;
import es.udc.stembach.backend.model.exceptions.*;

import java.util.List;

public interface UserService {
	
	void createAccount(User user, Long facultyId, Long schoolId, Long coordinatorId) throws DuplicateInstanceException, FacultyNotFoundException, SchoolNotFoundException;
	
	User login(String email, String password, User.RoleType roleType) throws IncorrectLoginException;
	

	User loginFromId(Long id, User.RoleType roleType) throws InstanceNotFoundException;

	void changePassword(Long id, User.RoleType roleType, String oldPassword, String newPassword)
		throws InstanceNotFoundException, IncorrectPasswordException;

	List<Faculty> findAllFaculties();

	List<School> findAllSchool();

	List<UDCTeacher> findAllUDCTeacher();

	List<Student> findAllStudentsOfGroup(Long groupId);

	Long getSchoolId(Long centerStemCoordinatorId) throws InstanceNotFoundException;

}
