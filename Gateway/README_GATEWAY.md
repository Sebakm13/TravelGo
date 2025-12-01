
# TravelGo Gateway (Spring Cloud)

## ¿Qué hace este módulo?
- Enruta todas las llamadas a /api/destinations hacia el microservicio real.
- Funciona como API Gateway para TravelGo.

## Cómo usar
1. Ejecutar el gateway:
   ```
   mvn spring-boot:run
   ```
2. Gateway corre en: `http://localhost:8080`
3. Microservicio de destinos debe estar en: `http://localhost:8081`
4. Endpoint final:
   ```
   http://localhost:8080/api/destinations
   ```

## Estructura
- pom.xml
- GatewayApplication.java
- application.yml

Lista para subir al GitHub.
