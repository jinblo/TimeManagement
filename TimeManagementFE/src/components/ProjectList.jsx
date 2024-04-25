import React, { useState, useEffect, useMemo } from 'react';
import { AgGridReact } from 'ag-grid-react';
import "ag-grid-community/styles/ag-grid.css";
import "ag-grid-community/styles/ag-theme-quartz.css";
import AddProject from './AddProject';
import DeleteProject from './DeleteProject';
import EditProject from './EditProject';
import AlertMessage from './AlertMessage';
import { useAuth } from '../services/AuthProvider';
import { getProjects } from '../services/ProjectService';


// Listing project details. Each project has delete and edit buttons
// Also includes a 'Add New Project' button 

const ProjectList = () => {
    const { token } = useAuth()
    const [projects, setProjects] = useState([]);
    const [alert, setAlert] = useState(null)

    const alertMessage = useMemo(() => {
        switch (alert) {
            case 'success': {
                return <AlertMessage alert={alert} alertMessage="Projekti tallennettu onnistuneesti" setAlert={setAlert} />
            }
            case 'info': {
                return <AlertMessage alert={alert} alertMessage="Projekti poistettu onnistuneesti" setAlert={setAlert} />
            }
            case 'error': {
                return <AlertMessage alert={alert} alertMessage="Projektin tallennus epäonnistui" setAlert={setAlert} />
            }
            default: {
                return <></>
            }
        }
    }, [alert]);

    // Fetching project data from the database and sorting them
    const fetchProjects = () => {
        getProjects(token)
        .then(data => {
            const sortedProjects = data.sort((a, b) => {
                if (a.role !== b.role) {
                    const order = { OWNER: 1, USER: 2, VIEWER: 3 };
                    return order[a.role] - order[b.role];
                } else {
                    return a.project.title.localeCompare(b.project.title);
                }
            });
            setProjects(sortedProjects);
        });
    }
    useEffect(fetchProjects, []);

    // Details showing in the table
    const [colDefs, setColDefs] = useState([
        {
            field: "project.title",
            headerName: "Projekti"
        },
        {
            field: "role",
            headerName: "Rooli",
            cellRenderer: params => {
                let roleText = "";
                switch (params.value) {
                    case "OWNER":
                        roleText = "Omistaja";
                        break;
                    case "USER":
                        roleText = "Käyttäjä";
                        break;
                    case "VIEWER":
                        roleText = "Seuraaja";
                        break;
                    default:
                        roleText = params.value;
                        break;
                }
                return roleText;
            }
        },
        {
            field: "project.id",
            headerName: "Muokkaa",
            cellRenderer: params => {
                return (
                    params.data.role === "OWNER" ?
                        <EditProject token={token} editData={params.data.project} setAlert={setAlert} fetchProjects={fetchProjects} />
                        : null
                )
            }
        },
        {
            field: "project.id",
            headerName: "Poista",
            cellRenderer: params => {
                return (
                    params.data.role === "OWNER" ?
                        <DeleteProject token={token} id={params.value} setAlert={setAlert} fetchProjects={fetchProjects} />
                        : null
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
            <AddProject token={token} setAlert={setAlert} fetchProjects={fetchProjects} />
        </div>
    )
};

export default ProjectList;