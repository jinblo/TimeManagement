import { jwtDecode } from "jwt-decode";
import { Box, Typography, Card, CardContent } from '@mui/material';
import { useAuth } from "../services/AuthProvider";
import { useEffect, useMemo, useState } from "react";
import EditUser from "./EditUser";
import AlertMessage from "./AlertMessage";


const User = () => {
  const { token } = useAuth();
  const decodedToken = jwtDecode(token);
  const { id, sub, first_name, last_name } = decodedToken;

  const [alert, setAlert] = useState(null)
  const alertMessage = useMemo(() => {
    switch (alert) {
      case 'success': {
        return <AlertMessage alert={alert} alertMessage="Kirjaus tallennettu onnistuneesti" setAlert={setAlert} />
      }
      case 'info': {
        return <AlertMessage alert={alert} alertMessage="Kirjaus poistettu onnistuneesti" setAlert={setAlert} />
      }
      case 'error': {
        return <AlertMessage alert={alert} alertMessage="Kirjauksen tallennus epäonnistui" setAlert={setAlert} />
      }

      default: {
        return <></>
      }
    }
  }, [alert]);


  return (
    <div>
      {alertMessage}
      <Box style={{ padding: 40 }}>
        <Typography gutterBottom variant="h5" component="div">
          Käyttäjätiedot
        </Typography>
        <Card sx={{ width: 500 }}>
          <CardContent>
            <Typography variant="subtitle1" >
              <Typography sx={{ fontWeight: 'bold' }}>
                käyttäjänimi:
              </Typography>
              {sub}
              <Typography sx={{ fontWeight: 'bold' }}>
                etunimi:
              </Typography>
              {first_name}
              <Typography sx={{ fontWeight: 'bold' }}>
                sukunimi:
              </Typography>
              {last_name}
            </Typography>
          </CardContent>
        </Card>
        <EditUser token={token} setAlert={setAlert} />
      </Box>
    </div>
  )
}

export default User;