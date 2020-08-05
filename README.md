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
  - Extending VaadinCDIServlet and setting SessionExpired error not to show
    and directly refresh the UI to login
  - Parse query parameters

- VersionLabel: Shared UIScoped component used by multiple views

- UserListService: 
  - ApplicationScoped user repository

- UserProfileHolder: 
  - VaadinSessionScoped user entity management
  - Login, logout and access level methods

- AdminView
  - Access control with restricted access
  - Async update of the view content using ManagedExecutorService and CompletableFuture,
    showing spinner while loading

- MainView
  - Using UIScoped (VersionLabel) and ViewScoped beans
  - Access control
  - Opening new browser tab with BrowserWindowOpener (demoing VaadinSessionScoped user enitity)
  - Demoing passing query and view parameters
 
 - LoginView
   - Simple login example
   - Using UIScoped (VersionLabel) bean
  
 - DemoUtils
   - Proper way of session fixation protection used in login process
