import { useState } from 'react';
import { jwtDecode } from "jwt-decode";
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField } from "@mui/material";
import { putUser } from '../services/AppUserService';
import { useAuth } from '../services/AuthProvider';

// function for editing user information. Opens a dialog with information from current user.
export default function EditUser({ token, setAlert }) {
  const { setToken } = useAuth();
  const decodedToken = jwtDecode(token);
  const { user_id, sub, first_name, last_name } = decodedToken;
  const [open, setOpen] = useState(false);
  const [user, setUser] = useState({
    id: '',
    username: '',
    first_name: '',
    last_name: '',
  });
  const [nameError, setNameError] = useState('');
  const [passError, setPassError] = useState('');

  // Fetching current user info and handling dialog
  const handleOpen = () => {
    setUser({
      id: user_id,
      username: sub,
      password_hash: '',
      first_name: first_name,
      last_name: last_name,
    })
    setOpen(true)
  }

  // Clearing the form and closing dialog
  const handleClose = () => {
    setNameError('');
    setPassError('');
    setOpen(false);
  }

  // Handling all changes
  const handleChange = (e) => {
    setUser({ ...user, [e.target.name]: e.target.value })
  }

  // Editing user info
  const editUser = async () => {
    // Checking that the fields are not empty
    if (user.username.trim() === "" || user.first_name.trim() === "" || user.last_name.trim() === "") {
      setNameError('Kenttä ei voi olla tyhjä');
    } else if (user.password_hash.length < 7) {
      setPassError(true)
    } else {
      try {
        putUser(token, user)
          .then(response => {
            if (response.ok) {
              // User edited successfully
              setToken(response.headers.get('Authorization'))
              setAlert('success')
              handleClose();
            } else {
              // Handle errors
              console.error('Failed to edit user');
              setAlert('error')
            }
          })
      } catch (error) {
        console.error('Error editing user:', error);
        setNameError('Error editing user');
      }
    }
  };


  // Returning a dialog, where a user can edit their own information
  return (
    <div>
      <Button style={{ marginTop: 20 }} variant="contained" onClick={handleOpen}>Muokkaa tietoja
      </Button>
      <Dialog
        open={open}
        onClose={handleClose}
        PaperProps={{
          component: 'form',
          onSubmit: (event) => {
            event.preventDefault();
            editUser();
          }
        }}
      >
        <DialogTitle>Muokkaa käyttäjätietoja</DialogTitle>
        <DialogContent>
          <TextField
            margin='normal'
            fullWidth
            required
            name="username"
            label="Käyttäjänimi"
            type="text"
            value={user.username}
            onChange={handleChange}
            error={nameError !== ''}
            helperText={nameError}
          />
          <TextField
            autoFocus
            required
            margin='normal'
            fullWidth
            name="first_name"
            label="Etunimi"
            type="text"
            value={user.first_name}
            onChange={handleChange}
            error={nameError !== ''}
            helperText={nameError}
          />
          <TextField
            autoFocus
            required
            margin='normal'
            fullWidth
            name="last_name"
            label="Sukunimi"
            type="text"
            value={user.last_name}
            onChange={handleChange}
            error={nameError !== ''}
            helperText={nameError}
          />
          <TextField
            required
            margin='normal'
            fullWidth
            name="password_hash"
            label="Salasana"
            type="password"
            autoComplete='password'
            value={user.password_hash}
            onChange={handleChange}
            error={passError !== ''}
            helperText="Vähimmäispituus 8 merkkiä"
          />
        </DialogContent>
        <DialogActions>
          <Button variant="contained" color='secondary' onClick={handleClose}>Peruuta</Button>
          <Button variant="contained" type='submit'>Tallenna</Button>
        </DialogActions>
      </Dialog>
    </div >
  )

}