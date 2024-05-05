# Työaikakirjauksen muokkaus

- Vaatii kirjautumisen
- Käyttäjä voi muokata vain omia työaikakirjauksia

**Metodi**: `PUT`

**Vaadittu projektirooli**: `OWNER`, `USER`

**URL**: `{host}/projects/{projectId}/entries/{entryId}`


## Esimerkkipyyntö:

**request parameter**: `{projectId}`: Projekti, johon työaikakirjaus kuuluu
                       `{entryId}`: Muokattavan työaikakirjauksen id

**request header**: `Authorization: {token}`

**request body**: 

```json
{
    "comment": "MUOKATTU TYÖAIKAKIRJAUS",
    "entry_date": "2022-02-01",
    "start_time": "08:00:00",
    "end_time": "13:35:00"
}

```

## Esimerkkivastaus:

**response status:** `200 OK` 
