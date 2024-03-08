import { useState } from 'react';
import { Box, AppBar, Tabs, Tab, Button, Toolbar, } from '@mui/material';
import { Link, Navigate, Outlet } from "react-router-dom";
import { useParams } from 'react-router-dom';
import { useAuth } from '../services/AuthProvider';
import Logout from './Logout';

const TabView = () => {
  const { token } = useAuth()
  let { id } = useParams();
  const [value, setValue] = useState(0);

  const handleChange = (e, val) => {
    setValue(val);
  }

  return (
    <Box>
      <AppBar position='static'>
        <Toolbar>
          <Tabs value={value} onChange={handleChange}
            textColor='inherit'>
            <Tab component={Link} to={`/`} label='Etusivu' />
            <Tab component={Link} to={'projectlist'} label='Projektit' />
            <Tab component={Link} to={'entrylist'} label='Tuntikirjaukset' />
            <Tab component={Link} to={'login'} label='Login' />
            <Tab component={Link} to={'logout'} label='Logout' />
          </Tabs>
        </Toolbar>
      </AppBar>
      <Outlet />
    </Box>
  )
}

export default TabView;
