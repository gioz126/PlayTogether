# Play Together

## What will the application do?
I want to make an application where users can **book sport courts** (mainly racket sport, such as badminton), **open play** (users can play together with other users that uses the application), **create / join sport community** (users can create sport community based on its' sport and other users can join the community). 

To be able to book sport courts, we need owners of the court (cooperate with courts' owners). If this works well, I will get an admin-fee for users who book courts through the application.

## Who will use it?
1. Users who have **hobby of playing racket sports** and are **lazy to call** the courts' owner/management.
2. Users who **wants to play** racket sport but **don't have any friends to play with**.

## Why is this project interest you
I am interested with this project because I am fond of playing badminton but hard to get court since I need to call the court management team first to book the court and I am an university student whose time is pretty limited. Furthermore, since moving to Canada, I don't really have much friends to play badminton with, therefore with one of the application's function to have a community / join a game, I can join a community / join a game in which I am interested in (in this case, badminton community / badminton game).

## Bonus functions that might be added
* Competition
* Sparring

## User Stories
- As a users, I want to be able to book court for badminton so that I don't have to call the court management team.
- As a users, I want to be able to open play session so that other users can join my play session.
- As a users, I want to be able to see available play sessions and join it.
- As a users, I want to be able to open and join community based on my sports' interest.
- As a users, I want to have the option to save my bookings, communities joined, and sessions joined.
- As a users, I want to have the option to load/see my bookings, communities joined, and sessions joined.


## Instruction for End User
- At start, the user will be greeted with PlayTogether app logo. After that, the user will be prompted to load data from previous session or not. **Do note that if there is no saved data, the app will close.**
- User will then need to enter their name, phone number, and select sport interest. If the user input same name that has been created from previous session, it will log the user back in with all data from previous session.
- User will then be shown with 3 main buttons which are `Bookings, Sessions, and Communities`.
- If user pressed `Booking` button, it will display 3 sub buttons which are `Book Court, View My Bookings, and Refresh`.
- For `Book Court` button, it will prompt the user to select facility , date, time and year to book a court. After user successfully booked the court, it will display the booking to the *text area*.
- For `View My Bookings` button, it will show all bookings that has been made by the user.
- For `Refresh` button, it will refresh the text area to show all bookings that has been made by the user.
- If user pressed `Sessions` button, it will display 4 sub buttons which are `Create Session(from booking), Join Session, Leave Session, and Refresh`.
- For `Create Session(from booking)` button, it will prompt the user to select which bookings does the user wants to make it into a *session*. After successfully creating a *session*, it will then display all sessions the user have created or joined.
- For `Join Session` button, it will prompt the user which sport sessions does the user wants to join, after that, the user will need to select from all available sessions that matches the user's chosen sport to join. After successfully joining a session, it will display all sessions the user have created or joined.
- For `Refresh` button, it will refresh the text area so that it shows all created/joined sessions that the user is connected with.
- If user pressed `Communities` button, it will display 9 sub buttons which are `Create Community, View All, Search By Sport, Search By Location, Join Community, View My Communities, Leave Community, Remove(Leader), and Refresh`.
- For `Create Community` button, it will prompt the user to input the community's name that the user wants to create, ask the user to select which sport is this community interested in, ask the user to select which location is this community based in, and ask the user to select maximum number of members. After successfully creating a community, it will display all active communities that are available in the app.
- For `View all` button, it will show all active communities that are available in the app.
- For `Search By Sport` button, it will prompt the user to select which sport does the user wants to filter all active communities with. If found, it will shows all active communities that are already filtered out by the user's chosen sport.
- For `Search By Location` button, it will prompt the user to select which location does the user wants to filter all active communities with. If found, it will show all active communities that are already filtered out by the user's chosen location.
- For `Join Community` button, it will prompt the user to select a community that the user wants to join from all active communities that are available in the app. After successfully joined a community, it will then show all communities that the user has joined or created.
- For `View My Communities` button, it will show all communities that the user has joined or created.
- For `Leave Community` button, it will prompt the user to select which community does the user wants to leave. After successfully leaving a community, it will then show all communities that the user has joined or created.
- For `Remove(Leader)` button, it will prompt the user to select which community does the user wants to remove/close. After successfully removing the community, it will then show all communities that the user has joined or created.
- For `Refresh` button, it will refresh the page to show all active communities that are available in the app.
- User can then press `file` at the top left of the panel to `save, load, and exit the app`. The app will also ask the user again to save/not save the app when pressing exit button.
