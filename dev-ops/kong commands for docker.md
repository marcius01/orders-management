## Register auth-service on kong (docker)

curl -i -X POST http://localhost:8001/services \
  --data name=auth-service \
  --data url='http://host.docker.internal:8080'

## Add Route

curl -i -X POST http://localhost:8001/services/auth-service/routes \
  --data 'paths[]=/api/v1/auth' \
  --data name=auth-route

## JWT Consumer Creation

curl -i -X POST http://localhost:8001/consumers \
  --data "username=my-consumer-app"

## Set Public Key To Consumer

PUBLIC_KEY_CONTENT=$(cat src/main/resources/publicKey.pem)

# Prima, come prima, metti la chiave pubblica in una variabile
PUBLIC_KEY_CONTENT=$(cat src/main/resources/publicKey.pem)

# Ora esegui il comando completo
curl -i -X POST http://localhost:8001/consumers/my-consumer-app/jwt \
  --data "algorithm=RS256" \
  --data "rsa_public_key=${PUBLIC_KEY_CONTENT}" \
  --data "key=https://skullprogrammer.tech/issuer"

## Active consumer for a service

curl -i -X POST http://localhost:8001/routes/auth-route/plugins \
  --data "name=jwt"