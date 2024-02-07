import { useState, useEffect } from 'react';
import { AgGridReact } from 'ag-grid-react';
import "ag-grid-community/styles/ag-grid.css";
import "ag-grid-community/styles/ag-theme-quartz.css";
import AddEntry from './AddEntry';
import DeleteEntry from './DeleteEntry';

const EntryList = () => {
  const [entries, setEntries] = useState([]);

  const fetchData = () => {
    fetch('http://localhost:8080/entries')
      .then(response => response.json())
      .then(data => setEntries(data))
      .catch(error => console.error(error))
  };

  useEffect(fetchData, []);

  const fetchWithOptions = (href, options) => {
    fetch(href, options)
      .then(response => fetchData())
      .catch(error => console.error(error))
  }

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
      field: "entry",
      headerName: "Muistiinpanot"
    },
    {
      field: "entry_id",
      headerName: "",
      cellRenderer: params => {
        return (
          <DeleteEntry entry_id={params.value} deleteEntry={fetchWithOptions} />
        )
      }
    },
  ])

  return (
    <div>
      <AddEntry />
      <div className="ag-theme-quartz" style={{ height: 500, marginTop: 5 }}>
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