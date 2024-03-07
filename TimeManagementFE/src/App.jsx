import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import 'dayjs/locale/fi';
import AuthProvider from "./services/AuthProvider"
import Routes from "./services/Routes"

function App() {

  return (
    <LocalizationProvider dateAdapter={AdapterDayjs} adapterLocale="fi">
      <AuthProvider>
        <Routes />
      </AuthProvider>
    </LocalizationProvider>
  )
}

export default App;
