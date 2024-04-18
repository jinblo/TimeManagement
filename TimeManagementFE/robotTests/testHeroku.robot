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
# Mitä vielä uupuu: entryn testaus
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
    [Tags]    postproject
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

# Ei pääse kiinni select osioon
# Adding a new entry to RobotTest project
#    [Tags]    postEntry
#    ClickEntries
#    AddEntryButton
#    Select From List By Index    name:project    1
#    Click Element    //MenuItem[contains(text(), 'RobotTest')]
#    Input Text    name=comment    CommentTest
#    SaveButton
#    Page Should Contain    CommentTest

# nyt ensimmäisen rivin kommentin Muokkaa
# Editing an existing entry
#    [Tags]    putEntry
#    ClickEntries
#    EditButton
#    EditEntryInfo
#    SaveButton
#    SuccessfullEdition

# poistaa ensimmäisen enrtyn
# Deleting the first entry
#    [Tags]    deleteEntry
#    ClickEntries
#    DeleteButton
#    EntryDeletionWarning
#    DeleteEntryButton
#    SuccessfulDeletion

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