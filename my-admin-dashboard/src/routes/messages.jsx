import '../styles/Messages.scss';
import '../styles/Common.scss';
import { useLocation } from "react-router-dom";
import { useEffect, useRef } from "react";
import { GoogleOAuthProvider, GoogleLogin } from '@react-oauth/google';
import { useAuthGoogle } from '../hooks/useAuthGoogle';
import { usePubSub } from '../hooks/usePubSub';
import { useMessages } from '../hooks/useMessages';
import { GetserviceIcon } from '../components/GetserviceIcon';
import { MessagesList } from '../components//MessagesList';
import { PubSubForm } from '../components/PubSubForm';
import PropTypes from 'prop-types';

export const action = async ({ request }) => {

};
export async function loader({ params }) {
    return { params }
}





const Messages = () => {
    const location = useLocation();
    const formRef = useRef(null);
    const service = 'message'
    const { clientId, exchangeCodeForToken, handleGoogleLoginSuccess, handleGoogleLoginError } = useAuthGoogle();
    const { submitPubSubForm } = usePubSub();
    const { messages, renderMessages } = useMessages();



    const handleSubmit = async (e) => {
        e.preventDefault();
        const formData = new FormData(formRef.current);
        const data = Object.fromEntries(formData);
        try {
            await submitPubSubForm(data);
        } catch (error) {
            console.error('Error submitting form:', error);
        }
    };
    useEffect(() => {
        const urlParams = new URLSearchParams(location.search);
        const code = urlParams.get('code');

        if (code) {
            exchangeCodeForToken(code);
        }
    }, [location]);
    return (
        <GoogleOAuthProvider clientId={clientId}>
            <div className='service-messages-container'>
                <ServiceHeader service={service} />
                <div className='google-service'>
                    <div className='google-button'>
                        <GoogleLogin
                            onSuccess={handleGoogleLoginSuccess}
                            onError={handleGoogleLoginError}
                        />
                    </div>
                    <PubSubForm formRef={formRef} onSubmit={handleSubmit} />
                </div>
                <MessagesList messages={messages} />
            </div>
        </GoogleOAuthProvider>
    );
};

const ServiceHeader = (service) => (
    <div className='service-title'>
        <GetserviceIcon service={service} size="40px" />
        <h1>Message</h1>
    </div>
);

ServiceHeader.propTypes = {
    service: PropTypes.string.isRequired,
};

export default Messages;
