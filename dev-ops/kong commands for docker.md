## Register auth-service on kong (docker)

curl -i -X POST http://localhost:8001/services \
  --data name=secure-auth-service \
  --data url='http://host.docker.internal:8080/secure'

curl -i -X POST http://localhost:8001/services \
  --data name=public-auth-service \
  --data url='http://host.docker.internal:8080/public'

curl -i -X POST http://localhost:8001/services \
  --data name=secure-product-service \
  --data url='http://host.docker.internal:8081/secure'

curl -i -X POST http://localhost:8001/services \
  --data name=public-product-service \
  --data url='http://host.docker.internal:8081/public'

curl -i -X POST http://localhost:8001/services \
  --data name=secure-order-service \
  --data url='http://host.docker.internal:8082/secure'

curl -i -X POST http://localhost:8001/services \
  --data name=public-order-service \
  --data url='http://host.docker.internal:8082/public'

## Add Route

curl -i -X POST http://localhost:8001/services/secure-auth-service/routes \
  --data 'paths[]=/api/v1/auth/secure' \
  --data name=secure-auth-route

curl -i -X POST http://localhost:8001/services/public-auth-service/routes \
  --data 'paths[]=/api/v1/auth/public' \
  --data name=public-auth-route

curl -i -X POST http://localhost:8001/services/secure-product-service/routes \
  --data name=secure-product-route \
  --data 'paths[]=/api/v1/products/secure'

curl -i -X POST http://localhost:8001/services/public-product-service/routes \
  --data name=public-product-route \
  --data 'paths[]=/api/v1/products/public'

curl -i -X POST http://localhost:8001/services/secure-order-service/routes \
  --data name=secure-order-route \
  --data 'paths[]=/api/v1/orders/secure'

curl -i -X POST http://localhost:8001/services/public-order-service/routes \
  --data name=public-order-route \
  --data 'paths[]=/api/v1/orders/public'

## JWT Consumer Creation

curl -i -X POST http://localhost:8001/consumers \
  --data "username=default-consumer-client"

## Set Public Key To Consumer

PUBLIC_KEY_CONTENT=$(cat src/main/resources/publicKey.pem)

# Ora esegui il comando completo
curl -i -X POST http://localhost:8001/consumers/default-consumer-client/jwt \
  --data "algorithm=RS256" \
  --data-urlencode "rsa_public_key=${PUBLIC_KEY_CONTENT}" \
  --data "key=https://skullprogrammer.tech/issuer"  

## Active jwt plugin for services

curl -i -X POST http://localhost:8001/services/secure-auth-service/plugins \
  --data "name=jwt"

curl -i -X POST http://localhost:8001/services/secure-product-service/plugins \
  --data "name=jwt" \
  --data "config.claims_to_headers=sub:X-User-Id,groups:X-User-Groups"

curl -i -X POST http://localhost:8001/services/secure-order-service/plugins \
--data "name=jwt"

## Transformer can't extract claims from jwt token
