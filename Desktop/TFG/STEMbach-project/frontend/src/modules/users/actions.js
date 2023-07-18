import * as actionTypes from './actionTypes';
import * as selectors from './selectors';
import backend from '../../backend';

const signUpCompleted = authenticatedUser => ({
    type: actionTypes.SIGN_UP_COMPLETED,
    authenticatedUser
});

export const signUp = (user, onSuccess, onErrors) => dispatch =>
    backend.userService.signUp(user,
        newUser => {
            onSuccess();
        },
        onErrors);

const loginCompleted = authenticatedUser => ({
    type: actionTypes.LOGIN_COMPLETED,
    authenticatedUser
});

export const login = (userName, password, role, onSuccess, onErrors, reauthenticationCallback) => dispatch =>
    backend.userService.login(userName, password, role,
        authenticatedUser => {
            dispatch(loginCompleted(authenticatedUser));
            onSuccess();
        },
        onErrors,
        reauthenticationCallback
    );

export const tryLoginFromServiceToken = reauthenticationCallback => dispatch =>
    backend.userService.tryLoginFromServiceToken(
        authenticatedUser => {
            if (authenticatedUser) {
                dispatch(loginCompleted(authenticatedUser));
            }
        },
        reauthenticationCallback
    );
    

export const logout = () => {

    backend.userService.logout();

    return {type: actionTypes.LOGOUT};

};

export const updateProfileCompleted = user => ({
    type: actionTypes.UPDATE_PROFILE_COMPLETED,
    user
})

export const updateProfile = (user, onSuccess, onErrors) => dispatch =>
    backend.userService.updateProfile(user, 
        user => {
            dispatch(updateProfileCompleted(user));
            onSuccess();
        },
        onErrors);

export const changePassword = (id, oldPassword, newPassword, onSuccess, onErrors) => dispatch =>
    backend.userService.changePassword(id, oldPassword, newPassword, onSuccess, onErrors);

export const findAllFacultiesCompleted = faculties => ({
    type: actionTypes.FIND_ALL_FACULTIES_COMPLETED,
    faculties
});

export const findAllFaculties = () => (dispatch, getState) => {
    const faculties = selectors.getFaculties(getState());

    if(!faculties){
        backend.userService.findAllFaculties(
            faculties => dispatch(findAllFacultiesCompleted(faculties))
        );
    }
}


export const findAllSchoolsCompleted = schools => ({
    type: actionTypes.FIND_ALL_SCHOOLS_COMPLETED,
    schools
});

export const findAllSchools = () => (dispatch, getState) => {
    const schools = selectors.getSchools(getState());

    if(!schools){
        backend.userService.findAllSchools(
            schools => dispatch(findAllSchoolsCompleted(schools))
        );
    }
}