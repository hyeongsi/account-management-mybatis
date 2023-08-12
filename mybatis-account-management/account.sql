DROP USER account CASCADE;

CREATE USER account
IDENTIFIED BY account;

GRANT connect, resource to account;

CREATE SEQUENCE account.account_id_seq;

CREATE TABLE account.tb_account (
  accountId number PRIMARY KEY,
  name varchar2(20) not null,
  balance number default 0
);

INSERT INTO account.tb_account(accountId, name, balance) 
VALUES (account.account_id_seq.nextval, '정약용', 500);
INSERT INTO account.tb_account(accountId, name, balance) 
VALUES (account.account_id_seq.nextval, '홍길동', 27000);
INSERT INTO account.tb_account(accountId, name, balance) 
VALUES (account.account_id_seq.nextval, '임꺽정', 100000);
INSERT INTO account.tb_account(accountId, name, balance) 
VALUES (account.account_id_seq.nextval, '장길산', 300000);
INSERT INTO account.tb_account(accountId, name, balance) 
VALUES (account.account_id_seq.nextval, '이순신', 16000000);
commit;

select * from account.tb_account;
