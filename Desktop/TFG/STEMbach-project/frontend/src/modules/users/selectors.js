const getModuleState = state => state.users;

export const getUser = state => 
    getModuleState(state).user;

export const isLoggedIn = state =>
    getUser(state) !== null

export const getEmail = state =>
    isLoggedIn(state) ? getUser(state).email : null;

export const getFaculties = state => getModuleState(state).faculties;

export const getSchools = state => getModuleState(state).schools;



