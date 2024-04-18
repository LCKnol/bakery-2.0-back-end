/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     15-4-2024 15:37:17                           */
/*==============================================================*/
USE bakeryDB;

DROP TABLE IF EXISTS PI;

DROP TABLE IF EXISTS DASHBOARD;

DROP TABLE IF EXISTS TEAMINROOM;

DROP TABLE IF EXISTS ROOM;

DROP TABLE IF EXISTS USERINTEAM;

DROP TABLE IF EXISTS TEAM;

DROP TABLE IF EXISTS USERSESSION;

DROP TABLE IF EXISTS USER;

/*==============================================================*/
/* Table: DASHBOARD                                             */
/*==============================================================*/
CREATE TABLE DASHBOARD
(
    DASHBOARDID  INT           NOT NULL AUTO_INCREMENT,
    USERID       INT,
    NAME         VARCHAR(64)   NOT NULL,
    DASHBOARDURL VARCHAR(1024) NOT NULL,
    IMAGEURL     VARCHAR(1024),
    PRIMARY KEY (DASHBOARDID)
);

/*==============================================================*/
/* Table: PI                                                    */
/*==============================================================*/
CREATE TABLE PI
(
    PIID        INT         NOT NULL AUTO_INCREMENT,
    ROOMNO      CHAR(4),
    DASHBOARDID INT,
    NAME        VARCHAR(64) NOT NULL,
    MACADRESS   VARCHAR(32) NOT NULL,
    STATUS      VARCHAR(32) NULL,
    PRIMARY KEY (PIID)
);

/*==============================================================*/
/* Table: ROOM                                                  */
/*==============================================================*/
CREATE TABLE ROOM
(
    ROOMNO CHAR(4) NOT NULL,
    PRIMARY KEY (ROOMNO)
);

/*==============================================================*/
/* Table: TEAM                                                  */
/*==============================================================*/
CREATE TABLE TEAM
(
    TEAMID   INT          NOT NULL AUTO_INCREMENT,
    TEAMNAME VARCHAR(256) NOT NULL,
    PRIMARY KEY (TEAMID)
);

/*==============================================================*/
/* Table: TEAMINROOM                                            */
/*==============================================================*/
CREATE TABLE TEAMINROOM
(
    TEAMID INT     NOT NULL,
    ROOMNO CHAR(4) NOT NULL,
    PRIMARY KEY (TEAMID, ROOMNO)
);

/*==============================================================*/
/* Table: USER                                                  */
/*==============================================================*/
CREATE TABLE USER
(
    USERID    INT          NOT NULL AUTO_INCREMENT,
    FIRSTNAME VARCHAR(64)  NOT NULL,
    LASTNAME  VARCHAR(64)  NOT NULL,
    PASSWORD  VARCHAR(256) NOT NULL,
    EMAIL     VARCHAR(256) NOT NULL,
    ISADMIN   BOOL         NOT NULL,
    PRIMARY KEY (USERID)
);

/*==============================================================*/
/* Table: USERINTEAM                                            */
/*==============================================================*/
CREATE TABLE USERINTEAM
(
    USERID INT NOT NULL,
    TEAMID INT NOT NULL,
    PRIMARY KEY (USERID, TEAMID)
);

/*==============================================================*/
/* Table: USERSESSION                                           */
/*==============================================================*/
CREATE TABLE USERSESSION
(
    SESSIONID INT         NOT NULL AUTO_INCREMENT,
    USERID    INT         NOT NULL,
    TOKEN     VARCHAR(36) NOT NULL,
    PRIMARY KEY (SESSIONID)
);

ALTER TABLE DASHBOARD
    ADD CONSTRAINT FK_DASHBOARDFROMUSER FOREIGN KEY (USERID)
        REFERENCES USER (USERID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PI
    ADD CONSTRAINT FK_DASHBOARDONPI FOREIGN KEY (DASHBOARDID)
        REFERENCES DASHBOARD (DASHBOARDID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE PI
    ADD CONSTRAINT FK_PIINROOM FOREIGN KEY (ROOMNO)
        REFERENCES ROOM (ROOMNO) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEAMINROOM
    ADD CONSTRAINT FK_TEAMINROOM FOREIGN KEY (TEAMID)
        REFERENCES TEAM (TEAMID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE TEAMINROOM
    ADD CONSTRAINT FK_TEAMINROOM2 FOREIGN KEY (ROOMNO)
        REFERENCES ROOM (ROOMNO) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE USERINTEAM
    ADD CONSTRAINT FK_USERINTEAM FOREIGN KEY (USERID)
        REFERENCES USER (USERID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE USERINTEAM
    ADD CONSTRAINT FK_USERINTEAM2 FOREIGN KEY (TEAMID)
        REFERENCES TEAM (TEAMID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE USERSESSION
    ADD CONSTRAINT FK_USERSESSION FOREIGN KEY (USERID)
        REFERENCES USER (USERID) ON DELETE RESTRICT ON UPDATE RESTRICT;

