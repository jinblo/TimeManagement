# Hae käyttäjä id:llä

**URL**: `/users/:pk`

**Metodi**: `GET`

**Vaadittu rooli**: -

**Polku**: `BASE_URL/{host}/users/{id}`

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




