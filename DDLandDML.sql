/*Creates a WritingGroup table that holds group name, the head writer, the year formed and the subject. Group name is the primary key */
create table WritingGroups(
    GroupName varchar(60) NOT NULL,
    HeadWriter varchar(20) NOT NULL,
    YearFormed int         NOT NULL,
    Subject    varchar(30) NOT NULL,
    constraint WritingGroups_pk PRIMARY KEY (GroupName)
);

/*Creates Publisher table that holds Publisher name, their address, phone, and email. Publisher name is the primary key*/
create table Publisher(
    PublisherName varchar(60) NOT NULL,
    PublisherAddress varchar(60) NOT NULL,
    PublisherPhone varchar(60) NOT NULL,
    PublisherEmail varchar(60) NOT NULL,
    constraint Publisher_pk PRIMARY KEY (PublisherName)
);

/*Creates Book table that holds group name, publisher name, book title, year published, and number of pages. Group name and book title is the 
primary key and group name and publisher name is the foreign key*/
create table Book(
    GroupName varchar(60) NOT NULL,
    PublisherName varchar(60) NOT NULL,
    BookTitle varchar(60) NOT NULL,
    YearPublished int         NOT NULL,
    NumberPages int NOT NULL,
    constraint Book_pk PRIMARY KEY (GroupName, BookTitle),
    constraint Book_fk_1 FOREIGN KEY (GroupName) REFERENCES WritingGroups (GroupName),
    constraint Book_fk_2 FOREIGN KEY (PublisherName) REFERENCES Publisher (PublisherName)
);



/* Inserts random group names, head writer, year formed, and subjet */
INSERT INTO WritingGroups(GroupName, HeadWriter, YearFormed, Subject) VALUES
    ('Writing Alliance', 'Leon Hartman', 2004, 'Fantasy'),
    ('Writer Crew', 'Terrence Stevenson', 2009, 'Mystery'),
    ('Story Association', 'Cory Palmer', 2006, 'Non-Fiction'),
    ('Space Writers', 'Pauline Robins', 2012, 'Sci-Fi'),
    ('Genesis Writers', 'John Doe', 1995, 'Horror');

/* Inserts random publisher name, their address, phone, and email. */
INSERT INTO Publisher(PublisherName, PublisherAddress, PublisherPhone, PublisherEmail) VALUES
    ('Tangled Publishing', '2430 Walker Street', '2485324513', 'tangledpublishing@gmail.com'),
    ('Six Nation Press', '3821 Miller Way', '9723495812', 'sixnationpressco@gmail.com'),
    ('Coffee Bean Review Press', '6244 West Park Avenue', '9812408255', 'coffeebeanpressreview@gmail.com'),
    ('Penguin Books', '532 Penguin Way', '9242948212', 'penguinbooks@gmail.com'),
    ('Harper Collins', '392 Collins St.', '3249239123', 'harpercollins@gmail.com');


/* Inserts random group name, publisher name, book title, year published, and number of pages. */
INSERT INTO Book(GroupName, PublisherName, BookTitle, YearPublished, NumberPages) VALUES
    ('Writing Alliance', 'Tangled Publishing', 'Northern Lights', 2008, 218),
    ('Writing Alliance', 'Six Nation Press', 'Snow Garden', 2014, 271),
    ('Writer Crew', 'Six Nation Press', 'The Moonstone', 2010, 181),
    ('Writer Crew', 'Coffee Bean Review Press', 'The Grayhound of Adam', 2015, 349),
    ('Story Association', 'Tangled Publishing', 'The Silent Springs', 2009, 193),
    ('Space Writers', 'Coffee Bean Review Press', 'Warp Drive', 2016, 387),
    ('Space Writers', 'Penguin Books', 'Starship Paradise', 2018, 435),
    ('Genesis Writers', 'Harper Collins', 'Dracula', 1995, 302);