# Rekisteröityminen

**Metodi**: `POST`

**URL**: `{host}/users`


## Esimerkkipyyntö:

**request body**: 

```json
{
    "first_name": "UUSI",
    "last_name": "KÄYTTÄJÄ",
    "username": "new_user1",
    "password_hash": "password"
}

```

## Esimerkkivastaus:

**response status:** `201 Created`: Rekisteröityminen onnistui
                     `409 Conflict`: Käyttäjätunnus on jo varattu
