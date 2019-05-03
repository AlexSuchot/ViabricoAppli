# ViabricoAppli
Application Viabrico

Cette application utilise l'API viabrico "https://viabrico.herokuapp.com/" hébergé sur Heroku. 

Nous nous sommes servi de la librairie AsyncHttpClient : https://github.com/AsyncHttpClient/async-http-client pour faire les requêtes.

Tout d'abord nous récupérons un token sécurisé qui est généré automatiquement lors de la création d'un utilisateur. Cela permet de sécuriser l'accès aux modifications si on est pas authentifié. 

Lorsqu'on arrive sur la page de connexion, on peut se connecter avec le mail et le password d'un utilisateur, sinon on peut aller en créer avec le bouton prévu à cette effet.

Nous utilisons les méthodes suivantes : 

# Post :

Sur la page de connexion on récupère le token via la route /login, si le token est bon (si addresse mail et mot de passe sont bons on arrive sur la page suivante, via la requête suivante :

```java
Request.post("https://viabrico.herokuapp.com/login", null, params, new TextHttpResponseHandler(){
}
```
Sur la page de création d'utilisateur on créer un utilisateur et un token automatiquement, avec la route /register de l'api :

```java
Request.post("https://viabrico.herokuapp.com/register", null, params, new TextHttpResponseHandler() {
}
```

# Get :

Une fois sur la page des fournisseurs :

On effecue une requête get pour récupérer la liste de tous les fournisseurs avec la route /suppliers :

```java
Request.get("https://viabrico.herokuapp.com/suppliers", token, null, new TextHttpResponseHandler(){
}
```

Lorsqu'on clique sur un fournisseur on récupère les données du fournisseur via son id et la route /suppliers :
```java
Request.get("https://viabrico.herokuapp.com/suppliers/" + id , token,null,new TextHttpResponseHandler(){
```

# Put :

Chaque fournisseur possède un bouton "edit" qui mène à la page de modification, une fois sur celle-ci on appel une requête put avec la route /suppliers/ + l'id du fournisseur :

```java
Request.put("https://viabrico.herokuapp.com/suppliers/" + id, token, params, new TextHttpResponseHandler() {
}
```

# Delete :

Chaque fournisseur possède un bouton "delete" qui supprime le fournisseur de la base de données. Le bouton fait appel à la requête delete avec la route /suppliers/ + l'id du fournisseur :

```java
Request.delete("https://viabrico.herokuapp.com/suppliers/" + id , token,null, new TextHttpResponseHandler(){
}
```


Le projet dans sa globalité (API, Site web, Application Androïd) a été réalisé par Marceau DAVID, Alexandre SUCHOT, Corentin GUILLARD, Camille GUERIN.
