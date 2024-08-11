import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { updateDetail } from '../utils/api';
export const useDetailFormHandling = (table, detail) => {
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);
    const [action, setAction] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();
        const formData = new FormData(event.target);
        const formObject = Object.fromEntries(formData.entries());
        formObject.action = action;

        try {
            const result = await updateDetail(table, detail, formObject);
            setSuccess(result);
        } catch (error) {
            console.error("Error:", error);
            setError(error.message);
        }
    };

    const handleSaveClick = () => {
        if (confirm("Please confirm you want to save this record.")) {
            setAction('update');
        }
    };

    const handleDeleteClick = () => {
        if (confirm("Please confirm you want to delete this record.")) {
            setAction('delete');
        }
    };

    useEffect(() => {
        if (error) {
            const userConfirmed = window.confirm(`Error: ${error}\nDo you want to go back?`);
            if (userConfirmed) {
                navigate(-1);
            }
        }
    }, [error, navigate]);

    useEffect(() => {
        if (success) {
            const userConfirmed = window.confirm(`Success: ${success}\nDo you want to go back?`);
            if (userConfirmed) {
                navigate(-1);
            }
        }
    }, [success, navigate]);

    return { handleSubmit, handleSaveClick, handleDeleteClick };
};