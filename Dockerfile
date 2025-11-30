# Dockerfile raíz — evita fallos en Docker Hub / CI al no encontrar un Dockerfile
FROM alpine:3.19

# Mensaje para confirmar que la raíz no construye microservicios
CMD ["echo", "Root Dockerfile: este repositorio usa múltiples microservicios con sus propios Dockerfiles."]
