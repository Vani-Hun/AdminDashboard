import React from 'react';
import PropTypes from 'prop-types';
import { toZonedTime, formatInTimeZone } from 'date-fns-tz';

export const ToastMessage = ({ msgWebsocketService, msgWebsocket, onClose }) => {
    const date = toZonedTime(msgWebsocket.created_at);
    const formattedDate = formatInTimeZone(date, 'Asia/Ho_Chi_Minh', 'dd/MM/yyyy HH:mm:ss');

    return (
        <div className='dashboard__toast' role='alert' aria-live='assertive' aria-atomic='true'>
            <div className='toast-header'>
                {msgWebsocket.thumbnail && (
                    <img
                        src={msgWebsocket.thumbnail}
                        className='rounded mr-2'
                        alt='Thumbnail'
                    />
                )}
                <strong className='mr-auto'>{msgWebsocketService}</strong>
                <small>{formattedDate}</small>
                <button
                    onClick={onClose}
                    type='button'
                    className='ml-2 mb-1 close'
                    data-dismiss='toast'
                    aria-label='Close'
                >
                    <span aria-hidden='true'>&times;</span>
                </button>
            </div>
            <div className='toast-body'>
                {msgWebsocket.caption}
                {msgWebsocket.from && msgWebsocket.from}
            </div>
            <div className='toast-timer-bar'></div>
        </div>
    );
};

ToastMessage.propTypes = {
    msgWebsocketService: PropTypes.string.isRequired,
    msgWebsocket: PropTypes.shape({
        created_at: PropTypes.string.isRequired,
        thumbnail: PropTypes.string,
        caption: PropTypes.string,
        from: PropTypes.string,
    }).isRequired,
    onClose: PropTypes.func.isRequired,
};