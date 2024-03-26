import { baseUrl } from "./baseUrl"

export async function getProjects(token) {
  const request =
    fetch(`${baseUrl}/projects`, {
      headers: {
        'Authorization': token
      }
    })
      .catch(error => console.error(error))
  return request.then(response => response.json())
}
export async function postProject(token, project) {
  const request =
    fetch(`${baseUrl}/projects`, {
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
    fetch(`${baseUrl}/projects/${project.id}`, {
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
    fetch(`${baseUrl}/projects/${project_id}`, {
      method: 'delete',
      headers: {
        'Authorization': token,
      },
    })
      .catch(error => console.error(error))
  return request
}
