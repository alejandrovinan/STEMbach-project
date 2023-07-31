import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';
import {act} from "react-dom/test-utils";

const initialState = {
    projectDetails: null,
    teacherSelectorList: null,
    bienniums: null
};

const projectDetails = (state = initialState.projectDetails, action) => {

    switch(action.type) {
        case actionTypes.PROJECT_CREATED_COMPLETED:
            return action.projectDetails;

        case actionTypes.FIND_PROJECT_DETAILS_COMPLETED:
            return action.projectDetails;

        case actionTypes.CLEAR_PROJECT_DETAILS_COMPLETED:
            return initialState.projectDetails;

        default:
            return state;
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

const reducer = combineReducers({
    projectDetails,
    teacherSelectorList,
    bienniums
});

export default reducer;