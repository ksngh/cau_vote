DROP TABLE IF EXISTS `VOTE`;

CREATE TABLE `VOTE` (
                        `ID`	UUID	NOT NULL,
                        `TITLE`	VARCHAR(50)	NOT NULL,
                        `CONTENT`	VARCHAR(1000)	NOT NULL,
                        `STATUS`	VARCHAR(30)	NOT NULL	DEFAULT 'PENDING'   COMMENT 'PENDING,ACTIVE,INACTIVE',
                        `LIMIT_PEOPLE`	TINYINT	NOT NULL,
                        `START_DATE`	DATETIME	NOT NULL,
                        `END_DATE`	DATETIME	NOT NULL,
                        `CREATED_AT`	DATETIME	NOT NULL,
                        `UPDATED_AT`	DATETIME	NULL,
                        `DELETED_AT`	DATETIME	NULL
);

DROP TABLE IF EXISTS `Ranking`;

CREATE TABLE `Ranking` (
                           `ID`	INT	NOT NULL,
                           `STUDENT_FK`	INT	NOT NULL,
                           `SEMESTER`	VARCHAR(30)	NOT NULL,
                           `ATTENDANCE_COUNT`	INT	NOT NULL
);

DROP TABLE IF EXISTS `STUDENT_VOTE`;

CREATE TABLE `STUDENT_VOTE` (
                                `ID`	INT	NOT NULL,
                                `STUDENT_FK`	VARCHAR(50)	NOT NULL,
                                `VOTE_FK`	VARCHAR(50)	NOT NULL,
                                `TYPE`	VARCHAR(10)	NOT NULL	COMMENT 'SABRE,FLEURET,EPEE',
                                `CREATED_AT`	DATETIME	NOT NULL,
                                `DELETED_AT`	DATETIME	NULL
);

DROP TABLE IF EXISTS `STUDENT`;

CREATE TABLE `STUDENT` (
                           `ID`	INT	NOT NULL,
                           `AUTHORITY_FK`	INT	NOT NULL,
                           `STUDENT_ID`	VARCHAR(30)	NOT NULL,
                           `EMAIL`	VARCHAR(50)	NOT NULL,
                           `NAME`	VARCHAR(30)	NOT NULL,
                           `MAJORITY`	VARCHAR(50)	NOT NULL,
                           `MEMBER_TYPE`	VARCHAR(10)	NOT NULL	COMMENT 'NEW,EXISTING,GRADUATED',
                           `CREATED_AT`	DATETIME	NOT NULL,
                           `UPDATED_AT`	DATETIME	NULL,
                           `DELETED_AT`	DATETIME	NULL
);

DROP TABLE IF EXISTS `AUTHORITY`;

CREATE TABLE `AUTHORITY` (
                             `ID`	INT	NOT NULL,
                             `NAME`	VARCHAR(30)	NOT NULL	COMMENT 'ADMIN,MEMBER'
);

DROP TABLE IF EXISTS `STUDENT_GEAR`;

CREATE TABLE `STUDENT_GEAR` (
                                `ID`	INT	NOT NULL,
                                `STUDENT_FK`	INT	NOT NULL,
                                `GEAR_FK`	INT	NOT NULL,
                                `LATE_FEE`	INT	NOT NULL	DEFAULT 0,
                                `RENTAL_DATE`	DATETIME	NOT NULL,
                                `DUE_DATE`	DATETIME	NOT NULL,
                                `RETURNED_AT`	DATETIME	NULL
);

DROP TABLE IF EXISTS `GEAR`;

CREATE TABLE `GEAR` (
                        `ID`	INT	NOT NULL,
                        `NUM`	INT	NOT NULL,
                        `TYPE`	VARCHAR(10)	NOT NULL	COMMENT 'SABRE,FLURET,EPEE,COMMON',
                        `NAME`	VARCHAR(30)	NOT NULL	COMMENT 'MASK, SWORD, GLOVE, UNIFORM_TOP, UNIFORM_BOTTOM, METAL, BODY_WIRE,MASK_WIRE'
);

ALTER TABLE `VOTE` ADD CONSTRAINT `PK_VOTE` PRIMARY KEY (
                                                         `ID`
    );

ALTER TABLE `Ranking` ADD CONSTRAINT `PK_RANKING` PRIMARY KEY (
                                                               `ID`
    );

ALTER TABLE `STUDENT_VOTE` ADD CONSTRAINT `PK_STUDENT_VOTE` PRIMARY KEY (
                                                                         `ID`
    );

ALTER TABLE `STUDENT` ADD CONSTRAINT `PK_STUDENT` PRIMARY KEY (
                                                               `ID`
    );

ALTER TABLE `AUTHORITY` ADD CONSTRAINT `PK_AUTHORITY` PRIMARY KEY (
                                                                   `ID`
    );

ALTER TABLE `STUDENT_GEAR` ADD CONSTRAINT `PK_STUDENT_GEAR` PRIMARY KEY (
                                                                         `ID`
    );

ALTER TABLE `GEAR` ADD CONSTRAINT `PK_GEAR` PRIMARY KEY (
                                                         `ID`
    );

ALTER TABLE `Ranking` ADD CONSTRAINT `FK_STUDENT_TO_Ranking_1` FOREIGN KEY (
                                                                            `STUDENT_FK`
    )
    REFERENCES `STUDENT` (
                          `ID`
        );

ALTER TABLE `STUDENT_VOTE` ADD CONSTRAINT `FK_STUDENT_TO_STUDENT_VOTE_1` FOREIGN KEY (
                                                                                      `STUDENT_FK`
    )
    REFERENCES `STUDENT` (
                          `ID`
        );

ALTER TABLE `STUDENT_VOTE` ADD CONSTRAINT `FK_VOTE_TO_STUDENT_VOTE_1` FOREIGN KEY (
                                                                                   `VOTE_FK`
    )
    REFERENCES `VOTE` (
                       `ID`
        );

ALTER TABLE `STUDENT` ADD CONSTRAINT `FK_AUTHORITY_TO_STUDENT_1` FOREIGN KEY (
                                                                              `AUTHORITY_FK`
    )
    REFERENCES `AUTHORITY` (
                            `ID`
        );

ALTER TABLE `STUDENT_GEAR` ADD CONSTRAINT `FK_STUDENT_TO_STUDENT_GEAR_1` FOREIGN KEY (
                                                                                      `STUDENT_FK`
    )
    REFERENCES `STUDENT` (
                          `ID`
        );

ALTER TABLE `STUDENT_GEAR` ADD CONSTRAINT `FK_GEAR_TO_STUDENT_GEAR_1` FOREIGN KEY (
                                                                                   `GEAR_FK`
    )
    REFERENCES `GEAR` (
                       `ID`
        );

