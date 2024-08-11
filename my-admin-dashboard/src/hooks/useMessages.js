import { toZonedTime, formatInTimeZone } from 'date-fns-tz';

export const useMessages = () => {
    const messages = JSON.parse(sessionStorage.getItem('messages')) || [];

    const renderMessages = (messages) => {
        if (messages && messages.length > 0) {
            return messages.map((message, index) => {
                const date = toZonedTime(message.created_at);
                const formattedDate = formatInTimeZone(date, 'Asia/Ho_Chi_Minh', 'dd/MM/yyyy HH:mm:ss');
                return (
                    <div key={index} className='message'>
                        <div className='message-from'>{message.from || "Media"}</div>
                        <div className='message-caption'>{message.caption}</div>
                        <div className='message-date'>{formattedDate}</div>
                    </div>
                );
            });
        }
        return null;
    };

    return { messages, renderMessages };
};