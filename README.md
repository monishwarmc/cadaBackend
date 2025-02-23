
# Templates

Api requesting templates

## store

### GET

```
https://cadabackend.onrender.com/store
```

Replace {id} with the 'store id'.

```
https://cadabackend.onrender.com/store/{id}
```

```
https://cadabackend.onrender.com/store/photo/{id}
```

### POST

body form data

<img src="/post-store.png" alt="post store" width="" height="">


```
https://cadabackend.onrender.com/store/add
```

### PUT

same body as post method but add changes in value. Can add just the adding parameter than adding all params. Replace {id} with the 'store id'.

```
https://cadabackend.onrender.com/store/update/{id}
```

### DELETE

Replace {id} with the 'store id'.

```
https://cadabackend.onrender.com/store/delete/{id}
```



## Product

### GET

```
https://cadabackend.onrender.com/product
```

Replace {id} with the 'store id'.

```
https://cadabackend.onrender.com/product/store/{id}
```

Replace {id} with the 'product id'.

```
https://cadabackend.onrender.com/product/photo/{id}
```

Replace {id} with the 'product id'.

```
https://cadabackend.onrender.com/product/{id}
```

### POST

body 'form-data'

<img src="/post-product.png" alt="post store" width="" height="">


Replace {id} with the 'store id'.

```
https://cadabackend.onrender.com/product/add/{id}
```

### PUT

same body as post method but add changes in value. Can add just the adding parameter than adding all params. Replace {id} with the 'store id'.

Replace {id} with the 'product id'.

```
https://cadabackend.onrender.com/product/update/{id}
```

### DELETE

Replace {id} with the 'product id'.

```
https://cadabackend.onrender.com/product/delete/{id}
```

# Deployment

Deployed on render => <a href="https://cadabackend.onrender.com" target="_blank" >CaDa Backend Application</a>