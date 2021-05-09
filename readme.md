# Installation

If you're testing this I'am 100% sure you're also a developer, follow these steps to make the application run:

- clone the repo, **git clone https://github.com/yuen1318/fxc.git**
- go to the directory of the project and run the docker command
- **docker build -f Dockerfile -t fx-api .**
- **docker run -p 8080:8080 fx-api**
- got to the swagger docs http://localhost:8080/swagger-ui.html

# User Story

**Backend : Rest API with Swagger + Tests**

**Problem definition: We would like to provide a FX currency widget for a website with the following inputs**:

- Buy Currency (Pick from a list of currencies) 
- Buy Amount 
-  Sell Currency (Pick from a list of currencies) 
-  Sell Amount 
- Display the rate offered

**Problem encountered: using the free api subscription of exchangeratesapi.io there are limitations like** : 
- you can't use ssl connection 
-  you can't use the convertion api (need to manual convert) 
- you can't use the base function

**Nearest Solution**
- derived a formula to cater the lack of 3rd party API functionality, in real life scenario we would ask the client if he/she is willing to upgrade the plan for this one and present what are the pros and cons
- the base is always **EUR** so we need to inverse the to the currency we are converting , eg: AUD = 1 / value of AUD per EUR
- after we got the multplier we can use is as toCurrencyValue = get AUD(in EUR) * toValueMultiplier

## Dissecting the User stories into sub task
* Create a GET req : {baseUrl}/convert?from=USD&to=EUR&amount=25 also display the rate offered
* Create a GET req : {baseUrl}/symbol display the symbol of the currencies as well as their description to be used by the UI on the dropdown menu
* Create a POST req : {baseUrl}/sell to invoke the sell functionality (request body is shown on the API definition on the swagger docs)
* Create a POST req : {baseUrl}/buy to invoke the sell functionality (request body is shown on the API definition on the swagger docs)


## Future Consideration / Notes

- we can add a **x-api-key** header to serve as our rate limiting per client in real life scenarios where in we can monitor each client request (can use as billing)
- i didn't add a version to the api eg : **{baseUrl}/api/v1/** since we can use an abstraction layer there via API Gateway Pattern or use a cloud service like AWS API GATEWAY
- i didn't add env variable for simplicity since we are on time constraint but in real life scenarios all assets/keys must be kept and encrypted if possible
- the **buy** and **sell** functionality is a little bit unclear to me since I'am not well verse on the FX jargons but in real life scenario we can ask the BA or the client to verify this, but on this exercise i think it's not applicable
- we can use Javers as our auditing library
- we can use Spring AOP in partner to our GlobalExceptionConfig to save the unhandled errors or the errors that are unknown to the application and arises in a specific scenario (we used this one for production support to minimized the time of replicating the issue)
- how do we scale? what is the architecture is monolith or microservice?