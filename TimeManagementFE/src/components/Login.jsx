import { useState } from "react"
import { Box, TextField, Button } from '@mui/material'
import { useNavigate } from "react-router-dom"
import { useAuth } from "../services/AuthProvider"
import { baseUrl } from "../services/baseUrl"

const Login = () => {
  const { setToken } = useAuth()
  const navigate = useNavigate()
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')

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
  )
}

export default Login;