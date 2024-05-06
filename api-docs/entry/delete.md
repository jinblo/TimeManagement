# Työaikakirjauksen poisto

- Vaatii kirjautumisen
- Käyttäjä voi poistaa vain omia työaikakirjauksia

**Metodi**: `DELETE`

**Vaadittu projektirooli**: `OWNER`, `USER`

**URL**: `{host}/projects/{projectId}/entries/{entryId}`


## Esimerkkipyyntö:

**request parameter**: `{projectId}`: Projekti, johon työaikakirjaus kuuluu
                       `{entryId}`: Poistettavan työaikakirjauksen id

**request header**: `Authorization: {token}`


## Esimerkkivastaus:

**response status:** `200 OK` 