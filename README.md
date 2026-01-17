# 🛒 Product Management System - Microservices with Kafka

A production-ready microservices application demonstrating event-driven architecture using Spring Boot, Apache Kafka, JWT authentication, and PostgreSQL. The system features role-based access control and real-time inventory synchronization between services.

## 📖 Project Overview

This project implements a **Product Management System** with two microservices:

- **Product Service (Port 8080)**: REST API for product CRUD operations with JWT-based authentication and role-based authorization (ADMIN/USER roles)
- **Inventory Service (Port 8081)**: Kafka consumer that listens to product quantity updates and automatically deletes products when inventory reaches zero

**Key Features:**
- 🔐 JWT-based stateless authentication with BCrypt password encryption
- 🛡️ Role-based access control (ADMIN can manage products, USER can only view)
- 🔄 Event-driven architecture using Apache Kafka for service communication
- 📦 Automatic inventory management (products deleted when quantity = 0)
- 🗄️ PostgreSQL database shared between services
- ⚡ Real-time synchronization between microservices

## 🛠️ Technologies

- **Java**: 17+
- **Spring Boot**: 3.5.9
- **Spring Security**: 6.5.7 (JWT)
- **Spring Data JPA**: 3.5.7
- **Apache Kafka**: 4.1 (KRaft mode)
- **PostgreSQL**: 17.5
- **JJWT**: 0.11.5
- **Lombok**: 1.18.42
- **Maven**: 3.x

## 🚀 Installation & Setup

### Prerequisites
- Java JDK 17+
- Maven 3.6+
- PostgreSQL 14+
- Apache Kafka 4.0+

# Start Kafka in KRaft mode
cd D:\kafka
bin\windows\kafka-storage.bat random-uuid
# Copy the UUID output, then run:
bin\windows\kafka-storage.bat format -t <YOUR_UUID> -c config\kraft\server.properties
bin\windows\kafka-server-start.bat config\kraft\server.properties

# In new terminal - Create topic
cd D:\kafka
bin\windows\kafka-topics.bat --create --topic product-events --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

# Start Kafka in KRaft mode
cd D:\kafka
bin\windows\kafka-storage.bat random-uuid
# Copy the UUID output, then run:
bin\windows\kafka-storage.bat format -t <YOUR_UUID> -c config\kraft\server.properties
bin\windows\kafka-server-start.bat config\kraft\server.properties

# In new terminal - Create topic
cd D:\kafka
bin\windows\kafka-topics.bat --create --topic product-events --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

| Endpoint                    | Method | USER | ADMIN | Public |
| --------------------------- | ------ | ---- | ----- | ------ |
| /auth/login                 | POST   | ✅    | ✅     | ✅      |
| /auth/register              | POST   | ✅    | ✅     | ✅      |
| /auth/admin/register        | POST   | ❌    | ✅     | ❌      |
| /api/products               | POST   | ❌    | ✅     | ❌      |
| /api/products               | GET    | ✅    | ✅     | ❌      |
| /api/products/{id}          | GET    | ✅    | ✅     | ❌      |
| /api/products/{id}/quantity | PUT    | ❌    | ✅     | ❌      |
| /api/products/{id}          | DELETE | ❌    | ✅     | ❌      |

Future Upgrade Add Redis to the Project.
