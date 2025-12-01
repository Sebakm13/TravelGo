# TravelGo – Backend (Microservicios)

Este backend está compuesto por:

- users-service (CRUD real con H2)
- bookings-service
- payments-service
- api-gateway

## Cómo levantar el backend completo

```bash
cd Backend
docker-compose up --build
```

El API Gateway quedará escuchando en:
http://localhost:8080
