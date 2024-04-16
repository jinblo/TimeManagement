# Muokkaa käyttäjää id:llä

**URL**: `/users/:pk`

**Metodi**: `PUT`

**Vaadittu rooli**: -

**Polku**: `BASE_URL/{host}/users/{id}`

## Esimerkkipyyntö:

**request header**: `Authorization: {token}`

**request body**: 

```json
{
 "id": 1,
"first_name": "Muokattu",
"last_name": "Käyttäjä",
"username": "new_user1",
"password_hash": "******"

}
```

**response header**: -

**response body:** -




