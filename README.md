# Event Management System

A Java Swing + MySQL desktop application for managing events, venues, sponsors, organizers, and visitors, built with a normalized relational database and role-based access control.

![Java](https://img.shields.io/badge/Java-Swing-orange?logo=java)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue?logo=mysql)
![JDBC](https://img.shields.io/badge/JDBC-Connector-informational)

## Overview

This system manages the core pieces of running an event: staff, sponsors, venues, tickets, and participants, all linked through a relational database.

- **Organizers** (staff) plan and run events based on their assigned task
- **Venues** host events, with capacity, address, and contact info
- **Sponsors** fund events with a specified budget
- **Visitors** purchase tickets to attend events
- **Participants** take part in events

The system has two access levels: **Organizer View** and **Visitor View**, each with different permissions.

## Database Design

The system is backed by a normalized relational database, designed through an ER diagram and relational schema.

**Core entities:**

| Entity | Key Attributes |
|---|---|
| EVENT | Event_ID, Name, Description, Capacity, Start/End Time, Fund_Budget |
| ORGANIZER | Badge_ID, First/Last Name, Task |
| VENUE | Venue_ID, Name, Address, Phone, Email |
| SPONSOR | Sponsor_ID, Name, Website |
| PARTICIPANT | Participant_ID, Name, Phone |
| VISITOR | Visitor_ID, Name, Phone, Ticket_No |
| TICKET | Ticket_Num, Price, Start/End Date |

**Relationships:** ORGANIZED_BY, HOSTED_AT, FUNDED_BY, PARTICIPATES_IN, SOLD_BY, PURCHASES

See the `/docs` folder for the full ER diagram and relational schema.

## User Roles

**Organizer**
- Add, update, and delete events and participants
- Run single-table, multi-table, and aggregate queries
- View sponsor, venue, and budget details for each event

**Visitor**
- Buy, update, or cancel their own tickets
- View their own profile and ticket history
- Cannot access organizer, sponsor, or other visitors' data

## Tech Stack

- **Language:** Java (Swing for GUI)
- **Database:** MySQL
- **Connectivity:** JDBC (MySQL Connector/J)
- **IDE:** Eclipse

## Setup

1. Clone the repo
   ```bash
   git clone https://github.com/your-username/event-management-system.git
   ```

2. Create a MySQL database named `event` and run the SQL scripts in `/database`.

3. Update credentials in `MySQLConnection.java`:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/event";
   private static final String USER = "root";
   private static final String PASSWORD = "your_password";
   ```

4. Add the [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) JAR to your project's build path.

5. Run `LoginFrame.java`, choose Organizer or Visitor, and log in.

## Team

Built for CSC380 — Fundamentals of Database Systems, King Saud University.

- Anfal Alobaied
- Danah Alsawat
- Shaden Alnoman
- Maha Bin Dakhil
