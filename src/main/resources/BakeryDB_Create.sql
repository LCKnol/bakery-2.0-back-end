DROP TABLE IF EXISTS PI;

DROP TABLE IF EXISTS TEAMINROOM;

DROP TABLE IF EXISTS DASHBOARD;

DROP TABLE IF EXISTS ROOM;

DROP TABLE IF EXISTS USERINTEAM;

DROP TABLE IF EXISTS TEAM;

DROP TABLE IF EXISTS USERSESSION;

DROP TABLE IF EXISTS PIREQUEST;

DROP TABLE IF EXISTS USERS;

DROP TABLE IF EXISTS PIREQUEST;

create table DASHBOARD
(
    DASHBOARDID          int not null AUTO_INCREMENT,
    USERID               int,
    NAME                 varchar(64) not null,
    DASHBOARDURL         varchar(1024) not null,
    IMAGEURL             varchar(1024),
    primary key (DASHBOARDID)
);

create table PI
(
    PIID                 int not null AUTO_INCREMENT,
    ROOMNO               varchar(10),
    DASHBOARDID          int,
    NAME                 varchar(64) not null,
    MACADDRESS            varchar(32) not null,
    STATUS               varchar(32) null,
    primary key (PIID)
);

create table ROOM
(
    ROOMNO               varchar(10) not null,
    primary key (ROOMNO)
);

create table TEAM
(
    TEAMID               int not null AUTO_INCREMENT,
    TEAMNAME             varchar(256) not null,
    primary key (TEAMID)
);

create table TEAMINROOM
(
    TEAMID               int not null,
    ROOMNO               varchar(10) not null,
    primary key (TEAMID, ROOMNO)
);

create table USERS
(
    USERID               int not null AUTO_INCREMENT,
    FIRSTNAME            varchar(64) not null,
    LASTNAME             varchar(64) not null,
    PASSWORD             varchar(256) not null,
    EMAIL                varchar(256) not null unique,
    ISADMIN              bool not null,
    primary key (USERID)
);

create table USERINTEAM
(
    USERID               int not null,
    TEAMID               int not null,
    primary key (USERID, TEAMID)
);

create table USERSESSION
(
    SESSIONID            int not null AUTO_INCREMENT,
    USERID               int not null,
    TOKEN                varchar(36) not null,
    primary key (SESSIONID)
);

create table PIREQUEST
(
    REQUESTID            int not null AUTO_INCREMENT,
    MACADDRESS            varchar(32) not null,
    REQUESTEDON          datetime not null,
    primary key (REQUESTID)
);
/*password: AvisiPassword*/
INSERT INTO USERS (FIRSTNAME, LASTNAME, PASSWORD, EMAIL, ISADMIN)
VALUES ('Arnoud', 'Visi', '$2a$10$piwNZPAOhMhdG7Xlm/3kkOs/hZeYlfyQPAY/z7SurggdiLxfzu.KC', 'Avisi@outlook.com', 0);

/* password: password1234*/
INSERT INTO USERS (FIRSTNAME, LASTNAME, PASSWORD, EMAIL, ISADMIN)
VALUES ('Piere', 'Formance', '$2a$10$RSCq/K46cwZ2OFt0m482dOh5YATJJmqN4jTn7be/X1QhPiSDZPphe', 'Piere69@hotmail.com',
        0);

/* password: CorrectHorseBatteryStaple*/
INSERT INTO USERS (FIRSTNAME, LASTNAME, PASSWORD, EMAIL, ISADMIN)
VALUES ('Jaque', 'Ouzi', '$2a$10$JM9Yx4oy51Xa4HxslV3zXeJeuAC2e5EHCH3Db3tvhnfzcZWZG2/oC', 'Juzzi@gmail.com', 0);

/*password: verySecret!!!!*/
INSERT INTO USERS (FIRSTNAME, LASTNAME, PASSWORD, EMAIL, ISADMIN)
VALUES ('Justin', 'Case', '$2a$10$06KLmAO9hniJbBznhR4yD.eyLWfwy.wjOt74b3mCbyaYDCz8G22yq', 'JIC@gmail.com', 0);

INSERT INTO ROOM (ROOMNO)
VALUES ('14.02');
INSERT INTO ROOM (ROOMNO)
VALUES ('14.04');
INSERT INTO ROOM (ROOMNO)
VALUES ('15.05');
INSERT INTO ROOM (ROOMNO)
VALUES ('13.01');

INSERT INTO DASHBOARD (USERID, NAME, DASHBOARDURL, IMAGEURL)
VALUES (1, 'MEMES', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ',
        'https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fstatic.vecteezy.com%2Fsystem%2Fresources%2Fpreviews%2F013%2F480%2F841%2Foriginal%2Fcartoon-illustration-of-mother-and-baby-ducks-vector.jpg&f=1&nofb=1&ipt=44a60e01529dd6fb9a7ee5510fd043aa451bbf13599518a5ae912f2499fd38a8&ipo=images');

INSERT INTO DASHBOARD (USERID, NAME, DASHBOARDURL, IMAGEURL)
VALUES (2, 'FancyDashboard', 'https://www.aldi.nl/',
        'https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fclipart-library.com%2Fnewhp%2F85-850924_alien-spaceship-ufo-future-fantasy-futuristic-alien-in.png&f=1&nofb=1&ipt=152625c66c7e671454fe7a31fec903a6adf7dad480b8a2e425678a491e130887&ipo=images');

INSERT INTO PI (ROOMNO, DASHBOARDID, NAME, MACADDRESS, STATUS)
VALUES ('14.02', null, '14.02:01', 'aa:41:16:f3:81:fc', 'offline');

INSERT INTO PI (ROOMNO, DASHBOARDID, NAME, MACADDRESS, STATUS)
VALUES ('14.02', 1, '14.02:02', 'bb:41:16:a3:81:fc', 'online');

INSERT INTO PI (ROOMNO, DASHBOARDID, NAME, MACADDRESS, STATUS)
VALUES ('15.05', null, '15.05:01', 'cc:41:00:f3:81:fc', null);

INSERT INTO TEAM (TEAMNAME)
VALUES ('Colossus');

INSERT INTO TEAM (TEAMNAME)
VALUES ('Amobus');

INSERT INTO TEAM (TEAMNAME)
VALUES ('TheBestTeamingTeamEverToHaveTeamed');

INSERT INTO TEAM (TEAMNAME)
VALUES ('MediocreTeam');

INSERT INTO TEAMINROOM (TEAMID, ROOMNO)
VALUES (1, '13.01');
INSERT INTO TEAMINROOM (TEAMID, ROOMNO)
VALUES (2, '14.02');
INSERT INTO TEAMINROOM (TEAMID, ROOMNO)
VALUES (3, '14.04');
INSERT INTO TEAMINROOM (TEAMID, ROOMNO)
VALUES (4, '15.05');
INSERT INTO TEAMINROOM (TEAMID, ROOMNO)
VALUES (2, '13.01');
INSERT INTO TEAMINROOM (TEAMID, ROOMNO)
VALUES (1, '14.02');
INSERT INTO TEAMINROOM (TEAMID, ROOMNO)
VALUES (2, '14.04');
INSERT INTO TEAMINROOM (TEAMID, ROOMNO)
VALUES (3, '15.05');

INSERT INTO USERINTEAM (USERID, TEAMID)
VALUES (1, 1);
INSERT INTO USERINTEAM (USERID, TEAMID)
VALUES (1, 2);
INSERT INTO USERINTEAM (USERID, TEAMID)
VALUES (2, 2);
INSERT INTO USERINTEAM (USERID, TEAMID)
VALUES (3, 3);
INSERT INTO USERINTEAM (USERID, TEAMID)
VALUES (4, 4);
INSERT INTO USERINTEAM (USERID, TEAMID)
VALUES (4, 3);

INSERT INTO PIREQUEST (MACADDRESS, REQUESTEDON)
VALUES('52:8D:4E:9A:2F:71', '2024-04-24 09:36:22');
INSERT INTO PIREQUEST (MACADDRESS, REQUESTEDON)
VALUES('A7:B1:3E:6F:8C:D2', '2024-04-25 18:45:10');

alter table DASHBOARD add constraint FK_DASHBOARDFROMUSER foreign key (USERID)
    references USERS (USERID) on delete restrict on update restrict;

alter table PI add constraint FK_DASHBOARDONPI foreign key (DASHBOARDID)
    references DASHBOARD (DASHBOARDID) on delete restrict on update restrict;

alter table PI add constraint FK_PIINROOM foreign key (ROOMNO)
    references ROOM (ROOMNO) on delete restrict on update restrict;

alter table TEAMINROOM add constraint FK_TEAMINROOM foreign key (TEAMID)
    references TEAM (TEAMID) on delete restrict on update restrict;

alter table TEAMINROOM add constraint FK_TEAMINROOM2 foreign key (ROOMNO)
    references ROOM (ROOMNO) on delete restrict on update restrict;

alter table USERINTEAM add constraint FK_USERINTEAM foreign key (USERID)
    references USERS (USERID) on delete restrict on update restrict;

alter table USERINTEAM add constraint FK_USERINTEAM2 foreign key (TEAMID)
    references TEAM (TEAMID) on delete restrict on update restrict;

alter table USERSESSION add constraint FK_USERSESSION foreign key (USERID)
    references USERS (USERID) on delete restrict on update restrict;


