# Random-users-generator
    ABOUT PROJECT
Random-users-generator is a web application that **automatically generates 100 random fake users on startup**, the generated users includes fields such as: **an id,firstname,lastname,phone-number,email-address,street-address,zip-code,city,state and country.**

This project is secured using OAuth2,security logs are saved to the securityLog.txt, and it also includes 2 InmemoryUsers- admin and user with different authorities.

### Inmemory users
1. admin, scope-read,create and delete.
   The admin can get a specific generated user by id, get all generated users,create a new user to be persisted to the database, delete a specific generated user by id and delete all generated users in the user repository.
2. user, scope-read.
   The user can get a specific generated user by id and get all generated users from the user repository only.

### Routes
#### GET
1. "/api/users/{id}" - fetches a user from the database using an id extracted from the path.Returns 200 if the operation was successful or 400 if an exception was encountered.  
2. "/api/users/" or "/api/users/all" - fetches all users in the database. Returns 200 if the operation was successful or 400 if an exception was encountered.
3. "/api/users" - fetches all users from the database using a page and a size parameter using either their default values or supplied values to fetch users from the database in Pages. Returns 200 if the operation was successful or 400 if an exception was encountered. 
#### POST
#### DELETE
1. "/api/users/{id}" - deletes a user from the database using an id extracted from the path.Returns 204 if the operation was successful or 400 if an exception was encountered.
2. "/api/users?id" - deletes a user from the database using an id supplied via parameter.Returns 204 if the operation was successful or 400 if an exception was encountered.
3. "api/users/all" - deletes all the users in the database. Returns 204 if the operation was successful or 400 if an exception was encountered.

### Dependencies
The dependencies are in the Pom.xml file located in the root directory. 
### How to run
1. Simply invoke the main method in the RandomUsersGeneratorApplication class located in the com.generator.randomusersgenerator package.
