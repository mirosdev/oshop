# oshop
Angular 7 SPA + Spring Boot 2 + H2 (fake) RDB - Organic Shop Practice Project
(project was built generally for refreshing hands-on practice purposes).

# Run Requirements
- Node.js
- Angular CLI
- Maven

# Database
No database installation required For structure and data review access http://localhost:8080/h2-console/ in your browser, enter 'jdbc:h2:mem:testdb' in 'JDBC URL' form field, let other fields set by default and click connect to connect to H2 database review. Everytime Spring Boot runs, the database is empty and then auto-initialized with dummy data.

# Run Commands
After required installations, locate your command line prompt into SPA directory,
- run 'npm install' to install Angular dependencies,
- then run 'ng serve' to run Angular SPA on port localhost:4200,
- and for Spring Boot, locate into Spring Rest directory and run 'mvn spring-boot:run'

# For Application User Experience
Login with 'guest@test.com', pw: 'password' for user role account, or 'test@test.com', pw: 'password' for admin role account.

# Features
Shopping Card (login later on check-out) feature and Bootstrap 4 styling.
