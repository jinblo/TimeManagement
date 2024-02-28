import { Box, Button, Dialog, DialogActions, DialogContent, DialogTitle, FormControl, InputLabel, MenuItem, OutlinedInput, Select, TextField } from "@mui/material";
import { DatePicker, TimePicker } from "@mui/x-date-pickers";
import dayjs from "dayjs";
import { useState } from "react";

// Työaikakirjauksen muokkaaminen. Kirjauksen siirtäminen projektilta toiselle ei ole nyt mahdollista.

const EditEntry = ({ oldEntry, saveEntry, projects }) => {
  const [entry, setEntry] = useState({
    entry_id: '',
    entry_date: '',
    start_time: '',
    end_time: '',
    comment: '',
    project: {
      id: '',
      title: ''
    }
  })

  // State for dialog
  const [open, setOpen] = useState(false);

  // Opening dialog with entry data
  const handleClickOpen = () => {
    setEntry(oldEntry)
    setOpen(true)
  }

  // Clearing the form and closing dialog
  const handleClose = () => {
    setEntry({
      entry_id: '',
      entry_date: '',
      start_time: '',
      end_time: '',
      comment: '',
      project: {
        id: '',
        title: ''
      }
    })
    setOpen(false);
  }

  // Handling changes 
  const handleChange = e => {
    let data = { ...entry }
    let name = e.target.name
    let value = e.target.value
    if (name == 'id') {
      data = {
        ...data,
        project: {
          [name]: value
        }
      }
    } else {
      data = {
        ...data,
        [name]: value
      }
    }
    setEntry(data)
  }

  // Saving edited entry
  const handleSave = () => {
    const href = `http://localhost:8080/projects/${entry.project.id}/entries/${entry.entry_id}`
    const options = {
      method: 'put',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(entry)
    }
    saveEntry(href, options);
    handleClose();
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
                disabled // Projektin muuttaminen ei ole nyt mahdollista
                name="id"
                value={entry.project.id}
                onChange={e => handleChange(e)}
                input={<OutlinedInput label="Projekti" />}
              >
                <MenuItem key={entry.project.id} value={entry.project.id}>{entry.project.title}</MenuItem>
                { /* List projects for select, current backend does NOT support
                projects ? projects.map(project => {
                  return <MenuItem key={project.id} value={project.id}>{project.title}</MenuItem>
                })
              : null */ }
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
          <TextField
            fullWidth
            margin='dense'
            name="entry"
            label="Muistiinpanot"
            value={entry.comment}
            type="text"
            onChange={e => setEntry({ ...entry, [e.target.name]: e.target.value })}
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

export default EditEntry;