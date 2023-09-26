import {useDispatch, useSelector} from "react-redux";
import * as selectors from '../selectors';
import * as userSelector from '../../users/selectors';
import * as actions from '../actions';
import ProjectInstances from "./ProjectInstances";
import {useEffect} from "react";
import {findProjectInstancesSummary} from "../actions";
import {Pager} from "../../common";

const ProjectInstanceSearch = () => {
    let enableBack = false;
    let enableNext = false;

    const projectInstances = useSelector(selectors.getAllProjectInstancesResults)
    const user = useSelector(userSelector.getUser);
    const role = user ? user.role : null;
    const dispatch = useDispatch();

    useEffect(() => {
        if(projectInstances){
            dispatch(actions.findProjectInstancesSummary(projectInstances.criteria));
        }else{
            dispatch(actions.findProjectInstancesSummary({
                page: 0,
                size: 5,
                role: role
            }))
        }
    }, [dispatch])

    if(projectInstances){
        if(projectInstances.criteria.page !== 0){
            enableBack =true;
        }

        if(projectInstances.result.existMoreItems){
            enableNext = true;
        }
    }

    return (
        <div>
            <ProjectInstances projectInstances={projectInstances ? projectInstances.result.items : []}/>
            <Pager
                back={{
                    enabled: enableBack,
                    onClick: () => dispatch(actions.previousFindProjectInstancesSummary(projectInstances.criteria))
                }}
                next={{
                    enabled: enableNext,
                    onClick: () => dispatch(actions.nextFindProjectInstancesSummary(projectInstances.criteria))
                }}/>
        </div>
    )
}

export default ProjectInstanceSearch;