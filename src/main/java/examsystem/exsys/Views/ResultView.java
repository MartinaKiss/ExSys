package examsystem.exsys.Views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import examsystem.exsys.Entities.Exam;
import examsystem.exsys.Entities.ExamResult;
import examsystem.exsys.Entities.Question;
import examsystem.exsys.Entities.Teacher;
import examsystem.exsys.Repositories.ExamRepository;
import examsystem.exsys.Repositories.QuestionRepository;
import examsystem.exsys.Repositories.ResultRepository;
import examsystem.exsys.Repositories.TeacherRepository;
import org.apache.pdfbox.contentstream.PDContentStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.olli.FileDownloadWrapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

@Route(value = "result", layout = SecondaryTemplateView.class)
@PageTitle("Eredmény")
@CssImport("styles/views/eredmény/eredmény-view.css")
public class ResultView extends Div implements HasUrlParameter<String>, AfterNavigationObserver {

    @Autowired
    ResultRepository resultRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    ExamRepository examRepository;

    @Autowired
    QuestionRepository questionRepository;

    private ExamResult examResult;
    private Teacher teacher;
    private Exam exam;

    PDDocument document = new PDDocument();
    File sansFontFile = new File("src/main/java/examsystem/exsys/Views/ttf/DejaVuLGCSans.ttf");
    File boldFontFile = new File("src/main/java/examsystem/exsys/Views/ttf/DejaVuLGCSans-Bold.ttf");
    PDImageXObject logoImage = PDImageXObject.createFromFile("src/main/webapp/frontend/logoLightMode.png", document);
    PDFont dejaVuSansFont = PDType0Font.load(document, sansFontFile);
    PDFont dejaVuBoldFont = PDType0Font.load(document, boldFontFile);
    float mainFontSize = 12;
    float titleFontSize = 20;
    float secondTitleSize = 14;
    float footerFontSize = 8;

    private VerticalLayout wrapper;

    private Button backToMain = new Button("Vissza a főoldalra");

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        try {
            examResult = resultRepository.findById(Integer.parseInt(s));
            teacher = teacherRepository.findById(examResult.getTeacherId());
            exam = examRepository.findById(examResult.getExam().getExamId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        try {
            createTitle(wrapper);
            createParagraph(wrapper, "Vizsgázó neve: ", examResult.getStudentName());
            createParagraph(wrapper, "Vizsgázó neptun kódja: ", examResult.getStudentNeptun());
            createParagraph(wrapper, "Vizsgázó email címe: ", examResult.getStudentEmail());
            createParagraph(wrapper, "Vizsga tárgya, neve: ", examResult.getSubject() + " - " + examResult.getExamName());
            createParagraph(wrapper, "Vizsga dátuma: ", DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(examResult.getTimeOfSubmission()));
            if(teacher.getTeacherTitle().equals("-")) {
                createParagraph(wrapper, "Oktató neve: ", teacher.getTeacherLastName() + " " + teacher.getTeacherFirstName());
            }
            else {
                createParagraph(wrapper, "Oktató neve: ", teacher.getTeacherTitle() + " " + teacher.getTeacherLastName() + " " + teacher.getTeacherFirstName());
            }
            createParagraph(wrapper, "Maximum elérhető pontok / Elért pontok: ", examResult.getSumOfMaxPoints() + "/" + examResult.getSumOfAttainedPoints());
            createParagraph(wrapper, examResult.getAttainedGrade());

            Paragraph paragraph1 = new Paragraph("A fenti tanusítványt kérjük töltse le az alábbi gombra kattintva:");
            wrapper.add(paragraph1);

            Button download = new Button("Tanusítvány letöltése");
            download.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            FileDownloadWrapper buttonWrapper = new FileDownloadWrapper(
                    new StreamResource(String.valueOf(examResult.getExamResultId()).concat(".pdf"), () -> new ByteArrayInputStream(getPDF()))
            );

            buttonWrapper.wrapComponent(download);

            wrapper.add(buttonWrapper);

            Paragraph paragraph2 = new Paragraph("A letöltött tanusítványt őrízze meg a jegy rögzítéséig, hogy igazolni tudja a vizsgán való részvételét és az elért eredményét.");
            wrapper.add(paragraph2);
            createButtonLayout(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultView() throws IOException {
        setId("result-view");
        wrapper = createWrapper();
        VerticalLayout container = new VerticalLayout();
        wrapper.setWidth("90%");
        container.setAlignItems(FlexComponent.Alignment.CENTER);
        container.add(wrapper);
        add(container);
    }

    private void createTitle(VerticalLayout wrapper) {
        H1 h1 = new H1("Eredmény");
        wrapper.add(h1);
    }

    private void createParagraph(VerticalLayout wrapper, int value){
        HorizontalLayout container = new HorizontalLayout();
        container.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        Paragraph paragraph = new Paragraph("Elért jegy: ");
        H4 h4 = new H4(String.valueOf(value));
        container.add(paragraph,h4);
        container.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
        wrapper.add(container);
    }

    private void createParagraph(VerticalLayout wrapper, String text, String value){
        HorizontalLayout container = new HorizontalLayout();
        Paragraph paragraph = new Paragraph(text);
        H4 h4 = new H4(String.valueOf(value));
        container.add(paragraph,h4);
        container.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
        wrapper.add(container);
    }

    private VerticalLayout createWrapper() {
        VerticalLayout wrapper = new VerticalLayout();
        wrapper.setId("wrapper");
        wrapper.setSpacing(false);
        return wrapper;
    }

    private void createButtonLayout(VerticalLayout wrapper) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        buttonLayout.setWidth("100%");
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        backToMain.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(backToMain);
        wrapper.add(buttonLayout);
    }

    private byte[] getPDF() {

        List<String> stringList = new ArrayList<>();

        stringList.add("Vizsgázó neve: " + examResult.getStudentName());
        stringList.add("Vizsgázó neptun kódja: " + examResult.getStudentNeptun());
        stringList.add("Vizsgázó email címe: " + examResult.getStudentEmail());
        stringList.add("Vizsga tárgya, neve: " + examResult.getSubject() + " - " + examResult.getExamName());
        stringList.add("Vizsga dátuma: " + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(examResult.getTimeOfSubmission()));
        if(teacher.getTeacherTitle().equals("-")) {
            stringList.add("Oktató neve: " + teacher.getTeacherLastName() + " " + teacher.getTeacherFirstName());
        }
        else {
            stringList.add("Oktató neve: " + teacher.getTeacherTitle() + " " + teacher.getTeacherLastName() + " " + teacher.getTeacherFirstName());
        }
        stringList.add("Maximum elérhető pontok / Elért pontok: " + examResult.getSumOfMaxPoints() + "/" + examResult.getSumOfAttainedPoints());
        stringList.add("Elért jegy: " + examResult.getAttainedGrade());

        PDPage page= new PDPage();
        
        try {
            PDPageContentStream mainPageContentStream = new PDPageContentStream(document, page);
            document.addPage(page);

            drawTemplate(mainPageContentStream, 1);

            mainPageContentStream.beginText();
            mainPageContentStream.setFont(dejaVuSansFont, mainFontSize);
            mainPageContentStream.newLineAtOffset(50, 650);
            mainPageContentStream.setLeading(35f);
            for (String line:stringList) {
                mainPageContentStream.showText(line);
                mainPageContentStream.newLine();
            }
            mainPageContentStream.endText();

            mainPageContentStream.close();

            List<Question> questionList = new ArrayList<>(questionRepository.findAllByExamId(exam.getExamId()));
            writeQuestionPages(questionList);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            document.save(os);
            return os.toByteArray();

        }catch (Exception e){
            Notification.show("Caught exception :" + e);
        }
        return null;
    }

    public void drawTemplate (PDPageContentStream questionPageContentStream, int pageNumber) throws IOException {

        questionPageContentStream.drawImage(logoImage, 50f, 700f, 103.5f, 40.45f);
        questionPageContentStream.setStrokingColor(0, 43, 77);
        questionPageContentStream.setNonStrokingColor(0, 43, 77);
        questionPageContentStream.setLineWidth(3f);
        questionPageContentStream.moveTo(50f, 690f);
        questionPageContentStream.lineTo(550f, 690f);
        questionPageContentStream.stroke();

        questionPageContentStream.moveTo(50f, 90f);
        questionPageContentStream.lineTo(550f, 90f);
        questionPageContentStream.stroke();

        questionPageContentStream.beginText();
        questionPageContentStream.setFont(dejaVuBoldFont, titleFontSize);
        questionPageContentStream.newLineAtOffset(160.5f, 708.5f);
        questionPageContentStream.setLeading(20f);
        questionPageContentStream.showText("Vizsga Eredmény");
        questionPageContentStream.endText();

        questionPageContentStream.beginText();
        questionPageContentStream.setFont(dejaVuSansFont, footerFontSize);
        questionPageContentStream.setNonStrokingColor(0, 43, 77);
        questionPageContentStream.newLineAtOffset(50, 75);
        questionPageContentStream.setLeading(14f);
        questionPageContentStream.showText("Kérjük örizze meg a tanusítványt ameddig a vizsgán elért jegy helyesen rögzítésre nem kerül a tanulmányi rendszerben.");
        questionPageContentStream.newLine();
        questionPageContentStream.showText("Ez a tanusítvány igazolja az fent említett hallgató viszgán való részvételét és a vizsgán elért eredményét.");
        questionPageContentStream.newLineAtOffset(250, -20);
        questionPageContentStream.showText(String.valueOf(pageNumber));
        questionPageContentStream.endText();
    }

    public void writeQuestionPages (List<Question> questionList) throws IOException {
        int flag = 0;
        int pageNumber = 1;
        while (flag < questionList.size() - 1){
            PDPage newPage = new PDPage();
            PDPageContentStream contentStream = new PDPageContentStream(document, newPage);
            document.addPage(newPage);
            drawTemplate(contentStream, pageNumber + 1);
            contentStream.setLeading(20f);
            int questionPerPageCounter = 0;
            int previousPageFlag = flag;
            for (int i = flag; i < previousPageFlag + 5 && i <questionList.size(); i++){
                Question question = questionList.get(i);
                contentStream.beginText();
                contentStream.setNonStrokingColor(0, 43, 77);

                contentStream.newLineAtOffset(50, 650 - 110 * questionPerPageCounter);
                contentStream.setFont(dejaVuBoldFont, secondTitleSize);
                contentStream.showText(flag + 1 + ". " + question.getQuestionText());
                contentStream.newLine();

                List<String> answerList = new ArrayList<>();
                answerList.add(question.getAnswer1());
                answerList.add(question.getAnswer2());
                answerList.add(question.getAnswer3());
                answerList.add(question.getAnswer4());

                JSONObject json = new JSONObject(examResult.getAnswersList());

                for (String answer:answerList) {
                    contentStream.setNonStrokingColor(0, 43, 77);
                    if (json.toMap().containsValue(answer)) {
                        if (BCrypt.checkpw(answer, question.getSolution())) {
                            contentStream.setNonStrokingColor(23, 117, 48);
                        } else {
                            contentStream.setNonStrokingColor(128, 0, 13);
                        }
                    }
                    contentStream.setFont(dejaVuSansFont, mainFontSize);
                    contentStream.showText(answer);
                    contentStream.newLine();
                }
                questionPerPageCounter = questionPerPageCounter + 1;
                flag = flag + 1;
                contentStream.endText();
            }
            contentStream.close();
            pageNumber = pageNumber + 1;
        }
    }
}
