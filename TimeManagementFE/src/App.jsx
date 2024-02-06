import { RouterProvider, createBrowserRouter } from "react-router-dom"
import ProjectList from "./components/ProjectList"
import TabView from "./components/TabView"

const router = createBrowserRouter([
  {
    element: <Tab />,
    children: [
      {
        path: '/projectlist',
        element: <ProjectList />
      },
    ]

  }
])

function App() {
  return (
    <RouterProvider router={router} />
  )
}

export default App;
