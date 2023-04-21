package es.udc.stembach.backend.model.services;

import es.udc.stembach.backend.model.entities.User;
import es.udc.stembach.backend.model.exceptions.DuplicateInstanceException;
import es.udc.stembach.backend.model.exceptions.IncorrectLoginException;
import es.udc.stembach.backend.model.exceptions.IncorrectPasswordException;
import es.udc.stembach.backend.model.exceptions.InstanceNotFoundException;

public interface UserService {
	
	void signUp(User user) throws DuplicateInstanceException;
	
	User login(String userName, String password) throws IncorrectLoginException;
	
	User loginFromId(Long id) throws InstanceNotFoundException;
	
	User updateProfile(Long id, String firstName, String lastName, String email) throws InstanceNotFoundException;
	
	void changePassword(Long id, String oldPassword, String newPassword)
		throws InstanceNotFoundException, IncorrectPasswordException;

}
