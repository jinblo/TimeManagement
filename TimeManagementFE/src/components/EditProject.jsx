import React, { useState, useEffect } from 'react';
import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    TextField,
    Typography,
    Table,
    TableHead,
    TableBody,
    TableRow,
    TableCell,
    Checkbox,
    Select,
    MenuItem
} from "@mui/material";
import { putProject } from '../services/ProjectService';
import { getUser } from '../services/AppUserService';


// Function for editing project information. Opens a dialog with information from chosen project.
// User can add, edit and delete users and their roles for chosen project
export default function EditProject({ token, editData, setAlert, fetchProjects }) {

    const [project, setProject] = useState({
        title: editData.title || "",
        id: editData.id || "",
        roles: editData.roles || "",
    });

    // Project's users
    const [addedUsers, setAddedUsers] = useState([]);

    // user's username input which is used to find the user
    const [username, setUsername] = useState('');

    // Error message shown to user
    const [errorMessage, setErrorMessage] = useState('');

    // Error message shown if title is empty
    const [errorMessageTitle, setErrorMessageTitle] = useState('');

    // Error message shown when seraching a username
    const [errorMessageUser, setErrorMessageUser] = useState('');

    // Error message shown if user does not have a role
    const [errorMessageUserRole, setErrorMessageUserRole] = useState('');

    // Handling dialog 
    const [open, setOpen] = useState(false);

    const handleOpen = () => {
        setProject({
            title: editData.title,
            id: editData.id,
            roles: editData.roles,
        })
        setAddedUsers(editData.roles)
        setOpen(true)
    }

    // Handling all changes
    const handleChange = (e) => {
        setProject({ ...project, [e.target.name]: e.target.value })
    }
    // Handling the change of the username of user input field
    const handleChangeUsername = (e) => {
        setUsername(e.target.value)
    }

    // Fetching the username
    const fetchUsername = () => {
        getUser(token, username)
            .then(data => {
                // checking if the user already exist in the project
                const index = addedUsers.findIndex(u => u.appUser.id === data.id);
                if (index !== -1) {
                    setErrorMessageUser("Käyttäjä on jo lisätty projektiin.");
                } else {
                    setAddedUsers([...addedUsers, {
                        appUser:
                        {
                            id: data.id,
                            first_name: data.first_name,
                            last_name: data.last_name,
                            username: data.username
                        },
                        role: 'USER',

                    }]);
                    setErrorMessageUser('');
                    setUsername('');
                }
            })
            .catch(error => {
                setErrorMessageUser("Käyttäjätunnusta ei onnistuttu hakemaan.");
                console.error(error);
            });
    };

    useEffect(() => {
        let newTable = [...addedUsers];
        const index = addedUsers.findIndex(user => user.role === "")
        if (index != -1) {
            newTable[index] = { ...newTable[index], role: null };
        }
        setProject({ ...project, roles: newTable })
    }, [addedUsers]);



    // Editing a project info and user roles
    const editProject = async () => {
        // Checking that all users have a role
        const allUsersHaveRoles = project.roles.every(user => user.role === 'OWNER' || user.role === 'USER' || user.role === 'VIEWER' || user.role === null);
        if (!allUsersHaveRoles) {
            setErrorMessageUserRole('Jokaisella käyttäjällä pitää olla rooli valittuna.');
            return;
        }
        // Ensure that at least one user is an OWNER of the project
        const hasOwner = addedUsers.some(user => user.role === 'OWNER');
        if (!hasOwner) {
            setErrorMessageUserRole('Ainakin yhden käyttäjän täytyy olla Owner.');
            return;
        }
        // Checking that the title is not empty
        if (project.title.trim() === "") {
            setErrorMessageTitle('Nimi ei voi olla tyhjä');
        } else {
            try {
                putProject(token, project)
                    .then(response => {
                        if (response.ok) {
                            // Project edited successfully
                            console.log('Project edited successfully');
                            fetchProjects();
                            setAlert('success')
                            handleClose();
                        } else {
                            // Handle errors
                            console.error('Failed to edit project');
                            setErrorMessage('Projektin muokkaus ei onnistunut');
                            setAlert('error')
                        }
                    })
            } catch (error) {
                console.error('Error editing project:', error);
                setErrorMessage('Jonkin meni pieleen');
            }
        }
    };

    // Closing dialog, reseting the form
    const handleClose = () => {
        setProject({ title: editData.title });
        setAddedUsers([]);
        setErrorMessage('');
        setErrorMessageTitle('');
        setErrorMessageUser('');
        setErrorMessageUserRole('');
        setOpen(false);
    }


    // Returning a dialog, where a user can edit project information
    return (
        <div>
            <Button variant="contained" size='small' onClick={handleOpen}>Muokkaa
            </Button>
            <Dialog open={open} onClose={() => setOpen(false)}>
                <DialogTitle>Muokkaa projektia ja käyttöoikeuksia</DialogTitle>
                <DialogContent>
                    <Typography style={{ fontSize: '16px', marginTop: 20 }}>Muokkaa projektin otsikkoa</Typography>
                    <TextField
                        autoFocus
                        required
                        margin='normal'
                        fullWidth
                        name="title"
                        label="Projektin nimi"
                        type="text"
                        value={project.title}
                        onChange={handleChange}
                        error={errorMessageTitle !== ''}
                        helperText={errorMessageTitle}
                    />

                    <Typography style={{ fontSize: '16px', marginTop: 20 }}>Lisää käyttäjiä projektille</Typography>
                    <TextField
                        autoFocus
                        required
                        margin='normal'
                        fullWidth
                        name="username"
                        label="Hae käyttäjänimellä"
                        type="text"
                        onChange={handleChangeUsername}
                        error={errorMessageUser !== ''}
                        helperText={errorMessageUser}
                    />
                    <Button variant="contained" style={{ marginTop: 10 }} onClick={fetchUsername}>Hae käyttäjä</Button>
                    <Typography style={{ fontSize: '16px', marginTop: 20, marginBottom: 10 }}>Projektille lisättävät/lisätyt käyttäjät</Typography>
                    {addedUsers.length > 1 ? (
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell>Käyttäjänimi</TableCell>
                                    <TableCell>Käyttäjän rooli</TableCell>
                                    <TableCell>Poista projektista</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {addedUsers
                                    .sort((a, b) => {
                                        // Sorting the list, owners first, then users, then viewers
                                        const roleOrder = {
                                            'OWNER': 0,
                                            'USER': 1,
                                            'VIEWER': 2,
                                        };
                                        return roleOrder[a.role] - roleOrder[b.role];
                                    })
                                    .map((user, index) => (
                                        <TableRow key={user.appUser.id}>
                                            <TableCell>{user.appUser.username}</TableCell>
                                            <TableCell>
                                                <Select
                                                    value={user.role}
                                                    onChange={e => {
                                                        let newTable = [...addedUsers]
                                                        newTable[index] = { ...user, role: e.target.value }
                                                        setAddedUsers(newTable)
                                                    }}
                                                    displayEmpty
                                                >
                                                    <MenuItem value="NON">Valitse</MenuItem>
                                                    <MenuItem value="USER">User</MenuItem>
                                                    <MenuItem value="VIEWER">Viewer</MenuItem>
                                                    <MenuItem value="OWNER">Owner</MenuItem>
                                                    <MenuItem value="">Poista</MenuItem>
                                                </Select>
                                            </TableCell>
                                            <TableCell>
                                                <Checkbox
                                                    color="primary"
                                                    checked={user.role === ""}
                                                    onChange={e => {
                                                        let newTable = [...addedUsers];
                                                        newTable[index] = { ...user, role: e.target.checked ? "" : "NON" };
                                                        setAddedUsers(newTable);
                                                    }}
                                                />
                                            </TableCell>
                                        </TableRow>
                                    ))}
                            </TableBody>
                        </Table>
                    ) : (
                        <Typography style={{ fontSize: '12px', marginTop: 10, fontStyle: 'italic', color: 'grey' }}>Projektille ei ole vielä lisätty muita käyttäjiä</Typography>
                    )}
                    <Typography style={{ fontSize: '14px' }} color="error">{errorMessageUserRole}</Typography>
                    <Typography style={{ fontSize: '14px' }} color="error">{errorMessage}</Typography>
                    <Typography style={{ fontSize: '12px', marginTop: 20 }}>Muokatut tiedot tallennetaan vasta kun painat tallenna kaikki muutokset -nappia</Typography>
                    <DialogActions style={{ justifyContent: "center", marginTop: 10, marginBottom: 10 }}>
                        <Button variant="contained" color="secondary" onClick={handleClose}>Peruuta</Button>
                        <Button variant="contained" onClick={editProject}>Tallenna kaikki muutokset</Button>
                    </DialogActions>
                </DialogContent>
            </Dialog>
        </div >
    )

}