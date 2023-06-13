# Init Phase and analysis via tests

2. For assignment chosen java as language of choice, removed unnecessary
1. Moved assignments docs to 'docs' directory to separate code and documentation
1. Added Gradle as a dependency manager of choice to provide Junit as test platform and open way to add dependency for
   HTTP handling
1. Applied standard java project structure with 'main' and 'test' to separate test and deployable code
1. Started of with creating tests verifying which of functionalities from assignment work properly on current code
1. Verified holidays in Sweden and checked dates left by colleague and confront them with each other
1. Based on dates left by colleague created parametrized tests for 'getTollFee()'
    1. Using rules in [ASSIGNMENT](ASSIGNMENT.md) created [get-toll-fee.csv](../src/test/resources/get-toll-fee.csv)
       with mapped fee value for particular time
    1. Debugging why '2013-03-26 14:25:00' is failing tests for `feeAmount == SEK 8` - found issue in `getTollFee()`
       ranges hour 14 not handled
    1. Attempting to fix problem without refactoring logic tree in `getTollFee()`
        1. While fixing error found another syntax problem
           with ```hour >= 8 && hour <= 14 && minute >= 30 && minute <= 59```
        2. Adding `2013-01-14 13:29:00` dateTime to parametrized file that will showcase problem in above statement
        3. Fixed statement to: `hour == 8 && minute >= 30 || (hour > 8 && hour <= 14)`
        4. Found another problem with statement `hour == 15 && minute >= 0 || hour == 16 && minute <= 59` - it only
           works because of previous statement fixing it to `hour == 15 && minute >= 30 || hour == 16 && minute <= 59`
        5. By fixing all above point new problem showcased isTollFreeDate() do not work due to not intuitive API of
           java.util.Date `getYear()` method returning current year - 1900. Hot-fixing it in preparation for refactor by
           adding 1900 to year value
        6. Tests pass
1. Written tests for Single Charge Rule and capping daily fees at 60SEK
    2. Found issue with calculating Single Charge Rule when more than 2 passes occur under one hour
    3. Decided that fixing the issue in current form of program would be time-consuming - test will stay but will hop to
       refactor phase

## Refactor of domain logic

1. Extract TollFeeCalculator to separate class with its own tests - this may be invalid decision as currently it is
   public method published in Library API
2. Removed unnecessary Car and Motorbike implementations which were never used, moved them with exempt vehicles to
   Enum and modified Vehicle interface to better consume VehicleType enum
    1. Due to this decision had to add Mockito as there is no Vehicle implementation - in future probably will change
       interface to "Tollable" and set Vehicle as Domain object implementing this interface
3. Analyzed exempt vehicles and implementation does not match documentation - fixed it
4. Migrate from java.util.Date to LocalDateTime and LocalTime
5. Simplified TollFeeCalculator logic and extracted Calendar
6. Due to short time for this activity stopped logging steps of refactor

## Instructions for deploy

1. Build image with:
   ```gradle bootBuildImage --imageName=app```
2. Run with docker compose
   ```docker compose up```