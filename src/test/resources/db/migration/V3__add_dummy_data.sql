
INSERT INTO codecoach_user (user_id, firstname, lastname, email, password, company, role, picture_url)
VALUES ('96f7383e-67c1-4cb9-936c-a046d13f7fec', 'Tom', 'Scheyltjens', 'tom@tom.com', 'e0dc0bf8e026a92d64af4a2c0353c3d7b7e62b4bfd41dc356a62cd629df0b397', 'Switchfully', 'COACH', 'assets/default-profile-picture.jpg');

INSERT INTO codecoach_user (user_id, firstname, lastname, email, password, role, picture_url)
VALUES ('2ac5b3fc-95df-40fb-8cab-b994bee20f0c', 'Calinh ', 'Coach', 'calilinh@timrocket.com', 'e93eb91ac8e1c93bb008fde946669ced0ba2fe95b3b9aa3599729d810580c251', 'COACH', 'assets/default-profile-picture.jpg');

INSERT INTO codecoach_user (user_id, firstname, lastname, email, password, role, picture_url)
VALUES ('8ec18d0f-1ae6-4dee-9191-4848b4dea8f6', 'Ruben', 'Neven', 'ruben.neven@minfin.fed.be', 'Dertig30', 'COACHEE', 'assets/default-profile-picture.jpg');

INSERT INTO coach_information (user_id, coach_xp, introduction, availability)
VALUES ('2ac5b3fc-95df-40fb-8cab-b994bee20f0c', 0, 'Hey I am new coach!', '');

INSERT INTO topic (topic_id, name)
VALUES ('066b9b8b-229c-415a-9c0a-2e389b85303e', 'KeyCloack'),
       ('bb196206-9a1f-4479-b75a-eb3f150d97c2', 'JavaScript');

INSERT INTO coach_topic (user_id, topic_id, experience)
VALUES ('96f7383e-67c1-4cb9-936c-a046d13f7fec', '066b9b8b-229c-415a-9c0a-2e389b85303e', 'JUNIOR');

INSERT INTO coach_topic (user_id, topic_id, experience)
VALUES ('96f7383e-67c1-4cb9-936c-a046d13f7fec', 'bb196206-9a1f-4479-b75a-eb3f150d97c2', 'JUNIOR');

INSERT INTO coach_information (user_id, coach_xp, introduction, availability)
VALUES ('96f7383e-67c1-4cb9-936c-a046d13f7fec', 250, 'Dit is een test introductie', 'test ');

