import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import 'dayjs/locale/fi';
import AuthProvider from "./services/AuthProvider"
import Routes from "./services/Routes"
import theme from './theme';
import { ThemeProvider } from '@mui/material/styles';


function App() {

  return (
    <ThemeProvider theme={theme}>
      <LocalizationProvider dateAdapter={AdapterDayjs} adapterLocale="fi">
        <AuthProvider>
          <Routes />
        </AuthProvider>
      </LocalizationProvider>
    </ThemeProvider>
  )
}

export default App;
