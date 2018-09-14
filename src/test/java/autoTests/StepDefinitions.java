package autoTests;

import com.epam.calculator.Logic;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;


public class StepDefinitions {
    int firstnumber;
    int secondnumber;
    int actualResult;
    int expectedResult;
    Logic logic = new Logic();


    @Given("^Two numbers (\\d+) and (\\d+)$")
    public void two_numbers_and(int arg1, int arg2) throws Exception { Logic logic = new Logic();
        this.firstnumber = arg1;
        this.secondnumber = arg2;
    }

    @When("^Use '(.*)' action$")
    public void use_test_action(String action){
        switch (action) {
            case "addition":  actualResult = (int) logic.addition(firstnumber, secondnumber); break;
            case "subtraction":  actualResult = (int) logic.subtraction(firstnumber, secondnumber); break;
            case "multiplication":  actualResult = (int) logic.multiplication(firstnumber, secondnumber); break;
            case "division":  actualResult = (int) logic.division(firstnumber, secondnumber); break;
        }




    }

    @Then("^result is (\\d+)$")
    public void result_is(int arg1)  {
        this.expectedResult = arg1;
        assertEquals(expectedResult, actualResult);
    }
}
