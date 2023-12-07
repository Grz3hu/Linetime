Simple Spring Boot + React application that lets users create timelines

ERD Diagram:
![Alt text](erd.png?raw=true "ERD Diagram")

TODO:
make roles creation automatic
    \connect linetime-db
    INSERT INTO roles (name) VALUES ('ROLE_USER');
admin user auto creation
cluster autoscaler for frontend/backend
add api_endpoint env to frontend
add helmcharts
(possibly) simplify amount of envs in k8s postgres definition

note:
-H 'Content-Type: application/json' has to be set in order to curl
