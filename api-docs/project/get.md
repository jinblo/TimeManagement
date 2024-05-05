# Kaikkien käyttäjän projektien haku

- Vaatii kirjautumisen
- Käyttäjä näkee vain omat projektinsa
- Käyttäjä näkee kaikki projektinsa, jossa hänellä on mikä tahansa vaadituista projektirooleista

**Metodi**: `GET`

**Vaadittu projektirooli**: `OWNER`, `USER`, `VIEWER`

**URL**: `{host}/projects`


## Esimerkkipyyntö:

**request header**: `Authorization: {token}`


## Esimerkkivastaus:

**response status:** `200 OK`

**response body:**

```json
[
    {
        "project": {
            "id": 1,
            "title": "Testproject 1",
            "roles": [
                {
                    "appUser": {
                        "id": 2,
                        "username": "new_user2"
                    },
                    "role": "USER"
                },
                {
                    "appUser": {
                        "id": 1,
                        "username": "new_user1"
                    },
                    "role": "OWNER"
                }
            ]
        },
        "appUser": {
            "id": 1,
            "username": "new_user1"
        },
        "role": "OWNER"
    },
    {
        "project": {
            "id": 2,
            "title": "Testproject 2",
            "roles": [
                {
                    "appUser": {
                        "id": 2,
                        "username": "new_user2"
                    },
                    "role": "OWNER"
                },
                {
                    "appUser": {
                        "id": 1,
                        "username": "new_user1"
                    },
                    "role": "USER"
                }
            ]
        },
        "appUser": {
            "id": 1,
            "username": "new_user1"
        },
        "role": "USER"
    }
]

```
