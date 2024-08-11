import React from 'react';
import PropTypes from 'prop-types';

const DateRangeSelector = ({ dateRange, setDateRange }) => {
    const handleStartDateChange = (e) => setDateRange({ ...dateRange, start: e.target.value });
    const handleEndDateChange = (e) => setDateRange({ ...dateRange, end: e.target.value });

    return (
        <div className="date-range-selector">
            <label>
                Start Date:
                <input type="date" value={dateRange.start} onChange={handleStartDateChange} />
            </label>
            <label>
                End Date:
                <input type="date" value={dateRange.end} onChange={handleEndDateChange} />
            </label>
        </div>
    );
};

DateRangeSelector.propTypes = {
    dateRange: PropTypes.shape({
        start: PropTypes.string,
        end: PropTypes.string,
    }).isRequired,
    setDateRange: PropTypes.func.isRequired,
};

export default React.memo(DateRangeSelector);