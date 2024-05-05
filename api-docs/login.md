# Kirjautuminen

**Metodi**: `POST`

**URL**: `{host}/login`


## Esimerkkipyyntö:

**request body:** 

```json
{
    "username": "username",
    "password": "password"
}

```

## Esimerkkivastaus:

**response status:** `200 OK`

**response header:** `Authorization: {token}`
