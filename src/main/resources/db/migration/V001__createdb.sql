/* db data model */

create table quiz (
    id varchar (16) not null primary key,
    title varchar (100) not null,
    instructions varchar (255) null,
    showPublic boolean null,
    showCorrect boolean null,
    suffleQuestions boolean null,
    passFraction smallint null,
    -- default for questions
    answernumbering varchar (10) not null,
    shuffleanswers boolean not null,
    -- quiz managememt
    active boolean not null default true,
    owner varchar (100) not null
);

create table question (
    id varchar (16) not null,
    quizId varchar (16) not null,
    text varchar (4000) not null,
    type varchar (25) not null,
    single boolean not null,
    answernumbering varchar (10) not null,
    shuffleanswers boolean not null,
    primary key (quizId, id),
    foreign key (quizId) references quiz(id)
);

create table answer (
    id varchar (16) not null,
    quizId varchar (16) not null,
    questionId varchar (16) not null,
    fraction smallint null,
    text varchar (4000) not null,
    feedback varchar (255) null,
    primary key (quizId, questionId, id),
    foreign key (quizId, questionId) references question(quizId, id)
);