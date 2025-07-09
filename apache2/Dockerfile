FROM 8ct8pus/apache-php-fpm-alpine:2.4.2

COPY ./sites-available/localhost.conf /config/vhosts/localhost.conf 
COPY ./sites-available/httpd.conf /etc/apache2/httpd.conf 

# CMD ["sh", "-c", "httpd -k restart"]