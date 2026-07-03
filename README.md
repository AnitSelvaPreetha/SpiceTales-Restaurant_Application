# Restaurant Management System

## Overview

The **Restaurant Management System** is a web-based application developed using **Spring Boot** that streamlines restaurant operations for administrators, staff, and customers. The system provides role-based access control and enables efficient management of menus, reservations, table allocation, order processing, billing, and customer feedback.

## Features

### Admin Features

* Secure admin login.
* Add, update, and manage dishes in the restaurant menu.
* Approve or reject customer table reservations.
* Allocate tables while approving reservations.
* Create staff accounts by assigning a username and password.
* Manage restaurant operations efficiently.
* Role-based access control to ensure only authorized users can access administrative features.

### Staff Features

* Secure staff login using credentials created by the administrator.
* View customer orders assigned for processing.
* Update order status throughout the preparation process, including:

  * Preparing
  * Ready
  * Delivered
* View reservation and order details required for daily operations.
* Access only staff-specific functionalities through role-based authorization.

### Customer Features

* Register and log in to the application.
* Browse the restaurant menu.
* Add dishes to the shopping cart.
* Place food orders.
* Track the live status of their orders (Preparing, Ready, Delivered).
* Reserve tables online.
* View reservation status and allocated table details.
* View current and previous orders.
* View detailed bills for completed orders.
* Reorder favorite dishes from order history.
* Submit separate feedback for each ordered dish.
* Manage personal profile information.

## Implemented Functionalities

* User Registration and Login
* Role-Based Authentication and Authorization (Admin, Staff, Customer)
* Menu Management
* Shopping Cart
* Food Ordering System
* Real-Time Order Status Tracking
* Table Reservation System
* Table Allocation by Admin
* Staff Management
* Order History
* Billing System
* Dish-wise Customer Feedback
* Reservation Management
* Responsive User Interface using Thymeleaf

## Technologies Used

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Security
* Thymeleaf
* MySQL
* Maven
* HTML
* CSS
* Bootstrap

## Prerequisites

Before running the project, ensure the following are installed:

* Java Development Kit (JDK 17 or later)
* Apache Maven
* MySQL Server (or H2 Database)
* IntelliJ IDEA, Eclipse, or Spring Tool Suite (STS)

## Getting Started

1. Clone the repository.
2. Configure the database properties in `application.properties`.
3. Build the project using Maven.
4. Run the Spring Boot application.
5. Open the application in your browser:

```
http://localhost:8080
```

## Future Enhancements

* Online payment gateway integration.
* Email and SMS notifications for reservations and order updates.
* QR code-based menu and ordering.
* Restaurant analytics dashboard.
* Inventory and stock management.
* Loyalty rewards and discount coupons.

## License

This project is developed for educational purposes and can be modified or extended for learning and research.
