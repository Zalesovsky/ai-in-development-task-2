-- Insert Geo locations
INSERT INTO springapp.geo (lat, lng) VALUES
('40.7128', '-74.0060'),  -- New York
('51.5074', '-0.1278'),   -- London
('48.8566', '2.3522'),    -- Paris
('35.6762', '139.6503'),  -- Tokyo
('55.7558', '37.6173'),   -- Moscow
('52.5200', '13.4050'),   -- Berlin
('41.9028', '12.4964'),   -- Rome
('37.7749', '-122.4194'), -- San Francisco
('31.2304', '121.4737'),  -- Shanghai
('28.6139', '77.2090');   -- New Delhi

-- Insert Addresses
INSERT INTO springapp.address (street, suite, city, zipcode, geo_id) VALUES
('123 Broadway', 'Suite 100', 'New York', '10001', 1),
('45 Oxford Street', 'Floor 5', 'London', 'W1D 2DZ', 2),
('78 Champs-Élysées', 'Apt 3B', 'Paris', '75008', 3),
('1-1-1 Ginza', 'Room 101', 'Tokyo', '104-0061', 4),
('15 Red Square', 'Office 42', 'Moscow', '109012', 5),
('10 Unter den Linden', 'Suite 200', 'Berlin', '10117', 6),
('Via del Corso 123', 'Floor 4', 'Rome', '00186', 7),
('Market Street 1000', 'Suite 500', 'San Francisco', '94103', 8),
('Nanjing Road 1', 'Tower 2', 'Shanghai', '200001', 9),
('Connaught Place 1', 'Office 10', 'New Delhi', '110001', 10);

-- Insert Companies
INSERT INTO springapp.company (name, catch_phrase, bs) VALUES
('Tech Innovators', 'Building the Future', 'Enterprise Software Solutions'),
('Global Services', 'Connecting Worlds', 'International Business Services'),
('Digital Dynamics', 'Transforming Digital', 'Digital Transformation Experts'),
('Smart Solutions', 'Smart for Everyone', 'AI and Machine Learning'),
('Future Systems', 'Tomorrow''s Technology Today', 'Next-Gen Technology');

-- Insert Users
INSERT INTO springapp.users (first_name, last_name, middle_name, email, address_id, phone, website, company_id) VALUES
('John', 'Smith', NULL, 'john.smith@techinnovators.com', 1, '+1-555-0101', 'www.johnsmith.com', 1),
('Emma', 'Wilson', NULL, 'emma.wilson@globalservices.com', 2, '+44-20-1234-5678', 'www.emmawilson.com', 2),
('Pierre', 'Dubois', NULL, 'pierre.dubois@digitaldynamics.com', 3, '+33-1-2345-6789', 'www.pierredubois.com', 3),
('Yuki', 'Tanaka', NULL, 'yuki.tanaka@smartsolutions.com', 4, '+81-3-1234-5678', 'www.yukitanaka.com', 4),
('Ivan', 'Petrov', 'Sergeevich', 'ivan.petrov@futuresystems.com', 5, '+7-495-123-4567', 'www.ivanpetrov.com', 5),
('Maria', 'Schmidt', NULL, 'maria.schmidt@techinnovators.com', 6, '+49-30-1234-5678', 'www.mariaschmidt.com', 1),
('Luca', 'Rossi', NULL, 'luca.rossi@globalservices.com', 7, '+39-06-1234-5678', 'www.lucarossi.com', 2),
('Sarah', 'Johnson', 'Elizabeth', 'sarah.johnson@digitaldynamics.com', 8, '+1-415-555-0123', 'www.sarahjohnson.com', 3),
('Wei', 'Chen', NULL, 'wei.chen@smartsolutions.com', 9, '+86-21-1234-5678', 'www.weichen.com', 4),
('Priya', 'Patel', NULL, 'priya.patel@futuresystems.com', 10, '+91-11-1234-5678', 'www.priyapatel.com', 5); 
