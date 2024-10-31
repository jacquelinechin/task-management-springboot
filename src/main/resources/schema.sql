CREATE TABLE IF NOT EXISTS task (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    title varchar(255),
    description varchar(255),
    status boolean
);