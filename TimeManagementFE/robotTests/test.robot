*** Comments ***
# Robotframerowk test cases for localhost
#
# *** HUOM SISÄINEN MUISTIINPANO ***
# Testien tekemistä varten tarvitaan omalle koneelle asennukset: Python ja pip
# Asenna robotframework koneellesi pip:illä komennolla: pip install robotframework
# Asenna SeleniumLibrary kirjasto: pip install robotframework-seleniumlibrary
#
# ** Ennen kuin projekti on julkaistu: käynnistä sekä BE että FE ennen testejä **
# Tee testit komennolla: robot test.robot TAI python -m robot test.robot
# Testi jossa tietty tagi: robot -i test test.robot
#
# Uupuu testit koskien käyttäjän lisäystä, roolin päivitystä sekä poistoa
# Uupuva hienosaaäntö: muokkaukset ja poistot koskemaan juuri lisättyä asiaa


*** Settings ***
Documentation       Localhost: Tests to check login and REST functions in project and entry

Library             SeleniumLibrary    15.0    5.0
Resource            resources.robot


*** Test Cases ***
## Rekisteröidään uusi käyttäjä
Registerin a new user
    [Tags]    localhost    register
    OpenBrowserLocalhost
    RegisterButton
    AddUserName
    AddPassword
    AddFirstName
    AddLastName
    SaveButton
    SuccessfullRegistration
    sleep    2s

## Kirjaudutaan sisään uutena käyttäjänä

Login to the TimeManagement service
    [Tags]    localhost    test    role    user
    # OpenBrowserLocalhost
    LogInWithCredentials
    LogInbutton
    PageContainHei

## Lisätään käyttäjälle projekti

Adding a new project after logged in
    [Tags]    postproject    localhost    test
    ClickProjects
    AddProjectButton
    AddProjectInfo
    SaveButton
    CheckProjectAdded

## Muokataan lisättyä projektia

Editing an existing project title
    [Tags]    putproject    localhost
    EditButton
    EditProjectInfo
    SaveAllChangesButton
    SuccessfullProjectEdition

## Lisätään projektille käyttäjä ja hänelle rooli user

Adding a user to the project with role user
    [Tags]    putproject    localhost    test
    #ClickProjects
    EditButton
    AddUserToProjectLocalhost
    FindUserButton
    SaveAllChangesButton
    SuccessfullProjectEdition

## Päivitetään käyttäjän rooli userista vieweriksi

Editing a user role from user to viewer
    [Tags]    putproject    localhost    test    role
    #ClickProjects
    EditButton
    Click Element    xpath=/html/body/div[2]/div[3]/div/div/table/tbody/tr[2]/td[2]/div/div
    Click Element    xpath=//li[contains(text(), 'Viewer')]
    Sleep    2s
    SaveAllChangesButton
    SuccessfullProjectEdition

## Poistetaan projektilta lisätty käyttäjä

Deleting the user from the project
    [Tags]    dleteproject    localhost    test    role
    #ClickProjects
    EditButton
    Click Element    xpath=/html/body/div[2]/div[3]/div/div/table/tbody/tr[2]/td[3]/span/input
    SaveAllChangesButton
    SuccessfullProjectEdition
    Sleep    2s

## Lisätään projektille uusi kirjaus

Adding a new entry to RobotTest project
    [Tags]    postentry    localhost
    ClickEntries
    AddEntryButton
    ChooseProjectName
    ChooseFirtsProjectName
    AddComment
    SaveButton
    EntryCommentOnThePage

## Muokataan kirjauksen kommenttia

Editing an existing entry
    [Tags]    putentry
    #ClickEntries
    EditButton
    EditEntryInfo
    SaveButton
    SuccessfullEdition

## Poistetaan kyseinen kirjaus

 Deleting the first entry
    [Tags]    deleteentry
    # ClickEntries
    DeleteButton
    EntryDeletionWarning
    DeleteEntryButton
    SuccessfulDeletion
    Sleep    2s

## Poistetaan kyseinen projekti

Deleting a project
    [Tags]    deleteproject    localhost    test
    ClickProjects
    DeleteButton
    ProjectDeletionWarning
    DeleteProjectButton
    ProjectSuccessfulDeletion
    Sleep    2s

## Muokataan käyttjän omia tietoja

Editing own user derails
    [Tags]    user
    ClickUserDetails
    EditButton
    FirstNameEdit
    PasswordAdd
    SaveButton
    UserInfoSavedSuccessfully
    Sleep    2s

## Kirjaudutaan ulos

Testing logging out function
    [Tags]    logout    localhost    test
    ClickLogOut
    SuccessfulLogOut

## Suljetaan selain

Closing Browser
    Close Browser
