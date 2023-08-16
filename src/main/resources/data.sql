INSERT INTO skyeng_postoffice (id, created, updated, index, post_name) VALUES (1, current_timestamp, current_timestamp, '150006', 'Почта Кукушка');
INSERT INTO skyeng_postoffice (id, created, updated, index, post_name) VALUES (2, current_timestamp, current_timestamp, '150007', 'Почта Колотушка');

INSERT INTO skyeng_mail (id, mail_type, address_recipient, index_recipient, name_recipient, state, postoffice_id, created, updated) VALUES (1, 'LETTER', 'кукушкино', '150006', 'VM4TECH', 'CREATED', 1, current_timestamp, current_timestamp);
INSERT INTO skyeng_mail (id, mail_type, address_recipient, index_recipient, name_recipient, state, postoffice_id, created, updated) VALUES (2, 'PACKAGE', 'колотушкино', '150007', 'VM4TECH', 'CREATED', 1, current_timestamp, current_timestamp);

INSERT INTO skyeng_mail (id, mail_type, address_recipient, index_recipient, name_recipient, state, postoffice_id, created, updated) VALUES (3, 'PACKAGE','none)', '150008', 'VM4TECH', 'CREATED', 2, current_timestamp, current_timestamp);

