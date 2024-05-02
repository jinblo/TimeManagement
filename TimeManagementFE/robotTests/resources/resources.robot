*** Settings ***
Library     SeleniumLibrary    15.0    5.0


*** Variables ***
${Browser}                          Chrome
${URL-localhost}                    http://localhost:5173/TimeManagement/login
${URL-github}                       https://teamred-ohjelmistoprojekti2.github.io/TimeManagement/
${AddedProject}                     RobotTest
${EditedText}                       Edited
${EntrySavedSuccessfully}           Kirjaus tallennettu onnistuneesti
${ProjectSavedSuccessfully}         Projekti tallennettu onnistuneesti
${ProjectDeletedSuccessfully}       Projekti poistettu onnistuneesti
${UsernameLocal}                    new_user2
${UsernameHeroku}                   RobotFriend
${CommentText}                      CommentText
&{TESTUSER}                         username=testi    password=AppUser1
&{TESTUSERHEROKU}                   username=RobotTest    password=RobotTest


*** Keywords ***
Add Comment to Entry Input Section
    Input Text    name=comment    ${CommentText}

Add First Name to Registration Input Section
    Input Text    name=first_name    Robotti

Add Last Name to Registration Input Section
    Input Text    name=last_name    Testaaja

Add Password to Registration Input Section
    Input Text    name=password_hash    ${TESTUSER}[password]

Add Test User's Current Password to Input Section
    Input Text    name=password_hash    ${TESTUSER}[password]

Add Title to Project Input Section
    Input Text    name=title    ${AddedProject}

Add Username to Registration Input Section
    Input Text    id=usernameReg    ${TESTUSER}[username]

Add Username to Project input section in Localhost
    Input Text    name=username    ${UsernameLocal}

Add Username to Project input section in Heroku
    Input Text    name=username    ${UsernameHeroku}

Choose First Project Name from the List
    Click Element    xpath=//*[@id="menu-project"]/div[3]/ul/li[1]

Choose Project for Added Entry
    Click Element    xpath=//*[@id="mui-component-select-project"]

Choose the Second User from the List
    Click Element    xpath=/html/body/div[4]/div[3]/div/div/table/tbody/tr[2]/td[2]/div/div

Choose the Viewer Role from the List
    Click Element    xpath=//li[contains(text(), 'Seuraaja')]

Choose the Option to remove the Second User from the Project
    Click Element    xpath=/html/body/div[4]/div[3]/div/div/table/tbody/tr[2]/td[3]/span/input

Click Add Entry Button
    Click Button    Lisää uusi kirjaus

Click Add Project Button
    Click Button    Lisää uusi projekti

Click Cancel Button
    Click Button    Peruuta

Click Delete Button
    Click Button    Poista

Click Delete Entry Button
    Click Button    Poista kirjaus

Click Delete Project Button
    Click Button    Poista projekti

Click Edit Button
    Click Button    Muokkaa

Click Edit Info Button
    Click Button    Muokkaa tietoja


Click Find Username Button
    Click Button    Hae käyttäjä

Click Login Button
    Click Button    Kirjaudu

Click Register Button
    Click Button    Rekisteröidy

Click Save All Changes Button
    Click Button    Tallenna kaikki muutokset

Click Save Button
    Click Button    Tallenna

Edit Entry's comment
    Input Text    name=entry    ${EditedText}

Edit Project Title
    Input Text    name=title    ${EditedText}

Edit User's First Name
    Input Text    name=first_name    1

Login With Heroku Test User Credentials
    Input Text    username    ${TESTUSERHEROKU}[username]
    Input Text    password    ${TESTUSERHEROKU}[password]

Login With Localhost Test User Credentials
    Input Text    username    ${TESTUSER}[username]
    Input Text    password    ${TESTUSER}[password]

Navigate to Entries Page
    Click Element   xpath=//*[@id="root"]/div/header/div/div[2]/button[3]

Navigate to Logout Page
    Click Element   xpath=//*[@id="root"]/div/header/div/div[4]/button[2]

Navigate to Projects Page
    Click Element    xpath=//*[@id="root"]/div/header/div/div[2]/button[2]

Navigate to User Detail's Page
    Click Element   xpath=//*[@id="root"]/div/header/div/div[4]/button[1]

Open Browser and Navigate to GitHub Page
    [Documentation]    Using deployed url for loginin
    Open Browser    ${URL-github}    ${Browser}

Open Browser and Navigate to Localhost
    [Documentation]    Using localhost url
    Open Browser    ${URL-localhost}    ${Browser}

Page Contain Added Project
    Page Should Contain    ${AddedProject}

Page Contain Entry Deletion Warning
    Page Should Contain    Vahvista työaikakirjauksen poisto.

Page Contain Entry Successful Saved
    Page Should Contain    ${EntrySavedSuccessfully}

Page Contain Does not have Other Users
    Page Should Contain    Projektille ei ole vielä lisätty muita käyttäjiä

Page Contain Heroku Test User's name
    [Documentation]    Checking that showing first and last names
    Page Should Contain    Hei Robot Framework

Page Contain Project Deletion Warning
    Page Should Contain    Haluatko varmasti poistaa kyseisen projektin?

Page Contain Project Successful Edition
    Page Should Contain    ${ProjectSavedSuccessfully}

Page Contain Successful Logout
    Page Should Contain    Sinut on kirjattu ulos

Page Contain Text Hei
    Page Should Contain    Hei

Page Contain User Info Saved Successfully
    Page Should Contain    Tiedot tallennettu onnistuneesti

Wait Page Contain Project Successful Deletion
    Wait Until Page Contains    ${ProjectDeletedSuccessfully}

Wait Page Contain Successful Deletion
    Wait Until Page Contains    Kirjaus poistettu onnistuneesti

Wait Page Contain Successfull Registration
    Wait Until Page Contains    Käyttäjä rekisteröity onnistuneesti
