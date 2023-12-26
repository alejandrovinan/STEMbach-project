import {Errors} from "../../common";
import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import * as actions from '../actions';
import * as projectActions from '../../projects/actions';
import {useParams} from "react-router-dom";
import * as selectors from "../selectors";
import * as projectSelectors from "../../projects/selectors";
import {FormattedMessage} from "react-intl";
import DatePicker from "react-datepicker";
import Select from "react-select";

const DefenseDetails = () => {

    const dispatch = useDispatch();

    const {id} = useParams();
    const projectId = Number(id);
    const projectDetails = useSelector(projectSelectors.getProjectInstanceDetails);
    const defenseDetails = useSelector(selectors.getDefense);
    const teachers = useSelector(projectSelectors.getTeacherSelectorList);

    const [backendErrors, setBackendErrors] = useState(null);

    const [date, setDate] = useState(new Date());
    const [place, setPlace] = useState("");
    const [mark, setMark] = useState();
    const [observations, setObservations] = useState("");
    const [teacherIds, setTeacherIds] = useState([]);
    const [teachersSelectorHolder, setTeachersSelectorHolder] = useState([]);
    const [options,setOptions] = useState([]);
    const [files, setFiles] = useState(null);
    const [filesToAdd, setFilesToAdd] = useState([]);
    const [filesToRemove, setFilesToRemove] = useState([]);
    const [editMode, setEditMode] = useState(false);

    let form;

    useEffect(()=>{

        if(!Number.isNaN(projectId)){
            if(projectDetails === null || projectDetails.id !== id){
                dispatch(projectActions.findProjectInstanceDetails(id))
            }
            dispatch(actions.findDefenseDetails(id, errors => setBackendErrors(errors)));
        }

        if(!teachers){
            dispatch(projectActions.findAllTeachers());
        }

    }, [id, dispatch]);

    if(!projectDetails || !defenseDetails || Number.isNaN(projectId)){
        return null;
    }

    const enableEditMode = e => {
        e.preventDefault();
        setEditMode(true);
        if(defenseDetails) {
            setDate(defenseDetails.date
                ? new Date(defenseDetails.date)
                : new Date());

            setPlace(defenseDetails.place);
            setMark(defenseDetails.mark);
            setObservations(defenseDetails.observations);
            let teacherIds = defenseDetails.teacherSummaryDtos
                ? defenseDetails.teacherSummaryDtos.map(t => {
                    return t.id
                })
                : [];
            setTeacherIds(teacherIds);

            let teacherSelectorData = defenseDetails.teacherSummaryDtos
                ? defenseDetails.teacherSummaryDtos.map(t => {
                    return {
                        value: t.id,
                        label: t.name + " " + t.surname + " " + t.secondSurname
                    }
                })
                : [];
            setTeachersSelectorHolder(teacherSelectorData);

            let optionsAux = teachers.map(t => {
                return {
                    value: t.id,
                    label: t.name + " " + t.surname + " " + t.secondSurname
                }
            })
            setOptions(optionsAux);
            setFiles(defenseDetails.recordFileDtos);
        }
    }

    const disableEditMode = e => {
        e.preventDefault();
        setEditMode(false);
        setDate(defenseDetails.date
            ? new Date(defenseDetails.date)
            : new Date());

        setPlace(defenseDetails.place);
        setMark(defenseDetails.mark);
        setObservations(defenseDetails.observations);
        let teacherIds = defenseDetails.teacherSummaryDtos
            ? defenseDetails.teacherSummaryDtos.map(t => {
                return t.id
            })
            : [];
        setTeacherIds(teacherIds);

        let teacherSelectorData = defenseDetails.teacherSummaryDtos
            ? defenseDetails.teacherSummaryDtos.map(t => {
                return {
                    value: t.id,
                    label: t.name + " " + t.surname + " " + t.secondSurname
                }
            })
            : [];
        setTeachersSelectorHolder(teacherSelectorData);

        let optionsAux = teachers.map(t => {
            return {
                value: t.id,
                label: t.name + " " + t.surname + " " + t.secondSurname
            }
        })
        setOptions(optionsAux);
        setFiles(defenseDetails.recordFileDtos);

        setFilesToAdd([]);
        setFilesToRemove([]);

    }

    const downloadFile = (file) => {
        //atob convierte a un string binario
        const decodedData = atob(file.fileData);
        const uint8Array = new Uint8Array(decodedData.length);

        for (let i = 0; i < decodedData.length; i++) {
            uint8Array[i] = decodedData.charCodeAt(i);
        }
        const blob = new Blob([uint8Array], {type: file.fileType});
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = file.fileName;
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        URL.revokeObjectURL(url);
    }

    const handleChange = (selectedOptions) => {
        setTeachersSelectorHolder(selectedOptions);
        let idsAux = selectedOptions.map(o => {return o.value});
        setTeacherIds(idsAux);
    }

    const changeFileToDelete = (fileName, e) => {
        e.preventDefault();
        let filesToKeep = files.filter((file) => file.fileName !== fileName);
        let removedFile = files.find(file => file.fileName === fileName);
        let filesToRemoveAux = [...filesToRemove, removedFile];

        setFiles(filesToKeep);
        setFilesToRemove(filesToRemoveAux);
    }

    const revertFileDeletion = (fileName, e) => {
        e.preventDefault();
        let filesToRemoveAux = filesToRemove.filter(file => file.fileName !== fileName);
        let fileToRestore = filesToRemove.find(file => file.fileName === fileName);
        let filesToKep = [...files, fileToRestore];

        setFiles(filesToKep);
        setFilesToRemove(filesToRemoveAux);
    }

    const fileUpload = e => {
        e.preventDefault();
        let filesUpload = e.target.files;
        let filesAux = Array.from(filesUpload).concat(filesToAdd);
        setFilesToAdd(filesAux);
    }

    const deleteFile = (fileName,e) => {
        e.preventDefault();
        const updatedFiles = filesToAdd.filter((file) => file.name !== fileName);
        setFilesToAdd(updatedFiles);
    }

    const handleSubmit = e => {
        e.preventDefault();
        const formData = new FormData();
        formData.append("place", place.trim());
        formData.append("date", date.toISOString());
        formData.append("mark", mark ? mark : "");
        formData.append("observations", observations);

        for (const item of filesToAdd) {
            formData.append("filesToAdd", item);
        }

        for(const item of filesToRemove) {
            formData.append("filesToRemove", item.id);
        }

        for (const item of teacherIds) {
            formData.append("teachers", item);
        }

        dispatch(actions.updateDefense(projectId, formData,
            () => {document.getElementById("cancel_editMode_btn").click();},
            errors => setBackendErrors(errors)
        ))
    }

    return (
        <div className="container w-50">
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>
            <div className="card bg-light border-dark">
                <h3 className="card-header text-center">
                    {projectDetails.title}
                </h3>
                <div className="card-body">
                    <form ref={node => form = node} onSubmit={e => handleSubmit(e)}>
                        <div className="form-group row">
                            <label htmlFor="title" className="col-md-2 col-form-label">
                                <FormattedMessage id="project.defenses.CreateDefense.form.date"/>
                            </label>
                            <div className="col-md-4">
                                <DatePicker selected={editMode
                                    ? date
                                    : defenseDetails.date
                                        ? new Date(defenseDetails.date)
                                        : new Date()}
                                    onChange={(date) => setDate(date)} disabled = {editMode ? false : true}/>
                            </div>
                            <label htmlFor="title" className="col-sm-2 col-form-label">
                                <FormattedMessage id="project.defenses.CreateDefense.form.mark"/>
                            </label>
                            <div className="col-md-2">
                                    <input type="number" id="title" className="form-control" value={editMode ? mark : defenseDetails.mark}
                                           onChange={e => setMark(e.target.value)} min="0" max="10" step=".01" disabled = {editMode ? false : true}/>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="title" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.defenses.CreateDefense.form.place"/>
                            </label>
                            <div className="col-md-7">
                                <input type="text" id="title" className="form-control" value={editMode ? place : defenseDetails.place}
                                       onChange={e => setPlace(e.target.value)} disabled = {editMode ? false : true}/>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="title" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.defenses.CreateDefense.form.observations"/>
                            </label>
                            <div className="col-md-7">
                                <textarea id="title" className="form-control" value={editMode ? observations : defenseDetails.observations} style={{height: "20vh"}}
                                          onChange={e => setObservations(e.target.value)} disabled = {editMode ? false : true}/>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="title" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.defenses.CreateDefense.form.judges"/>
                            </label>
                            <div className="col-md-7">
                                {!editMode ?
                                    <>
                                        <div>
                                            {defenseDetails.teacherSummaryDtos.map(t =>
                                                <div>
                                                    {t.name + " " + t.surname + " " + t.secondSurname}
                                                    <br/>
                                                    <a href={`mailto: ${t.email}`} className="link-primary">{t.email}</a>
                                                </div>
                                            )}
                                        </div>
                                    </> :
                                    <Select isMulti={true} options={options} onChange={handleChange}
                                            value={teachersSelectorHolder} isDisabled = {editMode ? false : true}/>
                                }
                            </div>
                        </div>
                        {(files !== null && editMode) ?
                        <div className="list-group m-3">
                            {files.map((file, index) => (
                                <div className="list-group-item w-75 align-self-center">
                                    <div className="d-flex flex-row">
                                        {file.fileName}
                                        <div className="align-self-end ml-auto">
                                            <button className="btn btn-danger" onClick={e => changeFileToDelete(file.fileName, e)}>
                                                <i className="fa-solid fa-trash"/>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            ))}
                        </div>
                            : defenseDetails.recordFileDtos != null &&
                        <div className="list-group m-3">
                            {defenseDetails.recordFileDtos.map((file, index) => (
                                <div className="list-group-item w-75 align-self-center">
                                    <div className="d-flex flex-row">
                                        {file.fileName}
                                        <div className="align-self-end ml-auto">
                                            <button className="btn btn-danger" onClick={() => downloadFile(file)}>
                                                <i className="fa-solid fa-download"/>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            ))}
                        </div>
                        }
                        {filesToRemove.length > 0 && editMode &&
                        <>
                            <h6 className="text-center">
                                <FormattedMessage id="project.defenses.filesToDelete"/>
                            </h6>
                            <div className="list-group m-3">
                                {filesToRemove.map((file, index) => (
                                    <div className="list-group-item w-75 align-self-center">
                                        <div className="d-flex flex-row">
                                            {file.fileName}
                                            <div className="align-self-end ml-auto">
                                                <button className="btn btn-danger" onClick={e => revertFileDeletion(file.fileName, e)}>
                                                    <i className="fa-solid fa-trash"/>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        </>
                        }
                        {editMode &&
                        <div>
                            <div className="form-group d-flex flex-row justify-content-center m-3">
                                <label htmlFor="photo-upload">
                                    <input id="photo-upload" className="form-control" type="file"
                                           accept="image/png, image/jpeg, text/plain, application/pdf, application/msword, application/vnd.ms-excel"
                                           multiple onChange={e => fileUpload(e)}/>
                                </label>
                            </div>
                            {filesToAdd !== null &&
                            <div className="list-group">
                                {filesToAdd.map((file, index) => (
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
                        </div>
                        }
                        <div className="form-group row">
                            {editMode ?
                                <div className="offset-md-8 col-md-5">
                                    <button type="submit" className="btn btn-primary m-2">
                                        <FormattedMessage id="project.projects.projectDetails.confirmEditButton"/>
                                    </button>
                                    <button id="cancel_editMode_btn" type="button" className="btn btn-secondary m-2" onClick={e => disableEditMode(e)}>
                                        <FormattedMessage id="project.projects.projectDetails.cancelEditButton"/>
                                    </button>
                                </div> :
                                <div className="offset-md-8 col-md-5">
                                    <button type="button" className="btn btn-primary m-2" onClick={e => enableEditMode(e)}>
                                        <FormattedMessage id="project.projects.projectDetails.editButton"/>
                                    </button>
                                </div>
                            }
                        </div>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default DefenseDetails;