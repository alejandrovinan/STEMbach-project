import {config, appFetch} from "./appFetch";

export const createProject = (title, description, observations, modality, url, offerZone, maxGroups, studentsPerGroup, bienniumId, teacherIds, onSucces, onErrors) =>
    appFetch('/projects/createProject',
    config('POST', {title, description, observations, modality, url, offerZone, maxGroups, studentsPerGroup, bienniumId, teacherIds}),
        onSucces,
        onErrors);

export const findAllBienniums = (onSuccess) =>
    appFetch('/projects/bienniums', config('GET'), onSuccess);

export const findProjectDetails = (id, onSuccess) =>
    appFetch(`/projects/projectDetails/${id}`, config('GET'), onSuccess);
