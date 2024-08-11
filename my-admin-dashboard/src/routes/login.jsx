// src/routes/login.jsx
import React from 'react';
import { useLoaderData, Form, redirect } from "react-router-dom";
import Cookies from 'js-cookie';
import LoginForm from '../components/LoginForm';
import { useLoginForm } from '../hooks/useLoginForm';
import { signIn } from '../utils/api';


export async function action({ request }) {
    const formData = await request.formData();
    const username = formData.get('username');
    const password = formData.get('password');

    try {
        const data = await signIn(username, password);
        Cookies.set('token', data.token, { expires: 7 });
        return redirect('/main/dashboard');
    } catch (error) {
        console.error('Error logging in:', error);
        return redirect(`/login?error=${encodeURIComponent(error.message)}`);
    }
}

export async function loader({ request }) {
    const url = new URL(request.url);
    const error = url.searchParams.get("error");
    return { error };
}

function Login() {
    const { error } = useLoaderData();
    const { username, setUsername, password, setPassword, handleSubmit } = useLoginForm();

    return (
        <LoginForm
            username={username}
            setUsername={setUsername}
            password={password}
            setPassword={setPassword}
            handleSubmit={handleSubmit}
            error={error}
        />
    );
}

export default Login;