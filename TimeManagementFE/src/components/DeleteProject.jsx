import { useState } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { deleteProject } from '../services/ProjectService';

// Delete project and request confirmation for deletion

export default function DeleteProject({ token, id, setAlert, fetchProjects }) {
  const [open, setOpen] = useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleDelete = () => {
    deleteProject(token, id)
      .then(response => {
        if (response.ok) {
          fetchProjects()
          setAlert('info')
        } else {
          setAlert('error')
        }
      })
    setOpen(false);
  };

  return (
    <div>
      <Button variant="contained" color="secondary" size='small' onClick={handleClickOpen}>
        Poista
      </Button>
      <Dialog
        open={open}
        onClose={handleClose}
      >
        <DialogTitle id="alert-dialog-title">
          {"Poista projekti"}
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            Haluatko varmasti poistaa kyseisen projektin? Projektin poisto poistaa kaikki sen kirjaukset.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Peruuta</Button>
          <Button color="secondary" onClick={handleDelete} autoFocus>
            Poista projekti
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}