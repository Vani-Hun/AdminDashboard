import React from 'react';
import PropTypes from 'prop-types';

const DetaiFormInput = ({ label, value, type = 'text' }) => (
    <div className="form-group">
        <label>{label}</label>
        <input
            placeholder={label}
            aria-label={label}
            type={type}
            name={label}
            defaultValue={value}
        />
    </div>
);

DetaiFormInput.propTypes = {
    label: PropTypes.string.isRequired,
    value: PropTypes.any,
    type: PropTypes.string,
};

export default DetaiFormInput;