import {Errors} from "../../common";
import {useEffect, useState} from "react";
import {FormattedMessage} from "react-intl";
import DatePicker from 'react-datepicker'
import {useDispatch, useSelector} from "react-redux";
import * as projectSelectors from "../../projects/selectors";
import Select from "react-select";
import * as projectActions from "../../projects/actions";
import * as actions from "../actions";
import {useNavigate, useParams} from "react-router-dom";
import DefenseDetails from "./DefenseDetails";


const CreateDefense = () => {

    const {id} = useParams();
    const projectId = Number(id);
    const [backendErrors, setBackendErrors] = useState(null);
    const [date, setDate] = useState(new Date());
    const [place, setPlace] = useState("");
    const [mark, setMark] = useState();
    const [observations, setObservations] = useState("");
    const [teacherIds, setTeacherIds] = useState([]);
    const [teachersSelectorHolder, setTeachersSelectorHolder] = useState([]);
    const [files, setFiles] = useState([]);

    const teachers = useSelector(projectSelectors.getTeacherSelectorList);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const options = [];

    let form;

    useEffect(()=>{
        if(teachers === null){
            dispatch(projectActions.findAllTeachers());
        }

    });

    if(teachers !== null){
        teachers.forEach(t => options.push(
            {value: t.id,
                label: t.name + " " + t.surname + " " + t.secondSurname}
        ));}

    const handleChange = (selectedOptions) => {
        setTeachersSelectorHolder(selectedOptions);
        setTeacherIds([]);
        selectedOptions.forEach(o => {setTeacherIds(teacherIds.concat(o.value))})
    }

    const fileUpload = e => {
        e.preventDefault();
        let filesUpload = e.target.files;
        let filesAux = Array.from(filesUpload).concat(files);
        setFiles(filesAux);
    }

    const deleteFile = (fileName,e) => {
        e.preventDefault();
        const updatedFiles = files.filter((file) => file.name !== fileName);
        setFiles(updatedFiles);
    }

    const handleSubmit = e => {
        e.preventDefault();
        const formData = new FormData();
        formData.append("place", place.trim());
        formData.append("date", date.toISOString());
        formData.append("mark", mark ? mark : "");
        formData.append("observations", observations);

        for (const item of files) {
            formData.append("recordFiles", item);
        }

        for (const item of teacherIds) {
            formData.append("teachers", item);
        }

        dispatch(actions.createDefense(projectId, formData,
            () => navigate(`/defenses/defenseDetails/${projectId}`),
            errors => setBackendErrors(errors)
        ))

    }

    return(
        <div className="container w-50">
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>
            <div className="card bg-light border-dark">
                <h5 className="card-header text-center">
                    <FormattedMessage id="project.defenses.CreateDefense.title"/>
                </h5>
                <div className="card-body">
                    <form ref={node => form = node} className="needs-validation" noValidate onSubmit={e => handleSubmit(e)}>
                        <div className="form-group row">
                            <label htmlFor="title" className="col-md-2 col-form-label">
                                <FormattedMessage id="project.defenses.CreateDefense.form.date"/>
                            </label>
                            <div className="col-md-4">
                                <DatePicker selected={date} onChange={(date) => setDate(date)}/>
                            </div>
                            <label htmlFor="title" className="col-sm-2 col-form-label">
                                <FormattedMessage id="project.defenses.CreateDefense.form.mark"/>
                            </label>
                            <div className="col-md-2">
                                <input type="number" id="title" className="form-control" value={mark}
                                       onChange={e => setMark(e.target.value)} min="0" max="10" step=".01"/>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="title" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.defenses.CreateDefense.form.place"/>
                            </label>
                            <div className="col-md-7">
                                <input type="text" id="title" className="form-control" value={place}
                                       onChange={e => setPlace(e.target.value)}/>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="title" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.defenses.CreateDefense.form.observations"/>
                            </label>
                            <div className="col-md-7">
                                <textarea id="title" className="form-control" value={observations}
                                       onChange={e => setObservations(e.target.value)}/>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="title" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.defenses.CreateDefense.form.judges"/>
                            </label>
                            <div className="col-md-7">
                                <Select isMulti={true} options={options} onChange={handleChange}
                                        value={teachersSelectorHolder}/>
                            </div>
                        </div>
                        <div className="form-group d-flex flex-row justify-content-center">
                            <label htmlFor="photo-upload">
                                <input id="photo-upload" className="form-control" type="file"
                                       accept="image/png, image/jpeg, text/plain, application/pdf, application/msword, application/vnd.ms-excel"
                                       multiple onChange={e => fileUpload(e)}/>
                            </label>
                        </div>
                        {files !== null &&
                            <div className="list-group">
                                {files.map((file, index) => (
                                    <div className="list-group-item">
                                        <div className="d-flex flex-row">
                                            {file.name}
                                            <div className="align-self-end ml-auto">
                                                <button className="btn btn-danger" onClick={(e) => {deleteFile(file.name, e)}}>
                                                    <i className="fa-solid fa-trash"/>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        }
                        <div className="form-group row">
                            <div className="offset-md-7 col-md-5">
                                <button type="submit" className="btn btn-primary">
                                    <FormattedMessage id="project.defenses.form.submit"/>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default CreateDefense;