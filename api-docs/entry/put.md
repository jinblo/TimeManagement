# Muokkaa id:llä haettua entryä

**URL**: `{host}/projects/:pk/entries/pk:`

**Metodi**: `PUT`

**Vaadittu rooli**: 

## Esimerkkipyyntö:

**Polku**: `BASE_URL/{host}/projects/{project_id}/entries/{entry_id}:`

**request header**: `Authorization: {token}`

**request body**: 
```json

  {
  "comment": "muokattu testi",
  "entry_date": "2024-04-15",
  "start_time": "10:05:00",
  "end_time": "12:10:00"
}

```
**response header**: -

**response body:** -


