import { useState } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { deleteEntry } from '../services/EntryService';

// Poistetaan työaikakirjaus ja pyydetään varmentamaan kirjauksen poisto

export default function DeleteEntry({ token, entry_id, setAlert, fetchEntries }) {
  const [open, setOpen] = useState(false);

  const handleDelete = () => {
    deleteEntry(token, entry_id)
      .then(response => {
        if (response.ok) {
          fetchEntries()
          setAlert('info')
        } else {
          setAlert('error')
        }
      })
    setOpen(false);
  };

  return (
    <div>
      <Button variant="outlined" color="error" size='small' onClick={() => setOpen(true)}>
        Poista
      </Button>
      <Dialog
        open={open}
        onClose={() => setOpen(false)}
      >
        <DialogTitle>
          {"Poista työaikakirjaus"}
        </DialogTitle>
        <DialogContent>
          <DialogContentText>
            Vahvista työaikakirjauksen poisto.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpen(false)}>Peruuta</Button>
          <Button color="error" onClick={handleDelete} autoFocus>
            Poista kirjaus
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}