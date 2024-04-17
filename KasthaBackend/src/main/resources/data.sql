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
('Bookshelves', 'https://images.pexels.com/photos/13883390/pexels-photo-13883390.jpeg?auto=compress&cs=tinysrgb&w=600', 1),
('Desk', 'https://images.pexels.com/photos/237436/pexels-photo-237436.jpeg?auto=compress&cs=tinysrgb&w=600', 1),
('Lamp', 'https://images.pexels.com/photos/45072/pexels-photo-45072.jpeg?auto=compress&cs=tinysrgb&w=600', 1),
('Mirror', 'https://images.pexels.com/photos/7045762/pexels-photo-7045762.jpeg?auto=compress&cs=tinysrgb&w=600', 2),
('Sofa', 'https://images.pexels.com/photos/271648/pexels-photo-271648.jpeg?auto=compress&cs=tinysrgb&w=600', 1),
('Table', 'https://images.pexels.com/photos/159839/office-home-house-desk-159839.jpeg?auto=compress&cs=tinysrgb&w=600', 1),
('Wall Art', 'https://images.pexels.com/photos/5653734/pexels-photo-5653734.jpeg?auto=compress&cs=tinysrgb&w=600', 2),
('Wardrobe', 'https://images.pexels.com/photos/5531540/pexels-photo-5531540.jpeg?auto=compress&cs=tinysrgb&w=600', 1);


-- Object Models for products
INSERT IGNORE INTO object_model(id, url)
VALUES
(1, "model/bed_antique_wodden.glb"),
(2, "model/bed_children.glb"),
(3, "model/bed_headrest_white.glb"),
(4, "model/bed_king.glb"),
(5, "model/bed_normal.glb"),
(6, "model/bed_premium.glb"),
(7, "model/bed_simple_bunk.glb"),
(8, "model/bookshelf_deluxe.glb"),
(9, "model/bookshelf_dusty_old.glb"),
(10,"model/bookshelf_large.glb"),
(11,"model/bookshelf.glb");

-- Bed Products
INSERT IGNORE INTO product(id, name, price, stock_quantity, category_id, model_id, description)
VALUES
(1, "Antique Wodden Bed", 24000, 20, 1, 1, "Exquisite antique wooden bed frame meticulously crafted from solid oak, boasting intricate carvings that exude timeless elegance. Its robust construction ensures both durability and charm, offering a regal centerpiece for any bedroom. Embrace the allure of vintage craftsmanship with this majestic piece, perfect for those seeking to infuse their space with character and sophistication."),
(2, "Children Bunk Bed", 14000, 18, 1, 2, "Introducing our charming child bed featuring a delightful pink and white color scheme with a playful bunk design. Crafted with safety and style in mind, this bed offers a cozy sleeping space for your little one while adding a touch of whimsy to their bedroom decor. The bunk design maximizes floor space, perfect for smaller rooms or for accommodating sleepovers with friends. With its sturdy construction and adorable aesthetic, this bed is sure to be a favorite among children and parents alike."),
(3, "Bed with Headrest", 30000, 4, 1, 3, "Elevate your bedroom with our luxurious Bed with Headrest. This elegant piece features a sleek design with a built-in headrest, providing ultimate comfort and style. Crafted from premium materials, this bed offers durability and sophistication, making it the perfect centerpiece for your bedroom."),
(4, "King Bed", 35000, 7, 1, 4, "Indulge in the grandeur of our King Bed. Fit for royalty, this expansive bed offers ample space for a truly regal sleep experience. With its sturdy construction and majestic presence, it exudes luxury and comfort, transforming your bedroom into a palace of relaxation and opulence."),
(5, "Normal Bed", 8000, 60, 1, 5, "Experience the perfect blend of simplicity and functionality with our Normal Bed. Designed for everyday comfort, this classic bed offers a cozy retreat after a long day. Its timeless appeal and practicality make it a versatile choice for any bedroom."),
(6, "Premium Bed", 39000, 10, 1, 6, "Indulge in unparalleled luxury with our Premium Bed. Featuring exquisite craftsmanship and premium materials, this bed epitomizes elegance and sophistication. Treat yourself to the ultimate sleeping experience with this refined centerpiece for your bedroom."),
(7, "Bunk Bed", 12000, 40, 1, 7, "Maximize space and fun with our Bunk Bed. Perfect for siblings or sleepovers, this playful design combines functionality with style. Crafted with safety in mind, it offers a sturdy and secure sleeping solution for kids, while its charming aesthetic adds a touch of whimsy to any room."),
(8, "Deluxe Bookshelf", 20000, 20, 2, 8, "Organize your library in style with our Deluxe Bookshelf. Featuring a sophisticated design and ample storage space, this bookshelf is perfect for displaying your favorite books and decorative items. Add a touch of elegance to your living space with this versatile and practical piece of furniture."),
(9, "Dusty Bookshelf", 14000, 10, 2, 9, "Add rustic charm to your home with our Dusty Bookshelf. With its weathered finish and vintage-inspired design, this bookshelf exudes character and warmth. Whether showcasing your book collection or decorative accents, it brings a touch of nostalgia to any room."),
(10, "Large Bookshelf", 18000, 7, 2, 10, "Make a statement with our Large Bookshelf. Boasting generous shelf space and a contemporary design, this bookshelf offers both style and functionality. Organize your belongings with ease while adding a modern touch to your living space."),
(11, "Bookshelf", 4000, 52, 2, 11, "Simplify storage with our versatile Bookshelf. Featuring a minimalist design and compact size, this bookshelf fits seamlessly into any room. Whether used for books, decor, or storage baskets, it offers practicality without compromising on style.");


-- Images
INSERT IGNORE INTO image(url, product_id)
VALUES
('image/bed_antique_wodden.png', 1),
('image/bed_children.png', 2),
('image/bed_headrest_white.png', 3),
('image/bed_king.png', 4),
('image/bed_normal.png', 5),
('image/bed_premium.png', 6),
('image/bed_simple_bunk.png', 7),
('image/bookshelf_deluxe.png', 8),
('image/bookshelf_dusty_old.png', 9),
('image/bookshelf_large.png', 10),
('image/bookshelf.png', 11);

