# Important Information

Changelog for performed operations that do not require conversation in my opinion is documented
in [changelog](changelog.md) file.

# Questions

1. Formatting does not comply to Java standards capitalization of methods, opening curly braces at new line -
   the question is if it was internal change of standard in project or just mistake?
2. How often does tollFreeVehicles collection changes? Depending on answer it can be beneficial to either store it in
   enum or database - as value in HashMap was never used I used Enum
3. At the moment two methods are public in Calculator: GetTollFee(Date, Vehicle), getTax(Vehicle, Date[]) is contract
   that both are available to client?
4. Can we replace java.util.Date with LocalDateTime?
5. Requirement "The single charge rule" states that one car can be charged only once in the hour that brings couple of
   questions:

- How to identify car? Easiest way is by plates number - but currently there is no domain logic for such conclusions
- In my country we do not have such law so my understanding may be invalid but when I read "tolling stations" I
  understood that payment is done on spot. Question is: When we pass gate at 6:01 we pay 8 SEK and when we leave at 6:31
  do we pay 5SEK? 13SEK - 8 SEK previously paid -> due to this confusion and no logic with payment in current code I
  will omit payment logic and only implement charging

6. Can singleChargeRule be calculated multiple times a day? Implementation suggests otherwise and documentation does not
   give info - will assume this is possible because documentation does not forbid this assumption
7. IMO there is a redundancy in model. Vehicle interface is implemented by Car and Motorbike which is fine
   but `getVehicleType()` is redundant then. Were there external factors for such approach? Question is driven by the
   fact that all listed classes and interface are used in public methods of calculator which will be shared in API of
   library and change of contract to clients can be cumbersome in the future. Additionally, there is no way to test
   tollFreeVehicles as they have no class implementation so far.

   Currently, Motorbike and Car have no field so as API DTOs they are indistinguishable
8. Is Motorcycle and Motorbike the same in context of fees? Code has implementation of Motorbike and documentation talks
   about Motorcycle - decided to merge both concepts in favor of documentation.
9. Should Tractor be taken into consideration - it does not appear in documentation but had been implemented?
10. Implementation of capping daily fees does not comply with initial implementation of colleague question is: are days
    sent to system always in span of one day like in implementation or cap be spanned on multiple days? Java
    implementation of Date.class does not forbid that so I will implement fee cap for multiple days.

# 2016 Holidays in Sweden

[source](https://www.calendarlabs.com/holidays/sweden/2013)

| Day of week | Date         | Name           |
|-------------|--------------|----------------|
| Tuesday     | Jan 01, 2013 | New Year's Day |
| Sunday      | Jan 06, 2013 | Epiphany       |
| Friday      | Mar 29, 2013 | Good Friday    |
| Sunday      | Mar 31, 2013 | Easter         |
| Monday      | Apr 01, 2013 | Easter Monday  |
| Wednesday   | May 01, 2013 | May Day        |
| Thursday    | May 09, 2013 | Ascension Day  |
| Sunday      | May 19, 2013 | Whit Sunday    |
| Monday      | May 20, 2013 | Whit Monday    |
| Thursday    | Jun 06, 2013 | National Day   |
| Saturday    | Jun 22, 2013 | Midsummer Day  |
| Friday      | Nov 01, 2013 | All Saints Day |
| Wednesday   | Dec 25, 2013 | Christmas Day  |
| Thursday    | Dec 26, 2013 | Boxing Day     |
| Tuesday     | Dec 31, 2013 | New Year's Eve |