import React, { useState, useEffect } from 'react';
import { AgGridReact } from 'ag-grid-react';
import "ag-grid-community/styles/ag-grid.css";
import "ag-grid-community/styles/ag-theme-quartz.css";
import AddProject from './AddProject';
import DeleteProject from './DeleteProject';


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

    const fetchWithOptions = (href, options) => {
        fetch(href, options)
          .then(response => fetchData())
          .catch(error => console.error(error))
      }

    // Details showing in the table
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
            cellRenderer: params => {
                return (
                    <DeleteProject id={params.value} deleteProject={fetchWithOptions} />
                )
            }
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
            <AddProject addProject={fetchWithOptions}/>
        </div>

    )

};

export default ProjectList;