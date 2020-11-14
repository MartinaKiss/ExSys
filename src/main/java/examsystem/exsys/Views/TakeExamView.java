package examsystem.exsys.Views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import examsystem.exsys.ExamElements.Exam;
import examsystem.exsys.ExamElements.ExamResult;
import examsystem.exsys.ExamElements.Question;
import examsystem.exsys.Repositories.ExamRepository;
import examsystem.exsys.Repositories.QuestionRepository;
import examsystem.exsys.Repositories.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Dictionary;
import java.util.Hashtable;


@Route(value = "takeexam", layout = SecondaryTemplateView.class)
@PageTitle("Vizsgázás")
@CssImport(value = "styles/views/vizsga/vizsga-view.css", include = "lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
public class TakeExamView extends Div implements HasUrlParameter<String>, AfterNavigationObserver {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ResultRepository resultRepository;

    private TextField fullName = new TextField();
    private TextField email = new TextField();
    private TextField neptunCode = new TextField();
    private Exam exam;
    private Dictionary<Integer, String> selectedAnswersList;

    private Button doneButton = new Button("Feladatlap leadása");
    private VerticalLayout wrapper;

    @PostConstruct
    public void init() {
        System.out.println("Init did a thing");
        setId("takeexam-view");
        setSizeFull();
        selectedAnswersList = new Hashtable<>();
        selectedAnswersList.put(1, "cica");
        wrapper = createWrapper();
        wrapper.setHeightFull();
        add(wrapper);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        System.out.println("SetParam did a thing");
        exam = examRepository.findByEnterExamCode(s);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        System.out.println("AfterNav did a thing");
        createTitle(wrapper, exam.getSubject() + " - " + exam.getExamName());
        createParagraph(wrapper, exam.getDescription());
        createDataFormLayout(wrapper);
        try {
            for (Question question:questionRepository.findAllByExamId(exam.getExamId())) {
                createQuestionCard(question, wrapper);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        createButtonLayout(wrapper);
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
        doneButton.addClickListener(e -> {
            ExamResult examResult = new ExamResult();
            examResult.setExamName(exam.getExamName());
            examResult.setSubject(exam.getSubject());
            examResult.setTeacherId(exam.getTeacher().getTeacherId());
            examResult.setStudentName(fullName.getValue());
            examResult.setStudentNeptun(neptunCode.getValue());
            examResult.setStudentEmail(email.getValue());
            examResult.setSumOfMaxPoints(exam.getMaxSumOfPoints());
            examResult.setTimeOfSubmission(LocalDateTime.now());
            examResult.setSumOfAttainedPoints(calculateAttainedPoints());
            examResult.setAttainedGrade(gradeExam(calculateAttainedPoints()));
            examResult.setExam(exam);
            resultRepository.save(examResult);
            UI.getCurrent().navigate("result/" + examResult.getExamResultId());
        });
        buttonLayout.add(doneButton);
        wrapper.add(buttonLayout);
    }

    private void createQuestionCard(Question question, VerticalLayout wrapper){
        selectedAnswersList.put(question.getQuestionId(),"null");
        RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        radioGroup.setLabel(question.getQuestionText());
        radioGroup.setItems(question.getAnswer1(),question.getAnswer2(), question.getAnswer3(), question.getAnswer4());
        radioGroup.setId(String.valueOf(question.getQuestionId()));
        radioGroup.addValueChangeListener(e -> {
            selectedAnswersList.put(question.getQuestionId(),radioGroup.getValue());
            Notification.show("Question number " + question.getQuestionId() + " changed to " + radioGroup.getValue());
        });
        wrapper.add(radioGroup);

    }

    private void createDataFormLayout(VerticalLayout wrapper) {
        FormLayout formLayout = new FormLayout();
        FormLayout.FormItem fullNameFormItem = addFormItem(wrapper, formLayout,
                fullName, "Teljes név");
        fullNameFormItem.setId("fullname");
        formLayout.setColspan(fullNameFormItem, 1);
        FormLayout.FormItem emailFormItem = addFormItem(wrapper, formLayout,
                email, "Email");
        emailFormItem.setId("email");
        formLayout.setColspan(emailFormItem, 1);
        FormLayout.FormItem neptunCodeFormItem = addFormItem(wrapper, formLayout,
                neptunCode, "Neptun kód");
        neptunCodeFormItem.setId("neptun");
        formLayout.setColspan(emailFormItem, 1);
    }

    private FormLayout.FormItem addFormItem(VerticalLayout wrapper,
                                            FormLayout formLayout, Component field, String fieldName) {
        FormLayout.FormItem formItem = formLayout.addFormItem(field, fieldName);
        wrapper.add(formLayout);
        field.getElement().getClassList().add("full-width");
        return formItem;
    }

    private double calculateAttainedPoints(){
        double attainedPoints = 0;
        try {
            for (Question question : questionRepository.findAllByExamId(exam.getExamId())) {
                if (selectedAnswersList.get(question.getQuestionId()).equals(question.getSolution())){
                    attainedPoints = attainedPoints + question.getAttainablePoints();
                }
                else if (exam.isWrongAnswerMinusPoint()){
                    attainedPoints = attainedPoints - exam.getValueOfMinusPoint();
                }
            }
            if (attainedPoints < 0){
                attainedPoints = 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return attainedPoints;
    }

    private int gradeExam(double attainedPoints){
        int grade = 1;

        if (attainedPoints >= exam.getGradeFivePointLimit()){
            grade = 5;
            return grade;
        }
        else if (attainedPoints >= exam.getGradeFourPointLimit()){
            grade = 4;
            return grade;
        }
        else if (attainedPoints >= exam.getGradeThreePointLimit()){
            grade = 3;
            return grade;
        }
        else if (attainedPoints >= exam.getGradeTwoPointLimit()){
            grade = 2;
            return grade;
        }
        return grade;
    }
}
