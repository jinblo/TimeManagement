# Työaikakirjausjärjestelmä - Team Red

Projekti on toteutettu Haaga-Helian Ohjelmistoprojekti2 kurssilla.
Toteuttava tiimi: Sonja Helminen, Katja Jääskeläinen, Kristin Luik, Ulla Montonen ja Anton Rezin. 


## Sovelluksen tarkoitus

Sovelluksen tarkoitus on toimia työaikakirjausjärjestelmänä, esimerkiksi yksityisyrittäjille, joiden on pidettävä kirjaa tehdystä työstä ja sen kestosta. Käyttäjällä voi samanaikaisesti olla hallinnoitavana useita projekteja, joille voidaan kohdentaa omia työaikakirjauksia. Sovelluksessa käyttäjä voi kirjoittaa muistiinpanoja tehdystä työstä ja merkitä työn keston sekä päivämäärän. Lisäksi työtunnit voidaan laskea yhteen käyttäjän valitsemalta ajanjaksolta tai projektilta.


## Tärkeimmät ominaisuudet

Sovellus vaatii rekisteröitymisen. Rekisteröitynyt käyttäjä voi luoda projekteja ja tehdä projekteille työaikakirjauksia.

## Toteutusteknologiat
* Spring Boot 3.2.1
* Java 21.0.2
* React 18.2.0
* JavaScript
* Node 20.11.0

## Tietokanta

![työaikaseuranta](https://github.com/TeamRed-Ohjelmistoprojekti2/TimeManagement/assets/91193039/da0099c9-94fb-4109-b955-0dba29ac7042)

### Project / projekti

| Attribute     | Type                    | Description                     | Validation                | Requirements |
|:------------- |:------------------------|:--------------------------------|:--------------------------|:-------------|
| id (PK)       | long /Integer           | Project id                      |                           |              |
| title         | String / Varchar        | Projektin nimi                  | A-z0-9.,-_!"#%&/()=?*{}[] | length>1     |
| List Entry    | entries / OneToMany     | Projektiin liittyvät kirjaukset |                           |              |

### Entry / työaikakirjaus

| Attribute       | Type                    | Description                    | Validation                | Requirements |
|:--------------- |:------------------------|:-------------------------------|:--------------------------|:-------------|
| enty_id (PK)    | long / Integer          | Entry id                       |                           |              |
| comment         | String / Varchar        | Kirjauksen kommentti           | A-z0-9.,-_!"#%&/()=?*{}[] |              |
| entry_date      | LocalDate / Date        | Kirjauksen päivämäärä          | 0-9-                      | 'YYYY-MM-DD' |
| start_time      | LocalTime / Time        | Aloitusajankohta               | 0-9:                      | 'HH:mm:ss'   |
| end_time        | LocalTime / Time        | Lopetusajankohta               | 0-9:                      | 'HH:mm:ss'   |
| project_id (FK) | JoinColumn / ManyToOne  | Viittaus projektiin            |                           |              |
| appUser_id (FK) | JoinColumn / ManyToOne  | Viittaus käyttäjään            |                           |              |

### AppUser / käyttäjä

| Attribute     | Type                    | Description                      | Validation                | Requirements |
|:------------- |:------------------------|:---------------------------------|:--------------------------|:-------------|
| id (PK)       | long / Integer          | AppUser id                       |                           |              |
| first_name    | String / Varchar        | Käyttäjän etunimi                | A-z-                      | length>2     |
| last_name     | String / Varchar        | Käyttäjän sukunimi               | A-z-                      | length>2     |
| email         | String / Varchar        | Käyttäjän sähköposti             | A-z._-@                   | length>3     |
| password_hash | String / Varchar        | Salasana hash muodossa           | A-z0-9.,-_!"#%&/()=?*{}[] | length>7     |
| List Project  | projects / OneToMany    | Projektiin liittyvät kirjaukset  |                           |              |

## Api-dokumentaatio

* [Entry](api-docs/entry/)
* [Project](api-docs/project/)
* [AppUser](api-docs/appuser/)

## Backlogit

* [Sprint backlog](https://github.com/orgs/TeamRed-Ohjelmistoprojekti2/projects/3/views/2)
* [Product backlog](https://github.com/orgs/TeamRed-Ohjelmistoprojekti2/projects/3)
