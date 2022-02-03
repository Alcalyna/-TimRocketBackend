ALTER TABLE members RENAME TO codecoach_user;

ALTER TABLE codecoach_user RENAME COLUMN id TO user_id;

CREATE TABLE IF NOT EXISTS coach_information(
    user_id UUID PRIMARY KEY,
    coach_xp INTEGER NOT NULL,
    introduction VARCHAR(500) NOT NULL,
    availability VARCHAR(500) NOT NULL,
    CONSTRAINT fk_coach_information_user_id
    FOREIGN KEY(user_id)
    REFERENCES codecoach_user (user_id)
);

CREATE TABLE IF NOT EXISTS topic (
    topic_id UUID PRIMARY KEY,
    name VARCHAR (25) NOT NULL
);

CREATE TABLE IF NOT EXISTS coach_topic(
    user_id UUID NOT NULL ,
    topic_id UUID NOT NULL,
    experience VARCHAR (10),
    PRIMARY KEY(user_id, topic_id),

    CONSTRAINT fk_coach_topic_topic_id
    FOREIGN KEY(topic_id)
    REFERENCES topic (topic_id),

    CONSTRAINT fk_coach_topic_user_id
    FOREIGN KEY(user_id)
    REFERENCES codecoach_user (user_id)
);

CREATE TABLE IF NOT EXISTS session(
                                      session_id UUID PRIMARY KEY,
                                      subject VARCHAR(25) NOT NULL,
    date DATE NOT NULL,
    time TIME NOT NULL,
    location VARCHAR(25) NOT NULL,
    f2f_location VARCHAR,
    remarks VARCHAR(250),
    coachee_id UUID NOT NULL,
    coach_id UUID NOT NULL,

    CONSTRAINT fk_session_coachee_user_id
    FOREIGN KEY (coachee_id)
    REFERENCES codecoach_user(user_id),

    CONSTRAINT fk_session_coach_user_id
    FOREIGN KEY (coach_id)
    REFERENCES codecoach_user(user_id)
    );

