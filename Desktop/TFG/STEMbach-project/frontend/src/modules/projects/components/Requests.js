import {FormattedMessage} from "react-intl";

const Requests = ({requests}) =>{
    return (
        <div className="table-responsive-sm">
            <table className="table table-hover">
                <thead className="thead-dark">
                    <tr>
                        <th className="text-center" scope="col">
                            <FormattedMessage id="project.requests.table.status"/>
                        </th>
                        <th className="text-center" scope="col">
                            <FormattedMessage id="project.requests.table.schoolName"/>
                        </th>
                        <th className="text-center" scope="col">
                            <FormattedMessage id="project.requests.table.groupId"/>
                        </th>
                        <th className="text-center" scope="col" colSpan="2">
                            <FormattedMessage id="project.requests.table.students"/>
                        </th>
                    </tr>
                </thead>
                <tbody>
                {requests.map(request =>
                    <tr key={request.id}>
                        <td className="text-center align-middle">
                            {request.status}
                        </td>
                        <td className="text-center align-middle">
                            {request.schoolName}
                        </td>
                        <td className="text-center align-middle">
                            {`G${request.groupId}`}
                        </td>
                        <td colSpan="2">
                            <div className="container">
                            {request.students.map(student =>
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
    )
}

export default Requests;