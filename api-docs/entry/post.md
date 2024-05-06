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
    "comment": "UUSI TYÖAIKAKIRJAUS",
    "entry_date": "2022-02-01",
    "start_time": "08:00:00",
    "end_time": "13:35:00"
}

```

## Esimerkkivastaus:

**response status:** `201 Created` 
