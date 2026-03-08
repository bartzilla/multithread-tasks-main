# Notes and Thoughts

### The following are a series of notes on the solution:

### Task 1: Dependency injection
- There is a cyclic dependency from `FileService` to `TaskService` and the other way around. The issue arises due to 
Spring cannot determine which bean to load first. There are strategies to indicate the framework the order to load, 
but in this case `FileService` does not require a dependency to `TaskService` since the only reason it has the dependency defined is to `get` 
the task. Instead, a `ProjectGenerationTask` object can be sent as parameter to `getTaskResult` and `storeResult` functions. 
In that way, there is no need to reload the task and the cycle is not necessary.

### Task 2: Extend the application
- Implementing a new type. For this I considered the possibility to keep the already existing task `ProjectGenerationTask` and its API working. So in order to extend the functionality and not break the existing API 
I decided to create a new task type `CronTask`. Now these two tasks will extend from a base class `Task` sharing all common fields such as `name`, `id` etc. And put their concrete fields in their respective concrete classes.
- Another important point is that in order to differentiate each type of task at the controller level when receiving the task body, jackson does not know which class to instantiate to deserialize the payload. 
Therefore, a new attribute `type = cron` or `type = project` is added to the body message when POSTING tasks. There are newer versions of jackson that can deduct the class based on attributes. This could be considered as an improvement.
- When it comes to services, I created two: `CronService` and `ProjectGenerationService` and two implementations which are picked up at runtime by the controllers depending on the type in order to provide the according task implementation.
- Each `CronTask` can be stopped at any time.
- The parameters `X` and `Y` are passed in the request body. I decided to do so since it is a POST method and even though they can be passed as query parameters it is appropriate to keep parameters for queries rather than posting.
- There can be simultaneous cron tasks executing and the consumer can request the progress of each `CronTask` at any time of the execution by using the id and the `progress` endpoint. Once the task's count is finished it will notify it.
- Cancelling a running task is possible by using the id and the `stop` endpoint. If there are no running tasks an appropriate message will be sent back.
- In test folder there are Unit & Integration test to exemplify some of the use cases. There are still several edge cases worth testing further. This has to be considered for improvement. 

### Task 3: Periodically clean up the tasks

- There is a `Cleanup` scheduler which can be configured via properties to determine how often the cleanup is 
executed. At the moment it is scheduled to run on Christmas day and it will delete all tasks which are 
expired. That is all tasks that exist longer than certain period of time and have not been executed. The expiration time for a task can be configured via app properties.

Kind regards

---

Requirements
- JDK 11+, Maven 3.X


Building the project
- `cd backend_MultithreadTasks` or go to the root of the backend_MultithreadTasks project
- `mvn clean install`
- `mvn spring-boot:run`


