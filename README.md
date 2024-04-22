## auth_ms:
Docker commands:
```
docker build -t chaza_wallet_auth_ms .
docker run -p 8080:8080 -e DATASOURCE_URL=X -e DB_USER=Y -e DB_PASSWORD="Z" --name chaza_wallet_auth_ms chaza_wallet_auth_ms
```
## auth_db:

Dockerfile:
```
FROM mysql:5.7
ENV MYSQL_ROOT_PASSWORD=juan1337
ENV MYSQL_DATABASE=auth_db
ENV MYSQL_USER=admin
ENV MYSQL_PASSWORD=juan1337
EXPOSE 3306
```
Docker commands:
```
docker build -t chaza_wallet_auth_db .
docker run -d -t -i -p 3306:3306 --name chaza_wallet_auth_db chaza_wallet_auth_db
```

## GraphQL
Create:
```
mutation {
    postUserAuth(
        username: "user3"
        password: "g4eg3fbkk9"
    ) {
        ok
        userAuth{
            username
            password
        }
    }
}
```
Authenticate:
```
mutation {
    authenticateUserAuth(
        username: "user3"
        password: "g4eg3fbkk9"
    ) {
        ok
        token
    }
}
```
Update password:
```
mutation {
    updateUserAuth(
        username: "user3"
        password: "1234"
    ) {
        ok
        userAuth{
            username
            password
        }
    }
}
```
Delete:
```
mutation {
    deleteUserAuth(
        username: "user3"
    ) {
        ok
    }
}
```
