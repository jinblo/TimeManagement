import { useState } from 'react';
import { Box, AppBar, Tabs, Tab, Toolbar, } from '@mui/material';
import { Link, Navigate, Outlet } from "react-router-dom";
import { useParams } from 'react-router-dom';
import { useAuth } from '../services/AuthProvider';

const TabView = () => {
  const { token } = useAuth()
  let { id } = useParams();
  const [tab, setTab] = useState(0);
  const [log, setLog] = useState(1);

  const handleTabChange = (e, val) => {
    setTab(val);
  }

  const handleLogChange = (e, val) => {
    setLog(val)
  }

  return (
    <Box>
      <AppBar position='static' sx={{ flexGrow: 1 }}>
        <Toolbar>
          <div style={{ flexGrow: 1 }}>
            {token ?
              <Tabs value={tab} onChange={handleTabChange}
                textColor='inherit' >
                <Tab component={Link} to={''} label='Etusivu' />
                <Tab component={Link} to={'projectlist'} label='Projektit' />
                <Tab component={Link} to={'entrylist'} label='Tuntikirjaukset' />
              </Tabs>
              : null
            }
          </div>
          {token ?
          <Tabs value={log} onChange={handleLogChange}
            textColor='inherit'>
              <Tab component={Link} to={'user'} label='Omat tiedot' />
              <Tab component={Link} to={'logout'} label='Kirjaudu ulos' />
            </Tabs>
              :
              <Tab component={Link} to={'login'} label='Kirjaudu sisään' />
          }
        </Toolbar>
      </AppBar>
      <Outlet />
    </Box>
  )
}

export default TabView;
