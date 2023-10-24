import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    projectDetails: null,
    projectResults: null,
    teacherSelectorList: null,
    bienniums: null,
    requests: null,
    projectInstancesResults: null,
    projectInstanceDetails: null
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

        case actionTypes.CLEAR_PROJECTS_SEARCH:
            return initialState.projectResults;

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

const requests = (state = initialState.requests, action) =>{

    switch (action.type) {
        case actionTypes.VIEW_REQUESTS_COMPLETED:
            return action.result;

        default:
            return state;
    }
}

const projectInstancesResults = (state = initialState.projectInstancesResults, action) => {

    switch (action.type){
        case actionTypes.FIND_PROJECT_INSTANCES_SUMMARY_COMPLETED:
            return action.result;

        case actionTypes.CLEAR_PROJECT_INSTANCES_SUMMARY_COMPLETED:
            return initialState.projectInstancesResults;

        default:
            return state;
    }
}

const projectInstanceDetails = (state = initialState.projectInstanceDetails, action) => {
    switch (action.type){

        case actionTypes.FIND_PROJECT_INSTANCE_DETAILS_COMPLETED:
            return action.projectInstanceDetails;

        case actionTypes.EDIT_PROJECT_INSTANCE_COMPLETED:
            return action.projectInstanceDetails;

        default:
            return state;
    }
}

const reducer = combineReducers({
    projectDetails,
    projectResults,
    teacherSelectorList,
    bienniums,
    requests,
    projectInstancesResults,
    projectInstanceDetails
});

export default reducer;