INSERT INTO Empresa (name, description) VALUES
                                                ( 'Tech Innovators', 'A leading company in tech innovation.'),
                                                ( 'Health Solutions', 'Provider of health and wellness solutions.') On conflict do nothing;
/*
-- Insertar datos de prueba en la tabla Ofertante
INSERT INTO Ofertante (id, name, last_name, phone, birthday,reputation, reputation_value, empresa_id) VALUES
                                                                                                              (1, 'Alice', 'Brown',  '555-0101', '1985-04-12','BAJA', 5, 1),
                                                                                                              (2, 'Bob', 'Smith',  '555-0102', '1990-06-15', 'ALTA', 100, 2)On conflict do nothing;
*/
-- Insertar datos de prueba en la tabla JobModality (suponiendo que existe)
INSERT INTO Job_modality (id, name, description) VALUES
                                       (1, 'In person','En esta modalidad trabajas desde una sede asignada'),
                                       (2, 'hybrid','En esta modalidad se trabajar unos dias desde la sede que se le indique al trabajador y desde casa') On conflict do nothing ;

-- Insertar datos de prueba en la tabla JobOffer
INSERT INTO Job_offers (id, title, description, requirements, logo, location, created_at, salary, benefits, status, job_modality_id, ofertante_id) VALUES
                                                                                                                                                   (1, 'Software Engineer', 'Develop and maintain software applications.', 'Java, Spring Boot', 'logo1.png', 'New York', '2024-09-01T09:00:00', 80000, 'Health insurance, 401k', 'ACTIVE', 1, 2),
                                                                                                                                                   (2, 'Data Scientist', 'Analyze data to provide insights.', 'Python, SQL', 'logo2.png', 'San Francisco', '2024-09-05T09:00:00', 90000, 'Health insurance, stock options', 'INACTIVE', 2, 2),
                                                                                                                                                   (3, 'AI Specialist', 'Develop and maintain AI models.', 'Python', 'logo3.png', 'Los Angeles', '2024-09-05T09:00:00', 95000, 'Health insurance, stock options', 'ACTIVE', 2, 2) On conflict do nothing;

-- Insertar datos de prueba en la tabla Postulante
/*INSERT INTO Postulante (id, name, last_name, phone, birthday,created_at, active) VALUES
                                                                                  (1, 'John', 'Doe', '555-0103', '1992-07-21',localtime, TRUE),

                                                                                  (2, 'Jane', 'Doe',  '555-0104', '1993-08-22',localtime, TRUE) On conflict do nothing;

*/

-- Insertar datos de prueba en la tabla Curriculum
INSERT INTO Curriculum (id, postulante_id, content) VALUES
                                                        (1, 2, '{"experience": "3 years as software developer", "education": "BSc Computer Science"}'),
                                                        (2, 3, '{"experience": "2 years as data analyst", "education": "MSc Data Science"}')On conflict do nothing;

-- Insertar datos de prueba en la tabla FavoriteJobOffers
INSERT INTO Favorite_Job_Offers (postulante_id, job_offer_id) VALUES
                                                         (2, 1),
                                                         (3, 2),
                                                         (2, 1)On conflict do nothing;

-- Insertar datos de prueba en la tabla Application
INSERT INTO Application (postulante_id, joboffer_id, date_applied) VALUES
                                                                      (3, 1, '2024-09-10'),
                                                                      (2, 2, '2024-09-11'),
                                                                      (3, 2, '2024-09-12')On conflict do nothing;

INSERT INTO Postulacion (id, estado, fecha_aplicacion, postulante_id, oferta_laboral_id) VALUES
                                                                                             (1, 'Pendiente', '2024-09-10', 3, 1),
                                                                                             (2, 'Aprobado', '2024-09-11', 2, 2),
                                                                                             (3, 'Cancelado', '2024-09-12', 3, 2)ON CONFLICT DO NOTHING;
/*
INSERT INTO Users (id, email,password) values (1,'u201816862@upc.edu.pe','12345678987')ON CONFLICT DO NOTHING;
*/