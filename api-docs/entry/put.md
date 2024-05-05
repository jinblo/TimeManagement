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
  "comment": "Muokattu työaikakirjaus",
  "entry_date": "2024-04-15",
  "start_time": "10:05:00",
  "end_time": "12:10:00",
}

```

## Esimerkkivastaus:

**response status:** `200 OK` 