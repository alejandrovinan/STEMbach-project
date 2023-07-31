import {init} from './appFetch';
import * as userService from './userService';
import * as projectsService from './projectsService';

export {default as NetworkError} from "./NetworkError";

// eslint-disable-next-line
export default {init, userService, projectsService};
