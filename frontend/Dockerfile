FROM node:24-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY . .
RUN npx ng build --configuration production

FROM node:20-alpine AS runtime
WORKDIR /srv/www
RUN npm i -g serve

# Angular build outputs to dist/<project-name>/browser by default
COPY --from=build /app/dist/frontend/browser/ ./

EXPOSE 80
CMD ["sh", "-c", "serve -s . -l 80"]
