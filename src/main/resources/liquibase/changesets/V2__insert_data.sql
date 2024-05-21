insert into users (name, username, password)
values ('SENIOR', 'senior@gmail.com', '$2a$12$qAp2kySTgTH12uSEYfYruO55.iWLFODFBq1/bzkpA/QCBxgFrZvd6'),
       ('JUNIOR', 'junior@yandex.ru', '$2a$12$M8TjLo1ZN3JOX0tnzwNTc.UoD5SlvoRmVQ41EN9fNNjglIwbYYtnu');

insert into books (title, author, description, status, expiration_date_to_read)
values ('Clean Code', 'Robert C. Martin ', null, 'READ_DONE', '2023-01-29 12:00:00'),
       ('Grokking Algorithms', 'Aditya Bhargava',
        'Learning about algorithms doesn''t have to be boring! Get a sneak peek at the fun, illustrated,' ||
        ' and friendly examples you''ll find in Grokking Algorithms on Manning Publications'' YouTube channel.',
        'READ_STARTED', '2024-05-30 00:00:00'),
       ('Java 8 Preview Sampler', 'Herbert Schildt', null, 'PLANNED_TO_READ', null),
       ('Computer Networks 5th', 'Andrew S. Tanenbaum', 'book for megabrains', 'READ_STARTED', '2025-01-01 00:00:00');

insert into users_books (book_id, user_id)
values (1, 1),
       (2, 2),
       (3, 2),
       (4, 1);

insert into users_roles (user_id, role)
values (1, 'ROLE_ADMIN'),
       (1, 'ROLE_USER'),
       (2, 'ROLE_USER');