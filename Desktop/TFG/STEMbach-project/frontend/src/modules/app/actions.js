import * as actionTypes from './actionTypes';
import backend from '../../backend';

export const loading = () => ({
    type: actionTypes.LOADING
});

export const loaded = () => ({
    type: actionTypes.LOADED
});

export const error = error => ({
    type: actionTypes.ERROR,
    error
});

export const asignProjects = (onSuccess) => {
    backend.projectsService.asignProjects(onSuccess);
}