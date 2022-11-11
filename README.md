# NinjaOne Backend Interview Project

The project is configured to use an in-memory H2 database that is volatile. If you wish to make it maintain data on application shut down, you can change the spring.database.jdbc-url to point at a file like `jdbc:h2:file:/{your file path here}`

## Model
![alt text](https://github.com/shuraG/backend-ninjaOne/blob/main/images/umlModel.png)


## Improvements
* Due to time the device types have been reduced to an ENUM. For greater flexibility, it should be implemented as its own entity, rather than a business object.
* Due to time, the documentation of endpoints use basic documentation JAVADOC, we should use a better tool as `Swagger`.
* At the moment the `RmmService` class handles both a general price and a price for a specific device type. To improve, you could think of an interface that implements the `getPrice(Type Device)` method and have two classes that implement: `CommonPrice`(A single price) and `SpecificPrice`(with a map of prices by type). So we delegate this responsibility to specific classes.
* At the moment `ExtraCost` is a burned value, like a `OBLIGATORY` cost. We need to handle like an entity.
* At the moment the integration tests do not cover all the base cases, you should at least test these.

## Starting the Application

Run the `BackendInterviewProjectApplication` class



## Endpoint description



## H2 Console 

In order to see and interact with your db, access the h2 console in your browser.
After running the application, go to:

http://localhost:8080/h2-console

Enter the information for the url, username, and password in the application.yml:

```yml
url: jdbc:h2:mem:localdb
username: sa 
password: password
```

You should be able to see a db console now that has the Sample Repository in it.

Type:

```sql
SELECT * FROM SAMPLE;
````

Click `Run`, you should see two rows, for ids `1` and `2`

### Suggestions

Feel free to remove or repurpose the existing Sample Repository, Entity, Controller, and Service. 
