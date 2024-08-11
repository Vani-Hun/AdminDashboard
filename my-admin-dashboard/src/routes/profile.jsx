import React from 'react';
import '../styles/Profile.scss'
import { useLoaderData } from 'react-router';

const Profile = () => {
    const { admin } = useLoaderData();

    return (
        <div className="admin-profile">
            <h1 className="page-title">Admin Profile</h1>
            <div className="profile-card">
                <div className="profile-header">
                    <img src={admin.logo} alt="Admin Avatar" className="admin-avatar" />
                    <div className="admin-info">
                        <h2>{admin.name}</h2>
                        <p>{admin.username}</p>
                        {/* <p>john.doe@example.com</p> */}
                    </div>
                </div>
                <div className="profile-details">
                    <div className="detail-group">
                        <h3>Personal Information</h3>
                        <p><strong>Phone:</strong> +1 (555) 123-4567</p>
                        <p><strong>Location:</strong> New York, USA</p>
                        <p><strong>Joined:</strong> January 1, 2020</p>
                    </div>
                    <div className="detail-group">
                        <h3>Account Statistics</h3>
                        <p><strong>Last Login:</strong> July 27, 2024, 09:15 AM</p>
                        <p><strong>Total Logins:</strong> 523</p>
                        <p><strong>Actions Performed:</strong> 1,234</p>
                    </div>
                </div>
                <div className="profile-actions">
                    <button className="btn btn-primary">Edit Profile</button>
                    <button className="btn btn-secondary">Change Password</button>
                </div>
            </div>
        </div>
    );
};

export default Profile;