import { Paper, Box, Button, Table, TableBody, TableCell, TableContainer, TableHead, TablePagination, TableRow } from '@mui/material';
import React, { useState, useEffect } from 'react';
import { AgGridReact } from 'ag-grid-react';
import "ag-grid-community/styles/ag-grid.css";
import "ag-grid-community/styles/ag-theme-quartz.css";
import 'ag-grid-community/styles/ag-theme-alpine.css';
import AddProject from './AddProject';


const ProjectList = () => {

    const [projects, setProjects] = useState([]);

    // Fetching project data from the database
    const fetchData = () => {
        fetch('http://localhost:8080/projects')
            .then(response => response.json())
            .then(data => setProjects(data))
            .catch(error => console.error(error))
    };

    useEffect(fetchData, []);


    const [colDefs, setColDefs] = useState([
        {
            field: "title",
            headerName: "Projekti"
        },
        {
            field: "id",
            headerName: "Muokkaa",
        },
        {
            field: "id",
            headerName: "Poista",
        },

    ])

    return (

        <div className="ag-theme-quartz" style={{ height: 500, marginTop: 10 }}>
            <AgGridReact
                rowData={projects}
                columnDefs={colDefs}
                defaultColDef={{
                    sortable: true,
                    filter: true,
                    floatingFilter: true
                }}
                paginationAutoPageSize={true}
                paginateChildRows={true}
                autoSizeStrategy={{ type: 'fitCellContents' }}
            />
            <AddProject />
        </div>

    )

};

export default ProjectList;