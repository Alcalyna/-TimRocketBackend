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

ALTER TABLE codecoach_user DROP COLUMN company;