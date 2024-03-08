
const baseUrl = 'http://localhost:8080/projects'

export async function getProjects(token) {
  const request =
    fetch(`${baseUrl}`, {
      headers: {
        'Authorization': token
      }
    })
      .catch(error => console.error(error))
  return request.then(response => response.json())
}
