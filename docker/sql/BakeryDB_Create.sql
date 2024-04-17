/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     15-4-2024 15:37:17                           */
/*==============================================================*/
USE bakeryDB;

drop table if exists DASHBOARD;

drop table if exists PI;

drop table if exists ROOM;

drop table if exists TEAM;

drop table if exists TEAMINROOM;

drop table if exists USER;

drop table if exists USERINTEAM;

drop table if exists USERSESSION;

/*==============================================================*/
/* Table: DASHBOARD                                             */
/*==============================================================*/
create table DASHBOARD
(
   DASHBOARDID          int not null AUTO_INCREMENT,
   USERID               int,
   NAME                 varchar(64) not null,
   DASHBOARDURL         varchar(1024) not null,
   IMAGEURL             varchar(1024),
   primary key (DASHBOARDID)
);

/*==============================================================*/
/* Table: PI                                                    */
/*==============================================================*/
create table PI
(
   PIID                 int not null AUTO_INCREMENT,
   ROOMNO               char(4),
   DASHBOARDID          int,
   NAME                 varchar(64) not null,
   MACADRESS            varchar(32) not null,
   primary key (PIID)
);

/*==============================================================*/
/* Table: ROOM                                                  */
/*==============================================================*/
create table ROOM
(
   ROOMNO               char(4) not null,
   primary key (ROOMNO)
);

/*==============================================================*/
/* Table: TEAM                                                  */
/*==============================================================*/
create table TEAM
(
   TEAMID               int not null AUTO_INCREMENT,
   TEAMNAME             varchar(256) not null,
   primary key (TEAMID)
);

/*==============================================================*/
/* Table: TEAMINROOM                                            */
/*==============================================================*/
create table TEAMINROOM
(
   TEAMID               int not null,
   ROOMNO               char(4) not null,
   primary key (TEAMID, ROOMNO)
);

/*==============================================================*/
/* Table: USER                                                  */
/*==============================================================*/
create table USER
(
   USERID               int not null AUTO_INCREMENT,
   FIRSTNAME            varchar(64) not null,
   LASTNAME             varchar(64) not null,
   PASSWORD             varchar(256) not null,
   EMAIL                varchar(256) not null,
   ISADMIN              bool not null,
   primary key (USERID)
);

/*==============================================================*/
/* Table: USERINTEAM                                            */
/*==============================================================*/
create table USERINTEAM
(
   USERID               int not null,
   TEAMID               int not null,
   primary key (USERID, TEAMID)
);

/*==============================================================*/
/* Table: USERSESSION                                           */
/*==============================================================*/
create table USERSESSION
(
   SESSIONID            int not null AUTO_INCREMENT,
   USERID               int not null,
   TOKEN                varchar(36) not null,
   primary key (SESSIONID)
);

alter table DASHBOARD add constraint FK_DASHBOARDFROMUSER foreign key (USERID)
      references USER (USERID) on delete restrict on update restrict;

alter table PI add constraint FK_DASHBOARDONPI foreign key (DASHBOARDID)
      references DASHBOARD (DASHBOARDID) on delete restrict on update restrict;

alter table PI add constraint FK_PIINROOM foreign key (ROOMNO)
      references ROOM (ROOMNO) on delete restrict on update restrict;

alter table TEAMINROOM add constraint FK_TEAMINROOM foreign key (TEAMID)
      references TEAM (TEAMID) on delete restrict on update restrict;

alter table TEAMINROOM add constraint FK_TEAMINROOM2 foreign key (ROOMNO)
      references ROOM (ROOMNO) on delete restrict on update restrict;

alter table USERINTEAM add constraint FK_USERINTEAM foreign key (USERID)
      references USER (USERID) on delete restrict on update restrict;

alter table USERINTEAM add constraint FK_USERINTEAM2 foreign key (TEAMID)
      references TEAM (TEAMID) on delete restrict on update restrict;

alter table USERSESSION add constraint FK_USERSESSION foreign key (USERID)
      references USER (USERID) on delete restrict on update restrict;

