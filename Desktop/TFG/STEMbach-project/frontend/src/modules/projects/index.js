import * as actionTypes from './actionTypes';
import reducer from './reducer';
import * as actions from "./actions";
import * as selectors from "./selectors";

export {default as CreateProjects} from './components/CreateProjects';
export {default as ProjectDetails} from './components/ProjectDetails';

// eslint-disable-next-line
export default {actions, actionTypes, reducer, selectors};