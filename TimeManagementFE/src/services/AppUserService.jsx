import { baseUrl } from "./baseUrl"


export async function postUser(user) {
  const request =
    fetch(`${baseUrl}/users`, {
      method: 'post',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(user)
    })
      .catch(error => console.error(error))
  return request
}

export async function putUser(token, user) {
  const request =
    fetch(`${baseUrl}/users/${user.id}`, {
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

export async function deleteUser(token, user_id) {
  const request =
    fetch(`${baseUrl}/users/${user_id}`, {
      method: 'delete',
      headers: {
        'Authorization': token,
      },
    })
      .catch(error => console.error(error))
  return request
}
