package es.udc.stembach.backend.rest.dtos;

import es.udc.stembach.backend.model.entities.CenterSTEMCoordinator;
import es.udc.stembach.backend.model.entities.STEMCoordinator;
import es.udc.stembach.backend.model.entities.UDCTeacher;
import es.udc.stembach.backend.model.entities.User;

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

}
