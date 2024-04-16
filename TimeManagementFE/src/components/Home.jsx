import { Box, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import { CardActionArea } from '@mui/material';
import { Link } from 'react-router-dom';
import Grid from '@mui/material/Grid';
import { useAuth } from '../services/AuthProvider';
import { getUser } from '../services/AppUserService';
import { jwtDecode } from "jwt-decode";

// Aloitussivu. Päivitetään vielä lopussa.
// Uupuu linkiki omiin tietoihin (sivua ei vielä ole)


const Home = () => {
  const { token } = useAuth();
  const decodedToken = jwtDecode(token);
  const { id, first_name, last_name } = decodedToken;

  console.log(decodedToken);

  return (
    <Box style={{ paddingLeft: 40, paddingRight: 40 }}>
      {first_name && last_name ? (
        <Typography variant="h6" style={{ marginTop: 30, marginBottom: 40 }}>Hei {first_name + " " + last_name}!</Typography>
      ) : (
        <Typography variant="h6" style={{ marginTop: 30, marginBottom: 40 }}>Hei!</Typography>
      )}
      <Grid container spacing={2}>
        <Grid item>
          <Card sx={{ maxWidth: 300 }}> {/* Projektit card */}
            <CardActionArea component={Link} to='projectlist'>
              <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                  Projektit
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Listaus kaikista projekteistasi. Lisää uusia projekteja sekä muokkaa niitä.
                </Typography>
              </CardContent>
            </CardActionArea>
          </Card>
        </Grid>
        <Grid item>
          <Card sx={{ maxWidth: 300 }}> {/* Tuntikirjaukset card */}
            <CardActionArea component={Link} to='entrylist'>
              <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                  Tuntikirjaukset
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Näe kaikki tuntikirjaukset samasta näkymästä. Lisää ja muokkaa kirjauksia.
                </Typography>
              </CardContent>
            </CardActionArea>
          </Card>
        </Grid>
        <Grid item>
          <Card sx={{ maxWidth: 300 }}> {/* Omat tiedot card */}
            <CardActionArea>
              <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                  Omat tiedot
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  (LINKKI UUPUU) Tarkastele ja muokkaa omia tietojasi.
                </Typography>
              </CardContent>
            </CardActionArea>
          </Card>
        </Grid>
        <Grid item>
          <Card sx={{ maxWidth: 300 }}> {/* Käyttäjien hallinta card */}
            <CardActionArea component={Link} to='projectlist'>
              <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                  Käyttäjien hallinta
                </Typography>
                <Typography variant="body2" color="text.secondary">
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