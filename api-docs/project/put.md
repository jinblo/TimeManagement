# Projektin muokkaus

- Vaatii kirjautumisen
- Sisältää projektin käyttäjien muokkauksen
    - Uusi käyttäjä lisätään projektiin lisäämällä käyttäjä `roles` listaan
    - Käyttäjän projektirooli muokataan asettamalla `role` arvo. Sallitut arvot ovat `OWNER`, `USER` ja `VIEWER` sekä `null`
    - Käyttäjä poistetaan projektista asettamalla `role` arvoksi `null`
    - Projektissa on oltava aina vähintään yksi käyttäjä, jonka projektirooli on `OWNER`

**Metodi**: `PUT`

**Vaadittu projektirooli**: `OWNER`

**URL**: `{host}/projects/{projectId}`


## Esimerkkipyyntö:

**request parameter**: `{projectId}`: Muokattavan projektin id

**request header:** `Authorization: {token}`

**request body:**

```json
{
    "title": "Muokattu projekti",
    "roles": [
        {
            "appUser": {
                "id": 1
            },
            "role": "OWNER"
        },
                {
            "appUser": {
                "id": 2
            },
            "role": "USER"
        }
    ]
}
```

## Esimerkkivastaus:

**response status:** `200 OK`
