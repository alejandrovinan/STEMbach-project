import {Errors} from "../../common";
import {useState} from "react";
import {FormattedMessage} from "react-intl";
import {useDispatch, useSelector} from "react-redux";
import * as userSelectors from "../../users/selectors";
import * as actions from '../actions';
import {useNavigate, useParams} from "react-router-dom";

const RequestForm = () => {

    let form;

    const {id} = useParams();
    const projectId = Number(id);
    const user = useSelector(userSelectors.getUser);
    const schoolId = user ? user.schoolId : null;
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const [backendErrors, setBackendErrors] = useState("");
    const [students,setStudents] = useState([
        {
            name: "",
            surname: "",
            secondSurname: "",
            dni: "",
            role: "STUDENT",
            schoolId: schoolId
        }
    ]);

    const generateForm = () => {
        setStudents([...students,
            {
                name: "",
                surname: "",
                secondSurname: "",
                dni: "",
                role: "STUDENT",
                schoolId: schoolId
            }
        ]);
    }

    const removeStudent = () => {
        let newStudents = [...students];
        newStudents.splice(students.length - 1, 1);
        setStudents(newStudents);
    }

    const handleUserUpdate = (position, field, data) => {
        const updatedObjects = students.map((user, i) => {
            if (i === position){
                switch (field) {
                    case "name":
                        return {...user, name: data};
                    case "surname":
                        return {...user, surname: data};
                    case "secondSurname":
                        return {...user, secondSurname: data};
                    case "dni":
                        return {...user, dni: data};
                    default:
                        return user;
                }
            } else {
                return user;
            }
        })

        setStudents(updatedObjects);
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        if(form.checkValidity()){
            dispatch(actions.requestProject(students, projectId, schoolId,
                request => navigate(`/projects/ProjectDetails/${projectId}`),
                errors => setBackendErrors(errors)))
        }
    }

    return(
        <div className="container">
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>
            <div className="card bg-light border-dark">
                <h5 className="card-header text-center">
                    <FormattedMessage id="project.projects.RequestForm.title"/>
                </h5>
                <br/>
                <div className="d-flex flex-row justify-content-end">
                    <div className="d-flex flex-column col-1">
                        <button className="btn btn-secondary rounded-circle" onClick={() => removeStudent()}>
                            <i className="fa-solid fa-minus"></i>
                        </button>
                    </div>
                    <div className="d-flex flex-column col-1">
                        <button className="btn btn-primary rounded-circle" onClick={() => generateForm()}>
                            <i className="fa-solid fa-plus"></i>
                        </button>
                    </div>
                </div>
                <div className="card-body">
                    <form ref={node => form = node} className="needs-validation" onSubmit={e => handleSubmit(e)}>
                        {students.length === 0 &&
                            <h5 className="text-center">
                                <FormattedMessage id="project.prijects.RequestForm.noStudents"/>
                            </h5>
                        }
                        {students.map((student, index) => (
                            <div>
                                <div className="d-flex flex-row justify-content-center">
                                    <div className="card bg-light border-dark">
                                        <div className="form-group row">
                                            <div className="col-md-3">
                                                <label className="col-form-label">
                                                    <FormattedMessage id="project.projects.RequestForm.name"/>
                                                </label>
                                                <input type="text" id={`name_${index}`} className="form-control" value={student.name}
                                                       onChange={e => handleUserUpdate(index, "name", e.target.value)} required/>
                                            </div>
                                            <div className="col-md-3">
                                                <label className="col-form-label">
                                                    <FormattedMessage id="project.projects.RequestForm.surname"/>
                                                </label>
                                                <input type="text" id={`surname_${index}`} className="form-control" value={student.surname}
                                                       onChange={e => handleUserUpdate(index, "surname", e.target.value)} required/>
                                            </div>
                                            <div className="col-md-3">
                                                <label className="col-form-label">
                                                    <FormattedMessage id="project.projects.RequestForm.secondSurname"/>
                                                </label>
                                                <input type="text" id={`secondSurname_${index}`} className="form-control" value={student.secondSurname}
                                                       onChange={e => handleUserUpdate(index, "secondSurname", e.target.value)} required/>
                                            </div>
                                            <div className="col-md-3">
                                                <label className="col-form-label">
                                                    <FormattedMessage id="project.projects.RequestForm.dni"/>
                                                </label>
                                                <input type="text" id={`dni_${index}`} className="form-control" value={student.dni}
                                                       onChange={e => handleUserUpdate(index, "dni", e.target.value)} required/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <br/>
                            </div>
                        ))}
                        <div className="d-flex flex-row justify-content-end">
                            <button type="submit" className="btn btn-primary btn-md ">
                                <FormattedMessage id="project.projects.RequestForm.requestProject"/>
                            </button>
                        </div>
                    </form>
                </div>
            </div>

        </div>
    )
}

export default RequestForm;