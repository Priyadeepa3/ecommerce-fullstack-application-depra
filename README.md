# Fullstack_Ecommerce_Application_Depra 

# 🛍️ E-Commerce Full Stack Application

## 📌 Overview

This is a full-stack e-commerce web application designed to simulate real-world online shopping systems. It includes secure authentication, product browsing, cart management, order processing, and advanced backend features like email services and global exception handling.

The project focuses on building a scalable and secure backend using Spring Boot, along with a simple frontend using HTML, CSS, and JavaScript.

---

## ✨ Features

### 🔐 Authentication & Security

* JWT-based authentication and authorization
* Secure login and registration system
* Role-based access (User/Admin)
* Stateless session management using Spring Security

---

### 🛒 E-Commerce Functionalities

* Product listing and product details
* Add to cart / remove from cart
* Order placement and order summary
* Wishlist functionality
* Address management
* Order tracking system

---

### 📧 Forgot Password (Email Service)

* Implemented password reset functionality using email
* Secure reset link generation
* Link expiration handling (1 hour)
* Integrated `JavaMailSender` for sending emails

---

### ⚙️ Backend Architecture

* RESTful API design using Spring Boot
* Layered architecture:

  * Controller
  * Service
  * Repository
* DTO pattern for clean data transfer
* Input validation using annotations

---

### 🛡️ Global Exception Handling

* Centralized exception handling using `@RestControllerAdvice`

* Custom exception classes:

  * ResourceNotFoundException

* Handled exceptions:

  * Validation errors
  * Bad credentials (login failure)
  * Illegal arguments
  * Runtime exceptions
  * Generic exceptions

* Structured error response format:

  * Timestamp
  * HTTP status
  * Error message
  * Request path

---

### 🔐 Security Configuration

* Custom `SecurityFilterChain` implementation
* JWT filter for request validation
* Protected routes based on roles:

  * Public APIs
  * Authenticated user APIs
  * Admin APIs
* CSRF disabled for REST APIs

---

### 🌐 CORS Configuration

* Configured Cross-Origin Resource Sharing (CORS)
* Allowed frontend-backend communication
* Supported multiple HTTP methods and headers

---

### 🧭 Web Configuration (Routing)

* Clean URL routing using `WebMvcConfigurer`
* Mapped routes like `/home`, `/products`, `/cart`
* Direct navigation without exposing `.html` in URLs

---

### 🌐 Frontend

* Built using HTML, CSS, and JavaScript
* Integrated with backend APIs
* Dynamic rendering of products and cart data

---

## 🛠️ Tech Stack

### 🔙 Backend

* Java
* Spring Boot
* Spring Security
* JWT Authentication
* Spring Data JPA
* MySQL

### 🔜 Frontend

* HTML
* CSS
* JavaScript

### 🧰 Tools

* Postman (API testing)
* Git & GitHub
* Maven

---

## 🔄 Project Flow

1. User registers and logs in
2. JWT token is generated
3. User browses products
4. Adds products to cart
5. Places order
6. Order stored in database
7. Password reset via email if needed

---

## 📂 Project Structure

```bash
src/
 ├── controller/
 ├── service/
 ├── repository/
 ├── dto/
 ├── entity/
 ├── exception/
 ├── config/
 ├── security/
 └── static/ (frontend files)
```

---

## 🧪 API Testing

* Tested using Postman
* Includes:

  * Authentication APIs
  * Product APIs
  * Cart APIs
  * Order APIs
  * Address APIs

---

## 📸 Screenshots

(Add UI screenshots here)

---

## 🚀 Future Enhancements

* Payment gateway integration
* Admin dashboard with analytics, order management, and product control panel
* Advanced product search with dynamic filtering (by category, price range, availability, keywords, color, material, and occasion)
* Cloud deployment (AWS / Render)

---

## 📢 Conclusion

This project demonstrates strong backend development skills including authentication, API design, exception handling, and secure system architecture. It reflects real-world application development practices and problem-solving ability.

---
