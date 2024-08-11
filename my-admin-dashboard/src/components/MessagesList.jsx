import React from 'react';
import PropTypes from 'prop-types';
import { toDate, format, formatInTimeZone, toZonedTime, getTimezoneOffsete } from 'date-fns-tz';
import { formatDistanceToNow, parseISO } from 'date-fns';
export const MessagesList = ({ messages }) => (
    <div className='messages-container'>
        {messages && messages.length > 0 ? (
            <>
                <div className='messages-row messages-header'>
                    <div>From</div>
                    <div>To</div>
                    <div>Detail</div>
                    <div>Date</div>
                </div>
                <div className='messages-body'>
                    {messages.map((message, index) => (
                        <MessageItem key={index} message={message} />
                    ))}
                </div>
            </>
        ) : null}
    </div>
);

MessagesList.propTypes = {
    messages: PropTypes.array,
};

const MessageItem = ({ message }) => {
    const date = toZonedTime(message.created_at);
    const formattedDate = formatInTimeZone(date, 'Asia/Ho_Chi_Minh', 'dd/MM/yyyy HH:mm:ss');

    return (
        <div className='messages-row message'>
            <div className='message-from'>{message.from || "Media"}</div>
            <div className='message-to'>{message.to || "Media"}</div>
            <div className='message-caption'>{message.caption}</div>
            <div className='message-date'>{formattedDate}</div>
        </div>
    );
};

MessageItem.propTypes = {
    message: PropTypes.shape({
        from: PropTypes.string,
        caption: PropTypes.string,
        created_at: PropTypes.string,
    }).isRequired,
};