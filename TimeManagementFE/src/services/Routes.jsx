import { RouterProvider, createBrowserRouter } from "react-router-dom"
import { useAuth } from "../services/AuthProvider"
import ProjectList from "../components/ProjectList"
import TabView from "../components/TabView"
import EntryList from "../components/EntryList"
import Error from "../components/Error"
import Home from "../components/Home"
import Login from "../components/Login"
import Logout from "../components/Logout"

function Routes() {
  const { token } = useAuth()

  const publicRoutes = [
    {
      index: true,
      path: '/',
      element: <Login />,
    }, {
      index: true,
      path: '/login',
      element: <Login />,
    },
  ]

  const userRoutes = [
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
    {
      path: '/logout',
      element: <Logout />,
    }
  ]


  const router = createBrowserRouter([{
    element: <TabView />,
    errorElement: <Error />,
    children: [
      ...(!token ? publicRoutes : []),
      ...userRoutes,
    ]
  }])

  return (
    <RouterProvider router={router} />
  )
}

export default Routes;
