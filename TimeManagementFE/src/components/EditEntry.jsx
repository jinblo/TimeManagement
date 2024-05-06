import { Box, Button, Dialog, DialogActions, DialogContent, DialogTitle, FormControl, FormHelperText, InputLabel, MenuItem, OutlinedInput, Select, TextField } from "@mui/material";
import { DatePicker, TimePicker } from "@mui/x-date-pickers";
import dayjs from "dayjs";
import { useState } from "react";
import { putEntry } from "../services/EntryService";

// Editing entry. Moving an entry from one project to another is not currently possible.

const EditEntry = ({ token, oldEntry, setAlert, fetchEntries }) => {
  const emptyEntry = {
    entry_id: '',
    entry_date: '',
    start_time: '',
    end_time: '',
    comment: '',
    project: {
      id: '',
      title: ''
    }
  }
  const [entry, setEntry] = useState(emptyEntry)
  // Dialog state
  const [open, setOpen] = useState(false);
  const [errorMessageTime, setErrorMessageTime] = useState('');


  // Opening dialog with the details of the entry to be edited
  const handleClickOpen = () => {
    setEntry(oldEntry)
    setOpen(true)
  }

  // Closing the dialog and clearing the form
  const handleClose = () => {
    setEntry(emptyEntry);
    setErrorMessageTime('');
    setOpen(false);
  }

  // Saving the edited entry.
  const handleSave = () => {
    if (dayjs(`2024-01-01 ${entry.start_time}`).isBefore(dayjs(`2024-01-01 ${entry.end_time}`))) {
    
    putEntry(token, entry)
      .then(response => {
        if (response.ok) {
          fetchEntries()
          setAlert('success')
        } else {
          setAlert('error')
        }
      })
    handleClose()
  } else {
    setErrorMessageTime("Lopetusaika ei voi olla ennen aloitusaikaa.");
  }
};

  return (
    <div>
      <Button variant="contained" size='small' onClick={handleClickOpen}>Muokkaa</Button>
      <Dialog open={open} onClose={() => setOpen(false)}>
        <DialogTitle>Muokkaa työaikakirjausta</DialogTitle>
        <DialogContent>
          <Box component="form" sx={{ display: 'flex', flexWrap: 'wrap' }}>
            <FormControl sx={{ width: 550, marginTop: '3px' }}>
              <InputLabel htmlFor="project">Projekti</InputLabel>
              <Select
                disabled // Changing the project is not possible at the moment
                name="id"
                value={entry.project.id}
                onChange={e => handleChange(e)}
                input={<OutlinedInput label="Projekti" />}
              >
                <MenuItem key={entry.project.id} value={entry.project.id}>{entry.project.title}</MenuItem>
              </Select>
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
            name="start_time"
            label="Aloitusaika"
            value={dayjs(`2024-01-01 ${entry.start_time}`)}
            onChange={value => setEntry({ ...entry, start_time: value.format('HH:mm:ss') })}
          />
          <TimePicker
            sx={{ marginLeft: '5px', width: 270 }}
            name="end_time"
            label="Lopetusaika"
            value={dayjs(`2024-01-01 ${entry.end_time}`)}
            onChange={value => setEntry({ ...entry, end_time: value.format('HH:mm:ss') })}
            minTime={dayjs(`2024-01-01 ${entry.start_time}`)}
          />
                    <FormHelperText style={{color: 'red'}}>{errorMessageTime}</FormHelperText>

          <TextField
            fullWidth
            margin='dense'
            name="entry"
            label="Muistiinpanot"
            value={entry.comment}
            type="text"
            onChange={e => setEntry({ ...entry, comment: e.target.value })}
          />
        </DialogContent>
        <DialogActions>
          <Button variant="contained" color="secondary" onClick={handleClose}>Peruuta</Button>
          <Button variant="contained" color="primary" onClick={handleSave}>Tallenna</Button>
        </DialogActions>
      </Dialog>
    </div >
  )
}

export default EditEntry;