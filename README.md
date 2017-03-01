# Localhost autologin hook

Automatically allows to logins to the user to Liferay portal when accessing from localhost with userId specified at portal-ext.properties.

User cases:

- Development, you want to login automatically with certain user id 
- Server warm up, run warmup script to refill the caches of the Liferay

``` 
#
# This property defines which user is logig automatically
#
localhost.autologin.userId=<write Liferay user Id>

```

## To compile 

```
mvn package
```
You find the installable artifact from the "target" directory.
