CREATE TABLE `STUDENT`
(
    `STUDENT_PK` VARCHAR(50) NOT NULL,
    `STUDENT_ID` INT         NOT NULL,
    `MAJORITY`   VARCHAR(50) NULL,
    `NAME`       VARCHAR(30) NULL,
    `ROLE`       VARCHAR(30) NULL,
    `MEMBER`     VARCHAR(30) NULL
);

CREATE TABLE `VOTE`
(
    `VOTE_PK`      VARCHAR(50) NOT NULL,
    `TITLE`        VARCHAR(100) NULL,
    `CONTENT`      VARCHAR(100) NULL,
    `START_DATE`   DATETIME NULL,
    `SUBMIT_DATE`  DATETIME NULL,
    `LIMIT_PEOPLE` INT NULL
);

CREATE TABLE `STUDENT_VOTE`
(
    `STUDENT_VOTE_PK` VARCHAR(50) NOT NULL,
    `VOTE_FK`         VARCHAR(50) NOT NULL,
    `STUDENT_FK`      VARCHAR(50) NOT NULL,
    `CHOICE`          INT NULL
);

ALTER TABLE `STUDENT`
    ADD CONSTRAINT `PK_STUDENT` PRIMARY KEY (
                                             `STUDENT_PK`
        );

ALTER TABLE `VOTE`
    ADD CONSTRAINT `PK_VOTE` PRIMARY KEY (
                                          `VOTE_PK`
        );

ALTER TABLE `STUDENT_VOTE`
    ADD CONSTRAINT `PK_STUDENT_VOTE` PRIMARY KEY (
                                                  `STUDENT_VOTE_PK`,
                                                  `VOTE_FK`,
                                                  `STUDENT_FK`
        );

ALTER TABLE `STUDENT_VOTE`
    ADD CONSTRAINT `FK_VOTE_TO_STUDENT_VOTE_1` FOREIGN KEY (
                                                            `VOTE_FK`
        )
        REFERENCES `VOTE` (
                           `VOTE_PK`
            );

ALTER TABLE `STUDENT_VOTE`
    ADD CONSTRAINT `FK_STUDENT_TO_STUDENT_VOTE_1` FOREIGN KEY (
                                                               `STUDENT_FK`
        )
        REFERENCES `STUDENT` (
                              `STUDENT_PK`
            );

