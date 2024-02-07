import { Box, Button, Dialog, DialogActions, DialogContent, DialogTitle, FormControl, InputLabel, MenuItem, OutlinedInput, Select, TextField } from "@mui/material";
import { DatePicker, TimePicker } from "@mui/x-date-pickers";
import dayjs from "dayjs";
import { useEffect, useState } from "react";

const AddEntry = () => {
  const [projects, setProjects] = useState()

  useEffect(() => {
    fetch('http://localhost:8080/projects')
      .then(response => response.json())
      .then(data => setProjects(data))
      .catch(error => console.error(error))
  }, [])

  const [entry, setEntry] = useState({
    project: '',
    entry_date: dayjs().format('YYYY-MM-DD'),
    start_time: dayjs().format('HH:mm:ss'),
    end_time: dayjs().format('HH:mm:ss'),
    entry: ''
  })

  const handleChange = event => {
    setEntry({ ...entry, [event.target.name]: event.target.value })
  }

  const [open, setOpen] = useState(false);

  return (
    <div style={{ float: 'right', margin: 20 }}>
      <Button variant="contained" onClick={() => setOpen(true)} >Lisää uusi kirjaus</Button>
      <Dialog open={open} onClose={() => setOpen(false)}>
        <DialogTitle>Uusi työaikakirjaus</DialogTitle>
        <DialogContent>
          <Box component="form" sx={{ display: 'flex', flexWrap: 'wrap' }}>
            <FormControl sx={{ m: 1, minWidth: 120 }}>
              <InputLabel htmlFor="project">Projekti</InputLabel>
              <Select
                name="project"
                value={entry.project}
                onChange={handleChange}
                input={<OutlinedInput label="Projekti" />}
              >
                {projects ? projects.map(project => {
                  return <MenuItem key={project.id}>{project.title}</MenuItem>
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
            label="Aloitusaika"
            name="start_time"
            value={dayjs(`2024-01-01 ${entry.start_time}`)}
            onChange={value => setEntry({ ...entry, start_time: value.format('HH:mm:ss') })}
          />
          <TimePicker
            sx={{ marginLeft: '5px', width: 270 }}
            label="Lopetusaika"
            name="end_time"
            value={dayjs(`2024-01-01 ${entry.end_time}`).add(1, 'h')}
            onChange={value => setEntry({ ...entry, end_time: value.format('HH:mm:ss') })}
          />
          <TextField
            fullWidth
            autoFocus
            margin='dense'
            name="entry"
            label="Muistiinpanot"
            type="text"
            onChange={event => handleChange(event)}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpen(false)}>Peruuta</Button>
          <Button onClick={() => console.log(entry)}>Tallenna</Button>
        </DialogActions>
      </Dialog>
    </div >
  )
}

export default AddEntry;