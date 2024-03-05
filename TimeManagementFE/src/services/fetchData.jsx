
const baseUrl = 'http://localhost:8080'

// Kirjausten hakeminen APIsta
const fetchData = (href, setData) => {
  fetch(`${baseUrl}/${href}`)
    .then(response => response.json())
    .then(data => setData(data))
    .catch(error => console.error(error))
};

// Post, Put tai Delete pyyntöjen tekeminen APIin
const fetchWithOptions = (href, options) => {
  fetch(`${baseUrl}/${href}`, options)
    .then(response => {
      if (response.ok) {
        fetchData()
        setAlert('success')
      } else {
        setAlert('error')
      }
    }
    )
    .catch(error => {
      console.error(error)
    })
}

export default {
  fetchData, fetchWithOptions
}