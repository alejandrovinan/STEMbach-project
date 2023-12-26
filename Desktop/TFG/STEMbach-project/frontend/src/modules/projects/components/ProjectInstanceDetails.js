import {FormattedMessage} from "react-intl";
import {Selector} from "../../users";
import Select from "react-select";
import {Errors} from "../../common";
import {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import * as selectors from "../selectors";
import * as actions from "../actions";
import * as userSelectors from "../../users/selectors";
import * as defenseActions from "../../defenses/actions";

const ProjectInstanceDetails = () => {

    const navigate = useNavigate();
    const dispatch = useDispatch();

    const [editMode, setEditMode] = useState(false);

    const {id} = useParams();
    const projectId = Number(id);
    const user = useSelector(userSelectors.getUser);
    const isStemCoordinator = user ? user.role === "STEMCOORDINATOR" : false;
    const isCenterStemCoordinator = user ? user.role === "CENTERSTEMCOORDINATOR" : false;
    const projectDetails = useSelector(selectors.getProjectInstanceDetails);
    const bienniums = useSelector(selectors.getAllBienniums);
    const teachers = useSelector(selectors.getTeacherSelectorList);

    const [existDefense, setExistDefense] = useState(false);

    const [backendErrors, setBackendErrors] = useState(null);
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [observations, setObservations] = useState('');
    const [modality, setModality] = useState('');
    const [url, setUrl] = useState("");
    const [offerZone, setOfferZone] = useState('');
    const [active, setActive] = useState('');
    const [bienniumId, setBienniumId] = useState('');
    const [teacherIds, setTeacherIds] = useState([]);
    const [students, setStudents] = useState([]);
    const [teachersSelectorHolder, setTeachersSelectorHolder] = useState([]);
    const [options,setOptions] = useState([]);
    const [isValidSelector, setIsValidSelector] = useState(true);
    const [newStudents, setNewStudents] = useState([
        {
            name: "",
            surname: "",
            secondSurname: "",
            dni: "",
            role: "STUDENT",
            schoolId: null
        }
    ]);

    let teacherIdsAux = [];
    let teacherSelectorAux = [];
    let optionsAux = [];

    let form;

    useEffect(()=>{

        if(!Number.isNaN(projectId)){
            if(projectDetails === null || projectDetails.id !== id){
                dispatch(actions.findProjectInstanceDetails(id))
            }

        }

        if(!bienniums){
            dispatch(actions.findAllBienniums());
        }

        if(!teachers){
            dispatch(actions.findAllTeachers());
        }

        dispatch(defenseActions.checkIfDefenseExists(projectId,
            result => {
            setExistDefense(result)
                if (result === true){
                    dispatch(defenseActions.findDefenseDetails(id, errors => setBackendErrors(errors)));
                }
        }))

    }, [id, dispatch]);

    if(!projectDetails){
        return null;
    }

    const enableEditMode = e =>{
        e.preventDefault();

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

        let studentsAux = [];
        projectDetails.students.forEach(s => {
            studentsAux = studentsAux.concat([{
                name: s.name,
                surname: s.surname,
                secondSurname: s.secondSurname,
                dni: s.dni,
                role: "STUDENT",
                schoolId: null
            }])
        });
        setNewStudents(studentsAux);
    }

    const disableEditMode = () => {
        setEditMode(false);

        setTeacherIds([]);
        setTeachersSelectorHolder([]);
    }

    const handleSubmit = e => {
        e.preventDefault();
        dispatch(actions.editProjectInstance(
            projectId,
            title,
            description,
            observations,
            modality,
            url,
            offerZone,
            bienniumId,
            teacherIds,
            newStudents,
            {
                id: projectDetails.students[0].groupId,
                hasProject: true,
                school: null
            },
            () => window.location.reload(),
            errors => setBackendErrors(errors)
        ))
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

    const generateForm = (e) => {
        e.preventDefault();
        setNewStudents([...newStudents,
            {
                name: "",
                surname: "",
                secondSurname: "",
                dni: "",
                role: "STUDENT",
                schoolId: null
            }
        ]);
    }

    const removeStudent = (e) => {
        e.preventDefault();
        let newStudentsAux = [...newStudents];
        if(newStudents.length > 1){
            newStudentsAux.splice(newStudents.length - 1, 1);
        }
        setNewStudents(newStudentsAux);
    }

    const removeCurrentStudent = (e, index) => {
        e.preventDefault();
        let newStudentsAux = [...newStudents];
        if(newStudentsAux.length > 1){
            newStudentsAux.splice(index, 1);
        }
        setNewStudents(newStudentsAux);
    }

    const handleUserUpdate = (position, field, data) => {
        const updatedObjects = newStudents.map((user, i) => {
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

        setNewStudents(updatedObjects);
    }

    return(
        <div className="container">
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>
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
                                                <option value="PRESENCIAL_DISTANCIA"><FormattedMessage
                                                    id="project.projects.form.modalitySelector.inPerson_distance"/></option>
                                            </select>
                                        </div>
                                        :
                                        <div>
                                            {projectDetails.modality === "PRESENCIAL" && <FormattedMessage id="project.projects.form.modalitySelector.inPerson"/>}
                                            {projectDetails.modality === "DISTANCIA" && <FormattedMessage id="project.projects.form.modalitySelector.distance"/>}
                                            {projectDetails.modality === "PRESENCIAL_DISTANCIA" && <FormattedMessage id="project.projects.form.modalitySelector.inPerson_distance"/>}
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
                                    <FormattedMessage id="project.users.login.roles.centerstemcoordinator"/>
                                </dt>
                                <dd>
                                    {projectDetails.centerSTEMCoordinator.name + " " + projectDetails.centerSTEMCoordinator.surname + " " + projectDetails.centerSTEMCoordinator.secondSurname}
                                    <br/>
                                    <a href={`mailto: ${projectDetails.centerSTEMCoordinator.email}`} className="link-primary">{projectDetails.centerSTEMCoordinator.email}</a>
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
                                                    {t.name + " " + t.surname + " " + t.secondSurname}
                                                    <br/>
                                                    <a href={`mailto: ${t.email}`} className="link-primary">{t.email}</a>
                                                </div>
                                            )}
                                        </div>}
                                </dd>
                                <dt>
                                    {editMode ?
                                        <div className="d-flex flex-row">
                                            <FormattedMessage id="project.requests.table.students"/>
                                            <div className="align-self-end ml-auto">
                                                <button className="btn btn-secondary rounded-circle" onClick={e => removeStudent(e)}>
                                                    <i className="fa-solid fa-minus"></i>
                                                </button>
                                                <button className="btn btn-primary rounded-circle" onClick={e => generateForm(e)}>
                                                    <i className="fa-solid fa-plus"></i>
                                                </button>
                                            </div>
                                        </div> :

                                        <FormattedMessage id="project.requests.table.students"/>

                                    }

                                </dt>
                                <dd>
                                {editMode ?
                                    <div>
                                        {newStudents.map((s, index) =>
                                            <div className="row">
                                                <div className="col-md-3">
                                                    <label className="col-form-label">
                                                        <FormattedMessage id="project.projects.RequestForm.name"/>
                                                    </label>
                                                    <input type="text" id={`name_${index}`} className="form-control" value={s.name}
                                                           onChange={e => handleUserUpdate(index, "name", e.target.value)} required/>
                                                </div>
                                                <div className="col-md-2">
                                                    <label className="col-form-label">
                                                        <FormattedMessage id="project.projects.RequestForm.surname"/>
                                                    </label>
                                                    <input type="text" id={`surname_${index}`} className="form-control" value={s.surname}
                                                           onChange={e => handleUserUpdate(index, "surname", e.target.value)} required/>
                                                </div>
                                                <div className="col-md-2">
                                                    <label className="col-form-label">
                                                        <FormattedMessage id="project.projects.RequestForm.surname"/>
                                                    </label>
                                                    <input type="text" id={`secondSurname_${index}`} className="form-control" value={s.secondSurname}
                                                           onChange={e => handleUserUpdate(index, "secondSurname", e.target.value)} required/>
                                                </div>
                                                <div className="col-md-3">
                                                    <label className="col-form-label">
                                                        <FormattedMessage id="project.projects.RequestForm.dni"/>
                                                    </label>
                                                    <input type="text" id={`dni_${index}`} className="form-control" value={s.dni}
                                                           onChange={e => handleUserUpdate(index, "dni", e.target.value)} required/>
                                                </div>
                                                <div className="col-md-1">
                                                    <label className="col-form-label"></label>
                                                    <button type="button" className="btn btn-danger" onClick={e => removeCurrentStudent(e, index)}>
                                                        <i className="fa-solid fa-trash"/>
                                                    </button>
                                                </div>
                                            </div>
                                        )}
                                    </div> :
                                    <div>
                                        {projectDetails.students.map(s =>
                                            <div>
                                                {s.name + " " + s.surname + " " + s.secondSurname + " - " + s.dni}
                                                <br/>
                                            </div>
                                        )}
                                    </div>
                                }
                                </dd>
                            </div>
                        </div>
                    </div>

                    {/*Se comprueba que se puedan mostrar botones en los siguientes casos:
                        -El usuario es coordinador y el proyecto o está activo o no está revisado (botones para revisar y cancelar el proyecto)
                        -El usuario es un profesor y al mismo tiempo el creador del proyecto (botón de edición)
                        -El usuario es un coordinador de centro (botón para solicitar el proyecto)
                    */}
                    <div className="card-footer">
                        <div className="d-flex flex-row-reverse">
                            {(user.id === projectDetails.createdBy || isStemCoordinator || user.id === projectDetails.centerSTEMCoordinator.id) &&
                            <div>
                                {!editMode ?
                                    <div>
                                        {existDefense === true ?
                                        <button type="button" className="btn btn-primary btn-md ml-auto m-1" onClick={() => navigate(`/defenses/defenseDetails/${projectId}`)}>
                                            <FormattedMessage id="project.defenseDetails.title"/>
                                        </button>:
                                        <button type="button" className="btn btn-primary btn-md ml-auto m-1" onClick={() => navigate(`/defenses/createDefense/${projectId}`)}>
                                            <FormattedMessage id="project.defenses.CreateDefense.title"/>
                                        </button>
                                        }
                                        <button type="button" className="btn btn-primary btn-md ml-auto m-1" onClick={e => enableEditMode(e)}>
                                            <FormattedMessage id="project.projects.projectDetails.editButton"/>
                                        </button>
                                    </div>:
                                    <div>
                                        {/*Botón para confirmar cambios*/}
                                        <button type="button" className="btn btn-secondary btn-md ml-auto m-1" onClick={() => disableEditMode()}>
                                            <FormattedMessage id="project.projects.projectDetails.cancelEditButton"/>
                                        </button>
                                        <button type="submit" className="btn btn-primary btn-md ml-auto m-1">
                                            <FormattedMessage id="project.projects.projectDetails.confirmEditButton"/>
                                        </button>
                                    </div>
                                }
                            </div>
                            }
                        </div>
                    </div>
                </div>
            </form>
        </div>
    );
}

export default ProjectInstanceDetails;