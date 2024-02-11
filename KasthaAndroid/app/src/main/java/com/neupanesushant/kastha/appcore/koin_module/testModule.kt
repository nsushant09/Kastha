package com.neupanesushant.kastha.appcore.koin_module

import com.neupanesushant.kastha.domain.model.Image
import com.neupanesushant.kastha.domain.model.ObjectModel
import com.neupanesushant.kastha.domain.model.Product
import org.koin.core.qualifier.named
import org.koin.dsl.module

val testModule = module {
    single(named("test_object_model")) {
        ObjectModel(1, "")
    }
    single(named("test_image")) {
        Image(1, "")
    }
    single(named("test_product")) {
        Product(
            1,
            "Wodden Sofa",
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
            listOf(
                get(named("test_image")),
                get(named("test_image")),
                get(named("test_image")),
                get(named("test_image")),
                get(named("test_image"))
            ),
            get(named("test_object_model"))
        )
    }

    single<List<String>>(named("test_carousel_images")) {
        listOf<String>(
            "https://images.pexels.com/photos/15927820/pexels-photo-15927820/free-photo-of-cards-and-decorations-for-the-lunar-new-year.jpeg?auto=compress&cs=tinysrgb&w=800&lazy=load",
            "https://images.pexels.com/photos/19040174/pexels-photo-19040174/free-photo-of-a-cup-of-tea-and-flowers-on-the-table.jpeg?auto=compress&cs=tinysrgb&w=800&lazy=load",
            "https://images.pexels.com/photos/6747320/pexels-photo-6747320.jpeg?auto=compress&cs=tinysrgb&w=800&lazy=load",
            "https://images.pexels.com/photos/6908252/pexels-photo-6908252.jpeg?auto=compress&cs=tinysrgb&w=800&lazy=load",
            "https://images.pexels.com/photos/20035207/pexels-photo-20035207/free-photo-of-red-house-by-the-sea.jpeg?auto=compress&cs=tinysrgb&w=800&lazy=load",
            "https://images.pexels.com/photos/3687633/pexels-photo-3687633.jpeg?auto=compress&cs=tinysrgb&w=800&lazy=load"
        )
    }

    single<List<Product>>(named("test_products")) {
        List(10) { _ -> get(named("test_product")) }
    }
}