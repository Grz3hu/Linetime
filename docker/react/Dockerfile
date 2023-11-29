FROM node:19-alpine
WORKDIR /app
COPY frontend/ .
RUN npm ci 
ENTRYPOINT ["npm", "start"]
