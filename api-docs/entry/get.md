# Kaikkien käyttäjän omien työaikakirjausten haku

- Vaatii kirjautumisen
- Käyttäjä näkee työaikakirjaukset kaikista projekteista, jossa hänellä on mikä tahansa vaadituista projektirooleista
- Käyttäjä näkee vain omat työaikakirjauksensa kustakin projektista

**Metodi**: `GET`

**Vaadittu projektirooli**: `OWNER`, `USER`

**URL**: `{host}/entries`


## Esimerkkipyyntö:

**request header:** `Authorization: {token}`


## Esimerkkivastaus:

**response status:** `200 OK`

**response body:**

```json
[
    {
        "id": 1,
        "comment": "Test entry 1",
        "entry_date": "2022-02-02",
        "start_time": "10:05:00",
        "end_time": "15:15:00",
        "project": {
            "id": 1,
            "title": "Testproject 1"
        },
    },
    {
        "id": 3,
        "comment": "Test entry 3",
        "entry_date": "2022-02-02",
        "start_time": "10:05:00",
        "end_time": "15:15:00",
        "project": {
            "id": 3,
            "title": "Testproject 3"
        },
    },
    {
        "id": 4,
        "comment": "Test entry 4",
        "entry_date": "2022-02-02",
        "start_time": "10:05:00",
        "end_time": "15:15:00",
        "project": {
            "id": 3,
            "title": "Testproject 3"
        },
    },
]
  
    
  


