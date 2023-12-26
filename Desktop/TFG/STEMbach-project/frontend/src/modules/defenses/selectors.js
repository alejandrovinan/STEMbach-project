const getModuleState = state => state.defenses;

export const getDefense = state => {
    return getModuleState(state).defense
}