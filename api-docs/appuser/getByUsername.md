# Käyttäjän haku käyttäjätunnuksella

- Vaatii kirjautumisen

**Metodi**: `GET`

**Vaadittu projektirooli**: `OWNER`

**URL**: `{host}/users/byusername/{username}`


## Esimerkkipyyntö:

**request parameter**: `{username}`: Haettavan käyttäjän käyttäjätunnus

**request header**: `Authorization: {token}`


## Esimerkkivastaus:

**response status:** `200 OK`

**response body:**

```json
{
    "id": 1,
    "username": "new_user1"
}

```
