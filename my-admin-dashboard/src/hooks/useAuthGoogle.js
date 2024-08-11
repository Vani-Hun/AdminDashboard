import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import * as KEY from '../constants'
import { exchangeCodeForToken as fetchExchangeCodeForToken, handleGoogleLoginSuccess as fetchGoogleLoginSuccess } from '../utils/api';

export const useAuthGoogle = () => {
    const [isLoading, setIsLoading] = useState(false);
    const navigate = useNavigate();
    const clientId = process.env.REACT_APP_GOOGLE_CLIENT_ID;

    const exchangeCodeForToken = async (code) => {
        console.log("code:", code);
        setIsLoading(true);
        try {
            const data = await fetchExchangeCodeForToken(code);
            console.log("Token data:", data);
            navigate(KEY.ROUTES.DASHBOARD_MESSAGE);
        } catch (error) {
            console.error('Error exchanging code for token:', error);
        } finally {
            setIsLoading(false);
        }
    };

    const handleGoogleLoginSuccess = async (credentialResponse) => {
        try {
            const data = await fetchGoogleLoginSuccess(credentialResponse);
            window.location.href = data.authUrl;
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const handleGoogleLoginError = () => {
        console.log('Login failed');
    };

    return {
        clientId,
        isLoading,
        exchangeCodeForToken,
        handleGoogleLoginSuccess,
        handleGoogleLoginError
    };
};

