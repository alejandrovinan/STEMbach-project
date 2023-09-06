package es.udc.stembach.backend.rest.dtos;

import es.udc.stembach.backend.model.entities.*;

import java.util.ArrayList;
import java.util.List;

public class UserConversor {
	
	private UserConversor() {}
	
	public final static UserDto toUserDto(User user) {

		UserDto userDto = new UserDto(user.getId(), user.getName(), user.getSurname(), user.getSecondSurname(), user.getRole().toString());

		switch(user.getRole()){
			case STEMCOORDINATOR:
				STEMCoordinator stemCoordinator = (STEMCoordinator) user;
				userDto.setEmail(stemCoordinator.getEmail());
				userDto.setPassword(stemCoordinator.getPassword());
				break;
			case CENTERSTEMCOORDINATOR:
				CenterSTEMCoordinator centerSTEMCoordinator = (CenterSTEMCoordinator) user;
				userDto.setEmail(centerSTEMCoordinator.getEmail());
				userDto.setPassword(centerSTEMCoordinator.getPassword());
				break;
			case UDCTEACHER:
				UDCTeacher udcTeacher = (UDCTeacher) user;
				userDto.setEmail(udcTeacher.getEmail());
				userDto.setPassword(udcTeacher.getPassword());
				userDto.setDni(udcTeacher.getDni());
				userDto.setFaculty(udcTeacher.getFaculty().getId());
				break;
		}
		return userDto;
	}
	
	public final static User toUser(UserDto userDto) {
		
		return new User(userDto.getId(), userDto.getName(), userDto.getSurname(), userDto.getSecondSurname(),
				User.RoleType.valueOf(userDto.getRole()));
	}

	public final static STEMCoordinator toSTEMCoordinator(UserDto userDto){
		return new STEMCoordinator(userDto.getId(), userDto.getName(), userDto.getSurname(), userDto.getSecondSurname(),
				User.RoleType.valueOf(userDto.getRole()), userDto.getEmail(), userDto.getPassword());
	}

	public final static CenterSTEMCoordinator toCenterSTEMCoordinator(UserDto userDto){
		return new CenterSTEMCoordinator(userDto.getId(), userDto.getName(), userDto.getSurname(), userDto.getSecondSurname(),
				User.RoleType.valueOf(userDto.getRole()), userDto.getEmail(), userDto.getPassword());
	}

	public final static UDCTeacher toUDCTeacher(UserDto userDto){
		return new UDCTeacher(userDto.getId(), userDto.getName(), userDto.getSurname(), userDto.getSecondSurname(),
				User.RoleType.valueOf(userDto.getRole()), userDto.getEmail(), userDto.getDni(), userDto.getPassword(),null);
	}
	
	public final static AuthenticatedUserDto toAuthenticatedUserDto(String serviceToken, User user) {
		return new AuthenticatedUserDto(serviceToken, toUserDto(user));
	}

	public final static UDCTeacherSummaryDto toUDCTeacherSummaryDto(UDCTeacher udcTeacher){
		return new UDCTeacherSummaryDto(udcTeacher.getId(), udcTeacher.getName(), udcTeacher.getSurname(), udcTeacher.getSecondSurname(), udcTeacher.getEmail(), udcTeacher.getDni());
	}

	public final static List<UDCTeacherSummaryDto> toUDCTeacherSummaryDtos(List<UDCTeacher> udcTeacherList){
		List<UDCTeacherSummaryDto> udcTeacherSummaryDtos = new ArrayList<>();
		udcTeacherList.forEach(t -> udcTeacherSummaryDtos.add(toUDCTeacherSummaryDto(t)));

		return udcTeacherSummaryDtos;
	}

	public final static UDCTeacherSelectorDto toUDCTeacherSelectorDto(UDCTeacher udcTeacher){
		return new UDCTeacherSelectorDto(udcTeacher.getId(), udcTeacher.getName(), udcTeacher.getSurname(), udcTeacher.getSecondSurname());
	}

	public final static List<UDCTeacherSelectorDto> toUdcTeachersSelectorDto(List<UDCTeacher> udcTeacherList){
		List<UDCTeacherSelectorDto> udcTeacherSelectorDtos = new ArrayList<>();
		udcTeacherList.forEach(t -> udcTeacherSelectorDtos.add(toUDCTeacherSelectorDto(t)));

		return udcTeacherSelectorDtos;
	}

	public final static Student toStudent(StudentDto studentDto){
		return new Student(studentDto.getName(), studentDto.getSurname(), studentDto.getSecondSurname(), studentDto.getRole(), null, null, studentDto.getDni());
	}

	public final static List<Student> toStudentList(List<StudentDto> studentDtos){
		List<Student> studentList = new ArrayList<>();
		studentDtos.forEach(s -> studentList.add(toStudent(s)));

		return studentList;
	}

	public final static StudentDto toStudentDto(Student student){
		return new StudentDto(student.getId(), student.getName(), student.getSurname(), student.getSecondSurname(), student.getDni(), student.getRole(), student.getSchool().getId(), student.getStudentGroup().getId());
	}

	public final static List<StudentDto> toStudentDtos(List<Student> students){
		List<StudentDto> studentDtos = new ArrayList<>();
		students.forEach(s -> studentDtos.add(toStudentDto(s)));

		return studentDtos;
	}
}
