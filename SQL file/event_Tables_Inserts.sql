CREATE TABLE PARTICIPANT (
    Phone VARCHAR(15) NOT NULL,
    Participant_ID CHAR(10),
    PName VARCHAR(15),
    PRIMARY KEY (Participant_ID)
);
CREATE TABLE SPONSOR (
    Sponsor_ID CHAR(10),
    SName VARCHAR(15),
    Website VARCHAR(255),
    PRIMARY KEY (Sponsor_ID)
);
CREATE TABLE VENUE (
    Venue_ID CHAR(10),
    Venue_Name VARCHAR(15),
    Address VARCHAR(30),
    Venue_PhoneNum VARCHAR(15),
    Email VARCHAR(30),
    PRIMARY KEY (Venue_ID)
);
CREATE TABLE ORGANIZER (
    Badge_ID CHAR(10),
    Fname VARCHAR(10),
    Lname VARCHAR(12),
    task VARCHAR(15),
    PRIMARY KEY (Badge_ID)
);
CREATE TABLE EVENT (
    Event_ID CHAR(10),
    Event_Name VARCHAR(30),
    description VARCHAR(255),
    Capacity INT,
    Start_Time TIME,
    End_Time TIME,
    Fund_Budget CHAR(8),
    Spr_ID CHAR(10),
    Ven_ID CHAR(10),
    PRIMARY KEY (Event_ID),
    FOREIGN KEY (Spr_ID) REFERENCES SPONSOR(Sponsor_ID),
    FOREIGN KEY (Ven_ID) REFERENCES VENUE(Venue_ID)
);
CREATE TABLE TICKET (
    Ticket_Num INT,
    Price INT,
    Start_Date DATE,
    End_Date DATE,
    Tick_Ev_ID CHAR(10),
    PRIMARY KEY (Ticket_Num),
    FOREIGN KEY (Tick_Ev_ID) REFERENCES EVENT(Event_ID)
);

CREATE TABLE VISITOR (
    Visitor_ID CHAR(10),
    VName VARCHAR(15),
    VPhone VARCHAR(15) NOT NULL,
    Tick_No INT,
    PRIMARY KEY (Visitor_ID),
    FOREIGN KEY (Tick_No) REFERENCES TICKET(Ticket_Num)
);

CREATE TABLE ORGANIZED_BY (
    Ba_ID CHAR(10),
    Org_Ev_ID CHAR(10),
    PRIMARY KEY (Ba_ID, Org_Ev_ID),
    FOREIGN KEY (Ba_ID) REFERENCES ORGANIZER(Badge_ID),
    FOREIGN KEY (Org_Ev_ID) REFERENCES EVENT(Event_ID)
);

CREATE TABLE PARTICIPATES_IN (
    Par_ID CHAR(10),
    Par_Ev_ID CHAR(10),
    PRIMARY KEY (Par_ID, Par_Ev_ID),
    FOREIGN KEY (Par_ID) REFERENCES PARTICIPANT(Participant_ID),
    FOREIGN KEY (Par_Ev_ID) REFERENCES EVENT(Event_ID)
);
