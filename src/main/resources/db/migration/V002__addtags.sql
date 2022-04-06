create table tag (
    name varchar(30) not null primary key,
    description varchar(255) null
);

create table quiz_tag (
    tagName varchar(30) not null references tag(name),
    quizId varchar (16) not null references quiz(id),
    primary key (tagName, quizId)
);

create table question_tag (
    tagName varchar(30) not null references tag(name),
    quizId varchar (16) not null,
    questionId varchar (16) not null,
    primary key (tagName, questionId, quizId),
    foreign key (quizId, questionId) references question(quizId, id)
);