package examsystem.exsys.views.takeexam;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import examsystem.exsys.backend.ExamElements.Answer;
import examsystem.exsys.backend.ExamElements.Exam;
import examsystem.exsys.backend.ExamElements.Question;
import examsystem.exsys.backend.repositories.ExamRepository;
import examsystem.exsys.backend.repositories.QuestionRepository;
import examsystem.exsys.views.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@Route(value = "takeexam", layout = MainView.class)
@PageTitle("Vizsgázás")
@CssImport(value = "styles/views/vizsga/vizsga-view.css", include = "lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
public class TakeExamView extends Div{

    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private QuestionRepository questionRepository;

    Grid<HorizontalLayout> grid = new Grid<>();

    private Exam exam = null;
    private List<Question> questions;
    private List<HorizontalLayout> questionCards;
    private Button doneButton = new Button("Feladatlap leadása");

//    @Override
//    public void setParameter(BeforeEvent beforeEvent, String s) {
//        try {
//            Exam exam = examRepository.findById(Integer.parseInt(s));
//
//            add(new Label("Exam: " + exam.getExamName()));
//            grid.setItems(questionRepository.findAllByExamId(exam.getExamId()));
//            grid.addColumn(Question::getQuestionId).setHeader("Id");
//            grid.addColumn(Question::getQuestionText).setHeader("Question");
//            grid.addColumn(Question::getAnswer1).setHeader("Answer 1");
//            grid.addColumn(Question::getAnswer2).setHeader("Answer 2");
//            grid.addColumn(Question::getAnswer3).setHeader("Answer 3");
//            grid.addColumn(Question::getAnswer4).setHeader("Answer 4");
//            add(grid);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public TakeExamView() throws Exception {
        setId("takeexam-view");
        addClassName("takeexam-view");
        setSizeFull();
        VerticalLayout wrapper = createWrapper();
        createTitle(wrapper, "Vizsga neve");
        createParagraph(wrapper, "Ide kerül be a tanár leírása arról, hogy a feladatsor kitöltése közben mire tessenek figyelni, meg ilyenek.");
        if(!(exam == null)) {
            if(!(exam.getQuestions().isEmpty())) {
                for (int i = 0; i < exam.getNumberOfQuestions(); i++) {
                    createCard(questions.get(i), i+1, wrapper);
                }
            }
        }
        else{
            createDummyCard(wrapper, 1,"Dummy question text1", "Dummy answer 1", "Dummy answer 2", "Dummy answer 3", "Dummy answer 4");
            createDummyCard(wrapper, 2, "Dummy question text2", "Dummy answer 1", "Dummy answer 2", "Dummy answer 3", "Dummy answer 4");
            createDummyCard(wrapper, 3, "Dummy question text3", "Dummy answer 1", "Dummy answer 2", "Dummy answer 3", "Dummy answer 4");
            createDummyCard(wrapper, 4, "Dummy question text4", "Dummy answer 1", "Dummy answer 2", "Dummy answer 3", "Dummy answer 4");
        }
        createButtonLayout(wrapper);
        add(wrapper);
    }

    private VerticalLayout createWrapper() {
        VerticalLayout wrapper = new VerticalLayout();
        wrapper.setId("wrapper");
        wrapper.setSpacing(true);
        return wrapper;
    }

    private void createTitle(VerticalLayout wrapper, String text) {
        H1 h1 = new H1(text);
        wrapper.add(h1);
    }

    private void createParagraph(VerticalLayout wrapper, String text){
        Paragraph paragraph = new Paragraph(text);
        wrapper.add(paragraph);
    }

    private void createButtonLayout(VerticalLayout wrapper) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        doneButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(doneButton);
        wrapper.add(buttonLayout);
    }

    private void createCard(Question question, int questionIndex, VerticalLayout wrapper) {
        HorizontalLayout questionCard = new HorizontalLayout();
        questionCard.addClassName("question_card");
        questionCard.setSpacing(false);

        VerticalLayout questionCardContent = new VerticalLayout();
        questionCardContent.addClassName("description");
        questionCardContent.setSpacing(false);
        questionCardContent.setPadding(false);

        HorizontalLayout questionHeader = new HorizontalLayout();
        questionHeader.addClassName("header");
        questionHeader.setSpacing(false);
        Span questionText = new Span(questionIndex + ". " + question.getQuestionText());
        questionText.addClassName("questionText");
        questionHeader.add(questionText);

        HorizontalLayout answers = new HorizontalLayout();
        answers.addClassName("answers");
        answers.setSpacing(false);

        CheckboxGroup<Answer> answerCheckboxGroup = new CheckboxGroup<>();
        List<Answer> answerCheckboxes = question.getAnswers();
        answerCheckboxGroup.setItems(answerCheckboxes);
        answerCheckboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        answers.add(answerCheckboxGroup);


        questionCardContent.add(questionHeader, answers);
        questionCard.add(questionCardContent);
        wrapper.add(questionCard);
    }

    private void createDummyCard(VerticalLayout wrapper, int questionIndex, String questionText, String answer1, String answer2, String answer3, String answer4){
        List<String> answers = new ArrayList<>();
        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);
        answers.add(answer4);
        HorizontalLayout questionCard = new HorizontalLayout();
        questionCard.addClassName("question_card");
        questionCard.setSpacing(false);

        VerticalLayout questionCardContent = new VerticalLayout();
        questionCardContent.addClassName("description");
        questionCardContent.setSpacing(false);
        questionCardContent.setPadding(false);

        HorizontalLayout questionHeader = new HorizontalLayout();
        questionHeader.addClassName("header");
        questionHeader.setSpacing(false);
        Span questionHeaderText = new Span(questionIndex +". " + questionText);
        questionHeaderText.addClassName("questionText");
        questionHeader.add(questionText);

        HorizontalLayout answersLayout = new HorizontalLayout();
        answersLayout.addClassName("answers");
        answersLayout.setSpacing(false);

        CheckboxGroup<String> answerCheckboxGroup = new CheckboxGroup<>();
        answerCheckboxGroup.setItems(answers);
        answerCheckboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        answersLayout.add(answerCheckboxGroup);


        questionCardContent.add(questionHeader, answersLayout);
        questionCard.add(questionCardContent);
        wrapper.add(questionCard);
    }

}
