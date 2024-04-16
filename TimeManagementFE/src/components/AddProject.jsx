import React, { useState } from 'react';
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField } from "@mui/material";
import { postProject } from '../services/ProjectService';

// Lisätään uusi projekti

export default function AddProject({ token, setAlert, fetchProjects }) {


    const emptyProject = {
        title: '',
    }

    const [project, setProject] = useState(emptyProject);
    const [errorMessage, setErrorMessage] = useState('');

    // Handling dialog 
    const [open, setOpen] = useState(false);

    // handling all changes
    const handleChange = (e) => {
        setProject({ ...project, [e.target.name]: e.target.value })
    }

    // adding new project to the database
    const addProject = () => {

        if (project.title.trim() === "") {
            setErrorMessage('Nimi ei voi olla tyhjä');
        } else {
            try {
                postProject(token, project)
                    .then(response => {
                        if (response.ok) {
                            // Project added successfully
                            console.log('Project added successfully');
                            fetchProjects();
                            setAlert('success')
                        } else {
                            // Handle errors
                            console.error('Failed to add project');
                            setAlert('error')
                        }
                    })
                handleClose()
            } catch (error) {
                console.error('Error adding project:', error);
                setErrorMessage('Error adding project');
            }
        }
    };

    // Clearing the form and closing dialog
    const handleClose = () => {
        setProject(emptyProject);
        setErrorMessage('');
        setOpen(false);
    }

    return (
        <div style={{ marginTop: 20 }}>
            <Button variant="contained" onClick={() => setOpen(true)} >Lisää uusi projekti</Button>
            <Dialog open={open} onClose={() => setOpen(false)}>
                <DialogTitle>Lisää uusi projekti</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        required
                        margin='normal'
                        fullWidth
                        name="title"
                        label="Projektin nimi"
                        type="text"
                        onChange={e => handleChange(e)}
                        error={errorMessage !== ''}
                        helperText={errorMessage}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Peruuta</Button>
                    <Button onClick={addProject}>Tallenna</Button>
                </DialogActions>
            </Dialog>
        </div >
    )
}
