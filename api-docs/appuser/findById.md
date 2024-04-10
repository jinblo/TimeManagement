# Hae käyttäjä id:llä

**URL**: `/users/:pk`

**Metodi**: `GET`

**Vaadittu rooli**: ÒWNER`

**Polku**: `BASE_URL/users/:pk`

## Esimerkkivastaus:

```json
{
    "appUser": {
        "id": 1,
        "first_name": "Ensimmäinen",
        "last_name": "Käyttäjä",
        "username": "new_user1"
    },
    "role": "OWNER"
}




