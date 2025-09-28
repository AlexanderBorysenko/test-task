# My Test Project

# App start:

## .env

Rename `.env.example` to `.env`

## For Development:
Starts all infrastructure in DEV watch mode.

```
sh run-dev
```

## For Production:
```bash
sh run-production workers=5
```

## Service ports

| Service  | Development (`docker-compose.dev.yaml`) | Production (`docker-compose.yaml`) |
|----------|------------------------------------------|-------------------------------------|
| Backend  | http://localhost:8080                    | http://localhost:8080               |
| Frontend | http://localhost:4200                    | http://localhost:80                 |

## Api

# Possible improvements:

## Backend:
- [-] Improve the folder structure
- [-] Pagination for list all
- [-] Add Item FAILDED status
    - [-] Endpoint to rerocess failed item
- [-] Check for PENDING jobs at the app startup and restart their porcessing
- [-] Create compleate CRUD for Item
- [-] Make DTO layers
    - [-] Make Mapping utils

## Worker
- [-] Change from Blocking to Async handling
- [-] Split the code into modules
    - [-] Make comfortable to add N new queue and callbacks
- [-] Improve the stability
    - [-] Retryes
    - [-] Reconnections

## Frontend
- [-] SSR Support
- [-] Move the Items Manger to separate abstraction
- [-] Make full production infra
    - [-] Separate dev/prodiuction config
    - [-] NGINX instead of node serve
        - [-] https for localhost