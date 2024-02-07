import { Box, Button, Dialog, DialogActions, DialogContent, DialogTitle, FormControl, InputLabel, MenuItem, OutlinedInput, Select, TextField } from "@mui/material";
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
    entry_date: '',
    start_time: '',
    end_time: '',
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
                id="project"
                value={entry.project}
                onChange={handleChange}
                input={<OutlinedInput label="Projekti" />}
              >
                {projects ? projects.map(project => {
                  <MenuItem key={project.id} value={project.title} >{project.title}</MenuItem>
                }) : null}
              </Select>
            </FormControl>
          </Box>
          <TextField
            autoFocus
            margin='dense'
            fullWidth
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