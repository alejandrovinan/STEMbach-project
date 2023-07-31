import backend from '../../backend';
import * as actionTypes from './actionTypes';
import * as selectors from './selectors';
import {FIND_ALL_BIENNIUM_COMPLETED} from "./actionTypes";

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
})

export const findAllTeachers = () => (dispatch, getState) => {
    const teachers = selectors.getTeacherSelectorList(getState());

    if(!teachers){
        backend.userService.findAllUDCTeachers(
            teachers => dispatch(findAllTeachersCompleted(teachers))
        );
    }
}

const findAllBienniumsCompleted = bienniums => ({
    type: actionTypes.FIND_ALL_BIENNIUM_COMPLETED,
    bienniums
})

export const findAllBienniums = () => (dispatch, getState) => {

    const bienniums = selectors.getAllBienniums(getState());

    if(!bienniums){
        backend.projectsService.findAllBienniums(
            bienniums => dispatch(findAllBienniumsCompleted(bienniums))
        );
    }
}

const findProjectDetailsCompleted = projectDetails => ({
    type: actionTypes.FIND_PROJECT_DETAILS_COMPLETED,
    projectDetails
});

export const findProjectDetails = id => dispatch => {
    backend.projectsService.findProjectDetails(id,
        projectDetails => dispatch(findProjectDetailsCompleted(projectDetails)));
}

export const clearProjectDetails = () =>({
    type: actionTypes.CLEAR_PROJECT_DETAILS_COMPLETED
})