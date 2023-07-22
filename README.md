# selenium-poi-automation

The project use Apache POI library and utility file to read test data from excel file and write result to excel file.

This application tests the deposit calculator on the site https://www.hdfcbank.com/personal/fd-test-calculator.
The appearance of the calculator is shown in the picture:

<picture>
 <img alt="calc-image" src="https://github.com/AleksandrSamusev/selenium-poi-automation/blob/main/src/test/resources/1.png?raw=true" width="" height="">
</picture>

The test data for the calculator fields are prepared and stored in a table in /testdata/caldata.xlsx file

<picture>
 <img alt="calc-image" src="https://github.com/AleksandrSamusev/selenium-poi-automation/blob/main/src/test/resources/3.png?raw=true" width="" height="">
</picture>

After starting the program, the test data from the cells of the table are substituted sequentially into the corresponding fields of the calculator. When all the values from the first line are substituted into the calculator, the "Calculate" button is pressed and the client's profit is calculated depending on the entered parameters.

Information with the amount of profit is displayed on the calculator page. The application copies the received actual data into the "Fact" column of the table and compares the expected profit result for row 1 with the actual result. If the results matching the value "passed" is put down to the "Result" column and the cell is filled with green color. If the values do not match, the value "failure" is put down and the cell is filled with red color.

<picture>
 <img alt="calc-image" src="https://github.com/AleksandrSamusev/selenium-poi-automation/blob/main/src/test/resources/2.png?raw=true" width="" height="">
</picture>

The result of testing the calculator is presented in the table:

<picture>
 <img alt="calc-image" src="https://github.com/AleksandrSamusev/selenium-poi-automation/blob/main/src/test/resources/4.png?raw=true" width="" height="">
</picture>