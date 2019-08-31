# cdi-demo
Demonstrating how to use CDI with Vaadin 8

There are number of CDI use cases demonstrated in this app, some of them are generic and not restricted to Vaadin usage

The views are implemented using MVP (Model View Presenter) pattern

Here is a brief summary what you can find here

- LoggerProducer:
  - Producer pattern, see  for injecting slf4j logger in classes

- MyVaadinUI:
  - Usage of @CDIUI annotation
  - Injectable CDINavigator
  - Using CDI events to trigger navigation

- VersionLabel: Shared UIScoped component used by multiple views

- UserListService: 
  - ApplicationScoped user repository

- UserProfileHolder: 
  - VaadinSessionScoped user entity management
  - Login, logout and access level methods

- AdminView
  - Access control with restricted access
  - Async update of the view content using ManagedExecutorService and CompletableFuture

- MainView
  - Using UIScoped (VersionLabel) and ViewScoped beans
  - Access control
  - Opening new browser tab with BrowserWindowOpener (demoing VaadinSessionScoped user enitity)
 
 - LoginView
   - Simple login example
   - Using UIScoped (VersionLabel) bean
  
