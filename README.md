# Project

Demo project exploring integrations, resilience and cache features.

# How to run

## Prepare infrastructure

```bash 
docker compose up 
```

### Apply Database structure (First run)

```bash
cat ./src/main/resources/00_init.sql | docker exec -i demo-postgres_primary-1 psql -U user -d postgres
```

### Fix replica (Second run)

Note that, running twice, replica will crash, so after start, remove it and restart docker compose

```bash
docker compose rm -s -v -f postgres_replica
```

## Start application

```bash
./mvnw clean spring-boot:run
```
## Test
```bash
./mvnw clean test
```
