import React, { useState } from 'react';
import { Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField } from "@mui/material";
import { postUser } from '../services/AppUserService';

// Uuden käyttäjän rekisteröitymine palveluun

const Register = ({ setAlert }) => {
  const newUser = {
    username: '',
    password_hash: '',
    first_name: '',
    last_name: '',
  }
  const [user, setUser] = useState(newUser);
  const [error, setError] = useState(false);

  // Handling dialog 
  const [open, setOpen] = useState(false);

  // handling all changes
  const handleChange = (e) => {
    setUser({ ...user, [e.target.name]: e.target.value })
  }

  // adding new project to the database
  const addUser = () => {
    if (user.password_hash.length < 7) {
      setError(true)
    } else {
      try {
        postUser(user)
          .then(response => {
            if (response.ok) {
              // User added successfully
              console.log('User added successfully');
              setAlert('success')
            } else {
              // Handle errors
              console.error('Failed to add user');
              setAlert('error')
            }
          })
        handleClose()
      } catch (error) {
        console.error('Error adding new user:', error);
      }
    }
  };

  // Clearing the form and closing dialog
  const handleClose = () => {
    setUser(newUser);
    setError(false);
    setOpen(false);
  }

  return (
    <div style={{ marginTop: 20 }}>
      <Button variant="contained" onClick={() => setOpen(true)} >Rekisteröidy</Button>
      <Dialog
        open={open}
        onClose={handleClose}
        PaperProps={{
          component: 'form',
          onSubmit: (event) => {
            event.preventDefault();
            addUser();
          }
        }}
      >
        <DialogTitle>Reksiteröidy</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            required
            margin='normal'
            fullWidth
            name="username"
            label="Käyttäjänimi"
            type="text"
            onChange={e => handleChange(e)}
          />
          <TextField
            required
            margin='normal'
            fullWidth
            name="password_hash"
            label="Salasana"
            type="password"
            autoComplete='password'
            onChange={e => handleChange(e)}
            error={error}
            helperText="Vähimmäispituus 8 merkkiä"
          />
          <TextField
            required
            margin='normal'
            fullWidth
            name="first_name"
            label="Etunimi"
            type="text"
            onChange={e => handleChange(e)}
          />
          <TextField
            required
            margin='normal'
            fullWidth
            name="last_name"
            label="Sukunimi"
            type="text"
            onChange={e => handleChange(e)}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Peruuta</Button>
          <Button type="submit">Tallenna</Button>
        </DialogActions>
      </Dialog>
    </div >
  )
}

export default Register;
