import * as selectors from '../selectors';
import * as actions from '../actions';
import {useDispatch, useSelector} from "react-redux";
import {Form, useParams} from "react-router-dom";
import {FormattedMessage} from "react-intl";
import {useEffect} from "react";

const ProjectDetails = () => {

    const {id} = useParams();

    const dispatch = useDispatch();
    const projectDetails = useSelector(selectors.getProjectDetails);
    console.log(id);

    useEffect(()=>{
        const projectId = Number(id);

        console.log("aaaaaaaaa")
        if(!Number.isNaN(projectId)){
            console.log("erwoeingoir")
            if(projectDetails === null || projectDetails.id !== id){
                dispatch(actions.findProjectDetails(id))
            }
        }

        return () => dispatch(actions.clearProjectDetails());
    }, [id, dispatch]);

    if(!projectDetails){
        return null;
    }


    return (
        <div className="card bg-light border-dark">
            <h3 className="card-header"> {projectDetails.title} </h3>
            <div className="card-body">
                <div className="row">
                    <div className="col">
                        <dt>
                            <FormattedMessage id="project.projects.form.description"/>
                        </dt>
                        <dd>
                            {projectDetails.description}
                        </dd>
                        {projectDetails.observations &&
                        <div>
                            <dt>
                                <FormattedMessage id="project.projects.form.observations"/>
                            </dt>
                            <dd>
                                {projectDetails.observations}
                            </dd>
                        </div>
                        }
                        <dt>
                            <FormattedMessage id="project.projects.form.modality"/>
                        </dt>
                        <dd>
                            {projectDetails.modality === "PRESENCIAL" && <FormattedMessage id="project.projects.form.modalitySelector.inPerson"/>}
                            {projectDetails.modality === "DISTANCIA" && <FormattedMessage id="project.projects.form.modalitySelector.distance"/>}
                            {projectDetails.modality === "PRESENCIAL-DISTANCIA" && <FormattedMessage id="project.projects.form.modalitySelector.inPerson_distance"/>}
                        </dd>
                        <dt>
                            <FormattedMessage id="project.projects.form.offerZone"/>
                        </dt>
                        <dd>
                            {projectDetails.offerZone === "COR" && <FormattedMessage id="project.projects.form.offerZoneSelector.cor"/>}
                            {projectDetails.offerZone === "FERR" && <FormattedMessage id="project.projects.form.offerZoneSelector.ferr"/>}
                            {projectDetails.offerZone === "GAL" && <FormattedMessage id="project.projects.form.offerZoneSelector.gal"/>}
                        </dd>
                        <dt>
                            <FormattedMessage id="project.projects.form.maxGroups"/>
                        </dt>
                        <dd>
                            {projectDetails.maxGroups}
                        </dd>
                        <dt>
                            <FormattedMessage id="project.projects.form.studentsPerGroup"/>
                        </dt>
                        <dd>
                            {projectDetails.studentsPerGroup}
                        </dd>
                    </div>
                    <div className="col">
                        {projectDetails.url &&
                        <div>
                            <dt>
                                <FormattedMessage id="project.projects.form.url"/>
                            </dt>
                            <dd>
                                <a href={projectDetails.url} className="link-primary">{projectDetails.url}</a>
                            </dd>
                        </div>
                        }
                        <dt>
                            <FormattedMessage id="project.projects.form.biennium"/>
                        </dt>
                        <dd>
                            {projectDetails.biennium.name}
                        </dd>
                        <dt>
                            <FormattedMessage id="project.projects.form.teachers"/>
                        </dt>
                        <dd>
                            {projectDetails.teacherList.map(t =>
                                <div>
                                    <u>{t.name + t.surname + t.secondSurname}</u>
                                    <br/>
                                    <a href={`mailto: ${t.email}`} className="link-primary">{t.email}</a>
                                </div>
                            )}
                        </dd>
                    </div>
                </div>
            </div>
        </div>

    );
}

export default ProjectDetails;