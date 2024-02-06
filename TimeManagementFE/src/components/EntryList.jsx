import { useState, useEffect } from 'react';

const EntryList = () => {

  const [entries, setEntries] = useState([]);

  const fetchData = () => {
    fetch('http://localhost:8080/entries')
      .then(response => response.json())
      .then(data => setEntries(data))
      .catch(error => console.error(error))
  };

  useEffect(fetchData, []);

  return (
    <table>
      <thead>
        <tr>
          <th>Project</th>
          <th>Date</th>
          <th>Start time</th>
          <th>End time</th>
          <th>Entry</th>
        </tr>
      </thead>
      <tbody>
        {entries.map(e =>
          <tr key={e.id}>
            <td>{e.project.title}</td>
            <td>{e.entry_date}</td>
            <td>{e.start_time}</td>
            <td>{e.end_time}</td>
            <td>{e.entry}</td>
          </tr>
        )}
      </tbody>
    </table>
  )

};

export default EntryList;