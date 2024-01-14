rem docker
docker build -t app .;
docker run -p 80:8080/tcp app;

rem k8s
docker context use default;
minikube start;
minikube docker-env | Invoke-Expression;
minikube image load app;
kubectl create -f deployment-definition.yml;
kubectl scale --replicas=3 deployment/app-deployment;
minikube service application;