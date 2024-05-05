# Käyttäjätietojen muokkaus

- Vaatii kirjautumisen
- Käyttäjä voi muokata vain omia tietojaan

**Metodi**: `PUT`

**URL**: `{host}/users/{id}`


## Esimerkkipyyntö:

**request parameter**: `{id}`: Muokattavan käyttäjän id

**request header**: `Authorization: {token}`

**request body**: 

```json
{
"first_name": "Muokattu",
"last_name": "Käyttäjä",
"username": "new_user1",
"password_hash": "salasana"
}

```

## Esimerkkivastaus:

**response status:** `200 OK`: Käyttäjätietojen muokkaus onnistui

**response header:** `Authorization: {token}`: päivitetty token


## Esimerkkivastaus 2:

**response status:** `409 Conflict`: Käyttäjätunnus on jo varattu