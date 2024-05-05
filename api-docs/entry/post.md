# Uuden työaikakirjauksen lisäys

- Vaatii kirjautumisen
- Käyttäjä voi lisätä työaikakirjauksen vain itselleen

**Metodi**: `POST`

**Vaadittu projektirooli**: `OWNER`, `USER`

**URL**: `{host}/projects/{projectId}/entries`


## Esimerkkipyyntö:

**request parameter**: `{projectId}`: Projekti, johon työaikakirjaus lisätään

**request header:** `Authorization: {token}`

**request body**: 

```json
{
  "comment": "Uusi työaikakirjaus",
  "entry_date": "2024-04-15",
  "start_time": "10:05:00",
  "end_time": "12:10:00",
}

```

## Esimerkkivastaus:

**response status:** `201 Created` 