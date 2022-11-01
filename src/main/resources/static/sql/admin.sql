/**
  나이 통계낼 때 필요한 테이블
 */
create table agetable(age bigint(2));
insert into agetable value(0);
insert into agetable value(1);
insert into agetable value(2);
insert into agetable value(3);
insert into agetable value(4);
insert into agetable value(5);
insert into agetable value(6);
insert into agetable value(7);
insert into agetable value(8);
insert into agetable value(9);


/*
 admin 아이디 비번 통일
 */
 insert into member(memberId, memberBirthday, memberName, memberPhoneNum, memberPw) value('admin1101', '1900-01-01', 'admin', '010-0000-0000', 'asdfasdf1!');
