const getModuleState = state => state.projects;

export const getProjectDetails = state => getModuleState(state).projectDetails;

export const getProjectInstanceDetails = state => getModuleState(state).projectInstanceDetails;

export const getTeacherSelectorList = state => getModuleState(state).teacherSelectorList;

export const getAllBienniums = state => getModuleState(state).bienniums;

export const getBienniumName = (bienniums, id) => {

    if(!bienniums){
        return "";
    }

    const biennium = bienniums.find(biennium => biennium.id === id);

    if(!biennium){
        return "";
    }

    return biennium.name;
}

export const getAllProjectResults = state => getModuleState(state).projectResults;

export const getAllRequests = state => getModuleState(state).requests;

export const getAllProjectInstancesResults = state => getModuleState(state).projectInstancesResults;