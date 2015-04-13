
Android Web Telephony Integration (WTI)
==================================================

Web Telephony Integration (WTI) is like Computer Telephony Integration (CTI),
but for the Web ;-)

This is a proof of concept (PoC) of how to integrate mobile phones
into a web application. This solves many problems.
For example, it enables [CRM](http://en.wikipedia.org/wiki/Customer_relationship_management)
users to quickly call a customer via a single click in the browser/web app.
Or vice versa - every incoming call may automatically open a popup displaying the caller contact data.
This proof of concept makes use of your existing Internet infrastructure
and uses cheap realtime app platform solutions to facilitate phone based workflows.

This demo app is based on Android, but can be ported to iOS too.

This PoC is powered by [Firebase - THE REALTIME APP PLATFORM](https://www.firebase.com/)


##### License

[![License](https://img.shields.io/:license-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)


Screenshot
---------------------------------------------------

##### Web, single page app

![Screenshot Web app](/screenshots/web.png?raw=true)

##### Android app

![Screenshot Android app](/screenshots/app-small.png?raw=true)


How it works
---------------------------------------------------

A simple architecture diagram may be worth than 1000 words ... 

![How it works in a simple architecture diagram](/docs/how_it_works.png?raw=true)

There are five important things, to make this magic work:

1. Firebase provides a shared document (JSON) based
2. The Andriod app connects to Firebase and registers callback handlers on certain nodes 
3. The Web app connects to Firebase and registers callback handlers on certain nodes
4. Via reading and writing document nodes, both apps communicate with each other
5. All publishing and subscribing of changes events is handled within the Firebase clients.

These use case do work in this proof of concept:

* Display incoming calls
* Lookup incoming phone number in phone book an display photo
* Initiate outgoing call


Technology used
---------------------------------------------------

### Android App
* Android SDK, Target 4.2.2 (API v17)
* Firebase Java Client v1.0.6 (maybe outdated version but still works ;-), see https://www.firebase.com/
* Ant build tool

### Web App
* jQuery 2.0.3
* Firebase.js (directly from their CDN)


Community & Badges
--------------------

If you like this project, endorse please: [![endorse](https://api.coderwall.com/nitram509/endorsecount.png)](https://coderwall.com/nitram509)