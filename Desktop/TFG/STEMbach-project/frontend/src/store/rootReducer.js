import {combineReducers} from 'redux';

import app from '../modules/app';
import users from '../modules/users';
import projects from '../modules/projects';

const rootReducer = combineReducers({
    app: app.reducer,
    users: users.reducer,
    projects: projects.reducer
});

export default rootReducer;
