				Findora Event Application 

Findora is a local event-finding Android application that helps users discover, host, and manage events in their area. With features like proximity-based notifications, event ticket management, and customizable settings, Findora aims to enhance the experience of event exploration and creation. 


Features

	1.Event Searching: Users can find local events based on proximity, keywords, and categories.
	2.Event Hosting: Event organizers can post events and and manage ticketing.
 USER DEFINED FEATURE # 1 (Google maps integration + location auto-complete feature )
	3.Google maps integration to find users current location and events location and Google places integration for location auto-complete.
 USER DEFINED FEATURE # 2 (Dark mode and Light mode)
	4.Customizable Settings: Themes, language preferences, privacy settings, and push notifications.
	5.Single Sign-On (SSO): Supports SSO and support will suppobiometric authentication for easy and secure login.
 USER DEFINED FEATURE # 3 (Image browsing)
        6.Image browsing integration giving users to upload to events a more detailed appearence.
        7.Real-Time Notifications: Receive updates and reminders about upcoming events.
        8.Local Deals & Promotions: Discover promotions and special deals associated with nearby events.

Project Structure

	- app/: Contains the main Android source code and resources.
	- gradle/: Gradle wrapper files for project build management.
	- google-services.json: Firebase configuration for push notifications and authentication.
	- secrets.properties: Contains secrets for API keys and other sensitive information.

					
					Getting Started 

Prerequisites

	- Android Studio (latest version)
	- Java Development Kit (JDK) 11
	- Kotlin
	- Firebase Account (for notifications and authentication) 

Installing

	> Clone the repository:
	> Open the project in Android Studio.
	> Ensure you have the necessary Firebase services set up and replace the google-	services.json file with your own if needed.
	> Build the project using Gradle

Build Tools

	> Gradle: Used for building and managing dependencies.
	> Kotlin: Primary programming language for the Android app.
	> Firebase: Provides real-time notifications, authentication, and analytics.

 API tools:
2 Difference sites were used to deploy our APIs
Deployed base url link for Railway : https://workingnodefandoraapi-production.up.railway.app  --curently used for the app
Deployed base url link for Render : https://findoraandriodapp-xyft.onrender.com   --works but not used due to speed

If none of the deployed URLs work you can run API locally use: http://10.0.2.2:4659 
with API GitHub link : https://github.com/NPMpayela/WorkingNodeFandoraApi   -run commands "npm install"  and  "npm run dev"

How to Use Findora

1. Signing Up/Logging In
	> When you open the app, you can sign up with an email address, or log in using your Single Sign-On (SSO) credentials (Google, Facebook, etc.).
        > While using email and password to register you will see a "Welcome <email>" message , the go back to login page then login with your registered account.

 > Create new account OR use Login  Email : " demoemail.com "
                                     Password : " find123 "

	> Biometric authentication (fingerprint/face recognition) will be enabled (POE) for a faster 	and more secure login experience after the first login.

2. Creating Your Profile
	> After logging in, you can set up your profile by adding personal details like your 	name, a profile picture, and your preferred event categories (e.g., music, tech, 	sports).
	> You can adjust privacy settings to control what information is visible to other users, 	such as whether your profile appears in event guest lists. 

3. Finding Events
	> On the main screen, you'll see a list of upcoming events based on your current 	location and preferences.
	> Use the search bar at the top to find specific events by keywords or categories.
	> Enable location services to find events near your area. You can also set a specific 	location manually.
	> Use filters to narrow down events by category, date, or proximity.
 
 3.1. Using Map feature
      > On the Bottom of the Home page navigate to the google maps icon.
      > Accept permissions and start using the map.
      
4. Exploring Event Details

	> Tap on any event to view more details, including:
		>Event location and time
		> Host information
		> Ticket availability
		> Description and event activities
		> You can choose to save the event for later, share it with friends, or RSVP to get a ticket.

5. Hosting an Event
	> If you're an event organizer, go to the Host Event section.
	> Fill in the event details, including the event name, date, location, description, ticket prices (if applicable), and the maximum number of attendees.
	> You can upload images and promotional materials to attract attendees.
	> Once published, your event will be visible to users in the area, and you can monitor 	RSVPs and ticket sales in real-time. 

6. Customizing Settings
	> Go to Settings to customize the app's interface and behavior:
		> Choose between dark mode and light mode.
		> Set your preferred language.
		> Enable or disable push notifications for upcoming events, changes, or promotions.
		>Manage your location and notification preferences, such as whether to receive alerts for nearby events.
 
7. Receiving Notifications
	> The app will notify you about:
		> Events happening near your location.
		> Reminders for saved and RSVP’d events.
		> Last-minute changes or updates from event organizers.
		> Promotions and deals for local businesses hosting or sponsoring events.

8. Logging Out
	> To log out, go to the Settings menu and scroll to the bottom to find the Log Out button.


License
This project is licensed under the MIT License - see the LICENSE file for details.
