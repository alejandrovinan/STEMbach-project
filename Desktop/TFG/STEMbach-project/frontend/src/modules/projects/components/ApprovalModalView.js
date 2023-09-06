import {FormattedMessage} from "react-intl";

const ApprovalModalView = ({actionOnSave, id, message}) => {
    return(
        <div className="modal fade" id={id} tabIndex="-1" role="dialog"
             aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div className="modal-dialog modal-dialog-centered" role="document">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title" id="exampleModalLongTitle">STEMBach</h5>
                        <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div className="modal-body">
                        {message}
                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" data-dismiss="modal">
                            <FormattedMessage id="project.global.buttons.close"/>
                        </button>
                        <button id="modalSaveBtn" type="button" className="btn btn-primary" onClick={actionOnSave} data-dismiss="modal">
                            <FormattedMessage id="project.global.buttons.save"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ApprovalModalView;