# Hae käyttäjä käyttäjänimellä

**URL**: `/byusername/{username}`

**Metodi**: `GET`

**Vaadittu rooli**: -

## Esimerkkipyyntö:

**Polku**: `BASE_URL/{host}/users/byusername/{username}`

**request header**: `Authorization: {token}`

**request body**: -

**response header**: -

**response body:**

```json
{
 "id": 1,
"first_name": "Ensimmäinen",
"last_name": "Käyttäjä",
"username": "new_user1",
"password_hash": "******"

}
