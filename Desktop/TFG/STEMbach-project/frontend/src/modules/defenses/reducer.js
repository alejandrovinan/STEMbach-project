import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    defense: null
}

const defense = (state = initialState.defense, action) => {

    switch (action.type){
        case actionTypes.CREATE_DEFENSE_COMPLETED:
            return action.defense;

        default:
            return state;
    }
}

const reducer = combineReducers({
    defense
})

export default reducer;