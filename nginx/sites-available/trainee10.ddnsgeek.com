server {
    listen 8090;
    server_name localhost;

    access_log /var/log/nginx/my_access.log;
    error_log /var/log/nginx/my_error.log;
    
    root /var/www/trainee10.ddnsgeek.com;

    location / {
        root /var/www/trainee10.ddnsgeek.com/html;
        index index.html;
    }
    
    location /505 {
        return 505;
    }

    location /content1 {
        proxy_pass http://localhost:9090/;
    }

     location /music {
        alias /var/www/trainee10.ddnsgeek.com/music/song.mp3;
        add_header Content-Disposition 'attachment; filename="song.mp3"'; 
    }

    location /info.php {
        proxy_pass http://apache:80/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }

    location /secondserver {
        return 301 https://httpbin.org/;
    }
}   