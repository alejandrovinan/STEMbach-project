package es.udc.stembach.backend.rest.controllers;

import es.udc.stembach.backend.model.entities.User;
import es.udc.stembach.backend.model.exceptions.*;
import es.udc.stembach.backend.model.services.UserService;
import es.udc.stembach.backend.rest.common.ErrorsDto;
import es.udc.stembach.backend.rest.common.JwtGenerator;
import es.udc.stembach.backend.rest.common.JwtInfo;
import es.udc.stembach.backend.rest.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;

import static es.udc.stembach.backend.rest.dtos.FacultyConversor.toFacultyDtos;
import static es.udc.stembach.backend.rest.dtos.SchoolConversor.toSchoolDtos;
import static es.udc.stembach.backend.rest.dtos.UserConversor.*;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final static String INCORRECT_LOGIN_EXCEPTION_CODE = "project.exceptions.IncorrectLoginException";
	private final static String INCORRECT_PASSWORD_EXCEPTION_CODE = "project.exceptions.IncorrectPasswordException";
	private final static String FACULTY_NOT_FOUND_EXCEPTION_CODE= "project.exceptions.FacultyNotFoundException";
	private final static String SCHOOL_NOT_FOUND_EXCEPTION_CODE= "project.exceptions.SchoolNotFoundException";
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private JwtGenerator jwtGenerator;
	
	@Autowired
	private UserService userService;
	
	@ExceptionHandler(IncorrectLoginException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorsDto handleIncorrectLoginException(IncorrectLoginException exception, Locale locale) {
		
		String errorMessage = messageSource.getMessage(INCORRECT_LOGIN_EXCEPTION_CODE, null,
				INCORRECT_LOGIN_EXCEPTION_CODE, locale);

		return new ErrorsDto(errorMessage);
		
	}
	
	@ExceptionHandler(IncorrectPasswordException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorsDto handleIncorrectPasswordException(IncorrectPasswordException exception, Locale locale) {
		
		String errorMessage = messageSource.getMessage(INCORRECT_PASSWORD_EXCEPTION_CODE, null,
				INCORRECT_PASSWORD_EXCEPTION_CODE, locale);

		return new ErrorsDto(errorMessage);
		
	}

	@ExceptionHandler(FacultyNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorsDto handleFacultyNotFoundException(FacultyNotFoundException exception, Locale locale){
		String errorsMessage = messageSource.getMessage(FACULTY_NOT_FOUND_EXCEPTION_CODE, null, FACULTY_NOT_FOUND_EXCEPTION_CODE, locale);

		return new ErrorsDto(errorsMessage);
	}

	@ExceptionHandler(SchoolNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorsDto handleSchoolNotFoundException(SchoolNotFoundException exception, Locale locale){
		String errorsMessage = messageSource.getMessage(SCHOOL_NOT_FOUND_EXCEPTION_CODE, null, SCHOOL_NOT_FOUND_EXCEPTION_CODE, locale);

		return new ErrorsDto(errorsMessage);
	}

	@PostMapping("/createAccount")
	public ResponseEntity<AuthenticatedUserDto> createAccount(@RequestAttribute Long userId,
															  @Validated({UserDto.AllValidations.class}) @RequestBody UserDto userDto)
			throws DuplicateInstanceException, FacultyNotFoundException, SchoolNotFoundException {

		User user = new User();
		switch (userDto.getRole()){
			case "UDCTEACHER":
				user = toUDCTeacher(userDto);
				break;
			case "CENTERSTEMCOORDINATOR":
				user = toCenterSTEMCoordinator(userDto);
				break;
		}

		userService.createAccount(user, userDto.getFacultyId(), userDto.getSchoolId(), userId);
		
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest().path("/{id}")
			.buildAndExpand(user.getId()).toUri();
	
		return ResponseEntity.created(location).body(toAuthenticatedUserDto(generateServiceToken(user), user));

	}
	
	@PostMapping("/login")
	public AuthenticatedUserDto login(@Validated @RequestBody LoginParamsDto params)
		throws IncorrectLoginException {
		User user = userService.login(params.getEmail(), params.getPassword(), params.getRoleType());
			
		return toAuthenticatedUserDto(generateServiceToken(user), user);
		
	}
	
	@PostMapping("/loginFromServiceToken")
	public AuthenticatedUserDto loginFromServiceToken(@RequestAttribute Long userId, @RequestAttribute User.RoleType role,
		@RequestAttribute String serviceToken) throws InstanceNotFoundException {
		
		User user = userService.loginFromId(userId, role);
		
		return toAuthenticatedUserDto(serviceToken, user);
		
	}



	@PostMapping("/{id}/changePassword")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changePassword(@RequestAttribute Long userId, @RequestAttribute User.RoleType role, @PathVariable Long id,
		@Validated @RequestBody ChangePasswordParamsDto params)
		throws PermissionException, InstanceNotFoundException, IncorrectPasswordException {
		
		if (!id.equals(userId)) {
			throw new PermissionException();
		}
		
		userService.changePassword(id, role, params.getOldPassword(), params.getNewPassword());
		
	}
	
	private String generateServiceToken(User user) {
		
		JwtInfo jwtInfo = new JwtInfo(user.getId(), user.getName(), user.getRole().toString());
		
		return jwtGenerator.generate(jwtInfo);
		
	}

	@GetMapping("/faculties")
	public List<FacultyDto> findAllFaculties(){
		return toFacultyDtos(userService.findAllFaculties());
	}

	@GetMapping("/schools")
	public List<SchoolDto> findAllSchools(){
		return toSchoolDtos(userService.findAllSchool());
	}

	@GetMapping("/selectorTeachers")
	public List<UDCTeacherSelectorDto> findAllUDCTeacher(){
		return toUdcTeachersSelectorDto(userService.findAllUDCTeacher());
	}
	
}
