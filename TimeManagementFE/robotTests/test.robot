*** Comments ***
Robotframerowk test cases for localhost

To perform testing, you need the following installations on your own machine: Python and pip
Install robotframework on your machine using pip command: pip install robotframework
Install the SeleniumLibrary library: pip install robotframework-seleniumlibrary

Start both the backend and frontend before running tests
Run the tests using the command: robot test.robot OR python -m robot test.robot
Run tests with a specific tag: robot -i test test.robot


*** Settings ***
Documentation       Localhost: Tests to check login and REST functions in project and entry

Library             SeleniumLibrary    15.0    5.0
Resource            resources.robot


*** Test Cases ***
Registerin a new user
    [Tags]      register
    Open Browser and Navigate to Localhost
    Click Register Button
    Add Username to Registration Input Section
    Add Password to Registration Input Section
    Add First Name to Registration Input Section
    Add Last Name to Registration Input Section
    Click Save Button
    Wait Page Contain Successfull Registration
    # sleep    2s

Login to the TimeManagement service
    [Tags]     test    role    user
    # Open Browser and Navigate to Localhost
    Login With Localhost Test User Credentials
    Click Login Button
    Page Contain Text Hei

Adding a new project after logged in
    [Tags]    postproject    localhost    test
    Navigate to Projects Page
    Click Add Project Button
    Add Title to Project Input Section
    Click Save Button
    Page Contain Added Project

Editing an existing project title
    [Documentation]    Editing the title of the project
    [Tags]    putproject    
    Click Edit Button
    Edit Project Title
    Click Save All Changes Button
    Page Contain Project Successful Edition

Adding a user to the project with role user
    [Documentation]    User will have the role user
    [Tags]    putproject    
    # Navigate to Projects Page
    Click Edit Button
    Add Username to Project input section in Localhost
    Click Find Username Button
    Click Save All Changes Button
    Page Contain Project Successful Edition

Editing a user role from user to viewer
    [Tags]    putproject   
    # Navigate to Projects Page
    Click Edit Button
    Choose the Second User from the List
    Choose the Viewer Role from the List
    Sleep    1s
    Click Save All Changes Button
    Page Contain Project Successful Edition

Deleting the user from the project
    [Tags]    dleteproject    localhost    role
    # Navigate to Projects Page
    Click Edit Button
    Choose the Option to remove the Second User from the Project
    Click Save All Changes Button
    Page Contain Project Successful Edition

Checking that Project don't have other users
    [Tags]    test
    Click Edit Button
    Page Contain Does not have Other Users
    Click Cancel Button 

Adding a new entry to RobotTest project
    [Tags]    postentry    localhost
    Navigate to Entries Page
    Click Add Entry Button
    Choose Project for Added Entry
    Choose First Project Name from the List
    Add Comment to Entry Input Section
    Click Save Button
    Page Contain Added Project

Editing an existing entry
    [Documentation]    Editing entry's comment
    [Tags]    putentry
    # Navigate to Entries Page
    Click Edit Button
    Edit Entry's comment
    Click Save Button
    Page Contain Entry Successful Saved

 Deleting the first entry
    [Tags]    deleteentry
    # Navigate to Entries Page
    Click Delete Button
    Page Contain Entry Deletion Warning
    Click Delete Entry Button
    Wait Page Contain Successful Deletion
    # Sleep    2s

Deleting a project
    [Tags]    deleteproject    localhost    test
    Navigate to Projects Page
    Click Delete Button
    Page Contain Project Deletion Warning
    Click Delete Project Button
    Wait Page Contain Project Successful Deletion
    # Sleep    2s

Editing user's own derails
    [Documentation]    Adding a character 1 at the end of the first name
    [Tags]    user
    Navigate to User Detail's Page
    Click Edit Button
    Edit User's First Name
    Add Test User's Current Password to Input Section
    Click Save Button
    Page Contain User Info Saved Successfully
    # Sleep    2s

Testing logging out function
    [Tags]    logout    localhost    test
    Navigate to Logout Page
    Page Contain Successful Logout

Closing Browser
    Close Browser
