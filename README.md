## Web automation test for some web store using cookies
### <a href="http://demowebshop.tricentis.com" target = "_blanc">demowebshop.tricentis.com</a>
## Instructions

<p>Make sure to create in the `src/test/resources/configuration/` folder configuration files/</p>

##### apiConfig.properties
It should contain:
- login for the account on <a href="http://demowebshop.tricentis.com" target = "_blanc">demowebshop.tricentis.com</a>
- password

#### apiConfig.properties example
```
demowebshop_login=myAwesomeShopLogin
demowebshop_password=myAwesomeShopPassword
```

##### webConfigRemote.properties
It should contain:
- the URL of the remote selenoid server
- login for the selenoid server
- password
- browser name
- browser version

#### webConfigRemote.properties example
```
remoteWebDriver=myselenoid.example.com
selenoid_login=admin
selenoid_password=qwerty
browser=CHROME
browserVersion=100.0
```

##### webConfigLocal.properties 
It should contain:
- browser name 
- browser version

#### webConfigLocal.properties example
```
browser=FIREFOX
browserVersion=102.0
```

## Local launch

```
gradle clean test -Denv="webConfigLocal"
```

## Remote launch

```
gradle clean test -Denv="webConfigRemote"
```