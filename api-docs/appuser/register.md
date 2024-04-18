# Rekisteröityminen

**URL**: `/{host}/users`

**Metodi**: `POST`

**Vaadittu rooli**: -

## Esimerkkipyyntö:

**Polku**: `BASE_URL/{host}/users`

**request header**: `Authorization: {token}`

**request body**: 

```json
{
 "id": 1,
"first_name": "Uusi",
"last_name": "Käyttäjä",
"username": "new_user1",
"password_hash": "******"

}

```

**response header**: -

**response body:** -
