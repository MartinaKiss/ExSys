package examsystem.exsys.views.register;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import examsystem.exsys.backend.Entities.Teacher;
import examsystem.exsys.backend.repositories.TeacherRepository;
import examsystem.exsys.views.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Route(value = "register", layout = MainView.class)
@PageTitle("Regisztráció")
@CssImport("styles/views/vizsgalétrehozása/vizsgalétrehozása-view.css")
public class RegisterView extends Div {

    @Autowired
    TeacherRepository teacherRepository;

    private TextField firstName = new TextField();
    private TextField lastName = new TextField();
    private Select<String> teacherTitle= new Select<>();
    private TextField neptunCode = new TextField();
    private TextField email = new TextField();
    private PasswordField password = new PasswordField();
    private PasswordField passwordAgain = new PasswordField();

    private Teacher teacher;
//    private Binder<Teacher> binder;

    private Button registerButton = new Button("Regisztráció");

    @PostConstruct
    public void Init() {
        setId("register-view");
//        binder=new Binder<>(Teacher.class);

        teacherTitle.setItems("-", "Dr.");
        teacherTitle.setValue("-");

        firstName.addValueChangeListener(event -> {
            String origin = event.isFromClient()
                    ? "by the user"
                    : "from code";
            String message = "firstName is " + event.getValue()
                    + " as set " + origin;
            Notification.show(message);
        });

        lastName.addValueChangeListener(event -> {
            String origin = event.isFromClient()
                    ? "by the user"
                    : "from code";
            String message = "lastName is " + event.getValue()
                    + " as set " + origin;
            Notification.show(message);
        });

        neptunCode.addValueChangeListener(event -> {
            String origin = event.isFromClient()
                    ? "by the user"
                    : "from code";
            String message = "NeptunCode is " + event.getValue()
                    + " as set " + origin;
            Notification.show(message);
        });

        teacherTitle.addValueChangeListener(event -> {
            String origin = event.isFromClient()
                    ? "by the user"
                    : "from code";
            String message = "Title is " + event.getValue()
                    + " as set " + origin;
            Notification.show(message);
        });

        email.addValueChangeListener(event -> {
            String origin = event.isFromClient()
                    ? "by the user"
                    : "from code";
            String message = "Email is " + event.getValue()
                    + " as set " + origin;
            Notification.show(message);
        });

        password.addValueChangeListener(event -> {
            String origin = event.isFromClient()
                    ? "by the user"
                    : "from code";
            String message = "Password is " + event.getValue()
                    + " as set " + origin;
            Notification.show(message);
        });

        passwordAgain.addValueChangeListener(event -> {
            String origin = event.isFromClient()
                    ? "by the user"
                    : "from code";
            String message = "PasswordAgain is " + event.getValue()
                    + " as set " + origin;
            Notification.show(message);
        });

        Div value = new Div();
        value.setText("Select a value");
        teacherTitle.addValueChangeListener(
                event -> value.setText("Selected: " + event.getValue()));

        VerticalLayout wrapper = createWrapper();
        VerticalLayout container = new VerticalLayout();

        container.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        container.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        container.setAlignItems(FlexComponent.Alignment.CENTER);

        wrapper.setWidth("90%");
        container.add(wrapper);

        createTitle(wrapper, "Regisztráció");
        createParagraph(wrapper, "Az alábbi adatlap kitöltésével regisztrálhat az ExSys online vizsgarendszerbe." +
                " Diákoknak nincs szükségük regisztrációra a vizsgarendszer használatához.");
        createFormLayout(wrapper);
        createButtonLayout(wrapper);

        wrapper.setWidth("90%");

        registerButton.addClickListener(buttonClickEvent -> {
            teacher = new Teacher();
            teacher.setNeptunCode(neptunCode.getValue());
            try {
                if(Long.getLong(String.valueOf(teacher.getTeacherId())) == null){
                    if(teacherRepository.findAll().isEmpty() || teacherRepository.findAll().stream().noneMatch(
                            lambdaTeacher -> lambdaTeacher.getNeptunCode().equals(teacher.getNeptunCode()))) {
                        if (password.getValue().equals(passwordAgain.getValue())) {
                            teacher.setTeacherFirstName(firstName.getValue());
                            teacher.setTeacherLastName(lastName.getValue());
                            teacher.setTeacherTitle(teacherTitle.getValue());
                            teacher.setEmailAddress(email.getValue());
                            teacher.setPassword(password.getValue());
                            teacherRepository.save(teacher);
                            Notification.show("Sikeres regisztráció");

                        } else {
                            Notification.show("A jelszavak nem egyeznek");
                        }
                    }
                    else{
                        Notification.show("Ezzel a neptun kóddal már regisztrált tanár.");
                    }
                }
                else{
                    Notification.show("Valami nem jó");

                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

//        binder.bindInstanceFields(this);
        add(container);
    }

    public void initSave() {
        teacher = new Teacher();
//        binder.setBean(teacher);
        setVisible(true);
    }

    public void initEdit(int id) {

        try {
            this.teacher = teacherRepository.findById(id);
//            binder.setBean(teacher);
            setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setTeacher(Teacher teacher) {
        if(teacher!=null){
            this.teacher = teacher;
//            binder.setBean(this.teacher);
        }
        this.teacher= teacher;
    }

    private void createTitle(VerticalLayout wrapper, String text) {
        H1 h1 = new H1(text);
        wrapper.add(h1);
    }

    private void createParagraph(VerticalLayout wrapper, String text){
        Paragraph paragraph = new Paragraph(text);
        wrapper.add(paragraph);
    }

    private VerticalLayout createWrapper() {
        VerticalLayout wrapper = new VerticalLayout();
        wrapper.setId("wrapper");
        wrapper.setSpacing(true);
        return wrapper;
    }

    private void createFormLayout(VerticalLayout wrapper) {
        FormLayout formLayout = new FormLayout();
        FormLayout.FormItem lastNameFormItem = addFormItem(wrapper, formLayout,
                lastName, "Vezetéknév");
        formLayout.setColspan(lastNameFormItem, 1);
        FormLayout.FormItem firstNameFormItem = addFormItem(wrapper, formLayout,
                firstName, "Keresztnév");
        formLayout.setColspan(firstNameFormItem, 1);
        FormLayout.FormItem titleFormItem = addFormItem(wrapper, formLayout,
                teacherTitle, "Titulus");
        formLayout.setColspan(titleFormItem, 1);
        FormLayout.FormItem emailFormItem = addFormItem(wrapper, formLayout,
                email, "Email");
        formLayout.setColspan(emailFormItem, 1);
        FormLayout.FormItem neptunCodeFormItem = addFormItem(wrapper, formLayout,
                neptunCode, "Neptun kód");
        formLayout.setColspan(neptunCodeFormItem, 1);
        FormLayout.FormItem passwordFormItem = addFormItem(wrapper, formLayout,
                password, "Jelszó");
        formLayout.setColspan(passwordFormItem, 1);
        FormLayout.FormItem passwordAgainFormItem = addFormItem(wrapper, formLayout,
                passwordAgain, "Jelszó újra");
        formLayout.setColspan(passwordAgainFormItem, 1);

    }

    private void createButtonLayout(VerticalLayout wrapper) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        buttonLayout.setWidth("100%");
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(registerButton);
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
