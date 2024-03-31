import { useState, useEffect, useMemo } from 'react';
import { AgGridReact } from 'ag-grid-react';
import "ag-grid-community/styles/ag-grid.css";
import "ag-grid-community/styles/ag-theme-quartz.css";
import AddEntry from './AddEntry';
import DeleteEntry from './DeleteEntry';
import EditEntry from './EditEntry';
import AlertMessage from './AlertMessage';
import { useAuth } from '../services/AuthProvider';
import { getEntries } from '../services/EntryService';
import { getProjects } from '../services/ProjectService';


// Listataan työaikakirjausten tiedot, sekä jokaiselle kirjaukselle poista nappi
// Lisää uusi työaikakirjaus -nappi myös mukana

const EntryList = () => {
  const { token } = useAuth()
  const [entries, setEntries] = useState([])
  const [projects, setProjects] = useState([])
  const [alert, setAlert] = useState(null)
  const alertMessage = useMemo(() => {
    switch (alert) {
      case 'success': {
        return <AlertMessage alert={alert} alertMessage="Kirjaus tallennettu onnistuneesti" setAlert={setAlert} />
      }
      case 'info': {
        return <AlertMessage alert={alert} alertMessage="Kirjaus poistettu onnistuneesti" setAlert={setAlert} />
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
  const fetchEntries = () => {
    getEntries(token)
      .then(data => setEntries(data))
  }
  useEffect(fetchEntries, []);

  // Projektien hakeminen APIsta
  const fetchProjects = () => {
    getProjects(token)
      .then(data => setProjects(data))
  }
  useEffect(fetchProjects, []);


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
      headerName: "Muokkaa",
      sortable: false,
      filter: false,
      width: 110,
      cellRenderer: params => {
        return (
          <EditEntry token={token} oldEntry={params.data} setAlert={setAlert} fetchEntries={fetchEntries} />
        )
      }
    },
    {
      field: "entry_id",
      headerName: "Poista",
      sortable: false,
      filter: false,
      width: 100,
      cellRenderer: params => {
        return (
          <DeleteEntry token={token} entry_id={params.value} setAlert={setAlert} fetchEntries={fetchEntries} />
        )
      }
    },
  ])

  return (
    <div className="ag-theme-quartz" style={{ height: 400, marginTop: 10 }}>
      {alertMessage}
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
      <AddEntry token={token} projects={projects} setAlert={setAlert} fetchEntries={fetchEntries} />
    </div>
  )
};

export default EntryList;