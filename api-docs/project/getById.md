# Tietyn projektin haku id:lla

- Vaatii kirjautumisen
- Käyttäjä näkee projektin vain, jos hänellä on jokin vaadituista projektirooleista
- Käyttäjä näkee vain omat työaikakirjauksensa, jos hänen projektiroolinsa on Käyttäjä (`USER`)
- Käyttäjä näkee projektin kaikki työaikakirjaukset, jos hänen projektiroolinsa on Omistaja (`OWNER`) tai Seuraaja (`VIEWER`)
- Käyttäjä ei näe työaikaikirjauksissa käyttäjätietoja, jos hänen projektiroolinsa on Seuraaja (`VIEWER`)

**Metodi**: `GET`

**Vaadittu projektirooli**: `OWNER`, `USER`, `VIEWER`

**URL**: `{host}/projects/{projectId}`


## Esimerkkipyyntö:

**request parameter**: `{projectId}`: Haettavan projektin id

**request header:** `Authorization: {token}`


## Esimerkkivastaus:

**response status:** `200 OK`

**response body:**

```json

{
    "id": 1,
    "title": "Testproject 1",
    "roles": [
        {
            "appUser": {
                "id": 2,
                "username": "new_user2"
            },
            "role": "USER"
        },
        {
            "appUser": {
                "id": 1,
                "username": "new_user1"
            },
            "role": "OWNER"
        }
    ],
    "entries": [
        {
            "id": 1,
            "comment": "Test entry 1",
            "entry_date": "2022-02-02",
            "start_time": "10:05:00",
            "end_time": "15:15:00",
            "appUser": {
                "id": 1,
                "username": "new_user1"
            },
        },
        {
            "id": 2,
            "comment": "Test entry 2",
            "entry_date": "2022-02-01",
            "start_time": "08:00:00",
            "end_time": "13:35:00",
            "appUser": {
                "id": 2,
                "username": "new_user2"
            }
        }
    ]
}

```