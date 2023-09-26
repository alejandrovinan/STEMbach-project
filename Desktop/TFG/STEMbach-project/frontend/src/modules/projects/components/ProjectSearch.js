import FilterSidebar from './FilterSidebar';
import Projects from './Projects';
import * as selectors from '../selectors';
import * as actions from '../actions';
import {useDispatch, useSelector} from "react-redux";
import {useEffect, useState} from "react";
import {Pager} from "../../common";

const ProjectSearch = () => {
    let enableBack = false;
    let enableNext = false;

    const projectResults = useSelector(selectors.getAllProjectResults);
    const bienniums = useSelector(selectors.getAllBienniums);
    const dispatch = useDispatch();

    useEffect(() => {
        if(!bienniums){
            dispatch(actions.findAllBienniums());
        }

        if(projectResults){
            dispatch(actions.findProjectsByCriteria(projectResults.criteria));
        }

        return () => dispatch(actions.clearProjectSearch());
    },[dispatch])

    if(projectResults){
        if(projectResults.criteria.page !== 0){
            enableBack =true;
        }

        if(projectResults.result.existMoreItems){
           enableNext = true;
        }
    }

    return(
        <div className="row">
            <FilterSidebar/>
            <Projects projectResults={projectResults ? projectResults.result.items : []} bienniums={bienniums}/>
            <Pager
                back={{
                    enabled: enableBack,
                    onClick: () => dispatch(actions.previousFindProjectsByCriteria(projectResults.criteria))
                }}
                next={{
                    enabled: enableNext,
                    onClick: () => dispatch(actions.nextFindProjectsByCriteria(projectResults.criteria))
                }}/>
        </div>
    )
}

export default ProjectSearch;