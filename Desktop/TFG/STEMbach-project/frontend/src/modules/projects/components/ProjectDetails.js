import * as selectors from '../selectors';
import * as userSelectors from '../../users/selectors';
import * as actions from '../actions';
import {useDispatch, useSelector} from "react-redux";
import {Form, useNavigate, useParams} from "react-router-dom";
import {FormattedMessage} from "react-intl";
import {useEffect, useState} from "react";
import ApprovalModalView from "./ApprovalModalView";
import users, {Selector} from "../../users";
import {Errors} from "../../common";
import Select from "react-select";
import ProjectRequests from "./ProjectRequests";

const ProjectDetails = () => {

    const navigate = useNavigate();
    const dispatch = useDispatch();

    const [editMode, setEditMode] = useState(false);
    const [requestsMode, setRequestsMode] = useState(false);
    let form;

    const {id} = useParams();
    const projectId = Number(id);
    const projectDetails = useSelector(selectors.getProjectDetails);
    const bienniums = useSelector(selectors.getAllBienniums);
    const teachers = useSelector(selectors.getTeacherSelectorList);

    const user = useSelector(userSelectors.getUser);
    const isStemCoordinator = user ? user.role === "STEMCOORDINATOR" : false;
    const isUDCTeacher = user ? user.role === "UDCTEACHER" : false;
    const isCenterStemCoordinator = user ? user.role === "CENTERSTEMCOORDINATOR" : false;

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
    let teacherIdsAux = [];
    const [teachersSelectorHolder, setTeachersSelectorHolder] = useState([]);
    let teacherSelectorAux = [];
    const [isValidSelector, setIsValidSelector] = useState(true);
    const [options,setOptions] = useState([]);
    let optionsAux = [];

    useEffect(()=>{

        if(!Number.isNaN(projectId)){
            if(projectDetails === null || projectDetails.id !== id){
                dispatch(actions.findProjectDetails(id))
            }
        }

        if(!bienniums){
            dispatch(actions.findAllBienniums());
        }

        if(!teachers){
            dispatch(actions.findAllTeachers());
        }

        return () => dispatch(actions.clearProjectDetails());
    }, [id, dispatch]);

    if(!projectDetails){
        return null;
    }

    const approveProject = () => {
        dispatch(actions.approveProject(projectId));
        window.location.reload(true);
    }

    const cancelProject = () => {
        dispatch(actions.cancelProject(projectId));
        window.location.reload(true);
    }

    const enableEditMode = () =>{

        setEditMode(true);

        if(!bienniums){
            dispatch(actions.findAllBienniums());
        }

        if(!teachers){
            dispatch(actions.findAllTeachers());
        }

        setTitle(projectDetails.title);
        setDescription(projectDetails.description);
        setObservations(projectDetails.observations);
        setModality(projectDetails.modality);
        setOfferZone(projectDetails.offerZone);
        setMaxGroups(projectDetails.maxGroups);
        setStudentsPerGroup(projectDetails.studentsPerGroup);
        setUrl(projectDetails.url);
        setBienniumId(projectDetails.biennium.id);
        teacherSelectorAux = [];
        projectDetails.teacherList.forEach(t => {
            teacherIdsAux = teacherIdsAux.concat(t.id);
            teacherSelectorAux = teacherSelectorAux.concat({
                value: t.id,
                label: t.name + " " + t.surname + " " + t.secondSurname
            });
            setTeacherIds(teacherIdsAux);
            setTeachersSelectorHolder(teacherSelectorAux);
        });
        optionsAux = [];
        teachers.forEach(t =>
            optionsAux = optionsAux.concat({
                value: t.id,
                label: t.name + " " + t.surname + " " + t.secondSurname
            })
        );
        setOptions(optionsAux);
    }

    const disableEditMode = () => {
        setEditMode(false);

        setTeacherIds([]);
        setTeachersSelectorHolder([]);
    }

    const handleSubmit = event => {
        event.preventDefault();
        if(form.checkValidity()){
            dispatch(actions.editProject(
                projectDetails.id, title.trim(), description.trim(), observations.trim(), modality.trim(),
                url, offerZone.trim(), maxGroups, studentsPerGroup, bienniumId, teacherIds,
                () => window.location.reload(),
                errors => setBackendErrors(errors)
            ))
        }
    }

    const handleChange = (selectedOptions) => {
        setIsValidSelector(true);
        setTeachersSelectorHolder(selectedOptions);
        setTeacherIds([]);
        selectedOptions.forEach(o => {setTeacherIds(teacherIds.concat(o.value))})

        if(selectedOptions.length === 0){
            setIsValidSelector(false);
        }
    }



    return (
        <div className="container">
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>
            <div className="d-flex flex-row">
                {!requestsMode ?
                <button type="button" className="btn btn-primary btn-md ml-auto" onClick={() => setRequestsMode(true)}>
                    <FormattedMessage id="project.projects.projectDetails.viewRequests"/>
                </button>
                :
                <button type="button" className="btn btn-secondary btn-md ml-auto" onClick={() => setRequestsMode(false)}>
                    <FormattedMessage id="project.projects.projectDetails.cancelViewRequests"/>
                </button>
                }
            </div>
            <br/>
            {!requestsMode ?
            <form ref={node => form = node} onSubmit={e => handleSubmit(e)}>
                <div className="card bg-light border-dark">
                    <h3 className="card-header text-center">
                        {editMode ?
                        <div>
                            <input value={title} className="form-control" type="text" onChange={e => {setTitle(e.target.value)}} required/>
                        </div> :
                            projectDetails.title
                        }
                    </h3>
                    <div className="card-body">
                        <div className="row">
                            <div className="col">
                                <dt>
                                    <FormattedMessage id="project.projects.form.description"/>
                                </dt>
                                <dd>
                                    {editMode ?
                                    <div>
                                        <textarea id="description" className="form-control"
                                                  value={description} onChange={e => setDescription(e.target.value)} required/>
                                    </div> :
                                        projectDetails.description
                                    }
                                </dd>
                                {projectDetails.observations &&
                                <div>
                                    <dt>
                                        <FormattedMessage id="project.projects.form.observations"/>
                                    </dt>
                                    <dd>
                                        {editMode ?
                                        <div>
                                            <textarea id="observations" className="form-control"
                                                      value={observations} onChange={e => setObservations(e.target.value)} required/>
                                        </div> :
                                            projectDetails.observations
                                        }
                                    </dd>
                                </div>
                                }
                                <dt>
                                    <FormattedMessage id="project.projects.form.modality"/>
                                </dt>
                                <dd>
                                    {editMode ?
                                    <div>
                                        <select name="modality" id="modalitySelector" className="form-select"
                                                onChange={e => setModality(e.target.value)} value={modality} required>
                                            <option disabled={true} value=""><FormattedMessage
                                                id="project.projects.form.modalitySelector.void"/></option>
                                            <option value="PRESENCIAL"><FormattedMessage
                                                id="project.projects.form.modalitySelector.inPerson"/></option>
                                            <option value="DISTANCIA"><FormattedMessage
                                                id="project.projects.form.modalitySelector.distance"/></option>
                                            <option value="PRESENCIAL-DISTANCIA"><FormattedMessage
                                                id="project.projects.form.modalitySelector.inPerson_distance"/></option>
                                        </select>
                                    </div>
                                    :
                                    <div>
                                        {projectDetails.modality === "PRESENCIAL" && <FormattedMessage id="project.projects.form.modalitySelector.inPerson"/>}
                                        {projectDetails.modality === "DISTANCIA" && <FormattedMessage id="project.projects.form.modalitySelector.distance"/>}
                                        {projectDetails.modality === "PRESENCIAL-DISTANCIA" && <FormattedMessage id="project.projects.form.modalitySelector.inPerson_distance"/>}
                                    </div>}
                            </dd>
                                <dt>
                                    <FormattedMessage id="project.projects.form.offerZone"/>
                                </dt>
                                <dd>
                                    {editMode ?
                                    <div>
                                        <select id="offerZoneSelector" className="form-select" onChange={e => setOfferZone(e.target.value)} value={offerZone} required>
                                            <option disabled={true} value=""><FormattedMessage id="project.projects.form.offerZoneSelector.void"/></option>
                                            <option value="COR"><FormattedMessage id="project.projects.form.offerZoneSelector.cor"/></option>
                                            <option value="FERR"><FormattedMessage id="project.projects.form.offerZoneSelector.ferr"/></option>
                                            <option value="GAL"><FormattedMessage id="project.projects.form.offerZoneSelector.gal"/></option>
                                        </select>
                                        <div className="invalid-feedback">
                                            <FormattedMessage id='project.global.validator.required'/>
                                        </div>
                                    </div> :
                                    <div>
                                        {projectDetails.offerZone === "COR" && <FormattedMessage id="project.projects.form.offerZoneSelector.cor"/>}
                                        {projectDetails.offerZone === "FERR" && <FormattedMessage id="project.projects.form.offerZoneSelector.ferr"/>}
                                        {projectDetails.offerZone === "GAL" && <FormattedMessage id="project.projects.form.offerZoneSelector.gal"/>}
                                    </div>}
                                </dd>
                                <dt>
                                    <FormattedMessage id="project.projects.form.maxGroups"/>
                                </dt>
                                <dd>
                                    {editMode ?
                                    <div>
                                        <input type="number" min="1" id="maxGroups" className="form-control" value={maxGroups}
                                               onChange={e => setMaxGroups(e.target.value)} required/>
                                    </div> :
                                        projectDetails.maxGroups
                                    }
                                </dd>
                                <dt>
                                    <FormattedMessage id="project.projects.form.studentsPerGroup"/>
                                </dt>
                                <dd>
                                    {editMode ?
                                    <div>
                                        <input type="number" min="1" id="studentsPerGroup" className="form-control" value={studentsPerGroup}
                                               onChange={e => setStudentsPerGroup(e.target.value)} required/>
                                    </div> :
                                        projectDetails.studentsPerGroup
                                    }
                                </dd>
                            </div>
                            <div className="col">
                                <div>
                                    {(projectDetails.url || editMode) &&
                                    <dt>
                                        <FormattedMessage id="project.projects.form.url"/>
                                    </dt>
                                    }
                                    <dd>
                                        {editMode &&
                                        <div>
                                            <input type="url" id="url" className="form-control" value={url}
                                                   onChange={e => setUrl(e.target.value)}/>
                                        </div>}

                                        {!editMode && projectDetails.url && <a href={projectDetails.url} className="link-primary">{projectDetails.url}</a>}
                                    </dd>
                                </div>
                                <dt>
                                    <FormattedMessage id="project.projects.form.biennium"/>
                                </dt>
                                <dd>
                                    {editMode ?
                                    <div>
                                        <Selector id="bienniumSelector" className="form-select" value={bienniumId}
                                                  onChange={e => setBienniumId(e.target.value)} data={bienniums}/>
                                    </div> :
                                        projectDetails.biennium.name
                                    }
                                </dd>
                                <dt>
                                    <FormattedMessage id="project.projects.form.teachers"/>
                                </dt>
                                <dd>
                                    {editMode ?
                                    <div>
                                        <Select isMulti={true} options={options} onChange={handleChange}
                                                value={teachersSelectorHolder} required/>
                                    </div> :
                                    <div>
                                        {projectDetails.teacherList.map(t =>
                                            <div>
                                                <u>{t.name + " " + t.surname + " " + t.secondSurname}</u>
                                                <br/>
                                                <a href={`mailto: ${t.email}`} className="link-primary">{t.email}</a>
                                            </div>
                                        )}
                                    </div>}
                                </dd>
                            </div>
                        </div>
                    </div>

                    {/*Se comprueba que se puedan mostrar botones en los siguientes casos:
                        -El usuario es coordinador y el proyecto o está activo o no está revisado (botones para revisar y cancelar el proyecto)
                        -El usuario es un profesor y al mismo tiempo el creador del proyecto (botón de edición)
                        -El usuario es un coordinador de centro (botón para solicitar el proyecto)
                    */}
                    {((isStemCoordinator && (!projectDetails.revised || projectDetails.active)) ||
                        (isUDCTeacher && projectDetails.createdBy.id) || (isCenterStemCoordinator)) &&
                    <div className="card-footer">
                        <div className="d-flex flex-row-reverse justify-content-evenly">
                            {/*Botón de revisión*/}
                            {!projectDetails.revised && isStemCoordinator &&
                            <div className="d-flex flex-column">
                                <button type="button" className="btn btn-primary btn-md ml-auto" data-toggle="modal" data-target="#modalView">
                                    <FormattedMessage id="project.projects.projectDetails.reviseButton"/>
                                </button>
                                <ApprovalModalView id="modalView" actionOnSave={() => approveProject()} message={<FormattedMessage id="project.projects.projectDetails.approvalModal.text"/>}/>
                            </div>
                            }
                            {/*Botón para cancelar el proyecto*/}
                            {projectDetails.active && isStemCoordinator &&
                            <div className="d-flex flex-column">
                                <button type="button" className="btn btn-primary btn-md ml-auto" data-toggle="modal" data-target="#modalViewCancel">
                                    <FormattedMessage id="project.projects.projectDetails.dropProject"/>
                                </button>
                                <ApprovalModalView id="modalViewCancel" actionOnSave={() => cancelProject()} message={<FormattedMessage id="project.projects.projectDetails.dropModal.text"/>}/>
                            </div>
                            }
                        </div>
                        {/*Botón de edición*/}
                        {isUDCTeacher && user.id === projectDetails.createdBy.id &&
                            <div className="d-flex flex-row-reverse">
                                {!editMode ?
                                <button type="button" className="btn btn-primary btn-md ml-auto" onClick={() => enableEditMode()}>
                                    <FormattedMessage id="project.projects.projectDetails.editButton"/>
                                </button> :
                                <div>
                                    {/*Botón para confirmar cambios*/}
                                    <button type="submit" className="btn btn-primary btn-md ml-auto">
                                        <FormattedMessage id="project.projects.projectDetails.confirmEditButton"/>
                                    </button>
                                </div>
                                }
                                {/*Botón para cancelar edición*/}
                                {editMode &&
                                <button type="button" className="btn btn-secondary btn-md ml-auto" onClick={() => disableEditMode()}>
                                    <FormattedMessage id="project.projects.projectDetails.cancelEditButton"/>
                                </button>
                                }
                            </div>}
                        {isCenterStemCoordinator &&
                        <div className="d-flex flex-row-reverse">
                            {/*Botón para solicitar proyecto*/}
                            <button type="button" className="btn btn-primary btn-md ml-auto" onClick={() => navigate(`/projects/${projectId}/RequestProject`)}>
                                <FormattedMessage id="project.projects.projectDetails.requestProject"/>
                            </button>
                        </div>}
                    </div>}
                </div>
            </form> :
            <ProjectRequests/>}
        </div>

    );
}

export default ProjectDetails;