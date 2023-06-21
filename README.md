# dceg: Example of using a docker-compose.yml 

## Usage
```
❯ docker compose up -d # you should see four containers start

# See how they are feeling:

❯ docker ps | nl
     1  CONTAINER ID   IMAGE             COMMAND                  CREATED         STATUS         PORTS                            NAMES
     2  a70d4c3926be   nginx:latest      "/docker-entrypoint.…"   2 minutes ago   Up 2 minutes   0.0.0.0:80->80/tcp               dceg-nginx-1
     3  34e2776107de   postgres:latest   "docker-entrypoint.s…"   2 minutes ago   Up 2 minutes   0.0.0.0:5432->5432/tcp           dceg-postgres-1
     4  3be0c8617446   dceg_angular      "/docker-entrypoint.…"   2 minutes ago   Up 2 minutes   80/tcp, 0.0.0.0:4200->4200/tcp   dceg-angular-1


```

Notice -- that the springboot app is failing to start.  `docker compose logs springboot` shows it's that the password is wrong in `application.properties`


### Problems

Currently getting:
```
dceg-springboot-1  | org.postgresql.util.PSQLException: Connection to postgres:5432 refused. Check that the hostname and port are correct and that the postmaster i
s accepting TCP/IP connections.
```

