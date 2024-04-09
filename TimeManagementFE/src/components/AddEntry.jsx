import { Box, Button, Dialog, DialogActions, DialogContent, DialogTitle, FormControl, FormHelperText, InputLabel, MenuItem, OutlinedInput, Select, TextField } from "@mui/material";
import { DatePicker, TimePicker } from "@mui/x-date-pickers";
import dayjs from "dayjs";
import { useState } from "react";
import { postEntry } from "../services/EntryService";

// Lisätään uusi työaikakirjaus

const AddEntry = ({ token, projects, setAlert, fetchEntries }) => {
  const emptyEntry = {
    entry_date: dayjs().format('YYYY-MM-DD'),
    start_time: dayjs().format('HH:mm:ss'),
    end_time: dayjs().add(6, 'h').format('HH:mm:ss'),
    comment: ''
  }
  const [entry, setEntry] = useState(emptyEntry)
  const [project_id, setProject_id] = useState('')
  const [errorMessage, setErrorMessage] = useState('');
  // Handling dialog 
  const [open, setOpen] = useState(false);

  // Saving new entry
  const handleSave = () => {
    if (project_id) {
      postEntry(token, entry, project_id)
        .then(response => {
          if (response.ok) {
            fetchEntries()
            setAlert('success')
          } else {
            setAlert('error')
          }
        })
      handleClose();
    } else {
      setErrorMessage("Valitse projekti")
    }
  };

  // Clearing the form and closing dialog
  const handleClose = () => {
    setEntry(emptyEntry)
    setProject_id('');
    setOpen(false);
  }

  return (
    <div style={{ marginTop: 20 }}>
      <Button variant="contained" onClick={() => setOpen(true)} >Lisää uusi kirjaus</Button>
      <Dialog open={open} onClose={() => setOpen(false)}>
        <DialogTitle>Uusi työaikakirjaus</DialogTitle>
        <DialogContent>
          <Box component="form" sx={{ display: 'flex', flexWrap: 'wrap' }}>
            <FormControl
              sx={{ width: 550, marginTop: '3px' }}
              error={errorMessage !== ''}
            >
              <InputLabel htmlFor="project">Projekti</InputLabel>
              <Select
                name="project"
                value={project_id}
                onChange={e => {
                  setProject_id(e.target.value)
                  setErrorMessage('')
                }}
                input={<OutlinedInput label="Projekti" />}
              >
                {projects ? projects.map(item => {
                  return <MenuItem key={item.project.id} value={item.project.id}>{item.project.title}</MenuItem>
                })
                  : null}
              </Select>
              <FormHelperText>{errorMessage}</FormHelperText>
            </FormControl>
          </Box>
          <DatePicker
            sx={{ width: 550, marginTop: 1, marginBottom: 1 }}
            label="Päivämäärä"
            name="entry_date"
            value={dayjs(entry.entry_date)}
            onChange={value => setEntry({ ...entry, entry_date: value.format('YYYY-MM-DD') })}
          />
          <TimePicker
            sx={{ marginRight: '5px', width: 270 }}
            label="Aloitusaika"
            name="start_time"
            value={dayjs(`2024-01-01 ${entry.start_time}`)}
            onChange={value => setEntry({ ...entry, start_time: value.format('HH:mm:ss') })}
          />
          <TimePicker
            sx={{ marginLeft: '5px', width: 270 }}
            label="Lopetusaika"
            name="end_time"
            value={dayjs(`2024-01-01 ${entry.end_time}`)}
            onChange={value => setEntry({ ...entry, end_time: value.format('HH:mm:ss') })}
            minTime={dayjs(`2024-01-01 ${entry.start_time}`)}
          />
          <TextField
            fullWidth
            autoFocus
            margin='dense'
            name="comment"
            label="Muistiinpanot"
            type="text"
            onChange={e => setEntry({ ...entry, comment: e.target.value })}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Peruuta</Button>
          <Button onClick={handleSave}>Tallenna</Button>
        </DialogActions>
      </Dialog>
    </div >
  )
}

export default AddEntry;