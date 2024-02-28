import { Alert, Fade } from "@mui/material";
import { useState } from "react";

// Alert for fetch results

const AlertMessage = ({ alert, alertMessage, setAlert }) => {
  const [show, setShow] = useState(true)

  return (
    <Fade
      in={show}
      timeout={{ enter: 1000, exit: 1000 }}
      addEndListener={() => {
        setTimeout(() => {
          setShow(false)
          setAlert(null)
        }, 3000)
      }}
    >
      <Alert severity={alert}>{alertMessage}</Alert>
    </Fade>
  )
}

export default AlertMessage;