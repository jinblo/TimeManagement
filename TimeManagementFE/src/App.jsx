import { RouterProvider, createBrowserRouter } from "react-router-dom"
import ProjectList from "./components/ProjectList"
import TabView from "./components/TabView"
import EntryList from "./components/EntryList"
import { useEffect, useState } from "react"
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
    <RouterProvider router={router} />
  )
}

export default App;
