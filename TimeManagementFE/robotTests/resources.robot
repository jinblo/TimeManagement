*** Settings ***
Library     SeleniumLibrary    15.0    5.0


*** Variables ***
${Browser}              Chrome
${URL-localhost}        http://localhost:5173/TimeManagement/login
${URL}                  https://teamred-ohjelmistoprojekti2.github.io/TimeManagement/
${AddedProject}         RobotTest
${EditedText}           Edited
${EditedProject}        RobotTestEdited
${AddedEntry}           RobotTestEntry
${EditedEntry}          RobotTestEntryEdited
&{TESTUSER}             username=new_user1    password=AppUser1
&{TESTUSERHEROKU}       username=RobotTest    password=RobotTest
${KirjausOnnistui}      Kirjaus tallennettu onnistuneesti


*** Keywords ***
### Press certain button on the page ###
SaveButton
    Click button    Tallenna

SaveAllChangesButton
    Click button    Tallenna kaikki muutokset

LogInbutton
    Click button    Kirjaudu

EditButton
    Click Button    Muokkaa

DeleteButton
    click button    Poista

DeleteProjectButton
    Click Button    Poista projekti

DeleteEntryButton
    Click Button    Poista kirjaus

AddProjectButton
    click button    Lisää uusi projekti

AddEntryButton
    click button    Lisää uusi kirjaus

### LogIn process using Localhost ###

OpenBrowserLocalhost
    Open Browser    ${URL-localhost}    ${Browser}

LogInWithCredentials
    Input Text    username    ${TESTUSER}[username]
    Input Text    password    ${TESTUSER}[password]

PageContainHei
    Page Should Contain    Hei

### LogIn process using Deployment version ###

OpenBrowserGitHUbPage
    Open Browser    ${URL}    ${Browser}

LogInWithHeroku
    Input Text    username    ${TESTUSERHEROKU}[username]
    Input Text    password    ${TESTUSERHEROKU}[password]

PageContainHeiRobotFramework
    Page Should Contain    Hei Robot Framework

### Adding project ###

AddProjectInfo
    Input Text    name=title    ${AddedProject}

CheckProjectAdded
    Page Should Contain    ${AddedProject}

### Editing project ###

EditProjectInfo
    Input Text    name=title    ${EditedText}

### Adding entry ###

### Editing entry ###

EditEntryInfo
    Input Text    name=entry    ${EditedText}

### Navigating on the page ###

ClickProjects
    Click Link    Projektit

ClickEntries
    Click Link    Tuntikirjaukset

ClickLogOut
    Click Link    Kirjaudu ulos

### Page contain ###

ProjectDeletionWarning
    Page Should Contain    Haluatko varmasti poistaa kyseisen projektin?

EntryDeletionWarning
    Page Should Contain    Vahvista työaikakirjauksen poisto.

SuccessfulDeletion
    Wait Until Page Contains    Kirjaus poistettu onnistuneesti

SuccessfulLogOut
    Page Should Contain    Sinut on kirjattu ulos

SuccessfullEdition
    Page Should Contain    ${KirjausOnnistui}