import { useState, useEffect, useCallback, useMemo, useRef } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

export const useWebSocket = (SOCKET_URL, token) => {
    const [msgWebsocket, setMSGWebsocket] = useState(null);
    const [msgWebsocketService, setMSGWebsocketService] = useState(null);
    const stompClientRef = useRef(null);

    const handleMessage = useCallback((message, serviceType) => {
        const data = JSON.parse(message.body);
        data.serviceType = serviceType;
        const messages = JSON.parse(localStorage.getItem('messages')) || [];
        messages.push(data);
        localStorage.setItem('messages', JSON.stringify(messages));
        setMSGWebsocket(data);
        setMSGWebsocketService(serviceType);
    }, []);

    const clientOptions = useMemo(() => ({
        webSocketFactory: () => new SockJS(SOCKET_URL),
        debug: (str) => console.log(str),
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
    }), [SOCKET_URL]);

    const onConnect = useCallback(() => {
        console.log('Connected to WebSocket');
        if (stompClientRef.current) {
            stompClientRef.current.subscribe('/topic/video', (message) => handleMessage(message, "New Video"));
            stompClientRef.current.subscribe('/topic/email', (message) => handleMessage(message, "New Email"));
        }
    }, [handleMessage]);

    const onStompError = useCallback((frame) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
    }, []);

    const onWebSocketClose = useCallback((event) => {
        console.log('WebSocket closed: ', event);
    }, []);

    useEffect(() => {
        if (!token) {
            console.error('No token provided for WebSocket connection');
            return;
        }

        stompClientRef.current = new Client(clientOptions);
        stompClientRef.current.onConnect = onConnect;
        stompClientRef.current.onStompError = onStompError;
        stompClientRef.current.onWebSocketClose = onWebSocketClose;

        stompClientRef.current.activate();

        return () => {
            if (stompClientRef.current) {
                stompClientRef.current.deactivate();
                console.log("WebSocket disconnected");
            }
        };
    }, [token, clientOptions, onConnect, onStompError, onWebSocketClose]);

    return { msgWebsocket, msgWebsocketService };
};