import { Box, Typography } from '@mui/material';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import { CardActionArea } from '@mui/material';
import { Link } from 'react-router-dom';
import Grid from '@mui/material/Grid';
import { useAuth } from '../services/AuthProvider';
import { jwtDecode } from "jwt-decode";


const Home = () => {
  const { token } = useAuth();
  const decodedToken = jwtDecode(token);
  const { first_name, last_name } = decodedToken;

  
  return (
    <Box style={{ paddingLeft: 40, paddingRight: 40 }}>
      {first_name && last_name ? (
        <Typography variant="h6" style={{ marginTop: 30, marginBottom: 40 }}>Hei {first_name + " " + last_name}!</Typography>
      ) : (
        <Typography variant="h6" style={{ marginTop: 30, marginBottom: 40 }}>Hei!</Typography>
      )}
      <Grid container spacing={2}>
        <Grid item>
          <Card sx={{ width: 300 }}> {/* Projektit card */}
            <CardActionArea component={Link} to='projectlist'>
              <CardContent sx={{ "&:hover": { backgroundColor: "#A9B8C3" } }}>
                <Typography gutterBottom variant="h5" component="div">
                  Projektit
                </Typography>
                <Typography variant="body2">
                  Listaus kaikista projekteistasi. Lisää uusia projekteja sekä muokkaa niitä.
                </Typography>
              </CardContent>
            </CardActionArea>
          </Card>
        </Grid>
        <Grid item>
          <Card sx={{ width: 300 }}> {/* Tuntikirjaukset card */}
            <CardActionArea component={Link} to='entrylist'>
              <CardContent sx={{ "&:hover": { backgroundColor: "#A9B8C3" } }}>
                <Typography gutterBottom variant="h5" component="div">
                  Tuntikirjaukset
                </Typography>
                <Typography variant="body2">
                  Näe kaikki tuntikirjaukset samasta näkymästä. Lisää ja muokkaa kirjauksia.
                </Typography>
              </CardContent>
            </CardActionArea>
          </Card>
        </Grid>
        <Grid item>
          <Card sx={{ width: 300 }}> {/* Omat tiedot card */}
            <CardActionArea component={Link} to='user'>
              <CardContent sx={{ "&:hover": { backgroundColor: "#A9B8C3" } }}>
                <Typography gutterBottom variant="h5" component="div">
                  Omat tiedot
                </Typography>
                <Typography variant="body2">
                  Tarkastele ja muokkaa omia tietojasi. Muista pitää tietosi ajan tasalla.
                </Typography>
              </CardContent>
            </CardActionArea>
          </Card>
        </Grid>
        <Grid item>
          <Card sx={{ width: 300 }}> {/* Käyttäjien hallinta card */}
            <CardActionArea component={Link} to='projectlist'>
              <CardContent sx={{ "&:hover": { backgroundColor: "#A9B8C3" } }}>
                <Typography gutterBottom variant="h5" component="div">
                  Käyttäjien hallinta
                </Typography>
                <Typography variant="body2">
                  Hallitse projektien käyttäjiä kyseisen projektin muokkausnäkymästä.
                </Typography>
              </CardContent>
            </CardActionArea>
          </Card>
        </Grid>
      </Grid>
    </Box>
  )
};

export default Home;