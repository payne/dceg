# Stage 1: Build Angular app
FROM node:14.17-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

# Stage 2: Serve Angular app with Nginx
FROM nginx:latest
COPY --from=build /app/dist/angular-app /usr/share/nginx/html
COPY ./nginx-custom.conf /etc/nginx/conf.d/default.conf
EXPOSE 4200
CMD ["nginx", "-g", "daemon off;"]

