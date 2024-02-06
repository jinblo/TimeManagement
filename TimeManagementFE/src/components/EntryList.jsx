import { useState } from 'react';
import { Table, Thead, Tbody, Tr, Th, Td } from 'react-super-responsive-table';
import 'react-super-responsive-table/dist/SuperResponsiveTableStyle.css';

const EntryList = () => {

  const [entries, setEntries] = useState([]);

  const fetchData = () => {
    fetch('http://localhost:8080/projects')
      .then(response => response.json())
      .then(data => setEntries(data))
      .catch(error => console.error(error))
  };

  useEffect(fetchData, []);

  return (
    <Table>
      <Thead>
        <Tr>
          <Th>Project</Th>
          <Th>Date</Th>
        </Tr>
      </Thead>

      <Tbody>
        <Tr>
          {entries.map(entry =>
            <Td key={entry.id}>{entry.title}</Td>)}
        </Tr>
      </Tbody>
    </Table>
  )

};

export default EntryList;