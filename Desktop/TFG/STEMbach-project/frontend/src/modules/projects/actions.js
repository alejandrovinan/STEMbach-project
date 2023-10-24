import backend from '../../backend';
import * as actionTypes from './actionTypes';
import * as selectors from './selectors';

const createProjectCompleted = projectDetails => ({
    type: actionTypes.PROJECT_CREATED_COMPLETED,
    projectDetails
});

export const createProject = (title, description, observations, modality, url, offerZone, maxGroups, studentsPerGroup, bienniumId, teacherIds, onSuccess, onErrors) =>
        dispatch => backend.projectsService.createProject(title, description, observations, modality, url, offerZone, maxGroups, studentsPerGroup, bienniumId, teacherIds,
            projectDetails => {
                        dispatch(createProjectCompleted(projectDetails));
                        onSuccess(projectDetails)
                    },
                    onErrors);

const findAllTeachersCompleted = teacherSelectorList => ({
    type: actionTypes.FIND_ALL_TEACHERS_COMPLETED,
    teacherSelectorList
});

export const findAllTeachers = () => (dispatch, getState) => {
    const teachers = selectors.getTeacherSelectorList(getState());

    if(!teachers){
        backend.userService.findAllUDCTeachers(
            teachers => dispatch(findAllTeachersCompleted(teachers))
        );
    }
};

const findAllBienniumsCompleted = bienniums => ({
    type: actionTypes.FIND_ALL_BIENNIUM_COMPLETED,
    bienniums
});

export const findAllBienniums = () => (dispatch, getState) => {

    const bienniums = selectors.getAllBienniums(getState());

    if(!bienniums){
        backend.projectsService.findAllBienniums(
            bienniums => dispatch(findAllBienniumsCompleted(bienniums))
        );
    }
};

const findProjectDetailsCompleted = projectDetails => ({
    type: actionTypes.FIND_PROJECT_DETAILS_COMPLETED,
    projectDetails
});

export const findProjectDetails = id => dispatch => {
    backend.projectsService.findProjectDetails(id,
        projectDetails => dispatch(findProjectDetailsCompleted(projectDetails)));
};

export const clearProjectDetails = () =>({
    type: actionTypes.CLEAR_PROJECT_DETAILS_COMPLETED
});

export const clearProjectSearch = () => ({
    type: actionTypes.CLEAR_PROJECTS_SEARCH
});

const findProjectsByCriteriaCompleted = projectResults => ({
    type: actionTypes.FIND_PROJECTS_BY_CRITERIA_COMPLETED,
    projectResults
});


export const findProjectsByCriteria = criteria => dispatch => {
    dispatch(clearProjectSearch());
    backend.projectsService.findProjectsByCriteria(criteria,
        result => dispatch(findProjectsByCriteriaCompleted({criteria, result}))
    );
};

export const previousFindProjectsByCriteria = criteria => dispatch =>{
    dispatch(findProjectsByCriteria({...criteria, page: criteria.page-1}));
};

export const nextFindProjectsByCriteria = criteria => dispatch =>{
    dispatch(findProjectsByCriteria({...criteria, page: criteria.page+1}));
};

const approveProjectCompleted = () => ({
    type: actionTypes.APPROVE_PROJECT_COMPLETED
});

export const approveProject = projectId => dispatch => {
    backend.projectsService.approveProject(projectId,
        () => dispatch(approveProjectCompleted()));
};

const cancelProjectCompleted = () => ({
    type: actionTypes.CANCEL_PROJECT_COMPLETED
});

export const cancelProject = projectId => dispatch => {
    backend.projectsService.cancelProject(projectId,
        () => dispatch(cancelProjectCompleted()));
};

const editProjectCompleted = projectDetails => ({
    type: actionTypes.PROJECT_EDITED_COMPLETED,
    projectDetails
});

export const editProject = (id, title, description, observations, modality, url, offerZone, maxGroups, studentsPerGroup, bienniumId, teacherIds, onSuccess, onErrors) =>
    dispatch => backend.projectsService.editProject(id, title, description, observations, modality, url, offerZone, maxGroups, studentsPerGroup, bienniumId, teacherIds,
        projectDetails => {
            dispatch(editProjectCompleted(projectDetails));
            onSuccess()
        },
        onErrors);

const requestProjectCompleted = request => ({
    type: actionTypes.CREATE_REQUEST_COMPLETED,
    request
})

export const requestProject = (students, projectId, schoolId, onSucces, onErrors) => dispatch =>
    backend.projectsService.requestProject(students, projectId, schoolId,
            request => {
                        dispatch(requestProjectCompleted(request));
                        onSucces();
                    },
        onErrors);

const viewRequestsCompleted = result =>({
    type: actionTypes.VIEW_REQUESTS_COMPLETED,
    result
})

export const viewRequests = criteria => dispatch =>{
    backend.projectsService.viewRequests(criteria,
        result => dispatch(viewRequestsCompleted({criteria, result})));
}

export const previousViewRequests = criteria => dispatch => {
    dispatch(viewRequests({...criteria, page: criteria.page - 1}));
}

export const nextViewRequests = criteria => dispatch => {
    dispatch(viewRequests({...criteria, page: criteria.page + 1}));
}

const findProjectInstanceSummaryCompleted = result => ({
    type: actionTypes.FIND_PROJECT_INSTANCES_SUMMARY_COMPLETED,
    result
})

export const findProjectInstancesSummary = criteria => dispatch => {
    backend.projectsService.findProjectInstanceSummary(criteria,
        result => dispatch(findProjectInstanceSummaryCompleted({criteria, result})));
}

export const previousFindProjectInstancesSummary = criteria => dispatch => {
    dispatch(findProjectInstancesSummary({...criteria, page: criteria.page - 1}));
}

export const nextFindProjectInstancesSummary = criteria => dispatch => {
    dispatch(findProjectInstancesSummary({...criteria, page: criteria.page + 1}));
}

export const clearProjectInstancesSearch = () => ({
    type: actionTypes.CLEAR_PROJECT_INSTANCES_SUMMARY_COMPLETED
})

const findProjectInstanceDetailsCompleted = projectInstanceDetails => ({
    type: actionTypes.FIND_PROJECT_INSTANCE_DETAILS_COMPLETED,
    projectInstanceDetails
});

export const findProjectInstanceDetails = id => dispatch => {
    backend.projectsService.findProjectInstanceDetails(id,
        projectInstanceDetails => dispatch(findProjectInstanceDetailsCompleted(projectInstanceDetails)));
};

const editProjectInstanceCompleted = projectInstanceDetails => ({
    type: actionTypes.EDIT_PROJECT_INSTANCE_COMPLETED,
    projectInstanceDetails
})

export const editProjectInstance = (id, title, description, observations, modality, url, offerZone, bienniumId, teacherIds, students, studentGroup, onSucess, onErrors) => dispatch => {
    backend.projectsService.editProjectInstance(id, title, description, observations, modality, url, offerZone, bienniumId, teacherIds, students, studentGroup,
        projectInstanceDetails =>{
            dispatch(editProjectInstanceCompleted(projectInstanceDetails));
            onSucess();
        },
        onErrors);
}
