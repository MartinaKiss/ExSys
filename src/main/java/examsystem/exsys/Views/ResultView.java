package examsystem.exsys.Views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.vaadin.olli.FileDownloadWrapper;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Route(value = "result", layout = MainTemplateView.class)
@PageTitle("Eredmény")
@CssImport("styles/views/eredmény/eredmény-view.css")
public class ResultView extends Div {

    private String pattern = "yyyy-MM-dd hh:mm";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    private Date examDate = simpleDateFormat.parse("2020-04-15 09:00");

    private String studentName = "Kiss Martina";
    private String studentNeptun = "W94PVA";
    private String studentEmail = "kissmartina97@gmail.com";
    private String examSubject = "Diszkrét Matematika II.";
    private String examName = "Félév végi zárthelyi dolgozat";
    private String teacherName = "Dr. Jenei Sándor";
    private int examResultId = 69420;
    private int attainedPoints = 69;
    private int maxPoints = 71;
    private int attainedMark = 5;

    private Button backToMain = new Button("Vissza a főoldalra");

    public ResultView() throws ParseException, TransformerException, ParserConfigurationException {
        setId("result-view");
        VerticalLayout wrapper = createWrapper();
        VerticalLayout container = new VerticalLayout();
        wrapper.setWidth("90%");
        container.setAlignItems(FlexComponent.Alignment.CENTER);

        createTitle(wrapper,"Eredmény");
        createParagraph(wrapper, "Vizsgázó neve: ", studentName);
        createParagraph(wrapper, "Vizsgázó neptun kódja: ", studentNeptun);
        createParagraph(wrapper, "Vizsgázó email címe: ", studentEmail);
        createParagraph(wrapper, "Vizsga tárgya, neve: ", examSubject, "-", examName);
        createParagraph(wrapper, "Vizsga dátuma:", examDate.toString());
        createParagraph(wrapper, "Oktató neve: ", teacherName);
        createParagraph(wrapper, "Vizsga eredmény azonosítója: ", examResultId);
        createParagraph(wrapper, "Maximum elérhető pontok / Elért pontok: ", maxPoints, "/", attainedPoints);
        createParagraph(wrapper, "Elért jegy: ", attainedMark);

        Paragraph paragraph1 = new Paragraph("A fenti tanusítványt kérjük töltse le az alábbi gombra kattintva:");
        wrapper.add(paragraph1);

        Button download = new Button("Tanusítvány letöltése");
        download.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        FileDownloadWrapper buttonWrapper = new FileDownloadWrapper(
//                new StreamResource("foo.txt", () -> new ByteArrayInputStream("foo.txt".getBytes())));
                new StreamResource(String.valueOf(examResultId).concat(".pdf"), () -> {
                    try {
                        return new ByteArrayInputStream(getPDF());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
        );

        buttonWrapper.wrapComponent(download);

        wrapper.add(buttonWrapper);

        Paragraph paragraph2 = new Paragraph("A letöltött tanusítványt őrízze meg a jegy rögzítéséig, hogy igazolni tudja a vizsgán való részvételét és az elért eredményét.");
        wrapper.add(paragraph2);
        createButtonLayout(wrapper);
        container.add(wrapper);
        add(container);
    }

    private void createTitle(VerticalLayout wrapper, String text) {
        H1 h1 = new H1(text);
        wrapper.add(h1);
    }

    private void createParagraph(VerticalLayout wrapper, String text, int value){
        HorizontalLayout container = new HorizontalLayout();
        container.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        Paragraph paragraph = new Paragraph(text);
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

    private void createParagraph(VerticalLayout wrapper, String text, String value1, String value2, String value3){
        HorizontalLayout container = new HorizontalLayout();
        Paragraph paragraph = new Paragraph(text);
        H4 h4First = new H4(value1);
        H4 h4Second = new H4(value2);
        H4 h4Third = new H4(value3);
        container.add(paragraph, h4First, h4Second, h4Third);
        container.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
        wrapper.add(container);
    }

    private void createParagraph(VerticalLayout wrapper, String text, int value1, String text2, int value2){
        HorizontalLayout container = new HorizontalLayout();
        container.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        Paragraph paragraph = new Paragraph(text);
        H4 h4Third = new H4(text2);
        H4 h4First = new H4(String.valueOf(value1));
        H4 h4Second = new H4(String.valueOf(value2));
        container.add(paragraph,h4First, h4Third, h4Second);
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

    private byte[] getPDF() throws IOException {
        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Eredmény </title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>Eredmény</h1>\n" +
                "    <table style=\"width:100%\">\n" +
                "        {{#Query}}\n" +
                "        <tr>\n" +
                "            <td>Vizsgázó neve: </td>\n" +
                "            <td>" + studentName + "</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>Vizsgázó neptun kódja: </td>\n" +
                "            <td>" + studentNeptun + "</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>Vizsgázó email címe: </td>\n" +
                "            <td>{{studentEmail}}</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>Vizsga tárgya, neve: </td>\n" +
                "            <td>{{examSubject}} - {{examName}}</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>Vizsga dátuma: </td>\n" +
                "            <td>{{examDate}}</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>Oktató neve: </td>\n" +
                "            <td>{{teacherName}}</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>Vizsga eredmény azonosítója: </td>\n" +
                "            <td>{{examResultId}}</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>Maximum elérhető pontok / Elért pontok: </td>\n" +
                "            <td>{{maxPoints}} / {{attainedPoints}}</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>Elért jegy: </td>\n" +
                "            <td>{{attainedMark}}</td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "</body>\n </html>";

        List<String> stringList = new ArrayList<>();

        stringList.add("Vizsgázó neve: " + studentName);
        stringList.add("Vizsgázó emailcíme: " + studentEmail);

        for (String line:stringList) {
            System.out.println(line);
        }

        PDDocument document = new PDDocument();
        PDPage page= new PDPage();
        PDFont pdfFont= PDType1Font.HELVETICA_BOLD;
        int fontSize = 14;
        try {
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(pdfFont, fontSize);
            document.addPage(page);
            contentStream.beginText();
            for (String line:stringList) {
                contentStream.showText(line);
                System.out.println("Elméletileg be lett írva, hogy: " + line);
                contentStream.newLine();
            }
            contentStream.endText();
            contentStream.close();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            document.save(os);
            return os.toByteArray();

        }catch (Exception e){
            System.out.println("Caught exception :" + e);
        }
        return null;
    }


    private void createDownloadablePDF () {

    }

}
