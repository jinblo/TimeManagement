import React, { useState } from 'react';
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField } from "@mui/material";

// function for editing project information. Opens a dialog with information from chosen project.
export default function EditProject(props) {

    const [project, setProject] = useState({
        title: props.editData.title || "",
    });

    // Error message shown to user
    const [errorMessage, setErrorMessage] = useState('');

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
                const response = await fetch(`http://localhost:8080/projects/${props.editData.id}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(project),
                });

                if (response.ok) {
                    // Project edited successfully
                    console.log('Project edited successfully');
                    setErrorMessage('');
                    props.editProject();
                    setOpen(false)
                } else {
                    // Handle errors
                    console.error('Failed to edit project');
                    setErrorMessage('Failed to edit project');
                }
            } catch (error) {
                console.error('Error editing project:', error);
                setErrorMessage('Error editing project');
            }
        }
    };

    // Handling dialog 
    const [open, setOpen] = useState(false);

    // Returning a dialog, where a user can edit project information
    return (
        <div>
            <Button variant="outlined" color="info" size='small' onClick={() => setOpen(true)}>Muokkaa
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
                    <Button onClick={() => setOpen(false)}>Peruuta</Button>
                    <Button onClick={editProject}>Tallenna</Button>
                </DialogActions>
            </Dialog>
        </div >
    )

}