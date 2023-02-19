package ru.netology.Pages;

import ru.netology.DataHelper.DataHelper;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        return new DashboardPage();
    }

    public VerificationPage invalidVerify(DataHelper.InvalidVerificationCode invalidVerificationCode) {
        codeField.setValue(invalidVerificationCode.getInvalidCode());
        verifyButton.click();
        $(".notification__content").shouldHave(text("Ошибка! " + "Неверно указан код! Попробуйте ещё раз."))
                .shouldBe(visible, Duration.ofSeconds(15));
        return null;
    }
}
