import { useState, useEffect, useMemo } from 'react';
import { AgGridReact } from 'ag-grid-react';
import "ag-grid-community/styles/ag-grid.css";
import "ag-grid-community/styles/ag-theme-quartz.css";
import AddEntry from './AddEntry';
import DeleteEntry from './DeleteEntry';
import EditEntry from './EditEntry';
import AlertMessage from './AlertMessage';
import { useAuth } from '../services/AuthProvider';


// Listataan työaikakirjausten tiedot, sekä jokaiselle kirjaukselle poista nappi
// Lisää uusi työaikakirjaus -nappi myös mukana

const EntryList = () => {
  const { token } = useAuth()
  const baseUrl = 'http://localhost:8080'
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

  // Kirjausten hakeminen APIsta
  useEffect(() => {
    fetch(`${baseUrl}/entries`, {
      headers: {
        'Authorization': token
      }
    })
      .then(response => response.json())
      .then(data => setEntries(data))
      .catch(error => console.error(error))
  }, []);

  // Projektien hakeminen APIsta
  useEffect(() => {
    fetch(`${baseUrl}/projects`, {
      headers: {
        'Authorization': token
      }
    })
      .then(response => response.json())
      .then(data => setProjects(data))
      .catch(error => console.error(error))
  }, []);


  // Post, Put tai Delete pyyntöjen tekeminen APIin
  const fetchWithOptions = (href, options) => {
    fetch(href, {
      ...options,
      headers: {
        'Authorization': token
      }
    })
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

  // Ag-gridin sarakkeiden määritys
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