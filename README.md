<div align="center">
    <h1> Secured RESTful API for Articles and Statistics </h1>
</div>

<div align="center">
    <a href="#introduction">Introduction</a> |
    <a href="#technologies">Technologies & Tools</a> |
    <a href="#functionalities">Functionalities</a> |
    <a href="#setup">Setup</a> |
    <a href="#testing">Testing</a> |
    <a href="#contribute">Contribute</a>
</div>

<div id="introduction">
    <br>
    <p>
        This project is a part of a new startup initiative, aiming to develop a cutting-edge software that stands out in the competitive 
        market. The application is a secured RESTful API designed for creating and listing articles, as well as providing statistical insights 
        accessible only to admins.
    </p>
</div>
<hr>

<div id="technologies">
    <h3> Technologies & Tools </h3>
    <p>
        The application is developed using a variety of modern technologies and tools to ensure scalability and security:
        <br> - Spring Boot Framework for the backend
        <br> - Spring Security for authentication and authorization users
        <br> - Spring Data JPA for database interaction
        <br> - JWT for secure token-based authentication 
        <br> - MapStruct for object mapping, simplifying the conversion between DTOs and entity classes
        <br> - Maven for dependency management and project build
        <br> - PostgreSQL for the relational database
        <br> - Liquibase for managing database schema changes and tracking revisions
        <br> - IntelliJ IDEA recommended development enviorment
        <br> - Swagger for API documentation
        <br> - JUnit and Mockito for unit testing
    </p>
</div>
<hr>

<div id="functionalities">
    <h3> Functionalities </h3>
    <p>
        The application provides the following functionalities:
        <br> - User registration and login
        <br> - Creating articles with mandatory fields: title, author, content, and publishing date
        <br> - Listing articles with pagination
        <br> - Viewing detailed article information
        <br> - Generating daily statistics of published articles for the past 7 days (Admin only)
    </p>
</div>
<hr>

<div id="setup">
    <h3> Setup </h3>
    <p>
        To set up the project locally, follow these steps:
        <br> 0. Install Postman(for make requests to endpoints or using web browser);
        <br> 1. Installed JDK and IntelliJ IDEA;
        <br> 2. MySql/PostgresSql or another preferable relational database;
        <br> 3. Maven (for building the project);
        <br> 4. Clone the repository: git clone https://github.com/MariaSukhetska
        <br> 5. Navigate to the project directory: cd final_project
        <br> 6. Build the project: mvn clean install
        <br> 7. Run the application: mvn spring-boot:run
        <br> 8. Access the Swagger UI for API documentation: http://localhost:8080/api/swagger-ui/index.html 
</p>
</div>
<hr>

<div id="testing">
    <h3> Testing </h3>
    <p>
        The application includes unit tests for verifying the functionality of the code. To run the tests, use the following command:
        <br> - mvn test or mvn clean package
    </p>
</div>
<hr>

<div id="contribute">
    <h3> Contribute </h3>
    <p>
        Contributions are welcome! If you have any suggestions or improvements, feel free to contact me 
        through LinkedIn(https://www.linkedin.com/in/maria-zhdanova-709ba8224/) or GitHub(https://github.com/MariaSukhetska).
    </p>
</div>
<hr>
