package es.udc.stembach.backend.model.services;

import es.udc.stembach.backend.model.entities.*;
import es.udc.stembach.backend.model.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly=true)
public class PermissionCheckerImpl implements PermissionChecker {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UDCTeacherDao udcTeacherDao;

	@Autowired
	private STEMCoordinatorDao stemCoordinatorDao;

	@Autowired
	private CenterSTEMCoordinatorDao centerSTEMCoordinatorDao;

	@Override
	public void checkUserExists(Long userId) throws InstanceNotFoundException {
		
		if (!userDao.existsById(userId)) {
			throw new InstanceNotFoundException("project.entities.user", userId);
		}
		
	}

	@Override
	public User checkUser(Long userId, User.RoleType roleType) throws InstanceNotFoundException {

		switch (roleType){
			case UDCTEACHER:
				Optional<UDCTeacher> udcTeacher = udcTeacherDao.findById(userId);
				if (!udcTeacher.isPresent()) {
					throw new InstanceNotFoundException("project.entities.user", userId);
				}
				return udcTeacher.get();

			case CENTERSTEMCOORDINATOR:
				Optional<CenterSTEMCoordinator> centerSTEMCoordinator = centerSTEMCoordinatorDao.findById(userId);
				if (!centerSTEMCoordinator.isPresent()) {
					throw new InstanceNotFoundException("project.entities.user", userId);
				}
				return centerSTEMCoordinator.get();

			case STEMCOORDINATOR:
				Optional<STEMCoordinator> stemCoordinator = stemCoordinatorDao.findById(userId);
				if (!stemCoordinator.isPresent()) {
					throw new InstanceNotFoundException("project.entities.user", userId);
				}
				return stemCoordinator.get();

			default:
				throw new InstanceNotFoundException("project.entities.user", userId);
		}
		
	}

}
