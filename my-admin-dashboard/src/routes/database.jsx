import React, { useCallback, useMemo, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import useTableData from '../hooks/useTableData';
import usePagination from '../hooks/usePagination';
import useSearch from '../hooks/useSearch';
import Pagination from '../components/Pagination';
import TableForm from '../components/TableForm';
import ErrorBoundary from '../components/ErrorBoundary';

const ITEMS_PER_PAGE = 10;

const Database = () => {
    const navigate = useNavigate();
    const { table } = useParams();
    const { tableData, isLoading, error, totalPages, fetchData } = useTableData(ITEMS_PER_PAGE);
    const { currentPage, handlePageChange } = usePagination(tableData.length, ITEMS_PER_PAGE);
    const { search, handleSearch, isSearching } = useSearch((value) => fetchData(table, currentPage, value));
    console.log("search:", search)

    useEffect(() => {
        if (table) {
            fetchData(table, currentPage);
        }
    }, [table, currentPage, fetchData]);

    const renderTableHeaders = useMemo(() => {
        if (!tableData.length) return null;
        const keys = Object.keys(tableData[0]);
        return (
            <tr>
                <th key="index">Index</th>
                {keys.map(key => (
                    <th key={key}>
                        <div className='head-container'>
                            {key}
                            <div className='arrow-container'>
                                <svg xmlns="http://www.w3.org/2000/svg" fill="#000000" height="20" width="20" version="1.1" id="Layer_1" viewBox="0 0 485 485">
                                    <g>
                                        <path d="M0,0v485h485V0H0z M455,455H30V30h425V455z" />
                                        <polygon points="370.589,194.565 349.411,173.317 242.5,279.88 135.589,173.317 114.411,194.565 242.5,322.237" />
                                    </g>
                                </svg>
                            </div>
                        </div>
                    </th>
                ))}
            </tr>
        );
    }, [tableData]);

    const renderTableRows = useCallback(() => {
        return tableData.map((row, rowIndex) => (
            <tr key={row.id || rowIndex} onClick={() => navigate(`${row.id}`)}>
                <td>{rowIndex + 1}</td>
                {Object.values(row).map((value, cellIndex) => (
                    <td key={cellIndex}>
                        {typeof value === 'boolean' ? (value ? 'True' : 'False') : value}
                    </td>
                ))}
            </tr>
        ));
    }, [tableData, navigate]);

    const handleFormSubmit = useCallback((formData) => {
        // Handle form submission logic here
        console.log('Form data:', formData);
    }, []);

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <ErrorBoundary>
            <div className={`table ${isLoading ? "loading" : ""}`}>
                <table>
                    <thead>{renderTableHeaders}</thead>
                    <tbody>{renderTableRows()}</tbody>
                </table>
            </div>
            <div className={`foot-container ${isLoading ? "loading" : ""}`}>
                <div className='option-container'>
                    <div className='search-form'>
                        <input id="search"
                            type="search"
                            placeholder="Search"
                            value={search}
                            onChange={handleSearch}
                            aria-label="Search contacts"
                        />
                        {isSearching && <div className="search-spinner" />}
                    </div>
                </div>

                <TableForm tableData={tableData} onSubmit={handleFormSubmit} />
                <Pagination
                    currentPage={currentPage}
                    totalPages={totalPages}
                    onPageChange={handlePageChange}
                />
            </div>
        </ErrorBoundary>
    );
};

export default React.memo(Database);

