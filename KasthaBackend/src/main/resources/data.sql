--Roles Data
INSERT IGNORE INTO role (name)
VALUES
('ADMIN'),
('USER');

INSERT IGNORE INTO `user` (id, first_name, last_name, email, password, gender, location)
VALUES (1, 'Kastha', 'Administrator', 'freemanurbanstore@gmail.com', '$2a$10$wauVe9DAhTgW5iRXPQSPYOvjKsri.uBB85KhCKrowNQQYNFDucjES', 'Male', 'Kathmandu, Nepal');
INSERT INTO user_role (user_id, role_id) VALUES (1, 1);

--Alignment Data
INSERT IGNORE INTO alignment (name)
VALUES
('VERTICAL'),
('HORIZONTAL');

--Category Data
-- Alignment Id : 1 is Vertical, 2 is Horizontal
INSERT IGNORE INTO category (name, image_url, alignment_id)
VALUES
('Bed', 'https://images.pexels.com/photos/164595/pexels-photo-164595.jpeg?auto=compress&cs=tinysrgb&w=600', 2),
('Bookshelves', 'https://images.pexels.com/photos/13883390/pexels-photo-13883390.jpeg?auto=compress&cs=tinysrgb&w=600', 2),
('Desk', 'https://images.pexels.com/photos/237436/pexels-photo-237436.jpeg?auto=compress&cs=tinysrgb&w=600', 2),
('Lamp', 'https://images.pexels.com/photos/45072/pexels-photo-45072.jpeg?auto=compress&cs=tinysrgb&w=600', 2),
('Mirror', 'https://images.pexels.com/photos/7045762/pexels-photo-7045762.jpeg?auto=compress&cs=tinysrgb&w=600', 1),
('Sofa', 'https://images.pexels.com/photos/271648/pexels-photo-271648.jpeg?auto=compress&cs=tinysrgb&w=600', 2),
('Table', 'https://images.pexels.com/photos/159839/office-home-house-desk-159839.jpeg?auto=compress&cs=tinysrgb&w=600', 2),
('Wall Art', 'https://images.pexels.com/photos/5653734/pexels-photo-5653734.jpeg?auto=compress&cs=tinysrgb&w=600', 1),
('Wardrobe', 'https://images.pexels.com/photos/5531540/pexels-photo-5531540.jpeg?auto=compress&cs=tinysrgb&w=600', 2);


-- Object Models for products
INSERT IGNORE INTO object_model(id, url)
VALUES
(1, "model/bed_antique_wooden.glb"),
(2, "model/bed_children.glb"),
(3, "model/bed_headrest_white.glb"),
(4, "model/bed_king.glb"),
(5, "model/bed_normal.glb"),
(6, "model/bed_premium.glb"),
(7, "model/bed_simple_bunk.glb"),
(8, "model/bookshelf_deluxe.glb"),
(9, "model/bookshelf_dusty_old.glb"),
(10,"model/bookshelf_large.glb"),
(11,"model/bookshelf.glb"),
(12, "model/desk_antique_wooden.glb"),
(13, "model/desk_modern_office.glb"),
(14, "model/desk_office.glb"),
(15, "model/desk_reception.glb"),
(16, "model/desk_rounded_office.glb"),
(17, "model/desk_scifi.glb"),
(18, "model/lamp_bed.glb"),
(19, "model/lamp_desk.glb"),
(20, "model/lamp_vintage.glb"),
(21, "model/sofa_armchair.glb"),
(22, "model/sofa_large.glb"),
(23, "model/sofa_leather.glb"),
(24, "model/sofa_normal.glb"),
(25, "model/sofa_persian.glb"),
(26, "model/sofa_premium.glb"),
(27, "model/table_coffee.glb"),
(28, "model/table_ornamental.glb"),
(29, "model/table_round.glb"),
(30, "model/table_wooden.glb"),
(31, "model/table_wooden_picnic.glb"),
(32, "model/mirror_carved.glb"),
(33, "model/mirror_honeycomb.glb"),
(34, "model/mirror_normal.glb"),
(35, "model/sofa_bean_bag.glb"),
(36, "model/sofa_dining.glb"),
(37, "model/wall_clock.glb"),
(38, "model/wall_tiles.glb"),
(39, "model/wardrobe_bedroom.glb");

-- Bed Products
INSERT IGNORE INTO product(id, name, price, stock_quantity, category_id, model_id, description)
VALUES
(1, "Antique Wooden Bed", 42000, 20, 1, 1, "Exquisite antique wooden bed frame meticulously crafted from solid oak, boasting intricate carvings that exude timeless elegance. Its robust construction ensures both durability and charm, offering a regal centerpiece for any bedroom. Embrace the allure of vintage craftsmanship with this majestic piece, perfect for those seeking to infuse their space with character and sophistication."),
(2, "Children Bunk Bed", 14000, 18, 1, 2, "Introducing our charming child bed featuring a delightful pink and white color scheme with a playful bunk design. Crafted with safety and style in mind, this bed offers a cozy sleeping space for your little one while adding a touch of whimsy to their bedroom decor. The bunk design maximizes floor space, perfect for smaller rooms or for accommodating sleepovers with friends. With its sturdy construction and adorable aesthetic, this bed is sure to be a favorite among children and parents alike."),
(3, "Bed with Headrest", 40000, 4, 1, 3, "Elevate your bedroom with our luxurious Bed with Headrest. This elegant piece features a sleek design with a built-in headrest, providing ultimate comfort and style. Crafted from premium materials, this bed offers durability and sophistication, making it the perfect centerpiece for your bedroom."),
(4, "King Bed", 53000, 7, 1, 4, "Indulge in the grandeur of our King Bed. Fit for royalty, this expansive bed offers ample space for a truly regal sleep experience. With its sturdy construction and majestic presence, it exudes luxury and comfort, transforming your bedroom into a palace of relaxation and opulence."),
(5, "Normal Bed", 15000, 60, 1, 5, "Experience the perfect blend of simplicity and functionality with our Normal Bed. Designed for everyday comfort, this classic bed offers a cozy retreat after a long day. Its timeless appeal and practicality make it a versatile choice for any bedroom."),
(6, "Premium Bed", 59000, 10, 1, 6, "Indulge in unparalleled luxury with our Premium Bed. Featuring exquisite craftsmanship and premium materials, this bed epitomizes elegance and sophistication. Treat yourself to the ultimate sleeping experience with this refined centerpiece for your bedroom."),
(7, "Bunk Bed", 22000, 40, 1, 7, "Maximize space and fun with our Bunk Bed. Perfect for siblings or sleepovers, this playful design combines functionality with style. Crafted with safety in mind, it offers a sturdy and secure sleeping solution for kids, while its charming aesthetic adds a touch of whimsy to any room."),
(8, "Deluxe Bookshelf", 20000, 20, 2, 8, "Organize your library in style with our Deluxe Bookshelf. Featuring a sophisticated design and ample storage space, this bookshelf is perfect for displaying your favorite books and decorative items. Add a touch of elegance to your living space with this versatile and practical piece of furniture."),
(9, "Dusty Bookshelf", 14000, 10, 2, 9, "Add rustic charm to your home with our Dusty Bookshelf. With its weathered finish and vintage-inspired design, this bookshelf exudes character and warmth. Whether showcasing your book collection or decorative accents, it brings a touch of nostalgia to any room."),
(10, "Large Bookshelf", 18000, 7, 2, 10, "Make a statement with our Large Bookshelf. Boasting generous shelf space and a contemporary design, this bookshelf offers both style and functionality. Organize your belongings with ease while adding a modern touch to your living space."),
(11, "Bookshelf", 4000, 52, 2, 11, "Simplify storage with our versatile Bookshelf. Featuring a minimalist design and compact size, this bookshelf fits seamlessly into any room. Whether used for books, decor, or storage baskets, it offers practicality without compromising on style."),
(12, "Antique Wooden Desk", 18000, 15, 3, 12, "Enhance your workspace with the timeless elegance of our Antique Wooden Desk. Crafted from rich oak wood, this desk features intricate detailing and ample storage, making it both functional and stylish. Whether used for work or study, its classic design adds a touch of sophistication to any room."),
(13, "Modern Office Desk", 25000, 10, 3, 13, "Upgrade your office space with our sleek Modern Office Desk. With its minimalist design and high-quality materials, this desk combines style and functionality. Featuring spacious storage drawers and a spacious work surface, it provides the perfect setting for productivity and creativity."),
(14, "Office Desk", 12000, 20, 3, 14, "Create a productive work environment with our Office Desk. Designed for simplicity and functionality, this desk offers ample space for your computer, paperwork, and office essentials. Its classic design and sturdy construction make it a practical addition to any home or office."),
(15, "Reception Desk", 30000, 5, 3, 15, "Make a lasting impression with our Reception Desk. Featuring a contemporary design and high-quality materials, this desk is perfect for welcoming guests and clients in style. Its spacious work surface and storage compartments ensure efficient organization and a professional appearance."),
(16, "Rounded Office Desk", 22000, 8, 3, 16, "Upgrade your workspace with our Rounded Office Desk. Featuring a unique curved design and premium materials, this desk adds a touch of elegance to any office or home office. With its spacious work surface and integrated storage, it provides a comfortable and organized setting for work or study."),
(17, "Sci-Fi Desk", 35000, 3, 3, 17, "Step into the future with our Sci-Fi Desk. Inspired by futuristic design concepts, this desk features sleek lines and innovative features. With its built-in lighting and advanced technology integration, it offers a cutting-edge workspace for tech enthusiasts and creative professionals."),
(18, "Bedside Lamp", 5000, 30, 4, 18, "Illuminate your bedroom with our stylish Bedside Lamp. Featuring a classic design and soft lighting, this lamp creates a warm and inviting atmosphere. Its compact size makes it perfect for nightstands or bedside tables, providing both functionality and charm."),
(19, "Desk Lamp", 7000, 25, 4, 19, "Brighten up your workspace with our versatile Desk Lamp. With its adjustable arm and diffused light, this lamp provides optimal illumination for reading, studying, or working. Its sleek design and modern aesthetic make it a stylish addition to any desk or table."),
(20, "Vintage Lamp", 9000, 20, 4, 20, "Add a touch of vintage charm to your home with our Vintage Lamp. Featuring an antique-inspired design and warm lighting, this lamp brings nostalgic appeal to any room. Whether used as a reading lamp or decorative accent, it adds character and ambiance to your living space."),
(21, "Armchair Sofa", 28000, 12, 6, 21, "Relax in style with our luxurious Armchair Sofa. Featuring plush cushions and a classic design, this sofa offers both comfort and elegance. Its compact size makes it perfect for small spaces or cozy corners, while its timeless appeal adds sophistication to any room."),
(22, "Large Sofa", 38000, 6, 6, 22, "Create a cozy gathering space with our Large Sofa. Designed for comfort and style, this sofa features generous seating and plush cushions, making it perfect for lounging or entertaining. Its versatile design and neutral upholstery make it easy to coordinate with any decor."),
(23, "Leather Sofa", 45000, 8, 6, 23, "Elevate your living room with our Leather Sofa. Crafted from premium leather and sturdy wood, this sofa combines luxury and durability. With its timeless design and comfortable seating, it offers a sophisticated centerpiece for any home."),
(24, "Normal Sofa", 20000, 20, 6, 24, "Experience everyday comfort with our Normal Sofa. Featuring a classic design and durable construction, this sofa provides a cozy retreat for relaxation and socializing. Its versatile style and neutral upholstery make it a timeless addition to any living space."),
(25, "Persian Sofa", 60000, 4, 6, 25, "Add a touch of opulence to your home with our Persian Sofa. Inspired by traditional Persian design, this sofa features intricate patterns and luxurious fabrics, creating a stunning focal point for your living room. With its plush cushions and elegant silhouette, it offers both comfort and style."),
(26, "Premium Sofa", 55000, 5, 6, 26, "Indulge in luxury with our Premium Sofa. Featuring premium materials and exquisite craftsmanship, this sofa epitomizes elegance and sophistication. Its timeless design and superior comfort make it the perfect centerpiece for any upscale living space."),
(27, "Coffee Table", 10000, 25, 7, 27, "Complete your living room with our stylish Coffee Table. Featuring a contemporary design and durable construction, this table offers both form and function. Its spacious surface and lower shelf provide ample space for books, magazines, and decor, making it a practical addition to any home."),
(28, "Ornamental Table", 15000, 15, 7, 28, "Add a touch of elegance to your living space with our Ornamental Table. Featuring intricate detailing and a sophisticated design, this table serves as both a functional surface and a decorative accent. Its versatile style makes it perfect for any room in your home."),
(29, "Round Table", 8000, 20, 7, 29, "Gather around our Round Table for stylish dining or entertaining. With its classic design and sturdy construction, this table provides a versatile setting for meals, games, or conversation. Its compact size makes it perfect for smaller spaces or cozy dining nooks."),
(30, "Wooden Table", 12000, 18, 7, 30, "Enhance your dining experience with our Wooden Table. Crafted from solid wood and featuring a timeless design, this table brings warmth and character to any dining room. Its sturdy construction and spacious surface make it perfect for family meals and gatherings."),
(31, "Picnic Table", 9000, 10, 7, 31, "Enjoy outdoor dining with our Picnic Table. Made from durable wood and designed for convenience, this table is perfect for picnics, barbecues, or camping trips. Its compact size and foldable design make it easy to transport and store, allowing you to dine al fresco wherever you go."),
(32, "Carved Mirror", 15000, 15, 5, 32, "Add a touch of elegance to your space with our Carved Mirror. Featuring intricate carvings and a classic design, this mirror serves as both a functional piece and a decorative accent, perfect for any room in your home."),
(33, "Honeycomb Mirror", 12000, 10, 5, 33, "Make a statement with our Honeycomb Mirror. Crafted with a unique honeycomb pattern and sleek frame, this mirror adds a modern touch to your space while providing a reflective surface for checking your appearance or enhancing the visual depth of a room."),
(34, "Mirror", 8000, 20, 5, 34, "Complete your decor with our versatile Mirror. Featuring a simple yet stylish design, this mirror blends seamlessly into any room, adding light and depth to your space. Hang it in your hallway, bedroom, or living room for a practical and decorative touch."),
(35, "Bean Bag", 5000, 30, 6, 35, "Relax in comfort with our Bean Bag. Perfect for lounging, reading, or watching TV, this bean bag offers cozy support and casual style. Its lightweight design makes it easy to move from room to room, providing flexible seating wherever you need it."),
(36, "Dining Chair", 6000, 40, 6, 36, "Upgrade your dining experience with our stylish Dining Chair. Featuring a classic design and sturdy construction, this chair provides comfortable seating for family meals or dinner parties. Its timeless appeal and versatile style make it a perfect addition to any dining room."),
(37, "Wall Clock", 7000, 25, 8, 37, "Keep track of time in style with our Wall Clock. Featuring a sleek design and reliable quartz movement, this clock adds both function and flair to your space. Hang it in your kitchen, living room, or office for a touch of modern elegance."),
(38, "Wall Tiles", 10000, 20, 8, 38, "Enhance your walls with our decorative Wall Tiles. Featuring a variety of patterns and colors, these tiles add texture and visual interest to any room. Whether used as a backsplash in your kitchen or an accent wall in your bathroom, they bring personality and charm to your space."),
(39, "Bedroom Closet", 35000, 8, 9, 39, "Organize your clothes and accessories with our Bedroom Closet. Featuring ample storage space and a sleek design, this closet offers both functionality and style. Its adjustable shelves and hanging rods allow for customizable storage solutions, while its modern aesthetic complements any bedroom decor.");


-- Images
INSERT IGNORE INTO image(url, product_id)
VALUES
('image/bed_antique_wooden.png', 1),
('image/bed_children.png', 2),
('image/bed_headrest_white.png', 3),
('image/bed_king.png', 4),
('image/bed_normal.png', 5),
('image/bed_premium.png', 6),
('image/bed_simple_bunk.png', 7),
('image/bookshelf_deluxe.png', 8),
('image/bookshelf_dusty_old.png', 9),
('image/bookshelf_large.png', 10),
('image/bookshelf.png', 11),
('image/desk_antique_wooden.png', 12),
('image/desk_modern_office.png', 13),
('image/desk_office.png', 14),
('image/desk_reception.png', 15),
('image/desk_rounded_office.png', 16),
('image/desk_scifi.png', 17),
('image/lamp_bed.png', 18),
('image/lamp_desk.png', 19),
('image/lamp_vintage.png', 20),
('image/sofa_armchair.png', 21),
('image/sofa_large.png', 22),
('image/sofa_leather.png', 23),
('image/sofa_normal.png', 24),
('image/sofa_persian.png', 25),
('image/sofa_premium.png', 26),
('image/table_coffee.png', 27),
('image/table_ornamental.png', 28),
('image/table_round.png', 29),
('image/table_wooden.png', 30),
('image/table_wooden_picnic.png', 31),
('image/mirror_carved.png', 32),
('image/mirror_honeycomb.png', 33),
('image/mirror_normal.png', 34),
('image/sofa_bean_bag.png', 35),
('image/sofa_dining.png', 36),
('image/wall_clock.png', 37),
('image/wall_tiles.png', 38),
('image/wardrobe_bedroom.png', 39);
