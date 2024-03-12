# Robotframerowk test cases

# *** HUOM SISÄINEN MUISTIINPANO ***
# Testien tekemistä varten tarvitaan omalle koneelle asennukset: Python ja pip
# Asenna robotframework koneellesi pip:illä komennolla: pip install robotframework
# Asenna SeleniumLibrary kirjasto: pip install robotframework-seleniumlibrary

# ** Ennen kuin projekti on julkaistu: käynnistä sekä BE että FE ennen testejä **
# Tee testit komennolla: robot test.robot TAI python -m robot test.robot

# Nyt toimivat testit ovat kirjautuminen, projektin lisäys, projektin muokkaus ja entryn muokkaus
# Mitä vielä uupuu: projektin poisto, entryn muokkaus ja poisto
# Uupuva hienosaaäntö: muokkaukset ja poistot koskemaan juuri lisättyä asiaa


*** Settings ***
Documentation           Test to check RF environment w/ SeleniumLibrary & ChromeDriver.
Library                 SeleniumLibrary   15.0   5.0

*** Variables ***
${Browser}              Chrome
${Sleep}                2
${URL}                  http://localhost:5173/login
${Username}             email@email.com
${Password}             AppUser1
 
*** Test Cases ***
# Testing login + project and entries REST features

# Testing Loging function 
Login to the TimeManagement service
        Open Browser            ${URL}              ${Browser} 
        Input Text              username            ${Username}
        Input Text              password            ${Password}
        click button            Login               
        Page Should Contain     Tämä on etusivu

# Testing to add a new project - POST
Adding a new project after logged in
        Click Link              Projektit
        click button            Lisää uusi projekti
        Input Text              name=title          RobotTest
        Click button            Tallenna
        Page Should Contain     RobotTest


# Testing to edit a project - PUT - ei löydä oikeaa kohtaa/buttonia, nyt kohdistuu ensimmäiseen riviin
Editing a project
#        click button            xpath=//td[contains(text(),'RobotTest')]/following-sibling::td/button[contains(text(),'Muokkaa')]
        click button            Muokkaa
        Input Text              name=title          Edited
        Click button            Tallenna
#        Page Should Contain     RobotTestEdited
        Page Should Contain     Edited

# Testing to add a new entry - POST - Ei pääse kiinni select osioon
# Adding a new entry to RobotTest project
#        Click Link                  Tuntikirjaukset
#        click button                Lisää uusi kirjaus
#        Select From List by Value   id=project_id           RobotTest
#        Input Text                  name=comment            RobotTestEntry
#        Click button                Tallenna
#        Page Should Contain         RobotTestEntry


# Testing to edit an entry - PUT - nyt ensimmäisen rivin kommentin Muokkaa
Editing a entry
        Click Link              Tuntikirjaukset
        click button            Muokkaa
        Input Text              name=entry            RobotTestEntry
        Click button            Tallenna
        Page Should Contain     RobotTestEntry

# Testing the deleting function in entry - DELETE


# Testing the deleting function in project - ei pääse käsiksi dialogiin, vielä työn alla
#Deleting a project RobotTestEdited
#        Click Link              Projektit
#        click button            Poista
#        Switch To Dialog
#        click button            Poista


# Testing that log out function works 
Logging out function
        Click Link              Logout
        Page Should Contain     Sinut on kirjattu ulos

# After tests have been done thene close browser
Closing Browser 
        Close Browser