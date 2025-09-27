# My Test Project

# App start:
```bash
docker compose build --pull --parallel
docker compose up --scale worker=5
```

# Possible improvements:

## Backend:
- [-] Improve the folder structure
- [-] Make DTO layers
    - [-] Make Mapping utils
- [-] Pagination for list all
- [-] Add Item FAILDED status
    - [-] ENdpoint to rerocess failed item
- [-] Check for PENDING jobs at the app startup and restart their porcessing
- [-] Compleate CRUD for Item

## Worker
- [-] Change from Blocking to Async handling
- [-] Split the code into modules
    - [-] Make comfortable to add N new queue and callbacks
- [-] Improve the stability
    - [-] Retryes
    - [-] Reconnections