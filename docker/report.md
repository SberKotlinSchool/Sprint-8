### Docker

* ``` cd .\docker\ ```
* ``` mvn install ```
* ``` docker build -t sprint8:latest .```
* ``` docker images ```

````bash
PS C:\Users\Ctac\IdeaProjects\Sprint-8\docker> docker images                      
REPOSITORY   TAG       IMAGE ID       CREATED          SIZE
sprint8      latest    5a8f081eaced   24 minutes ago   127MB
````

* ``` docker run -d -p 80:8080  sprint8 ```
* ```docker ps ```

````bash
PS C:\Users\Ctac\IdeaProjects\Sprint-8\docker> docker ps                        
CONTAINER ID   IMAGE     COMMAND                  CREATED         STATUS         PORTS                  NAMES
5f3dc51d4f65   sprint8   "java -jar docker-0.…"   6 seconds ago   Up 5 seconds   0.0.0.0:80->8080/tcp   goofy_sutherland
````

* по адресу http://127.0.0.1/hello или http://127.0.0.1:80/hello открывается страница с профилем: ```QA!```
* ```docker logs goofy_sutherland ```

````bash
PS C:\Users\Ctac\IdeaProjects\Sprint-8\docker> docker logs goofy_sutherland          

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.5.5)

2023-12-18 13:58:08.236  INFO 1 --- [           main] com.example.docker.DockerApplicationKt   : Starting DockerApplicationKt v0.0.1-SNAPSHOT using Java 1.8.0_212 on 5f3dc51d4f65 with PID 1 (/app/docker-0.0.1-SNAPSHOT.jar started by root in /app)
2023-12-18 13:58:08.240  INFO 1 --- [           main] com.example.docker.DockerApplicationKt   : The following profiles are active: qa
2023-12-18 13:58:10.443  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2023-12-18 13:58:10.474  INFO 1 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2023-12-18 13:58:10.475  INFO 1 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.53]
2023-12-18 13:58:10.572  INFO 1 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2023-12-18 13:58:10.572  INFO 1 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 2226 ms
2023-12-18 13:58:11.442  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2023-12-18 13:58:11.461  INFO 1 --- [           main] com.example.docker.DockerApplicationKt   : Started DockerApplicationKt in 4.218 seconds (JVM running for 5.51)
2023-12-18 13:59:10.390  INFO 1 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2023-12-18 13:59:10.390  INFO 1 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2023-12-18 13:59:10.392  INFO 1 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 2 ms
````

* ``` docker stop goofy_sutherland ```

### Kubernetes

````bash
PS C:\Users\Ctac\IdeaProjects\Sprint-8\docker> kubectl create -f .\deployment-definition.yml
deployment.apps/my-app-deployment created
service/my-app-service created

PS C:\Users\Ctac\IdeaProjects\Sprint-8\docker> kubectl get pod                                    
NAME                                 READY   STATUS    RESTARTS   AGE
my-app-deployment-5d99486987-9jwv6   1/1     Running   0          14s
my-app-deployment-5d99486987-m2p4f   1/1     Running   0          14s
my-app-deployment-5d99486987-zkfrt   1/1     Running   0          14s

PS C:\Users\Ctac\IdeaProjects\Sprint-8\docker> kubectl get all                                                 
NAME                                     READY   STATUS    RESTARTS   AGE
pod/my-app-deployment-5d99486987-9jwv6   1/1     Running   0          36s
pod/my-app-deployment-5d99486987-m2p4f   1/1     Running   0          36s
pod/my-app-deployment-5d99486987-zkfrt   1/1     Running   0          36s

NAME                     TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)    AGE
service/kubernetes       ClusterIP   10.96.0.1       <none>        443/TCP    103m
service/my-app-service   ClusterIP   10.99.131.221   <none>        8080/TCP   36s

NAME                                READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/my-app-deployment   3/3     3            3           36s

NAME                                           DESIRED   CURRENT   READY   AGE
replicaset.apps/my-app-deployment-5d99486987   3         3         3       36s
````

````bash
PS C:\Users\Ctac\IdeaProjects\Sprint-8\docker> kubectl delete services my-app-service       
service "my-app-service" deleted
PS C:\Users\Ctac\IdeaProjects\Sprint-8\docker> kubectl delete deployment my-app-deployment  
deployment.apps "my-app-deployment" deleted
PS C:\Users\Ctac\IdeaProjects\Sprint-8\docker> kubectl get all                            
NAME                 TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
service/kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP   105m
PS C:\Users\Ctac\IdeaProjects\Sprint-8\docker> kubectl get pod                              
No resources found in default namespace.
PS C:\Users\Ctac\IdeaProjects\Sprint-8\docker>
````


