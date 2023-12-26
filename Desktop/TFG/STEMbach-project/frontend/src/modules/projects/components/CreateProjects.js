import {Errors} from "../../common";
import {useEffect, useState} from "react";
import * as selectors from '../selectors';
import * as actions from '../actions';
import {FormattedMessage} from "react-intl";
import {useDispatch, useSelector} from "react-redux";
import Select from "react-select";
import {Selector} from "../../users";
import {useNavigate} from "react-router-dom";

const CreateProjects = () => {

    const navigate = useNavigate();
    const [backendErrors, setBackendErrors] = useState(null);
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [observations, setObservations] = useState('');
    const [modality, setModality] = useState('');
    const [url, setUrl] = useState("");
    const [validUrl, setValidUrl] = useState(true);
    const [offerZone, setOfferZone] = useState('');
    const [maxGroups, setMaxGroups] = useState('1');
    const [studentsPerGroup, setStudentsPerGroup] = useState('1');
    const [bienniumId, setBienniumId] = useState('');
    const [teacherIds, setTeacherIds] = useState([]);
    const [teachersSelectorHolder, setTeachersSelectorHolder] = useState([]);
    const [isValidSelector, setIsValidSelector] = useState(true);

    const teachers = useSelector(selectors.getTeacherSelectorList);
    const bienniums = useSelector(selectors.getAllBienniums);
    const dispatch = useDispatch();
    const options = [];
    //Usar concat para actualizar el array de profesores (setTeacherIds(teacherIds.concat(id)))

    let form;

    useEffect(()=>{
        if(teachers === null){
            dispatch(actions.findAllTeachers());
        }

        if(bienniums === null){
            dispatch(actions.findAllBienniums());
        }
    });

    if(teachers !== null){
        teachers.forEach(t => options.push(
            {value: t.id,
            label: t.name + " " + t.surname + " " + t.secondSurname}
    ));}


    const handleChange = (selectedOptions) => {
        setIsValidSelector(true);
        setTeachersSelectorHolder(selectedOptions);
        setTeacherIds([]);
        selectedOptions.forEach(o => {setTeacherIds(teacherIds.concat(o.value))})

        if(selectedOptions.length === 0){
            setIsValidSelector(false);
        }
    }

    const isValidUrl = urlString=> {
        try {
            setValidUrl(true);
            if(url !== "")
                return Boolean(new URL(urlString));

            return true;
        }
        catch(e){
            setValidUrl(false);
            return false;
        }
    }

    const handleSubmit = event => {
        event.preventDefault();
        if(form.checkValidity() && isValidUrl(url)){
            dispatch(actions.createProject(
                title.trim(), description.trim(), observations.trim(), modality.trim(),
                url, offerZone.trim(), maxGroups, studentsPerGroup, bienniumId, teacherIds,
                projectDetails => navigate(`/projects/ProjectDetails/${projectDetails.id}`),
                errors => setBackendErrors(errors)
            ))
        }else {
            if(teacherIds.length === 0){
                setIsValidSelector(false);
            }
            setBackendErrors(null);
            form.classList.add('was-validated');

        }
    }

    return (
        <div className="container">
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>
            <div className="card bg-light border-dark">
                <h5 className="card-header text-center">
                    <FormattedMessage id="project.projects.CreateProject.title"/>
                </h5>
                <div className="card-body">
                    <form ref={node => form = node} className="needs-validation" noValidate
                          onSubmit={e => handleSubmit(e)}>
                        <div className="form-group row">
                            <label htmlFor="title" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.projects.form.title"/>
                            </label>
                            <div className="col-md-7">
                                <input type="text" id="title" className="form-control" value={title}
                                        onChange={e => setTitle(e.target.value)} required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="description" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.projects.form.description"/>
                            </label>
                            <div className="col-md-7">
                                <textarea id="description" className="form-control"
                                          value={description} onChange={e => setDescription(e.target.value)} required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row" style={{height: "10vh"}}>
                            <label htmlFor="observations" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.projects.form.observations"/>
                            </label>
                            <div className="col-md-7">
                                <textarea id="observations" className="form-control h-100"
                                          value={observations} onChange={e => setObservations(e.target.value)}/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                             <label htmlFor="modality" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.projects.form.modality"/>
                            </label>
                            <div className="col-md-7">
                                <select name="modality" id="modalitySelector" className="form-select" onChange={e => setModality(e.target.value)} value={modality} required>
                                    <option disabled={true} value=""><FormattedMessage id="project.projects.form.modalitySelector.void"/></option>
                                    <option value="PRESENCIAL"><FormattedMessage id="project.projects.form.modalitySelector.inPerson"/></option>
                                    <option value="DISTANCIA"><FormattedMessage id="project.projects.form.modalitySelector.distance"/></option>
                                    <option value="PRESENCIAL_DISTANCIA"><FormattedMessage id="project.projects.form.modalitySelector.inPerson_distance"/></option>
                                </select>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="url" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.projects.form.url"/>
                            </label>
                            <div className="col-md-7">
                                <input type="url" id="url" className="form-control" value={url}
                                       onChange={e => setUrl(e.target.value)}/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.invalidUrl'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="offerZone" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.projects.form.offerZone"/>
                            </label>
                            <div className="col-md-7">
                                <select name="offerZone" id="offerZoneSelector" className="form-select" onChange={e => setOfferZone(e.target.value)} value={offerZone} required>
                                    <option disabled={true} value=""><FormattedMessage id="project.projects.form.offerZoneSelector.void"/></option>
                                    <option value="COR"><FormattedMessage id="project.projects.form.offerZoneSelector.cor"/></option>
                                    <option value="FERR"><FormattedMessage id="project.projects.form.offerZoneSelector.ferr"/></option>
                                    <option value="GAL"><FormattedMessage id="project.projects.form.offerZoneSelector.gal"/></option>
                                </select>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="maxGroups" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.projects.form.maxGroups"/>
                            </label>
                            <div className="col-md-7">
                                <input type="number" min="1" id="maxGroups" className="form-control" value={maxGroups}
                                       onChange={e => setMaxGroups(e.target.value)} required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="studentsPerGroup" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.projects.form.studentsPerGroup"/>
                            </label>
                            <div className="col-md-7">
                                <input type="number" min="1" id="studentsPerGroup" className="form-control" value={studentsPerGroup}
                                       onChange={e => setStudentsPerGroup(e.target.value)} required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="bienniumId" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.projects.form.biennium"/>
                            </label>
                            <div className="col-md-7">
                                <Selector id="bienniumSelector" className="form-select" value={bienniumId}
                                          onChange={e => setBienniumId(e.target.value)} data={bienniums}/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="teachers" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.projects.form.teachers"/>
                            </label>
                            <div className="col-md-7">
                                <Select isMulti={true} options={options} onChange={handleChange}
                                        value={teachersSelectorHolder} required/>
                                {!isValidSelector &&
                                <div className="text-danger">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>}
                            </div>
                        </div>
                        <div className="form-group row">
                            <div className="offset-md-3 col-md-2">
                                <button type="submit" className="btn btn-primary">
                                    <FormattedMessage id="project.projects.form.submit"/>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default CreateProjects;