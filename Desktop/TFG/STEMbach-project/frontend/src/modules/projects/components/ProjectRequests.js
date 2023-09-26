import * as selectors from '../selectors';
import * as actions from '../actions';
import {useDispatch, useSelector} from "react-redux";
import Requests from "./Requests";
import {useEffect} from "react";
import {Pager} from "../../common";

const ProjectRequests = () => {
    let enableBack = false;
    let enableNext = false;

    const projectDetails = useSelector(selectors.getProjectDetails);
    const requests = useSelector(selectors.getAllRequests)
    const dispatch = useDispatch();

    useEffect(() => {
        if(requests){
            dispatch(actions.viewRequests(requests.criteria))
        } else {
            dispatch(actions.viewRequests({
                id: projectDetails.id,
                page: 0,
                size: 5
            }))
        }
    }, [dispatch])

    if(requests){
        if(requests.criteria.page !== 0){
            enableBack = true;
        }

        if(requests.result.existMoreItems){
            enableNext = true;
        }
    }

    return (
        <div className="card bg-light border-dark">
            <h3 className="card-header text-center">
                {projectDetails.title}
            </h3>
            {<div className="card-body">
                <Requests requests={requests ? requests.result.items : []}/>
                <Pager
                    back={{
                        enabled: enableBack,
                        onClick: () => dispatch(actions.previousViewRequests(requests.criteria))
                    }}
                    next={{
                        enabled: enableNext,
                        onClick: () => dispatch(actions.nextViewRequests(requests.criteria))
                    }}/>
            </div>}
        </div>
    )
}

export default ProjectRequests;