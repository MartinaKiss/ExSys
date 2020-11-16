package examsystem.exsys.Views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import examsystem.exsys.Entities.Teacher;
import examsystem.exsys.ExamElements.Exam;
import examsystem.exsys.ExamElements.ExamResult;
import examsystem.exsys.Repositories.ExamRepository;
import examsystem.exsys.Repositories.ResultRepository;
import examsystem.exsys.Repositories.TeacherRepository;
import examsystem.exsys.Views.ViewComponents.Reloader;
import examsystem.exsys.Views.ViewComponents.Utils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.vaadin.olli.FileDownloadWrapper;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Route(value = "myexams", layout = MainTemplateView.class)
@PageTitle("Vizsgáim")
@CssImport("styles/views/vizsgáim/vizsgáim-view.css")
public class MyExamsView extends Div implements AfterNavigationObserver, Reloader {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    Utils utils;

    private Grid<Exam> exams;

    private List<String[]> resultList;

    private Teacher teacher;

    @PostConstruct
    private void init(){
        VerticalLayout container = new VerticalLayout();
        container.setAlignItems(FlexComponent.Alignment.CENTER);
        VerticalLayout wrapper = new VerticalLayout();


        exams = new Grid<>(Exam.class);
        setHeightFull();
        exams.removeAllColumns();
        exams.addColumn(Exam::getExamName).setHeader("Vizsga neve").setKey("name").setAutoWidth(true);
        exams.addColumn(Exam::getSubject).setHeader("Tárgy").setKey("subject").setAutoWidth(true);
        exams.addColumn(Exam::isExamActive).setHeader("Vizsga állapota").setKey("status").setAutoWidth(true);
        exams.addColumn(Exam::getEnterExamCode).setHeader("Beléptető kód").setKey("code").setAutoWidth(true);
        exams.addComponentColumn(this::starOrStopExamButton).setAutoWidth(true);
        exams.addComponentColumn(this::buildEditButton).setAutoWidth(true);
        exams.addComponentColumn(this::buildDeleteButton).setAutoWidth(true);
        exams.addComponentColumn(this::buildExportButton).setAutoWidth(true);

        H1 h1 = new H1("Vizsgáim");
        Paragraph paragraph = new Paragraph("Alább láthatja az eddig létrehozott vizsgáit, új vizsgát pedig a " +
                "Vizsga Létrehozása gombbal tud létrehozni. A táblázat soraiban található gombokkal tudja " +
                "elindítani/leállítani, szerkeszteni és törölni a vizsgát, illetve letölteni a kitöltött vizsgák" +
                " eredményeit csv formátumban. A 6 jegyű beléptető kódot kell megadnia a tanulóknak, hogy be tudjanak " +
                "lépni a vizsgára.");

        Button createExamButton = new Button("Vizsga Létrehozása", VaadinIcon.PLUS.create());
        createExamButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        createExamButton.addClickListener(e -> UI.getCurrent().navigate(CreateExamView.class));
        wrapper.add(h1, paragraph, createExamButton);
        container.add(wrapper);
        container.add(exams);
        add(container);
    }

    @Override
    public void reload() {
        exams.setItems(examRepository.findAllByTeacherId(teacher.getTeacherId()));
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        try {
            int teacherId = (int) UI.getCurrent().getSession().getAttribute("teacher");
            teacher = teacherRepository.findById(teacherId);
            exams.setItems(examRepository.findAllByTeacherId(teacher.getTeacherId()));
        } catch (NullPointerException nullPointer) {
            Notification.show("Ön nincs bejelentkezve");
        }
    }

    private Button starOrStopExamButton(Exam exam) {
        Button button = new Button("Indítás/Leállítás");
        button.addClickListener(e -> {
            try {
                if(exam.isExamActive().equals("Inaktív")) {
                    String enterCode = utils.generateEnterExamCode();
                    while (isExistByEnterExamCode(enterCode)){
                        enterCode = utils.generateEnterExamCode();
                    }
                    exam.setEnterExamCode(enterCode);
                    exam.setExamActive(true);
                }
                else {
                    exam.setEnterExamCode(null);
                    exam.setExamActive(false);
                }
                examRepository.update(exam);
                reload();

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        return button;
    }

    private Button buildEditButton(Exam exam) {
        Button editButton = new Button("Szerkesztés", VaadinIcon.PENCIL.create());
        editButton.addClickListener(e -> UI.getCurrent().navigate("createexam/" + exam.getExamId()));
        return editButton;
    }

    private Button buildDeleteButton(Exam exam) {
        Button deleteButton = new Button("Törlés",VaadinIcon.TRASH.create());
        deleteButton.addClickListener(e -> {
            try {
                deleteExam(exam);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        return deleteButton;
    }

    private void deleteExam(Exam exam) {
        examRepository.delete(exam);
        exams.setItems(examRepository.findAllByTeacherId(teacher.getTeacherId()));
    }

    private FileDownloadWrapper buildExportButton(Exam exam) {
        Button exportButton = new Button("Eredmények letöltése",VaadinIcon.ARROW_DOWN.create());
        try {
            List<ExamResult> results = new ArrayList<>(resultRepository.findAllByExamId(exam.getExamId()));

            resultList = new ArrayList<>();
            for (ExamResult examResult:results) {
                String[] resultArray = new String[]{examResult.getStudentName(), examResult.getStudentNeptun(), examResult.getStudentEmail(),
                            String.valueOf(examResult.getSumOfMaxPoints()), String.valueOf(examResult.getSumOfAttainedPoints()),
                            String.valueOf(examResult.getAttainedGrade())};
                resultList.add(resultArray);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }


        FileDownloadWrapper buttonWrapper = new FileDownloadWrapper(
                new StreamResource(exam.getExamName().concat(".csv"), () -> {
                    try {
                        return new ByteArrayInputStream(getCSV(exam.getExamName()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
        );
        buttonWrapper.wrapComponent(exportButton);

        return buttonWrapper;
    }

    private byte[] getCSV (String filename) throws IOException {
        File csvOutputFile = new File(filename);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println("\"Tanuló neve\",\"Tanuló neptun kódja\",\"Tanuló email címe\",\"Max pontszám\",\"Elért pontszám\",\"Érdemjegy\"");
            for (String[] line : resultList) {
                pw.println(convertToCSV(line));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return FileUtils.readFileToByteArray(csvOutputFile);
    }

    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    public boolean isExistByEnterExamCode(String enterExamCode){
        try {
            Optional<Exam> result = Optional.ofNullable(examRepository.findByEnterExamCode(enterExamCode));
            return true;
        }
        catch (EmptyResultDataAccessException emptyResultDataAccessException){
            return false;
        }
    }

}
