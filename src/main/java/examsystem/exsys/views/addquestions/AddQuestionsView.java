package examsystem.exsys.views.addquestions;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import examsystem.exsys.backend.ExamElements.Answer;
import examsystem.exsys.backend.ExamElements.Question;
import examsystem.exsys.views.main.MainView;

import java.util.ArrayList;
import java.util.List;

@Route(value = "addquestions", layout = MainView.class)
@PageTitle("Kérdések hozzáadása")
@CssImport("styles/views/tvkérdésekhozzáadása/t-vkérdésekhozzáadása-view.css")
public class AddQuestionsView extends Div {

    private Button cancel = new Button("Mégse");
    private Button nextButton = new Button("Tovább");
    private int numberOfQuestions = 8;

    public AddQuestionsView() {
        setId("addquestions-view");
        VerticalLayout wrapper = createWrapper();

        createTitle(wrapper);
        createParagraph(wrapper, "Itt adhatja meg a feladatsor kérdéseit, a hozzájuk tartozó válaszokat és a " +
                "kérdésekért kapható pontokat. A válaszok sorai mellet lévő checkbox-ot bepipálva tudja megjelölni a jó " +
                "válaszokat. Több jó válasz is megadható. Ha végzett a kérdések bevítelével és alaposan leellenőrizte " +
                "azokat, akkor a Tovább gombra kattintva léphet tovább az utolsó lépésre, ahol megadhatja a vizsga " +
                "ponthatárait, azt, hogy a helytelen válaszokért kerüljön-e pont levonásra, illetve hogy mennyi pont " +
                "kerüljön levonásra.");

        for(int i = 0; i<8; i++) {
            createFormLayout(wrapper, i+1);
        }
        createButtonLayout(wrapper);

        nextButton.addClickListener(e -> {
            Notification.show("Not implemented");
        });

        wrapper.setWidth("90%");
        VerticalLayout container = new VerticalLayout();
        container.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        container.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        container.setAlignItems(FlexComponent.Alignment.CENTER);
        container.add(wrapper);
        add(container);
    }

    private void createTitle(VerticalLayout wrapper) {
        H1 h1 = new H1("Kérdések hozzáadása");
        wrapper.add(h1);
    }

    private VerticalLayout createWrapper() {
        VerticalLayout wrapper = new VerticalLayout();
        wrapper.setId("wrapper");
        wrapper.setSpacing(true);
        return wrapper;
    }

    private void createParagraph(VerticalLayout wrapper, String text){
        Paragraph paragraph = new Paragraph(text);
        wrapper.add(paragraph);
    }

    private void createFormLayout(VerticalLayout wrapper, int index) {
        FormLayout formLayout = new FormLayout();
        formLayout.addClassName("question_card");

        HorizontalLayout questionHeader = new HorizontalLayout();
        questionHeader.addClassName("header");
        questionHeader.setSpacing(false);
        Span questionText = new Span(index + ". kérdés: ");
        questionText.addClassName("questionText");
        TextField questionTextField = new TextField();
        questionHeader.add(questionText, questionTextField);

        VerticalLayout answersLayout = new VerticalLayout();
        answersLayout.addClassName("answers");
        answersLayout.setSpacing(true);

        TextField answer1 = new TextField();
        TextField answer2 = new TextField();
        TextField answer3 = new TextField();
        TextField answer4 = new TextField();
        Checkbox checkbox1 = new Checkbox();
        Checkbox checkbox2 = new Checkbox();
        Checkbox checkbox3 = new Checkbox();
        Checkbox checkbox4 = new Checkbox();

        FormLayout.FormItem questionTextFormItem = addFormItem(wrapper, formLayout,
                questionTextField, index + ". Kérdés szövege");
        formLayout.setColspan(questionTextFormItem, 2);

        VerticalLayout answerContainer = new VerticalLayout();
        HorizontalLayout answerLine1 = new HorizontalLayout();
        FormLayout.FormItem answer1FormItem = addFormItem(wrapper, formLayout,
                answer1, "1. válasz szövege");
        formLayout.setColspan(answer1FormItem, 2);
        answerLine1.add(answer1FormItem, checkbox1);

        HorizontalLayout answerLine2 = new HorizontalLayout();
        FormLayout.FormItem answer2FormItem = addFormItem(wrapper, formLayout,
                answer2, "2. válasz szövege");
        formLayout.setColspan(questionTextFormItem, 2);
        answerLine2.add(answer2FormItem, checkbox2);

        HorizontalLayout answerLine3 = new HorizontalLayout();
        FormLayout.FormItem answer3FormItem = addFormItem(wrapper, formLayout,
                answer3, "3. válasz szövege");
        formLayout.setColspan(questionTextFormItem, 2);
        answerLine3.add(answer3FormItem, checkbox3);

        HorizontalLayout answerLine4 = new HorizontalLayout();
        FormLayout.FormItem answer4FormItem = addFormItem(wrapper, formLayout,
                answer4, "4. válasz szövege");
        formLayout.setColspan(questionTextFormItem, 2);
        answerLine4.add(answer4FormItem, checkbox4);

        answerContainer.add(answerLine1, answerLine2, answerLine3, answerLine4);
        formLayout.add(questionTextFormItem, answerContainer);
        wrapper.add(formLayout);
    }

    private void createButtonLayout(VerticalLayout wrapper) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        nextButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(cancel);
        buttonLayout.add(nextButton);
        wrapper.add(buttonLayout);
    }

    private FormLayout.FormItem addFormItem(VerticalLayout wrapper,
            FormLayout formLayout, Component field, String fieldName) {
        FormLayout.FormItem formItem = formLayout.addFormItem(field, fieldName);
        wrapper.add(formLayout);
        field.getElement().getClassList().add("full-width");
        return formItem;
    }

}
