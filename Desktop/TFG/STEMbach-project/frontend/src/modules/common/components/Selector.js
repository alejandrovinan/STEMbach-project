
import PropTypes from 'prop-types';

const Selector = ({id, className, value, onChange, data}) => {

    return(
        <select id={id} className={className} value={value} onChange={onChange} required>
            <option disabled={true} value=""></option>
            {data && data.map(d =>
                <option key={d.id} value={d.id}>{d.name}</option>
            )}
        </select>
    )
}

Selector.propTypes = {
    selectProps: PropTypes.object
};

export default Selector;