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

export const findAllProjects = ({page}, onSuccess) =>
    appFetch(`/projects/findAllProjects?page=${page}`, config('GET'), onSuccess);

export const findProjectsByCriteria = ({modality, offerZone, revised, active, maxGroups, studentsPerGroup, biennium, assigned, teachers, title, page}, onSuccess) =>{

    let path = `/projects/findByCriteria?page=${page}`;

    path += modality ? `&modality=${modality}` : "";
    path+= offerZone ? `&offerZone=${offerZone}` : "";
    path += `&revised=${revised}`;
    path+= `&active=${active}`;
    path += maxGroups ? `&maxGroups=${maxGroups}` : "";
    path += studentsPerGroup ? `&studentsPerGroups=${studentsPerGroup}` : "";
    path += biennium ? `&biennium=${biennium}` : "";
    path += `&assigned=${assigned}`;

    if(teachers && teachers.length > 0){
        path += `&teachers=${teachers.join(',')}`;
    }

    path += title ? `&title=${title}` : "";

    appFetch(path, config('GET'), onSuccess);
}

export const approveProject = (id, onSuccess) =>
    appFetch(`/projects/approveProject`, config('POST', {id}), onSuccess);

export const cancelProject = (id, onSuccess) =>
    appFetch('/projects/cancelProject', config('POST', {id}), onSuccess);

export const editProject = (id, title, description, observations, modality, url, offerZone, maxGroups, studentsPerGroup, bienniumId, teacherIds, onSucces, onErrors) =>
    appFetch('/projects/editProject',
        config('POST', {id, title, description, observations, modality, url, offerZone, maxGroups, studentsPerGroup, bienniumId, teacherIds}),
            onSucces,
            onErrors);

export const requestProject = (students, projectId, schoolId, onSucces, onErrors) =>
    appFetch('/projects/requestProject',
        config('POST', {students, projectId, schoolId}), onSucces, onErrors);

export const viewRequests = ({id, page, size}, onSuccess) =>{

    let path = `/projects/getAllProjectRequests/${id}?page=${page}&size=${size}`;

    appFetch(path, config('GET'), onSuccess);
}

export const asignProjects = (onSuccess) => {
    appFetch('/projects/asignProjects', config('POST'), onSuccess)
}

export const findProjectInstanceSummary = ({page, size, role}, onSuccess) => {
    appFetch(`/projects/findProjectsInstancesSummary?page=${page}&size=${size}&role=${role}`,
        config('GET'), onSuccess)
}

export const findProjectInstanceDetails = (id, onSuccess) =>
    appFetch(`/projects/projectInstanceDetails/${id}`, config('GET'), onSuccess);

export const editProjectInstance = (id, title, description, observations, modality, url, offerZone, bienniumId, teacherIds, students, studentGroup, onSuccess, onErrors) =>
    appFetch('/projects/editProjectInstance',
        config('POST', {id, title, description, observations, modality, url, offerZone, bienniumId, teacherIds, students, studentGroup}),
        onSuccess, onErrors);

export const createFacultyOrSchool = (type, name ,location, onSuccess, onErrors) =>{
    let path = `/projects/createSchool_Faculty?type=${type}&name=${name}&location=${location}`;
    appFetch(path, config('POST'), onSuccess ,onErrors);
}

