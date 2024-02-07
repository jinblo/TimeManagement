import { RouterProvider, createBrowserRouter } from "react-router-dom"
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import 'dayjs/locale/fi';
import ProjectList from "./components/ProjectList"
import TabView from "./components/TabView"
import EntryList from "./components/EntryList"
import Error from "./components/Error"
import Home from "./components/Home"

const router = createBrowserRouter([
  {
    element: <TabView />,
    errorElement: <Error />,
    children: [
      {
        index: true,
        element: <Home />,
      },
      {
        path: '/projectlist',
        element: <ProjectList />,
      },
      {
        path: '/entrylist',
        element: <EntryList />
      },
    ]

  }
])

function App() {

  return (
    <LocalizationProvider dateAdapter={AdapterDayjs} adapterLocale="fi">
      <RouterProvider router={router} />
    </LocalizationProvider>
  )
}

export default App;
