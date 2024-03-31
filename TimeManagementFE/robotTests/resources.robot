*** Settings ***
Library                 SeleniumLibrary   15.0   5.0

*** Keywords ***
SaveButton
    Click button        Tallenna

LogInWithCredentials
    Input Text          username            ${TESTUSER}[username]
    Input Text          password            ${TESTUSER}[password]

*** Variables ***
${Browser}              Chrome
${URL}                  http://localhost:5173/login
${AddedProject}         RobotTest
${EditedText}           Edited
${EditedProject}        RobotTestEdited
${AddedEntry}           RobotTestEntry
${EditedEntry}          RobotTestEntryEdited
&{TESTUSER}             username=new_user1   password=AppUser1