import {config, appFetch} from "./appFetch";

export const createDefense = (id, formData, onSuccess, onErrors) =>
    appFetch(`/defenses/create/${id}`, config("POST", formData), onSuccess, onErrors);

export const checkIfDefenseExists = (projectInstanceId, onSuccess) =>
    appFetch(`/defenses/checkIfExistsDefense?projectInstanceId=${projectInstanceId}`, config('GET'), onSuccess);

export const findDefenseDetails= (id, onSuccess, onErrors) =>
    appFetch(`/defenses/defenseDetails/${id}`, config('GET'), onSuccess, onErrors);

export const updateDefense = (id, formData, onSuccess, onErrors) =>
    appFetch(`/defenses/editDefense/${id}`, config('POST', formData), onSuccess, onErrors);
