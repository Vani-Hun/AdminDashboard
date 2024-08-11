
import '../styles/Dashboard.scss'
import '../styles/Common.scss'
import { Outlet, NavLink, Link, useLoaderData, Form, redirect, useNavigation, useSubmit, useNavigate } from "react-router-dom";
import { useEffect, useState, useCallback, useMemo } from "react";
import { GetserviceIcon } from '../components/GetserviceIcon';


export async function loader({ params }) {
    return { params }
}
export async function action() {

}

const TABLES = ['admin', 'customer', 'video', 'conversation', 'comment', 'message', 'notification', 'hashtag'];
const SERVICE = "database";

export default function DashboardContent() {
    const { params } = useLoaderData();
    const navigate = useNavigate();
    const [selectedTable, setSelectedTable] = useState(params.table || "");

    useEffect(() => {
        setSelectedTable(params.table || "");
    }, [params.table]);

    const handleTableChange = useCallback((event) => {
        const selectedValue = event.target.value;
        setSelectedTable(selectedValue);
        navigate(selectedValue);
    }, [navigate]);

    const tableOptions = useMemo(() => (
        TABLES.map(table => (
            <option key={table} value={table}>{table}</option>
        ))
    ), []);

    return (
        <>
            <header className="service-title">
                <GetserviceIcon service={SERVICE} size="40px" />
                <h1>Database</h1>
            </header>
            <main className="service-database-container">
                <section className="selected-table">
                    <h4>Table name</h4>
                    <select
                        value={selectedTable}
                        onChange={handleTableChange}
                        aria-label="Select a table"
                    >
                        <option value="">Select a table</option>
                        {tableOptions}
                    </select>
                </section>
                <Outlet />
            </main>
        </>
    );
}


