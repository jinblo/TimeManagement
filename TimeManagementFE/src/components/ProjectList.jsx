import { Paper, Box, Button, Table, TableBody, TableCell, TableContainer, TableHead, TablePagination, TableRow } from '@mui/material';
import React, { useState, useEffect } from 'react';


const ProjectList = () => {

    // Käytetty tavallista MUI taulukkoa, sillä agGrid tauluun en saanut nappeja
    // Nyt listataan projektin nimi sekä jokaisella projektilla muokkaa ja poista napit
    // Lisää uusi projekti -nappi myös mukana

    const [projects, setProjects] = useState([]);

    // Fetching project data from the database
    const fetchData = () => {
        fetch('http://localhost:8080/projects')
            .then(response => response.json())
            .then(data => setProjects(data))
            .catch(error => console.error(error))
    };

    useEffect(fetchData, []);



    return (
        <Box >
            <TableContainer component={Paper} style={{marginTop: 20, marginBottom: 20 }}>
                <Table sx={{ maxWidth: 500 }}>
                    <TableHead>
                        <TableRow>
                            <TableCell>Projektin Nimi</TableCell>
                            <TableCell>Muokkaa</TableCell>
                            <TableCell>Poista</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {projects.map((project) => (
                            <TableRow
                                key={project.id}
                            >
                                <TableCell component="th" scope="row">
                                    {project.title}
                                </TableCell>
                                <TableCell>Muokkaa</TableCell>
                                <TableCell>Poista</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <Button>Lisää uusi projekti</Button>
        </Box>
    )

};

export default ProjectList;