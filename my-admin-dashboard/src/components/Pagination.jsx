import React from 'react';

const Pagination = ({ currentPage, totalPages, onPageChange }) => {
    const pageNumbers = Array.from({ length: totalPages }, (_, i) => i + 1);

    return (
        <div className="pagination">
            <ul>
                {pageNumbers.map((number) => (
                    <li key={number} className={currentPage === number ? 'active' : ''}>
                        <a onClick={() => onPageChange(number)}>{number}</a>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default React.memo(Pagination);