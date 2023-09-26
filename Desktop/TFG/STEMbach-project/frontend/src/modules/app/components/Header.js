import {useSelector} from 'react-redux';
import {Link} from 'react-router-dom';
import {FormattedMessage} from 'react-intl';

import users from '../../users';
import React, {useState} from "react";
import * as actions from '../actions';
import {Success} from "../../common";

const Header = () => {

    const userName = useSelector(users.selectors.getEmail);
    const user = useSelector(users.selectors.getUser);
    const isStemCoordinator = user ? user.role === "STEMCOORDINATOR" : false;
    const isUDCTeacher = user ? user.role === "UDCTEACHER" : false;

    const asignProjects = () => {
        actions.asignProjects()
    }

    return (

        <nav className="navbar navbar-expand-lg navbar-light bg-light border">
            <Link className="navbar-brand" to="/">STEMBach</Link>
            <button className="navbar-toggler" type="button" 
                data-toggle="collapse" data-target="#navbarSupportedContent" 
                aria-controls="navbarSupportedContent" aria-expanded="false" 
                aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
            </button>

            <div className="collapse navbar-collapse" id="navbarSupportedContent">

                <ul className="navbar-nav mr-auto">
                    <li className="nav-item">
                        <Link className="nav-link" to="/projects/ProjectSearch">
                            <FormattedMessage id="project.projects.searchProject.header"/>
                        </Link>
                    </li>
                    {isStemCoordinator &&
                        <div className="navbar-nav">
                            <li className="nav-item">
                                <Link className="nav-link" to="/users/Accounts">
                                    <FormattedMessage id="project.user.accounts.title"/>
                                </Link>
                            </li>
                            <li className="nav-item">
                                <button className="nav-link" onClick={() => asignProjects()}>
                                    <FormattedMessage id="project.header.asignProjects.button"/>
                                </button>
                            </li>
                        </div>}
                    {isUDCTeacher &&
                    <li className="nav-item">
                        <Link className="nav-link" to="/projects/CreateProjects">
                            <FormattedMessage id="project.projects.CreateProject.title"/>
                        </Link>
                    </li>}
                </ul>
                
                {userName ? 

                <ul className="navbar-nav">

                    <li className="nav-item dropdown">

                        <a className="dropdown-toggle nav-link" href="/"
                            data-toggle="dropdown">
                            <span className="fa-solid fa-user"></span>&nbsp;
                            {userName}
                        </a>
                        <div className="dropdown-menu dropdown-menu-right">
                            <Link className="dropdown-item" to="/projects/projectInstanceSearch">
                                <FormattedMessage id="project.projectInstances.Title"/>
                            </Link>
                            <Link className="dropdown-item" to="/users/change-password">
                                <FormattedMessage id="project.users.ChangePassword.title"/>
                            </Link>
                            <div className="dropdown-divider"></div>
                            <Link className="dropdown-item" to="/users/logout">
                                <FormattedMessage id="project.app.Header.logout"/>
                            </Link>
                        </div>

                    </li>

                </ul>
                
                :

                <ul className="navbar-nav">
                    <li className="nav-item">
                        <Link className="nav-link" to="/users/login">
                            <FormattedMessage id="project.users.Login.title"/>
                        </Link>
                    </li>
                </ul>
                
                }

            </div>
        </nav>

    );

};

export default Header;
