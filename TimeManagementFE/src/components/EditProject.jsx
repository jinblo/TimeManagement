import React, { useState } from 'react';
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField } from "@mui/material";
import { putProject } from '../services/ProjectService';

// function for editing project information. Opens a dialog with information from chosen project.
export default function EditProject({ token, editData, setAlert, fetchProjects }) {

    const [project, setProject] = useState({
        title: editData.title || "",
        id: editData.id || ""
    });

    // Error message shown to user
    const [errorMessage, setErrorMessage] = useState('');

    // Handling dialog 
    const [open, setOpen] = useState(false);

    // Handling all changes
    const handleChange = (e) => {
        setProject({ ...project, [e.target.name]: e.target.value })
    }

    // Editing a project info
    const editProject = async () => {
        // Checking that the title is not empty
        if (project.title.trim() === "") {
            setErrorMessage('Nimi ei voi olla tyhjä');
        } else {
            try {
                putProject(token, project)
                    .then(response => {
                        if (response.ok) {
                            // Project edited successfully
                            console.log('Project edited successfully');
                            fetchProjects();
                            setAlert('success')
                            handleClose();
                        } else {
                            // Handle errors
                            console.error('Failed to edit project');
                            setErrorMessage('Failed to edit project');
                            setAlert('error')
                        }
                    })
            } catch (error) {
                console.error('Error editing project:', error);
                setErrorMessage('Error editing project');
            }
        }
    };

    // Clearing the form and closing dialog
    const handleClose = () => {
        setProject({ title: '' });
        setErrorMessage('');
        setOpen(false);
    }

    // Returning a dialog, where a user can edit project information
    return (
        <div>
            <Button variant="contained" size='small' onClick={() => setOpen(true)}>Muokkaa
            </Button>
            <Dialog open={open} onClose={() => setOpen(false)}>
                <DialogTitle>Muokkaa projektia</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        required
                        margin='normal'
                        fullWidth
                        name="title"
                        label="Projektin nimi"
                        type="text"
                        value={project.title}
                        onChange={handleChange}
                        error={errorMessage !== ''}
                        helperText={errorMessage}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Peruuta</Button>
                    <Button onClick={editProject}>Tallenna</Button>
                </DialogActions>
            </Dialog>
        </div >
    )

}