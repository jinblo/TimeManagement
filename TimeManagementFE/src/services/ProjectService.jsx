
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
export async function postProject(token, project) {
  const request =
    fetch(`${baseUrl}`, {
      method: 'post',
      headers: {
        'Authorization': token,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(project)
    })
      .catch(error => console.error(error))
  return request
}

export async function putProject(token, project) {
  const request =
    fetch(`${baseUrl}/${project.id}`, {
      method: 'put',
      headers: {
        'Authorization': token,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(project)
    })
      .catch(error => console.error(error))
  return request
}

export async function deleteProject(token, project_id) {
  const request =
    fetch(`${baseUrl}/${project_id}`, {
      method: 'delete',
      headers: {
        'Authorization': token,
      },
    })
      .catch(error => console.error(error))
  return request
}
