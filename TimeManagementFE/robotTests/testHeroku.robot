*** Comments ***
# Robotframerowk test cases for Heroku
#
# *** HUOM SISÄINEN MUISTIINPANO ***
# Testien tekemistä varten tarvitaan omalle koneelle asennukset: Python ja pip
# Asenna robotframework koneellesi pip:illä komennolla: pip install robotframework
# Asenna SeleniumLibrary kirjasto: pip install robotframework-seleniumlibrary
#
# Tee testit komennolla: robot testHeroku.robot TAI python -m robot testHeroku.robot
# Testi jossa tietty tagi: robot -i test testHeroku.robot
#
# Uupuu testit koskien käyttäjän lisäystä, roolin päivitystä sekä poistoa
# Uupuva hienosaaäntö: muokkaukset ja poistot koskemaan juuri lisättyä asiaa


*** Settings ***
Documentation       Deployment: Tests to check login and REST functions in project and entry

Library             SeleniumLibrary    15.0    5.0
Resource            resources.robot


*** Test Cases ***
Login to Heroku and GitHub Page
    [Tags]    test
    OpenBrowserGitHUbPage
    LogInWithHeroku
    LogInbutton
    PageContainHeiRobotFramework

Adding a new project after logged in
    [Tags]    postproject    test
    ClickProjects
    AddProjectButton
    AddProjectInfo
    SaveButton
    CheckProjectAdded

# Muutos kohdistuu ensimmäiseen riviin

Editing an existing project
    [Tags]    putproject
    EditButton
    EditProjectInfo
    SaveButton
    SuccessfullEdition

Adding a new entry to RobotTest project
    [Tags]    postentry    test
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
    [Tags]    deleteproject
    ClickProjects
    DeleteButton
    ProjectDeletionWarning
    DeleteProjectButton
    SuccessfulDeletion

Testing logging out function
    [Tags]    logout
    ClickLogOut
    SuccessfulLogOut

Closing Browser
    Close Browser
