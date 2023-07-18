import {FormattedMessage} from "react-intl";
import React from "react";
import {useHistory, useNavigate} from "react-router-dom";



const Accounts = () => {

    const navigate = useNavigate();
    const navigateToCreateAccounts = () =>{
        navigate('/users/CreateAccounts');
    }

    return(
        <div>
            <div className="container">
                <div className="row align-items-center">
                    <div className="col-4"></div>
                    <div className="col-4 d-flex justify-content-center">
                        <h2 className="">
                            <FormattedMessage id="project.user.accounts.title"/>
                        </h2>
                    </div>
                    <div className="col-4 d-flex justify-content-end">
                        <button type="button" className="btn btn-outline-primary btn-lg align-self-lg-end" onClick={() => {navigateToCreateAccounts()}}>
                            <FormattedMessage id="project.user.accounts.createAccountsButton"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Accounts;