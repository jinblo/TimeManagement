import { useState, useEffect } from 'react';
import { AgGridReact } from 'ag-grid-react';
import "ag-grid-community/styles/ag-grid.css";
import "ag-grid-community/styles/ag-theme-quartz.css";
import AddEntry from './AddEntry';
import DeleteEntry from './DeleteEntry';
import EditEntry from './EditEntry';

// Listataan työaikakirjausten tiedot, sekä jokaiselle kirjaukselle poista nappi
// Lisää uusi työaikakirjaus -nappi myös mukana

const EntryList = () => {
  const [entries, setEntries] = useState([])
  const [projects, setProjects] = useState()

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
          console.log("Entry saved successfully")
          fetchData()
        } else {
          console.log("Failed to save")
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
      cellRenderer: params => {
        return (
          <DeleteEntry entry_id={params.value} deleteEntry={fetchWithOptions} />
        )
      }
    },
  ])

  return (
    <div>
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