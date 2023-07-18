import {useSelector} from 'react-redux';
import {Route, Routes} from 'react-router-dom';

import AppGlobalComponents from './AppGlobalComponents';
import Home from './Home';
import {Login, SignUp, UpdateProfile, ChangePassword, Logout, Accounts, CreateAccounts} from '../../users';
import users from '../../users';

const Body = () => {

    const loggedIn = useSelector(users.selectors.isLoggedIn);
    const user = useSelector(users.selectors.getUser);
    const isStemCoordinator = user ? user.role === "STEMCOORDINATOR" : false;
    
   return (

        <div className="container">
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
            </Routes>
        </div>

    );

};

export default Body;
