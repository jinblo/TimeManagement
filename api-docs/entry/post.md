# Lisää uusi entry

**URL**: `{host}/projects/:pk/entries`

**Metodi**: `POST`

**Vaadittu rooli**: 

## Esimerkkipyyntö:

**Polku**: `BASE_URL/{host}/projects/{id}/entries`

**request body**: 
```json
[
  {
   "id": 1,
  "comment": "testi",
  "entry_date": "2024-04-15",
  "start_time": "10:05:00",
  "end_time": "12:10:00",
  "project": {
    "id": 1,
    "title": "Testiprojekti"
    },
  "appUser": 1
  }
]
```
**response header**: -

**response body:** -

  
