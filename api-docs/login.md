# Kirjautuminen

**URL**: `{host}/login/`

**Metodi**: `POST`

**Vaadittu rooli**: -

## Esimerkkivastaus:

**Polku**: `BASE_URL/{host}/login`

`Authorization: {token}`

**request body:** 

```json
{
"username": "username",
"password": "password"
}

```
**request body:** `Authorization: {token}`

**response status:** `200 OK`

**response body:**
