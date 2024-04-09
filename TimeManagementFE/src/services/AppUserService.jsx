import { baseUrl } from "./baseUrl"

export async function login(user) {
  const request =
    fetch(`${baseUrl}/login`, {
      method: 'POST',
      mode: 'cors',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(user)
    })
  return request
};

export async function register(user) {
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

export async function getUser(token, username) {
  const request =
    fetch(`${baseUrl}/users/byusername/${username}`, {
      headers: {
        'Authorization': token
      }
    })
      .catch(error => console.error(error))
  return request.then(response => response.json())
}