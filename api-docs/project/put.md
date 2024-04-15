# Muokkaa id:llä haettua projektia

**URL**: `/{host}/projects/:pk:`

**Metodi**: `PUT`

**Vaadittu rooli**: `OWNER`

## Esimerkkipyyntö:

**Polku**: `BASE_URL/{host}/projects/{project_id}`

**request header:** `Authorization: {token}`

**request body:** 

```json
{
    "title": "Muokattu projekti",
    "roles": [
        {
            "appUser": {
                "id": 1
            },
            "role": "OWNER"
        },
        {
            "appUser": {
                "id": 2
            },
            "role": "USER"
        }
    ]
```

**response header:** -

**response body:** -


