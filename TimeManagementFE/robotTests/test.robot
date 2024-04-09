# Robotframerowk test cases
#
# *** HUOM SISÄINEN MUISTIINPANO ***
# Testien tekemistä varten tarvitaan omalle koneelle asennukset: Python ja pip
# Asenna robotframework koneellesi pip:illä komennolla: pip install robotframework
# Asenna SeleniumLibrary kirjasto: pip install robotframework-seleniumlibrary
#
# ** Ennen kuin projekti on julkaistu: käynnistä sekä BE että FE ennen testejä **
# Tee testit komennolla: robot test.robot TAI python -m robot test.robot
#
# Mitä vielä uupuu: entryn muokkaus
# Uupuva hienosaaäntö: muokkaukset ja poistot koskemaan juuri lisättyä asiaa


*** Settings ***
Documentation           Tests to check login and REST functions in project and entry
Library                 SeleniumLibrary   15.0   5.0
Resource                resources.robot

*** Test Cases ***
Login to the TimeManagement service
        [Tags]                      loging  test
        Open Browser                ${URL}              ${Browser} 
        LogInWithCredentials
        click button                Login               
        Page Should Contain         Hei


Adding a new project after logged in
        [Tags]                      postProject
        Click Link                  Projektit
        click button                Lisää uusi projekti
        Input Text                  name=title          ${AddedProject} 
        SaveButton
        Page Should Contain         ${AddedProject} 


# Muutos kohdistuu ensimmäiseen riviin
Editing an existing project
        [Tags]                      putProject 
        Click Button                Muokkaa
        Input Text                  name=title          ${EditedText}
        SaveButton
        Page Should Contain         ${KirjausOnnistui}


# Ei pääse kiinni select osioon
#Adding a new entry to RobotTest project
#        [Tags]                      postEntry   test
#        Click Link                  Tuntikirjaukset
#        click button                Lisää uusi kirjaus
#        Select From List By Index   name:project    1
#        Click Element               //MenuItem[contains(text(), 'RobotTest')]
#        Input Text                  name=comment            CommentTest
#        SaveButton
#        Page Should Contain         CommentTest


# nyt ensimmäisen rivin kommentin Muokkaa
Editing an existing entry
        [Tags]                      putEntry
        Click Link                  Tuntikirjaukset
        click button                Muokkaa
        Input Text                  name=entry            ${EditedText}
        SaveButton
        Page Should Contain         ${KirjausOnnistui}

# poistaa ensimmäisen enrtyn
 Deleting the first entry
        [Tags]                      deleteEntry  
        Click Link                  Tuntikirjaukset
        click button                Poista
        Page Should Contain         Vahvista työaikakirjauksen poisto.
        Click Button                Poista kirjaus
        Wait Until Page Contains    Kirjaus poistettu onnistuneesti    

# poistaa ensimmäisen projektin
Deleting a project
        [Tags]                      deleteProject  
        Click Link                  Projektit
        click button                Poista
        Page Should Contain         Haluatko varmasti poistaa kyseisen projektin?
        Page Should Contain         Peruuta
        Click Button                Poista projekti
        Wait Until Page Contains    Kirjaus poistettu onnistuneesti


Testing logging out function
        [Tags]                      logout
        Click Link                  Kirjaudu ulos
        Page Should Contain         Sinut on kirjattu ulos


Closing Browser 
        Close Browser