import React, { useEffect, useRef, useState } from 'react';
import { GetserviceIcon } from './GetserviceIcon';
import Cookies from 'js-cookie';
import { useNavigate } from 'react-router';

export function Masthead({ admin }) {
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const dropdownRef = useRef(null);
    const timeoutRef = useRef(null);
    const navigate = useNavigate();

    const showDropdown = () => {
        if (timeoutRef.current) {
            clearTimeout(timeoutRef.current);
        }
        setIsDropdownOpen(true);
    };

    const hideDropdown = () => {
        timeoutRef.current = setTimeout(() => {
            setIsDropdownOpen(false);
        }, 2000);
    };

    useEffect(() => {
        return () => {
            if (timeoutRef.current) {
                clearTimeout(timeoutRef.current);
            }
        };
    }, []);
    const handleLogout = () => {
        Cookies.remove("token")
        navigate('/login');
    };
    return (
        <div className='masthead'>
            <div className='masthead__logo'>
                <a href="/main/dashboard">
                    <div>
                        <GetserviceIcon service="logo" size="40px" />
                    </div>
                </a>
                <div className='name-company'>Vani</div>
            </div>
            <div className='masthead__content'>
                <div className='masthead__personal-info'
                    onMouseEnter={showDropdown}
                    onMouseLeave={hideDropdown}>
                    <img src={admin.logo} alt="" />
                    {isDropdownOpen && (
                        <div className='dropdown-menu' ref={dropdownRef}>
                            <ul>
                                <li><a href="/main/profile">Profile</a></li>
                                <li><a href="/main/setting">Settings</a></li>
                                <li><a onClick={handleLogout}>Log out</a></li>
                            </ul>
                        </div>
                    )}
                </div>
                {/* <div className='masthead__settings'>
                    <GetserviceIcon service="setting" size="25px" />
                </div> */}
            </div>
        </div>
    )
}