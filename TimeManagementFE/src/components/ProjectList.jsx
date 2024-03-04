import React, { useState, useEffect, useMemo } from 'react';
import { AgGridReact } from 'ag-grid-react';
import "ag-grid-community/styles/ag-grid.css";
import "ag-grid-community/styles/ag-theme-quartz.css";
import AddProject from './AddProject';
import DeleteProject from './DeleteProject';
import EditProject from './EditProject';
import AlertMessage from './AlertMessage';

// Listataan projektin tiedot. Jokaisella projektilla poista ja muokkaa napit
// Lisää uusi projekti -nappi myös mukana 

const ProjectList = () => {

    const [projects, setProjects] = useState([]);
    const [alert, setAlert] = useState(null)

    const alertMessage = useMemo(() => {
        switch (alert) {
            case 'success': {
                return <AlertMessage alert={alert} alertMessage="Kirjaus tallennettu onnistuneesti" setAlert={setAlert} />
            }

            case 'error': {
                return <AlertMessage alert={alert} alertMessage="Kirjauksen tallennus epäonnistui" setAlert={setAlert} />
            }

            default: {
                return <></>
            }
        }
    }, [alert]);

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
            .then(response => {
                if (response.ok) {
                    fetchData()
                    setAlert('success')
                } else {
                    setAlert('error')
                }
            })
            .catch(error => {
                console.error(error)
            })
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
            cellRenderer: params => {
                return (
                    <EditProject editData={params.data} editProject={fetchWithOptions} />
                )
            }
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
        <div className="ag-theme-quartz" style={{ height: 400, marginTop: 10 }}>
            {alertMessage}
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
            <AddProject addProject={fetchWithOptions} />
        </div>
    )
};

export default ProjectList;