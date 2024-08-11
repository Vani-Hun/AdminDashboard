import React from 'react';
import { Form, useLoaderData, useParams, useNavigate } from "react-router-dom";
import Cookies from 'js-cookie';
import { useDetailFormHandling } from '../hooks/useDetailFormHandling';
import DetaiFormInput from '../components/DetailFormInput';
export async function loader({ params }) {
    return fetchDetail(params.table, params.detail);
}

async function fetchDetail(table, detail) {
    const token = Cookies.get('token');
    const host = process.env.REACT_APP_API_HOST;
    const response = await fetch(`${host}/v1/admin/${table}/${detail}`, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    });

    const data = await response.json();
    if (!response.ok) {
        if (data.statusCode === 404 && data.message === "Unauthorized path") {
            window.location.href = "/login";
        }
        throw new Error(data.message || 'Failed to fetch data');
    }
    return data;
}

export default function DatabaseDetail() {
    const data = useLoaderData();
    const { table, detail } = useParams();
    const navigate = useNavigate();
    const { handleSubmit, handleSaveClick, handleDeleteClick } = useDetailFormHandling(table, detail);

    const renderForm = (data) => {
        if (!data) return null;
        return Object.entries(data)
            .filter(([key]) => key !== 'id')
            .map(([key, value]) => (
                <DetaiFormInput key={key} label={key} value={value} />
            ));
    };

    return (
        <Form method="post" id="edit-form" onSubmit={handleSubmit}>
            <div className="form-edit">
                {renderForm(data)}
            </div>
            <div className="form-actions">
                <button id="save" type="submit" onClick={handleSaveClick}>Save</button>
                <button id="cancel" type="button" onClick={() => navigate(-1)}>Cancel</button>
                <button id="delete" type="submit" onClick={handleDeleteClick}>Delete</button>
            </div>
        </Form>
    );
}