import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    user: null,
    faculties: null,
    schools: null
};

const user = (state = initialState.user, action) => {

    switch (action.type) {

        case actionTypes.SIGN_UP_COMPLETED:
            return state;

        case actionTypes.LOGIN_COMPLETED:
            return action.authenticatedUser.user;

        case actionTypes.LOGOUT:
            return initialState.user;

        case actionTypes.UPDATE_PROFILE_COMPLETED:
            return action.user;

        default:
            return state;

    }

}

const faculties = (state = initialState.faculties, action) => {

    switch (action.type) {

        case actionTypes.FIND_ALL_FACULTIES_COMPLETED:
            return action.faculties;

        default:
            return state;
    }
}

const schools = (state = initialState.schools, action) => {

    switch (action.type) {

        case actionTypes.FIND_ALL_SCHOOLS_COMPLETED:
            return action.schools;

        default:
            return state;
    }
}

const reducer = combineReducers({
    user,
    faculties,
    schools
});

export default reducer;


