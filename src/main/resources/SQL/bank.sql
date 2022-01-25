DROP TABLE account;

Select *from person;
select *from account;

CREATE TABLE person(
                       person_id SERIAL PRIMARY KEY,
                       last_name VARCHAR(15),
                       first_name VARCHAR(15),
                       login VARCHAR(15),
                       password VARCHAR(15),
                       rank_person VARCHAR(10)
);

CREATE TABLE account(
                        acc_id SERIAL PRIMARY KEY,
                        acc_status VARCHAR(10),
                        balance NUMERIC,
                        acc_number VARCHAR(10),
                        type_acc VARCHAR(10),
                        sec_perName Varchar(10),
                        person_id SERIAL REFERENCES person(person_id) on delete cascade on update no action
);

INSERT INTO person(last_name,first_name,login, password, rank_person) VALUES
    ('Thos', 'PRO', 'Zeratul', '3333', 'ADMIN');

INSERT INTO person(last_name,first_name,login, password, rank_person) VALUES
    ('Pro', 'Thos', 'Karax', '1111', 'EMPLOYEE');