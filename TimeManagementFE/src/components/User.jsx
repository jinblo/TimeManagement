import { jwtDecode } from "jwt-decode";
import { Box, Typography, Card, CardContent } from '@mui/material';
import { useAuth } from "../services/AuthProvider";
import { useEffect, useMemo, useState } from "react";
import EditUser from "./EditUser";
import AlertMessage from "./AlertMessage";


const User = () => {
  const { token } = useAuth();
  const decodedToken = jwtDecode(token);
  const { sub, first_name, last_name } = decodedToken;

  const [alert, setAlert] = useState(null)
  const alertMessage = useMemo(() => {
    switch (alert) {
      case 'success': {
        return <AlertMessage alert={alert} alertMessage="Tiedot tallennettu onnistuneesti" setAlert={setAlert} />
      }
      case 'error': {
        return <AlertMessage alert={alert} alertMessage="Tallennus epäonnistui" setAlert={setAlert} />
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
            <Typography gutterBottom variant="subtitle1" component="div">
              <Typography component="span" fontWeight="bold">Käyttäjänimi: </Typography>
              <Typography component="span">{sub}</Typography>
            </Typography>
            <Typography gutterBottom variant="subtitle1" component="div">
              <Typography component="span" fontWeight="bold"> Etunimi: </Typography>
              <Typography component="span">{first_name}</Typography>
            </Typography>
            <Typography gutterBottom variant="subtitle1" component="div">
              <Typography component="span" fontWeight="bold">Sukunimi: </Typography>
              <Typography component="span">{last_name}</Typography>
            </Typography>
          </CardContent>
        </Card>
        <EditUser token={token} setAlert={setAlert} />
      </Box>
    </div>
  )
}

export default User;