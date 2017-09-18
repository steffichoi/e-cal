# e-cal

Building a calendar app using Android Studio.

## Basic Functionality
IMPLEMENTED
- check for the current date by displaying a calendarView in the main acvitity
- preview of daily schedule on the side bar to the right of the app's main activity based on date picked in the calendar
- use of drawer navigator
- use of floating action button to add tasks/events to your calendar
- view of daily schedule when date in side bar is clicked
- view of activity details when one item in daily schedule is clicked on
- daily schedule fragment can change based on date picked in side bar
- need to implement the same functionality for today tasks in sidebar
- swipe an item to delete, snackbar popup confirming action; allows user to undo action
- view event detail from clicking on event in sidebar 
- date time picker for adding tasks with constraints on start and end time
- location view in maps, on click location icon in add task fragment
- spinner for add item type and set reminder
- added custom reminder option; used date time picker
- dont allow user to set reminder for empty event
- reconfigured sqlite db to store datetime as int (extract as long); added db instrumented testing
- detail item data extraction from db
- on edit item, put info into bundle to add task
- if editing, update db!
- view of weekly schedule through drawer navigator
- set reminder/alarm for reminder/notification


NOT IMPLEMENTED

- to do list in side bar showing to do items for this date
->// add task button present? or click on time slot?

- view of activity details when one item in weekly schedule is clicked
- can navigate to next week through drop down menu as header
- navigate to daily view when date column clicked on
->// add task button present? or click on time slot? (liking time slot idea more,  but need to implement time slots)

- view of monthly schedule through drawer navigator
- dots in date box show if there is an item scheduled for that day

- view of to do list through drawer navigator
- need to design layout for the to do list 
- shopping list?

- filtering out all items in scheduled in calendar based on filter type in drawer (not sure if i want to get rid of this feature?)

- view account details when user icon in drawer navigator is clicked on

!!! IMPORTANT !!!
- set up database in the cloud/server !!!

PART 2 stuff to do when done with basic functionalities
- incorporation of scheduler project
- add option in drawer navigator
- list of tasks that have not been assigned and option to assign
- make a list of tasks to assign
- put parameters into program
- refactor program


NEED TO DO
- review code in adapters. should be able to condense
- use string resources to clean up code


BUGSS!!!
- add task time. needs to be 24 hr...? or something on that page is broken

REQUEST:
(enrico)
when making an event, let me add other people in my contacts list to it and automatically send an email asking for their rsvp