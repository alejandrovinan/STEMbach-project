import {FormattedMessage} from "react-intl";
import {Link} from "react-router-dom";

const ProjectInstances = ({projectInstances}) => {

    return (
        <div>
            <h5 className="card-header text-center">
                <FormattedMessage id="project.projects.projectSearch.title"/>
            </h5>
            <br/>
            <div className="table-responsive-sm">
                <table className="table table-hover">
                    <thead className="thead-dark">
                    <tr>
                        <th className="text-center" scope="col" colSpan="2">
                            <FormattedMessage id="project.projects.form.title"/>
                        </th>
                        <th className="text-center" scope="col" colSpan="2">
                            <FormattedMessage id="project.requests.table.students"/>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    {projectInstances.map(p =>
                        <tr key={p.id}>
                            <td className="text-center align-middle" colSpan="2">
                                <Link id={`project_${p.id}`} to={`/projects/ProjectInstanceDetails/${p.id}`}>
                                    {p.title}
                                </Link>
                            </td>
                            <td colSpan="2">
                                <div className="container">
                                    {p.students.map(student =>
                                        <div className="row">
                                            <div className="text-center">
                                                {student.name + " " + student.surname + " " + student.secondSurname}
                                            </div>
                                        </div>
                                    )}
                                </div>
                            </td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </div>
        </div>
    )
}

export default ProjectInstances;