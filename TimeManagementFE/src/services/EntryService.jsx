import { baseUrl } from "./baseUrl"

export async function getEntries(token) {
  const request =
    fetch(`${baseUrl}/entries`, {
      headers: {
        'Authorization': token
      }
    })
      .catch(error => console.error(error))
  return request.then(response => response.json())
}

export async function postEntry(token, entry, project_id) {
  const request =
    fetch(`${baseUrl}/projects/${project_id}/entries`, {
      method: 'post',
      headers: {
        'Authorization': token,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(entry)
    })
      .catch(error => console.error(error))
  return request
}

export async function putEntry(token, entry) {
  const request =
    fetch(`${baseUrl}/projects/${entry.project.id}/entries/${entry.entry_id}`, {
      method: 'put',
      headers: {
        'Authorization': token,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(entry)
    })
      .catch(error => console.error(error))
  return request
}

export async function deleteEntry(token, entry_id) {
  const request =
    fetch(`${baseUrl}/entries/${entry_id}`, {
      method: 'delete',
      headers: {
        'Authorization': token,
      },
    })
      .catch(error => console.error(error))
  return request
}
