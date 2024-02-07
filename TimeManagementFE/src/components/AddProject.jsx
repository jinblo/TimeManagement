import React, { useState } from 'react';
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField } from "@mui/material";


const AddProject = () => {


    const [project, setProject] = useState({
        title: '',
    })

    // handling all changes
    const handleChange = (e) => {
        setProject({ ...project, [e.target.name]: e.target.value})
    }

    // adding new project to the database
    const addProject = async () => {
        try {
            const response = await fetch('http://localhost:8080/projects', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(project),
            });

            if (response.ok) {
                // Project added successfully
                console.log('Project added successfully');
            } else {
                // Handle errors
                console.error('Failed to add project');
            }
        } catch (error) {
            console.error('Error adding project:', error);
        }
        setOpen(false)
    };

    // Handling dialog 
    const [open, setOpen] = useState(false);

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
                label="Projekti"
                type="text"
                onChange={e => handleChange(e)}
              />
            </DialogContent>
            <DialogActions>
              <Button onClick={() => setOpen(false)}>Peruuta</Button>
              <Button onClick={addProject}>Tallenna</Button>
            </DialogActions>
          </Dialog>
        </div >
      )
    }
    

export default AddProject;