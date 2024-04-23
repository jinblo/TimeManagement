*** Settings ***
Library     SeleniumLibrary    15.0    5.0
# Keywords sections:
# - Click certain button on the page
# - LogIn process using Localhost
# - LogIn process using Deployment version
# - Adding project process
# - Editing project process
# - Adding entry process
# - Editing entry process
# - Navigating on the page
# - Page contain
# TARKISTA ETTÄ KAIKKI KÄYTÖSSÄ


*** Variables ***
${Browser}                          Chrome
${URL-localhost}                    http://localhost:5173/TimeManagement/login
${URL}                              https://teamred-ohjelmistoprojekti2.github.io/TimeManagement/
${AddedProject}                     RobotTest
${EditedText}                       Edited
${EditedProject}                    RobotTestEdited
${AddedEntry}                       RobotTestEntry
${EditedEntry}                      RobotTestEntryEdited
&{TESTUSER}                         username=testi    password=AppUser1
&{TESTUSERHEROKU}                   username=RobotTest    password=RobotTest
${KirjausOnnistui}                  Kirjaus tallennettu onnistuneesti
${ProjectEditedSuccessfully}        Projekti tallennettu onnistuneesti
${ProjectDeletedSuccessfully}       Projekti poistettu onnistuneesti
${UsernameLocal}                    new_user2
${UsernameHeroku}                   RobotFriend
${CommentText}                      CommentText

# TARKISTA ETTÄ KAIKKI KÄYTÖSSÄ


*** Keywords ***
### Click certain button on the page ###
SaveButton
    Click Button    Tallenna

SaveAllChangesButton
    Click Button    Tallenna kaikki muutokset

LogInbutton
    Click Button    Kirjaudu

RegisterButton
    Click Button    Rekisteröidy

EditButton
    Click Button    Muokkaa

DeleteButton
    click Button    Poista

DeleteProjectButton
    Click Button    Poista projekti

DeleteEntryButton
    Click Button    Poista kirjaus

AddProjectButton
    click Button    Lisää uusi projekti

AddEntryButton
    click Button    Lisää uusi kirjaus

FindUserButton
    Click Button    Hae käyttäjä

### Registration ###

AddUserName
    Input Text    id=usernameReg    ${TESTUSER}[username]

AddPassword
    Input Text    name=password_hash    ${TESTUSER}[password]

AddFirstName
    Input Text    name=first_name    Robotti

AddLastName
    Input Text    name=last_name    Testaaja

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

### Adding project process ###

AddProjectInfo
    Input Text    name=title    ${AddedProject}

CheckProjectAdded
    Page Should Contain    ${AddedProject}

### Editing project process ###

EditProjectInfo
    Input Text    name=title    ${EditedText}

AddUserToProjectLocalhost
    Input Text    name=username    ${UsernameLocal}

AddUserToProjectHeroku
    Input Text    name=username    ${UsernameHeroku}

### Adding entry process ###

ChooseProjectName
    Click Element    xpath=//*[@id="mui-component-select-project"]

ChooseFirtsProjectName
    Click Element    xpath=//*[@id="menu-project"]/div[3]/ul/li[1]

AddComment
    Input Text    name=comment    ${CommentText}

### Editing entry process ###

EditEntryInfo
    Input Text    name=entry    ${EditedText}

### Editing user details

FirstNameEdit
    Input Text    name=first_name    1

PasswordAdd
    Input Text    name=password_hash    ${TESTUSER}[password]

### Navigating on the page ###

ClickProjects
    Click Link    Projektit

ClickEntries
    Click Link    Tuntikirjaukset

ClickLogOut
    Click Link    Kirjaudu ulos

ClickHomePage
    Click Link    Etusivu

ClickUserDetails
    Click Link    Omat tiedot
### Page contain ###

SuccessfullRegistration
    Wait Until Page Contains    Käyttäjä rekisteröity onnistuneesti

ProjectDeletionWarning
    Page Should Contain    Haluatko varmasti poistaa kyseisen projektin?

EntryDeletionWarning
    Page Should Contain    Vahvista työaikakirjauksen poisto.

SuccessfulDeletion
    Wait Until Page Contains    Kirjaus poistettu onnistuneesti

ProjectSuccessfulDeletion
    Wait Until Page Contains    ${ProjectDeletedSuccessfully}

SuccessfulLogOut
    Page Should Contain    Sinut on kirjattu ulos

SuccessfullEdition
    Page Should Contain    ${KirjausOnnistui}

SuccessfullProjectEdition
    Page Should Contain    ${ProjectEditedSuccessfully}

EntryCommentOnThePage
    Page Should Contain    ${CommentText}

UserInfoSavedSuccessfully
    Page Should Contain    Tiedot tallennettu onnistuneesti
