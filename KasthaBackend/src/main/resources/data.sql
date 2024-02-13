--Roles Data
INSERT IGNORE INTO role (name)
VALUES
('ADMIN'),
('USER');

--Alignment Data
INSERT IGNORE INTO alignment (name)
VALUES
('VERTICAL'),
('HORIZONTAL');

--Category Data
-- Alignment Id : 1 is Vertical, 2 is Horizontal
INSERT IGNORE INTO category (name, image_url, alignment_id)
VALUES
('Bed', 'https://images.pexels.com/photos/164595/pexels-photo-164595.jpeg?auto=compress&cs=tinysrgb&w=600', 1),
('Bookshelves', 'https://images.pexels.com/photos/13883390/pexels-photo-13883390.jpeg?auto=compress&cs=tinysrgb&w=600', 2),
('Desk', 'https://images.pexels.com/photos/237436/pexels-photo-237436.jpeg?auto=compress&cs=tinysrgb&w=600', 1),
('Lamp', 'https://images.pexels.com/photos/45072/pexels-photo-45072.jpeg?auto=compress&cs=tinysrgb&w=600', 1),
('Mirror', 'https://images.pexels.com/photos/7045762/pexels-photo-7045762.jpeg?auto=compress&cs=tinysrgb&w=600', 2),
('Sofa', 'https://images.pexels.com/photos/271648/pexels-photo-271648.jpeg?auto=compress&cs=tinysrgb&w=600', 1),
('Table', 'https://images.pexels.com/photos/159839/office-home-house-desk-159839.jpeg?auto=compress&cs=tinysrgb&w=600', 1),
('Wall Art', 'https://images.pexels.com/photos/5653734/pexels-photo-5653734.jpeg?auto=compress&cs=tinysrgb&w=600', 2),
('Wardrobe', 'https://images.pexels.com/photos/5531540/pexels-photo-5531540.jpeg?auto=compress&cs=tinysrgb&w=600', 1);