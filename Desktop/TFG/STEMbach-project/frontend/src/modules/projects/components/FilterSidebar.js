import {
    CDBSidebar,
    CDBSidebarContent,
    CDBSidebarFooter,
    CDBSidebarHeader,
    CDBSidebarMenu,
    CDBSidebarMenuItem,
} from 'cdbreact';
import {FormattedMessage} from "react-intl";
import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import users, {Selector} from "../../users";
import * as selectors from "../selectors";
import * as actions from '../actions';
import {act} from "react-dom/test-utils";
import {useNavigate} from "react-router-dom";
import Select from "react-select";

const FilterSidebar = () => {

    const [modality, setModality] = useState('');
    const [offerZone, setOfferZone] = useState('');
    const [revised, setRevised] = useState(true);
    const [active, setActive] = useState(true);
    const [maxGroups, setMaxGroups] = useState('');
    const [studentsPerGroups, setStudentsPerGroup] = useState('');
    const [biennium, setBiennium] = useState('');
    const [assigned, setAssigned] = useState(false);
    const [teacherIds, setTeacherIds] = useState([]);
    const [teachersSelectorHolder, setTeachersSelectorHolder] = useState([]);
    const [title, setTitle] = useState();

    const navigate = useNavigate();
    const teachers = useSelector(selectors.getTeacherSelectorList);
    const bienniums = useSelector(selectors.getAllBienniums);
    const dispatch = useDispatch();
    const user = useSelector(users.selectors.getUser);
    const isStemCoordinator = user ? user.role === "STEMCOORDINATOR" : false;

    const options = [];

    useEffect(() => {

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
        setTeachersSelectorHolder(selectedOptions);
        setTeacherIds([]);
        selectedOptions.forEach(o => {setTeacherIds(teacherIds.concat(o.value))})
    }

    const clearFilters = () => {
        setModality("");
        setOfferZone("");
        setActive(true);
        setMaxGroups("");
        setStudentsPerGroup("");
        setBiennium("");
        setAssigned(false);
        setRevised(true);
        setTeachersSelectorHolder([]);
        setTeacherIds([]);
        setTitle("");
    }

    const findProjects = event => {
        event.preventDefault();
        dispatch(actions.findProjectsByCriteria(
            {
                modality: modality,
                offerZone: offerZone,
                revised:revised,
                active:active,
                maxGroups: maxGroups,
                studentsPerGroup: studentsPerGroups,
                biennium: biennium,
                assigned:assigned,
                teachers: teacherIds,
                title: title,
                page: 0
            }
        ));
        navigate(`/projects/ProjectSearch`)
    }

    return(
        <div className="z-3 col-md-2">
            <CDBSidebar textColor="#fff" backgroundColor="#333" toggled={true}>
                <CDBSidebarHeader className="align-items-center" prefix={<i className="fa fa-bars"/>}>
                    <div>
                        <FormattedMessage id="project.projects.sidebar.headerTitle"/>
                    </div>
                </CDBSidebarHeader>
                <CDBSidebarContent className="sidebar-content">
                    <CDBSidebarMenu>
                        <CDBSidebarMenuItem>
                            <FormattedMessage id="project.projects.form.teachers"/>
                        </CDBSidebarMenuItem>
                        <Select isMulti={true} options={options} onChange={handleChange}
                                value={teachersSelectorHolder} className="form-select"/>
                        <br/>
                        <CDBSidebarMenuItem>
                            <FormattedMessage id="project.projects.form.title"/>
                            <input type="text" id="title" className="form-control" value={title}
                                   onChange={e => setTitle(e.target.value)}/>
                        </CDBSidebarMenuItem>
                        <br/>
                        <CDBSidebarMenuItem icon="fa-duotone fa-shop">
                            <FormattedMessage id="project.projects.form.modality"/>
                            <select name="modality" id="modalitySelector" className="form-select" onChange={e => setModality(e.target.value)} value={modality}>
                                <option disabled={true} value=""><FormattedMessage id="project.projects.form.modalitySelector.void"/></option>
                                <option value="PRESENCIAL"><FormattedMessage id="project.projects.form.modalitySelector.inPerson"/></option>
                                <option value="DISTANCIA"><FormattedMessage id="project.projects.form.modalitySelector.distance"/></option>
                                <option value="PRESENCIAL-DISTANCIA"><FormattedMessage id="project.projects.form.modalitySelector.inPerson_distance"/></option>
                            </select>
                        </CDBSidebarMenuItem>
                        <br/>
                        <CDBSidebarMenuItem icon="fa-duotone fa-location-dot">
                            <FormattedMessage id="project.projects.form.offerZone"/>
                            <select name="offerZone" id="offerZoneSelector" className="form-select" onChange={e => setOfferZone(e.target.value)} value={offerZone}>
                                <option disabled={true} value=""><FormattedMessage id="project.projects.form.offerZoneSelector.void"/></option>
                                <option value="COR"><FormattedMessage id="project.projects.form.offerZoneSelector.cor"/></option>
                                <option value="FERR"><FormattedMessage id="project.projects.form.offerZoneSelector.ferr"/></option>
                                <option value="GAL"><FormattedMessage id="project.projects.form.offerZoneSelector.gal"/></option>
                            </select>
                        </CDBSidebarMenuItem>
                        <br/>
                        <CDBSidebarMenuItem icon="fa-solid fa-ban">
                            <div className="form-check">
                                <input className="form-check-input" type="checkbox" onChange={e => {setActive(e.target.checked)}} id="revisedCheckBoxId" checked={active}/>
                                <label className="form-check-label" htmlFor="revisedCheckBoxId"><FormattedMessage id="project.projects.sidebar.active"/></label>
                            </div>
                        </CDBSidebarMenuItem>
                        <br/>
                        <CDBSidebarMenuItem icon="fa-regular fa-user-studentGroup">
                            <FormattedMessage id="project.projects.sidebar.maxGroups"/>
                            <input type="number" min="1" id="maxGroups" className="form-control" value={maxGroups}
                                   onChange={e => setMaxGroups(e.target.value)}/>
                        </CDBSidebarMenuItem>
                        <br/>
                        <CDBSidebarMenuItem icon="fa-regular fa-user">
                            <FormattedMessage id="project.projects.sidebar.studentsPerGroup"/>
                            <input type="number" min="1" id="maxGroups" className="form-control" value={studentsPerGroups}
                                   onChange={e => setStudentsPerGroup(e.target.value)}/>
                        </CDBSidebarMenuItem>
                        <br/>
                        <CDBSidebarMenuItem icon="fa-solid fa-calendar-days">
                            <FormattedMessage id="project.projects.form.biennium"/>
                            <Selector id="bienniumSelector" className="form-select" value={biennium}
                                      onChange={e => setBiennium(e.target.value)} data={bienniums}/>
                        </CDBSidebarMenuItem>
                        <br/>
                        <CDBSidebarMenuItem icon="fa-solid fa-question">
                            <div className="form-check">
                                <input className="form-check-input" type="checkbox" value={assigned} onChange={e => setAssigned(e.target.checked)} id="assignedCheckBoxId" checked={assigned}/>
                                <label className="form-check-label" htmlFor="assignedCheckBoxId"><FormattedMessage id="project.projects.sidebar.assigned"/></label>
                            </div>
                        </CDBSidebarMenuItem>
                    </CDBSidebarMenu>
                    <CDBSidebarFooter>
                        {isStemCoordinator ?
                            <CDBSidebarMenuItem icon="fa-solid fa-check-double">
                                <div className="form-check">
                                    <input className="form-check-input" type="checkbox" checked={revised} onChange={e => setRevised(e.target.checked)} id="revisedCheckBoxId"/>
                                    <label className="form-check-label" htmlFor="revisedCheckBoxId"><FormattedMessage id="project.projects.sidebar.revised"/></label>
                                </div>
                            </CDBSidebarMenuItem>
                            : null
                        }
                        <CDBSidebarMenuItem>
                            <div className="container">
                                <div className="row justify-content-around">
                                    <div className="col-6">
                                        <button type="button" className="btn btn-outline-light btn-sm" onClick={() => clearFilters()}>
                                            <FormattedMessage id="project.projects.sidebar.clearFilter"/>
                                        </button>
                                    </div>
                                    <div className="col-6">
                                        <button type="button" className="btn btn-outline-light btn-sm" onClick={e => findProjects(e)}>
                                            <FormattedMessage id="project.projects.sidebar.applyFilter"/>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </CDBSidebarMenuItem>
                    </CDBSidebarFooter>
                </CDBSidebarContent>
            </CDBSidebar>
        </div>
    );
}

export default FilterSidebar;