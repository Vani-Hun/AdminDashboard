import Cookies from 'js-cookie';
import * as KEY from '../constants'

const API_HOST = process.env.REACT_APP_API_HOST;

const createHeaders = () => ({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${Cookies.get('token')}`
});

const handleResponse = async (response) => {
    const data = await response.json();
    if (!response.ok) {
        if (data.statusCode === 404 && data.message === "Unauthorized path") {
            window.location.href = "/login";
        }
        throw new Error(data.message || 'An error occurred');
    }
    return data;
};

const sendRequest = async (url, method = 'GET', body = null) => {

    try {
        const response = await fetch(url, {
            method,
            headers: createHeaders(),
            ...(body && { body: JSON.stringify(body) }),
            credentials: 'include',
        });
        return handleResponse(response);
    } catch (error) {
        console.error(`Error in API request to ${url}:`, error);
        throw error;
    }
};
export async function verify() {
    const url = `${API_HOST}/v1/admin/verify`;
    return sendRequest(url, 'GET');
}
export async function signIn(username, password) {
    const url = `${API_HOST}/v1/admin/signIn`;
    return sendRequest(url, 'POST', { username, password });
}
export const fetchDatabase = async (database, itemsPerPage, page) => {
    const url = `${API_HOST}/v1/admin/${database}?itemsPerPage=${itemsPerPage}&page=${page}`;
    return sendRequest(url);
};

export const fetchSearchData = async (database, itemsPerPage, page, searchValue) => {
    const url = `${API_HOST}/v1/admin/${database}?searchValue=${searchValue}&itemsPerPage=${itemsPerPage}&page=${page}`;
    return sendRequest(url);
};

export const fetchSaveTopicData = async (data) => {
    const url = `${API_HOST}${KEY.API_ENDPOINTS.TOPIC}`;
    return sendRequest(url, 'POST', data);
};

export const updateDetail = async (table, detail, formObject) => {
    const url = `${API_HOST}/v1/admin/update/${table}/${detail}`;
    return sendRequest(url, 'POST', formObject);
};

export const exchangeCodeForToken = async (code) => {
    const url = `${API_HOST}${KEY.API_ENDPOINTS.OAUTH_CALLBACK}?code=${code}`;
    return sendRequest(url);
};

export const handleGoogleLoginSuccess = async (credentialResponse) => {
    const url = `${API_HOST}${KEY.API_ENDPOINTS.GOOGLE_AUTH}`;
    return sendRequest(url, 'POST', { token: credentialResponse.credential });
};

export const fetchStatsData = async (dateRange) => {
    const url = `${API_HOST}${KEY.API_ENDPOINTS.STATS}?start=${dateRange.start}&end=${dateRange.end}&filters=null`;
    return sendRequest(url);
};