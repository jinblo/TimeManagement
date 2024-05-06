# Uuden projektin lisäys

- Vaatii kirjautumisen

**Metodi**: `POST`

**Vaadittu projektirooli**: Lisääjä saa aina roolin `OWNER`

**URL**: `{host}/projects`


## Esimerkkipyyntö:

**request header:** `Authorization: {token}`

**request body:** 

```json
{
    "title": "UUSI PROJEKTI"
}

```

## Esimerkkivastaus:

**response status:** `201 Created`
