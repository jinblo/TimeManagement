# Hae käyttäjä käyttäjänimellä

**URL**: `/byusername/{username}`

**Metodi**: `GET`

**Vaadittu rooli**: `OWNER`

## Esimerkkipyyntö:

**Polku**: `BASE_URL/byusername/{test_user1}`

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
