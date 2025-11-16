# 🪵 Kastha – Mobile Application for Furniture Store with Augmented Reality

> **Kastha** is a full-stack mobile application designed for furniture stores, integrating **Augmented Reality (AR)** to enhance the online shopping experience.  
> Built using **Spring Boot** (backend) and **Android Studio with ARCore** (frontend), it enables users to visualize furniture in real environments before purchase and provides admins with powerful management tools.

---

## 🧭 Table of Contents
- [Overview](#overview)
- [Features](#features)
  - [User Features](#user-features)
  - [Admin Features](#admin-features)
- [Tech Stack](#tech-stack)
- [System Architecture](#system-architecture)
- [Installation Guide](#installation-guide)
  - [macOS](#macos)
  - [Windows](#windows)
- [Running the Application](#running-the-application)
- [Augmented Reality Highlights](#augmented-reality-highlights)
- [Contributors](#contributors)
- [License](#license)

---

<a name="overview"></a>
## Overview 🪄

**Kastha** revolutionizes the online furniture shopping experience by integrating **Augmented Reality (AR)**.  
Users can preview 3D models of furniture in their homes before buying, while store administrators manage inventory, track user feedback, and engage in real-time chat with customers.

The system consists of:
- A **Spring Boot backend** (REST API)
- An **Android app** with ARCore integration
- A **MySQL database** for data management

---

<a name="features"></a>
## 🌟 Features

<a name="user-features"></a>
### 👤 User Features
- 🔐 **User Authentication** (Sign-in, Registration, OTP verification)
- 🏠 **Home Page** with personalized recommendations
- 🪑 **Product Search & Filter**
- 💖 **Favorites / Wishlist**
- 🛒 **Cart Management**
- ⭐ **Product Reviews & Ratings**
- 🧍‍♂️ **Profile Management & Update**
- 💬 **Chat with Administrator**
- 🪩 **Augmented Reality View (ARCore)**

<a name="admin-features"></a>
### 🧑‍💼 Admin Features
- 🔑 **Secure Admin Login**
- ➕ **Add New Products**
- 📝 **Update / Edit Existing Products**
- 🗂️ **Manage Product Details & Models**
- 💬 **Chat with Customers**
- 🖼️ **Upload Images and 3D Models**

---

<a name="tech-stack"></a>
## 🧰 Tech Stack

| Category | Technology |
|-----------|-------------|
| **Frontend (Mobile)** | Android (Java/Kotlin), ARCore, XML UI |
| **Backend** | Spring Boot (Gradle Build), REST API |
| **Database** | MySQL |
| **AR Engine** | Google ARCore |
| **Development Tools** | Android Studio, IntelliJ IDEA |
| **Version Control** | Git & GitHub |
| **Build Tools** | Gradle |
| **Others** | Firebase (OTP), Glide, Retrofit |

---

<a name="system-architecture"></a>
## 🧩 System Architecture

The architecture follows a **client-server model** where:
- The **Android app** interacts with the **Spring Boot API**
- **MySQL** handles persistent data storage
- **Firebase** manages authentication and OTP verification
- **ARCore** renders 3D visualization in real environments

---

<a name="installation-guide"></a>
## ⚙️ Installation Guide

<a name="macos"></a>
### 🖥️ macOS

1. **Install MySQL**
   ```bash
   brew install mysql
   mysql_secure_installation
   mysql -u root -p
   CREATE DATABASE kastha_db;

2. **Install Java 17**
   ```bash
   brew install openjdk@17
   export JAVA_HOME=$(/usr/libexec/java_home -v 17)
   source ~/.zshrc
   
3. **Install Spring Boot via SDKMAN**
   ```bash
   curl -s "https://get.sdkman.io" | bash
   source "$HOME/.sdkman/bin/sdkman-init.sh"
   sdk install springboot

4. **Build and Run Backend**
    ```bash
    cd backend/
    ./gradlew build
    nohup java -jar build/libs/kastha.jar &

5. **Run the Android Application**
   - Open the Android project in **Android Studio**
   - Allow Gradle to sync
   - Select a device/emulator
   - Click the **Run** button (green arrow)

---

<a name="windows"></a>
### 💻 Windows

1. **Install MySQL**
   - Download MySQL from the official website
   - Install and configure root password
   - Create the database:
     ```bash
     mysql -u root -p
     CREATE DATABASE kastha_db;
     ```

2. **Install Java JDK 17**
   - Download from Oracle
   - Install and set environment variables:
     ```
     JAVA_HOME = C:\Program Files\Java\jdk-17
     PATH = %JAVA_HOME%\bin
     ```

3. **Install Gradle**
   - Download Gradle ZIP  
   - Extract anywhere  
   - Set environment variable:
     ```
     GRADLE_HOME = C:\Gradle
     PATH = %GRADLE_HOME%\bin
     ```

4. **Build and Run Backend**
   ```bash
   cd backend
   gradlew build
   java -jar build/libs/kastha.jar

<a name="running-the-application"></a>
## 🚀 Running the Application

### **Backend (Spring Boot)**
Start the backend server using Gradle:
    ```
        ./gradlew bootRun

Or Run the built application
    ```
        java -jar build/libs/kastha.jar
The backend then runs at
    ```
        http://localhost:8080/

### 📱 Running the Android Application

To run the Kastha Android mobile application:

1. **Open the project in Android Studio**
   - Launch Android Studio  
   - Click **Open**  
   - Select the project's Android folder  

2. **Allow Gradle to Sync**
   - Android Studio will automatically sync Gradle  
   - Wait until all dependencies are downloaded and the project builds successfully  

3. **Select a Device**
   - Connect a physical Android device **or**  
   - Start an Android Emulator  
   - Make sure the device supports ARCore (for AR features)  

4. **Build the Application**
   - Go to **Build → Clean Project**  
   - Then **Build → Rebuild Project**  

5. **Run the Application**
   - Press the **Run** button (green arrow on the toolbar)  
   - Select your device when prompted  
   - The application will install and launch on the selected device  

6. **Using AR Features**
   - Devices must support **Google ARCore**  
   - If ARCore is missing, the app will prompt for installation  
   - Simply follow the on-screen instructions  

---

### 🔧 Notes
- USB Debugging must be enabled on physical devices  
- Internet connection is required for:
  - Authentication (OTP)  
  - Fetching products  
  - Uploading chat images/audio  
- AR experience requires good lighting for plane detection  

---

<a name="screenshots"></a>

<!-- ## 📸 Screenshots

### 🔐 Authentication
- **Onboarding Screen**  
  ![Onboarding](images/onboarding.png)

- **Login Screen**  
  ![Login](images/login.png)

- **Registration Screen**  
  ![Registration](images/registration.png)

- **OTP Verification**  
  ![OTP](images/otp.png)

---

### 🏠 Home & Browsing
- **Home Page**  
  ![Home Page](images/home.png)

- **Product Detail Page**  
  ![Product Detail](images/product_detail.png)

- **Search Results**  
  ![Search](images/search.png)

---

### 🛒 Cart & Favorites
- **Cart Page**  
  ![Cart](images/cart.png)

- **Favorites / Wishlist**  
  ![Favorites](images/favorites.png)

---

### 🪄 Augmented Reality Mode
- **AR View**  
  ![AR View](images/ar_view.png)

---

### 👤 User Profile
- **User Profile Page**  
  ![User Profile](images/user_profile.png)

- **Update Profile Page**  
  ![Update Profile](images/update_profile.png)

---

### 🧑‍💼 Admin Dashboard
- **Admin Home**  
  ![Admin Home](images/admin_home.png)

- **Add Product Page**  
  ![Add Product](images/admin_add_product.png)

- **Edit Product Page**  
  ![Edit Product](images/admin_edit_product.png)

- **Chat With Customers**  
  ![Chat](images/admin_chat.png) -->

<a name="contributors"></a>
## Contributors

---

<a name="license"></a>
## 📜 License

This project was developed as part of the academic curriculum under  
**The British College** (Leeds Beckett University).

You may use, copy, or modify this project for **learning, research, or portfolio purposes**.  
Commercial use or redistribution without permission is not allowed.

Copyright © 2024 — Sushant Neupane







