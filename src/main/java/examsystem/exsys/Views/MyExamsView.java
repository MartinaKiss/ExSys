package examsystem.exsys.Views;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import examsystem.exsys.Entities.Teacher;
import examsystem.exsys.ExamElements.Exam;
import examsystem.exsys.ExamElements.ExamResult;
import examsystem.exsys.Repositories.ExamRepository;
import examsystem.exsys.Repositories.ResultRepository;
import examsystem.exsys.Repositories.TeacherRepository;
import examsystem.exsys.Views.MainTemplateView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@Route(value = "myexams", layout = MainTemplateView.class)
@PageTitle("Vizsgáim")
@CssImport("styles/views/vizsgáim/vizsgáim-view.css")
public class MyExamsView extends Div {

    private static final long serialVersionUID = 1L;
    public static final String NAME = "Secure";
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private ResultRepository resultRepository;

    private Grid<Exam> exams;
    private Grid<Exam> completedExams;

    private TextField firstname = new TextField();
    private TextField lastname = new TextField();
    private TextField email = new TextField();
    private PasswordField password = new PasswordField();

    private Button editButton = new Button("Módosítás");
    private Button downloadButton = new Button("Letöltés");
    private Button deleteButton = new Button("Törlés");

    Teacher loggedInTeacher;

//    @Override
//    public void setParameter(BeforeEvent beforeEvent, String s) {
//        try {
//            loggedInTeacher = teacherRepository.findById(Integer.parseInt(s));
//
//            add(new Label("Vizsgáim"));
//            exams.setItems(examRepository.findAllByTeacherId((loggedInTeacher.getTeacherId())));
//            exams.addColumn(Exam::getExamName).setHeader("Vizsga neve");
//            add(exams);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    public MyExamsView() {
        setId("myexams-view");
        // Configure Grid
        VerticalLayout wrapper = new VerticalLayout();

        exams = new Grid<>();
        exams.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        exams.setHeightFull();
        exams.addColumn(Exam::getExamName).setHeader("Vizsga neve");
        exams.addColumn(Exam::getSubject).setHeader("Tárgy");

        exams.setDetailsVisibleOnClick(true);

        Init();
        // the grid valueChangeEvent will clear the form too
//        cancel.addClickListener(e -> exams.asSingleSelect().clear());

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);
        wrapper.add(splitLayout);
        VerticalLayout container = new VerticalLayout();
        wrapper.setWidth("95%");
        container.add(wrapper);
        container.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        add(container);
    }

    @PostConstruct
    private void Init(){
        exams.addItemClickListener(new ComponentEventListener<ItemClickEvent<Exam>>() {
            @Override
            public void onComponentEvent(ItemClickEvent<Exam> event) {
                if (!(examRepository.findById(event.getItem().getExamId()) == null )) {
                    Exam toShow = event.getItem();
                    exams.setDetailsVisible(toShow, !exams.isDetailsVisible(toShow)); //Switches between show and hide.

                }
            }});

        completedExams = new Grid<>();
        completedExams.setItemDetailsRenderer(new ComponentRenderer<>(exam -> {
            VerticalLayout div = new VerticalLayout();
            List<ExamResult> completedExamsList = null;
            try {
                completedExamsList = resultRepository.findAllByExamId(exam.getExamId());
            } catch (Exception e) {
                e.printStackTrace();
            }

            Grid<ExamResult> details = new Grid<>();
            details.setSizeFull();
            details.addThemeNames("no-row-borders", "row-stripes", "no-headers");
            assert completedExamsList != null;
            if (!completedExamsList.isEmpty()) {
                details.setDataProvider(new ListDataProvider<>(completedExamsList));
                details.setHeightByRows(true);
                div.setSizeFull();
                div.add(details);
            }
            return div;
        }));
    }

    private void createTitle(VerticalLayout wrapper, String text) {
        H1 h1 = new H1(text);
        wrapper.add(h1);
    }

    private void createParagraph(VerticalLayout wrapper, String text){
        Paragraph paragraph = new Paragraph(text);
        wrapper.add(paragraph);
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorDiv = new Div();
        editorDiv.setId("editor-layout");
        Paragraph paragraph = new Paragraph("Alább láthatja a kijelölt vizsga adatait és a kitöltött feladatsorok " +
                "eredményeit. A módosítás gombbal szerkesztheti a vizsga adatait, a törlés gombbal pedig eltávolíthatja " +
                "a vizsgát a rendszerből. A letöltés gombbal exportálhatja az eddig beküldött dolgozatok eredményeit cvs " +
                "formátumban.");
        H2 h2 = new H2("Vizsga adatai");
        editorDiv.add(h2,paragraph);
        editorDiv.setMinWidth("40%");
        editorDiv.add(completedExams);
        createButtonLayout(editorDiv);
        splitLayout.addToSecondary(editorDiv);
    }

    private void createButtonLayout(Div editorDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        buttonLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        buttonLayout.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        buttonLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        downloadButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        buttonLayout.add(editButton, downloadButton, deleteButton);
        editorDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        H1 h1 = new H1("Vizsgáim");
        Paragraph paragraph = new Paragraph("Alább láthatja az eddig létrehozott vizsgáit, új vizsgát pedig a Vizsga Létrehozása gombbal tud létrehozni.");
        Button createExamButton = new Button("+ Vizsga Létrehozása");
        createExamButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        wrapper.add(h1, paragraph, createExamButton);
        wrapper.add(exams);

    }
}
