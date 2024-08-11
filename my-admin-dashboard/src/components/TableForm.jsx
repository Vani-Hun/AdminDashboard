import React, { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';

const TableForm = ({ tableData, onSubmit }) => {
    const [isFormVisible, setIsFormVisible] = useState(false);
    const navigate = useNavigate();
    const location = useLocation();
    const currentUrl = location.pathname + location.search + location.hash;
    const splitUrl = currentUrl.split("/")
    const TABLES = ['admin', 'customer', 'hashtag'];
    let availableData = false;
    splitUrl.forEach((item) => {
        if (TABLES.includes(item)) {
            availableData = true;
        }
    });
    const renderForm = () => {
        if (!tableData.length) return null;
        const keys = Object.keys(tableData[0]).filter(key => key !== 'id');

        return (
            <form id="add-form" onSubmit={handleSubmit}>
                {keys.map(key => (
                    <div className="form-edit" key={key}>
                        <label htmlFor={key}>{key}</label>
                        <input
                            id={key}
                            placeholder={key}
                            aria-label={key}
                            type="text"
                            name={key}
                        />
                    </div>
                ))}
                <div className='form-actions'>
                    <button id="save" type="submit">Save</button>
                    <button id="cancel" type="button" onClick={() => setIsFormVisible(false)}>Cancel</button>
                </div>
            </form>
        );
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        const formData = new FormData(event.target);
        const data = Object.fromEntries(formData);
        onSubmit(data);
        setIsFormVisible(false);
    };
    <button onClick={() => setIsFormVisible(true)}>+ Add Data</button>

    return (
        <div>
            {availableData === true ? <button onClick={() => setIsFormVisible(true)}>+ Add Data</button> : <button > Can't Add Data</button >}
            {isFormVisible && renderForm()}
        </div>
    );
};

export default React.memo(TableForm);