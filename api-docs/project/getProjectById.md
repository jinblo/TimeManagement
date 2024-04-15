# Hae projekti id:llä

**URL**: `{host}/projects/:pk`

**Metodi**: `GET`

**Vaadittu rooli**: `OWNER`

## Esimerkkipyyntö:

**Polku**: `BASE_URL/projects/{project_id}`

**request header:** `Authorization: {token}`

**request body:** -

**response header:** -

**response body:**

```json

{
    "id": 1,
    "title": "Testproject 1",
    "roles": [
        {
            "appUser": {
                "id": 2,
                "first_name": "Toinen",
                "last_name": "Käyttäjä",
                "username": "new_user2"
            },
            "role": "USER"
        },
        {
            "appUser": {
                "id": 1,
                "first_name": "Ensimmäinen",
                "last_name": "Käyttäjä",
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
            "appUser": 1
        },
        {
            "id": 2,
            "comment": "Test entry 2",
            "entry_date": "2022-02-01",
            "start_time": "08:00:00",
            "end_time": "13:35:00",
            "appUser": 2
        }
    ]
}
```
