import {config, appFetch, setServiceToken, getServiceToken, removeServiceToken, setReauthenticationCallback} from './appFetch';

export const login = (email, password, roleType, onSuccess, onErrors, reauthenticationCallback) =>
    appFetch('/users/login', config('POST', {email, password, roleType}),
        authenticatedUser => {
            setServiceToken(authenticatedUser.serviceToken);
            setReauthenticationCallback(reauthenticationCallback);
            onSuccess(authenticatedUser);
        }, 
        onErrors);

export const tryLoginFromServiceToken = (onSuccess, reauthenticationCallback) => {

    const serviceToken = getServiceToken();

    if (!serviceToken) {
        onSuccess();
        return;
    }

    setReauthenticationCallback(reauthenticationCallback);

    appFetch('/users/loginFromServiceToken', config('POST'),
        authenticatedUser => onSuccess(authenticatedUser),
        () => removeServiceToken()
    );

}

export const signUp = (user, onSuccess, onErrors) => {

    appFetch('/users/createAccount', config('POST', user),
        newUser => {
            onSuccess(newUser);
        }, 
        onErrors);

}

export const logout = () => removeServiceToken();

export const updateProfile = (user, onSuccess, onErrors) =>
    appFetch(`/users/${user.id}`, config('PUT', user),
        onSuccess, onErrors);

export const changePassword = (id, oldPassword, newPassword, onSuccess,
    onErrors) =>
    appFetch(`/users/${id}/changePassword`, 
        config('POST', {oldPassword, newPassword}),
        onSuccess, onErrors);

export const findAllFaculties = (onSuccess) =>
    appFetch('/users/faculties', config('GET'), onSuccess);

export const findAllSchools = (onSuccess) =>
    appFetch('/users/schools', config('GET'), onSuccess);