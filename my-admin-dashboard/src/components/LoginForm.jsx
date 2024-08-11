// src/components/LoginForm.jsx
import React from 'react';
import PropTypes from 'prop-types';
import { Form } from 'react-router-dom';
import { LOGIN_CONSTANTS } from '../constants';
import '../styles/Login.scss';
import '../styles/Common.scss';

function LoginForm({ username, setUsername, password, setPassword, handleSubmit, error }) {
    return (
        <div className='background'>
            <div className='form-container'>
                <KeyIcon />
                <Form className='form' method='post' onSubmit={handleSubmit}>
                    <h2 className='form-header'>{LOGIN_CONSTANTS.FORM_TITLE}</h2>
                    {error && <h3 className="error-message">{error}</h3>}
                    <InputLoginField
                        label={LOGIN_CONSTANTS.USERNAME_LABEL}
                        type="text"
                        id="username"
                        name="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        placeholder={LOGIN_CONSTANTS.USERNAME_PLACEHOLDER}
                        required
                    />
                    <InputLoginField
                        label={LOGIN_CONSTANTS.PASSWORD_LABEL}
                        type="password"
                        id="password"
                        name="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        placeholder={LOGIN_CONSTANTS.PASSWORD_PLACEHOLDER}
                        required
                    />
                    <button type='submit' className='submit-button'>
                        {LOGIN_CONSTANTS.SUBMIT_BUTTON_TEXT}
                    </button>
                </Form>
            </div>
        </div>
    );
}

LoginForm.propTypes = {
    username: PropTypes.string.isRequired,
    setUsername: PropTypes.func.isRequired,
    password: PropTypes.string.isRequired,
    setPassword: PropTypes.func.isRequired,
    handleSubmit: PropTypes.func.isRequired,
    error: PropTypes.string
};

function KeyIcon() {
    return (
        <div className='key-sticker-image'>
            <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="white"
                height="60px"
                width="60px"
                version="1.1"
                viewBox="0 0 485.017 485.017">
                <path d="M361.205,68.899c-14.663,0-28.447,5.71-38.816,16.078c-21.402,21.403-21.402,56.228,0,77.631c10.368,10.368,24.153,16.078,38.815,16.078s28.447-5.71,38.816-16.078c21.402-21.403,21.402-56.228,0-77.631C389.652,74.609,375.867,68.899,361.205,68.899z M378.807,141.394c-4.702,4.702-10.953,7.292-17.603,7.292s-12.901-2.59-17.603-7.291c-9.706-9.706-9.706-25.499,0-35.205c4.702-4.702,10.953-7.291,17.603-7.291s12.9,2.589,17.603,7.291C388.513,115.896,388.513,131.688,378.807,141.394z" />
                <path d="M441.961,43.036C414.21,15.284,377.311,0,338.064,0c-39.248,0-76.146,15.284-103.897,43.036c-42.226,42.226-54.491,105.179-32.065,159.698L0.254,404.584l-0.165,80.268l144.562,0.165v-55.722h55.705l0-55.705h55.705v-64.492l26.212-26.212c17.615,7.203,36.698,10.976,55.799,10.976c39.244,0,76.14-15.282,103.889-43.032C499.25,193.541,499.25,100.325,441.961,43.036z M420.748,229.617c-22.083,22.083-51.445,34.245-82.676,34.245c-18.133,0-36.237-4.265-52.353-12.333l-9.672-4.842l-49.986,49.985v46.918h-55.705l0,55.705h-55.705v55.688l-84.5-0.096l0.078-37.85L238.311,208.95l-4.842-9.672c-22.572-45.087-13.767-99.351,21.911-135.029C277.466,42.163,306.83,30,338.064,30c31.234,0,60.598,12.163,82.684,34.249C466.34,109.841,466.34,184.025,420.748,229.617z" />
            </svg>
        </div>
    );
}

const InputLoginField = ({ label, type, id, name, value, onChange, placeholder, required }) => (
    <div className='form-group'>
        <label htmlFor={id}>{label}</label>
        <input
            type={type}
            id={id}
            name={name}
            value={value}
            onChange={onChange}
            placeholder={placeholder}
            required={required}
        />
    </div>
);

InputLoginField.propTypes = {
    label: PropTypes.string.isRequired,
    type: PropTypes.string.isRequired,
    id: PropTypes.string.isRequired,
    name: PropTypes.string.isRequired,
    value: PropTypes.string.isRequired,
    onChange: PropTypes.func.isRequired,
    placeholder: PropTypes.string,
    required: PropTypes.bool,
};

export default LoginForm;