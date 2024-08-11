import React from 'react';
import '../styles/Setting.scss'
const Setting = () => {
    return (
        <div className="admin-settings">
            <h1 className="page-title">Admin Settings</h1>
            <div className="settings-card">
                <form>
                    <div className="form-section">
                        <h2>General Settings</h2>
                        <div className="form-group">
                            <label htmlFor="siteName">Site Name</label>
                            <input type="text" id="siteName" name="siteName" defaultValue="My Admin Dashboard" />
                        </div>
                        <div className="form-group">
                            <label htmlFor="siteUrl">Site URL</label>
                            <input type="url" id="siteUrl" name="siteUrl" defaultValue="https://admin.example.com" />
                        </div>
                    </div>

                    <div className="form-section">
                        <h2>Email Settings</h2>
                        <div className="form-group">
                            <label htmlFor="adminEmail">Admin Email</label>
                            <input type="email" id="adminEmail" name="adminEmail" defaultValue="admin@example.com" />
                        </div>
                        <div className="form-group">
                            <label htmlFor="emailNotifications">Email Notifications</label>
                            <select id="emailNotifications" name="emailNotifications">
                                <option value="all">All Notifications</option>
                                <option value="important">Important Only</option>
                                <option value="none">None</option>
                            </select>
                        </div>
                    </div>

                    <div className="form-section">
                        <h2>Security Settings</h2>
                        <div className="form-group">
                            <label htmlFor="twoFactor">Two-Factor Authentication</label>
                            <input type="checkbox" id="twoFactor" name="twoFactor" />
                            <span>Enable Two-Factor Authentication</span>
                        </div>
                        <div className="form-group">
                            <label htmlFor="sessionTimeout">Session Timeout (minutes)</label>
                            <input type="number" id="sessionTimeout" name="sessionTimeout" defaultValue="30" />
                        </div>
                    </div>

                    <div className="form-actions">
                        <button type="submit" className="btn btn-primary">Save Changes</button>
                        <button type="reset" className="btn btn-secondary">Reset</button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default Setting;