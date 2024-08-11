import { useNavigate, useSubmit } from 'react-router-dom';
import { useState } from 'react';

export const useLoginForm = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();
    const submit = useSubmit();

    const handleSubmit = (event) => {
        event.preventDefault();
        submit({ username, password }, { method: 'post', action: '/login' });
    };

    return {
        username,
        setUsername,
        password,
        setPassword,
        handleSubmit,
    };
};