import * as KEY from '../constants'
import Cookies from 'js-cookie';
import { fetchSaveTopicData } from '../utils/api'

export const usePubSub = () => {
    const submitPubSubForm = async (data) => {
        try {
            const response = await fetchSaveTopicData(data);
            return response;
        } catch (error) {
            console.error('Error submitting PubSub form:', error);
            throw error;
        }
    };

    return { submitPubSubForm };
};
