ALTER TABLE coach_topic DROP CONSTRAINT coach_topic_pkey;

ALTER TABLE coach_topic ADD PRIMARY KEY(user_id, topic_id);

ALTER TABLE coach_topic DROP CONSTRAINT coach_topic_experience_check;

ALTER TABLE coach_topic ALTER COLUMN experience TYPE VARCHAR(10);