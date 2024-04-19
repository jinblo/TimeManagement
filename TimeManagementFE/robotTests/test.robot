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
Login to the TimeManagement service
    [Tags]    localhost    test
    OpenBrowserLocalhost
    LogInWithCredentials
    LogInbutton
    PageContainHei

Adding a new project after logged in
    [Tags]    postproject    localhost
    ClickProjects
    AddProjectButton
    AddProjectInfo
    SaveButton
    CheckProjectAdded

# Muutos kohdistuu ensimmäiseen riviin

Editing an existing project
    [Tags]    putproject    localhost
    EditButton
    EditProjectInfo
    SaveAllChangesButton
    SuccessfullEdition

# Valitsee listan ensimmäisen projektin

Adding a new entry to RobotTest project
    [Tags]    postentry    test    localhost
    ClickEntries
    AddEntryButton
    Click Element    xpath=//*[@id="mui-component-select-project"]
    Click Element    xpath=//*[@id="menu-project"]/div[3]/ul/li[1]
    Input Text    name=comment    CommentTest
    SaveButton
    Page Should Contain    CommentTest

# nyt ensimmäisen rivin kommentin Muokkaa

Editing an existing entry
    [Tags]    putentry
    ClickEntries
    EditButton
    EditEntryInfo
    SaveButton
    SuccessfullEdition

# poistaa ensimmäisen enrtyn

 Deleting the first entry
    [Tags]    deleteentry
    ClickEntries
    DeleteButton
    EntryDeletionWarning
    DeleteEntryButton
    SuccessfulDeletion

# poistaa ensimmäisen projektin

Deleting a project
    [Tags]    deleteproject    heroku    localhost
    ClickProjects
    DeleteButton
    ProjectDeletionWarning
    DeleteProjectButton
    SuccessfulDeletion

Testing logging out function
    [Tags]    logout    heroku    localhost
    ClickLogOut
    SuccessfulLogOut

Closing Browser
    Close Browser
