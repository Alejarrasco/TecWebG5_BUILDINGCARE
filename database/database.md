# Base de datos

Para crear la imagen con postgres y la DB inicial: 
```
docker build -t condominio_img .
```
Luego, creamos el contenedor:
```
docker run -d --name condominio_ct -p 5432:5432 condominio_img
```
Hacemos correr el contenedor:
```
docker start condominio_ct
```
**Nota:** Para ingresar a la DB:
```
docker exec -it condominio_img bash
```
entramos a la consola del contenedor