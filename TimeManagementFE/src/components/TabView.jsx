import { useState } from 'react';
import { Box, AppBar, Tabs, Tab, Toolbar, IconButton, Menu, MenuItem, Typography, Button, Container, } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import PersonIcon from '@mui/icons-material/Person';
import { Link, Navigate, Outlet, useNavigate } from "react-router-dom";
import { useAuth } from '../services/AuthProvider';

const pages = ['Etusivu', 'Projektit', 'Tuntikirjaukset'];
const settings = ['Omat tiedot', 'Kirjaudu ulos']

const TabView = () => {
  const { token } = useAuth();
  const [anchorPage, setAnchorPage] = useState(null);
  const [anchorSetting, setAnchorSetting] = useState(null);
  const navigate = useNavigate();

  const handleOpenPageMenu = e => {
    setAnchorPage(e.target);
  }

  const handleClosePageMenu = (e) => {
    let page = ''
    switch (e.target.title) {
      case 'Projektit':
        page = 'projectlist'
        break;
      case 'Tuntikirjaukset':
        page = 'entrylist'
        break;
      default:
        page = ""
        break;
    }
    navigate(page)
    setAnchorPage(null);
  }

  const handleOpenSettingMenu = e => {
    setAnchorSetting(e.target);
  }

  const handleCloseSettingMenu = e => {
    let page = ''
    switch (e.target.title) {
      case 'Omat tiedot':
        page = 'user'
        break;
      case 'Kirjaudu ulos':
        page = 'logout'
        break;
      case 'Kirjaudu sisään':
        page = 'login'
        break;
      default:
        page = ""
        break;
    }
    navigate(page)
    setAnchorSetting(null)
  }

  return (
    <Box>
      <AppBar position='static' sx={{ flexGrow: 1 }}>
        <Toolbar>
            {token ?
            <>
              <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}>
                <IconButton
                  size='large'
                  onClick={handleOpenPageMenu}
                >
                  <MenuIcon />
                </IconButton>
                <Menu
                  anchorEl={anchorPage}
                  anchorOrigin={{ vertical: 'bottom', horizontal: 'left' }}
                  keepMounted
                  transformOrigin={{ vertical: 'top', horizontal: 'left' }}
                  open={Boolean(anchorPage)}
                  onClose={handleClosePageMenu}
                  sx={{ display: { xs: 'block', md: 'none' } }}
                >
                  {pages.map(page => (
                    <MenuItem key={page} onClick={handleClosePageMenu}>
                      <Typography textAlign='center' title={page}>{page}</Typography>
                    </MenuItem>
                  ))}
                </Menu>
              </Box>
              <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }}>
                {pages.map(page => (
                  <Button
                    key={page}
                    onClick={handleClosePageMenu}
                    title={page}
                    sx={{ my: 2, color: 'white', display: 'block' }}
                  >
                    {page}
                  </Button>
                ))}
              </Box>
              <Box sx={{ flexGrow: 0, display: { xs: 'flex', md: 'none' } }}>
                <IconButton
                  size='large'
                  onClick={handleOpenSettingMenu}
                >
                  <PersonIcon />
                </IconButton>
                <Menu
                  sx={{ mt: '45px' }}
                  anchorEl={anchorSetting}
                  anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
                  keepMounted
                  transformOrigin={{ vertical: 'top', horizontal: 'right' }}
                  open={Boolean(anchorSetting)}
                  onClose={handleCloseSettingMenu}
                >
                  {settings.map(setting => (
                    <MenuItem key={setting} onClick={handleCloseSettingMenu}>
                      <Typography textAlign='center' title={setting}>{setting}</Typography>
                    </MenuItem>
                  ))}
                </Menu>
              </Box>
              <Box sx={{ flexGrow: 0, display: { xs: 'none', md: 'flex' } }}>
                {settings.map(setting => (
                  <Button
                    key={setting}
                    onClick={handleCloseSettingMenu}
                    title={setting}
                    sx={{ my: 2, color: 'white', display: 'block' }}
                  >
                    {setting}
                  </Button>
                ))}
              </Box>
            </>
              :
            <>
              <Box sx={{ flexGrow: 1 }}></Box>
              <Box sx={{ flexGrow: 0 }}>
                <Button
                  onClick={handleCloseSettingMenu}
                  title="Kirjaudu sisään"
                  sx={{ my: 2, color: 'white', display: 'block' }}
                >
                  Kirjaudu sisään
                </Button>
              </Box>
            </>
          }
        </Toolbar>
      </AppBar>
      <Outlet />
    </Box>
  )
}

export default TabView;
