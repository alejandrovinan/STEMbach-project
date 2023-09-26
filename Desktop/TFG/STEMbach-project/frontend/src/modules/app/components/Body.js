import {useSelector} from 'react-redux';
import {Route, Routes} from 'react-router-dom';

import AppGlobalComponents from './AppGlobalComponents';
import Home from './Home';
import {Login, SignUp, UpdateProfile, ChangePassword, Logout, Accounts, CreateAccounts} from '../../users';
import users from '../../users';
import {CreateProjects, ProjectDetails, ProjectSearch} from "../../projects";
import RequestForm from "../../projects/components/RequestForm";
import ProjectInstanceSearch from "../../projects/components/ProjectInstanceSearch";

const Body = () => {

    const loggedIn = useSelector(users.selectors.isLoggedIn);
    const user = useSelector(users.selectors.getUser);
    const isStemCoordinator = user ? user.role === "STEMCOORDINATOR" : false;
    const isUDCTeacher = user ? user.role === "UDCTEACHER" : false;
    const isCenterStemCoordinator = user ? user.role === "CENTERSTEMCOORDINATOR" : false;
    
   return (

        <div className="container-fluid">
            <br/>
            <AppGlobalComponents/>
            <Routes>
                <Route path="/*" element={<Home/>}/>
                {loggedIn && <Route path="/users/update-profile" element={<UpdateProfile/>}/>}
                {loggedIn && <Route path="/users/change-password" element={<ChangePassword/>}/>}
                {loggedIn && <Route path="/users/logout" element={<Logout/>}/>}
                {!loggedIn && <Route path="/users/login" element={<Login/>}/>}
                {!loggedIn && <Route path="/users/signup" element={<SignUp/>}/>}
                {loggedIn && isStemCoordinator && <Route path="/users/Accounts" element={<Accounts/>}/>}
                {loggedIn && isStemCoordinator && <Route path="/users/CreateAccounts" element={<CreateAccounts/>}/>}
                {loggedIn && (isUDCTeacher || isStemCoordinator) && <Route path="/projects/CreateProjects" element={<CreateProjects/>}/>}
                <Route path="/projects/ProjectDetails/:id" element={<ProjectDetails/>}/>
                <Route path="/projects/ProjectSearch" element={<ProjectSearch/>}/>
                {loggedIn && isCenterStemCoordinator && <Route path="/projects/:id/RequestProject" element={<RequestForm/>}/>}
                {loggedIn && <Route path="/projects/projectInstanceSearch" element={<ProjectInstanceSearch/>}/>}
            </Routes>
        </div>

    );

};

export default Body;
