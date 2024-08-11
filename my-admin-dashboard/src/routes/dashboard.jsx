
import '../styles/Dashboard.scss'
import '../styles/Common.scss'
import { Outlet, NavLink, useLoaderData, useNavigate } from "react-router-dom";
import { useEffect, useState, useCallback, useMemo } from "react";
import Cookies from 'js-cookie';
import { useWebSocket } from '../hooks/useWebSocket';
import { GetserviceIcon } from '../components/GetserviceIcon';
import { ToastMessage } from '../components/ToastMessage';

const POPUP_TIMEOUT = 5000;
const token = Cookies.get('token');

export async function loader() {
    try {
        let services = ["database", "message", "stats"].sort()
        return { services }
    } catch (error) {
        console.error('Error fetching data:', error);
        throw error;
    }
}
export async function action() {

}



export default function Dashboard() {
    const { services } = useLoaderData();
    const [isPopupVisible, setPopupVisible] = useState(false);
    const navigate = useNavigate();

    const host = process.env.REACT_APP_API_HOST;
    const SOCKET_URL = `${host}/ws`;

    const { msgWebsocket, msgWebsocketService } = useWebSocket(SOCKET_URL, token);

    const handleServiceClick = useCallback((service) => {
        navigate(`${service}`);
    }, [navigate]);

    const handleClosePopup = useCallback(() => {
        setPopupVisible(false);
    }, []);

    useEffect(() => {
        if (msgWebsocket) {
            setPopupVisible(true);
            const timer = setTimeout(() => {
                setPopupVisible(false);
            }, POPUP_TIMEOUT);
            return () => clearTimeout(timer);
        }
    }, [msgWebsocket]);

    const serviceList = useMemo(() => (
        services.length ? (
            <ul className='service-ul'>
                {services.map(service => (
                    <li className='service-li' key={service}>
                        <NavLink to={`${service}`} className={({ isActive, isPending }) => isActive ? "active" : isPending ? "pending" : ""}>
                            <>
                                <GetserviceIcon service={service} />
                                {service}
                            </>
                        </NavLink>
                    </li>
                ))}
            </ul>
        ) : (
            <p>
                <i>No services</i>
            </p>
        )
    ), [services, handleServiceClick]);

    return (
        <div className='dashboard'>
            <nav className='dashboard__nav'>
                <h2 className='title-nav'>NAVIGATION</h2>
                {serviceList}
            </nav>
            <main className='dashboard__content'>
                <Outlet />
            </main>
            {isPopupVisible && (
                <ToastMessage
                    msgWebsocketService={msgWebsocketService}
                    msgWebsocket={msgWebsocket}
                    onClose={handleClosePopup}
                />
            )}
        </div>
    );
}



