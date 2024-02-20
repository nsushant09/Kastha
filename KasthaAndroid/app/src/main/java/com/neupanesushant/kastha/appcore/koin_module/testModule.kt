package com.neupanesushant.kastha.appcore.koin_module

import com.neupanesushant.kastha.domain.model.Alignment
import com.neupanesushant.kastha.domain.model.Category
import com.neupanesushant.kastha.domain.model.Image
import com.neupanesushant.kastha.domain.model.ObjectModel
import com.neupanesushant.kastha.domain.model.Product
import com.neupanesushant.kastha.domain.model.Review
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.sql.Date
import java.time.Instant

val testModule = module {
    single(named("test_object_model")) {
        ObjectModel(1, "")
    }
    single(named("test_image")) {
        Image(
            1,
            "https://img.freepik.com/free-photo/green-lifestyle-chair-white-background-furniture_1122-1833.jpg?size=626&ext=jpg&ga=GA1.1.1827367055.1703056848&semt=sph"
        )
    }

    single(named("test_image_2")) {
        Image(
            2,
            "https://img.freepik.com/free-photo/room-interior-hotel-bedroom_23-2150683419.jpg?size=626&ext=jpg&ga=GA1.1.1827367055.1703056848&semt=sph"
        )
    }

    single(named("test_image_3")) {
        Image(
            3,
            "https://img.freepik.com/free-photo/table-set-dinning-table_1339-3408.jpg?size=626&ext=jpg&ga=GA1.1.1827367055.1703056848&semt=ais"
        )
    }

    single(named("test_image_4")) {
        Image(
            4,
            "https://img.freepik.com/free-psd/wall-mockup-psd-modern-living-room_53876-129061.jpg?size=626&ext=jpg&ga=GA1.1.1827367055.1703056848&semt=ais"
        )
    }

    single(named("test_image_5")) {
        Image(
            5,
            "https://img.freepik.com/free-photo/desk-arrangement-with-book-lamp_23-2148745771.jpg?size=626&ext=jpg&ga=GA1.1.1827367055.1703056848&semt=ais"
        )
    }

    single {
        Alignment(1, "Horizontal")
    }

    single(named("category_bed")) {
        Category(
            1,
            "Bed",
            "https://img.freepik.com/free-photo/3d-rendering-modern-luxury-pastel-bedroom-with-marble-carpet-decor-deluxe-hotel_105762-2290.jpg?size=626&ext=jpg&ga=GA1.1.1827367055.1703056848&semt=sph",
            get()
        )
    }
    single(named("category_chair")) {
        Category(
            2,
            "Chair",
            "https://images.pexels.com/photos/2495555/pexels-photo-2495555.jpeg?auto=compress&cs=tinysrgb&w=800",
            get()
        )
    }
    single(named("category_table")) {
        Category(
            3,
            "Table",
            "https://images.pexels.com/photos/890669/pexels-photo-890669.jpeg?auto=compress&cs=tinysrgb&w=800",
            get()
        )
    }
    single(named("category_sofa")) {
        Category(
            4,
            "Sofa",
            "https://images.pexels.com/photos/276583/pexels-photo-276583.jpeg?auto=compress&cs=tinysrgb&w=800",
            get()
        )
    }
    single(named("test_categories")) {
        listOf<Category>(
            get(named("category_bed")),
            get(named("category_chair")),
            get(named("category_table")),
            get(named("category_sofa"))
        )
    }


    single(named("test_product")) {
        Product(
            1,
            "Wodden Chair",
            "Introducing our exquisite Wooden Elegance Sofa – a perfect blend of timeless design and unmatched comfort. Crafted with precision from high-quality solid wood, this sofa is not just a piece of furniture; it's a statement of style and sophistication.\n" +
                    "\n" +
                    "The frame of the sofa is constructed from sturdy and durable hardwood, ensuring longevity and resilience. The rich, warm tones of the natural wood create an inviting ambiance in any living space, seamlessly integrating with various interior styles.\n" +
                    "\n" +
                    "The carefully carved details on the arms and legs showcase the artisanal craftsmanship, adding a touch of vintage charm to the overall aesthetic. The wooden sofa is thoughtfully designed to provide both support and relaxation, featuring plush cushions upholstered in a premium fabric that complements the wood's natural beauty.\n" +
                    "\n" +
                    "Its generous seating space makes it ideal for lounging, entertaining, or simply unwinding after a long day. The sofa's ergonomic design ensures optimal comfort, making it a versatile and functional addition to your home.\n" +
                    "\n" +
                    "Whether you're looking to enhance your living room, create a cozy reading nook, or elevate your office waiting area, the Wooden Elegance Sofa effortlessly combines form and function. Invest in quality, style, and comfort with this timeless wooden sofa that transcends trends and adds a touch of sophistication to your living space. Upgrade your home with the enduring appeal of our Wooden Elegance Sofa – where luxury meets longevity.",
            124.1f,
            get(named("category_chair")),
            listOf<Image>(
                get(named("test_image")),
                get(named("test_image_2")),
                get(named("test_image_3")),
                get(named("test_image_4")),
                get(named("test_image_5"))
            ),
            get(named("test_object_model"))
        )
    }

    single<List<String>>(named("test_carousel_images")) {
        listOf<String>(
            "https://img.freepik.com/free-photo/3d-rendering-modern-luxury-pastel-bedroom-with-marble-carpet-decor-deluxe-hotel_105762-2290.jpg?size=626&ext=jpg&ga=GA1.1.1827367055.1703056848&semt=sph",
            "https://img.freepik.com/free-photo/dining-room-arrangement_23-2148893942.jpg?size=626&ext=jpg&ga=GA1.1.1827367055.1703056848&semt=ais",
            "https://img.freepik.com/free-photo/shot-empty-armchair-with-flower-pot_329181-17598.jpg?size=626&ext=jpg&ga=GA1.1.1827367055.1703056848&semt=sph"
        )
    }

    single<List<Product>>(named("test_products")) {
        List(10) { _ -> get(named("test_product")) }
    }

    single<Review>(named("test_review")) {
        Review(
            1,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            5, Date(Date.from(Instant.now()).time), "Sushant Neupane"
        )
    }

    single<List<Review>>(named("test_reviews")) {
        listOf(
            get(named("test_review")),
            get(named("test_review")),
            get(named("test_review")),
            get(named("test_review"))
        )
    }
}