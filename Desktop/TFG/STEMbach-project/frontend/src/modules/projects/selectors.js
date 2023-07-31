const getModuleState = state => state.projects;

export const getProjectDetails = state => getModuleState(state).projectDetails;

export const getTeacherSelectorList = state => getModuleState(state).teacherSelectorList;

export const getAllBienniums = state => getModuleState(state).bienniums;