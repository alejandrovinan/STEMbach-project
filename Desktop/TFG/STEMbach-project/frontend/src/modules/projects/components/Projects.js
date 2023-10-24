import {FormattedMessage} from "react-intl";
import * as selectors from '../selectors';
import {Link} from "react-router-dom";

const Projects = ({projectResults, bienniums}) => {


    return(
        <div className="col-md-9">
            <h5 className="card-header text-center">
                <FormattedMessage id="project.projects.projectSearch.title"/>
            </h5>
            <br/>
            <div className="table-responsive-sm">
                <table className="table table-hover">
                    <thead className="thead-dark">
                        <tr>
                            <th scope="col" colSpan="2">
                                <FormattedMessage id="project.projects.form.title"/>
                            </th>
                            <th scope="col">
                                <FormattedMessage id="project.projects.form.modality"/>
                            </th>
                            <th scope="col">
                                <FormattedMessage id="project.projects.form.offerZone"/>
                            </th>
                            <th scope="col">
                                <FormattedMessage id="project.projects.sidebar.maxGroups"/>
                            </th>
                            <th scope="col">
                                <FormattedMessage id="project.projects.sidebar.studentsPerGroup"/>
                            </th>
                            <th scope="col">
                                <FormattedMessage id="project.projects.form.biennium"/>
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                    {projectResults.map(project =>
                        <tr key={project.id}>
                            <td colSpan="2">
                                <Link id={`project_${project.id}`} to={`/projects/ProjectDetails/${project.id}`}>{project.title}</Link>
                            </td>
                            <td>
                                {project.modality === "PRESENCIAL" && <FormattedMessage id="project.projects.form.modalitySelector.inPerson"/>}
                                {project.modality === "DISTANCIA" && <FormattedMessage id="project.projects.form.modalitySelector.distance"/>}
                                {project.modality === "PRESENCIAL-DISTANCIA" && <FormattedMessage id="project.projects.form.modalitySelector.inPerson_distance"/>}
                            </td>
                            <td>
                                {project.offerZone === "COR" && <FormattedMessage id="project.projects.form.offerZoneSelector.cor"/>}
                                {project.offerZone === "FERR" && <FormattedMessage id="project.projects.form.offerZoneSelector.ferr"/>}
                                {project.offerZone === "GAL" && <FormattedMessage id="project.projects.form.offerZoneSelector.gal"/>}
                            </td>
                            <td>{project.maxGroups}</td>
                            <td>{project.studentsPerGroup}</td>
                            <td>{selectors.getBienniumName(bienniums, project.bienniumId)}</td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </div>
        </div>
    )
}

export default Projects;