*** Comments ***
Robotframerowk test cases for Heroku

To perform testing, you need the following installations on your own machine: Python and pip
Install robotframework on your machine using pip command: pip install robotframework
Install the SeleniumLibrary library: pip install robotframework-seleniumlibrary

Run the tests using the command: robot testHeroku.robot OR python -m robot testHeroku.robot
Run tests with a specific tag: robot -i test testHeroku.robot

Uupuu testit koskien käyttäjän lisäystä, roolin päivitystä sekä poistoa
Uupuva hienosaaäntö: muokkaukset ja poistot koskemaan juuri lisättyä asiaa


*** Settings ***
Documentation       Deployment: Tests to check login and REST functions in project and entry

Library             SeleniumLibrary    15.0    5.0
Resource            resources.robot


*** Test Cases ***
Login to Heroku and GitHub Page
    [Tags]    test
    Open Browser and Navigate to GitHub Page
    Login With Heroku Test User Credentials
    Click Login Button
    Page Contain Heroku Test User's name

Adding a new project after logged in
    [Tags]    postproject    test
    Navigate to Projects Page
    Click Add Project Button
    Add Title to Project Input Section
    Click Save Button
    Page Contain Added Project

Editing an existing project
    [Tags]    putproject
    Click Edit Button
    Edit Project Title
    Click Save Button
    Page Contain Entry Successful Saved

Adding a new entry to RobotTest project
    [Tags]    postentry    test
    Navigate to Entries Page
    Click Add Entry Button
    Choose Project for Added Entry
    Choose First Project Name from the List
    Add Comment to Entry Input Section
    Click Save Button
    Page Contain Entry Successful Saved

Editing an existing entry
    [Tags]    putentry
    Navigate to Entries Page
    Click Edit Button
    Edit Entry's comment
    Click Save Button
    Page Contain Entry Successful Saved

Deleting the first entry
    [Tags]    deleteentry
    Navigate to Entries Page
    Click Delete Button
    Page Contain Entry Deletion Warning
    Click Delete Entry Button
    Wait Page Contain Successful Deletion

Deleting a project
    [Tags]    deleteproject
    Navigate to Projects Page
    Click Delete Button
    Page Contain Project Deletion Warning
    Click Delete Project Button
    Wait Page Contain Successful Deletion

Testing logging out function
    [Tags]    logout
    Navigate to Logout Page
    Page Contain Successful Logout

Closing Browser
    Close Browser
