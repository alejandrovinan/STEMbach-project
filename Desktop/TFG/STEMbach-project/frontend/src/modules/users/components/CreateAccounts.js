import {useDispatch, useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import * as actions from "../actions";
import {Errors} from "../../common";
import {FormattedMessage} from "react-intl";
import {Selector} from "../index";
import * as selectors from '../selectors';

const CreateAccounts = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [role, setRole] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [name, setName] = useState('');
    const [surname, setSurname] = useState('');
    const [email, setEmail]  = useState('');
    const [dni, setDni] = useState('');
    const [facultyId, setFacultyId] = useState(1);
    const [schoolId, setSchoolId] = useState(1);
    const [backendErrors, setBackendErrors] = useState(null);
    const [passwordsDoNotMatch, setPasswordsDoNotMatch] = useState(false);

    const schools = useSelector(selectors.getSchools);
    const faculties = useSelector(selectors.getFaculties);

    let form;
    let confirmPasswordInput;

    useEffect(() => {
        dispatch(actions.findAllFaculties());
        dispatch(actions.findAllSchools());
    });

    const handleSubmit = event => {

        event.preventDefault();

        if (form.checkValidity() && checkConfirmPassword()) {

            dispatch(actions.signUp(
                {name: name.trim(),
                      password: password.trim(),
                      surname: (!surname.split(" ").at(0) ? "" : surname.split(" ").at(0).trim()),
                      secondSurname: (!surname.split(" ").at(1) ? "" : surname.split(" ").at(1).trim()),
                      email: email.trim(),
                      role: role,
                      dni: dni.trim(),
                      facultyId: facultyId,
                      schoolId: schoolId},
                () => navigate('/users/accounts'),
                errors => setBackendErrors(errors)
            ));


        } else {

            setBackendErrors(null);
            form.classList.add('was-validated');

        }

    }

    const checkConfirmPassword = () => {

        if (password !== confirmPassword) {

            confirmPasswordInput.setCustomValidity('error');
            setPasswordsDoNotMatch(true);

            return false;

        } else {
            return true;
        }

    }

    const handleConfirmPasswordChange = value => {

        confirmPasswordInput.setCustomValidity('');
        setConfirmPassword(value);
        setPasswordsDoNotMatch(false);

    }

    return (
        <div>
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>
            <div className="card bg-light border-dark">
                <h5 className="card-header">
                    <FormattedMessage id="project.users.CreateAccount.title"/>
                </h5>
                <div className="card-body">
                    <form ref={node => form = node}
                          className="needs-validation" noValidate
                          onSubmit={e => handleSubmit(e)}>
                        <div className="form-group row">
                            <label htmlFor="email" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.global.fields.email"/>
                            </label>
                            <div className="col-md-4">
                                <input type="email" id="email" className="form-control"
                                       value={email}
                                       onChange={e => setEmail(e.target.value)}
                                       required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.email'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="password" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.global.fields.password"/>
                            </label>
                            <div className="col-md-4">
                                <input type="password" id="password" className="form-control"
                                       value={password}
                                       onChange={e => setPassword(e.target.value)}
                                       required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="confirmPassword" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.users.SignUp.fields.confirmPassword"/>
                            </label>
                            <div className="col-md-4">
                                <input ref={node => confirmPasswordInput = node}
                                       type="password" id="confirmPassword" className="form-control"
                                       value={confirmPassword}
                                       onChange={e => handleConfirmPasswordChange(e.target.value)}
                                       required/>
                                <div className="invalid-feedback">
                                    {passwordsDoNotMatch ?
                                        <FormattedMessage id='project.global.validator.passwordsDoNotMatch'/> :
                                        <FormattedMessage id='project.global.validator.required'/>}
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="name" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.global.fields.name"/>
                            </label>
                            <div className="col-md-4">
                                <input type="text" id="name" className="form-control"
                                       value={name}
                                       onChange={e => setName(e.target.value)}
                                       required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="lastName" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.global.fields.lastName"/>
                            </label>
                            <div className="col-md-4">
                                <input type="text" id="lastName" className="form-control"
                                       value={surname}
                                       onChange={e => setSurname(e.target.value)}
                                       required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="password" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.global.fields.role"/>
                            </label>
                            <div className="col-md-4">
                                <select name="roles" id="roleSelector" className="form-control" onChange={e => setRole(e.target.value)} value={role} >
                                    <option value=""><FormattedMessage id="project.global.fields.role.select"/></option>
                                    <option value="UDCTEACHER"><FormattedMessage id="project.users.login.roles.udcteacher"/></option>
                                    <option value="CENTERSTEMCOORDINATOR"><FormattedMessage id="project.users.login.roles.centerstemcoordinator"/></option>
                                </select>
                            </div>
                        </div>
                        {role === "UDCTEACHER" &&
                        <div>
                            <div className="form-group row">
                                <label htmlFor="dni" className="col-md-3 col-form-label">
                                    <FormattedMessage id="project.global.fields.dni"/>
                                </label>
                                <div className="col-md-4">
                                    <input type="text" id="dni" className="form-control"
                                           value={dni}
                                           onChange={e => setDni(e.target.value)}
                                           required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="faculty" className="col-md-3 col-form-label">
                                    <FormattedMessage id="project.global.fields.faculty"/>
                                </label>
                                <div className="col-md-4">
                                    <Selector id="facultySelector" className="form-control"
                                              onChange={e => setFacultyId(e.target.value)} value={facultyId} data={faculties}/>
                                </div>
                            </div>
                        </div>
                        }
                        {role === "CENTERSTEMCOORDINATOR" &&
                        <div className="form-group row">
                            <label htmlFor="school" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.global.fields.schools"/>
                            </label>
                            <div className="col-md-4">
                                <Selector id="schoolSelector" className="form-control"
                                          onChange={e => setSchoolId(e.target.value)} value={schoolId} data={schools}/>
                            </div>
                        </div>
                        }
                        <div className="form-group row">
                            <div className="offset-md-3 col-md-2">
                                <button type="submit" className="btn btn-primary">
                                    <FormattedMessage id="project.users.CreateAccount.title"/>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default CreateAccounts;