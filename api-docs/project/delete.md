# Projektin poisto

- Vaatii kirjautumisen
- Käyttäjä voi poistaa vain oman projektinsa
- Projektin poisto poistaa myös siihen liittyvät työaikakirjaukset

**URL**: `{host}/projects/{projectId}`

**Metodi**: `DELETE`

**Vaadittu rooli**: `OWNER`


## Esimerkkipyyntö:

**request parameter**: `{projectId}`: Poistettavan projektin id

**request header:** `Authorization: {token}`


## Esimerkkivastaus:

**response status:** `200 OK`