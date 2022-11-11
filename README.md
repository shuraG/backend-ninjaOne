# NinjaOne Backend Interview Project

The project is configured to use an in-memory H2 database that is volatile. If you wish to make it maintain data on
application shut down, you can change the spring.database.jdbc-url to point at a file
like `jdbc:h2:file:/{your file path here}`

## Model

![alt text](https://github.com/shuraG/backend-ninjaOne/blob/main/images/umlModel.png)

## Improvements

* Due to time the device types have been reduced to an ENUM. For greater flexibility, it should be implemented as its
  own entity, rather than a business object.
* Due to time, the documentation of endpoints use basic documentation MARKDOWN, we should use a better tool as `Swagger` to automate the most part of this documentation software process.
* At the moment the `RmmService` class handles both a general price and a price for a specific device type. To improve,
  you could think of an interface that implements the `getPrice(Type Device)` method and have two classes that
  implement: `CommonPrice`(A single price) and `SpecificPrice`(with a map of prices by type). So we delegate this
  responsibility to specific classes.
* At the moment `ExtraCost` is a burned value, like a `OBLIGATORY` cost. We need to handle like an entity.
* At the moment the integration tests do not cover all the base cases, you should at least test these.

## Starting the Application

Run the `BackendInterviewProjectApplication` class

## Endpoint description

### Devices

* POST `/device `

  ```` 
  Description: Create a device, and return its ID.
  
  ````

  ````
  Request:
  {
    "systemName" : "Petronia CBA1",
    "typeDevice": "MAC"
  }
  ````

  ````
  Response:
  {
    "deviceId": "5fd2559c-4e07-4096-81d5-da63614dc90a"
  }
  ````

* POST `/device/{id_device}/subscription`

  ```` 
  Description: Subscribe an rmm service to device.
  id_device: Id of device to subscribe.
  serviceId: Id of rmm service.
  ````

  ````
  Path: http://localhost:8080/device/f829d5ac-9c54-468e-9040-6da7c3de7888/subscription
  
  Request:
  {
    "serviceId": "3990f2f2-cea1-4a49-a8df-c49d0d4d2182" 
  }
  ````

  ````
  Response: 201 CREATED
  ````
* GET `/device/cost`
    ```` 
  Description: return the total cost of a list of devices.
  Body: Array of Id devices.
  ````

  ````
  Request:
  [
    "d3e3410a-69f5-4cf7-871f-fc62bb114b21",
    "5fd2559c-4e07-4096-81d5-da63614dc90a"
  ]
  ````

  ````
  Response:
  {
    "totalCost": 19.90
  }
  ````
* DELETE `/device/{id_device}/subscription`
   ```` 
  Description: Unsubscription of a service.
  id_device: Id of device.
  serviceId: Id of rmm service to unsubscribe.
  ````

  ````
  Path: http://localhost:8080/device/f829d5ac-9c54-468e-9040-6da7c3de7888/subscription
  
  Request:
  {
    "serviceId": "d0bdd447-f8f4-4129-8005-3986a1cbd9d5"
  }
  ````

  ````
  Response: 204 NOT CONTENT
  ````
* DELETE `/device/{id_device}`
   ```` 
  Description: Remove an specific device.
  id_device: Id of device to remove.
  
  ````

  ````
  Path: http://localhost:8080/device/1f667487-8912-4b52-accf-619433ace9a4
  ````

  ````
  Response: 204 NOT CONTENT
  ````

### RmmServices
* POST `/rmmservice`
   ```` 
  Description: Create a rmm service with a common price.
  ````

  ````
  Request:
  {
    "name": "backup",
    "price": "15.90"
  }
  ````

  ````
  Response:
  {
    "rmmServiceId": "3990f2f2-cea1-4a49-a8df-c49d0d4d2182"
  }
  ````

  ```` 
  Description: Create a rmm service with prices for specific type device.
  ````

  ````
  Request:
  {
    "name": "antivirus",
    "prices":{
        "LINUX":"13.65",
        "MAC":"10.10"
    }
  }
  ````

  ````
  Response:
  {
    "rmmServiceId": "3990f2f2-cea1-4a49-a8df-c49d0d4d2182"
  }
  ````
  
* DELETE `/{id_rmmservice}`
   ```` 
  Description: DELETE a rmm service by ID.
  
  ````

  ````
  Path: http://localhost:8080/rmmservice/16b7d64d-123e-4c39-9eb6-97b49122d09c
  ````

  ````
  Response: 204 NOT CONTENT
  ````





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

### Suggestions

Feel free to remove or repurpose the existing Sample Repository, Entity, Controller, and Service. 
