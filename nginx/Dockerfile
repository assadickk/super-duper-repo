FROM nginx:alpine

LABEL version="1.0 Beta" maintainer="Asad Kiarimov" mail="assadkrmv@gmail.com" purpose="trainee"

COPY ./nginx.conf /etc/nginx/nginx.conf 
COPY ./sites-available/* /etc/nginx/conf.d/
COPY --chown=nginx:nginx --chmod=744 ./www /var/www

EXPOSE 8090/tcp