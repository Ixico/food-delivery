docker run -d -p 5432:5432 --name sigma-food -e POSTGRES_PASSWORD=postgrespw -v /var/lib/postgresql/data postgres