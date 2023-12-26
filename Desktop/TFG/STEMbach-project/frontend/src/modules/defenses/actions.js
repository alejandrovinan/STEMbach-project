import backend from '../../backend';
import * as actionTypes from './actionTypes';

const createDefenseCompleted = defense => ({
    type: actionTypes.CREATE_DEFENSE_COMPLETED,
    defense
})

export const createDefense = (id, formData, onSuccess, onErrors) => dispatch =>
    backend.defenseServie.createDefense(id, formData,
        defense => {
                    dispatch(createDefenseCompleted(defense));
                    onSuccess();
                },
                onErrors
        );

export const checkIfDefenseExists = (projectInstanceId, onSuccess) => dispatch =>
    backend.defenseServie.checkIfDefenseExists(projectInstanceId, result => onSuccess(result));

const findDefenseDetailsCompleted = defense => ({
    type: actionTypes.FIND_DEFENSE_DETAILS_COMPLETED,
    defense
})

export const findDefenseDetails = (id, onSuccess, onErrors) => dispatch =>
    backend.defenseServie.findDefenseDetails(id,
defense => {
        console.log(defense);
            dispatch(findDefenseDetailsCompleted(defense));
        },
        onErrors
    )

const updateDefenseCompleted = defense => ({
    type: actionTypes.UPDATE_DEFENSE_COMPLETED,
    defense
})

export const updateDefense = (id, formData, onSuccess, onErrors) => dispatch =>
    backend.defenseServie.updateDefense(id, formData, defense => {
        dispatch(updateDefenseCompleted(defense));
        onSuccess();
    }, onErrors);
