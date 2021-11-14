DROP TABLE if EXISTS schooldatabase.courses CASCADE;
DROP TABLE if EXISTS schooldatabase.students CASCADE;
DROP TABLE if EXISTS schooldatabase.groups;
DROP TABLE if EXISTS schooldatabase.students_courses;

CREATE TABLE schooldatabase.courses
(
    course_id SERIAL NOT NULL,
    course_name varchar(250) COLLATE pg_catalog."default",
    course_description varchar(250) COLLATE pg_catalog."default",
    CONSTRAINT "COURSES_pkey" PRIMARY KEY (course_id)
)

TABLESPACE pg_default;

ALTER TABLE schooldatabase.courses
    OWNER to postgres;

CREATE TABLE schooldatabase.groups
(
    group_id SERIAL,
    group_name varchar(250) COLLATE pg_catalog."default",
    CONSTRAINT "GROUPS_pkey" PRIMARY KEY (group_id)
)

TABLESPACE pg_default;

ALTER TABLE schooldatabase.groups
    OWNER to postgres;

CREATE TABLE schooldatabase.students
(
    student_id SERIAL NOT NULL,
    group_id integer REFERENCES schooldatabase.groups(group_id),
    first_name varchar(250) COLLATE pg_catalog."default",
    last_name varchar(250) COLLATE pg_catalog."default",
    CONSTRAINT students_pkey PRIMARY KEY (student_id)
)

TABLESPACE pg_default;

ALTER TABLE schooldatabase.students
    OWNER to postgres;

CREATE TABLE schooldatabase.students_courses
(
    student_id integer NOT NULL,
    course_id integer NOT NULL,
    FOREIGN KEY (student_id) REFERENCES schooldatabase.students(student_id),
    FOREIGN KEY (course_id) REFERENCES schooldatabase.courses(course_id),
    UNIQUE (student_id, course_id)
)

TABLESPACE pg_default;

ALTER TABLE schooldatabase.students_courses
    OWNER to postgres;
    