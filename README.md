# <a name='home'></a>daytron-money  [![Coverage Status](https://coveralls.io/repos/Daytron/daytron-money/badge.svg?branch=master)](https://coveralls.io/r/Daytron/daytron-money?branch=master) [![Build Status](https://travis-ci.org/Daytron/daytron-money.svg?branch=master)](https://travis-ci.org/Daytron/daytron-money) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.daytron/DaytronMoney/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.daytron/DaytronMoney) [![Stories in Ready](https://badge.waffle.io/Daytron/daytron-money.svg?label=ready&title=Ready)](http://waffle.io/Daytron/daytron-money)

A Java library for dealing simple monetary operations and conversions. 

Users are reminded that this software, like all other open source software, is provided on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND. See license for details.

### Contents
* <a href='#features'>Features</a>
* <a href='#requirements'>Requirements</a>
* <a href='#quicksetup'>Quick Setup</a>
* <a href='#documentation'>Documentation</a>
* <a href='#usage'>Usage</a>
 * <a href='#construction'>Construction</a>
 * <a href='#conversion'>Conversion</a>
 * <a href='#collection'>Collection</a>
* <a href='#development'>Development</a>
* <a href='#license'>License</a>
 
***

### <a name='features'></a>Features 
<sup><a href='#home'>[back to top]</a></sup>

- Precise monetary calculations up to 2e63-1 for each whole units and decimals values. (+-9,223,372,036,854,775,807.9223372036854775807)
- Latest and historical currency conversions.
- Custom data structures for storing and handling Money objects.
- Parse String, integer and long values into Money objects.
- Allows signed values
- Works with currency codes (ISO 4217)

### <a name='requirements'></a>Requirements 
<sup><a href='#home'>[back to top]</a></sup> 

This library requires the following to work properly:

- JDK 8
- A fantastic free currency api ([fixer.io]) courtesy of [Hakan Ensari](https://github.com/hakanensari).

#### Dependencies:
- Google's Gson for handling json objects
- JUnit for testing purposes



### <a name='quicksetup'></a>Quick Setup
<sup><a href='#home'>[back to top]</a></sup>  

Maven Artifact 
```xml
<dependency>
  <groupId>com.github.daytron</groupId>
  <artifactId>DaytronMoney</artifactId>
  <version>1.2.0</version>
</dependency>
``` 


### <a name='documentation'></a>Documentation 
<sup><a href='#home'>[back to top]</a></sup> 

Please refer to [Javadoc] for more information.


### <a name='usage'></a>Usage 
<sup><a href='#home'>[back to top]</a></sup> 


#####<a name='construction'></a>Construction
<sup><a href='#home'>[back to top]</a></sup> 

`Money` class represents all monetary values. Choose any from the following when creating a new object. 
```java
    // GBP 10.50
    Money moneyA = new Money.Builder()
        .currencyCode("GBP")
        .sign(SignValue.Positive)
        .wholeUnit(10)
        .decimalUnit(5)
        .build();
    
    // Local currency code with value of 10.50
    Money moneyB = new Money.Builder()
        .wholeUnit(10)
        .decimalUnit(5)
        .build();
    
    // Local currency code with a value of 10.05
    Money moneyC = new Money.Builder()
        .wholeUnit(10)
        .decimalUnit(5)
        .leadingDecimalZeroes(1)
        .build();
    
    // Negative value
    Money moneyD = new Money.Builder()
        .sign(SignValue.Negative)
        .wholeUnit(10)
        .decimalUnit(50)
        .build();
    
    // Default value is zero
    Money moneyE = new Money.Builder().build();
```

`MoneyFactory` class allows you to parse other data types into `Money` objects.
```java
MoneyFactory moneyFactory = new MoneyFactory("USD");
Money moneyA = moneyFactory.valueOf("100.00");
Money moneyB = moneyFactory.valueOf(100);
```

Acceptable `String` formats:
- "GBP 12.5"
- "GBP12.5"
- "USD-12"
- "USD -896,586,785,785,896.0025634589"
- ""
- "+9.6212"
- "-8.0000000"
- "0.00"
- "-10.12"
- "0"
- "286"
- "0.0000005"
- "000000000000000.6"
- "12,856,896.00963"

#####<a name='conversion'></a>Conversion
<sup><a href='#home'>[back to top]</a></sup> 

You can convert currency `Money` to another currency using `CurrencyExchange` class. This class uses `ConversionClient` to connect to an external API (see [fixer.io]). Conversion can be done through the following example.
```java

MoneyFactory moneyFactory = new MoneyFactory("USD");
         
CurrencyExchange cex = CurrencyExchange.getInstance();
Money moneyA = moneyFactory.valueOf("10.50");

// Latest conversion
Money moneyB = cex.convert(moneyA, "GBP");

// Historical conversion
LocalDateTime dateTime = LocalDateTime.of(2001, Month.MARCH, 2, 3, 31);
Money moneyC = cex.convert(moneyA, "GBP", dateTime);
```

#####<a name='collection'></a>Collection
<sup><a href='#home'>[back to top]</a></sup> 

`MoneyArrayList` and `MoneyHashMap` classes are custom data structures as an alternative to handle `Money` objects with added new functionalities (see [Javadoc] for more details).



### <a name='development'></a>Development 
<sup><a href='#home'>[back to top]</a></sup>  

Want to contribute? Please do open up an issue for any bug reports, recommendation or feedback. 



### <a name='license'></a>License 
<sup><a href='#home'>[back to top]</a></sup> 


MIT


[fixer.io]:http://fixer.io
[Javadoc]:https://daytron.github.io/daytron-money/

