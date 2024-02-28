import { useState, useEffect, useMemo } from 'react';
import { AgGridReact } from 'ag-grid-react';
import "ag-grid-community/styles/ag-grid.css";
import "ag-grid-community/styles/ag-theme-quartz.css";
import AddEntry from './AddEntry';
import DeleteEntry from './DeleteEntry';
import EditEntry from './EditEntry';
import { Alert } from '@mui/material';
import AlertMessage from './AlertMessage';

// Listataan työaikakirjausten tiedot, sekä jokaiselle kirjaukselle poista nappi
// Lisää uusi työaikakirjaus -nappi myös mukana

const EntryList = () => {
  const [entries, setEntries] = useState([])
  const [projects, setProjects] = useState()
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


  // Fetch entries from REST API
  const fetchData = () => {
    fetch('http://localhost:8080/entries')
      .then(response => response.json())
      .then(data => setEntries(data))
      .catch(error => console.error(error))
  };

  useEffect(fetchData, []);

  // Fetching all projects for select
  useEffect(() => {
    fetch('http://localhost:8080/projects')
      .then(response => response.json())
      .then(data => setProjects(data))
      .catch(error => console.error(error))
  }, [])

  // Create, Update or Delete entries from REST API
  const fetchWithOptions = (href, options) => {
    fetch(href, options)
      .then(response => {
        if (response.ok) {
          fetchData()
          setAlert('success')
        } else {
          setAlert('error')
        }
      }
      )
      .catch(error => {
        console.error(error)
      })
  }

  // Defining columns for ag-grid
  const [colDefs, setColDefs] = useState([
    {
      field: "project.title",
      headerName: "Projekti"
    },
    {
      field: "entry_date",
      headerName: "Päivämäärä",
    },
    {
      field: "start_time",
      headerName: "Aloitusaika",
    },
    {
      field: "end_time",
      headerName: "Lopetusaika",
    },
    {
      field: "comment",
      headerName: "Muistiinpanot"
    },
    {
      field: "entry_id",
      headerName: "",
      sortable: false,
      filter: false,
      width: 110,
      cellRenderer: params => {
        return (
          <EditEntry oldEntry={params.data} saveEntry={fetchWithOptions} projects={projects} />
        )
      }
    },
    {
      field: "entry_id",
      headerName: "",
      sortable: false,
      filter: false,
      width: 100,
      cellRenderer: params => {
        return (
          <DeleteEntry entry_id={params.value} deleteEntry={fetchWithOptions} />
        )
      }
    },
  ])

  return (
    <div>
      {alertMessage}
      <AddEntry saveEntry={fetchWithOptions} projects={projects} />
      <div className="ag-theme-quartz" style={{ height: 500, marginTop: 10 }}>
        <AgGridReact
          rowData={entries}
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
      </div>
    </div>
  )
};

export default EntryList;