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
    DASHBOARDID  int           not null AUTO_INCREMENT,
    TEAMID       int,
    NAME         varchar(64)   not null,
    DASHBOARDURL varchar(1024) not null,
    REFRESHRATE  int,
    primary key (DASHBOARDID)
);

create table PI
(
    PIID        int          not null AUTO_INCREMENT,
    ROOMNO      varchar(10),
    DASHBOARDID int,
    NAME        varchar(64)  not null,
    MACADDRESS  varchar(32)  not null,
    IPADDRESS   varchar(135) not null,
    STATUS      varchar(32)  null,
    primary key (PIID)
);

create table ROOM
(
    ROOMNO varchar(10) not null,
    primary key (ROOMNO)
);

create table TEAM
(
    TEAMID   int          not null AUTO_INCREMENT,
    TEAMNAME varchar(256) not null,
    primary key (TEAMID)
);

create table TEAMINROOM
(
    TEAMID int         not null,
    ROOMNO varchar(10) not null,
    primary key (TEAMID, ROOMNO)
);

create table USERS
(
    USERID    int          not null AUTO_INCREMENT,
    FIRSTNAME varchar(64)  not null,
    LASTNAME  varchar(64)  not null,
    PASSWORD  varchar(256) not null,
    EMAIL     varchar(256) not null unique,
    ISADMIN   bool         not null,
    primary key (USERID)
);

create table USERINTEAM
(
    USERID int not null,
    TEAMID int not null,
    primary key (USERID, TEAMID)
);

create table USERSESSION
(
    SESSIONID int         not null AUTO_INCREMENT,
    USERID    int         not null,
    TOKEN     varchar(36) not null,
    primary key (SESSIONID)
);

create table PIREQUEST
(
    REQUESTID   int          not null AUTO_INCREMENT,
    MACADDRESS  varchar(32)  not null,
    IPADDRESS   varchar(135) not null,
    REQUESTEDON datetime     not null,
    primary key (REQUESTID)
);

alter table DASHBOARD
    add constraint FK_DASHBOARDFROMTEAM foreign key (TEAMID)
        references TEAM (TEAMID) on delete cascade on update restrict;

alter table PI
    add constraint FK_DASHBOARDONPI foreign key (DASHBOARDID)
        references DASHBOARD (DASHBOARDID) on delete set null on update restrict;

alter table PI
    add constraint FK_PIINROOM foreign key (ROOMNO)
        references ROOM (ROOMNO) on delete restrict on update restrict;

alter table TEAMINROOM
    add constraint FK_TEAMINROOM foreign key (TEAMID)
        references TEAM (TEAMID) on delete cascade on update restrict;

alter table TEAMINROOM
    add constraint FK_TEAMINROOM2 foreign key (ROOMNO)
        references ROOM (ROOMNO) on delete cascade on update restrict;

alter table USERINTEAM
    add constraint FK_USERINTEAM foreign key (USERID)
        references USERS (USERID) on delete cascade on update restrict;

alter table USERINTEAM
    add constraint FK_USERINTEAM2 foreign key (TEAMID)
        references TEAM (TEAMID) on delete cascade on update restrict;

alter table USERSESSION
    add constraint FK_USERSESSION foreign key (USERID)
        references USERS (USERID) on delete cascade on update restrict;