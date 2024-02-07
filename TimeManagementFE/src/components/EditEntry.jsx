import { Box, Button, Dialog, DialogActions, DialogContent, DialogTitle, FormControl, InputLabel, MenuItem, OutlinedInput, Select, TextField } from "@mui/material";
import { DatePicker, TimePicker } from "@mui/x-date-pickers";
import dayjs from "dayjs";
import { useEffect, useState } from "react";

// Työaikakirjauksen muokkaaminen

const EditEntry = ({ oldEntry, saveEntry }) => {
  const [entry, setEntry] = useState({
    entry_id: '',
    entry_title: '',
    entry_date: '',
    start_time: '',
    end_time: '',
    entry: '',
    project: {
      id: '',
      title: ''
    }
  })
  const [projects, setProjects] = useState()
  const [open, setOpen] = useState(false);

  const handleClickOpen = () => {
    setEntry(oldEntry)
    setOpen(true)
  }

  // Fetching projects for select
  useEffect(() => {
    fetch('http://localhost:8080/projects')
      .then(response => response.json())
      .then(data => setProjects(data))
      .catch(error => console.error(error))
  }, [])

  const handleChange = event => {
    setEntry({ ...entry, [event.target.name]: event.target.value })
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
    setOpen(false);
  };

  return (
    <div>
      <Button variant="contained" size='small' onClick={handleClickOpen}>Muokkaa</Button>
      <Dialog open={open} onClose={() => setOpen(false)}>
        <DialogTitle>Muokkaa työaikakirjausta</DialogTitle>
        <DialogContent>
          <TextField
            fullWidth
            autoFocus
            margin='dense'
            name="entry_title"
            label="Otsikko"
            value={entry.entry_title}
            type="text"
            onChange={event => handleChange(event)}
          />
          <Box component="form" sx={{ display: 'flex', flexWrap: 'wrap' }}>
            <FormControl sx={{ width: 550, marginTop: '3px' }}>
              <InputLabel htmlFor="project">Projekti</InputLabel>
              <Select
                name="id"
                value={entry.project.id}
                onChange={event => setEntry({ ...entry, [entry.project.id]: event.target.value })}
                input={<OutlinedInput label="Projekti" />}
              >
                {projects ? projects.map(project => {
                  return <MenuItem key={project.id} value={project.id}>{project.title}</MenuItem>
                })
                  : null}
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
            value={dayjs(`2024-01-01 ${entry.end_time}`).add(1, 'h')}
            onChange={value => setEntry({ ...entry, end_time: value.format('HH:mm:ss') })}
          />
          <TextField
            fullWidth
            autoFocus
            margin='dense'
            name="entry"
            label="Muistiinpanot"
            value={entry.entry}
            type="text"
            onChange={event => handleChange(event)}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpen(false)}>Peruuta</Button>
          <Button onClick={handleSave}>Tallenna</Button>
        </DialogActions>
      </Dialog>
    </div >
  )
}

export default EditEntry;