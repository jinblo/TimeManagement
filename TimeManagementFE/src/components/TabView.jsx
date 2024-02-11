import { useState } from 'react';
import { Box, AppBar, Tabs, Tab, } from '@mui/material';
import { Link, Outlet } from "react-router-dom";
import { useParams } from 'react-router-dom';

const TabView = () => {
  let { id } = useParams();
  const [value, setValue] = useState(0);

  const handleChange = (e, val) => {
    setValue(val);
  }

  return (
    <Box>
      <AppBar position='static'>
        <Tabs value={value} onChange={handleChange}
          textColor='inherit'>
          <Tab component={Link} to={`/`} label='Etusivu' />
          <Tab component={Link} to={'projectlist'} label='Projektit' />
          <Tab component={Link} to={'entrylist'} label='Tuntikirjaukset' />
        </Tabs>
      </AppBar>
      <Outlet />
    </Box>
  )
}

export default TabView;