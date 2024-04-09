# Luo uusi käyttäjä

**URL**: `/users`

**Metodi**: `POST`

**Vaadittu rooli**: 

## Esimerkkipyyntö:

**Polku**: `BASE_URL/users`
```json
{
"first_name": "Testi",
"last_name": "Testiläinen",
  "username": "new_user1"
}
```
## Esimerkkivastaus:

```json
{
  "userId": 1,
  "first_name": "Testi",
  "last_name": "Testiläinen",
  "username": "new_user1"
}
```
