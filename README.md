# Smart Banking Transaction Monitoring System

A beginner-friendly Java web application for managing customer accounts and monitoring banking transactions.

## Technologies

- Java 17
- JDBC
- MySQL
- HTML
- CSS
- Jakarta Servlet / JSP
- Maven
- Apache Tomcat 10.1+

## Features

- Customer and admin login
- Customer account dashboard
- Deposit and withdrawal transactions
- Transaction history
- Transaction monitoring
- Rule-based alerts for unusually large transactions
- Admin transaction monitoring page
- MySQL database integration through JDBC

## Monitoring Rules

A transaction is flagged when:
1. The transaction amount is greater than or equal to ₹100,000.
2. The same account performs 3 or more transactions within 10 minutes.

These are demonstration rules for an academic project and are not real banking fraud-detection rules.

## Project Structure

```text
src/
├── main/
│   ├── java/com/smartbanking/
│   │   ├── dao/
│   │   ├── model/
│   │   ├── servlet/
│   │   └── util/
│   └── webapp/
│       ├── WEB-INF/
│       │   └── web.xml
│       ├── css/style.css
│       ├── login.jsp
│       ├── dashboard.jsp
│       ├── transactions.jsp
│       ├── admin.jsp
│       └── error.jsp
├── database/
│   └── schema.sql
└── pom.xml
```

## Database Setup

1. Install MySQL.
2. Open MySQL Workbench.
3. Run `database/schema.sql`.
4. By default the application uses:
   - Database: `smart_banking`
   - Username: `root`
   - Password: `root`

For a different MySQL password, set environment variables before starting Tomcat:

```text
DB_URL=jdbc:mysql://localhost:3306/smart_banking
DB_USER=root
DB_PASSWORD=your_password
```

The application falls back to the values in `DBConnection.java` when the environment variables are not set.

## Demo Login

After running `schema.sql`:

- Admin: `admin` / `admin123`
- Customer: `customer1` / `customer123`

Change these credentials before using the application outside a classroom/demo environment.

## Run with Maven

```bash
mvn clean package
```

Deploy:

```text
target/smart-banking.war
```

to Apache Tomcat 10.1+.

Then open:

```text
http://localhost:8080/smart-banking/
```

## Important

This is an academic/demo banking application. It does not connect to a real bank and should not be used for real financial transactions.
