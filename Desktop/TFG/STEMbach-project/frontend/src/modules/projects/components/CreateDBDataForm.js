import {FormattedMessage} from "react-intl";
import {useState} from "react";
import {useDispatch} from "react-redux";
import * as actions from '../actions';
import {Errors, Success} from "../../common";

const CreateDBDataForm = () => {
    const FACULTY = "0";
    const SCHOOL = "1";

    const dispatch = useDispatch();
    const [type, setType] = useState("");
    const [name, setName] = useState("");
    const [location, setLocation] = useState("");

    const [errors, setErrors] = useState();
    const [success, setSuccess] = useState(false);

    const resetForm = e => {
        setType("");
        setName("");
        setLocation("");
        setSuccess(true);
    }

    const handleSubmit = e => {
        e.preventDefault();
        dispatch(actions.createFacultyOrSchool(Number(type), name, location,
            () => resetForm(e),
                error => setErrors(error)));
    }

    return (
        <div className="container w-50">
            {success === true &&
            <Success onClose={() => setSuccess(false)} message={<FormattedMessage id="project.createDBData.sucess"/>}/>
            }
            <Errors errors={errors} onClose={() => setErrors(null)}/>
            <div className="card bg-light border-dark">
                <h5 className="card-header text-center">
                    <FormattedMessage id="project.projects.createDBdata.title"/>
                </h5>
                <div className="card-body">
                    <form className="needs-validation" onSubmit={e => handleSubmit(e)}>
                        <div className="form-group row m-2">
                            <label htmlFor="title" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.projects.DBDataForm.select.label"/>
                            </label>
                            <div className="col-md-7">
                                <select name="modality" id="modalitySelector" className="form-select"
                                        onChange={e => setType(e.target.value)} value={type} required>
                                    <option disabled={true} value=""><FormattedMessage id="project.projects.DBDataForm.select.placeholder"/></option>
                                    <option value={FACULTY}><FormattedMessage id="project.global.fields.faculty"/></option>
                                    <option value={SCHOOL}><FormattedMessage id="project.requests.table.schoolName"/></option>
                                </select>
                            </div>
                        </div>

                        {(type === FACULTY || type === SCHOOL) &&
                        <div className="form-group row m-2">
                            <label htmlFor="title" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.global.fields.name"/>
                            </label>
                            <div className="col-md-7">
                                <input type="text" id="title" className="form-control" value={name}
                                       onChange={e => setName(e.target.value)} required/>
                            </div>
                        </div>}
                        {type === SCHOOL &&
                        <div className="form-group row m-2">
                            <label htmlFor="title" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.projects.DBDataForm.location.label"/>
                            </label>
                            <div className="col-md-7">
                                <input type="text" id="title" className="form-control" value={location}
                                       onChange={e => setLocation(e.target.value)} required/>
                            </div>
                        </div>
                        }
                        {type &&
                        <div className="form-group d-flex flex-row m-2">
                            <div className="offset-md-3 col-md-7 text-right">
                                <button type="submit" className="btn btn-primary">
                                    <FormattedMessage id="project.projects.DBDataForm.submit"/>
                                </button>
                            </div>
                        </div>}
                    </form>
                </div>
            </div>
        </div>
    )
}

export default CreateDBDataForm;