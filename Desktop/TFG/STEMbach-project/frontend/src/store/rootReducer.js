import {combineReducers} from 'redux';

import app from '../modules/app';
import users from '../modules/users';
import projects from '../modules/projects';
import defenses from '../modules/defenses';

const rootReducer = combineReducers({
    app: app.reducer,
    users: users.reducer,
    projects: projects.reducer,
    defenses: defenses.reducer
});

export default rootReducer;
