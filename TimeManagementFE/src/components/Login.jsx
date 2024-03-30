import { useState, useMemo } from "react"
import { Box, TextField, Button, Typography } from '@mui/material'
import { useNavigate } from "react-router-dom"
import { useAuth } from "../services/AuthProvider"
import { baseUrl } from "../services/baseUrl"
import AlertMessage from './AlertMessage';
import Register from "./Register"

const Login = () => {
  const { setToken } = useAuth()
  const navigate = useNavigate()
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [alert, setAlert] = useState(null)
  const alertMessage = useMemo(() => {
    switch (alert) {
      case 'success': {
        return <AlertMessage alert={alert} alertMessage="Käyttäjä rekisteröity onnistuneesti" setAlert={setAlert} />
      }
      case 'error': {
        return <AlertMessage alert={alert} alertMessage="Käyttäjän rekisteröinti epäonnistui" setAlert={setAlert} />
      }

      default: {
        return <></>
      }
    }
  }, [alert]);

  const fetchToken = () => {
    fetch(`${baseUrl}/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      },
      body: JSON.stringify({ username: username, password: password })
    })
      .then(response => {
        if (response.ok) {
          setToken(response.headers.get('Authorization'))
          navigate("/", { replace: true })
        } else {
          alert('Wrong username or password');
          throw new Error("Error in fetch:" + response.statusText)
        }
      })
      .catch(err => console.error(err))
  }

  const handleSubmit = (event) => {
    event.preventDefault()
    fetchToken()
  }

  return (
    <Box>
      {alertMessage}
      <Box component='form' onSubmit={handleSubmit} sx={{ margin: 2 }}>
        <TextField
          autoFocus
          required
          name="username"
          label="Username"
          id="username"
          autoComplete="username"
          value={username}
          onChange={e => setUsername(e.target.value)}
        />
        <TextField
          required
          name="password"
          label="Password"
          id="password"
          type="password"
          autoComplete="current-password"
          value={password}
          onChange={e => setPassword(e.target.value)}
        />
        <Button type="submit">
          Login
        </Button>
      </Box>
      <Box sx={{ margin: 2, marginTop: 8 }}>
        <Register setAlert={setAlert} />
      </Box>
    </Box>
  )
}

export default Login;