import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Locale;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import com.github.javafaker.Faker;

public class CheckFormTests {

    Faker faker = new Faker();
    FakeValuesService fakeValuesService = new FakeValuesService(
            new Locale("en-GB"), new RandomService());
    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String mail = fakeValuesService.bothify("????##@gmail.com");
    String gender = "Male";
    String phoneNumber = fakeValuesService.regexify("[0-9]{10}");
    String date = "18";
    String month = "April";
    String year = "1985";
    String subject1 = "English";
    String subject2 = "History";
    String hobby1 = "Sports";
    String hobby2 = "Reading";
    String hobby3 = "Music";
    String currentAddress = faker.address().fullAddress();
    String state = "Uttar Pradesh";
    String city = "Agra";
    String fileName = "1.png";

    @BeforeAll
    static void setUp() {
        Configuration.startMaximized = true;
    }

    @Test
    public void fillForm() {
//        Заполнение и сохранение формы
        open("https://demoqa.com/automation-practice-form");
        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(mail);
        $("#genterWrapper").$(byText(gender)).click();
        $("#userNumber").setValue(phoneNumber);
        $("#dateOfBirthInput").click();
        $(".react-datepicker__year-select").selectOption(year);
        $(".react-datepicker__month-select").selectOption(month);
        $x("//div[@class='react-datepicker__month']//div[contains(text(),'" + date + "')]").click();
        $("#subjectsInput").setValue(subject1).pressEnter();
        $("#subjectsInput").setValue(subject2).pressEnter();
        $("#hobbiesWrapper").$(byText(hobby1)).click();
        $("#hobbiesWrapper").$(byText(hobby2)).click();
        $("#hobbiesWrapper").$(byText(hobby3)).click();
        $("#uploadPicture").uploadFile(new File("src/test/resources/" + fileName));
        $("#currentAddress").setValue(currentAddress);
        $x("//div[@id='state']//input").setValue(state).pressEnter();
        $x("//div[@id='city']//input").setValue(city).pressEnter();
        $("#submit").click();
        $x("//div[@id='example-modal-sizes-title-lg']").shouldHave(Condition.text("Thanks for submitting the form"));
//      Проверка имени и фамилии
        $x("//tbody//td[contains(text(),'Student Name')]/../td[2]").shouldHave(Condition.text(firstName));
        $x("//tbody//td[contains(text(),'Student Name')]/../td[2]").shouldHave(Condition.text(lastName));
//      Проверка почты
        $x("//tbody//td[contains(text(),'Student Email')]/../td[2]").shouldHave(Condition.text(mail));
//      Проверка пола
        $x("//tbody//td[contains(text(),'Gender')]/../td[2]").shouldHave(Condition.text("Male"));
//        Проверка номера телефона
        $x("//tbody//td[contains(text(),'Mobile')]/../td[2]").shouldHave(Condition.text(phoneNumber));
//        Проверка даты рождения
        $x("//tbody//td[contains(text(),'Date of Birth')]/../td[2]").shouldHave(Condition.text(date));
        $x("//tbody//td[contains(text(),'Date of Birth')]/../td[2]").shouldHave(Condition.text(month));
        $x("//tbody//td[contains(text(),'Date of Birth')]/../td[2]").shouldHave(Condition.text(year));
//        Проверка предметов
        $x("//tbody//td[contains(text(),'Subjects')]/../td[2]").shouldHave(Condition.text(subject1));
        $x("//tbody//td[contains(text(),'Subjects')]/../td[2]").shouldHave(Condition.text(subject2));
//        Проверка выбранных хобби
        $x("//tbody//td[contains(text(),'Hobbies')]/../td[2]").shouldHave(Condition.text(hobby1));
        $x("//tbody//td[contains(text(),'Hobbies')]/../td[2]").shouldHave(Condition.text(hobby2));
        $x("//tbody//td[contains(text(),'Hobbies')]/../td[2]").shouldHave(Condition.text(hobby3));
//        Проверка на наличие имени прикрепленного файла
        $x("//tbody//td[contains(text(),'Picture')]/../td[2]").shouldHave(Condition.text(fileName));
//        Проверка адреса
        $x("//tbody//td[contains(text(),'Address')]/../td[2]").shouldHave(Condition.text(currentAddress));
//        Проверка штата и города
        $x("//tbody//td[contains(text(),'State and City')]/../td[2]").shouldHave(Condition.text(state));
        $x("//tbody//td[contains(text(),'State and City')]/../td[2]").shouldHave(Condition.text(city));
    }

}