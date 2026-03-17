INSERT INTO member(name, email, password) VALUES('윤서준', 'SeojunYoon@goodee.co.kr', '$2a$12$qpOpJ13qb.ROkaYvteEKs.mWhls9lTnGpjLj9h3Gpw0/8Y8r1MR1C');
INSERT INTO member(name, email, password) VALUES('김재현', 'geoblo@naver.com', '$2a$12$qpOpJ13qb.ROkaYvteEKs.mWhls9lTnGpjLj9h3Gpw0/8Y8r1MR1C');
INSERT INTO member(name, email, password) VALUES('공미영', 'MiyeongKong@goodee.co.kr', '$2a$12$qpOpJ13qb.ROkaYvteEKs.mWhls9lTnGpjLj9h3Gpw0/8Y8r1MR1C');
INSERT INTO member(name, email, password) VALUES('김도윤', 'DoyunKim@goodee.co.kr', '$2a$12$qpOpJ13qb.ROkaYvteEKs.mWhls9lTnGpjLj9h3Gpw0/8Y8r1MR1C');

INSERT INTO authority(authority, member_id) VALUES('ROLE_ADMIN', 2);

INSERT INTO article(title, description, created, updated, member_id) VALUES('첫번째 게시글 제목', '첫번째 게시글 본문', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);
INSERT INTO article(title, description, created, updated, member_id) VALUES('두번째 게시글 제목', '두번째 게시글 본문', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2);
INSERT INTO article(title, description, created, updated, member_id) VALUES('세번째 게시글 제목', '세번째 게시글 본문', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3);
INSERT INTO article(title, description, created, updated, member_id) VALUES('네번째 게시글 제목', '네번째 게시글 본문', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4);