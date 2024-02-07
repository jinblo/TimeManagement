import { useState } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

// Poistetaan työaikakirjaus ja pyydetään varmentamaan kirjauksen poisto

export default function DeleteEntry(props) {
  const [open, setOpen] = useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleDelete = () => {
    const href = `http://localhost:8080/entries/${props.entry_id}`
    const options = {
      method: 'delete'
    }
    props.deleteEntry(href, options);
    setOpen(false);
  };

  return (
    <div>
      <Button variant="outlined" color="error" size='small' onClick={handleClickOpen}>
        Poista
      </Button>
      <Dialog
        open={open}
        onClose={handleClose}
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
          <Button onClick={handleClose}>Peruuta</Button>
          <Button color="error" onClick={handleDelete} autoFocus>
            Poista
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}