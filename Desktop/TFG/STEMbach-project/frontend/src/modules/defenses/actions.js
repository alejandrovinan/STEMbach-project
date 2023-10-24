import backend from '../../backend';
import * as actionTypes from './actionTypes';
import * as selectors from './selectors';

const createDefenseCompleted = defense => ({
    type: actionTypes.CREATE_DEFENSE_COMPLETED,
    defense
})

export const createDefense = (formData, onSuccess, onErrors) => dispatch =>
    backend.defenseServie.createDefense(formData,
        defense => {
                    dispatch(createDefenseCompleted(defense));
                    onSuccess();
                },
                onErrors
        );