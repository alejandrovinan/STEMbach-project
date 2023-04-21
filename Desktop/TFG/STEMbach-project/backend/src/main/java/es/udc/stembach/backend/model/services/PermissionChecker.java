package es.udc.stembach.backend.model.services;

import es.udc.stembach.backend.model.entities.User;
import es.udc.stembach.backend.model.exceptions.InstanceNotFoundException;

public interface PermissionChecker {
	
	void checkUserExists(Long userId) throws InstanceNotFoundException;
	
	User checkUser(Long userId) throws InstanceNotFoundException;
	
}
