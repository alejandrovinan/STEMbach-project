package es.udc.stembach.backend.model.services;

import es.udc.stembach.backend.model.entities.*;
import es.udc.stembach.backend.model.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private PermissionChecker permissionChecker;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UDCTeacherDao udcTeacherDao;

	@Autowired
	private STEMCoordinatorDao stemCoordinatorDao;

	@Autowired
	private CenterSTEMCoordinatorDao centerSTEMCoordinatorDao;

	@Autowired
	private FacultyDao facultyDao;

	@Autowired
	private SchoolDao schoolDao;

	@Autowired
	private CenterHistoryDao centerHistoryDao;

	@Autowired
	private StudentDao studentDao;

	private void createUDCTeacher(UDCTeacher udcTeacher, Long facultyId) throws DuplicateInstanceException, FacultyNotFoundException {

		if(udcTeacherDao.existsByEmail(udcTeacher.getEmail())){
			throw new DuplicateInstanceException("project.entities.UDCTeacher", udcTeacher.getEmail());
		}
		if(facultyId == null){
			throw new FacultyNotFoundException(0L);
		}
		Optional<Faculty> faculty = facultyDao.findById(facultyId);

		if(faculty.isEmpty()){
			throw new FacultyNotFoundException(facultyId);
		}

		udcTeacher.setFaculty(faculty.get());
		udcTeacher.setPassword(passwordEncoder.encode(udcTeacher.getPassword()));
		udcTeacherDao.save(udcTeacher);
	}

	private void createCenterSTEMCoordinator(CenterSTEMCoordinator centerSTEMCoordinator, Long schoolId) throws SchoolNotFoundException, DuplicateInstanceException {

		if(centerSTEMCoordinatorDao.existsByEmail(centerSTEMCoordinator.getEmail())){
			throw new DuplicateInstanceException("project.entities.centerstemcoordinator", centerSTEMCoordinator.getEmail());
		}
		if(schoolId == null){
			throw new SchoolNotFoundException(0L);
		}
		Optional<School> school = schoolDao.findById(schoolId);

		if(school.isEmpty()){
			throw new SchoolNotFoundException(schoolId);
		}

		centerSTEMCoordinator.setPassword(passwordEncoder.encode(centerSTEMCoordinator.getPassword()));
		Optional<CenterHistory> centerHistory = centerHistoryDao.findBySchoolIdAndEndDateIsNull(schoolId);

		if(centerHistory.isPresent()){
			centerHistory.get().setEndDate(LocalDateTime.now());
		}

		centerSTEMCoordinatorDao.save(centerSTEMCoordinator);
		centerHistoryDao.save(new CenterHistory(centerSTEMCoordinator, school.get(), LocalDateTime.now(), null));
	}

	@Override
	public void createAccount(User user, Long facultyId, Long schoolId, Long coordinatorId) throws DuplicateInstanceException, FacultyNotFoundException, SchoolNotFoundException {

		Optional<STEMCoordinator> stemCoordinator = stemCoordinatorDao.findById(coordinatorId);

		switch (user.getRole()){

			case UDCTEACHER:
				UDCTeacher udcTeacher = (UDCTeacher) user;
				createUDCTeacher(udcTeacher, facultyId);
				break;

			case CENTERSTEMCOORDINATOR:
				CenterSTEMCoordinator centerSTEMCoordinator = (CenterSTEMCoordinator) user;
				createCenterSTEMCoordinator(centerSTEMCoordinator, schoolId);
				break;

			case STUDENT:
				break;
		}
		
	}

	private UDCTeacher checkUDCTeacher(String email, String password) throws IncorrectLoginException {
		Optional<UDCTeacher> udcTeacher = udcTeacherDao.findByEmail(email);
		if (udcTeacher.isEmpty()) {
			throw new IncorrectLoginException(email, password);
		}

		if (!passwordEncoder.matches(password, udcTeacher.get().getPassword())) {
			throw new IncorrectLoginException(email, password);
		}

		return udcTeacher.get();
	}

	private STEMCoordinator checkSTEMCoordinator(String email, String password) throws IncorrectLoginException {
		Optional<STEMCoordinator> stemCoordinator = stemCoordinatorDao.findByEmail(email);
		if (stemCoordinator.isEmpty()) {
			throw new IncorrectLoginException(email, password);
		}

		if (!passwordEncoder.matches(password, stemCoordinator.get().getPassword())) {
			throw new IncorrectLoginException(email, password);
		}
		return stemCoordinator.get();
	}

	private CenterSTEMCoordinator checkCenterSTEMCoordinator(String email, String password) throws IncorrectLoginException {
		Optional<CenterSTEMCoordinator> centerSTEMCoordinator = centerSTEMCoordinatorDao.findByEmail(email);
		if (centerSTEMCoordinator.isEmpty()) {
			throw new IncorrectLoginException(email, password);
		}

		if (!passwordEncoder.matches(password, centerSTEMCoordinator.get().getPassword())) {
			throw new IncorrectLoginException(email, password);
		}

		return centerSTEMCoordinator.get();
	}


	@Override
	@Transactional(readOnly=true)
	public User login(String email, String password, User.RoleType roleType) throws IncorrectLoginException {

		return switch (roleType) {
			case UDCTEACHER -> checkUDCTeacher(email, password);
			case STEMCOORDINATOR -> checkSTEMCoordinator(email, password);
			case CENTERSTEMCOORDINATOR -> checkCenterSTEMCoordinator(email, password);
			default -> null;
		};
		
	}
	
	@Override
	@Transactional(readOnly=true)
	public User loginFromId(Long id, User.RoleType roleType) throws InstanceNotFoundException {
		return permissionChecker.checkUser(id, roleType);
	}

	@Override
	public void changePassword(Long id, User.RoleType roleType, String oldPassword, String newPassword)
		throws InstanceNotFoundException, IncorrectPasswordException {
		
		User user = permissionChecker.checkUser(id, roleType);
		switch (roleType) {
			case STEMCOORDINATOR -> {
				STEMCoordinator stemCoordinator = (STEMCoordinator) user;
				if (!passwordEncoder.matches(oldPassword, stemCoordinator.getPassword())) {
					throw new IncorrectPasswordException();
				} else {
					stemCoordinator.setPassword(passwordEncoder.encode(newPassword));
				}
			}
			case UDCTEACHER -> {
				UDCTeacher udcTeacher = (UDCTeacher) user;
				if (!passwordEncoder.matches(oldPassword, udcTeacher.getPassword())) {
					throw new IncorrectPasswordException();
				} else {
					udcTeacher.setPassword(passwordEncoder.encode(newPassword));
				}
			}
			case CENTERSTEMCOORDINATOR -> {
				CenterSTEMCoordinator centerSTEMCoordinator = (CenterSTEMCoordinator) user;
				if (!passwordEncoder.matches(oldPassword, centerSTEMCoordinator.getPassword())) {
					throw new IncorrectPasswordException();
				} else {
					centerSTEMCoordinator.setPassword(passwordEncoder.encode(newPassword));
				}
			}
		}
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Faculty> findAllFaculties() {
		Iterable<Faculty> facultyIterable = facultyDao.findAll(Sort.by(Sort.Direction.ASC, "name"));
		List<Faculty> facultyList = new ArrayList<>();

		facultyIterable.forEach(f -> facultyList.add(f));

		return facultyList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<School> findAllSchool() {
		Iterable<School> schoolIterable = schoolDao.findAll(Sort.by(Sort.Direction.ASC, "name"));
		List<School> schoolList = new ArrayList<>();

		schoolIterable.forEach(s -> schoolList.add(s));

		return schoolList;
	}


	@Override
	@Transactional(readOnly = true)
	public List<UDCTeacher> findAllUDCTeacher(){
		Iterable<UDCTeacher> udcTeachersIterable = udcTeacherDao.findAll(Sort.by(Sort.Direction.ASC, "name"));

		List<UDCTeacher> udcTeachers = new ArrayList<>();

		udcTeachersIterable.forEach(t -> udcTeachers.add(t));

		return udcTeachers;
	}

	@Override
	public List<Student> findAllStudentsOfGroup(Long groupId) {
		Iterable<Student> students = studentDao.findByStudentGroupId(groupId);

		List<Student> studentsList = new ArrayList<>();

		students.forEach(s -> studentsList.add(s));

		return studentsList;
	}

	@Override
	public Long getSchoolId(Long centerStemCoordinatorId) throws InstanceNotFoundException {
		Optional<CenterHistory> centerHistoryOptional = centerHistoryDao.findByCenterSTEMCoordinatorIdAndEndDateIsNull(centerStemCoordinatorId);

		if(centerHistoryOptional.isEmpty()){
			throw new InstanceNotFoundException("project.users.centerhistory", centerStemCoordinatorId);
		}

		return centerHistoryOptional.get().getSchool().getId();
	}
}
