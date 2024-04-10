# Poista käyttäjä id:llä

**URL**: `/users/:pk`

**Metodi**: `DELETE`

**Vaadittu rooli**: OWNER

## Esimerkkipyyntö:

**Polku**: `BASE_URL/users/:pk`

## Esimerkkipyyntö:
´´´json
{
    "title": "PROJEKTIN NIMI",
    "roles": [
        {
            "appUser": {
                "id": 2
            },
            "role": null
        },
        {
            "appUser": {
                "id": 1
            },
            "role": "OWNER"
        }
    ]
}
´´´json

## Vastauksen paluukoodit

**Koodit**:

`200 OK`
