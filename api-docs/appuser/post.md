# Luo uusi käyttäjä

**URL**: `/users`

**Metodi**: `POST`

**Vaadittu rooli**: `OWNER`

## Esimerkkipyyntö:

**Polku**: `BASE_URL/users`
```json
"title": "KÄYTTÄJÄN 1 LISÄYS",
    "roles": [
        {
            "appUser": {
                "id": 2,
                "first_name": "Toinen",
                "last_name": "Käyttäjä",
                "username": "new_user2"
            },
            "role": "OWNER"
        },
        {
            "appUser": {
                "id": 1,
                "first_name": "Ensimmäinen",
                "last_name": "Käyttäjä",
                "username": "new_user1"
            },
            "role": null
        }
    ]
}
