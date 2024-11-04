
INSERT INTO Job_offers (id, title, description, requirements, logo, location, created_at, salary, benefits, status, job_modality_id, ofertante_id) VALUES
                                                                                                                                                       (1, 'Software Engineer', 'Develop and maintain software applications.', 'Java, Spring Boot', 'logo1.png', 'New York', '2024-09-01T09:00:00', 80000, 'Health insurance, 401k', 'ACTIVE', 1, 3),
                                                                                                                                                       (2, 'Data Scientist', 'Analyze data to provide insights.', 'Python, SQL', 'logo2.png', 'San Francisco', '2024-09-05T09:00:00', 90000, 'Health insurance, stock options', 'INACTIVE', 2, 3),
                                                                                                                                                       (3, 'AI Specialist', 'Develop and maintain AI models.', 'Python', 'logo3.png', 'Los Angeles', '2024-09-05T09:00:00', 95000, 'Health insurance, stock options', 'ACTIVE', 2, 4) On conflict do nothing;

/*
INSERT INTO Empresa (name, description) VALUES
                                                ( 'Tech Innovators', 'A leading company in tech innovation.'),
                                                ( 'Health Solutions', 'Provider of health and wellness solutions.') On conflict do nothing;



Insert into roles (name) Values
('ADMIN'),('POSTULANTE'),('OFERTANTE');

INSERT INTO Users (email, password, role_id) values
('admin@example.com','$2a$10$cK/KHDRnwST/9TKkxt6Ee.rq10rL4L6/BeC/6S7QwDyFZmcXyysFC', 1),
('Alice@example.com','$2a$10$MNwZ7LYso.sQvHhxt9/cb.4hN.0qmTy.JYmFzjot06QJsGKn3aYKa', 3),
('Bob@example.com','$2a$10$MNwZ7LYso.sQvHhxt9/cb.4hN.0qmTy.JYmFzjot06QJsGKn3aYKa', 3),
('John@example.com','$2a$10$MNwZ7LYso.sQvHhxt9/cb.4hN.0qmTy.JYmFzjot06QJsGKn3aYKa', 2),
('Jane@example.com','$2a$10$MNwZ7LYso.sQvHhxt9/cb.4hN.0qmTy.JYmFzjot06QJsGKn3aYKa', 2) ON CONFLICT DO NOTHING;


INSERT INTO Ofertante (name, last_name, phone, user_id, birthday, created_at, reputation_value, empresa_id) VALUES
('example','ofertante','123456789',1,'2001-05-21T14:30:00.000+00:00',now(),100,1),
( 'Alice', 'Brown',  '555-0101',2 ,'1985-04-12',now(), 5, 1),
( 'Bob', 'Smith',  '555-0102', 3,'1990-06-15', now(),100, 2) On conflict do nothing;


INSERT INTO Postulante (name, last_name, phone, user_id, birthday, created_at, active) VALUES
('example','postulante','123456789',1,'2001-05-21T14:30:00.000+00:00',now(), TRUE),
('John', 'Doe', '555-0103', 4,'1992-07-21',now(), TRUE),
('Jane', 'Doe',  '555-0104', 5,'1993-08-22',now(), TRUE) On conflict do nothing;



-- Insertar datos de prueba en la tabla JobModality (suponiendo que existe)
INSERT INTO Job_modality (id, name, description) VALUES
                                                     (1, 'In person','En esta modalidad trabajas desde una sede asignada'),
                                                     (2, 'hybrid','En esta modalidad se trabajar unos dias desde la sede que se le indique al trabajador y desde casa') On conflict do nothing ;

-- Insertar datos de prueba en la tabla JobOffer
INSERT INTO Job_offers (id, title, description, requirements, logo, location, created_at, salary, benefits, status, job_modality_id, ofertante_id) VALUES
                                                                                                                                                   (1, 'Software Engineer', 'Develop and maintain software applications.', 'Java, Spring Boot', 'logo1.png', 'New York', '2024-09-01T09:00:00', 80000, 'Health insurance, 401k', 'ACTIVE', 1, 3),
                                                                                                                                                   (2, 'Data Scientist', 'Analyze data to provide insights.', 'Python, SQL', 'logo2.png', 'San Francisco', '2024-09-05T09:00:00', 90000, 'Health insurance, stock options', 'INACTIVE', 2, 3),
                                                                                                                                                   (3, 'AI Specialist', 'Develop and maintain AI models.', 'Python', 'logo3.png', 'Los Angeles', '2024-09-05T09:00:00', 95000, 'Health insurance, stock options', 'ACTIVE', 2, 4) On conflict do nothing;



-- Insertar datos de prueba en la tabla Curriculum
INSERT INTO Curriculum (id, postulante_id, content) VALUES
                                                        (1, 1, '{"experience": "3 years as software developer", "education": "BSc Computer Science"}'),
                                                        (2, 5, '{"experience": "2 years as data analyst", "education": "MSc Data Science"}')On conflict do nothing;

-- Insertar datos de prueba en la tabla FavoriteJobOffers
INSERT INTO Favorite_Job_Offers (postulante_id, job_offer_id) VALUES
                                                         (1, 1),
                                                         (5, 2),
                                                         (5, 1)On conflict do nothing;

-- Insertar datos de prueba en la tabla Application
INSERT INTO Application (postulante_id, joboffer_id, date_applied) VALUES
                                                                      (5, 1, '2024-09-10'),
                                                                      (1, 2, '2024-09-11'),
                                                                      (5, 2, '2024-09-12')On conflict do nothing;

INSERT INTO Postulacion (id, estado, fecha_aplicacion, postulante_id, oferta_laboral_id) VALUES
                                                                                             (1, 'Pendiente', '2024-09-10', 5, 1),
                                                                                             (2, 'Aprobado', '2024-09-11', 1, 2),
                                                                                             (3, 'Cancelado', '2024-09-12', 5, 2)ON CONFLICT DO NOTHING;

/*
INSERT INTO Users (id, email,password) values (1,'u201816862@upc.edu.pe','12345678987')ON CONFLICT DO NOTHING;
*/

/*
INSERT INTO roles (id, name)
VALUES
    (1, 'ADMIN'),
    (2, 'POSTUlANTE'),
    (3, 'OFERTANTE');
*/