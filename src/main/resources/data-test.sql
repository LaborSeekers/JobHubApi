INSERT INTO Empresa (id, name, description) VALUES
                                                (1, 'Tech Innovators', 'A leading company in tech innovation.'),
                                                (2, 'Health Solutions', 'Provider of health and wellness solutions.') On conflict do nothing;

-- Insertar datos de prueba en la tabla Ofertante
INSERT INTO Ofertante (id, name, last_name, email, password, phone, birthday, reputation_value, empresa_id) VALUES
                                                                                                              (1, 'Alice', 'Brown', 'alice.brown@example.com', 'password123', '555-0101', '1985-04-12', 5, 1),
                                                                                                              (2, 'Bob', 'Smith', 'bob.smith@example.com', 'password456', '555-0102', '1990-06-15', 4, 2)On conflict do nothing;

-- Insertar datos de prueba en la tabla JobModality (suponiendo que existe)
INSERT INTO Job_modality (id, name, description) VALUES
                                       (1, 'In person','En esta modalidad trabajas desde una sede asignada'),
                                       (2, 'hybrid','En esta modalidad se trabajar unos dias desde la sede que se le indique al trabajador y desde casa') On conflict do nothing ;

-- Insertar datos de prueba en la tabla JobOffer
INSERT INTO Job_offers (id, title, description, requirements, logo, location, created_at, salary, benefits, status, job_modality_id, ofertante_id) VALUES
                                                                                                                                                   (1, 'Software Engineer', 'Develop and maintain software applications.', 'Java, Spring Boot', 'logo1.png', 'New York', '2024-09-01T09:00:00', 80000, 'Health insurance, 401k', 'ACTIVE', 1, 1),
                                                                                                                                                   (2, 'Data Scientist', 'Analyze data to provide insights.', 'Python, SQL', 'logo2.png', 'San Francisco', '2024-09-05T09:00:00', 90000, 'Health insurance, stock options', 'INACTIVE', 2, 2),
                                                                                                                                                   (3, 'AI Specialist', 'Develop and maintain AI models.', 'Python', 'logo3.png', 'Los Angeles', '2024-09-05T09:00:00', 95000, 'Health insurance, stock options', 'ACTIVE', 2, 2) On conflict do nothing;

-- Insertar datos de prueba en la tabla Postulante
INSERT INTO Postulante (id, name, last_name, email, password, phone, birthday, active) VALUES
                                                                                  (1, 'John', 'Doe', 'john.doe@example.com', 'password789', '555-0103', '1992-07-21', TRUE),
                                                                                  (2, 'Jane', 'Doe', 'jane.doe@example.com', 'password101', '555-0104', '1993-08-22', TRUE) On conflict do nothing;

-- Insertar datos de prueba en la tabla Curriculum
INSERT INTO Curriculum (id, postulante_id, content) VALUES
                                                        (1, 1, '{"experience": "3 years as software developer", "education": "BSc Computer Science"}'),
                                                        (2, 2, '{"experience": "2 years as data analyst", "education": "MSc Data Science"}')On conflict do nothing;

-- Insertar datos de prueba en la tabla FavoriteJobOffers
INSERT INTO Favorite_Job_Offers (postulante_id, job_offer_id) VALUES
                                                         (1, 1),
                                                         (1, 2),
                                                         (2, 1)On conflict do nothing;

-- Insertar datos de prueba en la tabla Application
INSERT INTO Application (postulante_id, joboffer_id, date_applied) VALUES
                                                                      (1, 1, '2024-09-10'),
                                                                      (2, 2, '2024-09-11'),
                                                                      (1, 2, '2024-09-12')On conflict do nothing;