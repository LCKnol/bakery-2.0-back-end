/*==============================================================*/
/* Table: USER                                                  */
/*==============================================================*/

# password: AvisiPassword
INSERT INTO bakeryDB.USER (USERID, FIRSTNAME, LASTNAME, PASSWORD, EMAIL, ISADMIN)
VALUES (1, 'Arnoud', 'Visi', '$2a$10$piwNZPAOhMhdG7Xlm/3kkOs/hZeYlfyQPAY/z7SurggdiLxfzu.KC', 'Avisi@outlook.com', 0);

# password: password1234
INSERT INTO bakeryDB.USER (USERID, FIRSTNAME, LASTNAME, PASSWORD, EMAIL, ISADMIN)
VALUES (2, 'Piere', 'Formance', '$2a$10$RSCq/K46cwZ2OFt0m482dOh5YATJJmqN4jTn7be/X1QhPiSDZPphe', 'Piere69@hotmail.com',
        0);

# password: CorrectHorseBatteryStaple
INSERT INTO bakeryDB.USER (USERID, FIRSTNAME, LASTNAME, PASSWORD, EMAIL, ISADMIN)
VALUES (3, 'Jaque', 'Ouzi', '$2a$10$JM9Yx4oy51Xa4HxslV3zXeJeuAC2e5EHCH3Db3tvhnfzcZWZG2/oC', 'Juzzi@gmail.com', 0);

# password: verySecret!!!!
INSERT INTO bakeryDB.USER (USERID, FIRSTNAME, LASTNAME, PASSWORD, EMAIL, ISADMIN)
VALUES (4, 'Justin', 'Case', '$2a$10$06KLmAO9hniJbBznhR4yD.eyLWfwy.wjOt74b3mCbyaYDCz8G22yq', 'JIC@gmail.com', 0);


/*==============================================================*/
/* Table: ROOM                                                  */
/*==============================================================*/

INSERT INTO bakeryDB.ROOM (ROOMNO)
VALUES ('14.02');
INSERT INTO bakeryDB.ROOM (ROOMNO)
VALUES ('14.04');
INSERT INTO bakeryDB.ROOM (ROOMNO)
VALUES ('15.05');
INSERT INTO bakeryDB.ROOM (ROOMNO)
VALUES ('13.01');


/*==============================================================*/
/* Table: DASHBOARD                                             */
/*==============================================================*/

INSERT INTO bakeryDB.DASHBOARD (USERID, NAME, DASHBOARDURL, IMAGEURL)
VALUES (1, 'MEMES', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ',
        'https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fstatic.vecteezy.com%2Fsystem%2Fresources%2Fpreviews%2F013%2F480%2F841%2Foriginal%2Fcartoon-illustration-of-mother-and-baby-ducks-vector.jpg&f=1&nofb=1&ipt=44a60e01529dd6fb9a7ee5510fd043aa451bbf13599518a5ae912f2499fd38a8&ipo=images');

INSERT INTO bakeryDB.DASHBOARD (USERID, NAME, DASHBOARDURL, IMAGEURL)
VALUES (2, 'FancyDashboard', 'https://www.aldi.nl/',
        'https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fclipart-library.com%2Fnewhp%2F85-850924_alien-spaceship-ufo-future-fantasy-futuristic-alien-in.png&f=1&nofb=1&ipt=152625c66c7e671454fe7a31fec903a6adf7dad480b8a2e425678a491e130887&ipo=images');


/*==============================================================*/
/* Table: PI                                                    */
/*==============================================================*/

INSERT INTO bakeryDB.PI (ROOMNO, DASHBOARDID, NAME, MACADRESS, STATUS)
VALUES ('14.02', null, '14.02:01', 'aa:41:16:f3:81:fc', 'offline');

INSERT INTO bakeryDB.PI (ROOMNO, DASHBOARDID, NAME, MACADRESS, STATUS)
VALUES ('14.02', 1, '14.02:02', 'bb:41:16:a3:81:fc', 'online');

INSERT INTO bakeryDB.PI (ROOMNO, DASHBOARDID, NAME, MACADRESS, STATUS)
VALUES ('15.05', null, '15.05:01', 'cc:41:00:f3:81:fc', null);


/*==============================================================*/
/* Table: TEAM                                                  */
/*==============================================================*/

INSERT INTO bakeryDB.TEAM (TEAMNAME)
VALUES ('Colossus');

INSERT INTO bakeryDB.TEAM (TEAMNAME)
VALUES ('Amobus');

INSERT INTO bakeryDB.TEAM (TEAMNAME)
VALUES ('TheBestTeamingTeamEverToHaveTeamed');

INSERT INTO bakeryDB.TEAM (TEAMNAME)
VALUES ('MediocreTeam');


/*==============================================================*/
/* Table: TEAMINROOM                                            */
/*==============================================================*/

INSERT INTO bakeryDB.TEAMINROOM (TEAMID, ROOMNO)
VALUES (1, '13.01');
INSERT INTO bakeryDB.TEAMINROOM (TEAMID, ROOMNO)
VALUES (2, '14.02');
INSERT INTO bakeryDB.TEAMINROOM (TEAMID, ROOMNO)
VALUES (3, '14.04');
INSERT INTO bakeryDB.TEAMINROOM (TEAMID, ROOMNO)
VALUES (4, '15.05');
INSERT INTO bakeryDB.TEAMINROOM (TEAMID, ROOMNO)
VALUES (2, '13.01');
INSERT INTO bakeryDB.TEAMINROOM (TEAMID, ROOMNO)
VALUES (1, '14.02');
INSERT INTO bakeryDB.TEAMINROOM (TEAMID, ROOMNO)
VALUES (2, '14.04');
INSERT INTO bakeryDB.TEAMINROOM (TEAMID, ROOMNO)
VALUES (3, '15.05');


/*==============================================================*/
/* Table: TEAMINROOM                                            */
/*==============================================================*/

INSERT INTO bakeryDB.USERINTEAM (USERID, TEAMID) VALUES (1, 1);
INSERT INTO bakeryDB.USERINTEAM (USERID, TEAMID) VALUES (1, 2);
INSERT INTO bakeryDB.USERINTEAM (USERID, TEAMID) VALUES (2, 2);
INSERT INTO bakeryDB.USERINTEAM (USERID, TEAMID) VALUES (3, 3);
INSERT INTO bakeryDB.USERINTEAM (USERID, TEAMID) VALUES (4, 4);
INSERT INTO bakeryDB.USERINTEAM (USERID, TEAMID) VALUES (4, 3);
