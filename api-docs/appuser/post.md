# Luo uusi käyttäjä

**URL**: `/api/users`

**Metodi**: `POST`

**Vaadittu rooli**: 

## Esimerkkipyyntö:

**Polku**: `BASE_URL/api/users`
```json
{
"first_name": "Testi",
"last_name": "Testiläinen",
  "email": "test@tester.com"
}
```
## Esimerkkivastaus:

```json
{
  "userId": 1,
  "first_name": "Testi",
  "last_name": "Testiläinen",
  "email": "test@tester.com"
}
```
