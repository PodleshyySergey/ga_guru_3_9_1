package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Locale;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.AttachmentsHelper.*;
import static io.qameta.allure.Allure.step;

import com.github.javafaker.Faker;

public class CheckFormTests {

    Faker faker = new Faker();
    FakeValuesService fakeValuesService = new FakeValuesService(
            new Locale("en-GB"), new RandomService());

    String  firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            mail = fakeValuesService.bothify("????##@gmail.com"),
            gender = "Male",
            phoneNumber = fakeValuesService.regexify("[0-9]{10}"),
            date = "18",
            month = "April",
            year = "1985",
            subject1 = "English",
            subject2 = "History",
            hobby1 = "Sports",
            hobby2 = "Reading",
            hobby3 = "Music",
            currentAddress = faker.address().fullAddress(),
            state = "Uttar Pradesh",
            city = "Agra",
            fileName = "1.png";

    @BeforeAll
    static void setUp() {
        addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        Configuration.startMaximized = true;
    }

    @AfterEach
    @Step("Attachments")
    public void afterEach() {
        attachScreenshot("Last screenshot");
        attachPageSource();
        attachAsText("Browser console logs", getConsoleLogs());

        closeWebDriver();
    }

    @Test
    public void fillForm() {
//        Заполнение и сохранение формы
        step("Открытие страницы с формой для заполнения.", ()-> open("https://demoqa.com/automation-practice-form"));
        step("Ввод имени и фамилии", () -> {
            $("#firstName").setValue(firstName);
            $("#lastName").setValue(lastName);
        });
        step("Ввод почтыб пола и номера телефона", ()-> {
            $("#userEmail").setValue(mail);
            $("#genterWrapper").$(byText(gender)).click();
            $("#userNumber").setValue(phoneNumber);
        });
        step("Ввод даты рождения.", ()-> {
            $("#dateOfBirthInput").click();
            $(".react-datepicker__year-select").selectOption(year);
            $(".react-datepicker__month-select").selectOption(month);
            $x("//div[@class='react-datepicker__month']//div[contains(text(),'" + date + "')]").click();
        });
        step ("Выбор изучаемых предметов.", ()-> {
            $("#subjectsInput").setValue(subject1).pressEnter();
            $("#subjectsInput").setValue(subject2).pressEnter();
        });
        step("Выбор хобби", ()-> {
            $("#hobbiesWrapper").$(byText(hobby1)).click();
            $("#hobbiesWrapper").$(byText(hobby2)).click();
            $("#hobbiesWrapper").$(byText(hobby3)).click();
        });
        step("Загрузка файла картинки.", ()-> {
            $("#uploadPicture").uploadFile(new File("src/test/resources/" + fileName));
        });
        step("Внесение адреса, штата и города.", ()-> {
            $("#currentAddress").setValue(currentAddress);
            $x("//div[@id='state']//input").setValue(state).pressEnter();
            $x("//div[@id='city']//input").setValue(city).pressEnter();
        });
        step("Обращение к кнопке сохранения формы.", ()-> $("#submit").click());
        step("Проверка отображения формы просмотра внесенных значений.", ()-> {
            $x("//div[@id='example-modal-sizes-title-lg']").shouldHave(Condition.text("Thanks for submitting the form"));
        });
        step("Проверка имени и фамилии", ()-> {
            $x("//tbody//td[contains(text(),'Student Name')]/../td[2]").shouldHave(Condition.text(firstName));
            $x("//tbody//td[contains(text(),'Student Name')]/../td[2]").shouldHave(Condition.text(lastName));
        });
        step("Проверка почты", ()-> {
            $x("//tbody//td[contains(text(),'Student Email')]/../td[2]").shouldHave(Condition.text(mail));
        });
        step("Проверка пола", ()-> {
            $x("//tbody//td[contains(text(),'Gender')]/../td[2]").shouldHave(Condition.text(gender));
        });
        step("Проверка номера телефона", ()-> {
            $x("//tbody//td[contains(text(),'Mobile')]/../td[2]").shouldHave(Condition.text(phoneNumber));
        });
        step("Проверка даты рождения", ()-> {
            $x("//tbody//td[contains(text(),'Date of Birth')]/../td[2]").shouldHave(Condition.text(date));
            $x("//tbody//td[contains(text(),'Date of Birth')]/../td[2]").shouldHave(Condition.text(month));
            $x("//tbody//td[contains(text(),'Date of Birth')]/../td[2]").shouldHave(Condition.text(year));
        });
        step("Проверка предметов", ()-> {
            $x("//tbody//td[contains(text(),'Subjects')]/../td[2]").shouldHave(Condition.text(subject1));
            $x("//tbody//td[contains(text(),'Subjects')]/../td[2]").shouldHave(Condition.text(subject2));
        });
        step("Проверка выбранных хобби", ()-> {
            $x("//tbody//td[contains(text(),'Hobbies')]/../td[2]").shouldHave(Condition.text(hobby1));
            $x("//tbody//td[contains(text(),'Hobbies')]/../td[2]").shouldHave(Condition.text(hobby2));
            $x("//tbody//td[contains(text(),'Hobbies')]/../td[2]").shouldHave(Condition.text(hobby3));
        });
        step("Проверка на наличие имени прикрепленного файла", ()-> {
            $x("//tbody//td[contains(text(),'Picture')]/../td[2]").shouldHave(Condition.text(fileName));
        });
        step("Проверка адреса", ()-> {
            $x("//tbody//td[contains(text(),'Address')]/../td[2]").shouldHave(Condition.text(currentAddress));
        });
        step("Проверка штата и города", ()-> {
            $x("//tbody//td[contains(text(),'State and City')]/../td[2]").shouldHave(Condition.text(state));
            $x("//tbody//td[contains(text(),'State and City')]/../td[2]").shouldHave(Condition.text(city));
        });

    }

}