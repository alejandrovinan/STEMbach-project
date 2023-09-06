import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';
import {act} from "react-dom/test-utils";

const initialState = {
    projectDetails: null,
    projectResults: null,
    teacherSelectorList: null,
    bienniums: null,
    request: null
};

const projectDetails = (state = initialState.projectDetails, action) => {

    switch(action.type) {
        case actionTypes.PROJECT_CREATED_COMPLETED:
            return action.projectDetails;

        case actionTypes.FIND_PROJECT_DETAILS_COMPLETED:
            return action.projectDetails;

        case actionTypes.CLEAR_PROJECT_DETAILS_COMPLETED:
            return initialState.projectDetails;

        case actionTypes.APPROVE_PROJECT_COMPLETED:
            return {...state, revised: true}

        case actionTypes.CANCEL_PROJECT_COMPLETED:
            return {...state, active: false}

        case actionTypes.PROJECT_EDITED_COMPLETED:
            return action.projectDetails;

        default:
            return state;
    }
}

const projectResults = (state = initialState.projectResults, action) => {

    switch (action.type){
        case actionTypes.FIND_PROJECTS_BY_CRITERIA_COMPLETED:
            return action.projectResults;

        default:
            return initialState.projectResults
    }
}

const teacherSelectorList = (state = initialState.teacherSelectorList, action) => {
    switch (action.type) {
        case actionTypes.FIND_ALL_TEACHERS_COMPLETED:
            return action.teacherSelectorList;

        default:
            return state;
    }
}

const bienniums = (state = initialState.bienniums, action) => {

    switch (action.type) {
        case actionTypes.FIND_ALL_BIENNIUM_COMPLETED:
            return action.bienniums;

        default:
            return state;
    }
}

const request = (state = initialState.request, action) =>{

    switch (action.type) {
        case actionTypes.CREATE_REQUEST_COMPLETED:
            return action.request;

        default:
            return state;
    }
}

const reducer = combineReducers({
    projectDetails,
    projectResults,
    teacherSelectorList,
    bienniums,
    request
});

export default reducer;