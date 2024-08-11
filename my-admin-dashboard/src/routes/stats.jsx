import React, { useState } from 'react';
import { GetserviceIcon } from '../components/GetserviceIcon';
import DateRangeSelector from '../components/DateRangeSelector';
import KeyMetricsSummary from '../components/KeyMetricsSummary';
import BarChart from '../components/Charts/BarChart';
import ErrorPage from '../components/Common/Error-page';
import useStatsData from '../hooks/useStatsData';
import '../styles/Dashboard.scss'
import '../styles/Common.scss'
const StatsPage = () => {
    const [dateRange, setDateRange] = useState({ start: '', end: '' });
    const { data, keyMetrics, isLoading, error } = useStatsData(dateRange);

    if (isLoading) return null;
    if (error) return <ErrorPage message={error} />;

    return (
        <>
            <div className="service-title">
                <GetserviceIcon service={'stats'} size="40px" />
                <h1>Stats</h1>
            </div>
            <div className='service-stats-container'>
                <div className='stats-filter'>
                    <DateRangeSelector dateRange={dateRange} setDateRange={setDateRange} />
                </div>
                <div className="stats-page">
                    <KeyMetricsSummary data={keyMetrics} />
                    <div className="charts">
                        <BarChart data={data} />
                    </div>
                </div>
            </div>
        </>
    );
};

export default StatsPage;
// const Filters = ({ filters, setFilters }) => {
//     const handleFilterChange = (e) => setFilters({ ...filters, [e.target.name]: e.target.value });

//     return (
//         <div className="filters">
//             <label>
//                 Filter Name:
//                 <input type="text" name="filterName" value={filters.filterName || ''} onChange={handleFilterChange} />
//             </label>
//             {/* Add more filters as needed */}
//         </div>
//     );
// };
// const BarChart = ({ data }) => {
//     const chartData = {
//         labels: data.map(item => item.label),
//         datasets: [
//             {
//                 label: 'Data',
//                 data: data.map(item => item.count),
//                 backgroundColor: 'rgba(75,192,192,1)',
//                 borderColor: 'rgba(75,192,192,1)',
//                 borderWidth: 1,
//             },
//         ],
//     };

//     return <Bar data={chartData} />;
// };
// const LineChart = ({ data }) => {
//     const chartData = {
//         labels: data.map(item => item.label),
//         datasets: [
//             {
//                 label: 'Line Chart Data',
//                 data: data.map(item => item.count),
//                 backgroundColor: 'rgba(75,192,192,1)',
//                 borderColor: 'rgba(75,192,192,1)',
//                 borderWidth: 1,
//             },
//         ],
//     };

//     return <Line data={chartData} />;
// };
// const PieChart = ({ data }) => {
//     const chartData = {
//         labels: data.map(item => item.label),
//         datasets: [
//             {
//                 label: 'Data',
//                 data: data.map(item => item.count),
//                 backgroundColor: [
//                     'rgba(255,99,132,1)',
//                     'rgba(54,162,235,1)',
//                     'rgba(255,206,86,1)',
//                     'rgba(75,192,192,1)',
//                     'rgba(153,102,255,1)',
//                     'rgba(255,159,64,1)'
//                 ],
//                 borderColor: [
//                     'rgba(255,99,132,1)',
//                     'rgba(54,162,235,1)',
//                     'rgba(255,206,86,1)',
//                     'rgba(75,192,192,1)',
//                     'rgba(153,102,255,1)',
//                     'rgba(255,159,64,1)'
//                 ],
//                 borderWidth: 1,
//             },
//         ],
//     };

//     return <Pie data={chartData} />;
// };
// const HeatmapChart = ({ data }) => {
//     const xLabels = data.map(item => item.label); // e.g., ['Jan', 'Feb', 'Mar']
//     const yLabels = ['Metric 1', 'Metric 2', 'Metric 3'];
//     const heatmapData = [
//         [10, 30, 40],
//         [20, 50, 60],
//         [30, 70, 90],
//         [40, 90, 100],
//         [50, 110, 120],
//         [60, 130, 140],
//     ];

//     return (
//         <div style={{ width: '100%' }}>
//             <HeatMap
//                 xLabels={xLabels}
//                 yLabels={yLabels}
//                 data={heatmapData}
//                 height={50}
//                 onClick={(x, y) => alert(`Clicked (${x}, ${y})`)}
//             />
//         </div>
//     );
// };
// const DataTable = ({ data }) => {
//     return (
//         <table className="data-table">
//             <thead>
//                 <tr>
//                     <th>ID</th>
//                     <th>Name</th>
//                     <th>Count</th>
//                     {/* Add more columns as needed */}
//                 </tr>
//             </thead>
//             <tbody>
//                 {data.map((item, index) => (
//                     <tr key={index}>
//                         <td>{item.id}</td>
//                         <td>{item.name}</td>
//                         <td>{item.count}</td>
//                         {/* Add more columns as needed */}
//                     </tr>
//                 ))}
//             </tbody>
//         </table>
//     );
// };


