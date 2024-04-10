# Muokkaa käyttäjää id:llä

**URL**: `/users/:pk`

**Metodi**: `PUT`

**Vaadittu rooli**: `OWNER`

**Polku**: `BASE_URL/users/:pk`

## Esimerkkipyyntö:

```json
{
    "title": "PROJEKTIN NIMI",
    "roles": [
        {
            "appUser": {
                "id": 2
            },
            "role": null
        },
        {
            "appUser": {
                "id": 1
            },
            "role": "OWNER"
        }
    ]
}

