import { useState } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { deleteEntry } from '../services/EntryService';
import { useAuth } from '../services/AuthProvider';

// Poistetaan työaikakirjaus ja pyydetään varmentamaan kirjauksen poisto

export default function DeleteEntry({ entry_id, setAlert, fetchEntries }) {
  const { token } = useAuth()
  const [open, setOpen] = useState(false);

  const handleDelete = () => {
    deleteEntry(token, entry_id)
      .then(response => {
        if (response.ok) {
          fetchEntries()
          setAlert('deleted')
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
          {"Delete"}
        </DialogTitle>
        <DialogContent>
          <DialogContentText>
            Vahvista työaikakirjauksen poisto.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpen(false)}>Peruuta</Button>
          <Button color="error" onClick={handleDelete} autoFocus>
            Poista
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}