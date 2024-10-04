-- auto-generated definition
create table admin
(
    admin_pk     uuid         not null
        primary key,
    id           varchar(255) null,
    password     varchar(255) null,
    authority_fk uuid         not null,
    constraint FKk67v5sw6otnq1bq75i6jbbfpk
        foreign key (authority_fk) references authority (authority_pk)
);

-- auto-generated definition
create table authority
(
    authority_pk uuid                   not null
        primary key,
    role         enum ('ADMIN', 'USER') null
);

-- auto-generated definition
create table student
(
    student_pk   uuid         not null
        primary key,
    email        varchar(255) null,
    majority     varchar(255) null,
    member_type  varchar(255) null,
    name         varchar(255) null,
    student_id   varchar(255) null,
    authority_fk uuid         not null,
    constraint FKbc14e1oujbi7284oi9c5kegov
        foreign key (authority_fk) references authority (authority_pk)
);

-- auto-generated definition
create table student_vote
(
    student_vote_pk uuid not null
        primary key,
    choice          int  null,
    student_fk      uuid not null,
    vote_fk         uuid not null,
    constraint FKham45vyv1nd1nowc9uujtm0vp
        foreign key (student_fk) references student (student_pk),
    constraint FKmtkdhexiyae7j75jrpbh0ndma
        foreign key (vote_fk) references vote (vote_pk)
);

-- auto-generated definition
create table vote
(
    vote_pk      uuid         not null
        primary key,
    content      varchar(255) null,
    limit_people int          null,
    start_date   datetime(6)  null,
    submit_date  datetime(6)  null,
    title        varchar(255) null
);

