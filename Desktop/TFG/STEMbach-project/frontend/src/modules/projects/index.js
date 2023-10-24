import * as actionTypes from './actionTypes';
import reducer from './reducer';
import * as actions from "./actions";
import * as selectors from "./selectors";

export {default as CreateProjects} from './components/CreateProjects';
export {default as ProjectDetails} from './components/ProjectDetails';
export {default as ProjectSearch} from './components/ProjectSearch';
export {default as FilerSidebar} from './components/FilterSidebar';
export {default as Request} from './components/RequestForm';
export {default as ProjectInstacesSearch} from './components/ProjectInstanceSearch';
export {default as ProjectInstanceDetails} from './components/ProjectInstanceDetails';

// eslint-disable-next-line
export default {actions, actionTypes, reducer, selectors};